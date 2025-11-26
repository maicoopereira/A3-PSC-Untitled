# A3-PSC-Untitled

## Sobre o Projeto
A3-PSC-Untitled é um programa desenvolvido como parte da disciplina **Programação de Soluções Computacionais (PSC) da UNISUL**.  
O objetivo deste projeto é demonstrar a aplicação prática dos conteúdos da disciplina, incluindo:

- lógica de programação  
- modularização  
- manipulação de banco de dados  
- organização e estruturação de código

> O programa consiste em uma aplicação simples de gerenciamento de estoque. O programa apresenta flags de quantidade e alerta de validade. Além de um sistema de busca de produtos por qualquer informação contida.
> Ele utiliza banco MySQL alocado na porta 3306 do localhost. Antes de utilizar, se necessário, atualize com suas credenciais locais.

---

## Funcionalidades
- Execução das rotinas propostas na atividade A3  
- Código organizado de forma modular  
- Tratamento básico de entrada e saída  
- Estrutura simples adequada ao escopo acadêmico

*Manipulação do estoque via CRUD no MySQL.*

---

## Estrutura do Projeto
```
/A3-PSC-Untitled
  ├─ src/            ← código-fonte  
  ├─ build/          ← arquivos gerados na compilação  
  ├─ README.md       ← documentação do projeto  
  ├─ build.xml       ← arquivo de automação de build (se aplicável)  
  └─ manifest.mf     ← metadados do projeto (se aplicável)
```

---

## Como Compilar e Executar

### Pré-requisitos
- **Java** instalado (versão usada na disciplina ou superior)

### Passo a passo
1. Clone o repositório:
   ```bash
   git clone https://github.com/maicoopereira/A3-PSC-Untitled.git
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd A3-PSC-Untitled
   ```
3. Compile o código:
   ```bash
   javac src/*.java
   ```
4. Execute o programa:
   ```bash
   java Principal
   ```
