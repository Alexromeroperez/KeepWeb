<%-- 
    Document   : read
    Created on : 20-feb-2016, 11:33:58
    Author     : Alex
--%>

<%@page import="java.util.List"%>
<%@page import="hibernate.Keep"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    List<Keep> notas=(List)request.getAttribute("notas");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Keep</title>
    </head>
    <body>
        <h1>Tus notas</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>Contenido</th>
                    <th>Sincronizado</th>
                    <th>Imagenes</th>
                </tr>
            </thead>
            <tbody>
                <%
                  for(Keep k:notas){      
                %>
                <tr>
                    <td><%= k.getContenido()%></td>
                    <td><%= k.getEstado()%></td>
                    <td><%= k.getRuta()%></td>
                    <td><a href="go?tabla=keep&op=update&accion=view&login=pepe&origen=pc&keep=<%= k.getId()%>">Editar</a></td>
                    <td><a class="borrar" href="go?tabla=keep&op=delete&accion=do&login=pepe&origen=pc&keep=<%= k.getId()%>">Eliminar</a></td>
                </tr>
                <%
                  }
                %>
            </tbody>
        </table>
                <a href="go?tabla=keep&op=create&accion=view&login=pepe&origen=pc"><input type="button" value="Añadir nota" /></a>
            <script>
                var elementos=document.getElementsByClassName("borrar");
                for(var i=0;i<elementos.length;i++){
                    agregarEventoClick(elementos[i]);
                }
                function agregarEventoClick(elemento){
                    elemento.addEventListener("click",function(event){
                       var respuesta=confirm("¿Seguro?");
                       if(!respuesta){
                           event.preventDefault();
                       }
                    });
                }
            </script>
    </body>
</html>
