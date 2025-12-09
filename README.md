# 📚 Projeto Integrador: Catálogo de Mídias (Livros e Filmes)

> **Disciplina:** Projeto Integrador Transdisciplinar (PIT) - Ciência da Computação  
> **Metodologia:** Aprendizagem Baseada em Projetos (ABP)

## 🎯 Apresentação do Projeto

Este projeto consiste no desenvolvimento de uma aplicação *web* para catalogação e gerenciamento de mídias (livros, filmes e séries). O sistema foi desenvolvido seguindo estritamente os princípios da **Programação Orientada a Objetos (POO)** e o padrão de arquitetura **MVC (Model-View-Controller)**.

O objetivo é integrar competências de desenvolvimento *full-stack*, demonstrando domínio sobre a persistência de dados e segurança da informação sem o uso de *frameworks* de alto nível (como Spring), privilegiando a implementação "raiz" com **Jakarta EE**.

---

## 🏗️ Arquitetura e Tecnologias

A solução foi construída sobre a especificação **Jakarta EE 10**, utilizando as seguintes tecnologias mandatórias:

* ☕ **Linguagem:** Java 17 (LTS)
* 🎨 **Front-end (View):** JSP (JavaServer Pages) + JSTL + HTML5/Bootstrap.
* ⚙️ **Back-end (Controller):** Java Servlets (Jakarta Servlet API).
* 🗄️ **Persistência (Model):** JDBC (Java Database Connectivity) puro com padrão DAO.
* 🐘 **Banco de Dados:** PostgreSQL (Instalação Local).
* 🚀 **Servidor de Aplicação:** Apache Tomcat 10.

### Estrutura MVC

A aplicação respeita a separação de responsabilidades exigida:

1.  **Model (DAO + POJO):** Encapsula o acesso a dados (`PessoaDAO`, `LivroDAO`) e regras de negócio. Utiliza JDBC para executar instruções SQL.
2.  **View (JSP):** Responsável pela apresentação. Utiliza JSTL e Expression Language (EL) para exibir dados dinâmicos.
3.  **Controller (Servlet):** Recebe requisições HTTP, processa a lógica e despacha para a View correta.

---

## 📂 Estrutura do Projeto

```text
/src
  /main
    /java/br/com/projeto/bap
       /dao         # Camada de Persistência (SQL/JDBC)
       /model       # Classes POJO (Livro, Pessoa)
       /servlet     # Controladores HTTP (Lógica de navegação)
       /util        # Utilitários (ConnectionFactory)
    /resources      # Scripts SQL e configurações
    /webapp         # Páginas JSP, CSS e WEB-INF
       /WEB-INF     # Configurações de segurança (web.xml)
       *.jsp        # Telas do sistema (View)
🔌 Documentação de Rotas (Endpoints)

Embora a aplicação utilize renderização no servidor (JSP), a comunicação segue o protocolo HTTP padrão. Abaixo estão os endpoints disponíveis no Controller.
👤 Pessoas (Autores/Diretores)

Endpoint Base: /pessoa
Método	Parâmetros (Query/Body)	Ação	Descrição
GET	?acao=listar (Default)	Listar	Retorna a view lista-pessoas.jsp com todos os registros.
GET	?acao=editar&id={id}	Formulário	Retorna cadastro-pessoa.jsp preenchido com dados do ID.
GET	?acao=excluir&id={id}	Excluir	Remove o registro e redireciona para a lista.
POST	nomeCompleto, biografia...	Salvar/Atualizar	Se enviado ID, atualiza. Se não, cria novo registro.
📖 Livros

Endpoint Base: /livro
Método	Parâmetros (Query/Body)	Ação	Descrição
GET	?acao=listar	Listar	Retorna a view lista-livros.jsp com todos os livros.
GET	?acao=buscar&termo={txt}	Buscar	Filtra livros por título ou autor (SQL LIKE).
GET	?acao=editar&id={id}	Formulário	Retorna cadastro-livro.jsp com multiselect de autores.
GET	?acao=excluir&id={id}	Excluir	Remove o livro e seus vínculos N:M.
POST	titulo, autoresIds...	Salvar/Atualizar	Gerencia a transação de salvar livro e vincular autores.
🔒 Segurança e Robustez

Em conformidade com as exigências de segurança do projeto, foram implementadas as seguintes medidas:

    ✅ Prevenção contra SQL Injection: Todas as operações de banco de dados utilizam PreparedStatement. Isso garante que entradas do usuário sejam tratadas como dados literais e não como comandos executáveis.

    ✅ Tratamento de Exceções: Implementação robusta de blocos try-catch-finally para garantir a integridade da aplicação e o fechamento correto de conexões.

🛠️ Manual de Instalação e Execução
1. Configuração do Banco de Dados

Certifique-se de ter o PostgreSQL instalado localmente.

    Abra o pgAdmin ou terminal SQL.

    Crie um banco de dados chamado catalogo_db.

    Execute o script completo de criação das tabelas abaixo:

SQL

-- 1. Tabela de Pessoas (Autores e Diretores)
CREATE TABLE IF NOT EXISTS T_PESSOA (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    biografia TEXT,
    data_nascimento DATE,
    foto_url VARCHAR(1000) -- Link para foto de perfil
);

-- 2. Tabela de Livros
CREATE TABLE IF NOT EXISTS T_LIVRO (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    editora VARCHAR(255),
    isbn VARCHAR(20),
    ano INT,
    genero VARCHAR(100),
    sinopse TEXT,
    capa_url VARCHAR(1000) -- Link para imagem da capa
);

-- 3. Tabela Associativa (Relacionamento N:M)
CREATE TABLE IF NOT EXISTS T_OBRA_AUTORES (
    id_livro INT NOT NULL,
    id_pessoa INT NOT NULL,
    PRIMARY KEY (id_livro, id_pessoa),
    FOREIGN KEY (id_livro) REFERENCES T_LIVRO(id),
    FOREIGN KEY (id_pessoa) REFERENCES T_PESSOA(id)
);

2. Configuração da Conexão

Verifique o arquivo src/main/java/br/com/projeto/bap/util/ConnectionFactory.java. Certifique-se de que a variável PASS corresponde à senha do seu PostgreSQL local.
3. Compilação (Build)

Abra o terminal na raiz do projeto e execute o comando do Maven para gerar o pacote de distribuição (.war):
Bash

mvn clean package

O arquivo catalogo.war será gerado dentro da pasta target/.
4. Deploy no Tomcat

Para evitar erros de caminho ou links simbólicos de IDEs, realizaremos o deploy manual:

    Navegue até a pasta target/ do projeto e copie o arquivo catalogo.war.

    Vá até o diretório de instalação do seu Apache Tomcat.

    Abra a pasta webapps.

    Cole o arquivo catalogo.war dentro de webapps.

        Nota: Se houver uma pasta antiga chamada catalogo, apague-a antes de colar o novo arquivo.

5. Execução

    Inicie o Tomcat (via terminal bin/catalina.bat run ou via start da sua IDE apontando para a instalação local).

    Aguarde a mensagem de "Server startup" no log.

    Acesse a aplicação no navegador:

👉 http://localhost:8080/catalogo/
🔍 Queries Úteis para Testes

Caso queira popular o banco manualmente ou verificar os dados, utilize os scripts abaixo:
SQL

-- Inserir um Autor
INSERT INTO T_PESSOA (nome_completo, biografia, foto_url) 
VALUES ('J.R.R. Tolkien', 'O pai da fantasia moderna.', '[https://upload.wikimedia.org/wikipedia/commons/b/b4/Tolkien_1916.jpg](https://upload.wikimedia.org/wikipedia/commons/b/b4/Tolkien_1916.jpg)');

-- Inserir um Livro
INSERT INTO T_LIVRO (titulo, ano, genero, capa_url) 
VALUES ('O Hobbit', 1937, 'Fantasia', '[https://m.media-amazon.com/images/I/91RnHEbM9OL._AC_UF1000,1000_QL80_.jpg](https://m.media-amazon.com/images/I/91RnHEbM9OL._AC_UF1000,1000_QL80_.jpg)');

-- Vincular (Assumindo que ambos ganharam ID 1)
INSERT INTO T_OBRA_AUTORES (id_livro, id_pessoa) VALUES (1, 1);  

-- Verificar a "Mágica" (JOIN de Livro com Autor)
SELECT 
    l.titulo AS "Título do Livro",
    l.editora AS "Editora",
    p.nome_completo AS "Autor/Diretor"
FROM T_LIVRO l
INNER JOIN T_OBRA_AUTORES oa ON l.id = oa.id_livro
INNER JOIN T_PESSOA p ON oa.id_pessoa = p.id;

-- Filtrar Livro por titulo, capa_url, e ordenado por id
SELECT id, titulo, capa_url FROM T_LIVRO ORDER BY id;

-- Atualizar Livro, somente atributo capa_url (utilizar id para localizar livro e atualizar os atributos)
UPDATE T_LIVRO SET capa_url = 'https://upload.wikimedia.org/wikipedia/commons/a/a9/Biblia-1-.png' WHERE id = 46;
UPDATE T_LIVRO SET titulo=?, editora=?, isbn=?, ano=?, genero=?, sinopse=?, capa_url=? WHERE id=?;

-- Alterar Livro, adicionando nova propriedade (verificar regra de negócio para não desestruturar relação de dados)
ALTER TABLE T_LIVRO ADD COLUMN capa_url VARCHAR(1000);





Desenvolvido por: Gustavo Nunes

RGM: 38346818
