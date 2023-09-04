import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../instituicao-ensino.test-samples';

import { InstituicaoEnsinoFormService } from './instituicao-ensino-form.service';

describe('InstituicaoEnsino Form Service', () => {
  let service: InstituicaoEnsinoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InstituicaoEnsinoFormService);
  });

  describe('Service methods', () => {
    describe('createInstituicaoEnsinoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInstituicaoEnsinoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            logotipo: expect.any(Object),
            unidadeOrganica: expect.any(Object),
            nomeFiscal: expect.any(Object),
            numero: expect.any(Object),
            nif: expect.any(Object),
            cae: expect.any(Object),
            niss: expect.any(Object),
            fundador: expect.any(Object),
            fundacao: expect.any(Object),
            dimensao: expect.any(Object),
            slogam: expect.any(Object),
            telefone: expect.any(Object),
            telemovel: expect.any(Object),
            email: expect.any(Object),
            website: expect.any(Object),
            codigoPostal: expect.any(Object),
            enderecoDetalhado: expect.any(Object),
            latitude: expect.any(Object),
            longitude: expect.any(Object),
            descricao: expect.any(Object),
            isComparticipada: expect.any(Object),
            termosCompromissos: expect.any(Object),
            categoriaInstituicao: expect.any(Object),
            unidadePagadora: expect.any(Object),
            tipoVinculo: expect.any(Object),
            tipoInstalacao: expect.any(Object),
            sede: expect.any(Object),
          })
        );
      });

      it('passing IInstituicaoEnsino should create a new form with FormGroup', () => {
        const formGroup = service.createInstituicaoEnsinoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            logotipo: expect.any(Object),
            unidadeOrganica: expect.any(Object),
            nomeFiscal: expect.any(Object),
            numero: expect.any(Object),
            nif: expect.any(Object),
            cae: expect.any(Object),
            niss: expect.any(Object),
            fundador: expect.any(Object),
            fundacao: expect.any(Object),
            dimensao: expect.any(Object),
            slogam: expect.any(Object),
            telefone: expect.any(Object),
            telemovel: expect.any(Object),
            email: expect.any(Object),
            website: expect.any(Object),
            codigoPostal: expect.any(Object),
            enderecoDetalhado: expect.any(Object),
            latitude: expect.any(Object),
            longitude: expect.any(Object),
            descricao: expect.any(Object),
            isComparticipada: expect.any(Object),
            termosCompromissos: expect.any(Object),
            categoriaInstituicao: expect.any(Object),
            unidadePagadora: expect.any(Object),
            tipoVinculo: expect.any(Object),
            tipoInstalacao: expect.any(Object),
            sede: expect.any(Object),
          })
        );
      });
    });

    describe('getInstituicaoEnsino', () => {
      it('should return NewInstituicaoEnsino for default InstituicaoEnsino initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInstituicaoEnsinoFormGroup(sampleWithNewData);

        const instituicaoEnsino = service.getInstituicaoEnsino(formGroup) as any;

        expect(instituicaoEnsino).toMatchObject(sampleWithNewData);
      });

      it('should return NewInstituicaoEnsino for empty InstituicaoEnsino initial value', () => {
        const formGroup = service.createInstituicaoEnsinoFormGroup();

        const instituicaoEnsino = service.getInstituicaoEnsino(formGroup) as any;

        expect(instituicaoEnsino).toMatchObject({});
      });

      it('should return IInstituicaoEnsino', () => {
        const formGroup = service.createInstituicaoEnsinoFormGroup(sampleWithRequiredData);

        const instituicaoEnsino = service.getInstituicaoEnsino(formGroup) as any;

        expect(instituicaoEnsino).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInstituicaoEnsino should not enable id FormControl', () => {
        const formGroup = service.createInstituicaoEnsinoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInstituicaoEnsino should disable id FormControl', () => {
        const formGroup = service.createInstituicaoEnsinoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
