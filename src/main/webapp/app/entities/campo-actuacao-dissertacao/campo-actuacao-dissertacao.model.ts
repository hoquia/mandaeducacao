import { ICurso } from 'app/entities/curso/curso.model';

export interface ICampoActuacaoDissertacao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  isActivo?: boolean | null;
  cursos?: Pick<ICurso, 'id' | 'nome'>[] | null;
}

export type NewCampoActuacaoDissertacao = Omit<ICampoActuacaoDissertacao, 'id'> & { id: null };
