import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../lookup-item.test-samples';

import { LookupItemFormService } from './lookup-item-form.service';

describe('LookupItem Form Service', () => {
  let service: LookupItemFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LookupItemFormService);
  });

  describe('Service methods', () => {
    describe('createLookupItemFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLookupItemFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            ordem: expect.any(Object),
            isSistema: expect.any(Object),
            descricao: expect.any(Object),
            lookup: expect.any(Object),
          })
        );
      });

      it('passing ILookupItem should create a new form with FormGroup', () => {
        const formGroup = service.createLookupItemFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            ordem: expect.any(Object),
            isSistema: expect.any(Object),
            descricao: expect.any(Object),
            lookup: expect.any(Object),
          })
        );
      });
    });

    describe('getLookupItem', () => {
      it('should return NewLookupItem for default LookupItem initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLookupItemFormGroup(sampleWithNewData);

        const lookupItem = service.getLookupItem(formGroup) as any;

        expect(lookupItem).toMatchObject(sampleWithNewData);
      });

      it('should return NewLookupItem for empty LookupItem initial value', () => {
        const formGroup = service.createLookupItemFormGroup();

        const lookupItem = service.getLookupItem(formGroup) as any;

        expect(lookupItem).toMatchObject({});
      });

      it('should return ILookupItem', () => {
        const formGroup = service.createLookupItemFormGroup(sampleWithRequiredData);

        const lookupItem = service.getLookupItem(formGroup) as any;

        expect(lookupItem).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILookupItem should not enable id FormControl', () => {
        const formGroup = service.createLookupItemFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLookupItem should disable id FormControl', () => {
        const formGroup = service.createLookupItemFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
