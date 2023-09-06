import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { IMeioPagamento } from 'app/entities/meio-pagamento/meio-pagamento.model';
import { IConta } from 'app/entities/conta/conta.model';
import { ITransferenciaSaldo } from 'app/entities/transferencia-saldo/transferencia-saldo.model';
import { EstadoPagamento } from 'app/entities/enumerations/estado-pagamento.model';

export interface ITransacao {
  id: number;
  montante?: number | null;
  data?: dayjs.Dayjs | null;
  referencia?: string | null;
  estado?: EstadoPagamento | null;
  saldo?: number | null;
  anexo?: string | null;
  anexoContentType?: string | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  moeda?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  matricula?: Pick<IMatricula, 'id' | 'discente' | 'numeroMatricula'> | null;
  meioPagamento?: Pick<IMeioPagamento, 'id' | 'nome'> | null;
  conta?: Pick<IConta, 'id' | 'titulo'> | null;
  transferenciaSaldos?: Pick<ITransferenciaSaldo, 'id'>[] | null;
}

export type NewTransacao = Omit<ITransacao, 'id'> & { id: null };
