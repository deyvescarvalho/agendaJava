<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Contato - Agenda</title>
<style>
    /* Estilos Gerais */
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f4f7f6;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }

    /* Container do Formulário */
    .form-container {
        background-color: #fff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        width: 100%;
        max-width: 400px;
    }

    h1 {
        text-align: center;
        color: #333;
        margin-bottom: 25px;
        font-size: 24px;
    }

    /* Estilo da Tabela e Campos */
    table {
        width: 100%;
        border-collapse: collapse;
    }

    td {
        padding: 10px 0;
    }

    input[type="text"] {
        width: 100%;
        padding: 12px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box; /* Garante que padding não afete a largura */
        transition: border-color 0.3s;
    }

    /* Estilo para o campo read-only (ID) */
    input[readonly] {
        background-color: #eee;
        color: #777;
        cursor: not-allowed;
    }

    input[type="text"]:focus {
        border-color: #04AA6D;
        outline: none;
    }

    /* Estilo do Botão */
    .btn-salvar {
        width: 100%;
        background-color: #04AA6D;
        color: white;
        padding: 12px;
        border: none;
        border-radius: 4px;
        font-size: 16px;
        cursor: pointer;
        margin-top: 15px;
        transition: background-color 0.3s;
    }

    .btn-salvar:hover {
        background-color: #45a049;
    }
</style>
</head>
<body>
    
	<div class="form-container">
        <h1>Editar Contato</h1>

        <form name="frmContato" action="update">
            <table>
                <tr>
                    <td><input type="text" name="idcon" readonly value="<%out.print(request.getAttribute("idcon")); %>" ></td>
                </tr>
                <tr>
                    <td><input type="text" name="nome" placeholder="Nome" value="<%out.print(request.getAttribute("nome")); %>" ></td>
                </tr>
                <tr>
                    <td><input type="text" name="fone" placeholder="Telefone" value="<%out.print(request.getAttribute("fone")); %>" ></td>
                </tr>
                <tr>
                    <td><input type="text" name="email" placeholder="E-mail" value="<%out.print(request.getAttribute("email")); %>"></td>
                </tr>
            </table>
            <input type="button" class="btn-salvar" onclick="validar()" value="Salvar Alterações">
        </form>
    </div>

	<script src="scripts/validador.js"></script>
</body>
</html>
