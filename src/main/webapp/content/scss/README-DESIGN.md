# Design Moderno - Manda ERP

## Paleta de Cores

A nova paleta de cores do sistema foi modernizada com as seguintes cores:

| Nome           | HEX     | Uso Principal                        |
| -------------- | ------- | ------------------------------------ |
| Azul Carregado | #0A0F5B | Cor primária escura, textos, navbar  |
| Azul Vibrante  | #1E90FF | Cor primária, botões, links          |
| Amarelo        | #FFD700 | Alertas, warnings, destaques         |
| Prata          | #C0C0C0 | Cor secundária, bordas, placeholders |
| Branco         | #FFFFFF | Fundos, textos em elementos escuros  |

## Variáveis SCSS Disponíveis

```scss
// Cores principais
$azul-carregado: #0a0f5b;
$azul-vibrante: #1e90ff;
$amarelo: #ffd700;
$prata: #c0c0c0;
$branco: #ffffff;

// Gradientes
$gradient-primary: linear-gradient(135deg, $azul-vibrante 0%, $azul-carregado 100%);
$gradient-warning: linear-gradient(135deg, $amarelo 0%, #ffc000 100%);
$gradient-light: linear-gradient(135deg, $branco 0%, $prata 100%);
```

## Classes CSS Modernos

### Animações

- `.fade-in-up` - Animação de entrada de baixo para cima
- `.fade-in-down` - Animação de entrada de cima para baixo
- `.slide-in-left` - Animação de entrada da esquerda
- `.slide-in-right` - Animação de entrada da direita
- `.scale-in` - Animação de escala
- `.pulse-animation` - Animação de pulso

### Efeitos Hover

- `.hover-lift` - Elevação no hover
- `.hover-glow` - Brilho no hover
- `.hover-scale` - Escala no hover

### Componentes Modernos

#### Dashboard Cards

```html
<div class="dashboard-card">
  <div class="dashboard-card-icon primary">
    <i class="fas fa-users"></i>
  </div>
  <div class="dashboard-card-title">Total de Usuários</div>
  <div class="dashboard-card-value">1,247</div>
  <div class="dashboard-card-trend up"><i class="fas fa-arrow-up"></i> +12%</div>
</div>
```

#### Barra de Pesquisa Moderna

```html
<div class="modern-search">
  <input type="text" class="modern-search-input" placeholder="Pesquisar..." />
  <i class="fas fa-search modern-search-icon"></i>
</div>
```

#### Tabela Moderna

```html
<div class="modern-table-container">
  <table class="modern-table">
    <thead>
      <tr>
        <th>Nome</th>
        <th>Email</th>
        <th>Status</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>João Silva</td>
        <td>joao@email.com</td>
        <td><span class="modern-badge success">Ativo</span></td>
      </tr>
    </tbody>
  </table>
</div>
```

#### Formulário Moderno

```html
<div class="modern-form-group">
  <label class="modern-form-label">Nome completo</label>
  <input type="text" class="modern-form-input" placeholder="Digite seu nome" />
</div>
```

#### Badges Modernos

```html
<span class="modern-badge primary">Novo</span>
<span class="modern-badge success">Ativo</span>
<span class="modern-badge warning">Pendente</span>
<span class="modern-badge danger">Inativo</span>
```

#### Timeline Moderno

```html
<div class="modern-timeline">
  <div class="modern-timeline-item">
    <div class="modern-timeline-date">15 Jun 2025</div>
    <div class="modern-timeline-title">Sistema Atualizado</div>
    <div class="modern-timeline-description">Nova versão do sistema foi implementada com sucesso.</div>
  </div>
</div>
```

### Botões Modernos

O sistema agora inclui botões com gradientes e efeitos modernos:

- `.btn-primary` - Botão primário com gradiente azul
- `.btn-warning` - Botão de aviso com gradiente amarelo
- `.btn-secondary` - Botão secundário com gradiente prata

### Efeitos Especiais

#### Glassmorphism

```html
<div class="glass-card">Conteúdo com efeito de vidro</div>
```

#### Texto com Gradiente

```html
<h1 class="gradient-text">Título com Gradiente</h1>
<h2 class="gradient-text-gold">Título Dourado</h2>
```

#### Tooltip Moderno

```html
<span class="modern-tooltip" data-tooltip="Esta é uma dica moderna"> Passe o mouse aqui </span>
```

#### Skeleton Loading

```html
<div class="skeleton skeleton-title"></div>
<div class="skeleton skeleton-text"></div>
<div class="skeleton skeleton-text"></div>
```

## Configurações do Bootstrap

O sistema agora usa as seguintes configurações modernizadas:

- **Shadows**: Habilitado para dar profundidade aos elementos
- **Gradients**: Habilitado para efeitos visuais modernos
- **Border Radius**: Aumentado para um visual mais suave
- **Transitions**: Habilitado para animações fluidas

## Implementação

Para usar os novos estilos, certifique-se de que os seguintes arquivos estão importados:

1. `_bootstrap-variables.scss` - Variáveis customizadas
2. `_animations.scss` - Animações e efeitos
3. `_modern-components.scss` - Componentes modernos
4. `global.scss` - Estilos globais atualizados

Todos os componentes foram projetados para serem responsivos e acessíveis, mantendo a compatibilidade com os padrões modernos de desenvolvimento web.
