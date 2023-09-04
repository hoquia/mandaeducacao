import dayjs from 'dayjs/esm';

import { IResponsavelTurma, NewResponsavelTurma } from './responsavel-turma.model';

export const sampleWithRequiredData: IResponsavelTurma = {
  id: 52655,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
};

export const sampleWithPartialData: IResponsavelTurma = {
  id: 62791,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IResponsavelTurma = {
  id: 51384,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-04'),
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T07:12'),
};

export const sampleWithNewData: NewResponsavelTurma = {
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
