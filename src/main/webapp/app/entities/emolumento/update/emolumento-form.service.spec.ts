import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../emolumento.test-samples';

import { EmolumentoFormService } from './emolumento-form.service';

describe('Emolumento Form Service', () => {
  let service: EmolumentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmolumentoFormService);
  });

  describe('Service methods', () => {
    describe('createEmolumentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmolumentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagem: expect.any(Object),
            numero: expect.any(Object),
            nome: expect.any(Object),
            preco: expect.any(Object),
            quantidade: expect.any(Object),
            periodo: expect.any(Object),
            inicioPeriodo: expect.any(Object),
            fimPeriodo: expect.any(Object),
            isObrigatorioMatricula: expect.any(Object),
            isObrigatorioConfirmacao: expect.any(Object),
            categoria: expect.any(Object),
            imposto: expect.any(Object),
            referencia: expect.any(Object),
            planoMulta: expect.any(Object),
          })
        );
      });

      it('passing IEmolumento should create a new form with FormGroup', () => {
        const formGroup = service.createEmolumentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagem: expect.any(Object),
            numero: expect.any(Object),
            nome: expect.any(Object),
            preco: expect.any(Object),
            quantidade: expect.any(Object),
            periodo: expect.any(Object),
            inicioPeriodo: expect.any(Object),
            fimPeriodo: expect.any(Object),
            isObrigatorioMatricula: expect.any(Object),
            isObrigatorioConfirmacao: expect.any(Object),
            categoria: expect.any(Object),
            imposto: expect.any(Object),
            referencia: expect.any(Object),
            planoMulta: expect.any(Object),
          })
        );
      });
    });

    describe('getEmolumento', () => {
      it('should return NewEmolumento for default Emolumento initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEmolumentoFormGroup(sampleWithNewData);

        const emolumento = service.getEmolumento(formGroup) as any;

        expect(emolumento).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmolumento for empty Emolumento initial value', () => {
        const formGroup = service.createEmolumentoFormGroup();

        const emolumento = service.getEmolumento(formGroup) as any;

        expect(emolumento).toMatchObject({});
      });

      it('should return IEmolumento', () => {
        const formGroup = service.createEmolumentoFormGroup(sampleWithRequiredData);

        const emolumento = service.getEmolumento(formGroup) as any;

        expect(emolumento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmolumento should not enable id FormControl', () => {
        const formGroup = service.createEmolumentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmolumento should disable id FormControl', () => {
        const formGroup = service.createEmolumentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
