import dayjs from 'dayjs/esm';

import { IOcorrencia, NewOcorrencia } from './ocorrencia.model';

export const sampleWithRequiredData: IOcorrencia = {
  id: 78018,
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T09:15'),
};

export const sampleWithPartialData: IOcorrencia = {
  id: 78556,
  descricao: '../fake-data/blob/hipster.txt',
  hash: 'Developer non-volatile cinzento',
  timestamp: dayjs('2023-09-03T09:58'),
};

export const sampleWithFullData: IOcorrencia = {
  id: 31933,
  uniqueOcorrencia: 'withdrawal Investment asymmetric',
  descricao: '../fake-data/blob/hipster.txt',
  evidencia: '../fake-data/blob/hipster.png',
  evidenciaContentType: 'unknown',
  hash: 'multi-byte',
  timestamp: dayjs('2023-09-03T16:23'),
};

export const sampleWithNewData: NewOcorrencia = {
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T14:05'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
