<%
String nom=(String)request.getAttribute("nom");
int numero=(int)request.getAttribute("numero");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Coucouu </h1>
        <p><%out.print(nom);%></p>
        <p><%out.print(numero);%></p>
</body>
</html>