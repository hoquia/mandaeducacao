import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plano-multa.test-samples';

import { PlanoMultaFormService } from './plano-multa-form.service';

describe('PlanoMulta Form Service', () => {
  let service: PlanoMultaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanoMultaFormService);
  });

  describe('Service methods', () => {
    describe('createPlanoMultaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanoMultaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            diaAplicacaoMulta: expect.any(Object),
            metodoAplicacaoMulta: expect.any(Object),
            taxaMulta: expect.any(Object),
            isTaxaMultaPercentual: expect.any(Object),
            diaAplicacaoJuro: expect.any(Object),
            metodoAplicacaoJuro: expect.any(Object),
            taxaJuro: expect.any(Object),
            isTaxaJuroPercentual: expect.any(Object),
            aumentarJuroEmDias: expect.any(Object),
            isAtivo: expect.any(Object),
            utilizador: expect.any(Object),
          })
        );
      });

      it('passing IPlanoMulta should create a new form with FormGroup', () => {
        const formGroup = service.createPlanoMultaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            diaAplicacaoMulta: expect.any(Object),
            metodoAplicacaoMulta: expect.any(Object),
            taxaMulta: expect.any(Object),
            isTaxaMultaPercentual: expect.any(Object),
            diaAplicacaoJuro: expect.any(Object),
            metodoAplicacaoJuro: expect.any(Object),
            taxaJuro: expect.any(Object),
            isTaxaJuroPercentual: expect.any(Object),
            aumentarJuroEmDias: expect.any(Object),
            isAtivo: expect.any(Object),
            utilizador: expect.any(Object),
          })
        );
      });
    });

    describe('getPlanoMulta', () => {
      it('should return NewPlanoMulta for default PlanoMulta initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPlanoMultaFormGroup(sampleWithNewData);

        const planoMulta = service.getPlanoMulta(formGroup) as any;

        expect(planoMulta).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanoMulta for empty PlanoMulta initial value', () => {
        const formGroup = service.createPlanoMultaFormGroup();

        const planoMulta = service.getPlanoMulta(formGroup) as any;

        expect(planoMulta).toMatchObject({});
      });

      it('should return IPlanoMulta', () => {
        const formGroup = service.createPlanoMultaFormGroup(sampleWithRequiredData);

        const planoMulta = service.getPlanoMulta(formGroup) as any;

        expect(planoMulta).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanoMulta should not enable id FormControl', () => {
        const formGroup = service.createPlanoMultaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanoMulta should disable id FormControl', () => {
        const formGroup = service.createPlanoMultaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
