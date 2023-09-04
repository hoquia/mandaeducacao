import { ILookup } from 'app/entities/lookup/lookup.model';

export interface ILookupItem {
  id: number;
  codigo?: string | null;
  ordem?: number | null;
  isSistema?: boolean | null;
  descricao?: string | null;
  lookup?: Pick<ILookup, 'id' | 'nome'> | null;
}

export type NewLookupItem = Omit<ILookupItem, 'id'> & { id: null };
