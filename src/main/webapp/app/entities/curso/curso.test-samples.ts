import { ICurso, NewCurso } from './curso.model';

export const sampleWithRequiredData: ICurso = {
  id: 11617,
  codigo: 'Metal FTP extend',
  nome: 'actuating Bedfordshire',
};

export const sampleWithPartialData: ICurso = {
  id: 99527,
  codigo: 'bypass wireless human-resource',
  nome: 'deposit',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ICurso = {
  id: 37341,
  imagem: '../fake-data/blob/hipster.png',
  imagemContentType: 'unknown',
  codigo: 'Sapatos',
  nome: 'Washington',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewCurso = {
  codigo: 'Bedfordshire web-readiness capacitor',
  nome: 'Taka Congelado',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
