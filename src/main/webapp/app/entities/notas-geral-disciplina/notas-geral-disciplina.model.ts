import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IDocente } from 'app/entities/docente/docente.model';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';

export interface INotasGeralDisciplina {
  id: number;
  chaveComposta?: string | null;
  periodoLancamento?: number | null;
  media1?: number | null;
  media2?: number | null;
  media3?: number | null;
  exame?: number | null;
  recurso?: number | null;
  exameEspecial?: number | null;
  notaConselho?: number | null;
  mediaFinalDisciplina?: number | null;
  timestamp?: dayjs.Dayjs | null;
  hash?: string | null;
  faltaJusticada?: number | null;
  faltaInjustificada?: number | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  docente?: Pick<IDocente, 'id' | 'nome'> | null;
  disciplinaCurricular?: Pick<IDisciplinaCurricular, 'id' | 'descricao'> | null;
  matricula?: Pick<IMatricula, 'id' | 'numeroMatricula'> | null;
  estado?: Pick<IEstadoDisciplinaCurricular, 'id' | 'descricao'> | null;
}

export type NewNotasGeralDisciplina = Omit<INotasGeralDisciplina, 'id'> & { id: null };
