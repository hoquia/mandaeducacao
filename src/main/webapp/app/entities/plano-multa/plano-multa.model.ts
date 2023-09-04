import { IUser } from 'app/entities/user/user.model';
import { MetodoAplicacaoMulta } from 'app/entities/enumerations/metodo-aplicacao-multa.model';

export interface IPlanoMulta {
  id: number;
  descricao?: string | null;
  diaAplicacaoMulta?: number | null;
  metodoAplicacaoMulta?: MetodoAplicacaoMulta | null;
  taxaMulta?: number | null;
  isTaxaMultaPercentual?: boolean | null;
  diaAplicacaoJuro?: number | null;
  metodoAplicacaoJuro?: MetodoAplicacaoMulta | null;
  taxaJuro?: number | null;
  isTaxaJuroPercentual?: boolean | null;
  aumentarJuroEmDias?: number | null;
  isAtivo?: boolean | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewPlanoMulta = Omit<IPlanoMulta, 'id'> & { id: null };
