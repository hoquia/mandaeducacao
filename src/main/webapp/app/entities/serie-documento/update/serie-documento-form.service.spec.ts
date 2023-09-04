import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../serie-documento.test-samples';

import { SerieDocumentoFormService } from './serie-documento-form.service';

describe('SerieDocumento Form Service', () => {
  let service: SerieDocumentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SerieDocumentoFormService);
  });

  describe('Service methods', () => {
    describe('createSerieDocumentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSerieDocumentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            anoFiscal: expect.any(Object),
            versao: expect.any(Object),
            serie: expect.any(Object),
            isAtivo: expect.any(Object),
            isPadrao: expect.any(Object),
            tipoDocumento: expect.any(Object),
          })
        );
      });

      it('passing ISerieDocumento should create a new form with FormGroup', () => {
        const formGroup = service.createSerieDocumentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            anoFiscal: expect.any(Object),
            versao: expect.any(Object),
            serie: expect.any(Object),
            isAtivo: expect.any(Object),
            isPadrao: expect.any(Object),
            tipoDocumento: expect.any(Object),
          })
        );
      });
    });

    describe('getSerieDocumento', () => {
      it('should return NewSerieDocumento for default SerieDocumento initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSerieDocumentoFormGroup(sampleWithNewData);

        const serieDocumento = service.getSerieDocumento(formGroup) as any;

        expect(serieDocumento).toMatchObject(sampleWithNewData);
      });

      it('should return NewSerieDocumento for empty SerieDocumento initial value', () => {
        const formGroup = service.createSerieDocumentoFormGroup();

        const serieDocumento = service.getSerieDocumento(formGroup) as any;

        expect(serieDocumento).toMatchObject({});
      });

      it('should return ISerieDocumento', () => {
        const formGroup = service.createSerieDocumentoFormGroup(sampleWithRequiredData);

        const serieDocumento = service.getSerieDocumento(formGroup) as any;

        expect(serieDocumento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISerieDocumento should not enable id FormControl', () => {
        const formGroup = service.createSerieDocumentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSerieDocumento should disable id FormControl', () => {
        const formGroup = service.createSerieDocumentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
