import { IUser } from 'app/entities/user/user.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { ITurma } from 'app/entities/turma/turma.model';
import { IDocente } from 'app/entities/docente/docente.model';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { TipoAula } from 'app/entities/enumerations/tipo-aula.model';
import { EstadoLicao } from 'app/entities/enumerations/estado-licao.model';

export interface IPlanoAula {
  id: number;
  tipoAula?: TipoAula | null;
  semanaLectiva?: number | null;
  perfilEntrada?: string | null;
  perfilSaida?: string | null;
  assunto?: string | null;
  objectivoGeral?: string | null;
  objectivosEspecificos?: string | null;
  tempoTotalLicao?: number | null;
  estado?: EstadoLicao | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  unidadeTematica?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  subUnidadeTematica?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  turma?: Pick<ITurma, 'id' | 'descricao'> | null;
  docente?: Pick<IDocente, 'id' | 'nome'> | null;
  disciplinaCurricular?: Pick<IDisciplinaCurricular, 'id' | 'descricao'> | null;
}

export type NewPlanoAula = Omit<IPlanoAula, 'id'> & { id: null };
