import { IPeriodoHorario, NewPeriodoHorario } from './periodo-horario.model';

export const sampleWithRequiredData: IPeriodoHorario = {
  id: 49810,
  descricao: 'Plástico Cambridgeshire firmware',
  tempo: 85081,
  inicio: 'Tactics',
  fim: 'Multi-tiered integrate',
};

export const sampleWithPartialData: IPeriodoHorario = {
  id: 50732,
  descricao: 'Peixe Nebraska',
  tempo: 48988,
  inicio: 'Madagáscar enable',
  fim: 'Plástico International',
};

export const sampleWithFullData: IPeriodoHorario = {
  id: 67647,
  descricao: 'Chapéu compress',
  tempo: 18903,
  inicio: 'Santa',
  fim: 'Pizza convergence',
};

export const sampleWithNewData: NewPeriodoHorario = {
  descricao: 'Chapéu deposit Berkshire',
  tempo: 8423,
  inicio: 'extranet Assimilated',
  fim: 'bus magenta',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
