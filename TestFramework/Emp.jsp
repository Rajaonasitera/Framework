<%@page language="java" contentType="text/html"%>
<%@ page import="model.*"%>
<% Emp p = (Emp)request.getAttribute("emp"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
        <p><%out.print(p.getNom());%></p>
        <p><%out.print(p.getNumero());%></p>
</body>
</html>