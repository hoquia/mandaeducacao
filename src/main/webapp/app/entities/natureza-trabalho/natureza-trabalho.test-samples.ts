import { INaturezaTrabalho, NewNaturezaTrabalho } from './natureza-trabalho.model';

export const sampleWithRequiredData: INaturezaTrabalho = {
  id: 55047,
  nome: 'Algodão',
};

export const sampleWithPartialData: INaturezaTrabalho = {
  id: 54823,
  nome: 'Persevering web-readiness deposit',
};

export const sampleWithFullData: INaturezaTrabalho = {
  id: 36919,
  nome: 'bronzeado',
  descricao: '../fake-data/blob/hipster.txt',
  isActivo: false,
};

export const sampleWithNewData: NewNaturezaTrabalho = {
  nome: 'Alaska',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
