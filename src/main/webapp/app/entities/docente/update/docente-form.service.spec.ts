import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../docente.test-samples';

import { DocenteFormService } from './docente-form.service';

describe('Docente Form Service', () => {
  let service: DocenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocenteFormService);
  });

  describe('Service methods', () => {
    describe('createDocenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fotografia: expect.any(Object),
            nome: expect.any(Object),
            nascimento: expect.any(Object),
            nif: expect.any(Object),
            inss: expect.any(Object),
            sexo: expect.any(Object),
            pai: expect.any(Object),
            mae: expect.any(Object),
            documentoNumero: expect.any(Object),
            documentoEmissao: expect.any(Object),
            documentoValidade: expect.any(Object),
            residencia: expect.any(Object),
            dataInicioFuncoes: expect.any(Object),
            telefonePrincipal: expect.any(Object),
            telefoneParente: expect.any(Object),
            email: expect.any(Object),
            numeroAgente: expect.any(Object),
            temAgregacaoPedagogica: expect.any(Object),
            observacao: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            nacionalidade: expect.any(Object),
            naturalidade: expect.any(Object),
            tipoDocumento: expect.any(Object),
            grauAcademico: expect.any(Object),
            categoriaProfissional: expect.any(Object),
            unidadeOrganica: expect.any(Object),
            estadoCivil: expect.any(Object),
            responsavelTurno: expect.any(Object),
            responsavelAreaFormacao: expect.any(Object),
            responsavelCurso: expect.any(Object),
            responsavelDisciplina: expect.any(Object),
            responsavelTurma: expect.any(Object),
          })
        );
      });

      it('passing IDocente should create a new form with FormGroup', () => {
        const formGroup = service.createDocenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fotografia: expect.any(Object),
            nome: expect.any(Object),
            nascimento: expect.any(Object),
            nif: expect.any(Object),
            inss: expect.any(Object),
            sexo: expect.any(Object),
            pai: expect.any(Object),
            mae: expect.any(Object),
            documentoNumero: expect.any(Object),
            documentoEmissao: expect.any(Object),
            documentoValidade: expect.any(Object),
            residencia: expect.any(Object),
            dataInicioFuncoes: expect.any(Object),
            telefonePrincipal: expect.any(Object),
            telefoneParente: expect.any(Object),
            email: expect.any(Object),
            numeroAgente: expect.any(Object),
            temAgregacaoPedagogica: expect.any(Object),
            observacao: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            nacionalidade: expect.any(Object),
            naturalidade: expect.any(Object),
            tipoDocumento: expect.any(Object),
            grauAcademico: expect.any(Object),
            categoriaProfissional: expect.any(Object),
            unidadeOrganica: expect.any(Object),
            estadoCivil: expect.any(Object),
            responsavelTurno: expect.any(Object),
            responsavelAreaFormacao: expect.any(Object),
            responsavelCurso: expect.any(Object),
            responsavelDisciplina: expect.any(Object),
            responsavelTurma: expect.any(Object),
          })
        );
      });
    });

    describe('getDocente', () => {
      it('should return NewDocente for default Docente initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDocenteFormGroup(sampleWithNewData);

        const docente = service.getDocente(formGroup) as any;

        expect(docente).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocente for empty Docente initial value', () => {
        const formGroup = service.createDocenteFormGroup();

        const docente = service.getDocente(formGroup) as any;

        expect(docente).toMatchObject({});
      });

      it('should return IDocente', () => {
        const formGroup = service.createDocenteFormGroup(sampleWithRequiredData);

        const docente = service.getDocente(formGroup) as any;

        expect(docente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocente should not enable id FormControl', () => {
        const formGroup = service.createDocenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocente should disable id FormControl', () => {
        const formGroup = service.createDocenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
