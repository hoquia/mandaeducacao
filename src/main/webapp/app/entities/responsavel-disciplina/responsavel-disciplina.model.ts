import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IDisciplina } from 'app/entities/disciplina/disciplina.model';

export interface IResponsavelDisciplina {
  id: number;
  de?: dayjs.Dayjs | null;
  ate?: dayjs.Dayjs | null;
  descricao?: string | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  disciplina?: Pick<IDisciplina, 'id' | 'nome'> | null;
}

export type NewResponsavelDisciplina = Omit<IResponsavelDisciplina, 'id'> & { id: null };
