import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../imposto.test-samples';

import { ImpostoFormService } from './imposto-form.service';

describe('Imposto Form Service', () => {
  let service: ImpostoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImpostoFormService);
  });

  describe('Service methods', () => {
    describe('createImpostoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createImpostoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            pais: expect.any(Object),
            taxa: expect.any(Object),
            isRetencao: expect.any(Object),
            motivoDescricao: expect.any(Object),
            motivoCodigo: expect.any(Object),
            tipoImposto: expect.any(Object),
            codigoImposto: expect.any(Object),
            motivoIsencaoCodigo: expect.any(Object),
            motivoIsencaoDescricao: expect.any(Object),
          })
        );
      });

      it('passing IImposto should create a new form with FormGroup', () => {
        const formGroup = service.createImpostoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            pais: expect.any(Object),
            taxa: expect.any(Object),
            isRetencao: expect.any(Object),
            motivoDescricao: expect.any(Object),
            motivoCodigo: expect.any(Object),
            tipoImposto: expect.any(Object),
            codigoImposto: expect.any(Object),
            motivoIsencaoCodigo: expect.any(Object),
            motivoIsencaoDescricao: expect.any(Object),
          })
        );
      });
    });

    describe('getImposto', () => {
      it('should return NewImposto for default Imposto initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createImpostoFormGroup(sampleWithNewData);

        const imposto = service.getImposto(formGroup) as any;

        expect(imposto).toMatchObject(sampleWithNewData);
      });

      it('should return NewImposto for empty Imposto initial value', () => {
        const formGroup = service.createImpostoFormGroup();

        const imposto = service.getImposto(formGroup) as any;

        expect(imposto).toMatchObject({});
      });

      it('should return IImposto', () => {
        const formGroup = service.createImpostoFormGroup(sampleWithRequiredData);

        const imposto = service.getImposto(formGroup) as any;

        expect(imposto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IImposto should not enable id FormControl', () => {
        const formGroup = service.createImpostoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewImposto should disable id FormControl', () => {
        const formGroup = service.createImpostoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
