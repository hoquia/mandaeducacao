import dayjs from 'dayjs/esm';

import { IInstituicaoEnsino, NewInstituicaoEnsino } from './instituicao-ensino.model';

export const sampleWithRequiredData: IInstituicaoEnsino = {
  id: 32451,
  logotipo: '../fake-data/blob/hipster.png',
  logotipoContentType: 'unknown',
  unidadeOrganica: 'up up quantifying',
  numero: 'withdrawal Regional',
  telefone: 'Loaf Fresco Dynamic',
  email: 'Francisca93@hotmail.com',
  enderecoDetalhado: 'Carolina Betão Pennsylvania',
};

export const sampleWithPartialData: IInstituicaoEnsino = {
  id: 87006,
  logotipo: '../fake-data/blob/hipster.png',
  logotipoContentType: 'unknown',
  unidadeOrganica: 'Fresco',
  numero: 'Casa Salsichas',
  niss: 'Forward',
  fundador: 'Colorado',
  fundacao: dayjs('2023-09-03'),
  dimensao: 'neural Iraque',
  slogam: 'homogeneous',
  telefone: 'synergies driver',
  email: 'Antnio_Antunes79@gmail.com',
  enderecoDetalhado: 'Bebé support compressing',
  descricao: '../fake-data/blob/hipster.txt',
  isComparticipada: false,
};

export const sampleWithFullData: IInstituicaoEnsino = {
  id: 74706,
  logotipo: '../fake-data/blob/hipster.png',
  logotipoContentType: 'unknown',
  unidadeOrganica: 'Future',
  nomeFiscal: 'state context-sensitive salmão',
  numero: 'Fords',
  nif: 'lima',
  cae: 'Salada relationships customized',
  niss: 'Camisa',
  fundador: 'Pizza Fantástico',
  fundacao: dayjs('2023-09-03'),
  dimensao: 'Camisa Ergonómico',
  slogam: 'Yen aggregate',
  telefone: 'Web Investment cross-platform',
  telemovel: 'withdrawal',
  email: 'Olvia.Correia@hotmail.com',
  website: 'Field',
  codigoPostal: 'drive impa',
  enderecoDetalhado: 'Cross-platform Ringgit sensor',
  latitude: 24586,
  longitude: 83940,
  descricao: '../fake-data/blob/hipster.txt',
  isComparticipada: true,
  termosCompromissos: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewInstituicaoEnsino = {
  logotipo: '../fake-data/blob/hipster.png',
  logotipoContentType: 'unknown',
  unidadeOrganica: 'transmitter SMTP target',
  numero: 'override solid Fresco',
  telefone: 'Genérico Associate Atum',
  email: 'Lorena.Vicente38@live.com',
  enderecoDetalhado: 'Tennessee Pequeno withdrawal',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
