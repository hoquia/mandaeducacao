import dayjs from 'dayjs/esm';
import { IFactura } from 'app/entities/factura/factura.model';
import { IEmolumento } from 'app/entities/emolumento/emolumento.model';
import { EstadoItemFactura } from 'app/entities/enumerations/estado-item-factura.model';

export interface IItemFactura {
  id: number;
  quantidade?: number | null;
  precoUnitario?: number | null;
  desconto?: number | null;
  multa?: number | null;
  juro?: number | null;
  precoTotal?: number | null;
  estado?: EstadoItemFactura | null;
  taxType?: string | null;
  taxCountryRegion?: string | null;
  taxCode?: string | null;
  taxPercentage?: number | null;
  taxExemptionReason?: string | null;
  taxExemptionCode?: string | null;
  emissao?: dayjs.Dayjs | null;
  expiracao?: dayjs.Dayjs | null;
  periodo?: number | null;
  descricao?: string | null;
  factura?: Pick<IFactura, 'id' | 'numero'> | null;
  emolumento?: Pick<IEmolumento, 'id' | 'nome'> | null;
}

export type NewItemFactura = Omit<IItemFactura, 'id'> & { id: null };
