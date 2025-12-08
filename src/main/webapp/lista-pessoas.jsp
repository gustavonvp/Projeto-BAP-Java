<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
 <head>   
    <meta charset="UTF-8">
        <title>Autores e Diretores</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
            .hover-effect { transition: transform 0.2s; }
            .hover-effect:hover { transform: translateY(-5px); box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
            .profile-img-container {
                height: 200px; width: 200px; margin: 20px auto;
                border-radius: 50%; overflow: hidden; border: 5px solid #fff;
                box-shadow: 0 0 10px rgba(0,0,0,0.1); background-color: #e9ecef;
                display: flex; align-items: center; justify-content: center;
            }
    </style>
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">📚 Catálogo BAP</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp">Home</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-primary">Autores e Diretores</h2>
            <a href="cadastro-pessoa.jsp" class="btn btn-success">
                ➕ Nova Pessoa
            </a>
        </div>


    <div class="card mb-4 shadow-sm border-0">
            <div class="card-body">
                <form action="pessoa" method="get" class="row g-2">
                    <input type="hidden" name="acao" value="buscar">
                    <div class="col-md-10">
                        <input type="text" name="termo" class="form-control" placeholder="Buscar por nome...">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">🔍 Buscar</button>
                    </div>
                </form>
            </div>
    </div>
<div class="row row-cols-1 row-cols-md-3 g-4">
            <c:forEach items="${listaPessoas}" var="p">
                <div class="col">
                    <div class="card h-100 border-0 shadow-sm hover-effect text-center">
                        
                        <div class="profile-img-container">
                            <c:choose>
                                <c:when test="${not empty p.fotoUrl}">
                                    <img src="${p.fotoUrl}" style="width: 100%; height: 100%; object-fit: cover;" alt="${p.nomeCompleto}">
                                </c:when>
                                <c:otherwise>
                                    <span style="font-size: 3rem;">👤</span>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="card-body">
                            <h5 class="card-title text-dark fw-bold">${p.nomeCompleto}</h5>
                            <p class="card-text text-muted mb-2">
                                <small>🎂 Nasc: ${p.dataNascimento != null ? p.dataNascimento : 'N/A'}</small>
                            </p>
                            <p class="card-text text-secondary small text-truncate" style="max-width: 90%; margin: 0 auto;">
                                ${empty p.biografia ? 'Sem biografia.' : p.biografia}
                            </p>
                        </div>

                        <div class="card-footer bg-white border-0 pb-3">
                            <a href="pessoa?acao=editar&id=${p.id}" class="btn btn-outline-primary btn-sm px-3">Editar</a>
                            <a href="pessoa?acao=excluir&id=${p.id}" 
                               class="btn btn-outline-danger btn-sm px-3"
                               onclick="return confirm('Excluir este autor?');">Excluir</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
            
            <c:if test="${empty listaPessoas}">
                <div class="col-12 text-center py-5"><p class="text-muted">Nenhum registro encontrado.</p></div>
            </c:if>
        </div>
        
        <div class="text-center mt-4 pb-5">
            <a href="index.jsp" class="btn btn-secondary">Voltar ao Início</a>
        </div>
    </div>
</body>
</html>