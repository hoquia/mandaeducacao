import { IPlanoAula } from 'app/entities/plano-aula/plano-aula.model';

export interface IDetalhePlanoAula {
  id: number;
  estrategiaAula?: string | null;
  tempoActividade?: number | null;
  recursosEnsino?: string | null;
  tituloActividade?: string | null;
  actividadesDocente?: string | null;
  actividadesDiscentes?: string | null;
  avaliacao?: string | null;
  bibliografia?: string | null;
  observacao?: string | null;
  pdf?: string | null;
  pdfContentType?: string | null;
  video?: string | null;
  videoContentType?: string | null;
  audio?: string | null;
  audioContentType?: string | null;
  planoAula?: Pick<IPlanoAula, 'id' | 'assunto'> | null;
}

export type NewDetalhePlanoAula = Omit<IDetalhePlanoAula, 'id'> & { id: null };
