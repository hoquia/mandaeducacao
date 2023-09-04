import dayjs from 'dayjs/esm';

import { TipoAvaliacao } from 'app/entities/enumerations/tipo-avaliacao.model';

import { IPeriodoLancamentoNota, NewPeriodoLancamentoNota } from './periodo-lancamento-nota.model';

export const sampleWithRequiredData: IPeriodoLancamentoNota = {
  id: 37882,
  tipoAvaliacao: TipoAvaliacao['NOTA1'],
  de: dayjs('2023-09-03T11:28'),
  ate: dayjs('2023-09-03T15:59'),
};

export const sampleWithPartialData: IPeriodoLancamentoNota = {
  id: 2729,
  tipoAvaliacao: TipoAvaliacao['EXAME'],
  de: dayjs('2023-09-03T11:07'),
  ate: dayjs('2023-09-03T01:08'),
  timestamp: dayjs('2023-09-03T15:33'),
};

export const sampleWithFullData: IPeriodoLancamentoNota = {
  id: 7101,
  tipoAvaliacao: TipoAvaliacao['NOTA1'],
  de: dayjs('2023-09-03T21:23'),
  ate: dayjs('2023-09-03T08:34'),
  timestamp: dayjs('2023-09-03T21:58'),
};

export const sampleWithNewData: NewPeriodoLancamentoNota = {
  tipoAvaliacao: TipoAvaliacao['NOTA_CONSELHO'],
  de: dayjs('2023-09-03T00:55'),
  ate: dayjs('2023-09-03T16:54'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
