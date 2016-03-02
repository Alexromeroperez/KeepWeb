<%-- 
    Document   : editar
    Created on : 21-feb-2016, 12:01:16
    Author     : Alex
--%>

<%@page import="hibernate.Keep"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    Keep k=(Keep)request.getAttribute("nota");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Editar Keep</h1>
        <form action="go?tabla=keep&op=update&accion=do&origen=pc&login=<%= request.getParameter("login")%>&keep=<%= request.getParameter("keep")%>" method="POST">
            
            Contenido:<input type="text" name="contenido" value="<%= k.getContenido()%>"/><br/>
            <input type="submit" value="Enviar" />
        </form>
    </body>
</html>
