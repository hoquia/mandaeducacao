import { IProvedorNotificacao, NewProvedorNotificacao } from './provedor-notificacao.model';

export const sampleWithRequiredData: IProvedorNotificacao = {
  id: 15926,
  telefone: 'laranja bypass Kuwaiti',
  email: 'Miguel84@outlook.com',
  link: 'Aço',
  token: 'neural Algodão',
  username: 'success Applications',
  password: 'Sharable Group',
  hash: 'Beleza',
};

export const sampleWithPartialData: IProvedorNotificacao = {
  id: 12151,
  telefone: 'Strategist',
  email: 'Paulo_Lopes94@yahoo.com',
  link: 'disintermediate Franc',
  token: 'Sabonete Iraque',
  username: 'Streamlined seize',
  password: 'Tactics programming',
  hash: 'Panamá',
  isPadrao: true,
};

export const sampleWithFullData: IProvedorNotificacao = {
  id: 12868,
  telefone: 'Plástico Bedfordshire',
  email: 'Miguel_Santos34@mail.pt',
  link: 'plug-and-play',
  token: 'connect',
  username: 'Avon Regional Granito',
  password: 'Down-sized systematic',
  hash: 'Lead scale matrix',
  isPadrao: false,
};

export const sampleWithNewData: NewProvedorNotificacao = {
  telefone: 'Peso didactic',
  email: 'Margarida_Carneiro24@mail.pt',
  link: 'payment parsing Sabonete',
  token: 'open-source bluetooth',
  username: 'Creative',
  password: 'generating',
  hash: 'Enhanced',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
