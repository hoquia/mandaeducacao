import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nivel-ensino.test-samples';

import { NivelEnsinoFormService } from './nivel-ensino-form.service';

describe('NivelEnsino Form Service', () => {
  let service: NivelEnsinoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NivelEnsinoFormService);
  });

  describe('Service methods', () => {
    describe('createNivelEnsinoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNivelEnsinoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            idadeMinima: expect.any(Object),
            idadeMaxima: expect.any(Object),
            duracao: expect.any(Object),
            unidadeDuracao: expect.any(Object),
            classeInicial: expect.any(Object),
            classeFinal: expect.any(Object),
            classeExame: expect.any(Object),
            totalDisciplina: expect.any(Object),
            responsavelTurno: expect.any(Object),
            responsavelAreaFormacao: expect.any(Object),
            responsavelCurso: expect.any(Object),
            responsavelDisciplina: expect.any(Object),
            responsavelTurma: expect.any(Object),
            responsavelGeral: expect.any(Object),
            responsavelPedagogico: expect.any(Object),
            responsavelAdministrativo: expect.any(Object),
            responsavelSecretariaGeral: expect.any(Object),
            responsavelSecretariaPedagogico: expect.any(Object),
            descricaoDocente: expect.any(Object),
            descricaoDiscente: expect.any(Object),
            referencia: expect.any(Object),
            anoLectivos: expect.any(Object),
            classes: expect.any(Object),
          })
        );
      });

      it('passing INivelEnsino should create a new form with FormGroup', () => {
        const formGroup = service.createNivelEnsinoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            idadeMinima: expect.any(Object),
            idadeMaxima: expect.any(Object),
            duracao: expect.any(Object),
            unidadeDuracao: expect.any(Object),
            classeInicial: expect.any(Object),
            classeFinal: expect.any(Object),
            classeExame: expect.any(Object),
            totalDisciplina: expect.any(Object),
            responsavelTurno: expect.any(Object),
            responsavelAreaFormacao: expect.any(Object),
            responsavelCurso: expect.any(Object),
            responsavelDisciplina: expect.any(Object),
            responsavelTurma: expect.any(Object),
            responsavelGeral: expect.any(Object),
            responsavelPedagogico: expect.any(Object),
            responsavelAdministrativo: expect.any(Object),
            responsavelSecretariaGeral: expect.any(Object),
            responsavelSecretariaPedagogico: expect.any(Object),
            descricaoDocente: expect.any(Object),
            descricaoDiscente: expect.any(Object),
            referencia: expect.any(Object),
            anoLectivos: expect.any(Object),
            classes: expect.any(Object),
          })
        );
      });
    });

    describe('getNivelEnsino', () => {
      it('should return NewNivelEnsino for default NivelEnsino initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNivelEnsinoFormGroup(sampleWithNewData);

        const nivelEnsino = service.getNivelEnsino(formGroup) as any;

        expect(nivelEnsino).toMatchObject(sampleWithNewData);
      });

      it('should return NewNivelEnsino for empty NivelEnsino initial value', () => {
        const formGroup = service.createNivelEnsinoFormGroup();

        const nivelEnsino = service.getNivelEnsino(formGroup) as any;

        expect(nivelEnsino).toMatchObject({});
      });

      it('should return INivelEnsino', () => {
        const formGroup = service.createNivelEnsinoFormGroup(sampleWithRequiredData);

        const nivelEnsino = service.getNivelEnsino(formGroup) as any;

        expect(nivelEnsino).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INivelEnsino should not enable id FormControl', () => {
        const formGroup = service.createNivelEnsinoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNivelEnsino should disable id FormControl', () => {
        const formGroup = service.createNivelEnsinoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
