import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IDocente } from 'app/entities/docente/docente.model';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { ICategoriaOcorrencia } from 'app/entities/categoria-ocorrencia/categoria-ocorrencia.model';
import { ILicao } from 'app/entities/licao/licao.model';

export interface IOcorrencia {
  id: number;
  uniqueOcorrencia?: string | null;
  descricao?: string | null;
  evidencia?: string | null;
  evidenciaContentType?: string | null;
  hash?: string | null;
  timestamp?: dayjs.Dayjs | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  referencia?: Pick<IOcorrencia, 'id'> | null;
  docente?: Pick<IDocente, 'id' | 'nome'> | null;
  matricula?: Pick<IMatricula, 'id' | 'numeroMatricula'> | null;
  estado?: Pick<ICategoriaOcorrencia, 'id' | 'descricao'> | null;
  licao?: Pick<ILicao, 'id' | 'numero'> | null;
}

export type NewOcorrencia = Omit<IOcorrencia, 'id'> & { id: null };
