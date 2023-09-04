import { UnidadeDuracao } from 'app/entities/enumerations/unidade-duracao.model';
import { Suspensao } from 'app/entities/enumerations/suspensao.model';

import { IMedidaDisciplinar, NewMedidaDisciplinar } from './medida-disciplinar.model';

export const sampleWithRequiredData: IMedidaDisciplinar = {
  id: 6543,
  descricao: 'Carro',
  periodo: UnidadeDuracao['AULA'],
  suspensao: Suspensao['EXPULSO'],
};

export const sampleWithPartialData: IMedidaDisciplinar = {
  id: 21108,
  descricao: 'virtual',
  periodo: UnidadeDuracao['ANO'],
  suspensao: Suspensao['ESCOLA'],
};

export const sampleWithFullData: IMedidaDisciplinar = {
  id: 57230,
  descricao: 'Linda castanho hub',
  periodo: UnidadeDuracao['DIA'],
  suspensao: Suspensao['EXPULSO'],
  tempo: 17152,
};

export const sampleWithNewData: NewMedidaDisciplinar = {
  descricao: 'Principal Peso Rato',
  periodo: UnidadeDuracao['AULA'],
  suspensao: Suspensao['AULA'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
