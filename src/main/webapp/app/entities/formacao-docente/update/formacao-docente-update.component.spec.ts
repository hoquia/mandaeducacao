import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FormacaoDocenteFormService } from './formacao-docente-form.service';
import { FormacaoDocenteService } from '../service/formacao-docente.service';
import { IFormacaoDocente } from '../formacao-docente.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';

import { FormacaoDocenteUpdateComponent } from './formacao-docente-update.component';

describe('FormacaoDocente Management Update Component', () => {
  let comp: FormacaoDocenteUpdateComponent;
  let fixture: ComponentFixture<FormacaoDocenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let formacaoDocenteFormService: FormacaoDocenteFormService;
  let formacaoDocenteService: FormacaoDocenteService;
  let lookupItemService: LookupItemService;
  let docenteService: DocenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FormacaoDocenteUpdateComponent],
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
      .overrideTemplate(FormacaoDocenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FormacaoDocenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    formacaoDocenteFormService = TestBed.inject(FormacaoDocenteFormService);
    formacaoDocenteService = TestBed.inject(FormacaoDocenteService);
    lookupItemService = TestBed.inject(LookupItemService);
    docenteService = TestBed.inject(DocenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LookupItem query and add missing value', () => {
      const formacaoDocente: IFormacaoDocente = { id: 456 };
      const grauAcademico: ILookupItem = { id: 46326 };
      formacaoDocente.grauAcademico = grauAcademico;

      const lookupItemCollection: ILookupItem[] = [{ id: 3663 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [grauAcademico];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formacaoDocente });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Docente query and add missing value', () => {
      const formacaoDocente: IFormacaoDocente = { id: 456 };
      const docente: IDocente = { id: 98518 };
      formacaoDocente.docente = docente;

      const docenteCollection: IDocente[] = [{ id: 47726 }];
      jest.spyOn(docenteService, 'query').mockReturnValue(of(new HttpResponse({ body: docenteCollection })));
      const additionalDocentes = [docente];
      const expectedCollection: IDocente[] = [...additionalDocentes, ...docenteCollection];
      jest.spyOn(docenteService, 'addDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formacaoDocente });
      comp.ngOnInit();

      expect(docenteService.query).toHaveBeenCalled();
      expect(docenteService.addDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        docenteCollection,
        ...additionalDocentes.map(expect.objectContaining)
      );
      expect(comp.docentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const formacaoDocente: IFormacaoDocente = { id: 456 };
      const grauAcademico: ILookupItem = { id: 4833 };
      formacaoDocente.grauAcademico = grauAcademico;
      const docente: IDocente = { id: 90599 };
      formacaoDocente.docente = docente;

      activatedRoute.data = of({ formacaoDocente });
      comp.ngOnInit();

      expect(comp.lookupItemsSharedCollection).toContain(grauAcademico);
      expect(comp.docentesSharedCollection).toContain(docente);
      expect(comp.formacaoDocente).toEqual(formacaoDocente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormacaoDocente>>();
      const formacaoDocente = { id: 123 };
      jest.spyOn(formacaoDocenteFormService, 'getFormacaoDocente').mockReturnValue(formacaoDocente);
      jest.spyOn(formacaoDocenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formacaoDocente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formacaoDocente }));
      saveSubject.complete();

      // THEN
      expect(formacaoDocenteFormService.getFormacaoDocente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(formacaoDocenteService.update).toHaveBeenCalledWith(expect.objectContaining(formacaoDocente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormacaoDocente>>();
      const formacaoDocente = { id: 123 };
      jest.spyOn(formacaoDocenteFormService, 'getFormacaoDocente').mockReturnValue({ id: null });
      jest.spyOn(formacaoDocenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formacaoDocente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formacaoDocente }));
      saveSubject.complete();

      // THEN
      expect(formacaoDocenteFormService.getFormacaoDocente).toHaveBeenCalled();
      expect(formacaoDocenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormacaoDocente>>();
      const formacaoDocente = { id: 123 };
      jest.spyOn(formacaoDocenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formacaoDocente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(formacaoDocenteService.update).toHaveBeenCalled();
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

    describe('compareDocente', () => {
      it('Should forward to docenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(docenteService, 'compareDocente');
        comp.compareDocente(entity, entity2);
        expect(docenteService.compareDocente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
