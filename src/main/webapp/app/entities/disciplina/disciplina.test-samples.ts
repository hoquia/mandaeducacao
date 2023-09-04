import { IDisciplina, NewDisciplina } from './disciplina.model';

export const sampleWithRequiredData: IDisciplina = {
  id: 24640,
  codigo: 'Assistant Mission',
  nome: 'Oklahoma Lustroso Applications',
};

export const sampleWithPartialData: IDisciplina = {
  id: 49321,
  codigo: 'mobile Organic',
  nome: 'optical system',
};

export const sampleWithFullData: IDisciplina = {
  id: 1266,
  codigo: 'Plaza violeta',
  nome: 'Loan European',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDisciplina = {
  codigo: 'AGP',
  nome: 'Livros',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
