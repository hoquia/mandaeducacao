import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AreaFormacaoFormService } from './area-formacao-form.service';
import { AreaFormacaoService } from '../service/area-formacao.service';
import { IAreaFormacao } from '../area-formacao.model';
import { INivelEnsino } from 'app/entities/nivel-ensino/nivel-ensino.model';
import { NivelEnsinoService } from 'app/entities/nivel-ensino/service/nivel-ensino.service';

import { AreaFormacaoUpdateComponent } from './area-formacao-update.component';

describe('AreaFormacao Management Update Component', () => {
  let comp: AreaFormacaoUpdateComponent;
  let fixture: ComponentFixture<AreaFormacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let areaFormacaoFormService: AreaFormacaoFormService;
  let areaFormacaoService: AreaFormacaoService;
  let nivelEnsinoService: NivelEnsinoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AreaFormacaoUpdateComponent],
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
      .overrideTemplate(AreaFormacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AreaFormacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    areaFormacaoFormService = TestBed.inject(AreaFormacaoFormService);
    areaFormacaoService = TestBed.inject(AreaFormacaoService);
    nivelEnsinoService = TestBed.inject(NivelEnsinoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call NivelEnsino query and add missing value', () => {
      const areaFormacao: IAreaFormacao = { id: 456 };
      const nivelEnsino: INivelEnsino = { id: 55738 };
      areaFormacao.nivelEnsino = nivelEnsino;

      const nivelEnsinoCollection: INivelEnsino[] = [{ id: 96198 }];
      jest.spyOn(nivelEnsinoService, 'query').mockReturnValue(of(new HttpResponse({ body: nivelEnsinoCollection })));
      const additionalNivelEnsinos = [nivelEnsino];
      const expectedCollection: INivelEnsino[] = [...additionalNivelEnsinos, ...nivelEnsinoCollection];
      jest.spyOn(nivelEnsinoService, 'addNivelEnsinoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ areaFormacao });
      comp.ngOnInit();

      expect(nivelEnsinoService.query).toHaveBeenCalled();
      expect(nivelEnsinoService.addNivelEnsinoToCollectionIfMissing).toHaveBeenCalledWith(
        nivelEnsinoCollection,
        ...additionalNivelEnsinos.map(expect.objectContaining)
      );
      expect(comp.nivelEnsinosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const areaFormacao: IAreaFormacao = { id: 456 };
      const nivelEnsino: INivelEnsino = { id: 12662 };
      areaFormacao.nivelEnsino = nivelEnsino;

      activatedRoute.data = of({ areaFormacao });
      comp.ngOnInit();

      expect(comp.nivelEnsinosSharedCollection).toContain(nivelEnsino);
      expect(comp.areaFormacao).toEqual(areaFormacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaFormacao>>();
      const areaFormacao = { id: 123 };
      jest.spyOn(areaFormacaoFormService, 'getAreaFormacao').mockReturnValue(areaFormacao);
      jest.spyOn(areaFormacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaFormacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaFormacao }));
      saveSubject.complete();

      // THEN
      expect(areaFormacaoFormService.getAreaFormacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(areaFormacaoService.update).toHaveBeenCalledWith(expect.objectContaining(areaFormacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaFormacao>>();
      const areaFormacao = { id: 123 };
      jest.spyOn(areaFormacaoFormService, 'getAreaFormacao').mockReturnValue({ id: null });
      jest.spyOn(areaFormacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaFormacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaFormacao }));
      saveSubject.complete();

      // THEN
      expect(areaFormacaoFormService.getAreaFormacao).toHaveBeenCalled();
      expect(areaFormacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaFormacao>>();
      const areaFormacao = { id: 123 };
      jest.spyOn(areaFormacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaFormacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(areaFormacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareNivelEnsino', () => {
      it('Should forward to nivelEnsinoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(nivelEnsinoService, 'compareNivelEnsino');
        comp.compareNivelEnsino(entity, entity2);
        expect(nivelEnsinoService.compareNivelEnsino).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
