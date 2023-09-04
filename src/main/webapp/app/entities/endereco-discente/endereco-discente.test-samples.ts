import { TipoEndereco } from 'app/entities/enumerations/tipo-endereco.model';

import { IEnderecoDiscente, NewEnderecoDiscente } from './endereco-discente.model';

export const sampleWithRequiredData: IEnderecoDiscente = {
  id: 93086,
  tipo: TipoEndereco['FACTURACAO'],
};

export const sampleWithPartialData: IEnderecoDiscente = {
  id: 4831,
  tipo: TipoEndereco['FACTURACAO'],
  bairro: 'PNG Fantástico Dynamic',
  rua: 'Avon',
  latitude: 59663,
  longitude: 34647,
};

export const sampleWithFullData: IEnderecoDiscente = {
  id: 48024,
  tipo: TipoEndereco['FACTURACAO'],
  bairro: 'connecting Guilder Pound',
  rua: 'recontextualize',
  numeroCasa: 'User',
  codigoPostal: 'Aço Incrível',
  latitude: 81835,
  longitude: 96424,
};

export const sampleWithNewData: NewEnderecoDiscente = {
  tipo: TipoEndereco['FACTURACAO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
