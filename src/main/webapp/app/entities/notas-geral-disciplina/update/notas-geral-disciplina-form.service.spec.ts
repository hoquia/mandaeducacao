import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../notas-geral-disciplina.test-samples';

import { NotasGeralDisciplinaFormService } from './notas-geral-disciplina-form.service';

describe('NotasGeralDisciplina Form Service', () => {
  let service: NotasGeralDisciplinaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotasGeralDisciplinaFormService);
  });

  describe('Service methods', () => {
    describe('createNotasGeralDisciplinaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNotasGeralDisciplinaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta: expect.any(Object),
            periodoLancamento: expect.any(Object),
            media1: expect.any(Object),
            media2: expect.any(Object),
            media3: expect.any(Object),
            exame: expect.any(Object),
            recurso: expect.any(Object),
            exameEspecial: expect.any(Object),
            notaConselho: expect.any(Object),
            mediaFinalDisciplina: expect.any(Object),
            timestamp: expect.any(Object),
            hash: expect.any(Object),
            faltaJusticada: expect.any(Object),
            faltaInjustificada: expect.any(Object),
            utilizador: expect.any(Object),
            docente: expect.any(Object),
            disciplinaCurricular: expect.any(Object),
            matricula: expect.any(Object),
            estado: expect.any(Object),
          })
        );
      });

      it('passing INotasGeralDisciplina should create a new form with FormGroup', () => {
        const formGroup = service.createNotasGeralDisciplinaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta: expect.any(Object),
            periodoLancamento: expect.any(Object),
            media1: expect.any(Object),
            media2: expect.any(Object),
            media3: expect.any(Object),
            exame: expect.any(Object),
            recurso: expect.any(Object),
            exameEspecial: expect.any(Object),
            notaConselho: expect.any(Object),
            mediaFinalDisciplina: expect.any(Object),
            timestamp: expect.any(Object),
            hash: expect.any(Object),
            faltaJusticada: expect.any(Object),
            faltaInjustificada: expect.any(Object),
            utilizador: expect.any(Object),
            docente: expect.any(Object),
            disciplinaCurricular: expect.any(Object),
            matricula: expect.any(Object),
            estado: expect.any(Object),
          })
        );
      });
    });

    describe('getNotasGeralDisciplina', () => {
      it('should return NewNotasGeralDisciplina for default NotasGeralDisciplina initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNotasGeralDisciplinaFormGroup(sampleWithNewData);

        const notasGeralDisciplina = service.getNotasGeralDisciplina(formGroup) as any;

        expect(notasGeralDisciplina).toMatchObject(sampleWithNewData);
      });

      it('should return NewNotasGeralDisciplina for empty NotasGeralDisciplina initial value', () => {
        const formGroup = service.createNotasGeralDisciplinaFormGroup();

        const notasGeralDisciplina = service.getNotasGeralDisciplina(formGroup) as any;

        expect(notasGeralDisciplina).toMatchObject({});
      });

      it('should return INotasGeralDisciplina', () => {
        const formGroup = service.createNotasGeralDisciplinaFormGroup(sampleWithRequiredData);

        const notasGeralDisciplina = service.getNotasGeralDisciplina(formGroup) as any;

        expect(notasGeralDisciplina).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INotasGeralDisciplina should not enable id FormControl', () => {
        const formGroup = service.createNotasGeralDisciplinaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNotasGeralDisciplina should disable id FormControl', () => {
        const formGroup = service.createNotasGeralDisciplinaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
