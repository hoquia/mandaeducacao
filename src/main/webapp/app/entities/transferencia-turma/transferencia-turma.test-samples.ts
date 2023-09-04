import dayjs from 'dayjs/esm';

import { ITransferenciaTurma, NewTransferenciaTurma } from './transferencia-turma.model';

export const sampleWithRequiredData: ITransferenciaTurma = {
  id: 72423,
};

export const sampleWithPartialData: ITransferenciaTurma = {
  id: 26314,
};

export const sampleWithFullData: ITransferenciaTurma = {
  id: 93340,
  timestamp: dayjs('2023-09-04T00:21'),
};

export const sampleWithNewData: NewTransferenciaTurma = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
