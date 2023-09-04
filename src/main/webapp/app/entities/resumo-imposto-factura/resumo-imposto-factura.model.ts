import { IFactura } from 'app/entities/factura/factura.model';

export interface IResumoImpostoFactura {
  id: number;
  isRetencao?: boolean | null;
  descricao?: string | null;
  tipo?: string | null;
  taxa?: number | null;
  incidencia?: number | null;
  montante?: number | null;
  factura?: Pick<IFactura, 'id' | 'numero'> | null;
}

export type NewResumoImpostoFactura = Omit<IResumoImpostoFactura, 'id'> & { id: null };
