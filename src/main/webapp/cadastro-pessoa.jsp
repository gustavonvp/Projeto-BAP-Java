<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Pessoa</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">📚 Voltar ao Início</a>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">Cadastrar Novo Autor ou Diretor</h4>
                    </div>
                    <div class="card-body">
                        
                        <form action="pessoa" method="post">
                            <input type="hidden" name="id" value="${pessoa.id}">
                            <div class="mb-3">
                                <label for="nome" class="form-label">Nome Completo *</label>
                                <input type="text" class="form-control" id="nome" name="nomeCompleto" value="${pessoa.nomeCompleto}" required>
                                <div class="form-text">Ex: J.R.R. Tolkien, Christopher Nolan</div>
                            </div>

                            <div class="mb-3">
                                <label for="data" class="form-label">Data de Nascimento</label>
                                <input type="date" class="form-control" id="data" name="dataNascimento" value="${pessoa.dataNascimento}">
                            </div>

                            <div class="mb-3">
                                <label for="bio" class="form-label">Biografia</label>
                                <textarea class="form-control" id="bio" name="biografia" rows="3">${pessoa.biografia}</textarea>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="index.jsp" class="btn btn-secondary">Cancelar</a>
                                <button type="submit" class="btn btn-success">${empty pessoa.id ? 'Salvar Novo' : 'Atualizar Dados'}</button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>