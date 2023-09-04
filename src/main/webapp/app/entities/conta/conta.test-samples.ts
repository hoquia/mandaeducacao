import { TipoConta } from 'app/entities/enumerations/tipo-conta.model';

import { IConta, NewConta } from './conta.model';

export const sampleWithRequiredData: IConta = {
  id: 17789,
  tipo: TipoConta['BANCO'],
  titulo: 'Aço Mobility Madeira',
  numero: 'Congelado',
  titular: 'turn-key Monitored JBOD',
};

export const sampleWithPartialData: IConta = {
  id: 48851,
  imagem: '../fake-data/blob/hipster.png',
  imagemContentType: 'unknown',
  tipo: TipoConta['CAIXA'],
  titulo: 'Account salmão Sapatos',
  numero: 'wireless Quality Regional',
  iban: 'BG34FGAI546607966518W4',
  titular: 'Missouri Refinado partnerships',
  isPadrao: true,
};

export const sampleWithFullData: IConta = {
  id: 80484,
  imagem: '../fake-data/blob/hipster.png',
  imagemContentType: 'unknown',
  tipo: TipoConta['BANCO'],
  titulo: 'blockchains input',
  numero: 'Avon Isle connect',
  iban: 'DK5685438004839001',
  titular: 'Loan Fiji',
  isPadrao: false,
};

export const sampleWithNewData: NewConta = {
  tipo: TipoConta['BANCO'],
  titulo: 'Licenciado Guiana Money',
  numero: 'Austrália Agent Account',
  titular: 'Betão',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
