import { IUser } from 'app/entities/user/user.model';
import { IEmolumento } from 'app/entities/emolumento/emolumento.model';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { ICurso } from 'app/entities/curso/curso.model';
import { IClasse } from 'app/entities/classe/classe.model';
import { ITurno } from 'app/entities/turno/turno.model';
import { IPlanoMulta } from 'app/entities/plano-multa/plano-multa.model';

export interface IPrecoEmolumento {
  id: number;
  preco?: number | null;
  isEspecificoCurso?: boolean | null;
  isEspecificoAreaFormacao?: boolean | null;
  isEspecificoClasse?: boolean | null;
  isEspecificoTurno?: boolean | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  emolumento?: Pick<IEmolumento, 'id' | 'nome'> | null;
  areaFormacao?: Pick<IAreaFormacao, 'id' | 'nome'> | null;
  curso?: Pick<ICurso, 'id' | 'nome'> | null;
  classe?: Pick<IClasse, 'id' | 'descricao'> | null;
  turno?: Pick<ITurno, 'id' | 'nome'> | null;
  planoMulta?: Pick<IPlanoMulta, 'id' | 'descricao'> | null;
}

export type NewPrecoEmolumento = Omit<IPrecoEmolumento, 'id'> & { id: null };
