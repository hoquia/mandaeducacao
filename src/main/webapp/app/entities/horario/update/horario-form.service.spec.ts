import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../horario.test-samples';

import { HorarioFormService } from './horario-form.service';

describe('Horario Form Service', () => {
  let service: HorarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HorarioFormService);
  });

  describe('Service methods', () => {
    describe('createHorarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHorarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta1: expect.any(Object),
            chaveComposta2: expect.any(Object),
            diaSemana: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
            referencia: expect.any(Object),
            periodo: expect.any(Object),
            docente: expect.any(Object),
            disciplinaCurricular: expect.any(Object),
          })
        );
      });

      it('passing IHorario should create a new form with FormGroup', () => {
        const formGroup = service.createHorarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta1: expect.any(Object),
            chaveComposta2: expect.any(Object),
            diaSemana: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
            referencia: expect.any(Object),
            periodo: expect.any(Object),
            docente: expect.any(Object),
            disciplinaCurricular: expect.any(Object),
          })
        );
      });
    });

    describe('getHorario', () => {
      it('should return NewHorario for default Horario initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createHorarioFormGroup(sampleWithNewData);

        const horario = service.getHorario(formGroup) as any;

        expect(horario).toMatchObject(sampleWithNewData);
      });

      it('should return NewHorario for empty Horario initial value', () => {
        const formGroup = service.createHorarioFormGroup();

        const horario = service.getHorario(formGroup) as any;

        expect(horario).toMatchObject({});
      });

      it('should return IHorario', () => {
        const formGroup = service.createHorarioFormGroup(sampleWithRequiredData);

        const horario = service.getHorario(formGroup) as any;

        expect(horario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHorario should not enable id FormControl', () => {
        const formGroup = service.createHorarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHorario should disable id FormControl', () => {
        const formGroup = service.createHorarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
