export interface IDisciplina {
  id: number;
  codigo?: string | null;
  nome?: string | null;
  descricao?: string | null;
}

export type NewDisciplina = Omit<IDisciplina, 'id'> & { id: null };
