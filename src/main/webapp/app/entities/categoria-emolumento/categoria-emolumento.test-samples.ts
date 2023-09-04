import { ICategoriaEmolumento, NewCategoriaEmolumento } from './categoria-emolumento.model';

export const sampleWithRequiredData: ICategoriaEmolumento = {
  id: 10539,
  nome: 'bypassing',
};

export const sampleWithPartialData: ICategoriaEmolumento = {
  id: 21794,
  nome: 'Diverse Connecticut connecting',
  isServico: true,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ICategoriaEmolumento = {
  id: 94557,
  nome: 'Ohio SMS invoice',
  isServico: true,
  cor: 'Operative sensor',
  descricao: '../fake-data/blob/hipster.txt',
  isIsentoMulta: true,
  isIsentoJuro: true,
};

export const sampleWithNewData: NewCategoriaEmolumento = {
  nome: 'Granito paradigms Communications',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
