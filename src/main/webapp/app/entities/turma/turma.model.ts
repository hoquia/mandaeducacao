import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IPlanoCurricular } from 'app/entities/plano-curricular/plano-curricular.model';
import { ITurno } from 'app/entities/turno/turno.model';
import { TipoTurma } from 'app/entities/enumerations/tipo-turma.model';
import { CriterioDescricaoTurma } from 'app/entities/enumerations/criterio-descricao-turma.model';
import { CriterioNumeroChamada } from 'app/entities/enumerations/criterio-numero-chamada.model';

export interface ITurma {
  id: number;
  chaveComposta?: string | null;
  tipoTurma?: TipoTurma | null;
  sala?: number | null;
  descricao?: string | null;
  lotacao?: number | null;
  confirmado?: number | null;
  abertura?: dayjs.Dayjs | null;
  encerramento?: dayjs.Dayjs | null;
  criterioDescricao?: CriterioDescricaoTurma | null;
  criterioOrdenacaoNumero?: CriterioNumeroChamada | null;
  fazInscricaoDepoisMatricula?: boolean | null;
  isDisponivel?: boolean | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  referencia?: Pick<ITurma, 'id' | 'descricao'> | null;
  planoCurricular?: Pick<IPlanoCurricular, 'id' | 'descricao'> | null;
  turno?: Pick<ITurno, 'id' | 'nome'> | null;
}

export type NewTurma = Omit<ITurma, 'id'> & { id: null };
