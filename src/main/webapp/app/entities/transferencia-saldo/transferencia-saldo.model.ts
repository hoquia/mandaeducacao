import dayjs from 'dayjs/esm';
import { IDiscente } from 'app/entities/discente/discente.model';
import { IUser } from 'app/entities/user/user.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { ITransacao } from 'app/entities/transacao/transacao.model';

export interface ITransferenciaSaldo {
  id: number;
  montante?: number | null;
  isMesmaConta?: boolean | null;
  descricao?: string | null;
  timestamp?: dayjs.Dayjs | null;
  de?: Pick<IDiscente, 'id' | 'nome'> | null;
  para?: Pick<IDiscente, 'id' | 'nome'> | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  motivoTransferencia?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  transacoes?: Pick<ITransacao, 'id' | 'referencia'>[] | null;
}

export type NewTransferenciaSaldo = Omit<ITransferenciaSaldo, 'id'> & { id: null };
