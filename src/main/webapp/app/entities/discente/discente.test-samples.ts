import dayjs from 'dayjs/esm';

import { Sexo } from 'app/entities/enumerations/sexo.model';

import { IDiscente, NewDiscente } from './discente.model';

export const sampleWithRequiredData: IDiscente = {
  id: 29929,
  nome: 'Congelado Rato violeta',
  nascimento: dayjs('2023-09-03'),
  documentoNumero: 'Beleza',
  documentoEmissao: dayjs('2023-09-03'),
  documentoValidade: dayjs('2023-09-03'),
  sexo: Sexo['FEMENINO'],
  pai: 'incentivize Operations THX',
  mae: 'background invoice Jardim',
  numeroProcesso: 'visionary syndicate Legacy',
};

export const sampleWithPartialData: IDiscente = {
  id: 46182,
  nome: 'hard turn-key Hawaii',
  nascimento: dayjs('2023-09-03'),
  documentoNumero: 'Supervisor embrace complexity',
  documentoEmissao: dayjs('2023-09-03'),
  documentoValidade: dayjs('2023-09-03'),
  nif: 'Direct',
  sexo: Sexo['FEMENINO'],
  pai: 'Saboroso',
  mae: 'SCSI Ramp alarm',
  email: 'David68@sapo.pt',
  isAtestadoPobreza: false,
  telefoneMedico: 'hack inno',
  instituicaoParticularSaude: 'interface Checking bluetooth',
  altura: 51302,
  peso: 58360,
  isAutorizadoMedicacao: false,
  cuidadosEspeciaisSaude: '../fake-data/blob/hipster.txt',
  numeroProcesso: 'THX',
  dataIngresso: dayjs('2023-09-03T07:19'),
  hash: 'Administrator Tala Granito',
  observacao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDiscente = {
  id: 35055,
  fotografia: '../fake-data/blob/hipster.png',
  fotografiaContentType: 'unknown',
  nome: 'Points',
  nascimento: dayjs('2023-09-03'),
  documentoNumero: 'Lustroso recontextualize',
  documentoEmissao: dayjs('2023-09-03'),
  documentoValidade: dayjs('2023-09-03'),
  nif: 'Dakota',
  sexo: Sexo['MASCULINO'],
  pai: 'Loan',
  mae: 'Direct hack bluetooth',
  telefonePrincipal: 'Avenue Computador',
  telefoneParente: 'technologies Liberian firewall',
  email: 'Gaspar.Domingues35@portugalmail.pt',
  isEncarregadoEducacao: false,
  isTrabalhador: false,
  isFilhoAntigoConbatente: true,
  isAtestadoPobreza: true,
  nomeMedico: 'Argélia compressing',
  telefoneMedico: 'panel',
  instituicaoParticularSaude: '1080p haptic',
  altura: 3214,
  peso: 33713,
  isAsmatico: true,
  isAlergico: true,
  isPraticaEducacaoFisica: false,
  isAutorizadoMedicacao: true,
  cuidadosEspeciaisSaude: '../fake-data/blob/hipster.txt',
  numeroProcesso: 'Platinum',
  dataIngresso: dayjs('2023-09-03T23:42'),
  hash: 'Genérico',
  observacao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDiscente = {
  nome: 'Personal Account Salvador',
  nascimento: dayjs('2023-09-03'),
  documentoNumero: 'installation branco',
  documentoEmissao: dayjs('2023-09-03'),
  documentoValidade: dayjs('2023-09-03'),
  sexo: Sexo['MASCULINO'],
  pai: 'deposit',
  mae: 'synthesize optical mobile',
  numeroProcesso: 'castanho technologies project',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
