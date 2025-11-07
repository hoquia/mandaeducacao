# Organização do Formulário de Categoria Emolumento

## Estrutura Aplicada

### **Seções Organizadas (3 seções)**

1. **Informações Básicas**

   - 🏷️ Nome da Categoria (text obrigatório)
   - 🎨 Cor de Identificação (color picker + text)
   - 📄 Descrição (textarea opcional)

2. **Tipo e Configuração**

   - 🔔 É Categoria de Serviço (switch)

3. **Regras Financeiras**
   - 🛡️ Isento de Multa (switch)
   - 📊 Isento de Juros (switch)
   - ⚠️ Plano de Multa Associado (select)

### **Características Implementadas**

- **Color Picker Avançado**: Input de cor com preview em tempo real e campo de texto hexadecimal
- **Switches Modernos**: Checkboxes convertidos para switches com containers estilizados
- **Organização Financeira**: Seção específica para regras de multas e juros
- **Fieldsets Semânticos**: Cada seção com legend e ícones contextuais
- **Layout Responsivo**: Grid system adaptável para mobile
- **Validação Visual**: Feedback imediato para campos obrigatórios
- **Textos de Ajuda**: Explicações contextuais para switches e selects

### **Padrão de Cores Aplicado**

- **Verde Principal** (#4caf50): Tema principal do formulário (financeiro/emolumentos)
- **Azul Vibrante** (#1E90FF): Seção de informações básicas
- **Roxo** (#9c27b0): Seção de tipo e configuração
- **Laranja** (#ff9800): Seção de regras financeiras e switches ativos
- **Azul Carregado** (#0A0F5B): Textos e labels
- **Branco** (#FFFFFF): Fundos e contraste

### **Melhorias Específicas**

1. **Color Picker**:

   - Input nativo de cor com styling customizado
   - Campo de texto complementar mostrando valor hexadecimal
   - Container flexível para melhor UX
   - Estados de hover e focus

2. **Switches de Regras Financeiras**:

   - Containers com background sutil e bordas
   - Hover effects específicos para cada tipo
   - Textos de ajuda explicativos
   - Ícones contextuais para cada regra

3. **Organização Lógica**:

   - Informações básicas primeiro (nome, cor, descrição)
   - Tipo de categoria (serviço ou não)
   - Regras financeiras específicas (multas e juros)

4. **Select de Plano de Multa**:
   - Placeholder informativo
   - Texto de ajuda explicativo
   - Integração com regras de isenção

### **Campos de Validação**

- **Obrigatórios**: Nome da Categoria
- **Opcionais**: Cor, Descrição, Todas as configurações de switches, Plano de Multa
- **Condicionais**: Plano de Multa (relacionado com isenção de multa)
- **Feedback**: Mensagens de erro contextuais e textos de ajuda

### **Experiência com Color Picker**

- **Input Color**: Seletor nativo de cores do browser
- **Preview**: Valor hexadecimal em tempo real
- **Fallback**: Campo de texto para entrada manual
- **Styling**: Container flexível com gaps apropriados
- **Responsive**: Empilhamento em mobile

### **Switches com Contexto**

- **Serviço**: Switch roxo para indicar tipo de categoria
- **Isenções**: Switches laranja para regras financeiras
- **Textos de Ajuda**: Explicações claras do propósito de cada opção
- **Estados Visuais**: Cores diferentes para diferentes contextos

### **Tecnologias Utilizadas**

- Bootstrap 5 form-switch para toggles modernos
- HTML5 color input para seleção de cores
- FontAwesome para iconografia contextual
- Angular Reactive Forms com validação
- SCSS com tema verde/financeiro
- Grid system responsivo

### **Ícones Contextuais Aplicados**

- 💰 **money-bill-wave**: Título principal (categoria emolumento)
- ℹ️ **info-circle**: Informações básicas
- ⚙️ **cogs**: Tipo e configuração
- 🧮 **calculator**: Regras financeiras
- 🏷️ **tag**: Nome da categoria
- 🎨 **palette**: Cor
- 📄 **file-alt**: Descrição
- 🔔 **concierge-bell**: Serviço
- 🛡️ **shield-alt**: Isenção de multa
- 📊 **percentage**: Isenção de juros
- ⚠️ **exclamation-triangle**: Plano de multa

### **Textos de Ajuda Implementados**

- **Serviço**: "Marque se esta categoria representa um serviço específico da instituição"
- **Isento de Multa**: "Categoria não sofre aplicação de multas por atraso"
- **Isento de Juros**: "Categoria não sofre aplicação de juros por atraso"
- **Plano de Multa**: "Define qual plano de multa será aplicado quando não há isenção"
