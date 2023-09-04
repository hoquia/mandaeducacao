import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';

export interface IImposto {
  id: number;
  descricao?: string | null;
  pais?: string | null;
  taxa?: number | null;
  isRetencao?: boolean | null;
  motivoDescricao?: string | null;
  motivoCodigo?: string | null;
  tipoImposto?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  codigoImposto?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  motivoIsencaoCodigo?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  motivoIsencaoDescricao?: Pick<ILookupItem, 'id' | 'descricao'> | null;
}

export type NewImposto = Omit<IImposto, 'id'> & { id: null };
