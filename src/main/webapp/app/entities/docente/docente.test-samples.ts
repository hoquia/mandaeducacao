import dayjs from 'dayjs/esm';

import { Sexo } from 'app/entities/enumerations/sexo.model';

import { IDocente, NewDocente } from './docente.model';

export const sampleWithRequiredData: IDocente = {
  id: 15988,
  nome: 'withdrawal Representative',
  nascimento: dayjs('2023-09-03'),
  sexo: Sexo['FEMENINO'],
  pai: 'open',
  mae: 'payment primary solution-oriented',
  documentoNumero: 'array',
  documentoEmissao: dayjs('2023-09-03'),
  documentoValidade: dayjs('2023-09-03'),
  residencia: 'Avon',
  dataInicioFuncoes: dayjs('2023-09-03'),
  telefonePrincipal: 'Loaf Mercearia fúcsia',
};

export const sampleWithPartialData: IDocente = {
  id: 6140,
  nome: 'Borders mindshare',
  nascimento: dayjs('2023-09-03'),
  nif: 'Angola firmware clicks-and-mortar',
  inss: 'Metal Fresco matrix',
  sexo: Sexo['MASCULINO'],
  pai: 'Pequeno',
  mae: 'Accounts',
  documentoNumero: 'port Beleza Analyst',
  documentoEmissao: dayjs('2023-09-03'),
  documentoValidade: dayjs('2023-09-03'),
  residencia: 'Accounts sticky',
  dataInicioFuncoes: dayjs('2023-09-03'),
  telefonePrincipal: 'Betão',
  numeroAgente: 'Account Rupee withdrawal',
  temAgregacaoPedagogica: true,
  hash: 'asymmetric',
};

export const sampleWithFullData: IDocente = {
  id: 42965,
  fotografia: '../fake-data/blob/hipster.png',
  fotografiaContentType: 'unknown',
  nome: 'Lane hour',
  nascimento: dayjs('2023-09-03'),
  nif: 'hack',
  inss: 'Incrível',
  sexo: Sexo['MASCULINO'],
  pai: 'Cambridgeshire Manager 1080p',
  mae: 'Mobility',
  documentoNumero: 'Croácia',
  documentoEmissao: dayjs('2023-09-03'),
  documentoValidade: dayjs('2023-09-03'),
  residencia: 'Inteligente Teclado',
  dataInicioFuncoes: dayjs('2023-09-03'),
  telefonePrincipal: 'Engineer deposit',
  telefoneParente: 'application deposit',
  email: 'Toms_Amaral@gmail.com',
  numeroAgente: 'Customer',
  temAgregacaoPedagogica: false,
  observacao: '../fake-data/blob/hipster.txt',
  hash: 'Jardim foreground',
  timestamp: dayjs('2023-09-03T10:26'),
};

export const sampleWithNewData: NewDocente = {
  nome: 'Livros invoice',
  nascimento: dayjs('2023-09-03'),
  sexo: Sexo['MASCULINO'],
  pai: 'IB Oregon cross-platform',
  mae: 'RSS Investment scale',
  documentoNumero: 'deposit sexy',
  documentoEmissao: dayjs('2023-09-03'),
  documentoValidade: dayjs('2023-09-03'),
  residencia: 'innovative',
  dataInicioFuncoes: dayjs('2023-09-03'),
  telefonePrincipal: 'drive Mobility azure',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
