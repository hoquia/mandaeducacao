import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { CategoriaClassificacao } from 'app/entities/enumerations/categoria-classificacao.model';

export interface IEstadoDisciplinaCurricular {
  id: number;
  uniqueSituacaoDisciplina?: string | null;
  classificacao?: CategoriaClassificacao | null;
  codigo?: string | null;
  descricao?: string | null;
  cor?: string | null;
  valor?: number | null;
  disciplinasCurriculars?: Pick<IDisciplinaCurricular, 'id'>[] | null;
  referencia?: Pick<IEstadoDisciplinaCurricular, 'id'> | null;
}

export type NewEstadoDisciplinaCurricular = Omit<IEstadoDisciplinaCurricular, 'id'> & { id: null };
