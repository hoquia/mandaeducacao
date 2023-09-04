export interface IMeioPagamento {
  id: number;
  imagem?: string | null;
  imagemContentType?: string | null;
  codigo?: string | null;
  nome?: string | null;
  numeroDigitoReferencia?: number | null;
  isPagamentoInstantanio?: boolean | null;
  hash?: string | null;
  link?: string | null;
  token?: string | null;
  username?: string | null;
  password?: string | null;
  formatoReferencia?: string | null;
}

export type NewMeioPagamento = Omit<IMeioPagamento, 'id'> & { id: null };
