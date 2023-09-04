import { IAreaFormacao, NewAreaFormacao } from './area-formacao.model';

export const sampleWithRequiredData: IAreaFormacao = {
  id: 3277,
  codigo: 'Technician indexing',
  nome: 'Jordânia deposit',
};

export const sampleWithPartialData: IAreaFormacao = {
  id: 96266,
  codigo: 'XSS Camisa connecting',
  nome: 'customer Salsichas',
};

export const sampleWithFullData: IAreaFormacao = {
  id: 79970,
  imagem: '../fake-data/blob/hipster.png',
  imagemContentType: 'unknown',
  codigo: 'viral ubiquitous',
  nome: 'JBOD Account interactive',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAreaFormacao = {
  codigo: 'even-keeled Coordinator ability',
  nome: 'Berkshire',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
