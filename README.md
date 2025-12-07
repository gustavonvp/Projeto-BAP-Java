# 📚 Projeto Integrador: Catálogo de Mídias (Livros e Filmes)

> **Disciplina:** Projeto Integrador Transdisciplinar (PIT) - Ciência da Computação
> **Metodologia:** Aprendizagem Baseada em Projetos (ABP)

## 🎯 Apresentação do Projeto

Este projeto consiste no desenvolvimento de uma aplicação *web* para catalogação e gerenciamento de mídias (livros, filmes e séries). [cite_start]O sistema foi desenvolvido seguindo estritamente os princípios da **Programação Orientada a Objetos (POO)** e o padrão de arquitetura **MVC (Model-View-Controller)**, atendendo aos requisitos da disciplina de PIT[cite: 57, 67].

[cite_start]O objetivo é integrar competências de desenvolvimento *full-stack*, demonstrando domínio sobre a persistência de dados e segurança da informação sem o uso de *frameworks* de alto nível (como Spring), privilegiando a implementação "raiz" com **Jakarta EE**[cite: 283].

---

## 🏗️ Arquitetura e Tecnologias

[cite_start]A solução foi construída sobre a especificação **Jakarta EE 10**, utilizando as seguintes tecnologias mandatórias descritas no material teórico [cite: 283-287]:

* **Linguagem:** Java 17 (LTS)
* **Front-end (View):** JSP (JavaServer Pages) + JSTL + HTML5/Bootstrap.
* **Back-end (Controller):** Java Servlets (Jakarta Servlet API).
* **Persistência (Model):** JDBC (Java Database Connectivity) puro com padrão DAO.
* **Banco de Dados:** PostgreSQL (Instalação Local).
* **Servidor de Aplicação:** Apache Tomcat 10.

### Estrutura MVC

A aplicação respeita a separação de responsabilidades exigida:

1.  **Model (DAO + POJO):** Encapsula o acesso a dados (`PessoaDAO`, `LivroDAO`) e regras de negócio. [cite_start]Utiliza JDBC para executar instruções SQL[cite: 287].
2.  **View (JSP):** Responsável pela apresentação. [cite_start]Utiliza JSTL e Expression Language (EL) para exibir dados dinâmicos[cite: 285].
3.  [cite_start]**Controller (Servlet):** Recebe requisições HTTP, processa a lógica e despacha para a View correta[cite: 284].

---

## 🔒 Segurança e Robustez (ISO/IEC 27001)

[cite_start]Em conformidade com as exigências de segurança do projeto[cite: 266], foram implementadas as seguintes medidas:

* **Prevenção contra SQL Injection:** Todas as operações de banco de dados utilizam **`PreparedStatement`**. [cite_start]Isso garante que entradas do usuário sejam tratadas como dados literais e não como comandos executáveis, mitigando a vulnerabilidade crítica apontada na Situação-Problema 1[cite: 302, 397].
* [cite_start]**Tratamento de Exceções:** Implementação robusta de blocos `try-catch-finally` para garantir a integridade da aplicação e o fechamento correto de recursos (conexões), conforme exigido na Situação-Problema 2[cite: 204, 417].

---

## ✨ Funcionalidades Implementadas

[cite_start]O sistema atende aos requisitos funcionais mandatórios[cite: 73]:

* ✅ **Interface Web:** Navegação intuitiva para gerenciamento do catálogo.
* ✅ **CRUD Completo:** Cadastro, Leitura, Atualização e Exclusão de itens.
* ✅ **Persistência:** Todos os dados são salvos em banco de dados relacional.
* ✅ **Busca:** Funcionalidade de pesquisa por título ou autor.

---

## 🛠️ Manual de Instalação e Execução

[cite_start]Este guia atende ao requisito de "Manual do usuário simplificado"[cite: 345].

### 1. Configuração do Banco de Dados
Certifique-se de ter o **PostgreSQL** instalado localmente.
1.  Abra o **pgAdmin** ou terminal SQL.
2.  Crie um banco de dados chamado `catalogo_db`.
3.  Execute o script de criação das tabelas (disponível em `src/main/resources/schema.sql` ou abaixo):

```sql
CREATE TABLE T_PESSOA (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    biografia TEXT,
    data_nascimento DATE
);