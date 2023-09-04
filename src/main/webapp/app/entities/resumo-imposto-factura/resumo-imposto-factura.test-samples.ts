import { IResumoImpostoFactura, NewResumoImpostoFactura } from './resumo-imposto-factura.model';

export const sampleWithRequiredData: IResumoImpostoFactura = {
  id: 81998,
  descricao: 'Designer',
  tipo: 'Customer-focused lima Aço',
  taxa: 71,
  incidencia: 94061,
  montante: 224,
};

export const sampleWithPartialData: IResumoImpostoFactura = {
  id: 19953,
  isRetencao: true,
  descricao: 'Toalhas',
  tipo: 'Account',
  taxa: 73,
  incidencia: 8197,
  montante: 43077,
};

export const sampleWithFullData: IResumoImpostoFactura = {
  id: 66306,
  isRetencao: false,
  descricao: 'whiteboard exploit',
  tipo: 'Engineer tertiary',
  taxa: 35,
  incidencia: 97526,
  montante: 56919,
};

export const sampleWithNewData: NewResumoImpostoFactura = {
  descricao: 'scalable Baamas circuit',
  tipo: 'Union Dynamic',
  taxa: 94,
  incidencia: 17361,
  montante: 36398,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
