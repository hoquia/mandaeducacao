import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../factura.test-samples';

import { FacturaFormService } from './factura-form.service';

describe('Factura Form Service', () => {
  let service: FacturaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FacturaFormService);
  });

  describe('Service methods', () => {
    describe('createFacturaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFacturaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numero: expect.any(Object),
            codigoEntrega: expect.any(Object),
            dataEmissao: expect.any(Object),
            dataVencimento: expect.any(Object),
            cae: expect.any(Object),
            inicioTransporte: expect.any(Object),
            fimTransporte: expect.any(Object),
            observacaoGeral: expect.any(Object),
            observacaoInterna: expect.any(Object),
            estado: expect.any(Object),
            origem: expect.any(Object),
            timestamp: expect.any(Object),
            isMoedaEntrangeira: expect.any(Object),
            moeda: expect.any(Object),
            cambio: expect.any(Object),
            totalMoedaEntrangeira: expect.any(Object),
            totalIliquido: expect.any(Object),
            totalDescontoComercial: expect.any(Object),
            totalLiquido: expect.any(Object),
            totalImpostoIVA: expect.any(Object),
            totalImpostoEspecialConsumo: expect.any(Object),
            totalDescontoFinanceiro: expect.any(Object),
            totalFactura: expect.any(Object),
            totalImpostoRetencaoFonte: expect.any(Object),
            totalPagar: expect.any(Object),
            debito: expect.any(Object),
            credito: expect.any(Object),
            totalPago: expect.any(Object),
            totalDiferenca: expect.any(Object),
            isAutoFacturacao: expect.any(Object),
            isRegimeCaixa: expect.any(Object),
            isEmitidaNomeEContaTerceiro: expect.any(Object),
            isNovo: expect.any(Object),
            isFiscalizado: expect.any(Object),
            signText: expect.any(Object),
            hash: expect.any(Object),
            hashShort: expect.any(Object),
            hashControl: expect.any(Object),
            keyVersion: expect.any(Object),
            utilizador: expect.any(Object),
            motivoAnulacao: expect.any(Object),
            matricula: expect.any(Object),
            referencia: expect.any(Object),
            documentoComercial: expect.any(Object),
          })
        );
      });

      it('passing IFactura should create a new form with FormGroup', () => {
        const formGroup = service.createFacturaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numero: expect.any(Object),
            codigoEntrega: expect.any(Object),
            dataEmissao: expect.any(Object),
            dataVencimento: expect.any(Object),
            cae: expect.any(Object),
            inicioTransporte: expect.any(Object),
            fimTransporte: expect.any(Object),
            observacaoGeral: expect.any(Object),
            observacaoInterna: expect.any(Object),
            estado: expect.any(Object),
            origem: expect.any(Object),
            timestamp: expect.any(Object),
            isMoedaEntrangeira: expect.any(Object),
            moeda: expect.any(Object),
            cambio: expect.any(Object),
            totalMoedaEntrangeira: expect.any(Object),
            totalIliquido: expect.any(Object),
            totalDescontoComercial: expect.any(Object),
            totalLiquido: expect.any(Object),
            totalImpostoIVA: expect.any(Object),
            totalImpostoEspecialConsumo: expect.any(Object),
            totalDescontoFinanceiro: expect.any(Object),
            totalFactura: expect.any(Object),
            totalImpostoRetencaoFonte: expect.any(Object),
            totalPagar: expect.any(Object),
            debito: expect.any(Object),
            credito: expect.any(Object),
            totalPago: expect.any(Object),
            totalDiferenca: expect.any(Object),
            isAutoFacturacao: expect.any(Object),
            isRegimeCaixa: expect.any(Object),
            isEmitidaNomeEContaTerceiro: expect.any(Object),
            isNovo: expect.any(Object),
            isFiscalizado: expect.any(Object),
            signText: expect.any(Object),
            hash: expect.any(Object),
            hashShort: expect.any(Object),
            hashControl: expect.any(Object),
            keyVersion: expect.any(Object),
            utilizador: expect.any(Object),
            motivoAnulacao: expect.any(Object),
            matricula: expect.any(Object),
            referencia: expect.any(Object),
            documentoComercial: expect.any(Object),
          })
        );
      });
    });

    describe('getFactura', () => {
      it('should return NewFactura for default Factura initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFacturaFormGroup(sampleWithNewData);

        const factura = service.getFactura(formGroup) as any;

        expect(factura).toMatchObject(sampleWithNewData);
      });

      it('should return NewFactura for empty Factura initial value', () => {
        const formGroup = service.createFacturaFormGroup();

        const factura = service.getFactura(formGroup) as any;

        expect(factura).toMatchObject({});
      });

      it('should return IFactura', () => {
        const formGroup = service.createFacturaFormGroup(sampleWithRequiredData);

        const factura = service.getFactura(formGroup) as any;

        expect(factura).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFactura should not enable id FormControl', () => {
        const formGroup = service.createFacturaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFactura should disable id FormControl', () => {
        const formGroup = service.createFacturaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
