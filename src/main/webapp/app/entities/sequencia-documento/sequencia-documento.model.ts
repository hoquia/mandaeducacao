import dayjs from 'dayjs/esm';
import { ISerieDocumento } from 'app/entities/serie-documento/serie-documento.model';

export interface ISequenciaDocumento {
  id: number;
  sequencia?: number | null;
  data?: dayjs.Dayjs | null;
  hash?: string | null;
  timestamp?: dayjs.Dayjs | null;
  serie?: Pick<ISerieDocumento, 'id' | 'serie'> | null;
}

export type NewSequenciaDocumento = Omit<ISequenciaDocumento, 'id'> & { id: null };
