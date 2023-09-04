import dayjs from 'dayjs/esm';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { IDocente } from 'app/entities/docente/docente.model';

export interface IFormacaoDocente {
  id: number;
  instituicaoEnsino?: string | null;
  areaFormacao?: string | null;
  curso?: string | null;
  especialidade?: string | null;
  grau?: string | null;
  inicio?: dayjs.Dayjs | null;
  fim?: dayjs.Dayjs | null;
  anexo?: string | null;
  anexoContentType?: string | null;
  grauAcademico?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  docente?: Pick<IDocente, 'id' | 'nome'> | null;
}

export type NewFormacaoDocente = Omit<IFormacaoDocente, 'id'> & { id: null };
