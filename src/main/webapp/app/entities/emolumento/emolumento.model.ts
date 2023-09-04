import { ICategoriaEmolumento } from 'app/entities/categoria-emolumento/categoria-emolumento.model';
import { IImposto } from 'app/entities/imposto/imposto.model';
import { IPlanoMulta } from 'app/entities/plano-multa/plano-multa.model';

export interface IEmolumento {
  id: number;
  imagem?: string | null;
  imagemContentType?: string | null;
  numero?: string | null;
  nome?: string | null;
  preco?: number | null;
  quantidade?: number | null;
  periodo?: number | null;
  inicioPeriodo?: number | null;
  fimPeriodo?: number | null;
  isObrigatorioMatricula?: boolean | null;
  isObrigatorioConfirmacao?: boolean | null;
  categoria?: Pick<ICategoriaEmolumento, 'id' | 'nome'> | null;
  imposto?: Pick<IImposto, 'id' | 'descricao'> | null;
  referencia?: Pick<IEmolumento, 'id' | 'nome'> | null;
  planoMulta?: Pick<IPlanoMulta, 'id' | 'descricao'> | null;
}

export type NewEmolumento = Omit<IEmolumento, 'id'> & { id: null };
