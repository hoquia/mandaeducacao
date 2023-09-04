import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../endereco-discente.test-samples';

import { EnderecoDiscenteFormService } from './endereco-discente-form.service';

describe('EnderecoDiscente Form Service', () => {
  let service: EnderecoDiscenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnderecoDiscenteFormService);
  });

  describe('Service methods', () => {
    describe('createEnderecoDiscenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEnderecoDiscenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            bairro: expect.any(Object),
            rua: expect.any(Object),
            numeroCasa: expect.any(Object),
            codigoPostal: expect.any(Object),
            latitude: expect.any(Object),
            longitude: expect.any(Object),
            pais: expect.any(Object),
            provincia: expect.any(Object),
            municipio: expect.any(Object),
            discente: expect.any(Object),
          })
        );
      });

      it('passing IEnderecoDiscente should create a new form with FormGroup', () => {
        const formGroup = service.createEnderecoDiscenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            bairro: expect.any(Object),
            rua: expect.any(Object),
            numeroCasa: expect.any(Object),
            codigoPostal: expect.any(Object),
            latitude: expect.any(Object),
            longitude: expect.any(Object),
            pais: expect.any(Object),
            provincia: expect.any(Object),
            municipio: expect.any(Object),
            discente: expect.any(Object),
          })
        );
      });
    });

    describe('getEnderecoDiscente', () => {
      it('should return NewEnderecoDiscente for default EnderecoDiscente initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEnderecoDiscenteFormGroup(sampleWithNewData);

        const enderecoDiscente = service.getEnderecoDiscente(formGroup) as any;

        expect(enderecoDiscente).toMatchObject(sampleWithNewData);
      });

      it('should return NewEnderecoDiscente for empty EnderecoDiscente initial value', () => {
        const formGroup = service.createEnderecoDiscenteFormGroup();

        const enderecoDiscente = service.getEnderecoDiscente(formGroup) as any;

        expect(enderecoDiscente).toMatchObject({});
      });

      it('should return IEnderecoDiscente', () => {
        const formGroup = service.createEnderecoDiscenteFormGroup(sampleWithRequiredData);

        const enderecoDiscente = service.getEnderecoDiscente(formGroup) as any;

        expect(enderecoDiscente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEnderecoDiscente should not enable id FormControl', () => {
        const formGroup = service.createEnderecoDiscenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEnderecoDiscente should disable id FormControl', () => {
        const formGroup = service.createEnderecoDiscenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
