import { TipoAula } from 'app/entities/enumerations/tipo-aula.model';
import { EstadoLicao } from 'app/entities/enumerations/estado-licao.model';

import { IPlanoAula, NewPlanoAula } from './plano-aula.model';

export const sampleWithRequiredData: IPlanoAula = {
  id: 30984,
  tipoAula: TipoAula['OUTRO'],
  semanaLectiva: 39234,
  perfilEntrada: '../fake-data/blob/hipster.txt',
  perfilSaida: '../fake-data/blob/hipster.txt',
  assunto: 'hack',
  objectivoGeral: '../fake-data/blob/hipster.txt',
  objectivosEspecificos: '../fake-data/blob/hipster.txt',
  estado: EstadoLicao['APROVADA'],
};

export const sampleWithPartialData: IPlanoAula = {
  id: 58337,
  tipoAula: TipoAula['PRATICA'],
  semanaLectiva: 80439,
  perfilEntrada: '../fake-data/blob/hipster.txt',
  perfilSaida: '../fake-data/blob/hipster.txt',
  assunto: 'Hills programming',
  objectivoGeral: '../fake-data/blob/hipster.txt',
  objectivosEspecificos: '../fake-data/blob/hipster.txt',
  estado: EstadoLicao['CANCELADA'],
};

export const sampleWithFullData: IPlanoAula = {
  id: 61353,
  tipoAula: TipoAula['APRESENTACAO'],
  semanaLectiva: 19185,
  perfilEntrada: '../fake-data/blob/hipster.txt',
  perfilSaida: '../fake-data/blob/hipster.txt',
  assunto: 'Toalhas portal',
  objectivoGeral: '../fake-data/blob/hipster.txt',
  objectivosEspecificos: '../fake-data/blob/hipster.txt',
  tempoTotalLicao: 12543,
  estado: EstadoLicao['APROVADA'],
};

export const sampleWithNewData: NewPlanoAula = {
  tipoAula: TipoAula['TEORICA'],
  semanaLectiva: 49281,
  perfilEntrada: '../fake-data/blob/hipster.txt',
  perfilSaida: '../fake-data/blob/hipster.txt',
  assunto: 'Cadeira logistical',
  objectivoGeral: '../fake-data/blob/hipster.txt',
  objectivosEspecificos: '../fake-data/blob/hipster.txt',
  estado: EstadoLicao['ADIADA'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
