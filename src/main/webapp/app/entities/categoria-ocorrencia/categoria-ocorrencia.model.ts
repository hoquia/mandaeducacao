import { IDocente } from 'app/entities/docente/docente.model';
import { IMedidaDisciplinar } from 'app/entities/medida-disciplinar/medida-disciplinar.model';

export interface ICategoriaOcorrencia {
  id: number;
  codigo?: string | null;
  sansaoDisicplinar?: string | null;
  isNotificaEncaregado?: boolean | null;
  isSendEmail?: boolean | null;
  isSendSms?: boolean | null;
  isSendPush?: boolean | null;
  descricao?: string | null;
  observacao?: string | null;
  encaminhar?: Pick<IDocente, 'id' | 'nome'> | null;
  referencia?: Pick<ICategoriaOcorrencia, 'id'> | null;
  medidaDisciplinar?: Pick<IMedidaDisciplinar, 'id' | 'descricao'> | null;
}

export type NewCategoriaOcorrencia = Omit<ICategoriaOcorrencia, 'id'> & { id: null };
