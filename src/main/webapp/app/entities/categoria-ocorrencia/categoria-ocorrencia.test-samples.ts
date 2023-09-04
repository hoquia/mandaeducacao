import { ICategoriaOcorrencia, NewCategoriaOcorrencia } from './categoria-ocorrencia.model';

export const sampleWithRequiredData: ICategoriaOcorrencia = {
  id: 99589,
  codigo: 'Lake online exploit',
  descricao: 'enhance Web Butão',
};

export const sampleWithPartialData: ICategoriaOcorrencia = {
  id: 24007,
  codigo: 'District',
  isNotificaEncaregado: true,
  isSendSms: true,
  descricao: 'navigate castanho',
};

export const sampleWithFullData: ICategoriaOcorrencia = {
  id: 36409,
  codigo: 'Berkshire',
  sansaoDisicplinar: 'optical',
  isNotificaEncaregado: true,
  isSendEmail: true,
  isSendSms: true,
  isSendPush: true,
  descricao: 'Account embrace productize',
  observacao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewCategoriaOcorrencia = {
  codigo: 'action-items Investor',
  descricao: 'Secured',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
