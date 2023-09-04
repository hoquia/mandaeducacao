import { IEstadoDissertacao, NewEstadoDissertacao } from './estado-dissertacao.model';

export const sampleWithRequiredData: IEstadoDissertacao = {
  id: 11188,
  codigo: 'withdrawal',
  nome: 'Algodão',
};

export const sampleWithPartialData: IEstadoDissertacao = {
  id: 3116,
  codigo: 'Ports Accountability Desporto',
  nome: 'Buckinghamshire Marca',
};

export const sampleWithFullData: IEstadoDissertacao = {
  id: 39398,
  codigo: 'solution',
  nome: 'redundant utilisation',
  etapa: 41631,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewEstadoDissertacao = {
  codigo: 'prata Bedfordshire',
  nome: 'firmware laranja seize',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
