<%-- 
    Document   : insert
    Created on : 21-feb-2016, 12:01:41
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    String login=(String)request.getAttribute("login");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Añadir Keep</h1>
        <form action="go?tabla=keep&op=create&accion=do&origen=pc&login=<%= login%>" method="POST">
            
            Contenido<input type="text" name="contenido" value="" /><br/>
            <input type="submit" value="Enviar" />
        </form>
    </body>
</html>
