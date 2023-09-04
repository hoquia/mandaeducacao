import { IDisciplinaCurricular, NewDisciplinaCurricular } from './disciplina-curricular.model';

export const sampleWithRequiredData: IDisciplinaCurricular = {
  id: 66803,
  descricao: 'solution',
  mediaParaExame: 28371,
  mediaParaRecurso: 5724,
  mediaParaExameEspecial: 56419,
  mediaParaDespensar: 73029,
};

export const sampleWithPartialData: IDisciplinaCurricular = {
  id: 33569,
  uniqueDisciplinaCurricular: 'reboot',
  descricao: 'ubiquitous',
  cargaSemanal: 92606,
  isTerminal: false,
  mediaParaExame: 94695,
  mediaParaRecurso: 56027,
  mediaParaExameEspecial: 33091,
  mediaParaDespensar: 3422,
};

export const sampleWithFullData: IDisciplinaCurricular = {
  id: 15484,
  uniqueDisciplinaCurricular: 'facilitate transmitter Account',
  descricao: 'robust dynamic',
  cargaSemanal: 27355,
  isTerminal: false,
  mediaParaExame: 66423,
  mediaParaRecurso: 2817,
  mediaParaExameEspecial: 43848,
  mediaParaDespensar: 76396,
};

export const sampleWithNewData: NewDisciplinaCurricular = {
  descricao: 'Itália',
  mediaParaExame: 48649,
  mediaParaRecurso: 73334,
  mediaParaExameEspecial: 7488,
  mediaParaDespensar: 33400,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
