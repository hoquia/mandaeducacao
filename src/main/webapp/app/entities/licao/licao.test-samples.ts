import { EstadoLicao } from 'app/entities/enumerations/estado-licao.model';

import { ILicao, NewLicao } from './licao.model';

export const sampleWithRequiredData: ILicao = {
  id: 66482,
  numero: 68486,
  estado: EstadoLicao['CANCELADA'],
};

export const sampleWithPartialData: ILicao = {
  id: 46491,
  chaveComposta: 'Investor Irlanda',
  numero: 63494,
  estado: EstadoLicao['ADIADA'],
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ILicao = {
  id: 62100,
  chaveComposta: 'Bedfordshire bus',
  numero: 66209,
  estado: EstadoLicao['ADIADA'],
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewLicao = {
  numero: 84716,
  estado: EstadoLicao['ADIADA'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
