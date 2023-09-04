import { IPlanoMulta } from 'app/entities/plano-multa/plano-multa.model';
import { IPlanoDesconto } from 'app/entities/plano-desconto/plano-desconto.model';

export interface ICategoriaEmolumento {
  id: number;
  nome?: string | null;
  isServico?: boolean | null;
  cor?: string | null;
  descricao?: string | null;
  isIsentoMulta?: boolean | null;
  isIsentoJuro?: boolean | null;
  planoMulta?: Pick<IPlanoMulta, 'id' | 'descricao'> | null;
  planosDescontos?: Pick<IPlanoDesconto, 'id'>[] | null;
}

export type NewCategoriaEmolumento = Omit<ICategoriaEmolumento, 'id'> & { id: null };
