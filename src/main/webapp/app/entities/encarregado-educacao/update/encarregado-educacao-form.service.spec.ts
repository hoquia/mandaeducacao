import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../encarregado-educacao.test-samples';

import { EncarregadoEducacaoFormService } from './encarregado-educacao-form.service';

describe('EncarregadoEducacao Form Service', () => {
  let service: EncarregadoEducacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EncarregadoEducacaoFormService);
  });

  describe('Service methods', () => {
    describe('createEncarregadoEducacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEncarregadoEducacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fotografia: expect.any(Object),
            nome: expect.any(Object),
            nascimento: expect.any(Object),
            nif: expect.any(Object),
            sexo: expect.any(Object),
            documentoNumero: expect.any(Object),
            telefonePrincipal: expect.any(Object),
            telefoneAlternativo: expect.any(Object),
            email: expect.any(Object),
            residencia: expect.any(Object),
            enderecoTrabalho: expect.any(Object),
            rendaMensal: expect.any(Object),
            empresaTrabalho: expect.any(Object),
            hash: expect.any(Object),
            grauParentesco: expect.any(Object),
            tipoDocumento: expect.any(Object),
            profissao: expect.any(Object),
          })
        );
      });

      it('passing IEncarregadoEducacao should create a new form with FormGroup', () => {
        const formGroup = service.createEncarregadoEducacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fotografia: expect.any(Object),
            nome: expect.any(Object),
            nascimento: expect.any(Object),
            nif: expect.any(Object),
            sexo: expect.any(Object),
            documentoNumero: expect.any(Object),
            telefonePrincipal: expect.any(Object),
            telefoneAlternativo: expect.any(Object),
            email: expect.any(Object),
            residencia: expect.any(Object),
            enderecoTrabalho: expect.any(Object),
            rendaMensal: expect.any(Object),
            empresaTrabalho: expect.any(Object),
            hash: expect.any(Object),
            grauParentesco: expect.any(Object),
            tipoDocumento: expect.any(Object),
            profissao: expect.any(Object),
          })
        );
      });
    });

    describe('getEncarregadoEducacao', () => {
      it('should return NewEncarregadoEducacao for default EncarregadoEducacao initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEncarregadoEducacaoFormGroup(sampleWithNewData);

        const encarregadoEducacao = service.getEncarregadoEducacao(formGroup) as any;

        expect(encarregadoEducacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewEncarregadoEducacao for empty EncarregadoEducacao initial value', () => {
        const formGroup = service.createEncarregadoEducacaoFormGroup();

        const encarregadoEducacao = service.getEncarregadoEducacao(formGroup) as any;

        expect(encarregadoEducacao).toMatchObject({});
      });

      it('should return IEncarregadoEducacao', () => {
        const formGroup = service.createEncarregadoEducacaoFormGroup(sampleWithRequiredData);

        const encarregadoEducacao = service.getEncarregadoEducacao(formGroup) as any;

        expect(encarregadoEducacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEncarregadoEducacao should not enable id FormControl', () => {
        const formGroup = service.createEncarregadoEducacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEncarregadoEducacao should disable id FormControl', () => {
        const formGroup = service.createEncarregadoEducacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
