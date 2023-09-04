import dayjs from 'dayjs/esm';
import { IDiscente } from 'app/entities/discente/discente.model';
import { CategoriaAnexo } from 'app/entities/enumerations/categoria-anexo.model';

export interface IAnexoDiscente {
  id: number;
  categoria?: CategoriaAnexo | null;
  anexo?: string | null;
  anexoContentType?: string | null;
  descricao?: string | null;
  validade?: dayjs.Dayjs | null;
  timestamp?: dayjs.Dayjs | null;
  discente?: Pick<IDiscente, 'id' | 'nome'> | null;
}

export type NewAnexoDiscente = Omit<IAnexoDiscente, 'id'> & { id: null };
