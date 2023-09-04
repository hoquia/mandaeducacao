import { IImposto, NewImposto } from './imposto.model';

export const sampleWithRequiredData: IImposto = {
  id: 57604,
  descricao: 'Granito',
  taxa: 93,
};

export const sampleWithPartialData: IImposto = {
  id: 82966,
  descricao: 'à open-source sexy',
  pais: 'Impressionante',
  taxa: 65,
  motivoDescricao: 'cutting-edge',
};

export const sampleWithFullData: IImposto = {
  id: 75414,
  descricao: 'Money',
  pais: 'hub Cambridgeshire Open-architected',
  taxa: 98,
  isRetencao: true,
  motivoDescricao: 'transition',
  motivoCodigo: 'Berkshire Roupas',
};

export const sampleWithNewData: NewImposto = {
  descricao: 'Guiana Pequeno Pequeno',
  taxa: 37,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
