import { ITurno } from 'app/entities/turno/turno.model';

export interface IPeriodoHorario {
  id: number;
  descricao?: string | null;
  tempo?: number | null;
  inicio?: string | null;
  fim?: string | null;
  turno?: Pick<ITurno, 'id' | 'nome'> | null;
}

export type NewPeriodoHorario = Omit<IPeriodoHorario, 'id'> & { id: null };
