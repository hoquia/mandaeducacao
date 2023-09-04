import { CategoriaClassificacao } from 'app/entities/enumerations/categoria-classificacao.model';

import { IEstadoDisciplinaCurricular, NewEstadoDisciplinaCurricular } from './estado-disciplina-curricular.model';

export const sampleWithRequiredData: IEstadoDisciplinaCurricular = {
  id: 27771,
  classificacao: CategoriaClassificacao['REPROVADO'],
  codigo: 'Sem open-source Nova',
  descricao: 'Buckinghamshire',
  cor: 'Cape',
  valor: 74947,
};

export const sampleWithPartialData: IEstadoDisciplinaCurricular = {
  id: 22721,
  classificacao: CategoriaClassificacao['RECURSO'],
  codigo: 'microchip prata Drive',
  descricao: 'interactive lima monetize',
  cor: 'Jordânia Optimization prata',
  valor: 78670,
};

export const sampleWithFullData: IEstadoDisciplinaCurricular = {
  id: 24701,
  uniqueSituacaoDisciplina: 'Fresco Account Cambridgeshire',
  classificacao: CategoriaClassificacao['EXAME_ESPECIAL'],
  codigo: 'Artesanal Chapéu',
  descricao: 'Liaison multi-byte Engineer',
  cor: 'Object-based Functionality',
  valor: 17271,
};

export const sampleWithNewData: NewEstadoDisciplinaCurricular = {
  classificacao: CategoriaClassificacao['INDEFINIDO'],
  codigo: 'Diverse',
  descricao: 'back-end Plain Infrastructure',
  cor: 'Incrível',
  valor: 78789,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
