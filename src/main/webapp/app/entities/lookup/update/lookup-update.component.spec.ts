import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LookupFormService } from './lookup-form.service';
import { LookupService } from '../service/lookup.service';
import { ILookup } from '../lookup.model';

import { LookupUpdateComponent } from './lookup-update.component';

describe('Lookup Management Update Component', () => {
  let comp: LookupUpdateComponent;
  let fixture: ComponentFixture<LookupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let lookupFormService: LookupFormService;
  let lookupService: LookupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LookupUpdateComponent],
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
      .overrideTemplate(LookupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LookupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    lookupFormService = TestBed.inject(LookupFormService);
    lookupService = TestBed.inject(LookupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const lookup: ILookup = { id: 456 };

      activatedRoute.data = of({ lookup });
      comp.ngOnInit();

      expect(comp.lookup).toEqual(lookup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILookup>>();
      const lookup = { id: 123 };
      jest.spyOn(lookupFormService, 'getLookup').mockReturnValue(lookup);
      jest.spyOn(lookupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lookup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lookup }));
      saveSubject.complete();

      // THEN
      expect(lookupFormService.getLookup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(lookupService.update).toHaveBeenCalledWith(expect.objectContaining(lookup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILookup>>();
      const lookup = { id: 123 };
      jest.spyOn(lookupFormService, 'getLookup').mockReturnValue({ id: null });
      jest.spyOn(lookupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lookup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lookup }));
      saveSubject.complete();

      // THEN
      expect(lookupFormService.getLookup).toHaveBeenCalled();
      expect(lookupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILookup>>();
      const lookup = { id: 123 };
      jest.spyOn(lookupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lookup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(lookupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
