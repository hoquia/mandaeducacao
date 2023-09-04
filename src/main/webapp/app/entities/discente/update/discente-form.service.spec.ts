import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../discente.test-samples';

import { DiscenteFormService } from './discente-form.service';

describe('Discente Form Service', () => {
  let service: DiscenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiscenteFormService);
  });

  describe('Service methods', () => {
    describe('createDiscenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDiscenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fotografia: expect.any(Object),
            nome: expect.any(Object),
            nascimento: expect.any(Object),
            documentoNumero: expect.any(Object),
            documentoEmissao: expect.any(Object),
            documentoValidade: expect.any(Object),
            nif: expect.any(Object),
            sexo: expect.any(Object),
            pai: expect.any(Object),
            mae: expect.any(Object),
            telefonePrincipal: expect.any(Object),
            telefoneParente: expect.any(Object),
            email: expect.any(Object),
            isEncarregadoEducacao: expect.any(Object),
            isTrabalhador: expect.any(Object),
            isFilhoAntigoConbatente: expect.any(Object),
            isAtestadoPobreza: expect.any(Object),
            nomeMedico: expect.any(Object),
            telefoneMedico: expect.any(Object),
            instituicaoParticularSaude: expect.any(Object),
            altura: expect.any(Object),
            peso: expect.any(Object),
            isAsmatico: expect.any(Object),
            isAlergico: expect.any(Object),
            isPraticaEducacaoFisica: expect.any(Object),
            isAutorizadoMedicacao: expect.any(Object),
            cuidadosEspeciaisSaude: expect.any(Object),
            numeroProcesso: expect.any(Object),
            dataIngresso: expect.any(Object),
            hash: expect.any(Object),
            observacao: expect.any(Object),
            nacionalidade: expect.any(Object),
            naturalidade: expect.any(Object),
            tipoDocumento: expect.any(Object),
            profissao: expect.any(Object),
            grupoSanguinio: expect.any(Object),
            necessidadeEspecial: expect.any(Object),
            encarregadoEducacao: expect.any(Object),
          })
        );
      });

      it('passing IDiscente should create a new form with FormGroup', () => {
        const formGroup = service.createDiscenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fotografia: expect.any(Object),
            nome: expect.any(Object),
            nascimento: expect.any(Object),
            documentoNumero: expect.any(Object),
            documentoEmissao: expect.any(Object),
            documentoValidade: expect.any(Object),
            nif: expect.any(Object),
            sexo: expect.any(Object),
            pai: expect.any(Object),
            mae: expect.any(Object),
            telefonePrincipal: expect.any(Object),
            telefoneParente: expect.any(Object),
            email: expect.any(Object),
            isEncarregadoEducacao: expect.any(Object),
            isTrabalhador: expect.any(Object),
            isFilhoAntigoConbatente: expect.any(Object),
            isAtestadoPobreza: expect.any(Object),
            nomeMedico: expect.any(Object),
            telefoneMedico: expect.any(Object),
            instituicaoParticularSaude: expect.any(Object),
            altura: expect.any(Object),
            peso: expect.any(Object),
            isAsmatico: expect.any(Object),
            isAlergico: expect.any(Object),
            isPraticaEducacaoFisica: expect.any(Object),
            isAutorizadoMedicacao: expect.any(Object),
            cuidadosEspeciaisSaude: expect.any(Object),
            numeroProcesso: expect.any(Object),
            dataIngresso: expect.any(Object),
            hash: expect.any(Object),
            observacao: expect.any(Object),
            nacionalidade: expect.any(Object),
            naturalidade: expect.any(Object),
            tipoDocumento: expect.any(Object),
            profissao: expect.any(Object),
            grupoSanguinio: expect.any(Object),
            necessidadeEspecial: expect.any(Object),
            encarregadoEducacao: expect.any(Object),
          })
        );
      });
    });

    describe('getDiscente', () => {
      it('should return NewDiscente for default Discente initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDiscenteFormGroup(sampleWithNewData);

        const discente = service.getDiscente(formGroup) as any;

        expect(discente).toMatchObject(sampleWithNewData);
      });

      it('should return NewDiscente for empty Discente initial value', () => {
        const formGroup = service.createDiscenteFormGroup();

        const discente = service.getDiscente(formGroup) as any;

        expect(discente).toMatchObject({});
      });

      it('should return IDiscente', () => {
        const formGroup = service.createDiscenteFormGroup(sampleWithRequiredData);

        const discente = service.getDiscente(formGroup) as any;

        expect(discente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDiscente should not enable id FormControl', () => {
        const formGroup = service.createDiscenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDiscente should disable id FormControl', () => {
        const formGroup = service.createDiscenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
