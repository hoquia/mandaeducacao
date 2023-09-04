import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DetalhePlanoAulaFormService } from './detalhe-plano-aula-form.service';
import { DetalhePlanoAulaService } from '../service/detalhe-plano-aula.service';
import { IDetalhePlanoAula } from '../detalhe-plano-aula.model';
import { IPlanoAula } from 'app/entities/plano-aula/plano-aula.model';
import { PlanoAulaService } from 'app/entities/plano-aula/service/plano-aula.service';

import { DetalhePlanoAulaUpdateComponent } from './detalhe-plano-aula-update.component';

describe('DetalhePlanoAula Management Update Component', () => {
  let comp: DetalhePlanoAulaUpdateComponent;
  let fixture: ComponentFixture<DetalhePlanoAulaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detalhePlanoAulaFormService: DetalhePlanoAulaFormService;
  let detalhePlanoAulaService: DetalhePlanoAulaService;
  let planoAulaService: PlanoAulaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DetalhePlanoAulaUpdateComponent],
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
      .overrideTemplate(DetalhePlanoAulaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetalhePlanoAulaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detalhePlanoAulaFormService = TestBed.inject(DetalhePlanoAulaFormService);
    detalhePlanoAulaService = TestBed.inject(DetalhePlanoAulaService);
    planoAulaService = TestBed.inject(PlanoAulaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlanoAula query and add missing value', () => {
      const detalhePlanoAula: IDetalhePlanoAula = { id: 456 };
      const planoAula: IPlanoAula = { id: 36401 };
      detalhePlanoAula.planoAula = planoAula;

      const planoAulaCollection: IPlanoAula[] = [{ id: 45616 }];
      jest.spyOn(planoAulaService, 'query').mockReturnValue(of(new HttpResponse({ body: planoAulaCollection })));
      const additionalPlanoAulas = [planoAula];
      const expectedCollection: IPlanoAula[] = [...additionalPlanoAulas, ...planoAulaCollection];
      jest.spyOn(planoAulaService, 'addPlanoAulaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detalhePlanoAula });
      comp.ngOnInit();

      expect(planoAulaService.query).toHaveBeenCalled();
      expect(planoAulaService.addPlanoAulaToCollectionIfMissing).toHaveBeenCalledWith(
        planoAulaCollection,
        ...additionalPlanoAulas.map(expect.objectContaining)
      );
      expect(comp.planoAulasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detalhePlanoAula: IDetalhePlanoAula = { id: 456 };
      const planoAula: IPlanoAula = { id: 32991 };
      detalhePlanoAula.planoAula = planoAula;

      activatedRoute.data = of({ detalhePlanoAula });
      comp.ngOnInit();

      expect(comp.planoAulasSharedCollection).toContain(planoAula);
      expect(comp.detalhePlanoAula).toEqual(detalhePlanoAula);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalhePlanoAula>>();
      const detalhePlanoAula = { id: 123 };
      jest.spyOn(detalhePlanoAulaFormService, 'getDetalhePlanoAula').mockReturnValue(detalhePlanoAula);
      jest.spyOn(detalhePlanoAulaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalhePlanoAula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalhePlanoAula }));
      saveSubject.complete();

      // THEN
      expect(detalhePlanoAulaFormService.getDetalhePlanoAula).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(detalhePlanoAulaService.update).toHaveBeenCalledWith(expect.objectContaining(detalhePlanoAula));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalhePlanoAula>>();
      const detalhePlanoAula = { id: 123 };
      jest.spyOn(detalhePlanoAulaFormService, 'getDetalhePlanoAula').mockReturnValue({ id: null });
      jest.spyOn(detalhePlanoAulaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalhePlanoAula: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalhePlanoAula }));
      saveSubject.complete();

      // THEN
      expect(detalhePlanoAulaFormService.getDetalhePlanoAula).toHaveBeenCalled();
      expect(detalhePlanoAulaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalhePlanoAula>>();
      const detalhePlanoAula = { id: 123 };
      jest.spyOn(detalhePlanoAulaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalhePlanoAula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detalhePlanoAulaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePlanoAula', () => {
      it('Should forward to planoAulaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoAulaService, 'comparePlanoAula');
        comp.comparePlanoAula(entity, entity2);
        expect(planoAulaService.comparePlanoAula).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
