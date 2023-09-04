import { ILookup, NewLookup } from './lookup.model';

export const sampleWithRequiredData: ILookup = {
  id: 75058,
  codigo: 'Savings disintermediate',
  nome: 'Corporate Mesa architect',
};

export const sampleWithPartialData: ILookup = {
  id: 42738,
  codigo: 'Ferramentas',
  nome: 'Profound SSL Refinado',
  descricao: '../fake-data/blob/hipster.txt',
  isSistema: true,
  isModificavel: false,
};

export const sampleWithFullData: ILookup = {
  id: 6690,
  codigo: 'Borders calculate',
  nome: 'Bedfordshire interfaces Analyst',
  descricao: '../fake-data/blob/hipster.txt',
  isSistema: false,
  isModificavel: false,
};

export const sampleWithNewData: NewLookup = {
  codigo: 'THX',
  nome: 'Product Internal monetize',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
