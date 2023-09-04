import { IUser } from 'app/entities/user/user.model';
import { IClasse } from 'app/entities/classe/classe.model';
import { ICurso } from 'app/entities/curso/curso.model';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';

export interface IPlanoCurricular {
  id: number;
  descricao?: string | null;
  formulaClassificacaoFinal?: string | null;
  numeroDisciplinaAprova?: number | null;
  numeroDisciplinaReprova?: number | null;
  numeroDisciplinaRecurso?: number | null;
  numeroDisciplinaExame?: number | null;
  numeroDisciplinaExameEspecial?: number | null;
  numeroFaltaReprova?: number | null;
  pesoMedia1?: number | null;
  pesoMedia2?: number | null;
  pesoMedia3?: number | null;
  pesoRecurso?: number | null;
  pesoExame?: number | null;
  pesoExameEspecial?: number | null;
  pesoNotaCoselho?: number | null;
  siglaProva1?: string | null;
  siglaProva2?: string | null;
  siglaProva3?: string | null;
  siglaMedia1?: string | null;
  siglaMedia2?: string | null;
  siglaMedia3?: string | null;
  formulaMedia?: string | null;
  formulaDispensa?: string | null;
  formulaExame?: string | null;
  formulaRecurso?: string | null;
  formulaExameEspecial?: string | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  classe?: Pick<IClasse, 'id' | 'descricao'> | null;
  curso?: Pick<ICurso, 'id' | 'nome'> | null;
  disciplinasCurriculars?: Pick<IDisciplinaCurricular, 'id'>[] | null;
}

export type NewPlanoCurricular = Omit<IPlanoCurricular, 'id'> & { id: null };
