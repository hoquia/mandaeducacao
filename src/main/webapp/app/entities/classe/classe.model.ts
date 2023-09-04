import { INivelEnsino } from 'app/entities/nivel-ensino/nivel-ensino.model';
import { IPeriodoLancamentoNota } from 'app/entities/periodo-lancamento-nota/periodo-lancamento-nota.model';

export interface IClasse {
  id: number;
  descricao?: string | null;
  nivesEnsinos?: Pick<INivelEnsino, 'id' | 'nome'>[] | null;
  periodosLancamentoNotas?: Pick<IPeriodoLancamentoNota, 'id'>[] | null;
}

export type NewClasse = Omit<IClasse, 'id'> & { id: null };
