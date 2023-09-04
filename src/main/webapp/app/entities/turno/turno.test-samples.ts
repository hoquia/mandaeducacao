import { ITurno, NewTurno } from './turno.model';

export const sampleWithRequiredData: ITurno = {
  id: 41525,
  codigo: 'Markets',
  nome: 'withdrawal zero auxiliary',
};

export const sampleWithPartialData: ITurno = {
  id: 11798,
  codigo: 'Plains Web Namíbia',
  nome: 'Inteligente Loan Configuration',
};

export const sampleWithFullData: ITurno = {
  id: 21495,
  codigo: 'Metal Peixe',
  nome: 'Peso Money invoice',
};

export const sampleWithNewData: NewTurno = {
  codigo: 'Vietname Dollar Linda',
  nome: 'Turquia',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
