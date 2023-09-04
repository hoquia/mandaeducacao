import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AnexoDiscenteFormService } from './anexo-discente-form.service';
import { AnexoDiscenteService } from '../service/anexo-discente.service';
import { IAnexoDiscente } from '../anexo-discente.model';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';

import { AnexoDiscenteUpdateComponent } from './anexo-discente-update.component';

describe('AnexoDiscente Management Update Component', () => {
  let comp: AnexoDiscenteUpdateComponent;
  let fixture: ComponentFixture<AnexoDiscenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoDiscenteFormService: AnexoDiscenteFormService;
  let anexoDiscenteService: AnexoDiscenteService;
  let discenteService: DiscenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AnexoDiscenteUpdateComponent],
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
      .overrideTemplate(AnexoDiscenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoDiscenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoDiscenteFormService = TestBed.inject(AnexoDiscenteFormService);
    anexoDiscenteService = TestBed.inject(AnexoDiscenteService);
    discenteService = TestBed.inject(DiscenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Discente query and add missing value', () => {
      const anexoDiscente: IAnexoDiscente = { id: 456 };
      const discente: IDiscente = { id: 16893 };
      anexoDiscente.discente = discente;

      const discenteCollection: IDiscente[] = [{ id: 49826 }];
      jest.spyOn(discenteService, 'query').mockReturnValue(of(new HttpResponse({ body: discenteCollection })));
      const additionalDiscentes = [discente];
      const expectedCollection: IDiscente[] = [...additionalDiscentes, ...discenteCollection];
      jest.spyOn(discenteService, 'addDiscenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoDiscente });
      comp.ngOnInit();

      expect(discenteService.query).toHaveBeenCalled();
      expect(discenteService.addDiscenteToCollectionIfMissing).toHaveBeenCalledWith(
        discenteCollection,
        ...additionalDiscentes.map(expect.objectContaining)
      );
      expect(comp.discentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoDiscente: IAnexoDiscente = { id: 456 };
      const discente: IDiscente = { id: 62602 };
      anexoDiscente.discente = discente;

      activatedRoute.data = of({ anexoDiscente });
      comp.ngOnInit();

      expect(comp.discentesSharedCollection).toContain(discente);
      expect(comp.anexoDiscente).toEqual(anexoDiscente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoDiscente>>();
      const anexoDiscente = { id: 123 };
      jest.spyOn(anexoDiscenteFormService, 'getAnexoDiscente').mockReturnValue(anexoDiscente);
      jest.spyOn(anexoDiscenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoDiscente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoDiscente }));
      saveSubject.complete();

      // THEN
      expect(anexoDiscenteFormService.getAnexoDiscente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoDiscenteService.update).toHaveBeenCalledWith(expect.objectContaining(anexoDiscente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoDiscente>>();
      const anexoDiscente = { id: 123 };
      jest.spyOn(anexoDiscenteFormService, 'getAnexoDiscente').mockReturnValue({ id: null });
      jest.spyOn(anexoDiscenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoDiscente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoDiscente }));
      saveSubject.complete();

      // THEN
      expect(anexoDiscenteFormService.getAnexoDiscente).toHaveBeenCalled();
      expect(anexoDiscenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoDiscente>>();
      const anexoDiscente = { id: 123 };
      jest.spyOn(anexoDiscenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoDiscente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoDiscenteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDiscente', () => {
      it('Should forward to discenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(discenteService, 'compareDiscente');
        comp.compareDiscente(entity, entity2);
        expect(discenteService.compareDiscente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
