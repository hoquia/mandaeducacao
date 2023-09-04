import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../lookup.test-samples';

import { LookupFormService } from './lookup-form.service';

describe('Lookup Form Service', () => {
  let service: LookupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LookupFormService);
  });

  describe('Service methods', () => {
    describe('createLookupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLookupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            isSistema: expect.any(Object),
            isModificavel: expect.any(Object),
          })
        );
      });

      it('passing ILookup should create a new form with FormGroup', () => {
        const formGroup = service.createLookupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            isSistema: expect.any(Object),
            isModificavel: expect.any(Object),
          })
        );
      });
    });

    describe('getLookup', () => {
      it('should return NewLookup for default Lookup initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLookupFormGroup(sampleWithNewData);

        const lookup = service.getLookup(formGroup) as any;

        expect(lookup).toMatchObject(sampleWithNewData);
      });

      it('should return NewLookup for empty Lookup initial value', () => {
        const formGroup = service.createLookupFormGroup();

        const lookup = service.getLookup(formGroup) as any;

        expect(lookup).toMatchObject({});
      });

      it('should return ILookup', () => {
        const formGroup = service.createLookupFormGroup(sampleWithRequiredData);

        const lookup = service.getLookup(formGroup) as any;

        expect(lookup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILookup should not enable id FormControl', () => {
        const formGroup = service.createLookupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLookup should disable id FormControl', () => {
        const formGroup = service.createLookupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
