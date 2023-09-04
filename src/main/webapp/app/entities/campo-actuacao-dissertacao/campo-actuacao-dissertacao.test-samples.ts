import { ICampoActuacaoDissertacao, NewCampoActuacaoDissertacao } from './campo-actuacao-dissertacao.model';

export const sampleWithRequiredData: ICampoActuacaoDissertacao = {
  id: 87215,
  nome: 'Interface',
};

export const sampleWithPartialData: ICampoActuacaoDissertacao = {
  id: 43853,
  nome: 'PNG Costa',
};

export const sampleWithFullData: ICampoActuacaoDissertacao = {
  id: 73343,
  nome: 'Savings Applications SQL',
  descricao: '../fake-data/blob/hipster.txt',
  isActivo: true,
};

export const sampleWithNewData: NewCampoActuacaoDissertacao = {
  nome: 'FTP Frango',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
