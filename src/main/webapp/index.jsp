<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Catálogo BAP - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-5">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">📚 Catálogo BAP</a>
        </div>
    </nav>

    <div class="container">
        <div class="p-5 mb-4 bg-light rounded-3 shadow-sm">
            <div class="container-fluid py-5">
                <h1 class="display-5 fw-bold">Bem-vindo ao Catálogo</h1>
                <p class="col-md-8 fs-4">Gerencie seus livros, filmes e seus respectivos autores e diretores de forma simples e segura.</p>
                
                <hr class="my-4">
                
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-start">
                    <a href="cadastro-pessoa.jsp" class="btn btn-primary btn-lg px-4 gap-3">
                        ➕ Cadastrar Pessoa (Autor/Diretor)
                    </a>
                    <a href="pessoa" class="btn btn-outline-dark btn-lg px-4">
                        📋 Ver Lista Completa
                    </a>
                    <button type="button" class="btn btn-outline-secondary btn-lg px-4" disabled>
                        ➕ Cadastrar Livro (Em Breve)
                    </button>
                    
                </div>
          
                    
            

            </div>
        </div>
    </div>
</body>
</html>