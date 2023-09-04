import dayjs from 'dayjs/esm';

import { EstadoDocumentoComercial } from 'app/entities/enumerations/estado-documento-comercial.model';

import { IRecibo, NewRecibo } from './recibo.model';

export const sampleWithRequiredData: IRecibo = {
  id: 27598,
  data: dayjs('2023-09-03'),
  numero: 'azure Solutions repurpose',
  totalSemImposto: 46305,
  totalComImposto: 20263,
  totalDescontoComercial: 17447,
  totalDescontoFinanceiro: 56321,
  totalIVA: 31952,
  totalRetencao: 66124,
  totalJuro: 87938,
  cambio: 76110,
  totalMoedaEstrangeira: 45062,
  totalPagar: 49911,
  totalPago: 85978,
  totalFalta: 31341,
  totalTroco: 42294,
  timestamp: dayjs('2023-09-03T13:39'),
  estado: EstadoDocumentoComercial['A'],
  origem: 'Technician Chapéu',
};

export const sampleWithPartialData: IRecibo = {
  id: 40353,
  data: dayjs('2023-09-03'),
  numero: 'Bola PNG',
  totalSemImposto: 93231,
  totalComImposto: 98575,
  totalDescontoComercial: 40446,
  totalDescontoFinanceiro: 14783,
  totalIVA: 66918,
  totalRetencao: 88670,
  totalJuro: 44305,
  cambio: 83957,
  totalMoedaEstrangeira: 69961,
  totalPagar: 60778,
  totalPago: 82874,
  totalFalta: 892,
  totalTroco: 94798,
  timestamp: dayjs('2023-09-03T08:35'),
  descricao: '../fake-data/blob/hipster.txt',
  estado: EstadoDocumentoComercial['P'],
  origem: 'Inteligente Cocos Senior',
};

export const sampleWithFullData: IRecibo = {
  id: 50498,
  data: dayjs('2023-09-03'),
  vencimento: dayjs('2023-09-03'),
  numero: 'withdrawal',
  totalSemImposto: 85714,
  totalComImposto: 921,
  totalDescontoComercial: 16392,
  totalDescontoFinanceiro: 94767,
  totalIVA: 29088,
  totalRetencao: 77882,
  totalJuro: 34304,
  cambio: 20658,
  totalMoedaEstrangeira: 85408,
  totalPagar: 8139,
  totalPago: 36853,
  totalFalta: 13617,
  totalTroco: 50553,
  isNovo: true,
  timestamp: dayjs('2023-09-03T05:45'),
  descricao: '../fake-data/blob/hipster.txt',
  debito: 55035,
  credito: 50446,
  isFiscalizado: true,
  signText: 'quantifying RAM',
  hash: 'embrace cross-media Licenciado',
  hashShort: 'Aruban',
  hashControl: 'content generating',
  keyVersion: 4592,
  estado: EstadoDocumentoComercial['P'],
  origem: 'multimedia',
};

export const sampleWithNewData: NewRecibo = {
  data: dayjs('2023-09-03'),
  numero: 'Avon Auto backing',
  totalSemImposto: 33598,
  totalComImposto: 9516,
  totalDescontoComercial: 39740,
  totalDescontoFinanceiro: 86280,
  totalIVA: 87105,
  totalRetencao: 47117,
  totalJuro: 15053,
  cambio: 43879,
  totalMoedaEstrangeira: 84705,
  totalPagar: 71902,
  totalPago: 57879,
  totalFalta: 46855,
  totalTroco: 36019,
  timestamp: dayjs('2023-09-03T05:14'),
  estado: EstadoDocumentoComercial['A'],
  origem: 'Faroé Beleza',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
