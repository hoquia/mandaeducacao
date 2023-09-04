import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../notas-periodica-disciplina.test-samples';

import { NotasPeriodicaDisciplinaFormService } from './notas-periodica-disciplina-form.service';

describe('NotasPeriodicaDisciplina Form Service', () => {
  let service: NotasPeriodicaDisciplinaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotasPeriodicaDisciplinaFormService);
  });

  describe('Service methods', () => {
    describe('createNotasPeriodicaDisciplinaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNotasPeriodicaDisciplinaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta: expect.any(Object),
            periodoLancamento: expect.any(Object),
            nota1: expect.any(Object),
            nota2: expect.any(Object),
            nota3: expect.any(Object),
            media: expect.any(Object),
            faltaJusticada: expect.any(Object),
            faltaInjustificada: expect.any(Object),
            comportamento: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
            docente: expect.any(Object),
            disciplinaCurricular: expect.any(Object),
            matricula: expect.any(Object),
            estado: expect.any(Object),
          })
        );
      });

      it('passing INotasPeriodicaDisciplina should create a new form with FormGroup', () => {
        const formGroup = service.createNotasPeriodicaDisciplinaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta: expect.any(Object),
            periodoLancamento: expect.any(Object),
            nota1: expect.any(Object),
            nota2: expect.any(Object),
            nota3: expect.any(Object),
            media: expect.any(Object),
            faltaJusticada: expect.any(Object),
            faltaInjustificada: expect.any(Object),
            comportamento: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
            docente: expect.any(Object),
            disciplinaCurricular: expect.any(Object),
            matricula: expect.any(Object),
            estado: expect.any(Object),
          })
        );
      });
    });

    describe('getNotasPeriodicaDisciplina', () => {
      it('should return NewNotasPeriodicaDisciplina for default NotasPeriodicaDisciplina initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNotasPeriodicaDisciplinaFormGroup(sampleWithNewData);

        const notasPeriodicaDisciplina = service.getNotasPeriodicaDisciplina(formGroup) as any;

        expect(notasPeriodicaDisciplina).toMatchObject(sampleWithNewData);
      });

      it('should return NewNotasPeriodicaDisciplina for empty NotasPeriodicaDisciplina initial value', () => {
        const formGroup = service.createNotasPeriodicaDisciplinaFormGroup();

        const notasPeriodicaDisciplina = service.getNotasPeriodicaDisciplina(formGroup) as any;

        expect(notasPeriodicaDisciplina).toMatchObject({});
      });

      it('should return INotasPeriodicaDisciplina', () => {
        const formGroup = service.createNotasPeriodicaDisciplinaFormGroup(sampleWithRequiredData);

        const notasPeriodicaDisciplina = service.getNotasPeriodicaDisciplina(formGroup) as any;

        expect(notasPeriodicaDisciplina).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INotasPeriodicaDisciplina should not enable id FormControl', () => {
        const formGroup = service.createNotasPeriodicaDisciplinaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNotasPeriodicaDisciplina should disable id FormControl', () => {
        const formGroup = service.createNotasPeriodicaDisciplinaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
