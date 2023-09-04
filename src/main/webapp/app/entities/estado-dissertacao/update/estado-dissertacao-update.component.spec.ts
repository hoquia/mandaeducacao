import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EstadoDissertacaoFormService } from './estado-dissertacao-form.service';
import { EstadoDissertacaoService } from '../service/estado-dissertacao.service';
import { IEstadoDissertacao } from '../estado-dissertacao.model';

import { EstadoDissertacaoUpdateComponent } from './estado-dissertacao-update.component';

describe('EstadoDissertacao Management Update Component', () => {
  let comp: EstadoDissertacaoUpdateComponent;
  let fixture: ComponentFixture<EstadoDissertacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let estadoDissertacaoFormService: EstadoDissertacaoFormService;
  let estadoDissertacaoService: EstadoDissertacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EstadoDissertacaoUpdateComponent],
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
      .overrideTemplate(EstadoDissertacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstadoDissertacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    estadoDissertacaoFormService = TestBed.inject(EstadoDissertacaoFormService);
    estadoDissertacaoService = TestBed.inject(EstadoDissertacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const estadoDissertacao: IEstadoDissertacao = { id: 456 };

      activatedRoute.data = of({ estadoDissertacao });
      comp.ngOnInit();

      expect(comp.estadoDissertacao).toEqual(estadoDissertacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstadoDissertacao>>();
      const estadoDissertacao = { id: 123 };
      jest.spyOn(estadoDissertacaoFormService, 'getEstadoDissertacao').mockReturnValue(estadoDissertacao);
      jest.spyOn(estadoDissertacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadoDissertacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estadoDissertacao }));
      saveSubject.complete();

      // THEN
      expect(estadoDissertacaoFormService.getEstadoDissertacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(estadoDissertacaoService.update).toHaveBeenCalledWith(expect.objectContaining(estadoDissertacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstadoDissertacao>>();
      const estadoDissertacao = { id: 123 };
      jest.spyOn(estadoDissertacaoFormService, 'getEstadoDissertacao').mockReturnValue({ id: null });
      jest.spyOn(estadoDissertacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadoDissertacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estadoDissertacao }));
      saveSubject.complete();

      // THEN
      expect(estadoDissertacaoFormService.getEstadoDissertacao).toHaveBeenCalled();
      expect(estadoDissertacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstadoDissertacao>>();
      const estadoDissertacao = { id: 123 };
      jest.spyOn(estadoDissertacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadoDissertacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(estadoDissertacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
