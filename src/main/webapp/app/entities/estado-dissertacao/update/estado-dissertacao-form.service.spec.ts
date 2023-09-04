import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../estado-dissertacao.test-samples';

import { EstadoDissertacaoFormService } from './estado-dissertacao-form.service';

describe('EstadoDissertacao Form Service', () => {
  let service: EstadoDissertacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstadoDissertacaoFormService);
  });

  describe('Service methods', () => {
    describe('createEstadoDissertacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEstadoDissertacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            etapa: expect.any(Object),
            descricao: expect.any(Object),
          })
        );
      });

      it('passing IEstadoDissertacao should create a new form with FormGroup', () => {
        const formGroup = service.createEstadoDissertacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            etapa: expect.any(Object),
            descricao: expect.any(Object),
          })
        );
      });
    });

    describe('getEstadoDissertacao', () => {
      it('should return NewEstadoDissertacao for default EstadoDissertacao initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEstadoDissertacaoFormGroup(sampleWithNewData);

        const estadoDissertacao = service.getEstadoDissertacao(formGroup) as any;

        expect(estadoDissertacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewEstadoDissertacao for empty EstadoDissertacao initial value', () => {
        const formGroup = service.createEstadoDissertacaoFormGroup();

        const estadoDissertacao = service.getEstadoDissertacao(formGroup) as any;

        expect(estadoDissertacao).toMatchObject({});
      });

      it('should return IEstadoDissertacao', () => {
        const formGroup = service.createEstadoDissertacaoFormGroup(sampleWithRequiredData);

        const estadoDissertacao = service.getEstadoDissertacao(formGroup) as any;

        expect(estadoDissertacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEstadoDissertacao should not enable id FormControl', () => {
        const formGroup = service.createEstadoDissertacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEstadoDissertacao should disable id FormControl', () => {
        const formGroup = service.createEstadoDissertacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
