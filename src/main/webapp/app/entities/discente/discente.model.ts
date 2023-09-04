import dayjs from 'dayjs/esm';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { IEncarregadoEducacao } from 'app/entities/encarregado-educacao/encarregado-educacao.model';
import { Sexo } from 'app/entities/enumerations/sexo.model';

export interface IDiscente {
  id: number;
  fotografia?: string | null;
  fotografiaContentType?: string | null;
  nome?: string | null;
  nascimento?: dayjs.Dayjs | null;
  documentoNumero?: string | null;
  documentoEmissao?: dayjs.Dayjs | null;
  documentoValidade?: dayjs.Dayjs | null;
  nif?: string | null;
  sexo?: Sexo | null;
  pai?: string | null;
  mae?: string | null;
  telefonePrincipal?: string | null;
  telefoneParente?: string | null;
  email?: string | null;
  isEncarregadoEducacao?: boolean | null;
  isTrabalhador?: boolean | null;
  isFilhoAntigoConbatente?: boolean | null;
  isAtestadoPobreza?: boolean | null;
  nomeMedico?: string | null;
  telefoneMedico?: string | null;
  instituicaoParticularSaude?: string | null;
  altura?: number | null;
  peso?: number | null;
  isAsmatico?: boolean | null;
  isAlergico?: boolean | null;
  isPraticaEducacaoFisica?: boolean | null;
  isAutorizadoMedicacao?: boolean | null;
  cuidadosEspeciaisSaude?: string | null;
  numeroProcesso?: string | null;
  dataIngresso?: dayjs.Dayjs | null;
  hash?: string | null;
  observacao?: string | null;
  nacionalidade?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  naturalidade?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  tipoDocumento?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  profissao?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  grupoSanguinio?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  necessidadeEspecial?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  encarregadoEducacao?: Pick<IEncarregadoEducacao, 'id' | 'nome'> | null;
}

export type NewDiscente = Omit<IDiscente, 'id'> & { id: null };
