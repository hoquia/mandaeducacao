import dayjs from 'dayjs/esm';

import { EstadoAcademico } from 'app/entities/enumerations/estado-academico.model';

import { IMatricula, NewMatricula } from './matricula.model';

export const sampleWithRequiredData: IMatricula = {
  id: 55032,
  numeroMatricula: 'Ouguiya Teclado AI',
  estado: EstadoAcademico['EXPULSO'],
};

export const sampleWithPartialData: IMatricula = {
  id: 95262,
  chaveComposta1: 'generating Village Program',
  numeroMatricula: 'Principal CFP world-class',
  estado: EstadoAcademico['REPROVADO'],
  timestamp: dayjs('2023-09-03T19:14'),
  descricao: '../fake-data/blob/hipster.txt',
  termosCompromissos: '../fake-data/blob/hipster.png',
  termosCompromissosContentType: 'unknown',
};

export const sampleWithFullData: IMatricula = {
  id: 87422,
  chaveComposta1: 'open-source',
  chaveComposta2: 'transmitting',
  numeroMatricula: 'Wisconsin',
  numeroChamada: 81487,
  estado: EstadoAcademico['VIGENTE'],
  timestamp: dayjs('2023-09-03T21:31'),
  descricao: '../fake-data/blob/hipster.txt',
  termosCompromissos: '../fake-data/blob/hipster.png',
  termosCompromissosContentType: 'unknown',
  isAceiteTermosCompromisso: true,
};

export const sampleWithNewData: NewMatricula = {
  numeroMatricula: 'withdrawal Berkshire',
  estado: EstadoAcademico['EXAME'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
