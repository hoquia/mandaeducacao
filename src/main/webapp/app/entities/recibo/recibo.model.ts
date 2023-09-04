import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { IDocumentoComercial } from 'app/entities/documento-comercial/documento-comercial.model';
import { ITransacao } from 'app/entities/transacao/transacao.model';
import { EstadoDocumentoComercial } from 'app/entities/enumerations/estado-documento-comercial.model';

export interface IRecibo {
  id: number;
  data?: dayjs.Dayjs | null;
  vencimento?: dayjs.Dayjs | null;
  numero?: string | null;
  totalSemImposto?: number | null;
  totalComImposto?: number | null;
  totalDescontoComercial?: number | null;
  totalDescontoFinanceiro?: number | null;
  totalIVA?: number | null;
  totalRetencao?: number | null;
  totalJuro?: number | null;
  cambio?: number | null;
  totalMoedaEstrangeira?: number | null;
  totalPagar?: number | null;
  totalPago?: number | null;
  totalFalta?: number | null;
  totalTroco?: number | null;
  isNovo?: boolean | null;
  timestamp?: dayjs.Dayjs | null;
  descricao?: string | null;
  debito?: number | null;
  credito?: number | null;
  isFiscalizado?: boolean | null;
  signText?: string | null;
  hash?: string | null;
  hashShort?: string | null;
  hashControl?: string | null;
  keyVersion?: number | null;
  estado?: EstadoDocumentoComercial | null;
  origem?: string | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  matricula?: Pick<IMatricula, 'id' | 'numeroMatricula'> | null;
  documentoComercial?: Pick<IDocumentoComercial, 'id' | 'siglaInterna'> | null;
  transacao?: Pick<ITransacao, 'id' | 'referencia'> | null;
}

export type NewRecibo = Omit<IRecibo, 'id'> & { id: null };
