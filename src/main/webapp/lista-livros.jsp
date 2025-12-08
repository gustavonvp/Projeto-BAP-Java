<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Catálogo de Livros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <nav class="navbar navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">📚 Catálogo BAP</a>
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="index.jsp">Home</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-success">Livros Catalogados</h2>
            <a href="livro" class="btn btn-success">➕ Novo Livro</a>
        </div>

        <div class="card mb-4">
            <div class="card-body">
                <form action="livro" method="get" class="row g-2">
                    <input type="hidden" name="acao" value="buscar">
                    
                    <div class="col-md-10">
                        <input type="text" name="termo" class="form-control" 
                               placeholder="Digite o título do livro ou nome do autor...">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">🔍 Buscar</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="card shadow">
            <div class="card-body p-0">
                <table class="table table-striped table-hover mb-0">
                    <thead class="table-success">
                        <tr>
                            <th>ID</th>
                            <th>Título</th>
                            <th>Editora</th>
                            <th>Ano</th>
                            <th>Gênero</th>
                            <th class="text-end">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listaLivros}" var="livro">
                            <tr>
                                <td>${livro.id}</td>
                                <td><strong>${livro.titulo}</strong></td>
                                <td>${livro.editora}</td>
                                <td>${livro.ano}</td>
                                <td><span class="badge bg-secondary">${livro.genero}</span></td>
                                <td class="text-end">
                                    <button class="btn btn-sm btn-outline-primary">Editar</button>
                                    <button class="btn btn-sm btn-outline-danger">Excluir</button>
                                </td>
                            </tr>
                        </c:forEach>
                        
                        <c:if test="${empty listaLivros}">
                            <tr>
                                <td colspan="6" class="text-center py-4 text-muted">
                                    Nenhum livro encontrado. Comece a catalogar!
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
        
        <div class="mt-3 text-center">
            <a href="index.jsp" class="btn btn-secondary">Voltar</a>
        </div>
    </div>
</body>
</html>