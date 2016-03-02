package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import hibernate.Keep;

public final class read_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
 
    List<Keep> notas=(List)request.getAttribute("notas");

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Keep</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Tus notas</h1>\n");
      out.write("        <table border=\"1\">\n");
      out.write("            <thead>\n");
      out.write("                <tr>\n");
      out.write("                    <th>Contenido</th>\n");
      out.write("                    <th>Sincronizado</th>\n");
      out.write("                    <th>Imagenes</th>\n");
      out.write("                </tr>\n");
      out.write("            </thead>\n");
      out.write("            <tbody>\n");
      out.write("                ");

                  for(Keep k:notas){      
                
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    <td>");
      out.print( k.getContenido());
      out.write("</td>\n");
      out.write("                    <td>");
      out.print( k.getEstado());
      out.write("</td>\n");
      out.write("                    <td>");
      out.print( k.getRuta());
      out.write("</td>\n");
      out.write("                    <td><a href=\"go?tabla=keep&op=update&accion=view&keep=");
      out.print( k.getId());
      out.write("\">Editar</a></td>\n");
      out.write("                    <td><a class=\"borrar\" href=\"go?tabla=keep&op=delete&accion=do&login=pepe&origen=pc&keep=");
      out.print( k.getId());
      out.write("\">Eliminar</a></td>\n");
      out.write("                </tr>\n");
      out.write("                ");

                  }
                
      out.write("\n");
      out.write("            </tbody>\n");
      out.write("        </table>\n");
      out.write("                <a href=\"go?tabla=keep&op=create&accion=view&login=pepe&origen=pc\"><input type=\"button\" value=\"Añadir nota\" /></a>\n");
      out.write("            <script>\n");
      out.write("                var elementos=document.getElementsByClassName(\"borrar\");\n");
      out.write("                for(var i=0;i<elementos.length;i++){\n");
      out.write("                    agregarEventoClick(elementos[i]);\n");
      out.write("                }\n");
      out.write("                function agregarEventoClick(elemento){\n");
      out.write("                    elemento.addEventListener(\"click\",function(event){\n");
      out.write("                       var respuesta=confirm(\"¿Seguro?\");\n");
      out.write("                       if(!respuesta){\n");
      out.write("                           event.preventDefault();\n");
      out.write("                       }\n");
      out.write("                    });\n");
      out.write("                }\n");
      out.write("            </script>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
