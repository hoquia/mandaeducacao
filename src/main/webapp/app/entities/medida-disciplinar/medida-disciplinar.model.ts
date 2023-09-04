import { UnidadeDuracao } from 'app/entities/enumerations/unidade-duracao.model';
import { Suspensao } from 'app/entities/enumerations/suspensao.model';

export interface IMedidaDisciplinar {
  id: number;
  descricao?: string | null;
  periodo?: UnidadeDuracao | null;
  suspensao?: Suspensao | null;
  tempo?: number | null;
}

export type NewMedidaDisciplinar = Omit<IMedidaDisciplinar, 'id'> & { id: null };
