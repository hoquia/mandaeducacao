import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../campo-actuacao-dissertacao.test-samples';

import { CampoActuacaoDissertacaoFormService } from './campo-actuacao-dissertacao-form.service';

describe('CampoActuacaoDissertacao Form Service', () => {
  let service: CampoActuacaoDissertacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CampoActuacaoDissertacaoFormService);
  });

  describe('Service methods', () => {
    describe('createCampoActuacaoDissertacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCampoActuacaoDissertacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            isActivo: expect.any(Object),
            cursos: expect.any(Object),
          })
        );
      });

      it('passing ICampoActuacaoDissertacao should create a new form with FormGroup', () => {
        const formGroup = service.createCampoActuacaoDissertacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            isActivo: expect.any(Object),
            cursos: expect.any(Object),
          })
        );
      });
    });

    describe('getCampoActuacaoDissertacao', () => {
      it('should return NewCampoActuacaoDissertacao for default CampoActuacaoDissertacao initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCampoActuacaoDissertacaoFormGroup(sampleWithNewData);

        const campoActuacaoDissertacao = service.getCampoActuacaoDissertacao(formGroup) as any;

        expect(campoActuacaoDissertacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewCampoActuacaoDissertacao for empty CampoActuacaoDissertacao initial value', () => {
        const formGroup = service.createCampoActuacaoDissertacaoFormGroup();

        const campoActuacaoDissertacao = service.getCampoActuacaoDissertacao(formGroup) as any;

        expect(campoActuacaoDissertacao).toMatchObject({});
      });

      it('should return ICampoActuacaoDissertacao', () => {
        const formGroup = service.createCampoActuacaoDissertacaoFormGroup(sampleWithRequiredData);

        const campoActuacaoDissertacao = service.getCampoActuacaoDissertacao(formGroup) as any;

        expect(campoActuacaoDissertacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICampoActuacaoDissertacao should not enable id FormControl', () => {
        const formGroup = service.createCampoActuacaoDissertacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCampoActuacaoDissertacao should disable id FormControl', () => {
        const formGroup = service.createCampoActuacaoDissertacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
