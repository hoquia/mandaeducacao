import dayjs from 'dayjs/esm';

import { INotasGeralDisciplina, NewNotasGeralDisciplina } from './notas-geral-disciplina.model';

export const sampleWithRequiredData: INotasGeralDisciplina = {
  id: 39053,
  timestamp: dayjs('2023-09-03T06:08'),
};

export const sampleWithPartialData: INotasGeralDisciplina = {
  id: 14648,
  chaveComposta: 'SMTP Verde',
  media2: 73059,
  media3: 20632,
  exame: 97958,
  timestamp: dayjs('2023-09-03T14:52'),
  hash: 'North functionalities',
  faltaJusticada: 5086,
};

export const sampleWithFullData: INotasGeralDisciplina = {
  id: 43473,
  chaveComposta: 'prata',
  periodoLancamento: 2,
  media1: 13950,
  media2: 78398,
  media3: 15418,
  exame: 23805,
  recurso: 51117,
  exameEspecial: 55299,
  notaConselho: 87855,
  mediaFinalDisciplina: 31252,
  timestamp: dayjs('2023-09-03T23:28'),
  hash: 'innovative',
  faltaJusticada: 90502,
  faltaInjustificada: 69427,
};

export const sampleWithNewData: NewNotasGeralDisciplina = {
  timestamp: dayjs('2023-09-03T03:05'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
