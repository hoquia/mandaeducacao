import dayjs from 'dayjs/esm';

import { IAplicacaoRecibo, NewAplicacaoRecibo } from './aplicacao-recibo.model';

export const sampleWithRequiredData: IAplicacaoRecibo = {
  id: 24098,
  totalFactura: 89205,
  totalPago: 84104,
  totalDiferenca: 68256,
};

export const sampleWithPartialData: IAplicacaoRecibo = {
  id: 12782,
  totalFactura: 85529,
  totalPago: 72699,
  totalDiferenca: 54073,
};

export const sampleWithFullData: IAplicacaoRecibo = {
  id: 87473,
  totalFactura: 19724,
  totalPago: 86536,
  totalDiferenca: 32035,
  timestamp: dayjs('2023-09-03T07:56'),
};

export const sampleWithNewData: NewAplicacaoRecibo = {
  totalFactura: 72046,
  totalPago: 93305,
  totalDiferenca: 97066,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
