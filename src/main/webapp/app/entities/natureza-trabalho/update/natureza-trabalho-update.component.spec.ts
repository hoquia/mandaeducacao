import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NaturezaTrabalhoFormService } from './natureza-trabalho-form.service';
import { NaturezaTrabalhoService } from '../service/natureza-trabalho.service';
import { INaturezaTrabalho } from '../natureza-trabalho.model';

import { NaturezaTrabalhoUpdateComponent } from './natureza-trabalho-update.component';

describe('NaturezaTrabalho Management Update Component', () => {
  let comp: NaturezaTrabalhoUpdateComponent;
  let fixture: ComponentFixture<NaturezaTrabalhoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let naturezaTrabalhoFormService: NaturezaTrabalhoFormService;
  let naturezaTrabalhoService: NaturezaTrabalhoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NaturezaTrabalhoUpdateComponent],
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
      .overrideTemplate(NaturezaTrabalhoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NaturezaTrabalhoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    naturezaTrabalhoFormService = TestBed.inject(NaturezaTrabalhoFormService);
    naturezaTrabalhoService = TestBed.inject(NaturezaTrabalhoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const naturezaTrabalho: INaturezaTrabalho = { id: 456 };

      activatedRoute.data = of({ naturezaTrabalho });
      comp.ngOnInit();

      expect(comp.naturezaTrabalho).toEqual(naturezaTrabalho);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INaturezaTrabalho>>();
      const naturezaTrabalho = { id: 123 };
      jest.spyOn(naturezaTrabalhoFormService, 'getNaturezaTrabalho').mockReturnValue(naturezaTrabalho);
      jest.spyOn(naturezaTrabalhoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ naturezaTrabalho });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: naturezaTrabalho }));
      saveSubject.complete();

      // THEN
      expect(naturezaTrabalhoFormService.getNaturezaTrabalho).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(naturezaTrabalhoService.update).toHaveBeenCalledWith(expect.objectContaining(naturezaTrabalho));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INaturezaTrabalho>>();
      const naturezaTrabalho = { id: 123 };
      jest.spyOn(naturezaTrabalhoFormService, 'getNaturezaTrabalho').mockReturnValue({ id: null });
      jest.spyOn(naturezaTrabalhoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ naturezaTrabalho: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: naturezaTrabalho }));
      saveSubject.complete();

      // THEN
      expect(naturezaTrabalhoFormService.getNaturezaTrabalho).toHaveBeenCalled();
      expect(naturezaTrabalhoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INaturezaTrabalho>>();
      const naturezaTrabalho = { id: 123 };
      jest.spyOn(naturezaTrabalhoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ naturezaTrabalho });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(naturezaTrabalhoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
