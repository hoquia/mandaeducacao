import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../longonkelo-historico.test-samples';

import { LongonkeloHistoricoFormService } from './longonkelo-historico-form.service';

describe('LongonkeloHistorico Form Service', () => {
  let service: LongonkeloHistoricoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LongonkeloHistoricoFormService);
  });

  describe('Service methods', () => {
    describe('createLongonkeloHistoricoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLongonkeloHistoricoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            operacao: expect.any(Object),
            entidadeNome: expect.any(Object),
            entidadeCodigo: expect.any(Object),
            payload: expect.any(Object),
            host: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
          })
        );
      });

      it('passing ILongonkeloHistorico should create a new form with FormGroup', () => {
        const formGroup = service.createLongonkeloHistoricoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            operacao: expect.any(Object),
            entidadeNome: expect.any(Object),
            entidadeCodigo: expect.any(Object),
            payload: expect.any(Object),
            host: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
          })
        );
      });
    });

    describe('getLongonkeloHistorico', () => {
      it('should return NewLongonkeloHistorico for default LongonkeloHistorico initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLongonkeloHistoricoFormGroup(sampleWithNewData);

        const longonkeloHistorico = service.getLongonkeloHistorico(formGroup) as any;

        expect(longonkeloHistorico).toMatchObject(sampleWithNewData);
      });

      it('should return NewLongonkeloHistorico for empty LongonkeloHistorico initial value', () => {
        const formGroup = service.createLongonkeloHistoricoFormGroup();

        const longonkeloHistorico = service.getLongonkeloHistorico(formGroup) as any;

        expect(longonkeloHistorico).toMatchObject({});
      });

      it('should return ILongonkeloHistorico', () => {
        const formGroup = service.createLongonkeloHistoricoFormGroup(sampleWithRequiredData);

        const longonkeloHistorico = service.getLongonkeloHistorico(formGroup) as any;

        expect(longonkeloHistorico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILongonkeloHistorico should not enable id FormControl', () => {
        const formGroup = service.createLongonkeloHistoricoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLongonkeloHistorico should disable id FormControl', () => {
        const formGroup = service.createLongonkeloHistoricoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
