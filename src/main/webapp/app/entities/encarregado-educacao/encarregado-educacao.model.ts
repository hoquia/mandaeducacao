import dayjs from 'dayjs/esm';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { Sexo } from 'app/entities/enumerations/sexo.model';

export interface IEncarregadoEducacao {
  id: number;
  fotografia?: string | null;
  fotografiaContentType?: string | null;
  nome?: string | null;
  nascimento?: dayjs.Dayjs | null;
  nif?: string | null;
  sexo?: Sexo | null;
  documentoNumero?: string | null;
  telefonePrincipal?: string | null;
  telefoneAlternativo?: string | null;
  email?: string | null;
  residencia?: string | null;
  enderecoTrabalho?: string | null;
  rendaMensal?: number | null;
  empresaTrabalho?: string | null;
  hash?: string | null;
  grauParentesco?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  tipoDocumento?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  profissao?: Pick<ILookupItem, 'id' | 'descricao'> | null;
}

export type NewEncarregadoEducacao = Omit<IEncarregadoEducacao, 'id'> & { id: null };
