import { UnidadeDuracao } from 'app/entities/enumerations/unidade-duracao.model';

import { INivelEnsino, NewNivelEnsino } from './nivel-ensino.model';

export const sampleWithRequiredData: INivelEnsino = {
  id: 50362,
  codigo: 'program Tanzânia index',
  nome: 'index',
  unidadeDuracao: UnidadeDuracao['ANO'],
};

export const sampleWithPartialData: INivelEnsino = {
  id: 43787,
  codigo: 'Prático Suave',
  nome: 'Plástico',
  descricao: '../fake-data/blob/hipster.txt',
  unidadeDuracao: UnidadeDuracao['AULA'],
  totalDisciplina: 7118,
  responsavelTurno: 'Anguila azul',
  responsavelAreaFormacao: 'Supervisor',
  responsavelCurso: 'Licenciado solid',
  responsavelDisciplina: 'Loan Executive Lustroso',
  responsavelPedagogico: 'Web Gold payment',
  descricaoDocente: 'mobile',
};

export const sampleWithFullData: INivelEnsino = {
  id: 97734,
  codigo: 'Polarised artificial',
  nome: 'Specialist Designer',
  descricao: '../fake-data/blob/hipster.txt',
  idadeMinima: 33528,
  idadeMaxima: 67230,
  duracao: 49773,
  unidadeDuracao: UnidadeDuracao['MES'],
  classeInicial: 98806,
  classeFinal: 91711,
  classeExame: 78338,
  totalDisciplina: 41143,
  responsavelTurno: 'Analyst',
  responsavelAreaFormacao: 'web-enabled firmware',
  responsavelCurso: 'implementation',
  responsavelDisciplina: 'out-of-the-box Mercearia deposit',
  responsavelTurma: 'SAS New',
  responsavelGeral: 'SSL North Roupas',
  responsavelPedagogico: 'loyalty',
  responsavelAdministrativo: 'Georgia',
  responsavelSecretariaGeral: 'Cadeira input JBOD',
  responsavelSecretariaPedagogico: 'Groves Plástico bandwidth',
  descricaoDocente: 'Organized Account',
  descricaoDiscente: 'Artesanal ADP',
};

export const sampleWithNewData: NewNivelEnsino = {
  codigo: 'Public-key Loan',
  nome: 'North Data',
  unidadeDuracao: UnidadeDuracao['HORA'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
