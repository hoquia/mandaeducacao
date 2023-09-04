import { IUser } from 'app/entities/user/user.model';
import { ITurma } from 'app/entities/turma/turma.model';
import { IPeriodoHorario } from 'app/entities/periodo-horario/periodo-horario.model';
import { IDocente } from 'app/entities/docente/docente.model';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DiaSemana } from 'app/entities/enumerations/dia-semana.model';

export interface IHorario {
  id: number;
  chaveComposta1?: string | null;
  chaveComposta2?: string | null;
  diaSemana?: DiaSemana | null;
  utilizador?: Pick<IUser, 'id' | 'login'> | null;
  turma?: Pick<ITurma, 'id' | 'descricao'> | null;
  referencia?: Pick<IHorario, 'id'> | null;
  periodo?: Pick<IPeriodoHorario, 'id' | 'descricao'> | null;
  docente?: Pick<IDocente, 'id' | 'nome'> | null;
  disciplinaCurricular?: Pick<IDisciplinaCurricular, 'id' | 'descricao'> | null;
}

export type NewHorario = Omit<IHorario, 'id'> & { id: null };
