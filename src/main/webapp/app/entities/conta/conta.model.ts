import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { TipoConta } from 'app/entities/enumerations/tipo-conta.model';

export interface IConta {
  id: number;
  imagem?: string | null;
  imagemContentType?: string | null;
  tipo?: TipoConta | null;
  titulo?: string | null;
  numero?: string | null;
  iban?: string | null;
  titular?: string | null;
  isPadrao?: boolean | null;
  moeda?: Pick<ILookupItem, 'id' | 'descricao'> | null;
}

export type NewConta = Omit<IConta, 'id'> & { id: null };
