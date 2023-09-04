import { IMeioPagamento, NewMeioPagamento } from './meio-pagamento.model';

export const sampleWithRequiredData: IMeioPagamento = {
  id: 66899,
  codigo: 'Investor Group',
  nome: 'withdrawal Crianças',
};

export const sampleWithPartialData: IMeioPagamento = {
  id: 91967,
  codigo: 'Suíça Senegal deposit',
  nome: 'bus metrics',
  numeroDigitoReferencia: 73560,
  isPagamentoInstantanio: false,
  token: 'withdrawal',
  formatoReferencia: 'navigating Prático ameixa',
};

export const sampleWithFullData: IMeioPagamento = {
  id: 64798,
  imagem: '../fake-data/blob/hipster.png',
  imagemContentType: 'unknown',
  codigo: '24/7 users',
  nome: 'programming fúcsia visualize',
  numeroDigitoReferencia: 61507,
  isPagamentoInstantanio: false,
  hash: 'cultivate Metal',
  link: 'architecture verde Luvas',
  token: 'withdrawal Chile program',
  username: 'one-to-one Account',
  password: 'Jardim Electrónica',
  formatoReferencia: 'logistical architectures',
};

export const sampleWithNewData: NewMeioPagamento = {
  codigo: 'deposit Incrível',
  nome: 'quantifying Metal',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
