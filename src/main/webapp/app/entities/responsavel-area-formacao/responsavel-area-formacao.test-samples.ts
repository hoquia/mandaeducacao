import dayjs from 'dayjs/esm';

import { IResponsavelAreaFormacao, NewResponsavelAreaFormacao } from './responsavel-area-formacao.model';

export const sampleWithRequiredData: IResponsavelAreaFormacao = {
  id: 6584,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
};

export const sampleWithPartialData: IResponsavelAreaFormacao = {
  id: 85565,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T11:54'),
};

export const sampleWithFullData: IResponsavelAreaFormacao = {
  id: 35990,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T23:32'),
};

export const sampleWithNewData: NewResponsavelAreaFormacao = {
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
