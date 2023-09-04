export interface INaturezaTrabalho {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  isActivo?: boolean | null;
}

export type NewNaturezaTrabalho = Omit<INaturezaTrabalho, 'id'> & { id: null };
