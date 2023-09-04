import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ICurso } from 'app/entities/curso/curso.model';

export interface IResponsavelCurso {
  id: number;
  de?: dayjs.Dayjs | null;
  ate?: dayjs.Dayjs | null;
  descricao?: string | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  curso?: Pick<ICurso, 'id' | 'nome'> | null;
}

export type NewResponsavelCurso = Omit<IResponsavelCurso, 'id'> & { id: null };
