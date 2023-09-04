import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LookupItemFormService } from './lookup-item-form.service';
import { LookupItemService } from '../service/lookup-item.service';
import { ILookupItem } from '../lookup-item.model';
import { ILookup } from 'app/entities/lookup/lookup.model';
import { LookupService } from 'app/entities/lookup/service/lookup.service';

import { LookupItemUpdateComponent } from './lookup-item-update.component';

describe('LookupItem Management Update Component', () => {
  let comp: LookupItemUpdateComponent;
  let fixture: ComponentFixture<LookupItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let lookupItemFormService: LookupItemFormService;
  let lookupItemService: LookupItemService;
  let lookupService: LookupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LookupItemUpdateComponent],
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
      .overrideTemplate(LookupItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LookupItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    lookupItemFormService = TestBed.inject(LookupItemFormService);
    lookupItemService = TestBed.inject(LookupItemService);
    lookupService = TestBed.inject(LookupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Lookup query and add missing value', () => {
      const lookupItem: ILookupItem = { id: 456 };
      const lookup: ILookup = { id: 80264 };
      lookupItem.lookup = lookup;

      const lookupCollection: ILookup[] = [{ id: 16274 }];
      jest.spyOn(lookupService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupCollection })));
      const additionalLookups = [lookup];
      const expectedCollection: ILookup[] = [...additionalLookups, ...lookupCollection];
      jest.spyOn(lookupService, 'addLookupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ lookupItem });
      comp.ngOnInit();

      expect(lookupService.query).toHaveBeenCalled();
      expect(lookupService.addLookupToCollectionIfMissing).toHaveBeenCalledWith(
        lookupCollection,
        ...additionalLookups.map(expect.objectContaining)
      );
      expect(comp.lookupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const lookupItem: ILookupItem = { id: 456 };
      const lookup: ILookup = { id: 19470 };
      lookupItem.lookup = lookup;

      activatedRoute.data = of({ lookupItem });
      comp.ngOnInit();

      expect(comp.lookupsSharedCollection).toContain(lookup);
      expect(comp.lookupItem).toEqual(lookupItem);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILookupItem>>();
      const lookupItem = { id: 123 };
      jest.spyOn(lookupItemFormService, 'getLookupItem').mockReturnValue(lookupItem);
      jest.spyOn(lookupItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lookupItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lookupItem }));
      saveSubject.complete();

      // THEN
      expect(lookupItemFormService.getLookupItem).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(lookupItemService.update).toHaveBeenCalledWith(expect.objectContaining(lookupItem));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILookupItem>>();
      const lookupItem = { id: 123 };
      jest.spyOn(lookupItemFormService, 'getLookupItem').mockReturnValue({ id: null });
      jest.spyOn(lookupItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lookupItem: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lookupItem }));
      saveSubject.complete();

      // THEN
      expect(lookupItemFormService.getLookupItem).toHaveBeenCalled();
      expect(lookupItemService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILookupItem>>();
      const lookupItem = { id: 123 };
      jest.spyOn(lookupItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lookupItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(lookupItemService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLookup', () => {
      it('Should forward to lookupService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(lookupService, 'compareLookup');
        comp.compareLookup(entity, entity2);
        expect(lookupService.compareLookup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
