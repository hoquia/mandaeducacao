import { ILookupItem, NewLookupItem } from './lookup-item.model';

export const sampleWithRequiredData: ILookupItem = {
  id: 36111,
  descricao: 'protocol Chief',
};

export const sampleWithPartialData: ILookupItem = {
  id: 50772,
  isSistema: false,
  descricao: '1080p unleash Carro',
};

export const sampleWithFullData: ILookupItem = {
  id: 26172,
  codigo: 'Assistant multi-byte',
  ordem: 60005,
  isSistema: false,
  descricao: 'product',
};

export const sampleWithNewData: NewLookupItem = {
  descricao: 'Supervisor Agent deposit',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
