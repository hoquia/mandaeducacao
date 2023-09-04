import { IEmolumento, NewEmolumento } from './emolumento.model';

export const sampleWithRequiredData: IEmolumento = {
  id: 88000,
  numero: 'Arizona Berkshire',
  nome: 'Director Facilitator Madeira',
  preco: 8421,
  quantidade: 27277,
};

export const sampleWithPartialData: IEmolumento = {
  id: 9959,
  numero: 'Manager Computador Niue',
  nome: 'technologies software Kwanza',
  preco: 40582,
  quantidade: 55532,
  periodo: 7,
  inicioPeriodo: 2,
  isObrigatorioConfirmacao: false,
};

export const sampleWithFullData: IEmolumento = {
  id: 26470,
  imagem: '../fake-data/blob/hipster.png',
  imagemContentType: 'unknown',
  numero: 'Hungria neutral withdrawal',
  nome: 'Stravenue infrastructures',
  preco: 36208,
  quantidade: 5214,
  periodo: 9,
  inicioPeriodo: 4,
  fimPeriodo: 10,
  isObrigatorioMatricula: false,
  isObrigatorioConfirmacao: true,
};

export const sampleWithNewData: NewEmolumento = {
  numero: 'time-frame JSON Dollar',
  nome: 'non-volatile Betão',
  preco: 49351,
  quantidade: 31437,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
