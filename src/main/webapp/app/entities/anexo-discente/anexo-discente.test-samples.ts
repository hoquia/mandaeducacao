import dayjs from 'dayjs/esm';

import { CategoriaAnexo } from 'app/entities/enumerations/categoria-anexo.model';

import { IAnexoDiscente, NewAnexoDiscente } from './anexo-discente.model';

export const sampleWithRequiredData: IAnexoDiscente = {
  id: 86018,
  categoria: CategoriaAnexo['OUTRO'],
};

export const sampleWithPartialData: IAnexoDiscente = {
  id: 32889,
  categoria: CategoriaAnexo['CIVIL'],
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IAnexoDiscente = {
  id: 31378,
  categoria: CategoriaAnexo['SAUDE'],
  anexo: '../fake-data/blob/hipster.png',
  anexoContentType: 'unknown',
  descricao: '../fake-data/blob/hipster.txt',
  validade: dayjs('2023-09-03'),
  timestamp: dayjs('2023-09-03T18:07'),
};

export const sampleWithNewData: NewAnexoDiscente = {
  categoria: CategoriaAnexo['CIVIL'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
