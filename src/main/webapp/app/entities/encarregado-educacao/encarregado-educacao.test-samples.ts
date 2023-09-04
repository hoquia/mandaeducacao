import dayjs from 'dayjs/esm';

import { Sexo } from 'app/entities/enumerations/sexo.model';

import { IEncarregadoEducacao, NewEncarregadoEducacao } from './encarregado-educacao.model';

export const sampleWithRequiredData: IEncarregadoEducacao = {
  id: 66226,
  nome: 'bandwidth repurpose',
  nascimento: dayjs('2023-09-03'),
  sexo: Sexo['FEMENINO'],
  documentoNumero: 'Licenciado functionalities',
  telefonePrincipal: 'Bedfordshire ouro Mesa',
};

export const sampleWithPartialData: IEncarregadoEducacao = {
  id: 26552,
  fotografia: '../fake-data/blob/hipster.png',
  fotografiaContentType: 'unknown',
  nome: 'global Montana',
  nascimento: dayjs('2023-09-03'),
  nif: 'Automóveis Persistent Prático',
  sexo: Sexo['MASCULINO'],
  documentoNumero: 'Queijo',
  telefonePrincipal: 'open-source',
  residencia: 'Marca Director',
  enderecoTrabalho: 'Plástico Fantástico',
  rendaMensal: 6302,
};

export const sampleWithFullData: IEncarregadoEducacao = {
  id: 81382,
  fotografia: '../fake-data/blob/hipster.png',
  fotografiaContentType: 'unknown',
  nome: 'Namibia Fantástico Bicicleta',
  nascimento: dayjs('2023-09-03'),
  nif: '24/365 Program back-end',
  sexo: Sexo['MASCULINO'],
  documentoNumero: 'Bahamian castanho',
  telefonePrincipal: 'pixel Specialist Automóveis',
  telefoneAlternativo: 'Plástico vermelho',
  email: 'urea23@gmail.com',
  residencia: 'Money compress',
  enderecoTrabalho: 'seize',
  rendaMensal: 96028,
  empresaTrabalho: 'Índia',
  hash: 'CFA Executive',
};

export const sampleWithNewData: NewEncarregadoEducacao = {
  nome: 'world-class impactful',
  nascimento: dayjs('2023-09-03'),
  sexo: Sexo['FEMENINO'],
  documentoNumero: 'Minnesota Metal Delaware',
  telefonePrincipal: 'Agent Jogos Música',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
