<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Lista de Pessoas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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

        <div class="card shadow-sm">
            <div class="card-body p-0">
                <table class="table table-hover table-striped mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Nome Completo</th>
                            <th>Data Nasc.</th>
                            <th class="text-end">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listaPessoas}" var="p">
                            <tr>
                                <td>${p.id}</td>
                                <td><strong>${p.nomeCompleto}</strong></td>
                                <td>${p.dataNascimento}</td>
                                <td class="text-end">
                                    <a href="pessoa?acao=editar&id=${p.id}" class="btn btn-sm btn-outline-primary me-1">Editar</a>
                                    
                                    <a href="pessoa?acao=excluir&id=${p.id}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Tem certeza? Se excluir este autor, ele será removido dos livros.');">Excluir</a>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty listaPessoas}">
                            <tr>
                                <td colspan="4" class="text-center py-4 text-muted">
                                    Nenhum registro encontrado no banco de dados.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
        
        <div class="mt-4 text-center">
            <a href="index.jsp" class="text-decoration-none">← Voltar para a Página Inicial</a>
        </div>
    </div>

</body>
</html>