import dayjs from 'dayjs/esm';

import { ILongonkeloHistorico, NewLongonkeloHistorico } from './longonkelo-historico.model';

export const sampleWithRequiredData: ILongonkeloHistorico = {
  id: 27325,
  operacao: 'panel client-driven Bedfordshire',
  entidadeNome: 'Saboroso Loan',
  entidadeCodigo: 'Quality',
  payload: '../fake-data/blob/hipster.txt',
  host: 'coherent',
  timestamp: dayjs('2023-09-03T08:26'),
};

export const sampleWithPartialData: ILongonkeloHistorico = {
  id: 96283,
  operacao: 'wireless platforms',
  entidadeNome: 'Suave Nebraska Realigned',
  entidadeCodigo: 'Aço Account',
  payload: '../fake-data/blob/hipster.txt',
  host: 'Savings',
  hash: 'Lights',
  timestamp: dayjs('2023-09-03T07:39'),
};

export const sampleWithFullData: ILongonkeloHistorico = {
  id: 62851,
  operacao: 'Aço architect Algodão',
  entidadeNome: 'Markets Dollar',
  entidadeCodigo: 'program',
  payload: '../fake-data/blob/hipster.txt',
  host: 'Trace drive',
  hash: 'invoice',
  timestamp: dayjs('2023-09-03T02:58'),
};

export const sampleWithNewData: NewLongonkeloHistorico = {
  operacao: 'bandwidth withdrawal',
  entidadeNome: 'Checking',
  entidadeCodigo: 'invoice',
  payload: '../fake-data/blob/hipster.txt',
  host: 'applications',
  timestamp: dayjs('2023-09-03T10:36'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
