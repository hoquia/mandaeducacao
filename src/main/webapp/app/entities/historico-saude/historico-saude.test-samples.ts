import dayjs from 'dayjs/esm';

import { IHistoricoSaude, NewHistoricoSaude } from './historico-saude.model';

export const sampleWithRequiredData: IHistoricoSaude = {
  id: 33654,
  nome: 'withdrawal Product',
  inicio: dayjs('2023-09-03T02:43'),
  timestamp: dayjs('2023-09-03T22:55'),
};

export const sampleWithPartialData: IHistoricoSaude = {
  id: 90244,
  nome: 'Intranet Teclado',
  descricao: '../fake-data/blob/hipster.txt',
  inicio: dayjs('2023-09-03T20:37'),
  situacaoPrescricao: 'Assistant Tunnel',
  timestamp: dayjs('2023-09-03T21:35'),
};

export const sampleWithFullData: IHistoricoSaude = {
  id: 69421,
  nome: 'back-end',
  descricao: '../fake-data/blob/hipster.txt',
  inicio: dayjs('2023-09-03T07:09'),
  fim: dayjs('2023-09-03T19:02'),
  situacaoPrescricao: 'value-added Cambridgeshire',
  timestamp: dayjs('2023-09-03T03:08'),
  hash: 'Borders navigate Livros',
};

export const sampleWithNewData: NewHistoricoSaude = {
  nome: 'mindshare quantifying',
  inicio: dayjs('2023-09-03T23:45'),
  timestamp: dayjs('2023-09-03T14:15'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
