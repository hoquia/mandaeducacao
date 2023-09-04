import { ISerieDocumento, NewSerieDocumento } from './serie-documento.model';

export const sampleWithRequiredData: ISerieDocumento = {
  id: 63222,
  versao: 21332,
  serie: 'invoice Chapéu SMS',
};

export const sampleWithPartialData: ISerieDocumento = {
  id: 84548,
  anoFiscal: 56534,
  versao: 98758,
  serie: 'Front-line demand-driven violeta',
  isAtivo: false,
};

export const sampleWithFullData: ISerieDocumento = {
  id: 9721,
  anoFiscal: 22800,
  versao: 88261,
  serie: 'lima generating',
  isAtivo: false,
  isPadrao: true,
};

export const sampleWithNewData: NewSerieDocumento = {
  versao: 54133,
  serie: 'implementation panel',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
