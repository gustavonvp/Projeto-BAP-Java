# 📚 Projeto Integrador: Catálogo de Mídias (Livros, Filmes e Séries)

> **Disciplina:** Projeto Integrador Transdisciplinar (PIT) - Ciência da Computação
> **Metodologia:** Aprendizagem Baseada em Projetos (ABP)

## 🎯 Sobre o Projeto

Este projeto consiste no desenvolvimento de uma aplicação *web* completa para catalogação e gerenciamento de mídias (livros, filmes e séries). O sistema foi desenvolvido seguindo estritamente os princípios da **Programação Orientada a Objetos (POO)** e o padrão de arquitetura **MVC (Model-View-Controller)**, sem o uso de frameworks de alto nível, para demonstrar domínio dos fundamentos da linguagem Java.

O objetivo principal é integrar competências de desenvolvimento *full-stack*, banco de dados e engenharia de software para solucionar um problema real de organização de acervo pessoal.

---

## 🚀 Tecnologias e Arquitetura

O projeto foi construído sobre a plataforma **Jakarta EE** (Java Enterprise Edition), utilizando as seguintes tecnologias mandatórias:

* **Linguagem:** Java 17 (LTS)
* **Front-end (View):** JSP (JavaServer Pages) + JSTL + HTML5/CSS3 (Bootstrap 5)
* **Back-end (Controller):** Java Servlets (Jakarta Servlet API)
* **Persistência (Model):** JDBC (Java Database Connectivity) puro com padrão DAO (Data Access Object)
* **Banco de Dados:** PostgreSQL (via Docker)
* **Gerenciamento de Dependências:** Apache Maven

### 🏗️ Estrutura MVC Adotada

A aplicação segue a separação de responsabilidades exigida na documentação técnica:

1.  **Model (Camada de Dados):** Classes POJO (`Pessoa`, `Livro`) e classes DAO (`PessoaDAO`) responsáveis pelo SQL e conexão via `ConnectionFactory`.
2.  **View (Camada de Apresentação):** Arquivos `.jsp` que renderizam o HTML para o usuário.
3.  **Controller (Camada de Controle):** `Servlets` que interceptam as requisições HTTP, validam dados e orquestram a comunicação entre a View e o Model.

---

## ✨ Funcionalidades

O sistema implementa o **CRUD** completo e funcionalidades de busca:

* ✅ **Cadastro:** Inserção de novos itens e autores no banco de dados.
* ✅ **Leitura (Listagem):** Visualização tabular de todos os itens catalogados.
* ✅ **Edição:** Atualização de dados de obras e autores existentes.
* ✅ **Exclusão:** Remoção de registros do catálogo.
* ✅ **Busca:** Filtragem de itens por título ou autor.
* ✅ **Associação:** Vínculo entre Obras e Autores (Relacionamento N:M).

---

## 🔒 Segurança e Robustez

Conforme os requisitos de segurança da informação (ISO/IEC 27001), o projeto implementa "Secure by Design":

* **Prevenção contra SQL Injection:** Todas as consultas ao banco de dados utilizam **`PreparedStatement`** com parâmetros tipados, impedindo a concatenação direta de strings e a injeção de comandos maliciosos.
* **Tratamento de Exceções:** Uso de blocos `try-catch-finally` para garantir que conexões com o banco sejam fechadas e erros sejam tratados sem derrubar a aplicação.

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Java JDK 17
* Apache Maven
* Docker (para o Banco de Dados)
* VS Code (ou Eclipse) com suporte a Tomcat

### 1. Configurar o Banco de Dados
Suba o container do PostgreSQL utilizando o Docker:

```bash
docker-compose up -d db
