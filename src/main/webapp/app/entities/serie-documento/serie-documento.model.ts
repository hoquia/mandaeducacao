import { IDocumentoComercial } from 'app/entities/documento-comercial/documento-comercial.model';

export interface ISerieDocumento {
  id: number;
  anoFiscal?: number | null;
  versao?: number | null;
  serie?: string | null;
  isAtivo?: boolean | null;
  isPadrao?: boolean | null;
  tipoDocumento?: Pick<IDocumentoComercial, 'id' | 'siglaFiscal'> | null;
}

export type NewSerieDocumento = Omit<ISerieDocumento, 'id'> & { id: null };
