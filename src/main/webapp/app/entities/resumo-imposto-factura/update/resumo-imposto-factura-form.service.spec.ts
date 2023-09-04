import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../resumo-imposto-factura.test-samples';

import { ResumoImpostoFacturaFormService } from './resumo-imposto-factura-form.service';

describe('ResumoImpostoFactura Form Service', () => {
  let service: ResumoImpostoFacturaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResumoImpostoFacturaFormService);
  });

  describe('Service methods', () => {
    describe('createResumoImpostoFacturaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResumoImpostoFacturaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            isRetencao: expect.any(Object),
            descricao: expect.any(Object),
            tipo: expect.any(Object),
            taxa: expect.any(Object),
            incidencia: expect.any(Object),
            montante: expect.any(Object),
            factura: expect.any(Object),
          })
        );
      });

      it('passing IResumoImpostoFactura should create a new form with FormGroup', () => {
        const formGroup = service.createResumoImpostoFacturaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            isRetencao: expect.any(Object),
            descricao: expect.any(Object),
            tipo: expect.any(Object),
            taxa: expect.any(Object),
            incidencia: expect.any(Object),
            montante: expect.any(Object),
            factura: expect.any(Object),
          })
        );
      });
    });

    describe('getResumoImpostoFactura', () => {
      it('should return NewResumoImpostoFactura for default ResumoImpostoFactura initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResumoImpostoFacturaFormGroup(sampleWithNewData);

        const resumoImpostoFactura = service.getResumoImpostoFactura(formGroup) as any;

        expect(resumoImpostoFactura).toMatchObject(sampleWithNewData);
      });

      it('should return NewResumoImpostoFactura for empty ResumoImpostoFactura initial value', () => {
        const formGroup = service.createResumoImpostoFacturaFormGroup();

        const resumoImpostoFactura = service.getResumoImpostoFactura(formGroup) as any;

        expect(resumoImpostoFactura).toMatchObject({});
      });

      it('should return IResumoImpostoFactura', () => {
        const formGroup = service.createResumoImpostoFacturaFormGroup(sampleWithRequiredData);

        const resumoImpostoFactura = service.getResumoImpostoFactura(formGroup) as any;

        expect(resumoImpostoFactura).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResumoImpostoFactura should not enable id FormControl', () => {
        const formGroup = service.createResumoImpostoFacturaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResumoImpostoFactura should disable id FormControl', () => {
        const formGroup = service.createResumoImpostoFacturaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
