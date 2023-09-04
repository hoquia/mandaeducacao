import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { IDiscente } from 'app/entities/discente/discente.model';
import { TipoEndereco } from 'app/entities/enumerations/tipo-endereco.model';

export interface IEnderecoDiscente {
  id: number;
  tipo?: TipoEndereco | null;
  bairro?: string | null;
  rua?: string | null;
  numeroCasa?: string | null;
  codigoPostal?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  pais?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  provincia?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  municipio?: Pick<ILookupItem, 'id' | 'descricao'> | null;
  discente?: Pick<IDiscente, 'id' | 'nome'> | null;
}

export type NewEnderecoDiscente = Omit<IEnderecoDiscente, 'id'> & { id: null };
