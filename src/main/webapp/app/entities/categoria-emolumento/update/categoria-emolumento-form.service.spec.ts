import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../categoria-emolumento.test-samples';

import { CategoriaEmolumentoFormService } from './categoria-emolumento-form.service';

describe('CategoriaEmolumento Form Service', () => {
  let service: CategoriaEmolumentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriaEmolumentoFormService);
  });

  describe('Service methods', () => {
    describe('createCategoriaEmolumentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategoriaEmolumentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            isServico: expect.any(Object),
            cor: expect.any(Object),
            descricao: expect.any(Object),
            isIsentoMulta: expect.any(Object),
            isIsentoJuro: expect.any(Object),
            planoMulta: expect.any(Object),
            planosDescontos: expect.any(Object),
          })
        );
      });

      it('passing ICategoriaEmolumento should create a new form with FormGroup', () => {
        const formGroup = service.createCategoriaEmolumentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            isServico: expect.any(Object),
            cor: expect.any(Object),
            descricao: expect.any(Object),
            isIsentoMulta: expect.any(Object),
            isIsentoJuro: expect.any(Object),
            planoMulta: expect.any(Object),
            planosDescontos: expect.any(Object),
          })
        );
      });
    });

    describe('getCategoriaEmolumento', () => {
      it('should return NewCategoriaEmolumento for default CategoriaEmolumento initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCategoriaEmolumentoFormGroup(sampleWithNewData);

        const categoriaEmolumento = service.getCategoriaEmolumento(formGroup) as any;

        expect(categoriaEmolumento).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategoriaEmolumento for empty CategoriaEmolumento initial value', () => {
        const formGroup = service.createCategoriaEmolumentoFormGroup();

        const categoriaEmolumento = service.getCategoriaEmolumento(formGroup) as any;

        expect(categoriaEmolumento).toMatchObject({});
      });

      it('should return ICategoriaEmolumento', () => {
        const formGroup = service.createCategoriaEmolumentoFormGroup(sampleWithRequiredData);

        const categoriaEmolumento = service.getCategoriaEmolumento(formGroup) as any;

        expect(categoriaEmolumento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategoriaEmolumento should not enable id FormControl', () => {
        const formGroup = service.createCategoriaEmolumentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategoriaEmolumento should disable id FormControl', () => {
        const formGroup = service.createCategoriaEmolumentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
