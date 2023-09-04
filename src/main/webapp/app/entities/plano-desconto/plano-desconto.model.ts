import { ICategoriaEmolumento } from 'app/entities/categoria-emolumento/categoria-emolumento.model';
import { IMatricula } from 'app/entities/matricula/matricula.model';

export interface IPlanoDesconto {
  id: number;
  codigo?: string | null;
  nome?: string | null;
  isIsentoMulta?: boolean | null;
  isIsentoJuro?: boolean | null;
  desconto?: number | null;
  categoriasEmolumentos?: Pick<ICategoriaEmolumento, 'id' | 'nome'>[] | null;
  matriculas?: Pick<IMatricula, 'id'>[] | null;
}

export type NewPlanoDesconto = Omit<IPlanoDesconto, 'id'> & { id: null };
