import { DiaSemana } from 'app/entities/enumerations/dia-semana.model';

import { IHorario, NewHorario } from './horario.model';

export const sampleWithRequiredData: IHorario = {
  id: 42529,
  diaSemana: DiaSemana['TERCA'],
};

export const sampleWithPartialData: IHorario = {
  id: 67732,
  chaveComposta1: 'Coordinator Yen bluetooth',
  chaveComposta2: 'utilize Inteligente',
  diaSemana: DiaSemana['SABADO'],
};

export const sampleWithFullData: IHorario = {
  id: 83420,
  chaveComposta1: 'Borracha',
  chaveComposta2: 'Customer e-business cultivate',
  diaSemana: DiaSemana['QUARTA'],
};

export const sampleWithNewData: NewHorario = {
  diaSemana: DiaSemana['SEGUNDA'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
