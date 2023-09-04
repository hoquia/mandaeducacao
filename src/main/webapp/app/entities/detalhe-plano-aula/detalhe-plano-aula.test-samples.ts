import { IDetalhePlanoAula, NewDetalhePlanoAula } from './detalhe-plano-aula.model';

export const sampleWithRequiredData: IDetalhePlanoAula = {
  id: 52020,
  tempoActividade: 17290,
  tituloActividade: 'Automóveis',
  actividadesDocente: '../fake-data/blob/hipster.txt',
  actividadesDiscentes: '../fake-data/blob/hipster.txt',
  avaliacao: '../fake-data/blob/hipster.txt',
  bibliografia: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IDetalhePlanoAula = {
  id: 98985,
  tempoActividade: 69555,
  recursosEnsino: '../fake-data/blob/hipster.txt',
  tituloActividade: 'cinzento process Savings',
  actividadesDocente: '../fake-data/blob/hipster.txt',
  actividadesDiscentes: '../fake-data/blob/hipster.txt',
  avaliacao: '../fake-data/blob/hipster.txt',
  bibliografia: '../fake-data/blob/hipster.txt',
  video: '../fake-data/blob/hipster.png',
  videoContentType: 'unknown',
  audio: '../fake-data/blob/hipster.png',
  audioContentType: 'unknown',
};

export const sampleWithFullData: IDetalhePlanoAula = {
  id: 92359,
  estrategiaAula: '../fake-data/blob/hipster.txt',
  tempoActividade: 66035,
  recursosEnsino: '../fake-data/blob/hipster.txt',
  tituloActividade: 'Salada compressing',
  actividadesDocente: '../fake-data/blob/hipster.txt',
  actividadesDiscentes: '../fake-data/blob/hipster.txt',
  avaliacao: '../fake-data/blob/hipster.txt',
  bibliografia: '../fake-data/blob/hipster.txt',
  observacao: '../fake-data/blob/hipster.txt',
  pdf: '../fake-data/blob/hipster.png',
  pdfContentType: 'unknown',
  video: '../fake-data/blob/hipster.png',
  videoContentType: 'unknown',
  audio: '../fake-data/blob/hipster.png',
  audioContentType: 'unknown',
};

export const sampleWithNewData: NewDetalhePlanoAula = {
  tempoActividade: 46475,
  tituloActividade: 'modular digital',
  actividadesDocente: '../fake-data/blob/hipster.txt',
  actividadesDiscentes: '../fake-data/blob/hipster.txt',
  avaliacao: '../fake-data/blob/hipster.txt',
  bibliografia: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
