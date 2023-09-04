import dayjs from 'dayjs/esm';

import { ITransferenciaSaldo, NewTransferenciaSaldo } from './transferencia-saldo.model';

export const sampleWithRequiredData: ITransferenciaSaldo = {
  id: 37027,
};

export const sampleWithPartialData: ITransferenciaSaldo = {
  id: 50541,
  montante: 56670,
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T00:40'),
};

export const sampleWithFullData: ITransferenciaSaldo = {
  id: 10412,
  montante: 43393,
  isMesmaConta: true,
  descricao: '../fake-data/blob/hipster.txt',
  timestamp: dayjs('2023-09-03T19:45'),
};

export const sampleWithNewData: NewTransferenciaSaldo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
