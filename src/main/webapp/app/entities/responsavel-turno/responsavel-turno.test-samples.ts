import dayjs from 'dayjs/esm';

import { IResponsavelTurno, NewResponsavelTurno } from './responsavel-turno.model';

export const sampleWithRequiredData: IResponsavelTurno = {
  id: 32528,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
};

export const sampleWithPartialData: IResponsavelTurno = {
  id: 57782,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IResponsavelTurno = {
  id: 2855,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T16:40'),
};

export const sampleWithNewData: NewResponsavelTurno = {
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
