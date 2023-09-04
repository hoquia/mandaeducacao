export interface ITurno {
  id: number;
  codigo?: string | null;
  nome?: string | null;
}

export type NewTurno = Omit<ITurno, 'id'> & { id: null };
