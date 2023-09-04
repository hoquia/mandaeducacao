import dayjs from 'dayjs/esm';

import { EstadoPagamento } from 'app/entities/enumerations/estado-pagamento.model';

import { ITransacao, NewTransacao } from './transacao.model';

export const sampleWithRequiredData: ITransacao = {
  id: 45074,
  montante: 62233,
  data: dayjs('2023-09-03'),
  referencia: '1080p Account Corporate',
  estado: EstadoPagamento['PENDENTE'],
  saldo: 12458,
};

export const sampleWithPartialData: ITransacao = {
  id: 8188,
  montante: 27835,
  data: dayjs('2023-09-03'),
  referencia: 'Account',
  estado: EstadoPagamento['VALIDO'],
  saldo: 35899,
};

export const sampleWithFullData: ITransacao = {
  id: 65708,
  montante: 82452,
  data: dayjs('2023-09-03'),
  referencia: 'invoice Persistent Fresco',
  estado: EstadoPagamento['REJEITADO'],
  saldo: 67496,
  anexo: '../fake-data/blob/hipster.png',
  anexoContentType: 'unknown',
  timestamp: dayjs('2023-09-03T04:45'),
};

export const sampleWithNewData: NewTransacao = {
  montante: 54046,
  data: dayjs('2023-09-03'),
  referencia: 'AGP indexing Bedfordshire',
  estado: EstadoPagamento['PENDENTE'],
  saldo: 96568,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
