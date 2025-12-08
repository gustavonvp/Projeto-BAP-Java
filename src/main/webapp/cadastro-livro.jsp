<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Livro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <nav class="navbar navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">📚 Voltar ao Início</a>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-success text-white">
                        <h4 class="mb-0">📖 Novo Livro</h4>
                    </div>
                    <div class="card-body">
                        
                        <form action="livro" method="post">
                            <input type="hidden" name="id" value="${livro.id}">
                            
                            <div class="mb-3">
                                <label class="form-label">Título da Obra *</label>
                                <input type="text" class="form-control" name="titulo" value="${livro.titulo}" required>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Editora</label>
                                    <input type="text" class="form-control" name="editora" value="${livro.editora}">
                                </div>
                                <div class="col-md-3 mb-3">
                                    <label class="form-label">Ano</label>
                                    <input type="number" class="form-control" name="ano" value="${livro.ano}">
                                </div>
                                <div class="col-md-3 mb-3">
                                    <label class="form-label">Gênero</label>
                                    <input type="text" class="form-control" name="genero" placeholder="Ex: Fantasia">
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">ISBN</label>
                                <input type="text" class="form-control" name="isbn">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Sinopse</label>
                                <textarea class="form-control" name="sinopse" rows="3">${livro.sinopse}</textarea>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Link da Imagem da Capa</label>
                                <div class="input-group">
                                    <span class="input-group-text">🔗</span>
                                    <input type="url" class="form-control" name="capaUrl" 
                                        value="${livro.capaUrl}" 
                                        placeholder="Cole aqui o link da imagem (http://...)">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold text-primary">Selecione os Autores</label>
                                <div class="form-text mb-1">Segure a tecla <code>Ctrl</code> para selecionar múltiplos.</div>
                                
                                <select class="form-select" name="autoresIds" multiple size="5">
                                    <c:forEach items="${listaAutores}" var="opcaoAutor">
                                    
                                        <c:set var="estaSelecionado" value="false" />
                                            <c:if test="${not empty livro.autores}">
                                                <c:forEach items="${livro.autores}" var="autorDoLivro">
                                                    <c:if test="${autorDoLivro.id eq opcaoAutor.id}">
                                                        <c:set var="estaSelecionado" value="true" />
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        <option value="${opcaoAutor.id}" ${estaSelecionado ? 'selected' : ''}>
                                            ${opcaoAutor.nomeCompleto}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Sinopse</label>
                                <textarea class="form-control" name="sinopse" rows="3"></textarea>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-success btn-lg">Salvar Livro</button>
                                <a href="index.jsp" class="btn btn-outline-secondary">Cancelar</a>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>