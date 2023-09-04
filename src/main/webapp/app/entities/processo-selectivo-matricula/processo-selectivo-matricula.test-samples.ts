import dayjs from 'dayjs/esm';

import { IProcessoSelectivoMatricula, NewProcessoSelectivoMatricula } from './processo-selectivo-matricula.model';

export const sampleWithRequiredData: IProcessoSelectivoMatricula = {
  id: 58305,
};

export const sampleWithPartialData: IProcessoSelectivoMatricula = {
  id: 57407,
  dataTeste: dayjs('2023-09-03T00:48'),
};

export const sampleWithFullData: IProcessoSelectivoMatricula = {
  id: 73523,
  localTeste: 'Global',
  dataTeste: dayjs('2023-09-03T12:07'),
  notaTeste: 29768,
  isAdmitido: false,
};

export const sampleWithNewData: NewProcessoSelectivoMatricula = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
