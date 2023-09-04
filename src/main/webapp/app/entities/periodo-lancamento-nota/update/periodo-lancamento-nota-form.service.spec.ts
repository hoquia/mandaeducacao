import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../periodo-lancamento-nota.test-samples';

import { PeriodoLancamentoNotaFormService } from './periodo-lancamento-nota-form.service';

describe('PeriodoLancamentoNota Form Service', () => {
  let service: PeriodoLancamentoNotaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PeriodoLancamentoNotaFormService);
  });

  describe('Service methods', () => {
    describe('createPeriodoLancamentoNotaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPeriodoLancamentoNotaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipoAvaliacao: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            classes: expect.any(Object),
          })
        );
      });

      it('passing IPeriodoLancamentoNota should create a new form with FormGroup', () => {
        const formGroup = service.createPeriodoLancamentoNotaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipoAvaliacao: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            classes: expect.any(Object),
          })
        );
      });
    });

    describe('getPeriodoLancamentoNota', () => {
      it('should return NewPeriodoLancamentoNota for default PeriodoLancamentoNota initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPeriodoLancamentoNotaFormGroup(sampleWithNewData);

        const periodoLancamentoNota = service.getPeriodoLancamentoNota(formGroup) as any;

        expect(periodoLancamentoNota).toMatchObject(sampleWithNewData);
      });

      it('should return NewPeriodoLancamentoNota for empty PeriodoLancamentoNota initial value', () => {
        const formGroup = service.createPeriodoLancamentoNotaFormGroup();

        const periodoLancamentoNota = service.getPeriodoLancamentoNota(formGroup) as any;

        expect(periodoLancamentoNota).toMatchObject({});
      });

      it('should return IPeriodoLancamentoNota', () => {
        const formGroup = service.createPeriodoLancamentoNotaFormGroup(sampleWithRequiredData);

        const periodoLancamentoNota = service.getPeriodoLancamentoNota(formGroup) as any;

        expect(periodoLancamentoNota).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPeriodoLancamentoNota should not enable id FormControl', () => {
        const formGroup = service.createPeriodoLancamentoNotaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPeriodoLancamentoNota should disable id FormControl', () => {
        const formGroup = service.createPeriodoLancamentoNotaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
