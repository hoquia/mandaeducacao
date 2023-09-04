import { ModuloDocumento } from 'app/entities/enumerations/modulo-documento.model';
import { OrigemDocumento } from 'app/entities/enumerations/origem-documento.model';
import { DocumentoFiscal } from 'app/entities/enumerations/documento-fiscal.model';

import { IDocumentoComercial, NewDocumentoComercial } from './documento-comercial.model';

export const sampleWithRequiredData: IDocumentoComercial = {
  id: 10802,
  modulo: ModuloDocumento['FINANCA'],
  origem: OrigemDocumento['P'],
  siglaInterna: 'SCSI G',
  descricao: 'Orchestrator Jardim alarm',
  siglaFiscal: DocumentoFiscal['OR'],
};

export const sampleWithPartialData: IDocumentoComercial = {
  id: 17055,
  modulo: ModuloDocumento['COMPRA'],
  origem: OrigemDocumento['P'],
  siglaInterna: 'sticky',
  descricao: 'program',
  siglaFiscal: DocumentoFiscal['AC'],
  isMovimentaCaixa: false,
  isEnviaSMS: false,
  isEnviaPush: false,
};

export const sampleWithFullData: IDocumentoComercial = {
  id: 94615,
  modulo: ModuloDocumento['VENDA'],
  origem: OrigemDocumento['P'],
  siglaInterna: 'Buckin',
  descricao: 'Chapéu Orchard',
  siglaFiscal: DocumentoFiscal['NR'],
  isMovimentaEstoque: true,
  isMovimentaCaixa: true,
  isNotificaEntidade: true,
  isNotificaGerente: false,
  isEnviaSMS: true,
  isEnviaEmail: false,
  isEnviaPush: true,
  validaCreditoDisponivel: true,
};

export const sampleWithNewData: NewDocumentoComercial = {
  modulo: ModuloDocumento['VENDA'],
  origem: OrigemDocumento['I'],
  siglaInterna: 'Connec',
  descricao: 'Frango HTTP Pizza',
  siglaFiscal: DocumentoFiscal['GC'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
