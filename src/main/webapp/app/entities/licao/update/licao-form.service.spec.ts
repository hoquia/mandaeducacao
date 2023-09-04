import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../licao.test-samples';

import { LicaoFormService } from './licao-form.service';

describe('Licao Form Service', () => {
  let service: LicaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LicaoFormService);
  });

  describe('Service methods', () => {
    describe('createLicaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLicaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta: expect.any(Object),
            numero: expect.any(Object),
            estado: expect.any(Object),
            descricao: expect.any(Object),
            utilizador: expect.any(Object),
            planoAula: expect.any(Object),
            horario: expect.any(Object),
          })
        );
      });

      it('passing ILicao should create a new form with FormGroup', () => {
        const formGroup = service.createLicaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta: expect.any(Object),
            numero: expect.any(Object),
            estado: expect.any(Object),
            descricao: expect.any(Object),
            utilizador: expect.any(Object),
            planoAula: expect.any(Object),
            horario: expect.any(Object),
          })
        );
      });
    });

    describe('getLicao', () => {
      it('should return NewLicao for default Licao initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLicaoFormGroup(sampleWithNewData);

        const licao = service.getLicao(formGroup) as any;

        expect(licao).toMatchObject(sampleWithNewData);
      });

      it('should return NewLicao for empty Licao initial value', () => {
        const formGroup = service.createLicaoFormGroup();

        const licao = service.getLicao(formGroup) as any;

        expect(licao).toMatchObject({});
      });

      it('should return ILicao', () => {
        const formGroup = service.createLicaoFormGroup(sampleWithRequiredData);

        const licao = service.getLicao(formGroup) as any;

        expect(licao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILicao should not enable id FormControl', () => {
        const formGroup = service.createLicaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLicao should disable id FormControl', () => {
        const formGroup = service.createLicaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
