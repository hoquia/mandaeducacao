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
  id: 60856,
  quantidade: 22654,
  precoUnitario: 50824,
  desconto: 92949,
  multa: 31575,
  juro: 89212,
  precoTotal: 10305,
  estado: EstadoItemFactura['ATRASADO'],
  taxType: 'New',
  taxCountryRegion: 'progra',
  taxCode: 'Chade navi',
  taxPercentage: 60,
  taxExemptionReason: 'Distributed',
  taxExemptionCode: 'Que',
};

export const sampleWithFullData: IItemFactura = {
  id: 68312,
  quantidade: 22921,
  precoUnitario: 8863,
  desconto: 45832,
  multa: 37746,
  juro: 28752,
  precoTotal: 60790,
  estado: EstadoItemFactura['ATRASADO'],
  taxType: 'Orc',
  taxCountryRegion: 'Accoun',
  taxCode: 'groupware ',
  taxPercentage: 34,
  taxExemptionReason: 'ouro recontextualize',
  taxExemptionCode: 'Toa',
};

export const sampleWithNewData: NewItemFactura = {
  quantidade: 23294,
  precoUnitario: 54421,
  desconto: 82568,
  multa: 5608,
  juro: 98760,
  precoTotal: 62316,
  estado: EstadoItemFactura['PAGO'],
  taxType: 'dig',
  taxCountryRegion: 'multi-',
  taxCode: 'hub RSS Pr',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
