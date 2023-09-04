import dayjs from 'dayjs/esm';

import { IResponsavelCurso, NewResponsavelCurso } from './responsavel-curso.model';

export const sampleWithRequiredData: IResponsavelCurso = {
  id: 40419,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
};

export const sampleWithPartialData: IResponsavelCurso = {
  id: 27959,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T01:32'),
};

export const sampleWithFullData: IResponsavelCurso = {
  id: 77624,
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T07:01'),
};

export const sampleWithNewData: NewResponsavelCurso = {
  de: dayjs('2023-09-03'),
  ate: dayjs('2023-09-03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
