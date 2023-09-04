import { IClasse, NewClasse } from './classe.model';

export const sampleWithRequiredData: IClasse = {
  id: 50071,
  descricao: 'Pakistan',
};

export const sampleWithPartialData: IClasse = {
  id: 16949,
  descricao: 'castanho Metal invoice',
};

export const sampleWithFullData: IClasse = {
  id: 53162,
  descricao: 'invoice Account cinzento',
};

export const sampleWithNewData: NewClasse = {
  descricao: 'Analyst prata bandwidth',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
