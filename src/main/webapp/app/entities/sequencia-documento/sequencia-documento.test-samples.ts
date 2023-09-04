import dayjs from 'dayjs/esm';

import { ISequenciaDocumento, NewSequenciaDocumento } from './sequencia-documento.model';

export const sampleWithRequiredData: ISequenciaDocumento = {
  id: 10379,
  sequencia: 95489,
  data: dayjs('2023-09-03'),
  hash: 'PCI Data',
  timestamp: dayjs('2023-09-03T20:24'),
};

export const sampleWithPartialData: ISequenciaDocumento = {
  id: 21472,
  sequencia: 25430,
  data: dayjs('2023-09-03'),
  hash: 'paradigms',
  timestamp: dayjs('2023-09-03T20:39'),
};

export const sampleWithFullData: ISequenciaDocumento = {
  id: 46343,
  sequencia: 73335,
  data: dayjs('2023-09-03'),
  hash: 'monitor',
  timestamp: dayjs('2023-09-03T19:37'),
};

export const sampleWithNewData: NewSequenciaDocumento = {
  sequencia: 45047,
  data: dayjs('2023-09-03'),
  hash: 'Faroé',
  timestamp: dayjs('2023-09-03T13:55'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
