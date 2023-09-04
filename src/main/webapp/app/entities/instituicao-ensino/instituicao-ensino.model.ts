import dayjs from 'dayjs/esm';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';

export interface IInstituicaoEnsino {
  id: number;
  logotipo?: string | null;
  logotipoContentType?: string | null;
  unidadeOrganica?: string | null;
  nomeFiscal?: string | null;
  numero?: string | null;
  nif?: string | null;
  cae?: string | null;
  niss?: string | null;
  fundador?: string | null;
  fundacao?: dayjs.Dayjs | null;
  dimensao?: string | null;
  slogam?: string | null;
  telefone?: string | null;
  telemovel?: string | null;
  email?: string | null;
  website?: string | null;
  codigoPostal?: string | null;
  enderecoDetalhado?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  descricao?: string | null;
  isComparticipada?: boolean | null;
  termosCompromissos?: string | null;
  categoriaInstituicao?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  unidadePagadora?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  tipoVinculo?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  tipoInstalacao?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  sede?: Pick<IInstituicaoEnsino, 'id' | 'unidadeOrganica'> | null;
}

export type NewInstituicaoEnsino = Omit<IInstituicaoEnsino, 'id'> & { id: null };
