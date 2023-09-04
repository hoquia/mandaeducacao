import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../transferencia-turma.test-samples';

import { TransferenciaTurmaFormService } from './transferencia-turma-form.service';

describe('TransferenciaTurma Form Service', () => {
  let service: TransferenciaTurmaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransferenciaTurmaFormService);
  });

  describe('Service methods', () => {
    describe('createTransferenciaTurmaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTransferenciaTurmaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            timestamp: expect.any(Object),
            de: expect.any(Object),
            para: expect.any(Object),
            utilizador: expect.any(Object),
            motivoTransferencia: expect.any(Object),
            matricula: expect.any(Object),
          })
        );
      });

      it('passing ITransferenciaTurma should create a new form with FormGroup', () => {
        const formGroup = service.createTransferenciaTurmaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            timestamp: expect.any(Object),
            de: expect.any(Object),
            para: expect.any(Object),
            utilizador: expect.any(Object),
            motivoTransferencia: expect.any(Object),
            matricula: expect.any(Object),
          })
        );
      });
    });

    describe('getTransferenciaTurma', () => {
      it('should return NewTransferenciaTurma for default TransferenciaTurma initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTransferenciaTurmaFormGroup(sampleWithNewData);

        const transferenciaTurma = service.getTransferenciaTurma(formGroup) as any;

        expect(transferenciaTurma).toMatchObject(sampleWithNewData);
      });

      it('should return NewTransferenciaTurma for empty TransferenciaTurma initial value', () => {
        const formGroup = service.createTransferenciaTurmaFormGroup();

        const transferenciaTurma = service.getTransferenciaTurma(formGroup) as any;

        expect(transferenciaTurma).toMatchObject({});
      });

      it('should return ITransferenciaTurma', () => {
        const formGroup = service.createTransferenciaTurmaFormGroup(sampleWithRequiredData);

        const transferenciaTurma = service.getTransferenciaTurma(formGroup) as any;

        expect(transferenciaTurma).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITransferenciaTurma should not enable id FormControl', () => {
        const formGroup = service.createTransferenciaTurmaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTransferenciaTurma should disable id FormControl', () => {
        const formGroup = service.createTransferenciaTurmaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
