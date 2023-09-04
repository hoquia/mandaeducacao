import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ITurma } from 'app/entities/turma/turma.model';
import { IDocente } from 'app/entities/docente/docente.model';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';
import { Comporamento } from 'app/entities/enumerations/comporamento.model';

export interface INotasPeriodicaDisciplina {
  id: number;
  chaveComposta?: string | null;
  periodoLancamento?: number | null;
  nota1?: number | null;
  nota2?: number | null;
  nota3?: number | null;
  media?: number | null;
  faltaJusticada?: number | null;
  faltaInjustificada?: number | null;
  comportamento?: Comporamento | null;
  hash?: string | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  turma?: Pick<ITurma, 'id' | 'descricao'> | null;
  docente?: Pick<IDocente, 'id' | 'nome'> | null;
  disciplinaCurricular?: Pick<IDisciplinaCurricular, 'id' | 'descricao'> | null;
  matricula?: Pick<IMatricula, 'id' | 'numeroMatricula'> | null;
  estado?: Pick<IEstadoDisciplinaCurricular, 'id' | 'descricao'> | null;
}

export type NewNotasPeriodicaDisciplina = Omit<INotasPeriodicaDisciplina, 'id'> & { id: null };
