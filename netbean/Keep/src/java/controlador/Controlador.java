/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import gestion.GestorKeep;
import gestion.GestorUsuario;
import hibernate.Keep;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Alex
 */
@WebServlet(name = "Controlador", urlPatterns = {"/go"})
public class Controlador extends HttpServlet {

   enum Camino{
        forward,redirect,print
    }
   class Destino {
       public Camino camino;
       public String url;
       public String texto;
       
        public Destino(Camino camino, String url,String texto) {
            this.camino = camino;
            this.url = url;
            this.texto=texto;
        }
        private Destino() {
        }
       
   }
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Destino destino;
        String tabla=request.getParameter("tabla");//persona,idioma,etc
        String op=request.getParameter("op");//create,update,read,delete,login
        String accion=request.getParameter("accion");//view,do
        String origen=request.getParameter("origen");
        destino=handle(request,response,tabla,op,accion,origen);

        if(destino==null){
            System.out.println("ENTRA AQUI");
            destino=new Destino(Camino.forward,"/WEB-INF/index.jsp","");
        }
        if(destino.camino==Camino.forward){
        request.getServletContext().
                getRequestDispatcher(destino.url).forward(request, response);
        }else if(destino.camino==Camino.redirect){
            response.sendRedirect(destino.url);
        }else{
            response.setContentType("application/json;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(destino.texto);
            }
        }
    }
    private Destino handle(HttpServletRequest request, HttpServletResponse response,
            String tabla,String op,String accion,String origen){
        if(origen==null) {
            origen="pc";
        }
        if(tabla ==null || op==null || accion==null){
            tabla="usuario";
            op="read";
            accion="view";
        }
        switch (tabla) {
            case "usuario":
                return handleUsuario(request,response,op,accion,origen);
            case "keep":
                return handleKeep(request, response, op, accion, origen);
            default:
        }
        return null;
    }

    private Destino handleUsuario(HttpServletRequest request, HttpServletResponse response,
            String op,String accion,String origen){
        switch(op){
            case "login":
                if(origen.equalsIgnoreCase("android")){
                    JSONObject obj= GestorUsuario.getLogin(request.getParameter("login"),
                            request.getParameter("pass"));
                    return new Destino(Camino.print,"",obj.toString());
                    /*http://192.168.208.101:8080/Keep/go?tabla=usuario&op=login&accion=&login=pepe&pass=pepe&origen=android*/
                }
        }
        return null;
    }
    
    private Destino handleKeep(HttpServletRequest request, HttpServletResponse response,
            String op,String accion,String origen){
        switch(op){
            case "create":
                if(origen.equalsIgnoreCase("android")){
                    Keep k=new Keep(null,Integer.parseInt(request.getParameter("idAndroid")),
                            request.getParameter("contenido"),null,"estable");
                    GestorKeep.insertKeep(k,request.getParameter("login"));
                    return new Destino(Camino.print,"","Elemento insertado");
                }else{
                    switch(accion){
                        case "view":
                            request.setAttribute("login", request.getParameter("login"));
                            return new Destino(Camino.forward,"/WEB-INF/insert.jsp","");
                        case "do":
                            Keep k=new Keep();
                            k.setContenido(request.getParameter("contenido"));
                            k.setEstado("estable");
                            k.setRuta(request.getParameter("ruta"));
                            GestorKeep.insertKeep(k,request.getParameter("login"));
                            return new Destino(Camino.redirect,"go?tabla=keep&op=read&accion=&origen=pc&login="+request.getParameter("login")+"","");
                    }
                }
            case "read":
                List<Keep> notas=GestorKeep.getKeeps(request.getParameter("login"));
                switch(origen){
                    case "android":
                        JSONArray keeps=new JSONArray();
                        for(Keep k:notas){
                            JSONObject keep=new JSONObject();
                            keep.put("contenido", k.getContenido());
                            keep.put("estado", k.getEstado());
                            keep.put("id", k.getId());
                            keeps.put(keep);
                        }
                        JSONObject r=new JSONObject();
                        r.put("r", keeps);
                        return new Destino(Camino.print,"",r.toString());
                    case "pc":
                        request.setAttribute("notas", notas);
                        return new Destino(Camino.forward,"/WEB-INF/read.jsp","");
                }
            case "update":
                switch(origen){
                    case "android":
                        Keep nota=new Keep(null,null,request.getParameter("contenido"),null,"");
                        GestorKeep.updateKeep(nota, Integer.parseInt(request.getParameter("idAndroid")));
                        return new Destino(Camino.print,"","");
                    case "pc":
                        if(accion.equalsIgnoreCase("view")){
                            Keep k=(Keep)GestorKeep.getKeepsId(Integer.parseInt(request.getParameter("keep")));
                            request.setAttribute("nota", k);
                            return new Destino(Camino.forward,"/WEB-INF/edit.jsp","");
                        }else{
                            Keep k=new Keep();
                            k.setContenido(request.getParameter("contenido"));
                            k.setEstado("estable");
                            k.setRuta("null");
                            int id=Integer.parseInt(request.getParameter("keep"));
                            GestorKeep.updateKeep(k, id);
                            return new Destino(Camino.redirect,"go?tabla=keep&op=read&accion=&origen=pc&login="+request.getParameter("login")+"","");
                        }
                }
            
            case "delete":
                switch (origen){
                        case "android":
                            GestorKeep.deleteKeep(Integer.parseInt(request.getParameter("idAndroid")));
                            return new Destino(Camino.print,"","Elemento borrado");
                        case "pc":
                            if(accion.compareTo("do")==0){
                                int id=Integer.parseInt(request.getParameter("keep"));
                                GestorKeep.deleteKeep(id);
                                return new Destino(Camino.redirect,"go?tabla=keep&op=read&accion=&login="+request.getParameter("login")+"&origen=pc","");
                            }
                }
        }
        return null;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
