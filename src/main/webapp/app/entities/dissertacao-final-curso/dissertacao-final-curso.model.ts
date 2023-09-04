import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ITurma } from 'app/entities/turma/turma.model';
import { IDocente } from 'app/entities/docente/docente.model';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { IDiscente } from 'app/entities/discente/discente.model';
import { IEstadoDissertacao } from 'app/entities/estado-dissertacao/estado-dissertacao.model';
import { INaturezaTrabalho } from 'app/entities/natureza-trabalho/natureza-trabalho.model';

export interface IDissertacaoFinalCurso {
  id: number;
  numero?: string | null;
  timestamp?: dayjs.Dayjs | null;
  data?: dayjs.Dayjs | null;
  tema?: string | null;
  objectivoGeral?: string | null;
  objectivosEspecificos?: string | null;
  introducao?: string | null;
  resumo?: string | null;
  problema?: string | null;
  resultado?: string | null;
  metodologia?: string | null;
  referenciasBibliograficas?: string | null;
  observacaoOrientador?: string | null;
  observacaoAreaFormacao?: string | null;
  observacaoInstituicao?: string | null;
  hash?: string | null;
  termosCompromissos?: string | null;
  isAceiteTermosCompromisso?: boolean | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  turma?: Pick<ITurma, 'id' | 'descricao'> | null;
  orientador?: Pick<IDocente, 'id' | 'nome'> | null;
  especialidade?: Pick<IAreaFormacao, 'id' | 'nome'> | null;
  discente?: Pick<IDiscente, 'id' | 'nome'> | null;
  estado?: Pick<IEstadoDissertacao, 'id' | 'nome'> | null;
  natureza?: Pick<INaturezaTrabalho, 'id' | 'nome'> | null;
}

export type NewDissertacaoFinalCurso = Omit<IDissertacaoFinalCurso, 'id'> & { id: null };
