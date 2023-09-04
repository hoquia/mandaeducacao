import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../resumo-academico.test-samples';

import { ResumoAcademicoFormService } from './resumo-academico-form.service';

describe('ResumoAcademico Form Service', () => {
  let service: ResumoAcademicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResumoAcademicoFormService);
  });

  describe('Service methods', () => {
    describe('createResumoAcademicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResumoAcademicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            temaProjecto: expect.any(Object),
            notaProjecto: expect.any(Object),
            observacao: expect.any(Object),
            localEstagio: expect.any(Object),
            notaEstagio: expect.any(Object),
            mediaFinalDisciplina: expect.any(Object),
            classificacaoFinal: expect.any(Object),
            numeroGrupo: expect.any(Object),
            mesaDefesa: expect.any(Object),
            livroRegistro: expect.any(Object),
            numeroFolha: expect.any(Object),
            chefeSecretariaPedagogica: expect.any(Object),
            subDirectorPedagogico: expect.any(Object),
            directorGeral: expect.any(Object),
            tutorProjecto: expect.any(Object),
            juriMesa: expect.any(Object),
            empresaEstagio: expect.any(Object),
            assinaturaDigital: expect.any(Object),
            hash: expect.any(Object),
            utilizador: expect.any(Object),
            ultimaTurmaMatriculada: expect.any(Object),
            discente: expect.any(Object),
            situacao: expect.any(Object),
          })
        );
      });

      it('passing IResumoAcademico should create a new form with FormGroup', () => {
        const formGroup = service.createResumoAcademicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            temaProjecto: expect.any(Object),
            notaProjecto: expect.any(Object),
            observacao: expect.any(Object),
            localEstagio: expect.any(Object),
            notaEstagio: expect.any(Object),
            mediaFinalDisciplina: expect.any(Object),
            classificacaoFinal: expect.any(Object),
            numeroGrupo: expect.any(Object),
            mesaDefesa: expect.any(Object),
            livroRegistro: expect.any(Object),
            numeroFolha: expect.any(Object),
            chefeSecretariaPedagogica: expect.any(Object),
            subDirectorPedagogico: expect.any(Object),
            directorGeral: expect.any(Object),
            tutorProjecto: expect.any(Object),
            juriMesa: expect.any(Object),
            empresaEstagio: expect.any(Object),
            assinaturaDigital: expect.any(Object),
            hash: expect.any(Object),
            utilizador: expect.any(Object),
            ultimaTurmaMatriculada: expect.any(Object),
            discente: expect.any(Object),
            situacao: expect.any(Object),
          })
        );
      });
    });

    describe('getResumoAcademico', () => {
      it('should return NewResumoAcademico for default ResumoAcademico initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResumoAcademicoFormGroup(sampleWithNewData);

        const resumoAcademico = service.getResumoAcademico(formGroup) as any;

        expect(resumoAcademico).toMatchObject(sampleWithNewData);
      });

      it('should return NewResumoAcademico for empty ResumoAcademico initial value', () => {
        const formGroup = service.createResumoAcademicoFormGroup();

        const resumoAcademico = service.getResumoAcademico(formGroup) as any;

        expect(resumoAcademico).toMatchObject({});
      });

      it('should return IResumoAcademico', () => {
        const formGroup = service.createResumoAcademicoFormGroup(sampleWithRequiredData);

        const resumoAcademico = service.getResumoAcademico(formGroup) as any;

        expect(resumoAcademico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResumoAcademico should not enable id FormControl', () => {
        const formGroup = service.createResumoAcademicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResumoAcademico should disable id FormControl', () => {
        const formGroup = service.createResumoAcademicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
