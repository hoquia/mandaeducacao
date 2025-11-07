# Formulário de Plano Curricular - Organização Moderna

## Status: ✅ MODERNIZADO COMPLETAMENTE

### Estrutura do Formulário

O formulário de **Plano Curricular** foi organizado seguindo o padrão moderno estabelecido com 6 seções lógicas principais. Este é um formulário complexo com mais de 25 campos organizados de forma hierárquica:

#### 1. **Configuração Básica** 🎓

- **Classe**: Classe para a qual se aplica o plano (obrigatório)
- **Curso**: Curso associado ao plano curricular (obrigatório)

#### 2. **Critérios da Situação da Disciplina** ✅

- **Nº Disciplinas Aprova**: Mínimo de disciplinas para aprovação (obrigatório)
- **Nº Disciplinas Reprova**: Máximo de disciplinas para reprovação (obrigatório)
- **Nº Disciplinas Recurso**: Disciplinas elegíveis para recurso (obrigatório)
- **Nº Disciplinas Exame**: Disciplinas que vão a exame (obrigatório)
- **Nº Disciplinas Exame Especial**: Disciplinas para exame especial (obrigatório)
- **Nº Faltas Reprova**: Máximo de faltas antes da reprovação (obrigatório)

#### 3. **Peso das Médias** ⚖️

- **Peso Média 1**: Peso percentual da primeira média (0-100%)
- **Peso Média 2**: Peso percentual da segunda média (0-100%)
- **Peso Média 3**: Peso percentual da terceira média (0-100%)
- **Peso Recurso**: Peso do recurso (0-100%)
- **Peso Exame**: Peso do exame (0-100%)
- **Peso Exame Especial**: Peso do exame especial (0-100%)
- **Peso Nota Conselho**: Peso da nota do conselho (0-100%)

#### 4. **Sigla das Avaliações** 📝

- **Sigla Prova 1**: Abreviação para a primeira prova (obrigatório, máx 10 chars)
- **Sigla Prova 2**: Abreviação para a segunda prova (obrigatório, máx 10 chars)
- **Sigla Prova 3**: Abreviação para a terceira prova (obrigatório, máx 10 chars)

#### 5. **Sigla das Médias** 📊

- **Sigla Média 1**: Abreviação para a primeira média (obrigatório, máx 10 chars)
- **Sigla Média 2**: Abreviação para a segunda média (obrigatório, máx 10 chars)
- **Sigla Média 3**: Abreviação para a terceira média (obrigatório, máx 10 chars)

#### 6. **Fórmulas de Classificação** 🧮

- **Fórmula Classificação Final**: Fórmula para calcular a classificação final (obrigatório)
- **Fórmula Média**: Fórmula para calcular a média (obrigatório)
- **Fórmula Dispensa**: Condição para dispensa de exame (obrigatório)
- **Fórmula Exame**: Fórmula para nota com exame (obrigatório)
- **Fórmula Recurso**: Fórmula para nota de recurso (obrigatório)
- **Fórmula Exame Especial**: Fórmula para exame especial (obrigatório)

### Características Implementadas

#### ✅ Estrutura HTML Moderna

- Container → Wrapper → Form → Sections → Fields
- 6 seções semânticas com `fieldset` e `legend`
- Grid responsivo adaptativo (col-md-4, col-md-6, col-md-3)
- Labels tradicionais com ícones específicos por contexto

#### ✅ Design System Aplicado

- **Cores Temáticas Específicas**:
  - Azul Vibrante (#1E90FF) - Primary/Configuração básica
  - Verde (#28a745) - Critérios de disciplina
  - Laranja (#fd7e14) - Pesos das médias
  - Roxo (#6f42c1) - Siglas/Abreviações
  - Vermelho (#dc3545) - Fórmulas
  - Azul Carregado (#0A0F5B) - Texto principal
  - Prata (#C0C0C0) - Elementos secundários

#### ✅ Campos Especializados por Seção

1. **ng-select** para Classe/Curso com busca e placeholder
2. **Campos numéricos** com validação min/max e gradientes temáticos
3. **Campos de texto para siglas** com transformação automática em maiúsculas
4. **Campos de fórmula** com fonte monospace e tooltips informativos

#### ✅ Validações Complexas

- **Campos obrigatórios**: 16 campos obrigatórios identificados
- **Validação numérica**: Min/max para pesos (0-100%), min para critérios (>0)
- **Validação de texto**: Maxlength para siglas (10 caracteres)
- **Feedback visual**: Estados de erro específicos por tipo de campo

#### ✅ UX Enhancements Avançadas

- **Placeholders contextuais**: Exemplos específicos para cada campo
- **Form text diferenciado**: Explicações claras do propósito de cada campo
- **Indicadores visuais laterais**: Cores por seção para organização visual
- **Animações escalonadas**: Entrada progressiva das 6 seções
- **Estados de hover específicos**: Cores diferentes por tipo de seção

#### ✅ Responsividade Complexa

- **Layout adaptativo**: 3-4 colunas em desktop, empilhamento em mobile
- **ng-select responsivo**: Ajuste de altura e comportamento em mobile
- **Botões full-width**: Em dispositivos móveis
- **Espaçamento inteligente**: g-3 para gaps responsivos

### Lógica de Organização Hierárquica

#### Seção 1: Configuração Básica (Identidade)

**Objetivo**: Definir QUAL plano curricular está sendo configurado

- Estabelece a base: classe + curso

#### Seção 2: Critérios da Situação da Disciplina (Regras)

**Objetivo**: Definir QUANTAS disciplinas determinam cada situação

- Aprovação, reprovação, recursos, exames
- Critérios de faltas

#### Seção 3: Peso das Médias (Importância)

**Objetivo**: Definir QUANTO PESO cada avaliação tem

- Distribuição percentual das médias
- Pesos de recursos e exames

#### Seção 4: Sigla das Avaliações (Nomenclatura - Provas)

**Objetivo**: Definir COMO as provas são identificadas

- P1, P2, P3 ou AC1, AC2, AC3, etc.

#### Seção 5: Sigla das Médias (Nomenclatura - Médias)

**Objetivo**: Definir COMO as médias são identificadas

- M1, M2, M3 ou MT1, MT2, MT3, etc.

#### Seção 6: Fórmulas de Classificação (Cálculos)

**Objetivo**: Definir COMO os cálculos são realizados

- Fórmulas matemáticas para cada tipo de situação

### Validações Implementadas

#### Campos Obrigatórios (16):

1. Classe, 2. Curso
2. Nº Disciplinas Aprova, 4. Nº Disciplinas Reprova, 5. Nº Disciplinas Recurso
3. Nº Disciplinas Exame, 7. Nº Disciplinas Exame Especial, 8. Nº Faltas Reprova
4. Sigla Prova 1, 10. Sigla Prova 2, 11. Sigla Prova 3
5. Sigla Média 1, 13. Sigla Média 2, 14. Sigla Média 3
6. Fórmula Classificação Final, 16. Fórmula Média, 17. Fórmula Dispensa
7. Fórmula Exame, 19. Fórmula Recurso, 20. Fórmula Exame Especial

#### Validações Específicas:

- **Critérios numéricos**: Valores > 0
- **Pesos**: Valores entre 0-100
- **Siglas**: Máximo 10 caracteres, transformadas em maiúsculas
- **Fórmulas**: Formato livre com tooltip de orientação

### Arquivos Implementados

1. **HTML**: `plano-curricular-update.component.html`

   - Estrutura moderna com 6 seções hierárquicas
   - 25+ campos organizados logicamente
   - ng-select para dropdowns complexos
   - Validação completa com feedback visual

2. **SCSS**: `plano-curricular-update.component.scss`
   - Sistema de cores temáticas por seção
   - Campos especializados (numbers, text, formulas, ng-select)
   - Animações escalonadas e indicadores visuais
   - Responsividade complexa para múltiplas colunas

### Características Visuais Especiais

#### Indicadores Visuais por Seção

- **Configuração Básica**: Ícones de school/book, sem indicador lateral
- **Critérios**: Barra lateral verde, ícones de check/times/redo
- **Pesos**: Barra lateral laranja, ícones de percentage/balance
- **Siglas**: Barra lateral roxa, ícones de font/edit/chart
- **Fórmulas**: Barra lateral vermelha, fonte monospace, ícones matemáticos

#### Animações Progressivas

- **6 seções** com delays de 0.1s a 0.6s
- **Form actions** com delay de 0.7s
- **Efeitos de hover** específicos por tipo de campo
- **Transformações sutis** (scale 1.01) em focus

#### Estados Interativos Avançados

- **ng-select customizado** com cores temáticas
- **Tooltips automáticos** nos campos de fórmula
- **Hover diferenciado** por seção (verde, laranja, roxo, vermelho)
- **Loading states** nos botões

### Integração com Sistema

- **ng-select**: Integração completa com Angular Material
- **Reactive Forms**: Validação complexa multi-seção
- **SCSS Libraries**: Uso extensivo de mixins e variáveis
- **Icons**: FontAwesome com ícones contextuais específicos

### Padrão para Formulários Complexos

Este formulário serve como **template definitivo para formulários complexos** com:

- **Múltiplas seções lógicas** (5-7 seções)
- **Campos especializados** (dropdowns, números, texto, fórmulas)
- **Validações complexas** (obrigatórios, ranges, formatos)
- **Organização hierárquica** (configuração → regras → cálculos)
- **Responsividade avançada** (3-4 colunas adaptativas)

### Próximos Formulários Complexos

**Formulários que podem seguir esta estrutura**:

1. **Configuração de Sistema de Notas**
2. **Plano de Avaliação Detalhado**
3. **Configuração de Calendário Acadêmico**
4. **Sistema de Matrículas Complexo**

**Padrão a replicar**:

1. **Seção de Identidade** (2-3 campos obrigatórios)
2. **Seções de Regras/Critérios** (4-8 campos numéricos)
3. **Seções de Configuração** (3-6 campos especializados)
4. **Seção de Nomenclatura** (3-5 campos de texto)
5. **Seção de Cálculos/Fórmulas** (4-6 campos complexos)
6. **Validação hierárquica** e **feedback visual especializado**
