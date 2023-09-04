import dayjs from 'dayjs/esm';

import { IAnoLectivo, NewAnoLectivo } from './ano-lectivo.model';

export const sampleWithRequiredData: IAnoLectivo = {
  id: 29248,
  ano: 27265,
  inicio: dayjs('2023-09-03'),
  fim: dayjs('2023-09-03'),
  descricao: 'haptic Proactive Market',
};

export const sampleWithPartialData: IAnoLectivo = {
  id: 13736,
  ano: 96856,
  inicio: dayjs('2023-09-03'),
  fim: dayjs('2023-09-03'),
  descricao: 'state system',
  timestam: dayjs('2023-09-03T12:44'),
};

export const sampleWithFullData: IAnoLectivo = {
  id: 90507,
  ano: 15943,
  inicio: dayjs('2023-09-03'),
  fim: dayjs('2023-09-03'),
  descricao: '24/365 Re-contextualized auxiliary',
  timestam: dayjs('2023-09-03T03:39'),
  isActual: true,
};

export const sampleWithNewData: NewAnoLectivo = {
  ano: 8292,
  inicio: dayjs('2023-09-03'),
  fim: dayjs('2023-09-03'),
  descricao: 'FTP',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
