import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ITurno } from 'app/entities/turno/turno.model';

export interface IResponsavelTurno {
  id: number;
  de?: dayjs.Dayjs | null;
  ate?: dayjs.Dayjs | null;
  descricao?: string | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  turno?: Pick<ITurno, 'id' | 'nome'> | null;
}

export type NewResponsavelTurno = Omit<IResponsavelTurno, 'id'> & { id: null };
