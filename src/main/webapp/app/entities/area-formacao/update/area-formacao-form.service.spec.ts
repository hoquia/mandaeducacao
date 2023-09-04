import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../area-formacao.test-samples';

import { AreaFormacaoFormService } from './area-formacao-form.service';

describe('AreaFormacao Form Service', () => {
  let service: AreaFormacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AreaFormacaoFormService);
  });

  describe('Service methods', () => {
    describe('createAreaFormacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAreaFormacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagem: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            nivelEnsino: expect.any(Object),
          })
        );
      });

      it('passing IAreaFormacao should create a new form with FormGroup', () => {
        const formGroup = service.createAreaFormacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagem: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            nivelEnsino: expect.any(Object),
          })
        );
      });
    });

    describe('getAreaFormacao', () => {
      it('should return NewAreaFormacao for default AreaFormacao initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAreaFormacaoFormGroup(sampleWithNewData);

        const areaFormacao = service.getAreaFormacao(formGroup) as any;

        expect(areaFormacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewAreaFormacao for empty AreaFormacao initial value', () => {
        const formGroup = service.createAreaFormacaoFormGroup();

        const areaFormacao = service.getAreaFormacao(formGroup) as any;

        expect(areaFormacao).toMatchObject({});
      });

      it('should return IAreaFormacao', () => {
        const formGroup = service.createAreaFormacaoFormGroup(sampleWithRequiredData);

        const areaFormacao = service.getAreaFormacao(formGroup) as any;

        expect(areaFormacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAreaFormacao should not enable id FormControl', () => {
        const formGroup = service.createAreaFormacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAreaFormacao should disable id FormControl', () => {
        const formGroup = service.createAreaFormacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
