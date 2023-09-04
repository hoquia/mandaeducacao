import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../documento-comercial.test-samples';

import { DocumentoComercialFormService } from './documento-comercial-form.service';

describe('DocumentoComercial Form Service', () => {
  let service: DocumentoComercialFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentoComercialFormService);
  });

  describe('Service methods', () => {
    describe('createDocumentoComercialFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocumentoComercialFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            modulo: expect.any(Object),
            origem: expect.any(Object),
            siglaInterna: expect.any(Object),
            descricao: expect.any(Object),
            siglaFiscal: expect.any(Object),
            isMovimentaEstoque: expect.any(Object),
            isMovimentaCaixa: expect.any(Object),
            isNotificaEntidade: expect.any(Object),
            isNotificaGerente: expect.any(Object),
            isEnviaSMS: expect.any(Object),
            isEnviaEmail: expect.any(Object),
            isEnviaPush: expect.any(Object),
            validaCreditoDisponivel: expect.any(Object),
            transformaEm: expect.any(Object),
          })
        );
      });

      it('passing IDocumentoComercial should create a new form with FormGroup', () => {
        const formGroup = service.createDocumentoComercialFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            modulo: expect.any(Object),
            origem: expect.any(Object),
            siglaInterna: expect.any(Object),
            descricao: expect.any(Object),
            siglaFiscal: expect.any(Object),
            isMovimentaEstoque: expect.any(Object),
            isMovimentaCaixa: expect.any(Object),
            isNotificaEntidade: expect.any(Object),
            isNotificaGerente: expect.any(Object),
            isEnviaSMS: expect.any(Object),
            isEnviaEmail: expect.any(Object),
            isEnviaPush: expect.any(Object),
            validaCreditoDisponivel: expect.any(Object),
            transformaEm: expect.any(Object),
          })
        );
      });
    });

    describe('getDocumentoComercial', () => {
      it('should return NewDocumentoComercial for default DocumentoComercial initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDocumentoComercialFormGroup(sampleWithNewData);

        const documentoComercial = service.getDocumentoComercial(formGroup) as any;

        expect(documentoComercial).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocumentoComercial for empty DocumentoComercial initial value', () => {
        const formGroup = service.createDocumentoComercialFormGroup();

        const documentoComercial = service.getDocumentoComercial(formGroup) as any;

        expect(documentoComercial).toMatchObject({});
      });

      it('should return IDocumentoComercial', () => {
        const formGroup = service.createDocumentoComercialFormGroup(sampleWithRequiredData);

        const documentoComercial = service.getDocumentoComercial(formGroup) as any;

        expect(documentoComercial).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocumentoComercial should not enable id FormControl', () => {
        const formGroup = service.createDocumentoComercialFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocumentoComercial should disable id FormControl', () => {
        const formGroup = service.createDocumentoComercialFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
