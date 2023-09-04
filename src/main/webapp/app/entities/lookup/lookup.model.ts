export interface ILookup {
  id: number;
  codigo?: string | null;
  nome?: string | null;
  descricao?: string | null;
  isSistema?: boolean | null;
  isModificavel?: boolean | null;
}

export type NewLookup = Omit<ILookup, 'id'> & { id: null };
