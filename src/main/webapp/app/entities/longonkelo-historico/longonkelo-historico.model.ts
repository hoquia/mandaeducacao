import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';

export interface ILongonkeloHistorico {
  id: number;
  operacao?: string | null;
  entidadeNome?: string | null;
  entidadeCodigo?: string | null;
  payload?: string | null;
  host?: string | null;
  hash?: string | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewLongonkeloHistorico = Omit<ILongonkeloHistorico, 'id'> & { id: null };
