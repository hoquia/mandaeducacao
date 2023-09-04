import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../responsavel-area-formacao.test-samples';

import { ResponsavelAreaFormacaoFormService } from './responsavel-area-formacao-form.service';

describe('ResponsavelAreaFormacao Form Service', () => {
  let service: ResponsavelAreaFormacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResponsavelAreaFormacaoFormService);
  });

  describe('Service methods', () => {
    describe('createResponsavelAreaFormacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResponsavelAreaFormacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            areaFormacao: expect.any(Object),
          })
        );
      });

      it('passing IResponsavelAreaFormacao should create a new form with FormGroup', () => {
        const formGroup = service.createResponsavelAreaFormacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            areaFormacao: expect.any(Object),
          })
        );
      });
    });

    describe('getResponsavelAreaFormacao', () => {
      it('should return NewResponsavelAreaFormacao for default ResponsavelAreaFormacao initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResponsavelAreaFormacaoFormGroup(sampleWithNewData);

        const responsavelAreaFormacao = service.getResponsavelAreaFormacao(formGroup) as any;

        expect(responsavelAreaFormacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewResponsavelAreaFormacao for empty ResponsavelAreaFormacao initial value', () => {
        const formGroup = service.createResponsavelAreaFormacaoFormGroup();

        const responsavelAreaFormacao = service.getResponsavelAreaFormacao(formGroup) as any;

        expect(responsavelAreaFormacao).toMatchObject({});
      });

      it('should return IResponsavelAreaFormacao', () => {
        const formGroup = service.createResponsavelAreaFormacaoFormGroup(sampleWithRequiredData);

        const responsavelAreaFormacao = service.getResponsavelAreaFormacao(formGroup) as any;

        expect(responsavelAreaFormacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResponsavelAreaFormacao should not enable id FormControl', () => {
        const formGroup = service.createResponsavelAreaFormacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResponsavelAreaFormacao should disable id FormControl', () => {
        const formGroup = service.createResponsavelAreaFormacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
