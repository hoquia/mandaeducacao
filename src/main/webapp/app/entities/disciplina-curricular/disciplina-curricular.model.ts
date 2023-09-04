import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { IPlanoCurricular } from 'app/entities/plano-curricular/plano-curricular.model';
import { IDisciplina } from 'app/entities/disciplina/disciplina.model';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';

export interface IDisciplinaCurricular {
  id: number;
  uniqueDisciplinaCurricular?: string | null;
  descricao?: string | null;
  cargaSemanal?: number | null;
  isTerminal?: boolean | null;
  mediaParaExame?: number | null;
  mediaParaRecurso?: number | null;
  mediaParaExameEspecial?: number | null;
  mediaParaDespensar?: number | null;
  componente?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  regime?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  planosCurriculars?: Pick<IPlanoCurricular, 'id' | 'descricao'>[] | null;
  disciplina?: Pick<IDisciplina, 'id' | 'nome'> | null;
  referencia?: Pick<IDisciplinaCurricular, 'id' | 'descricao'> | null;
  estados?: Pick<IEstadoDisciplinaCurricular, 'id'>[] | null;
}

export type NewDisciplinaCurricular = Omit<IDisciplinaCurricular, 'id'> & { id: null };
