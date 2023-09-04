import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFactura, NewFactura } from '../factura.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFactura for edit and NewFacturaFormGroupInput for create.
 */
type FacturaFormGroupInput = IFactura | PartialWithRequiredKeyOf<NewFactura>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFactura | NewFactura> = Omit<T, 'inicioTransporte' | 'fimTransporte' | 'timestamp'> & {
  inicioTransporte?: string | null;
  fimTransporte?: string | null;
  timestamp?: string | null;
};

type FacturaFormRawValue = FormValueOf<IFactura>;

type NewFacturaFormRawValue = FormValueOf<NewFactura>;

type FacturaFormDefaults = Pick<
  NewFactura,
  | 'id'
  | 'inicioTransporte'
  | 'fimTransporte'
  | 'timestamp'
  | 'isMoedaEntrangeira'
  | 'isAutoFacturacao'
  | 'isRegimeCaixa'
  | 'isEmitidaNomeEContaTerceiro'
  | 'isNovo'
  | 'isFiscalizado'
>;

type FacturaFormGroupContent = {
  id: FormControl<FacturaFormRawValue['id'] | NewFactura['id']>;
  numero: FormControl<FacturaFormRawValue['numero']>;
  codigoEntrega: FormControl<FacturaFormRawValue['codigoEntrega']>;
  dataEmissao: FormControl<FacturaFormRawValue['dataEmissao']>;
  dataVencimento: FormControl<FacturaFormRawValue['dataVencimento']>;
  cae: FormControl<FacturaFormRawValue['cae']>;
  inicioTransporte: FormControl<FacturaFormRawValue['inicioTransporte']>;
  fimTransporte: FormControl<FacturaFormRawValue['fimTransporte']>;
  observacaoGeral: FormControl<FacturaFormRawValue['observacaoGeral']>;
  observacaoInterna: FormControl<FacturaFormRawValue['observacaoInterna']>;
  estado: FormControl<FacturaFormRawValue['estado']>;
  origem: FormControl<FacturaFormRawValue['origem']>;
  timestamp: FormControl<FacturaFormRawValue['timestamp']>;
  isMoedaEntrangeira: FormControl<FacturaFormRawValue['isMoedaEntrangeira']>;
  moeda: FormControl<FacturaFormRawValue['moeda']>;
  cambio: FormControl<FacturaFormRawValue['cambio']>;
  totalMoedaEntrangeira: FormControl<FacturaFormRawValue['totalMoedaEntrangeira']>;
  totalIliquido: FormControl<FacturaFormRawValue['totalIliquido']>;
  totalDescontoComercial: FormControl<FacturaFormRawValue['totalDescontoComercial']>;
  totalLiquido: FormControl<FacturaFormRawValue['totalLiquido']>;
  totalImpostoIVA: FormControl<FacturaFormRawValue['totalImpostoIVA']>;
  totalImpostoEspecialConsumo: FormControl<FacturaFormRawValue['totalImpostoEspecialConsumo']>;
  totalDescontoFinanceiro: FormControl<FacturaFormRawValue['totalDescontoFinanceiro']>;
  totalFactura: FormControl<FacturaFormRawValue['totalFactura']>;
  totalImpostoRetencaoFonte: FormControl<FacturaFormRawValue['totalImpostoRetencaoFonte']>;
  totalPagar: FormControl<FacturaFormRawValue['totalPagar']>;
  debito: FormControl<FacturaFormRawValue['debito']>;
  credito: FormControl<FacturaFormRawValue['credito']>;
  totalPago: FormControl<FacturaFormRawValue['totalPago']>;
  totalDiferenca: FormControl<FacturaFormRawValue['totalDiferenca']>;
  isAutoFacturacao: FormControl<FacturaFormRawValue['isAutoFacturacao']>;
  isRegimeCaixa: FormControl<FacturaFormRawValue['isRegimeCaixa']>;
  isEmitidaNomeEContaTerceiro: FormControl<FacturaFormRawValue['isEmitidaNomeEContaTerceiro']>;
  isNovo: FormControl<FacturaFormRawValue['isNovo']>;
  isFiscalizado: FormControl<FacturaFormRawValue['isFiscalizado']>;
  signText: FormControl<FacturaFormRawValue['signText']>;
  hash: FormControl<FacturaFormRawValue['hash']>;
  hashShort: FormControl<FacturaFormRawValue['hashShort']>;
  hashControl: FormControl<FacturaFormRawValue['hashControl']>;
  keyVersion: FormControl<FacturaFormRawValue['keyVersion']>;
  utilizador: FormControl<FacturaFormRawValue['utilizador']>;
  motivoAnulacao: FormControl<FacturaFormRawValue['motivoAnulacao']>;
  matricula: FormControl<FacturaFormRawValue['matricula']>;
  referencia: FormControl<FacturaFormRawValue['referencia']>;
  documentoComercial: FormControl<FacturaFormRawValue['documentoComercial']>;
};

export type FacturaFormGroup = FormGroup<FacturaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FacturaFormService {
  createFacturaFormGroup(factura: FacturaFormGroupInput = { id: null }): FacturaFormGroup {
    const facturaRawValue = this.convertFacturaToFacturaRawValue({
      ...this.getFormDefaults(),
      ...factura,
    });
    return new FormGroup<FacturaFormGroupContent>({
      id: new FormControl(
        { value: facturaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      numero: new FormControl(facturaRawValue.numero, {
        validators: [Validators.required, Validators.maxLength(60)],
      }),
      codigoEntrega: new FormControl(facturaRawValue.codigoEntrega),
      dataEmissao: new FormControl(facturaRawValue.dataEmissao, {
        validators: [Validators.required],
      }),
      dataVencimento: new FormControl(facturaRawValue.dataVencimento, {
        validators: [Validators.required],
      }),
      cae: new FormControl(facturaRawValue.cae, {
        validators: [Validators.maxLength(10)],
      }),
      inicioTransporte: new FormControl(facturaRawValue.inicioTransporte, {
        validators: [Validators.required],
      }),
      fimTransporte: new FormControl(facturaRawValue.fimTransporte, {
        validators: [Validators.required],
      }),
      observacaoGeral: new FormControl(facturaRawValue.observacaoGeral),
      observacaoInterna: new FormControl(facturaRawValue.observacaoInterna),
      estado: new FormControl(facturaRawValue.estado, {
        validators: [Validators.required],
      }),
      origem: new FormControl(facturaRawValue.origem, {
        validators: [Validators.required],
      }),
      timestamp: new FormControl(facturaRawValue.timestamp, {
        validators: [Validators.required],
      }),
      isMoedaEntrangeira: new FormControl(facturaRawValue.isMoedaEntrangeira),
      moeda: new FormControl(facturaRawValue.moeda, {
        validators: [Validators.required, Validators.maxLength(12)],
      }),
      cambio: new FormControl(facturaRawValue.cambio, {
        validators: [Validators.min(0)],
      }),
      totalMoedaEntrangeira: new FormControl(facturaRawValue.totalMoedaEntrangeira, {
        validators: [Validators.min(0)],
      }),
      totalIliquido: new FormControl(facturaRawValue.totalIliquido, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalDescontoComercial: new FormControl(facturaRawValue.totalDescontoComercial, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalLiquido: new FormControl(facturaRawValue.totalLiquido, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalImpostoIVA: new FormControl(facturaRawValue.totalImpostoIVA, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalImpostoEspecialConsumo: new FormControl(facturaRawValue.totalImpostoEspecialConsumo, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalDescontoFinanceiro: new FormControl(facturaRawValue.totalDescontoFinanceiro, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalFactura: new FormControl(facturaRawValue.totalFactura, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalImpostoRetencaoFonte: new FormControl(facturaRawValue.totalImpostoRetencaoFonte, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalPagar: new FormControl(facturaRawValue.totalPagar, {
        validators: [Validators.required, Validators.min(0)],
      }),
      debito: new FormControl(facturaRawValue.debito, {
        validators: [Validators.min(0)],
      }),
      credito: new FormControl(facturaRawValue.credito, {
        validators: [Validators.min(0)],
      }),
      totalPago: new FormControl(facturaRawValue.totalPago, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalDiferenca: new FormControl(facturaRawValue.totalDiferenca, {
        validators: [Validators.required],
      }),
      isAutoFacturacao: new FormControl(facturaRawValue.isAutoFacturacao),
      isRegimeCaixa: new FormControl(facturaRawValue.isRegimeCaixa),
      isEmitidaNomeEContaTerceiro: new FormControl(facturaRawValue.isEmitidaNomeEContaTerceiro),
      isNovo: new FormControl(facturaRawValue.isNovo),
      isFiscalizado: new FormControl(facturaRawValue.isFiscalizado),
      signText: new FormControl(facturaRawValue.signText),
      hash: new FormControl(facturaRawValue.hash, {
        validators: [Validators.maxLength(172)],
      }),
      hashShort: new FormControl(facturaRawValue.hashShort),
      hashControl: new FormControl(facturaRawValue.hashControl, {
        validators: [Validators.maxLength(70)],
      }),
      keyVersion: new FormControl(facturaRawValue.keyVersion),
      utilizador: new FormControl(facturaRawValue.utilizador),
      motivoAnulacao: new FormControl(facturaRawValue.motivoAnulacao),
      matricula: new FormControl(facturaRawValue.matricula, {
        validators: [Validators.required],
      }),
      referencia: new FormControl(facturaRawValue.referencia),
      documentoComercial: new FormControl(facturaRawValue.documentoComercial, {
        validators: [Validators.required],
      }),
    });
  }

  getFactura(form: FacturaFormGroup): IFactura | NewFactura {
    return this.convertFacturaRawValueToFactura(form.getRawValue() as FacturaFormRawValue | NewFacturaFormRawValue);
  }

  resetForm(form: FacturaFormGroup, factura: FacturaFormGroupInput): void {
    const facturaRawValue = this.convertFacturaToFacturaRawValue({ ...this.getFormDefaults(), ...factura });
    form.reset(
      {
        ...facturaRawValue,
        id: { value: facturaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FacturaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      inicioTransporte: currentTime,
      fimTransporte: currentTime,
      timestamp: currentTime,
      isMoedaEntrangeira: false,
      isAutoFacturacao: false,
      isRegimeCaixa: false,
      isEmitidaNomeEContaTerceiro: false,
      isNovo: false,
      isFiscalizado: false,
    };
  }

  private convertFacturaRawValueToFactura(rawFactura: FacturaFormRawValue | NewFacturaFormRawValue): IFactura | NewFactura {
    return {
      ...rawFactura,
      inicioTransporte: dayjs(rawFactura.inicioTransporte, DATE_TIME_FORMAT),
      fimTransporte: dayjs(rawFactura.fimTransporte, DATE_TIME_FORMAT),
      timestamp: dayjs(rawFactura.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertFacturaToFacturaRawValue(
    factura: IFactura | (Partial<NewFactura> & FacturaFormDefaults)
  ): FacturaFormRawValue | PartialWithRequiredKeyOf<NewFacturaFormRawValue> {
    return {
      ...factura,
      inicioTransporte: factura.inicioTransporte ? factura.inicioTransporte.format(DATE_TIME_FORMAT) : undefined,
      fimTransporte: factura.fimTransporte ? factura.fimTransporte.format(DATE_TIME_FORMAT) : undefined,
      timestamp: factura.timestamp ? factura.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
