import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../historico-saude.test-samples';

import { HistoricoSaudeFormService } from './historico-saude-form.service';

describe('HistoricoSaude Form Service', () => {
  let service: HistoricoSaudeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HistoricoSaudeFormService);
  });

  describe('Service methods', () => {
    describe('createHistoricoSaudeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHistoricoSaudeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            inicio: expect.any(Object),
            fim: expect.any(Object),
            situacaoPrescricao: expect.any(Object),
            timestamp: expect.any(Object),
            hash: expect.any(Object),
            utilizador: expect.any(Object),
            discente: expect.any(Object),
          })
        );
      });

      it('passing IHistoricoSaude should create a new form with FormGroup', () => {
        const formGroup = service.createHistoricoSaudeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            inicio: expect.any(Object),
            fim: expect.any(Object),
            situacaoPrescricao: expect.any(Object),
            timestamp: expect.any(Object),
            hash: expect.any(Object),
            utilizador: expect.any(Object),
            discente: expect.any(Object),
          })
        );
      });
    });

    describe('getHistoricoSaude', () => {
      it('should return NewHistoricoSaude for default HistoricoSaude initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createHistoricoSaudeFormGroup(sampleWithNewData);

        const historicoSaude = service.getHistoricoSaude(formGroup) as any;

        expect(historicoSaude).toMatchObject(sampleWithNewData);
      });

      it('should return NewHistoricoSaude for empty HistoricoSaude initial value', () => {
        const formGroup = service.createHistoricoSaudeFormGroup();

        const historicoSaude = service.getHistoricoSaude(formGroup) as any;

        expect(historicoSaude).toMatchObject({});
      });

      it('should return IHistoricoSaude', () => {
        const formGroup = service.createHistoricoSaudeFormGroup(sampleWithRequiredData);

        const historicoSaude = service.getHistoricoSaude(formGroup) as any;

        expect(historicoSaude).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHistoricoSaude should not enable id FormControl', () => {
        const formGroup = service.createHistoricoSaudeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHistoricoSaude should disable id FormControl', () => {
        const formGroup = service.createHistoricoSaudeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
