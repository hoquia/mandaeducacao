import { IAnoLectivo } from 'app/entities/ano-lectivo/ano-lectivo.model';
import { IClasse } from 'app/entities/classe/classe.model';
import { UnidadeDuracao } from 'app/entities/enumerations/unidade-duracao.model';

export interface INivelEnsino {
  id: number;
  codigo?: string | null;
  nome?: string | null;
  descricao?: string | null;
  idadeMinima?: number | null;
  idadeMaxima?: number | null;
  duracao?: number | null;
  unidadeDuracao?: UnidadeDuracao | null;
  classeInicial?: number | null;
  classeFinal?: number | null;
  classeExame?: number | null;
  totalDisciplina?: number | null;
  responsavelTurno?: string | null;
  responsavelAreaFormacao?: string | null;
  responsavelCurso?: string | null;
  responsavelDisciplina?: string | null;
  responsavelTurma?: string | null;
  responsavelGeral?: string | null;
  responsavelPedagogico?: string | null;
  responsavelAdministrativo?: string | null;
  responsavelSecretariaGeral?: string | null;
  responsavelSecretariaPedagogico?: string | null;
  descricaoDocente?: string | null;
  descricaoDiscente?: string | null;
  referencia?: Pick<INivelEnsino, 'id' | 'nome'> | null;
  anoLectivos?: Pick<IAnoLectivo, 'id'>[] | null;
  classes?: Pick<IClasse, 'id'>[] | null;
}

export type NewNivelEnsino = Omit<INivelEnsino, 'id'> & { id: null };
