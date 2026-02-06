<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>
<% ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos"); %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agenda de Contatos</title>
    <style>
        /* CSS Bonito e Moderno */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 20px;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #2c3e50;
        }

        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .btn-novo {
            display: inline-block;
            padding: 10px 20px;
            background-color: #27ae60;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-bottom: 20px;
            font-weight: bold;
            transition: background 0.3s;
        }
         .btn-gerarRelatorio {
            display: inline-block;
            padding: 10px 20px;
            background-color: #AA4A44;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-bottom: 20px;
            font-weight: bold;
            transition: background 0.3s;
        }

        .btn-novo:hover {
            background-color: #219150;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        table thead {
            background-color: #34495e;
            color: white;
        }

        table th, table td {
            text-align: left;
            padding: 15px;
            border-bottom: 1px solid #ddd;
        }

        table tbody tr:hover {
            background-color: #f1f1f1;
        }

        /* Estilização dos Botões de Ação */
        .btn-acao {
            padding: 6px 12px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
            font-weight: bold;
            margin-right: 5px;
            transition: opacity 0.2s;
        }

        .btn-acao:hover {
            opacity: 0.8;
        }

        .btn-editar {
            background-color: #3498db;
            color: white;
        }

        .btn-excluir {
            background-color: #e74c3c;
            color: white;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Agenda de Contatos</h1>
    <a href="novo.html" class="btn-novo">+ Novo contato</a>
  
    <a href="report" class="btn-gerarRelatorio" target="_blank" rel="noopener noreferrer">Gerar Relatório</a>

    <table >
        <thead>
            <tr>
                <th>ID</th>
                <th>NOME</th>
                <th>FONE</th>
                <th>EMAIL</th>
                <th>AÇÕES</th>
            </tr>
        </thead>
        <tbody>
            <%if (lista != null) {
                for (int i = 0; i < lista.size(); i++) {%>
                <tr>
                    <td><%=lista.get(i).getIdcon()%></td>
                    <td><%=lista.get(i).getNome()%></td>
                    <td><%=lista.get(i).getFone()%></td>
                    <td><%=lista.get(i).getEmail()%></td>
                    <td>
                        <!-- Botoes Editar/Excluir -->
                        <a href="select?idcon=<%=lista.get(i).getIdcon()%>" class="btn-acao btn-editar">Editar</a>
                        <a href="delete?idcon=<%=lista.get(i).getIdcon()%>" class="btn-acao btn-excluir" onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
                    </td>
                </tr>
            <% }
            }%>
        </tbody>
    </table>
</div>

</body>
</html>
