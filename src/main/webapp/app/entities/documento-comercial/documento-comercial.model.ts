import { ModuloDocumento } from 'app/entities/enumerations/modulo-documento.model';
import { OrigemDocumento } from 'app/entities/enumerations/origem-documento.model';
import { DocumentoFiscal } from 'app/entities/enumerations/documento-fiscal.model';

export interface IDocumentoComercial {
  id: number;
  modulo?: ModuloDocumento | null;
  origem?: OrigemDocumento | null;
  siglaInterna?: string | null;
  descricao?: string | null;
  siglaFiscal?: DocumentoFiscal | null;
  isMovimentaEstoque?: boolean | null;
  isMovimentaCaixa?: boolean | null;
  isNotificaEntidade?: boolean | null;
  isNotificaGerente?: boolean | null;
  isEnviaSMS?: boolean | null;
  isEnviaEmail?: boolean | null;
  isEnviaPush?: boolean | null;
  validaCreditoDisponivel?: boolean | null;
  transformaEm?: Pick<IDocumentoComercial, 'id' | 'descricao'> | null;
}

export type NewDocumentoComercial = Omit<IDocumentoComercial, 'id'> & { id: null };
