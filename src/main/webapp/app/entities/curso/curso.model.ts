import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { ICampoActuacaoDissertacao } from 'app/entities/campo-actuacao-dissertacao/campo-actuacao-dissertacao.model';

export interface ICurso {
  id: number;
  imagem?: string | null;
  imagemContentType?: string | null;
  codigo?: string | null;
  nome?: string | null;
  descricao?: string | null;
  areaFormacao?: Pick<IAreaFormacao, 'id' | 'nome'> | null;
  camposActuacaos?: Pick<ICampoActuacaoDissertacao, 'id'>[] | null;
}

export type NewCurso = Omit<ICurso, 'id'> & { id: null };
