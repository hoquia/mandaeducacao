import dayjs from 'dayjs/esm';

import { TipoTurma } from 'app/entities/enumerations/tipo-turma.model';
import { CriterioDescricaoTurma } from 'app/entities/enumerations/criterio-descricao-turma.model';
import { CriterioNumeroChamada } from 'app/entities/enumerations/criterio-numero-chamada.model';

import { ITurma, NewTurma } from './turma.model';

export const sampleWithRequiredData: ITurma = {
  id: 7606,
  tipoTurma: TipoTurma['LABORATORIO'],
  sala: 96929,
  descricao: 'capacitor Money ubiquitous',
  lotacao: 58641,
  confirmado: 31064,
};

export const sampleWithPartialData: ITurma = {
  id: 35712,
  chaveComposta: 'hacking applications',
  tipoTurma: TipoTurma['EXAME_ESPECIAL'],
  sala: 48450,
  descricao: 'Plástico',
  lotacao: 14630,
  confirmado: 46764,
  fazInscricaoDepoisMatricula: false,
};

export const sampleWithFullData: ITurma = {
  id: 42283,
  chaveComposta: 'Customizable',
  tipoTurma: TipoTurma['EXAME_ESPECIAL'],
  sala: 63129,
  descricao: 'e-business bus',
  lotacao: 35812,
  confirmado: 38586,
  abertura: dayjs('2023-09-03'),
  encerramento: dayjs('2023-09-03'),
  criterioDescricao: CriterioDescricaoTurma['ALFABETICA'],
  criterioOrdenacaoNumero: CriterioNumeroChamada['DATA_CONFIRMACAO'],
  fazInscricaoDepoisMatricula: false,
  isDisponivel: false,
};

export const sampleWithNewData: NewTurma = {
  tipoTurma: TipoTurma['EXAME'],
  sala: 46138,
  descricao: 'e-tailers',
  lotacao: 17510,
  confirmado: 68471,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
