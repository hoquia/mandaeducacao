import dayjs from 'dayjs/esm';

import { Comporamento } from 'app/entities/enumerations/comporamento.model';

import { INotasPeriodicaDisciplina, NewNotasPeriodicaDisciplina } from './notas-periodica-disciplina.model';

export const sampleWithRequiredData: INotasPeriodicaDisciplina = {
  id: 93099,
  media: 33388,
  timestamp: dayjs('2023-09-03T12:49'),
};

export const sampleWithPartialData: INotasPeriodicaDisciplina = {
  id: 30079,
  nota1: 88205,
  media: 80811,
  faltaJusticada: 16305,
  timestamp: dayjs('2023-09-03T15:52'),
};

export const sampleWithFullData: INotasPeriodicaDisciplina = {
  id: 48841,
  chaveComposta: 'Saboroso Impressionante Account',
  periodoLancamento: 2,
  nota1: 71939,
  nota2: 63947,
  nota3: 92046,
  media: 22793,
  faltaJusticada: 98910,
  faltaInjustificada: 9813,
  comportamento: Comporamento['NORMAL'],
  hash: 'quantifying',
  timestamp: dayjs('2023-09-03T09:56'),
};

export const sampleWithNewData: NewNotasPeriodicaDisciplina = {
  media: 82121,
  timestamp: dayjs('2023-09-03T21:49'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
