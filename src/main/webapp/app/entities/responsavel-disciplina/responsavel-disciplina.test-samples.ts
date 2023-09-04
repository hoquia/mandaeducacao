import dayjs from 'dayjs/esm';

import { IResponsavelDisciplina, NewResponsavelDisciplina } from './responsavel-disciplina.model';

export const sampleWithRequiredData: IResponsavelDisciplina = {
  id: 34333,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
};

export const sampleWithPartialData: IResponsavelDisciplina = {
  id: 36191,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  timestamp: dayjs('2023-09-03T12:04'),
};

export const sampleWithFullData: IResponsavelDisciplina = {
  id: 10820,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T14:29'),
};

export const sampleWithNewData: NewResponsavelDisciplina = {
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
