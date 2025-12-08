<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Catálogo de Livros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        .hover-effect {
            transition: transform 0.2s ease-in-out, box-shadow 0.2s ease;
        }
        .hover-effect:hover {
            transform: translateY(-5px);
            box-shadow: 0 .5rem 1rem rgba(0,0,0,.15)!important;
        }
        .card-img-wrapper {
            height: 300px; 
            overflow: hidden; 
            background-color: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    </style>
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

        <div class="card mb-4 shadow-sm">
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

        <div class="row row-cols-1 row-cols-md-3 g-4">
            
            <c:forEach items="${listaLivros}" var="livro">
                <div class="col">
                    <div class="card h-100 shadow-sm border-0 hover-effect">
                        
                        <div class="card-img-wrapper">
                            <c:choose>
                                <c:when test="${not empty livro.capaUrl}">
                                    <img src="${livro.capaUrl}" class="card-img-top h-100" style="object-fit: cover;" alt="Capa do Livro">
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center text-muted">
                                        <h1 class="display-4">📖</h1>
                                        <small>Sem Capa</small>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="card-body">
                            <h5 class="card-title text-truncate" title="${livro.titulo}">
                                ${livro.titulo}
                            </h5>
                            <p class="card-text mb-1">
                                <small class="text-muted">Editora:</small> ${livro.editora}
                            </p>
                            <span class="badge bg-primary mb-2">${livro.genero}</span>
                        </div>

                        <div class="card-footer bg-white border-top-0 d-flex justify-content-between">
                            <a href="livro?acao=editar&id=${livro.id}" class="btn btn-outline-primary btn-sm">
                                Editar
                            </a>
                            <a href="livro?acao=excluir&id=${livro.id}" 
                               class="btn btn-outline-danger btn-sm"
                               onclick="return confirm('Tem certeza que deseja excluir este livro?');">
                               Excluir
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
            
            <c:if test="${empty listaLivros}">
                <div class="col-12 text-center py-5">
                    <p class="text-muted fs-4">Nenhum livro encontrado.</p>
                </div>
            </c:if>

        </div>
        <div class="mt-4 text-center pb-5">
            <a href="index.jsp" class="btn btn-secondary">Voltar ao Início</a>
        </div>
    </div>

</body>
</html>