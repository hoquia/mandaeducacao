import { MetodoAplicacaoMulta } from 'app/entities/enumerations/metodo-aplicacao-multa.model';

import { IPlanoMulta, NewPlanoMulta } from './plano-multa.model';

export const sampleWithRequiredData: IPlanoMulta = {
  id: 80617,
  descricao: 'bluetooth',
  diaAplicacaoMulta: 22,
  metodoAplicacaoMulta: MetodoAplicacaoMulta['EXACTO_MES_EMOLUMENTO'],
  taxaMulta: 22986,
};

export const sampleWithPartialData: IPlanoMulta = {
  id: 69087,
  descricao: 'connect',
  diaAplicacaoMulta: 20,
  metodoAplicacaoMulta: MetodoAplicacaoMulta['DEPOIS_MES_EMOLUMENTO'],
  taxaMulta: 75546,
  isTaxaMultaPercentual: false,
  diaAplicacaoJuro: 27,
  isTaxaJuroPercentual: true,
  aumentarJuroEmDias: 24922,
};

export const sampleWithFullData: IPlanoMulta = {
  id: 84700,
  descricao: 'Arkansas Carolina attitude',
  diaAplicacaoMulta: 27,
  metodoAplicacaoMulta: MetodoAplicacaoMulta['DEPOIS_MES_EMOLUMENTO'],
  taxaMulta: 90136,
  isTaxaMultaPercentual: false,
  diaAplicacaoJuro: 11,
  metodoAplicacaoJuro: MetodoAplicacaoMulta['EXACTO_MES_EMOLUMENTO'],
  taxaJuro: 33719,
  isTaxaJuroPercentual: false,
  aumentarJuroEmDias: 17059,
  isAtivo: true,
};

export const sampleWithNewData: NewPlanoMulta = {
  descricao: 'Teclado SMS Salsichas',
  diaAplicacaoMulta: 30,
  metodoAplicacaoMulta: MetodoAplicacaoMulta['DEPOIS_MES_EMOLUMENTO'],
  taxaMulta: 16403,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
