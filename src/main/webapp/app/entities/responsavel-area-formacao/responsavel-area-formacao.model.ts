import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';

export interface IResponsavelAreaFormacao {
  id: number;
  de?: dayjs.Dayjs | null;
  ate?: dayjs.Dayjs | null;
  descricao?: string | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  areaFormacao?: Pick<IAreaFormacao, 'id' | 'nome'> | null;
}

export type NewResponsavelAreaFormacao = Omit<IResponsavelAreaFormacao, 'id'> & { id: null };
