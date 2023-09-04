import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../recibo.test-samples';

import { ReciboFormService } from './recibo-form.service';

describe('Recibo Form Service', () => {
  let service: ReciboFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReciboFormService);
  });

  describe('Service methods', () => {
    describe('createReciboFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReciboFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            vencimento: expect.any(Object),
            numero: expect.any(Object),
            totalSemImposto: expect.any(Object),
            totalComImposto: expect.any(Object),
            totalDescontoComercial: expect.any(Object),
            totalDescontoFinanceiro: expect.any(Object),
            totalIVA: expect.any(Object),
            totalRetencao: expect.any(Object),
            totalJuro: expect.any(Object),
            cambio: expect.any(Object),
            totalMoedaEstrangeira: expect.any(Object),
            totalPagar: expect.any(Object),
            totalPago: expect.any(Object),
            totalFalta: expect.any(Object),
            totalTroco: expect.any(Object),
            isNovo: expect.any(Object),
            timestamp: expect.any(Object),
            descricao: expect.any(Object),
            debito: expect.any(Object),
            credito: expect.any(Object),
            isFiscalizado: expect.any(Object),
            signText: expect.any(Object),
            hash: expect.any(Object),
            hashShort: expect.any(Object),
            hashControl: expect.any(Object),
            keyVersion: expect.any(Object),
            estado: expect.any(Object),
            origem: expect.any(Object),
            utilizador: expect.any(Object),
            matricula: expect.any(Object),
            documentoComercial: expect.any(Object),
            transacao: expect.any(Object),
          })
        );
      });

      it('passing IRecibo should create a new form with FormGroup', () => {
        const formGroup = service.createReciboFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            vencimento: expect.any(Object),
            numero: expect.any(Object),
            totalSemImposto: expect.any(Object),
            totalComImposto: expect.any(Object),
            totalDescontoComercial: expect.any(Object),
            totalDescontoFinanceiro: expect.any(Object),
            totalIVA: expect.any(Object),
            totalRetencao: expect.any(Object),
            totalJuro: expect.any(Object),
            cambio: expect.any(Object),
            totalMoedaEstrangeira: expect.any(Object),
            totalPagar: expect.any(Object),
            totalPago: expect.any(Object),
            totalFalta: expect.any(Object),
            totalTroco: expect.any(Object),
            isNovo: expect.any(Object),
            timestamp: expect.any(Object),
            descricao: expect.any(Object),
            debito: expect.any(Object),
            credito: expect.any(Object),
            isFiscalizado: expect.any(Object),
            signText: expect.any(Object),
            hash: expect.any(Object),
            hashShort: expect.any(Object),
            hashControl: expect.any(Object),
            keyVersion: expect.any(Object),
            estado: expect.any(Object),
            origem: expect.any(Object),
            utilizador: expect.any(Object),
            matricula: expect.any(Object),
            documentoComercial: expect.any(Object),
            transacao: expect.any(Object),
          })
        );
      });
    });

    describe('getRecibo', () => {
      it('should return NewRecibo for default Recibo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createReciboFormGroup(sampleWithNewData);

        const recibo = service.getRecibo(formGroup) as any;

        expect(recibo).toMatchObject(sampleWithNewData);
      });

      it('should return NewRecibo for empty Recibo initial value', () => {
        const formGroup = service.createReciboFormGroup();

        const recibo = service.getRecibo(formGroup) as any;

        expect(recibo).toMatchObject({});
      });

      it('should return IRecibo', () => {
        const formGroup = service.createReciboFormGroup(sampleWithRequiredData);

        const recibo = service.getRecibo(formGroup) as any;

        expect(recibo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRecibo should not enable id FormControl', () => {
        const formGroup = service.createReciboFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRecibo should disable id FormControl', () => {
        const formGroup = service.createReciboFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
