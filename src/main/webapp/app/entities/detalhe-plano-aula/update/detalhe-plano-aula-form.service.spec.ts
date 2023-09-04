import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../detalhe-plano-aula.test-samples';

import { DetalhePlanoAulaFormService } from './detalhe-plano-aula-form.service';

describe('DetalhePlanoAula Form Service', () => {
  let service: DetalhePlanoAulaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DetalhePlanoAulaFormService);
  });

  describe('Service methods', () => {
    describe('createDetalhePlanoAulaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDetalhePlanoAulaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            estrategiaAula: expect.any(Object),
            tempoActividade: expect.any(Object),
            recursosEnsino: expect.any(Object),
            tituloActividade: expect.any(Object),
            actividadesDocente: expect.any(Object),
            actividadesDiscentes: expect.any(Object),
            avaliacao: expect.any(Object),
            bibliografia: expect.any(Object),
            observacao: expect.any(Object),
            pdf: expect.any(Object),
            video: expect.any(Object),
            audio: expect.any(Object),
            planoAula: expect.any(Object),
          })
        );
      });

      it('passing IDetalhePlanoAula should create a new form with FormGroup', () => {
        const formGroup = service.createDetalhePlanoAulaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            estrategiaAula: expect.any(Object),
            tempoActividade: expect.any(Object),
            recursosEnsino: expect.any(Object),
            tituloActividade: expect.any(Object),
            actividadesDocente: expect.any(Object),
            actividadesDiscentes: expect.any(Object),
            avaliacao: expect.any(Object),
            bibliografia: expect.any(Object),
            observacao: expect.any(Object),
            pdf: expect.any(Object),
            video: expect.any(Object),
            audio: expect.any(Object),
            planoAula: expect.any(Object),
          })
        );
      });
    });

    describe('getDetalhePlanoAula', () => {
      it('should return NewDetalhePlanoAula for default DetalhePlanoAula initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDetalhePlanoAulaFormGroup(sampleWithNewData);

        const detalhePlanoAula = service.getDetalhePlanoAula(formGroup) as any;

        expect(detalhePlanoAula).toMatchObject(sampleWithNewData);
      });

      it('should return NewDetalhePlanoAula for empty DetalhePlanoAula initial value', () => {
        const formGroup = service.createDetalhePlanoAulaFormGroup();

        const detalhePlanoAula = service.getDetalhePlanoAula(formGroup) as any;

        expect(detalhePlanoAula).toMatchObject({});
      });

      it('should return IDetalhePlanoAula', () => {
        const formGroup = service.createDetalhePlanoAulaFormGroup(sampleWithRequiredData);

        const detalhePlanoAula = service.getDetalhePlanoAula(formGroup) as any;

        expect(detalhePlanoAula).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDetalhePlanoAula should not enable id FormControl', () => {
        const formGroup = service.createDetalhePlanoAulaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDetalhePlanoAula should disable id FormControl', () => {
        const formGroup = service.createDetalhePlanoAulaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
