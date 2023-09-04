import dayjs from 'dayjs/esm';
import { ITurma } from 'app/entities/turma/turma.model';
import { IUser } from 'app/entities/user/user.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { IMatricula } from 'app/entities/matricula/matricula.model';

export interface ITransferenciaTurma {
  id: number;
  timestamp?: dayjs.Dayjs | null;
  de?: Pick<ITurma, 'id' | 'descricao'> | null;
  para?: Pick<ITurma, 'id' | 'descricao'> | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  motivoTransferencia?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  matricula?: Pick<IMatricula, 'id' | 'numeroMatricula'> | null;
}

export type NewTransferenciaTurma = Omit<ITransferenciaTurma, 'id'> & { id: null };
