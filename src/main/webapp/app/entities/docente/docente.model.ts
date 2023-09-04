import dayjs from 'dayjs/esm';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { IResponsavelTurno } from 'app/entities/responsavel-turno/responsavel-turno.model';
import { IResponsavelAreaFormacao } from 'app/entities/responsavel-area-formacao/responsavel-area-formacao.model';
import { IResponsavelCurso } from 'app/entities/responsavel-curso/responsavel-curso.model';
import { IResponsavelDisciplina } from 'app/entities/responsavel-disciplina/responsavel-disciplina.model';
import { IResponsavelTurma } from 'app/entities/responsavel-turma/responsavel-turma.model';
import { Sexo } from 'app/entities/enumerations/sexo.model';

export interface IDocente {
  id: number;
  fotografia?: string | null;
  fotografiaContentType?: string | null;
  nome?: string | null;
  nascimento?: dayjs.Dayjs | null;
  nif?: string | null;
  inss?: string | null;
  sexo?: Sexo | null;
  pai?: string | null;
  mae?: string | null;
  documentoNumero?: string | null;
  documentoEmissao?: dayjs.Dayjs | null;
  documentoValidade?: dayjs.Dayjs | null;
  residencia?: string | null;
  dataInicioFuncoes?: dayjs.Dayjs | null;
  telefonePrincipal?: string | null;
  telefoneParente?: string | null;
  email?: string | null;
  numeroAgente?: string | null;
  temAgregacaoPedagogica?: boolean | null;
  observacao?: string | null;
  hash?: string | null;
  timestamp?: dayjs.Dayjs | null;
  nacionalidade?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  naturalidade?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  tipoDocumento?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  grauAcademico?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  categoriaProfissional?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  unidadeOrganica?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  estadoCivil?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  responsavelTurno?: Pick<IResponsavelTurno, 'id'> | null;
  responsavelAreaFormacao?: Pick<IResponsavelAreaFormacao, 'id'> | null;
  responsavelCurso?: Pick<IResponsavelCurso, 'id'> | null;
  responsavelDisciplina?: Pick<IResponsavelDisciplina, 'id'> | null;
  responsavelTurma?: Pick<IResponsavelTurma, 'id'> | null;
}

export type NewDocente = Omit<IDocente, 'id'> & { id: null };
