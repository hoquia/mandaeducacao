import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../preco-emolumento.test-samples';

import { PrecoEmolumentoFormService } from './preco-emolumento-form.service';

describe('PrecoEmolumento Form Service', () => {
  let service: PrecoEmolumentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrecoEmolumentoFormService);
  });

  describe('Service methods', () => {
    describe('createPrecoEmolumentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrecoEmolumentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            preco: expect.any(Object),
            isEspecificoCurso: expect.any(Object),
            isEspecificoAreaFormacao: expect.any(Object),
            isEspecificoClasse: expect.any(Object),
            isEspecificoTurno: expect.any(Object),
            utilizador: expect.any(Object),
            emolumento: expect.any(Object),
            areaFormacao: expect.any(Object),
            curso: expect.any(Object),
            classe: expect.any(Object),
            turno: expect.any(Object),
            planoMulta: expect.any(Object),
          })
        );
      });

      it('passing IPrecoEmolumento should create a new form with FormGroup', () => {
        const formGroup = service.createPrecoEmolumentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            preco: expect.any(Object),
            isEspecificoCurso: expect.any(Object),
            isEspecificoAreaFormacao: expect.any(Object),
            isEspecificoClasse: expect.any(Object),
            isEspecificoTurno: expect.any(Object),
            utilizador: expect.any(Object),
            emolumento: expect.any(Object),
            areaFormacao: expect.any(Object),
            curso: expect.any(Object),
            classe: expect.any(Object),
            turno: expect.any(Object),
            planoMulta: expect.any(Object),
          })
        );
      });
    });

    describe('getPrecoEmolumento', () => {
      it('should return NewPrecoEmolumento for default PrecoEmolumento initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrecoEmolumentoFormGroup(sampleWithNewData);

        const precoEmolumento = service.getPrecoEmolumento(formGroup) as any;

        expect(precoEmolumento).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrecoEmolumento for empty PrecoEmolumento initial value', () => {
        const formGroup = service.createPrecoEmolumentoFormGroup();

        const precoEmolumento = service.getPrecoEmolumento(formGroup) as any;

        expect(precoEmolumento).toMatchObject({});
      });

      it('should return IPrecoEmolumento', () => {
        const formGroup = service.createPrecoEmolumentoFormGroup(sampleWithRequiredData);

        const precoEmolumento = service.getPrecoEmolumento(formGroup) as any;

        expect(precoEmolumento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrecoEmolumento should not enable id FormControl', () => {
        const formGroup = service.createPrecoEmolumentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPrecoEmolumento should disable id FormControl', () => {
        const formGroup = service.createPrecoEmolumentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
