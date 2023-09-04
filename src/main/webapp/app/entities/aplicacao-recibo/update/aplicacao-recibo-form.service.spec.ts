import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../aplicacao-recibo.test-samples';

import { AplicacaoReciboFormService } from './aplicacao-recibo-form.service';

describe('AplicacaoRecibo Form Service', () => {
  let service: AplicacaoReciboFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AplicacaoReciboFormService);
  });

  describe('Service methods', () => {
    describe('createAplicacaoReciboFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAplicacaoReciboFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            totalFactura: expect.any(Object),
            totalPago: expect.any(Object),
            totalDiferenca: expect.any(Object),
            timestamp: expect.any(Object),
            itemFactura: expect.any(Object),
            factura: expect.any(Object),
            recibo: expect.any(Object),
          })
        );
      });

      it('passing IAplicacaoRecibo should create a new form with FormGroup', () => {
        const formGroup = service.createAplicacaoReciboFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            totalFactura: expect.any(Object),
            totalPago: expect.any(Object),
            totalDiferenca: expect.any(Object),
            timestamp: expect.any(Object),
            itemFactura: expect.any(Object),
            factura: expect.any(Object),
            recibo: expect.any(Object),
          })
        );
      });
    });

    describe('getAplicacaoRecibo', () => {
      it('should return NewAplicacaoRecibo for default AplicacaoRecibo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAplicacaoReciboFormGroup(sampleWithNewData);

        const aplicacaoRecibo = service.getAplicacaoRecibo(formGroup) as any;

        expect(aplicacaoRecibo).toMatchObject(sampleWithNewData);
      });

      it('should return NewAplicacaoRecibo for empty AplicacaoRecibo initial value', () => {
        const formGroup = service.createAplicacaoReciboFormGroup();

        const aplicacaoRecibo = service.getAplicacaoRecibo(formGroup) as any;

        expect(aplicacaoRecibo).toMatchObject({});
      });

      it('should return IAplicacaoRecibo', () => {
        const formGroup = service.createAplicacaoReciboFormGroup(sampleWithRequiredData);

        const aplicacaoRecibo = service.getAplicacaoRecibo(formGroup) as any;

        expect(aplicacaoRecibo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAplicacaoRecibo should not enable id FormControl', () => {
        const formGroup = service.createAplicacaoReciboFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAplicacaoRecibo should disable id FormControl', () => {
        const formGroup = service.createAplicacaoReciboFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
