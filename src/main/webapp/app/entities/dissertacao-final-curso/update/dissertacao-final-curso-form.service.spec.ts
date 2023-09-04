import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dissertacao-final-curso.test-samples';

import { DissertacaoFinalCursoFormService } from './dissertacao-final-curso-form.service';

describe('DissertacaoFinalCurso Form Service', () => {
  let service: DissertacaoFinalCursoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DissertacaoFinalCursoFormService);
  });

  describe('Service methods', () => {
    describe('createDissertacaoFinalCursoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDissertacaoFinalCursoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numero: expect.any(Object),
            timestamp: expect.any(Object),
            data: expect.any(Object),
            tema: expect.any(Object),
            objectivoGeral: expect.any(Object),
            objectivosEspecificos: expect.any(Object),
            introducao: expect.any(Object),
            resumo: expect.any(Object),
            problema: expect.any(Object),
            resultado: expect.any(Object),
            metodologia: expect.any(Object),
            referenciasBibliograficas: expect.any(Object),
            observacaoOrientador: expect.any(Object),
            observacaoAreaFormacao: expect.any(Object),
            observacaoInstituicao: expect.any(Object),
            hash: expect.any(Object),
            termosCompromissos: expect.any(Object),
            isAceiteTermosCompromisso: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
            orientador: expect.any(Object),
            especialidade: expect.any(Object),
            discente: expect.any(Object),
            estado: expect.any(Object),
            natureza: expect.any(Object),
          })
        );
      });

      it('passing IDissertacaoFinalCurso should create a new form with FormGroup', () => {
        const formGroup = service.createDissertacaoFinalCursoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numero: expect.any(Object),
            timestamp: expect.any(Object),
            data: expect.any(Object),
            tema: expect.any(Object),
            objectivoGeral: expect.any(Object),
            objectivosEspecificos: expect.any(Object),
            introducao: expect.any(Object),
            resumo: expect.any(Object),
            problema: expect.any(Object),
            resultado: expect.any(Object),
            metodologia: expect.any(Object),
            referenciasBibliograficas: expect.any(Object),
            observacaoOrientador: expect.any(Object),
            observacaoAreaFormacao: expect.any(Object),
            observacaoInstituicao: expect.any(Object),
            hash: expect.any(Object),
            termosCompromissos: expect.any(Object),
            isAceiteTermosCompromisso: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
            orientador: expect.any(Object),
            especialidade: expect.any(Object),
            discente: expect.any(Object),
            estado: expect.any(Object),
            natureza: expect.any(Object),
          })
        );
      });
    });

    describe('getDissertacaoFinalCurso', () => {
      it('should return NewDissertacaoFinalCurso for default DissertacaoFinalCurso initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDissertacaoFinalCursoFormGroup(sampleWithNewData);

        const dissertacaoFinalCurso = service.getDissertacaoFinalCurso(formGroup) as any;

        expect(dissertacaoFinalCurso).toMatchObject(sampleWithNewData);
      });

      it('should return NewDissertacaoFinalCurso for empty DissertacaoFinalCurso initial value', () => {
        const formGroup = service.createDissertacaoFinalCursoFormGroup();

        const dissertacaoFinalCurso = service.getDissertacaoFinalCurso(formGroup) as any;

        expect(dissertacaoFinalCurso).toMatchObject({});
      });

      it('should return IDissertacaoFinalCurso', () => {
        const formGroup = service.createDissertacaoFinalCursoFormGroup(sampleWithRequiredData);

        const dissertacaoFinalCurso = service.getDissertacaoFinalCurso(formGroup) as any;

        expect(dissertacaoFinalCurso).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDissertacaoFinalCurso should not enable id FormControl', () => {
        const formGroup = service.createDissertacaoFinalCursoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDissertacaoFinalCurso should disable id FormControl', () => {
        const formGroup = service.createDissertacaoFinalCursoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
