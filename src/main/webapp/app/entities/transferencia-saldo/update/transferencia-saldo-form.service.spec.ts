import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../transferencia-saldo.test-samples';

import { TransferenciaSaldoFormService } from './transferencia-saldo-form.service';

describe('TransferenciaSaldo Form Service', () => {
  let service: TransferenciaSaldoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransferenciaSaldoFormService);
  });

  describe('Service methods', () => {
    describe('createTransferenciaSaldoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTransferenciaSaldoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            montante: expect.any(Object),
            isMesmaConta: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            de: expect.any(Object),
            para: expect.any(Object),
            utilizador: expect.any(Object),
            motivoTransferencia: expect.any(Object),
            transacoes: expect.any(Object),
          })
        );
      });

      it('passing ITransferenciaSaldo should create a new form with FormGroup', () => {
        const formGroup = service.createTransferenciaSaldoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            montante: expect.any(Object),
            isMesmaConta: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            de: expect.any(Object),
            para: expect.any(Object),
            utilizador: expect.any(Object),
            motivoTransferencia: expect.any(Object),
            transacoes: expect.any(Object),
          })
        );
      });
    });

    describe('getTransferenciaSaldo', () => {
      it('should return NewTransferenciaSaldo for default TransferenciaSaldo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTransferenciaSaldoFormGroup(sampleWithNewData);

        const transferenciaSaldo = service.getTransferenciaSaldo(formGroup) as any;

        expect(transferenciaSaldo).toMatchObject(sampleWithNewData);
      });

      it('should return NewTransferenciaSaldo for empty TransferenciaSaldo initial value', () => {
        const formGroup = service.createTransferenciaSaldoFormGroup();

        const transferenciaSaldo = service.getTransferenciaSaldo(formGroup) as any;

        expect(transferenciaSaldo).toMatchObject({});
      });

      it('should return ITransferenciaSaldo', () => {
        const formGroup = service.createTransferenciaSaldoFormGroup(sampleWithRequiredData);

        const transferenciaSaldo = service.getTransferenciaSaldo(formGroup) as any;

        expect(transferenciaSaldo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITransferenciaSaldo should not enable id FormControl', () => {
        const formGroup = service.createTransferenciaSaldoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTransferenciaSaldo should disable id FormControl', () => {
        const formGroup = service.createTransferenciaSaldoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
