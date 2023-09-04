import { IPlanoDesconto, NewPlanoDesconto } from './plano-desconto.model';

export const sampleWithRequiredData: IPlanoDesconto = {
  id: 13041,
  codigo: 'Camp Bacon',
  nome: 'panel Chapéu niches',
  desconto: 64648,
};

export const sampleWithPartialData: IPlanoDesconto = {
  id: 17673,
  codigo: 'state dynamic',
  nome: 'parsing bluetooth Brinquedos',
  isIsentoJuro: true,
  desconto: 13590,
};

export const sampleWithFullData: IPlanoDesconto = {
  id: 92144,
  codigo: 'Luvas',
  nome: 'Dam',
  isIsentoMulta: true,
  isIsentoJuro: true,
  desconto: 66322,
};

export const sampleWithNewData: NewPlanoDesconto = {
  codigo: 'payment Computador Rato',
  nome: 'Savings PNG Fantástico',
  desconto: 65268,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
