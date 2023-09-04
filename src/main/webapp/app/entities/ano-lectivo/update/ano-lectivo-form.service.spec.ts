import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ano-lectivo.test-samples';

import { AnoLectivoFormService } from './ano-lectivo-form.service';

describe('AnoLectivo Form Service', () => {
  let service: AnoLectivoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnoLectivoFormService);
  });

  describe('Service methods', () => {
    describe('createAnoLectivoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnoLectivoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ano: expect.any(Object),
            inicio: expect.any(Object),
            fim: expect.any(Object),
            descricao: expect.any(Object),
            timestam: expect.any(Object),
            isActual: expect.any(Object),
            directorGeral: expect.any(Object),
            subDirectorPdagogico: expect.any(Object),
            subDirectorAdministrativo: expect.any(Object),
            responsavelSecretariaGeral: expect.any(Object),
            responsavelSecretariaPedagogico: expect.any(Object),
            utilizador: expect.any(Object),
            nivesEnsinos: expect.any(Object),
            turma: expect.any(Object),
            horario: expect.any(Object),
            planoAula: expect.any(Object),
            licao: expect.any(Object),
            processoSelectivoMatricula: expect.any(Object),
            ocorrencia: expect.any(Object),
            notasPeriodicaDisciplina: expect.any(Object),
            notasGeralDisciplina: expect.any(Object),
            dissertacaoFinalCurso: expect.any(Object),
            factura: expect.any(Object),
            recibo: expect.any(Object),
            responsavelTurno: expect.any(Object),
            responsavelAreaFormacao: expect.any(Object),
            responsavelCurso: expect.any(Object),
            responsavelDisciplina: expect.any(Object),
            responsavelTurma: expect.any(Object),
          })
        );
      });

      it('passing IAnoLectivo should create a new form with FormGroup', () => {
        const formGroup = service.createAnoLectivoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ano: expect.any(Object),
            inicio: expect.any(Object),
            fim: expect.any(Object),
            descricao: expect.any(Object),
            timestam: expect.any(Object),
            isActual: expect.any(Object),
            directorGeral: expect.any(Object),
            subDirectorPdagogico: expect.any(Object),
            subDirectorAdministrativo: expect.any(Object),
            responsavelSecretariaGeral: expect.any(Object),
            responsavelSecretariaPedagogico: expect.any(Object),
            utilizador: expect.any(Object),
            nivesEnsinos: expect.any(Object),
            turma: expect.any(Object),
            horario: expect.any(Object),
            planoAula: expect.any(Object),
            licao: expect.any(Object),
            processoSelectivoMatricula: expect.any(Object),
            ocorrencia: expect.any(Object),
            notasPeriodicaDisciplina: expect.any(Object),
            notasGeralDisciplina: expect.any(Object),
            dissertacaoFinalCurso: expect.any(Object),
            factura: expect.any(Object),
            recibo: expect.any(Object),
            responsavelTurno: expect.any(Object),
            responsavelAreaFormacao: expect.any(Object),
            responsavelCurso: expect.any(Object),
            responsavelDisciplina: expect.any(Object),
            responsavelTurma: expect.any(Object),
          })
        );
      });
    });

    describe('getAnoLectivo', () => {
      it('should return NewAnoLectivo for default AnoLectivo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAnoLectivoFormGroup(sampleWithNewData);

        const anoLectivo = service.getAnoLectivo(formGroup) as any;

        expect(anoLectivo).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnoLectivo for empty AnoLectivo initial value', () => {
        const formGroup = service.createAnoLectivoFormGroup();

        const anoLectivo = service.getAnoLectivo(formGroup) as any;

        expect(anoLectivo).toMatchObject({});
      });

      it('should return IAnoLectivo', () => {
        const formGroup = service.createAnoLectivoFormGroup(sampleWithRequiredData);

        const anoLectivo = service.getAnoLectivo(formGroup) as any;

        expect(anoLectivo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnoLectivo should not enable id FormControl', () => {
        const formGroup = service.createAnoLectivoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnoLectivo should disable id FormControl', () => {
        const formGroup = service.createAnoLectivoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
