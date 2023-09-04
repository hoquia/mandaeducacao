export interface IEstadoDissertacao {
  id: number;
  codigo?: string | null;
  nome?: string | null;
  etapa?: number | null;
  descricao?: string | null;
}

export type NewEstadoDissertacao = Omit<IEstadoDissertacao, 'id'> & { id: null };
