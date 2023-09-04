import { IPrecoEmolumento, NewPrecoEmolumento } from './preco-emolumento.model';

export const sampleWithRequiredData: IPrecoEmolumento = {
  id: 2084,
  preco: 30504,
};

export const sampleWithPartialData: IPrecoEmolumento = {
  id: 31305,
  preco: 22099,
  isEspecificoCurso: true,
  isEspecificoTurno: false,
};

export const sampleWithFullData: IPrecoEmolumento = {
  id: 63472,
  preco: 77969,
  isEspecificoCurso: false,
  isEspecificoAreaFormacao: false,
  isEspecificoClasse: true,
  isEspecificoTurno: true,
};

export const sampleWithNewData: NewPrecoEmolumento = {
  preco: 92646,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
