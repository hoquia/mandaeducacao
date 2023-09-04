import dayjs from 'dayjs/esm';

import { IFormacaoDocente, NewFormacaoDocente } from './formacao-docente.model';

export const sampleWithRequiredData: IFormacaoDocente = {
  id: 16155,
  instituicaoEnsino: 'sensor IB Licenciado',
  areaFormacao: 'Liaison',
  grau: 'Birmânia',
  inicio: dayjs('2023-09-03'),
};

export const sampleWithPartialData: IFormacaoDocente = {
  id: 33558,
  instituicaoEnsino: 'Auto International Fort',
  areaFormacao: 'Credit navigating',
  curso: 'Cambridgeshire interactive card',
  especialidade: 'Mão',
  grau: 'firewall payment Artesanal',
  inicio: dayjs('2023-09-03'),
  anexo: '../fake-data/blob/hipster.png',
  anexoContentType: 'unknown',
};

export const sampleWithFullData: IFormacaoDocente = {
  id: 44053,
  instituicaoEnsino: 'rosa cultivate',
  areaFormacao: 'Feito RSS Human',
  curso: 'Falls',
  especialidade: 'Cambridgeshire Customer-focused back-end',
  grau: 'New',
  inicio: dayjs('2023-09-03'),
  fim: dayjs('2023-09-03'),
  anexo: '../fake-data/blob/hipster.png',
  anexoContentType: 'unknown',
};

export const sampleWithNewData: NewFormacaoDocente = {
  instituicaoEnsino: 'Madeira',
  areaFormacao: 'heuristic',
  grau: 'Market Inverse',
  inicio: dayjs('2023-09-03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
