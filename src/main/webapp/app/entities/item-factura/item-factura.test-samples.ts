import dayjs from 'dayjs/esm';

import { EstadoItemFactura } from 'app/entities/enumerations/estado-item-factura.model';

import { IItemFactura, NewItemFactura } from './item-factura.model';

export const sampleWithRequiredData: IItemFactura = {
  id: 94,
  quantidade: 32356,
  precoUnitario: 41541,
  desconto: 6176,
  multa: 59313,
  juro: 62624,
  precoTotal: 29999,
  estado: EstadoItemFactura['CANCELADO'],
  taxType: 'Sal',
  taxCountryRegion: 'backin',
  taxCode: 'Quirguizis',
};

export const sampleWithPartialData: IItemFactura = {
  id: 31575,
  quantidade: 89212,
  precoUnitario: 10305,
  desconto: 72489,
  multa: 26986,
  juro: 57781,
  precoTotal: 60845,
  estado: EstadoItemFactura['PAGO'],
  taxType: 'pro',
  taxCountryRegion: 'Chade ',
  taxCode: 'database H',
  taxPercentage: 22,
  taxExemptionReason: 'optical Automóveis',
  taxExemptionCode: 'ope',
  emissao: dayjs('2023-09-03'),
  periodo: 46567,
  descricao: 'roxo bronzeado',
};

export const sampleWithFullData: IItemFactura = {
  id: 2055,
  quantidade: 10308,
  precoUnitario: 60337,
  desconto: 82402,
  multa: 98250,
  juro: 54124,
  precoTotal: 31226,
  estado: EstadoItemFactura['PAGO'],
  taxType: 'ban',
  taxCountryRegion: 'missio',
  taxCode: 'protocol B',
  taxPercentage: 23,
  taxExemptionReason: 'morph Kroon up',
  taxExemptionCode: 'e-c',
  emissao: dayjs('2023-09-03'),
  expiracao: dayjs('2023-09-03'),
  periodo: 6695,
  descricao: 'Mississippi hub RSS',
};

export const sampleWithNewData: NewItemFactura = {
  quantidade: 91737,
  precoUnitario: 27717,
  desconto: 34033,
  multa: 53418,
  juro: 4012,
  precoTotal: 5463,
  estado: EstadoItemFactura['ATRASADO'],
  taxType: 'cas',
  taxCountryRegion: 'JSON',
  taxCode: 'Central',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
