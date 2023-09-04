import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DiscenteFormService } from './discente-form.service';
import { DiscenteService } from '../service/discente.service';
import { IDiscente } from '../discente.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IEncarregadoEducacao } from 'app/entities/encarregado-educacao/encarregado-educacao.model';
import { EncarregadoEducacaoService } from 'app/entities/encarregado-educacao/service/encarregado-educacao.service';

import { DiscenteUpdateComponent } from './discente-update.component';

describe('Discente Management Update Component', () => {
  let comp: DiscenteUpdateComponent;
  let fixture: ComponentFixture<DiscenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let discenteFormService: DiscenteFormService;
  let discenteService: DiscenteService;
  let lookupItemService: LookupItemService;
  let encarregadoEducacaoService: EncarregadoEducacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DiscenteUpdateComponent],
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
      .overrideTemplate(DiscenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiscenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    discenteFormService = TestBed.inject(DiscenteFormService);
    discenteService = TestBed.inject(DiscenteService);
    lookupItemService = TestBed.inject(LookupItemService);
    encarregadoEducacaoService = TestBed.inject(EncarregadoEducacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LookupItem query and add missing value', () => {
      const discente: IDiscente = { id: 456 };
      const nacionalidade: ILookupItem = { id: 2164 };
      discente.nacionalidade = nacionalidade;
      const naturalidade: ILookupItem = { id: 81192 };
      discente.naturalidade = naturalidade;
      const tipoDocumento: ILookupItem = { id: 57819 };
      discente.tipoDocumento = tipoDocumento;
      const profissao: ILookupItem = { id: 39249 };
      discente.profissao = profissao;
      const grupoSanguinio: ILookupItem = { id: 5955 };
      discente.grupoSanguinio = grupoSanguinio;
      const necessidadeEspecial: ILookupItem = { id: 96144 };
      discente.necessidadeEspecial = necessidadeEspecial;

      const lookupItemCollection: ILookupItem[] = [{ id: 4681 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [nacionalidade, naturalidade, tipoDocumento, profissao, grupoSanguinio, necessidadeEspecial];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ discente });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EncarregadoEducacao query and add missing value', () => {
      const discente: IDiscente = { id: 456 };
      const encarregadoEducacao: IEncarregadoEducacao = { id: 76161 };
      discente.encarregadoEducacao = encarregadoEducacao;

      const encarregadoEducacaoCollection: IEncarregadoEducacao[] = [{ id: 60111 }];
      jest.spyOn(encarregadoEducacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: encarregadoEducacaoCollection })));
      const additionalEncarregadoEducacaos = [encarregadoEducacao];
      const expectedCollection: IEncarregadoEducacao[] = [...additionalEncarregadoEducacaos, ...encarregadoEducacaoCollection];
      jest.spyOn(encarregadoEducacaoService, 'addEncarregadoEducacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ discente });
      comp.ngOnInit();

      expect(encarregadoEducacaoService.query).toHaveBeenCalled();
      expect(encarregadoEducacaoService.addEncarregadoEducacaoToCollectionIfMissing).toHaveBeenCalledWith(
        encarregadoEducacaoCollection,
        ...additionalEncarregadoEducacaos.map(expect.objectContaining)
      );
      expect(comp.encarregadoEducacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const discente: IDiscente = { id: 456 };
      const nacionalidade: ILookupItem = { id: 64773 };
      discente.nacionalidade = nacionalidade;
      const naturalidade: ILookupItem = { id: 77600 };
      discente.naturalidade = naturalidade;
      const tipoDocumento: ILookupItem = { id: 75762 };
      discente.tipoDocumento = tipoDocumento;
      const profissao: ILookupItem = { id: 54180 };
      discente.profissao = profissao;
      const grupoSanguinio: ILookupItem = { id: 78901 };
      discente.grupoSanguinio = grupoSanguinio;
      const necessidadeEspecial: ILookupItem = { id: 10752 };
      discente.necessidadeEspecial = necessidadeEspecial;
      const encarregadoEducacao: IEncarregadoEducacao = { id: 6795 };
      discente.encarregadoEducacao = encarregadoEducacao;

      activatedRoute.data = of({ discente });
      comp.ngOnInit();

      expect(comp.lookupItemsSharedCollection).toContain(nacionalidade);
      expect(comp.lookupItemsSharedCollection).toContain(naturalidade);
      expect(comp.lookupItemsSharedCollection).toContain(tipoDocumento);
      expect(comp.lookupItemsSharedCollection).toContain(profissao);
      expect(comp.lookupItemsSharedCollection).toContain(grupoSanguinio);
      expect(comp.lookupItemsSharedCollection).toContain(necessidadeEspecial);
      expect(comp.encarregadoEducacaosSharedCollection).toContain(encarregadoEducacao);
      expect(comp.discente).toEqual(discente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiscente>>();
      const discente = { id: 123 };
      jest.spyOn(discenteFormService, 'getDiscente').mockReturnValue(discente);
      jest.spyOn(discenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ discente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: discente }));
      saveSubject.complete();

      // THEN
      expect(discenteFormService.getDiscente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(discenteService.update).toHaveBeenCalledWith(expect.objectContaining(discente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiscente>>();
      const discente = { id: 123 };
      jest.spyOn(discenteFormService, 'getDiscente').mockReturnValue({ id: null });
      jest.spyOn(discenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ discente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: discente }));
      saveSubject.complete();

      // THEN
      expect(discenteFormService.getDiscente).toHaveBeenCalled();
      expect(discenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiscente>>();
      const discente = { id: 123 };
      jest.spyOn(discenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ discente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(discenteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLookupItem', () => {
      it('Should forward to lookupItemService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(lookupItemService, 'compareLookupItem');
        comp.compareLookupItem(entity, entity2);
        expect(lookupItemService.compareLookupItem).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEncarregadoEducacao', () => {
      it('Should forward to encarregadoEducacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(encarregadoEducacaoService, 'compareEncarregadoEducacao');
        comp.compareEncarregadoEducacao(entity, entity2);
        expect(encarregadoEducacaoService.compareEncarregadoEducacao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
