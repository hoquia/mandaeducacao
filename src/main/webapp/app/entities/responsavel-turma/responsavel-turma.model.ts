import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ITurma } from 'app/entities/turma/turma.model';

export interface IResponsavelTurma {
  id: number;
  de?: dayjs.Dayjs | null;
  ate?: dayjs.Dayjs | null;
  descricao?: string | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  turma?: Pick<ITurma, 'id' | 'descricao'> | null;
}

export type NewResponsavelTurma = Omit<IResponsavelTurma, 'id'> & { id: null };
