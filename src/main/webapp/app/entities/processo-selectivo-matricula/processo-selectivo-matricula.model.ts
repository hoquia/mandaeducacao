import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ITurma } from 'app/entities/turma/turma.model';
import { IDiscente } from 'app/entities/discente/discente.model';

export interface IProcessoSelectivoMatricula {
  id: number;
  localTeste?: string | null;
  dataTeste?: dayjs.Dayjs | null;
  notaTeste?: number | null;
  isAdmitido?: boolean | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  turma?: Pick<ITurma, 'id' | 'descricao'> | null;
  discente?: Pick<IDiscente, 'id' | 'nome'> | null;
}

export type NewProcessoSelectivoMatricula = Omit<IProcessoSelectivoMatricula, 'id'> & { id: null };
