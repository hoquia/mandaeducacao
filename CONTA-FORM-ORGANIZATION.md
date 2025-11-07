# Organização do Formulário de Conta

## Estrutura Aplicada

### **Seções Organizadas (4 seções)**

1. **Imagem da Conta**

   - 🖼️ Imagem (upload com preview)

2. **Classificação da Conta**

   - 🔖 Tipo (select obrigatório)
   - 📝 Título (text obrigatório)

3. **Detalhes Bancários**

   - #️⃣ Número da Conta (text obrigatório)
   - 📊 IBAN (text opcional)
   - 👤 Titular da Conta (text obrigatório)

4. **Configuração**
   - 💲 Moeda (select com lookup)
   - ⭐ Conta Padrão (checkbox como switch)

### **Características Implementadas**

- **Upload de Imagem Modernizado**: Container com preview, informações do arquivo e botão de remoção elegante
- **Fieldsets Semânticos**: Cada seção com legend e ícones contextuais
- **Layout Responsivo**: Grid system com breakpoints para mobile
- **Validação Visual**: Feedback imediato para campos obrigatórios
- **Switch Styling**: Checkbox convertido para switch moderno
- **Ações Centralizadas**: Botões de cancelar e salvar com gradientes e animações

### **Padrão de Cores Aplicado**

- **Azul Carregado** (#0A0F5B): Títulos e texto principal
- **Azul Vibrante** (#1E90FF): Ícones e bordas de foco
- **Amarelo** (#FFD700): Switch ativo e ícones de configuração
- **Prata** (#C0C0C0): Estados desabilitados
- **Branco** (#FFFFFF): Fundos e contraste

### **Melhorias Específicas**

1. **Upload de Imagem**:

   - Container com border dashed e hover effects
   - Preview com dimensões controladas
   - Informações do arquivo com botão de remoção
   - Label customizado para input file

2. **Organização Lógica**:

   - Separação entre aspectos visuais, classificação, detalhes bancários e configuração
   - Agrupamento de campos relacionados
   - Flow natural de preenchimento

3. **Experiência Mobile**:
   - Ações empilhadas em telas pequenas
   - Padding reduzido em mobile
   - Preview de imagem adaptativo

### **Campos de Validação**

- **Obrigatórios**: Tipo, Título, Número da Conta, Titular
- **Opcionais**: Imagem, IBAN, Moeda, Conta Padrão
- **Feedback**: Mensagens de erro contextuais e coloridas

### **Tecnologias Utilizadas**

- Bootstrap 5 com classes modernas (form-select, form-check, etc.)
- FontAwesome para iconografia contextual
- Angular Reactive Forms com validação
- SCSS com mixins e variáveis organizadas
- Responsive design com grid system
