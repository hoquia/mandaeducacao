import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IClasse } from 'app/entities/classe/classe.model';
import { TipoAvaliacao } from 'app/entities/enumerations/tipo-avaliacao.model';

export interface IPeriodoLancamentoNota {
  id: number;
  tipoAvaliacao?: TipoAvaliacao | null;
  de?: dayjs.Dayjs | null;
  ate?: dayjs.Dayjs | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  classes?: Pick<IClasse, 'id' | 'descricao'>[] | null;
}

export type NewPeriodoLancamentoNota = Omit<IPeriodoLancamentoNota, 'id'> & { id: null };
