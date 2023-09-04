import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../categoria-ocorrencia.test-samples';

import { CategoriaOcorrenciaFormService } from './categoria-ocorrencia-form.service';

describe('CategoriaOcorrencia Form Service', () => {
  let service: CategoriaOcorrenciaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriaOcorrenciaFormService);
  });

  describe('Service methods', () => {
    describe('createCategoriaOcorrenciaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategoriaOcorrenciaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            sansaoDisicplinar: expect.any(Object),
            isNotificaEncaregado: expect.any(Object),
            isSendEmail: expect.any(Object),
            isSendSms: expect.any(Object),
            isSendPush: expect.any(Object),
            descricao: expect.any(Object),
            observacao: expect.any(Object),
            encaminhar: expect.any(Object),
            referencia: expect.any(Object),
            medidaDisciplinar: expect.any(Object),
          })
        );
      });

      it('passing ICategoriaOcorrencia should create a new form with FormGroup', () => {
        const formGroup = service.createCategoriaOcorrenciaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            sansaoDisicplinar: expect.any(Object),
            isNotificaEncaregado: expect.any(Object),
            isSendEmail: expect.any(Object),
            isSendSms: expect.any(Object),
            isSendPush: expect.any(Object),
            descricao: expect.any(Object),
            observacao: expect.any(Object),
            encaminhar: expect.any(Object),
            referencia: expect.any(Object),
            medidaDisciplinar: expect.any(Object),
          })
        );
      });
    });

    describe('getCategoriaOcorrencia', () => {
      it('should return NewCategoriaOcorrencia for default CategoriaOcorrencia initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCategoriaOcorrenciaFormGroup(sampleWithNewData);

        const categoriaOcorrencia = service.getCategoriaOcorrencia(formGroup) as any;

        expect(categoriaOcorrencia).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategoriaOcorrencia for empty CategoriaOcorrencia initial value', () => {
        const formGroup = service.createCategoriaOcorrenciaFormGroup();

        const categoriaOcorrencia = service.getCategoriaOcorrencia(formGroup) as any;

        expect(categoriaOcorrencia).toMatchObject({});
      });

      it('should return ICategoriaOcorrencia', () => {
        const formGroup = service.createCategoriaOcorrenciaFormGroup(sampleWithRequiredData);

        const categoriaOcorrencia = service.getCategoriaOcorrencia(formGroup) as any;

        expect(categoriaOcorrencia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategoriaOcorrencia should not enable id FormControl', () => {
        const formGroup = service.createCategoriaOcorrenciaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategoriaOcorrencia should disable id FormControl', () => {
        const formGroup = service.createCategoriaOcorrenciaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
