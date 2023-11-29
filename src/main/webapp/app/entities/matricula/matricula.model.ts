import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IPlanoDesconto } from 'app/entities/plano-desconto/plano-desconto.model';
import { ITurma } from 'app/entities/turma/turma.model';
import { IEncarregadoEducacao } from 'app/entities/encarregado-educacao/encarregado-educacao.model';
import { IDiscente } from 'app/entities/discente/discente.model';
import { EstadoAcademico } from 'app/entities/enumerations/estado-academico.model';

export interface IMatricula {
  id: number;
  chaveComposta1?: string | null;
  chaveComposta2?: string | null;
  numeroMatricula?: string | null;
  numeroChamada?: number | null;
  estado?: EstadoAcademico | null;
  timestamp?: dayjs.Dayjs | null;
  descricao?: string | null;
  termosCompromissos?: string | null;
  termosCompromissosContentType?: string | null;
  isAceiteTermosCompromisso?: boolean | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  categoriasMatriculas?: Pick<IPlanoDesconto, 'id' | 'nome'>[] | null;
  turma?: Pick<ITurma, 'id' | 'descricao' | 'turno'> | null;
  responsavelFinanceiro?: Pick<IEncarregadoEducacao, 'id' | 'nome'> | null;
  discente?: Pick<IDiscente, 'id' | 'nome' | 'sexo'> | null;
  referencia?: Pick<IMatricula, 'id'> | null;
}

export type NewMatricula = Omit<IMatricula, 'id'> & { id: null };
