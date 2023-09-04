import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IDiscente } from 'app/entities/discente/discente.model';

export interface IHistoricoSaude {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  inicio?: dayjs.Dayjs | null;
  fim?: dayjs.Dayjs | null;
  situacaoPrescricao?: string | null;
  timestamp?: dayjs.Dayjs | null;
  hash?: string | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  discente?: Pick<IDiscente, 'id' | 'nome'> | null;
}

export type NewHistoricoSaude = Omit<IHistoricoSaude, 'id'> & { id: null };
