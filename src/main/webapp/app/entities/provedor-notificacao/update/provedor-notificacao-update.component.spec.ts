import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProvedorNotificacaoFormService } from './provedor-notificacao-form.service';
import { ProvedorNotificacaoService } from '../service/provedor-notificacao.service';
import { IProvedorNotificacao } from '../provedor-notificacao.model';
import { IInstituicaoEnsino } from 'app/entities/instituicao-ensino/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/service/instituicao-ensino.service';

import { ProvedorNotificacaoUpdateComponent } from './provedor-notificacao-update.component';

describe('ProvedorNotificacao Management Update Component', () => {
  let comp: ProvedorNotificacaoUpdateComponent;
  let fixture: ComponentFixture<ProvedorNotificacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let provedorNotificacaoFormService: ProvedorNotificacaoFormService;
  let provedorNotificacaoService: ProvedorNotificacaoService;
  let instituicaoEnsinoService: InstituicaoEnsinoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProvedorNotificacaoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProvedorNotificacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProvedorNotificacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    provedorNotificacaoFormService = TestBed.inject(ProvedorNotificacaoFormService);
    provedorNotificacaoService = TestBed.inject(ProvedorNotificacaoService);
    instituicaoEnsinoService = TestBed.inject(InstituicaoEnsinoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call InstituicaoEnsino query and add missing value', () => {
      const provedorNotificacao: IProvedorNotificacao = { id: 456 };
      const instituicao: IInstituicaoEnsino = { id: 12799 };
      provedorNotificacao.instituicao = instituicao;

      const instituicaoEnsinoCollection: IInstituicaoEnsino[] = [{ id: 31915 }];
      jest.spyOn(instituicaoEnsinoService, 'query').mockReturnValue(of(new HttpResponse({ body: instituicaoEnsinoCollection })));
      const additionalInstituicaoEnsinos = [instituicao];
      const expectedCollection: IInstituicaoEnsino[] = [...additionalInstituicaoEnsinos, ...instituicaoEnsinoCollection];
      jest.spyOn(instituicaoEnsinoService, 'addInstituicaoEnsinoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ provedorNotificacao });
      comp.ngOnInit();

      expect(instituicaoEnsinoService.query).toHaveBeenCalled();
      expect(instituicaoEnsinoService.addInstituicaoEnsinoToCollectionIfMissing).toHaveBeenCalledWith(
        instituicaoEnsinoCollection,
        ...additionalInstituicaoEnsinos.map(expect.objectContaining)
      );
      expect(comp.instituicaoEnsinosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const provedorNotificacao: IProvedorNotificacao = { id: 456 };
      const instituicao: IInstituicaoEnsino = { id: 57084 };
      provedorNotificacao.instituicao = instituicao;

      activatedRoute.data = of({ provedorNotificacao });
      comp.ngOnInit();

      expect(comp.instituicaoEnsinosSharedCollection).toContain(instituicao);
      expect(comp.provedorNotificacao).toEqual(provedorNotificacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProvedorNotificacao>>();
      const provedorNotificacao = { id: 123 };
      jest.spyOn(provedorNotificacaoFormService, 'getProvedorNotificacao').mockReturnValue(provedorNotificacao);
      jest.spyOn(provedorNotificacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provedorNotificacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: provedorNotificacao }));
      saveSubject.complete();

      // THEN
      expect(provedorNotificacaoFormService.getProvedorNotificacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(provedorNotificacaoService.update).toHaveBeenCalledWith(expect.objectContaining(provedorNotificacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProvedorNotificacao>>();
      const provedorNotificacao = { id: 123 };
      jest.spyOn(provedorNotificacaoFormService, 'getProvedorNotificacao').mockReturnValue({ id: null });
      jest.spyOn(provedorNotificacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provedorNotificacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: provedorNotificacao }));
      saveSubject.complete();

      // THEN
      expect(provedorNotificacaoFormService.getProvedorNotificacao).toHaveBeenCalled();
      expect(provedorNotificacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProvedorNotificacao>>();
      const provedorNotificacao = { id: 123 };
      jest.spyOn(provedorNotificacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provedorNotificacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(provedorNotificacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareInstituicaoEnsino', () => {
      it('Should forward to instituicaoEnsinoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(instituicaoEnsinoService, 'compareInstituicaoEnsino');
        comp.compareInstituicaoEnsino(entity, entity2);
        expect(instituicaoEnsinoService.compareInstituicaoEnsino).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
