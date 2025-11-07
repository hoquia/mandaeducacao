# Docente Form Organization

## Overview

The **Docente** (Teacher) form has been modernized following the established design pattern. This is an **advanced form** with 7 logical sections organizing comprehensive teacher information including personal data, documentation, professional details, and academic credentials.

## Form Structure

### 1. **Fotografia (Photography) Section**

- **Purpose**: Photo upload and management
- **Fields**: 1
  - `fotografia` (Photo upload with preview)
- **Styling**: Green theme (success color) for media upload
- **Features**:
  - Drag-and-drop interface
  - Image preview with metadata
  - File size and format validation
  - Remove photo functionality

### 2. **Dados Pessoais (Personal Data) Section**

- **Purpose**: Core personal identification information
- **Fields**: 8
  - `nome` (Full Name) - Required
  - `sexo` (Gender) - Required, dropdown
  - `nascimento` (Birth Date) - Required, date picker
  - `estadoCivil` (Marital Status) - Required, dropdown
  - `nacionalidade` (Nationality) - Required, dropdown
  - `naturalidade` (Place of Birth) - Required, dropdown
  - `pai` (Father's Name) - Required
  - `mae` (Mother's Name) - Required
- **Layout**: 3 rows with responsive grid (6-3-3, 4-4-4, 6-6)
- **Validation**: All fields required with contextual error messages

### 3. **Documentação (Documentation) Section**

- **Purpose**: Official documents and identification
- **Fields**: 6
  - `nif` (Tax ID) - Required
  - `inss` (Social Security) - Optional
  - `tipoDocumento` (Document Type) - Required, dropdown
  - `documentoNumero` (Document Number) - Required
  - `documentoEmissao` (Issue Date) - Required, date picker
  - `documentoValidade` (Expiry Date) - Required, date picker
- **Styling**: Yellow/warning theme for important documentation
- **Layout**: 2 rows (6-6, 3-3-3-3)

### 4. **Contacto (Contact) Section**

- **Purpose**: Communication information
- **Fields**: 3
  - `telefonePrincipal` (Primary Phone) - Required, tel input
  - `telefoneParente` (Emergency Contact) - Optional, tel input
  - `email` (Email) - Required, email validation
- **Styling**: Info/blue theme for communication
- **Layout**: Single row (4-4-4)
- **Features**: Proper input types (tel, email) with validation

### 5. **Endereço (Address) Section**

- **Purpose**: Residential address information
- **Fields**: 1
  - `residencia` (Residence) - Required, textarea
- **Layout**: Full width textarea
- **Features**: Multi-line address input with placeholder guidance

### 6. **Informação Profissional (Professional Information) Section**

- **Purpose**: Employment and professional details
- **Fields**: 4
  - `dataInicioFuncoes` (Start Date) - Required, date picker
  - `numeroAgente` (Agent Number) - Required
  - `categoriaProfissional` (Professional Category) - Required, dropdown
  - `temAgregacaoPedagogica` (Has Pedagogical Training) - Boolean, toggle switch
- **Styling**: Silver/gray theme for professional data
- **Layout**: 2 rows (4-4-4, 12)
- **Features**: Modern toggle switch for boolean field

### 7. **Informação Académica (Academic Information) Section**

- **Purpose**: Educational background and institutional affiliation
- **Fields**: 2
  - `grauAcademico` (Academic Degree) - Required, dropdown
  - `unidadeOrganica` (Organizational Unit) - Required, dropdown
- **Styling**: Purple theme for academic credentials
- **Layout**: Single row (6-6)

### 8. **Observações (Observations) Section**

- **Purpose**: Additional notes and comments
- **Fields**: 1
  - `observacao` (Observations) - Optional, textarea
- **Layout**: Full width textarea
- **Features**: Large textarea for detailed notes

## Technical Implementation

### Modern Features Applied

1. **Progressive Animation**: Staggered section animations (0.1s to 0.8s delays)
2. **Color-Coded Themes**: Each section has contextual color coding
3. **Responsive Design**: Mobile-optimized layouts with proper breakpoints
4. **Advanced Input Types**: tel, email, date pickers, file upload
5. **Validation Framework**: Comprehensive client-side validation
6. **Accessibility**: Proper ARIA labels, focus management, keyboard navigation

### Styling Approach

- **Professional Education Theme**: Blue primary with contextual section colors
- **Photo Section**: Green (success) for media management
- **Documentation**: Yellow (warning) for important documents
- **Contact**: Blue (info) for communication
- **Professional**: Gray (neutral) for work information
- **Academic**: Purple for educational credentials
- **Clean Form Actions**: Centered buttons with hover effects

### Form Complexity Classification

**Classification**: Advanced Form

- **Sections**: 7 logical sections
- **Total Fields**: 25+ including hidden fields
- **Specialized Components**: Photo upload, date pickers, toggle switches
- **Validation Rules**: 15+ required fields with cross-field validation
- **Layout Complexity**: Multi-row responsive grids with varying column spans

## Field Organization Logic

### Primary Information (Sections 1-2)

- Photo and core personal identification
- Most frequently accessed and updated information

### Official Documentation (Section 3)

- Legal and administrative documents
- Critical for institutional compliance

### Contact & Address (Sections 4-5)

- Communication and location information
- Grouped together for logical flow

### Professional Context (Sections 6-7)

- Employment and academic information
- Institutional relationship data

### Additional Information (Section 8)

- Supplementary notes and observations
- Optional detailed information

## Validation Strategy

### Required Field Distribution

- **Personal Data**: 8/8 fields required (100%)
- **Documentation**: 5/6 fields required (83%)
- **Contact**: 2/3 fields required (67%)
- **Address**: 1/1 field required (100%)
- **Professional**: 3/4 fields required (75%)
- **Academic**: 2/2 fields required (100%)
- **Observations**: 0/1 fields required (0%)

### Input Type Optimization

- **Text**: General information fields
- **Email**: Proper email validation
- **Tel**: Phone number formatting
- **Date**: Calendar picker integration
- **Select**: Dropdown for predefined values
- **Textarea**: Multi-line text areas
- **Checkbox**: Boolean toggle switches
- **File**: Image upload with preview

This comprehensive form provides complete teacher registration functionality while maintaining user-friendly organization and modern design principles.
