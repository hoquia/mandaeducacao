import dayjs from 'dayjs/esm';
import { IItemFactura } from 'app/entities/item-factura/item-factura.model';
import { IFactura } from 'app/entities/factura/factura.model';
import { IRecibo } from 'app/entities/recibo/recibo.model';

export interface IAplicacaoRecibo {
  id: number;
  totalFactura?: number | null;
  totalPago?: number | null;
  totalDiferenca?: number | null;
  timestamp?: dayjs.Dayjs | null;
  itemFactura?: Pick<IItemFactura, 'id'> | null;
  factura?: Pick<IFactura, 'id' | 'numero'> | null;
  recibo?: Pick<IRecibo, 'id' | 'numero'> | null;
}

export type NewAplicacaoRecibo = Omit<IAplicacaoRecibo, 'id'> & { id: null };
