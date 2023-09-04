import { IUser } from 'app/entities/user/user.model';
import { IPlanoAula } from 'app/entities/plano-aula/plano-aula.model';
import { IHorario } from 'app/entities/horario/horario.model';
import { EstadoLicao } from 'app/entities/enumerations/estado-licao.model';

export interface ILicao {
  id: number;
  chaveComposta?: string | null;
  numero?: number | null;
  estado?: EstadoLicao | null;
  descricao?: string | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  planoAula?: Pick<IPlanoAula, 'id' | 'assunto'> | null;
  horario?: Pick<IHorario, 'id'> | null;
}

export type NewLicao = Omit<ILicao, 'id'> & { id: null };
