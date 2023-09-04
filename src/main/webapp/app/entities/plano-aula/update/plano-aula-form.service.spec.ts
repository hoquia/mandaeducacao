import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plano-aula.test-samples';

import { PlanoAulaFormService } from './plano-aula-form.service';

describe('PlanoAula Form Service', () => {
  let service: PlanoAulaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanoAulaFormService);
  });

  describe('Service methods', () => {
    describe('createPlanoAulaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanoAulaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipoAula: expect.any(Object),
            semanaLectiva: expect.any(Object),
            perfilEntrada: expect.any(Object),
            perfilSaida: expect.any(Object),
            assunto: expect.any(Object),
            objectivoGeral: expect.any(Object),
            objectivosEspecificos: expect.any(Object),
            tempoTotalLicao: expect.any(Object),
            estado: expect.any(Object),
            utilizador: expect.any(Object),
            unidadeTematica: expect.any(Object),
            subUnidadeTematica: expect.any(Object),
            turma: expect.any(Object),
            docente: expect.any(Object),
            disciplinaCurricular: expect.any(Object),
          })
        );
      });

      it('passing IPlanoAula should create a new form with FormGroup', () => {
        const formGroup = service.createPlanoAulaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipoAula: expect.any(Object),
            semanaLectiva: expect.any(Object),
            perfilEntrada: expect.any(Object),
            perfilSaida: expect.any(Object),
            assunto: expect.any(Object),
            objectivoGeral: expect.any(Object),
            objectivosEspecificos: expect.any(Object),
            tempoTotalLicao: expect.any(Object),
            estado: expect.any(Object),
            utilizador: expect.any(Object),
            unidadeTematica: expect.any(Object),
            subUnidadeTematica: expect.any(Object),
            turma: expect.any(Object),
            docente: expect.any(Object),
            disciplinaCurricular: expect.any(Object),
          })
        );
      });
    });

    describe('getPlanoAula', () => {
      it('should return NewPlanoAula for default PlanoAula initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPlanoAulaFormGroup(sampleWithNewData);

        const planoAula = service.getPlanoAula(formGroup) as any;

        expect(planoAula).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanoAula for empty PlanoAula initial value', () => {
        const formGroup = service.createPlanoAulaFormGroup();

        const planoAula = service.getPlanoAula(formGroup) as any;

        expect(planoAula).toMatchObject({});
      });

      it('should return IPlanoAula', () => {
        const formGroup = service.createPlanoAulaFormGroup(sampleWithRequiredData);

        const planoAula = service.getPlanoAula(formGroup) as any;

        expect(planoAula).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanoAula should not enable id FormControl', () => {
        const formGroup = service.createPlanoAulaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanoAula should disable id FormControl', () => {
        const formGroup = service.createPlanoAulaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
