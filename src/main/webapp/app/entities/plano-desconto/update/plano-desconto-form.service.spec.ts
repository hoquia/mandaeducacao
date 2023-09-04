import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plano-desconto.test-samples';

import { PlanoDescontoFormService } from './plano-desconto-form.service';

describe('PlanoDesconto Form Service', () => {
  let service: PlanoDescontoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanoDescontoFormService);
  });

  describe('Service methods', () => {
    describe('createPlanoDescontoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanoDescontoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            isIsentoMulta: expect.any(Object),
            isIsentoJuro: expect.any(Object),
            desconto: expect.any(Object),
            categoriasEmolumentos: expect.any(Object),
            matriculas: expect.any(Object),
          })
        );
      });

      it('passing IPlanoDesconto should create a new form with FormGroup', () => {
        const formGroup = service.createPlanoDescontoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            isIsentoMulta: expect.any(Object),
            isIsentoJuro: expect.any(Object),
            desconto: expect.any(Object),
            categoriasEmolumentos: expect.any(Object),
            matriculas: expect.any(Object),
          })
        );
      });
    });

    describe('getPlanoDesconto', () => {
      it('should return NewPlanoDesconto for default PlanoDesconto initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPlanoDescontoFormGroup(sampleWithNewData);

        const planoDesconto = service.getPlanoDesconto(formGroup) as any;

        expect(planoDesconto).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanoDesconto for empty PlanoDesconto initial value', () => {
        const formGroup = service.createPlanoDescontoFormGroup();

        const planoDesconto = service.getPlanoDesconto(formGroup) as any;

        expect(planoDesconto).toMatchObject({});
      });

      it('should return IPlanoDesconto', () => {
        const formGroup = service.createPlanoDescontoFormGroup(sampleWithRequiredData);

        const planoDesconto = service.getPlanoDesconto(formGroup) as any;

        expect(planoDesconto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanoDesconto should not enable id FormControl', () => {
        const formGroup = service.createPlanoDescontoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanoDesconto should disable id FormControl', () => {
        const formGroup = service.createPlanoDescontoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
