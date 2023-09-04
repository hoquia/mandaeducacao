import dayjs from 'dayjs/esm';

import { IDissertacaoFinalCurso, NewDissertacaoFinalCurso } from './dissertacao-final-curso.model';

export const sampleWithRequiredData: IDissertacaoFinalCurso = {
  id: 6356,
  numero: 'JBOD Cambridgeshire incentivize',
  data: dayjs('2023-09-03'),
  tema: 'Sem override bluetooth',
  objectivoGeral: 'bypass Liaison',
  objectivosEspecificos: '../fake-data/blob/hipster.txt',
  introducao: '../fake-data/blob/hipster.txt',
  resumo: '../fake-data/blob/hipster.txt',
  problema: '../fake-data/blob/hipster.txt',
  resultado: '../fake-data/blob/hipster.txt',
  metodologia: '../fake-data/blob/hipster.txt',
  referenciasBibliograficas: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IDissertacaoFinalCurso = {
  id: 97563,
  numero: 'reinvent Keys',
  timestamp: dayjs('2023-09-03T09:14'),
  data: dayjs('2023-09-03'),
  tema: 'JBOD convergence',
  objectivoGeral: 'Bacon',
  objectivosEspecificos: '../fake-data/blob/hipster.txt',
  introducao: '../fake-data/blob/hipster.txt',
  resumo: '../fake-data/blob/hipster.txt',
  problema: '../fake-data/blob/hipster.txt',
  resultado: '../fake-data/blob/hipster.txt',
  metodologia: '../fake-data/blob/hipster.txt',
  referenciasBibliograficas: '../fake-data/blob/hipster.txt',
  observacaoOrientador: '../fake-data/blob/hipster.txt',
  observacaoInstituicao: '../fake-data/blob/hipster.txt',
  hash: 'Dakota',
  termosCompromissos: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDissertacaoFinalCurso = {
  id: 4682,
  numero: 'Plástico Martinica matrix',
  timestamp: dayjs('2023-09-03T02:48'),
  data: dayjs('2023-09-03'),
  tema: 'background',
  objectivoGeral: 'back Algodão',
  objectivosEspecificos: '../fake-data/blob/hipster.txt',
  introducao: '../fake-data/blob/hipster.txt',
  resumo: '../fake-data/blob/hipster.txt',
  problema: '../fake-data/blob/hipster.txt',
  resultado: '../fake-data/blob/hipster.txt',
  metodologia: '../fake-data/blob/hipster.txt',
  referenciasBibliograficas: '../fake-data/blob/hipster.txt',
  observacaoOrientador: '../fake-data/blob/hipster.txt',
  observacaoAreaFormacao: '../fake-data/blob/hipster.txt',
  observacaoInstituicao: '../fake-data/blob/hipster.txt',
  hash: 'visionary Solutions cross-platform',
  termosCompromissos: '../fake-data/blob/hipster.txt',
  isAceiteTermosCompromisso: false,
};

export const sampleWithNewData: NewDissertacaoFinalCurso = {
  numero: 'preto orquídea Executive',
  data: dayjs('2023-09-03'),
  tema: 'Louisiana homogeneous channels',
  objectivoGeral: 'turn-key',
  objectivosEspecificos: '../fake-data/blob/hipster.txt',
  introducao: '../fake-data/blob/hipster.txt',
  resumo: '../fake-data/blob/hipster.txt',
  problema: '../fake-data/blob/hipster.txt',
  resultado: '../fake-data/blob/hipster.txt',
  metodologia: '../fake-data/blob/hipster.txt',
  referenciasBibliograficas: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
