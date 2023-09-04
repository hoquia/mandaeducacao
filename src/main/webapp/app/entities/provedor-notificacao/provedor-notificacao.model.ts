import { IInstituicaoEnsino } from 'app/entities/instituicao-ensino/instituicao-ensino.model';

export interface IProvedorNotificacao {
  id: number;
  telefone?: string | null;
  email?: string | null;
  link?: string | null;
  token?: string | null;
  username?: string | null;
  password?: string | null;
  hash?: string | null;
  isPadrao?: boolean | null;
  instituicao?: Pick<IInstituicaoEnsino, 'id' | 'unidadeOrganica'> | null;
}

export type NewProvedorNotificacao = Omit<IProvedorNotificacao, 'id'> & { id: null };
