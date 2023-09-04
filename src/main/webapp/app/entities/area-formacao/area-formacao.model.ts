import { INivelEnsino } from 'app/entities/nivel-ensino/nivel-ensino.model';

export interface IAreaFormacao {
  id: number;
  imagem?: string | null;
  imagemContentType?: string | null;
  codigo?: string | null;
  nome?: string | null;
  descricao?: string | null;
  nivelEnsino?: Pick<INivelEnsino, 'id' | 'nome'> | null;
}

export type NewAreaFormacao = Omit<IAreaFormacao, 'id'> & { id: null };
