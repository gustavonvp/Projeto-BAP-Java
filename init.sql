-- Garante que começamos limpo (opcional)
DROP TABLE IF EXISTS T_OBRA_AUTORES;
DROP TABLE IF EXISTS T_LIVRO;
DROP TABLE IF EXISTS T_PESSOA;

-- Tabela para a classe Pessoa (Autores/Diretores)
CREATE TABLE T_PESSOA (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    biografia TEXT,
    data_nascimento DATE
);

-- Tabela para Livros (para quando criarmos o LivroDAO)
CREATE TABLE T_LIVRO (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    editora VARCHAR(255),
    isbn VARCHAR(20),
    ano INT
);

-- Inserir um dado de teste inicial (opcional)
INSERT INTO T_PESSOA (nome_completo, biografia, data_nascimento) 
VALUES ('J.R.R. Tolkien', 'Autor de O Senhor dos Anéis', '1892-01-03');