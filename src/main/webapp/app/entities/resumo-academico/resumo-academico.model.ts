import { IUser } from 'app/entities/user/user.model';
import { ITurma } from 'app/entities/turma/turma.model';
import { IDiscente } from 'app/entities/discente/discente.model';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';

export interface IResumoAcademico {
  id: number;
  temaProjecto?: string | null;
  notaProjecto?: number | null;
  observacao?: string | null;
  localEstagio?: string | null;
  notaEstagio?: number | null;
  mediaFinalDisciplina?: number | null;
  classificacaoFinal?: number | null;
  numeroGrupo?: string | null;
  mesaDefesa?: string | null;
  livroRegistro?: string | null;
  numeroFolha?: string | null;
  chefeSecretariaPedagogica?: string | null;
  subDirectorPedagogico?: string | null;
  directorGeral?: string | null;
  tutorProjecto?: string | null;
  juriMesa?: string | null;
  empresaEstagio?: string | null;
  assinaturaDigital?: string | null;
  assinaturaDigitalContentType?: string | null;
  hash?: string | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  ultimaTurmaMatriculada?: Pick<ITurma, 'id' | 'descricao'> | null;
  discente?: Pick<IDiscente, 'id' | 'nome'> | null;
  situacao?: Pick<IEstadoDisciplinaCurricular, 'id' | 'descricao'> | null;
}

export type NewResumoAcademico = Omit<IResumoAcademico, 'id'> & { id: null };
