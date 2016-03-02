
package gestion;

import hibernate.HibernateUtil;
import hibernate.Usuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONObject;


public class GestorUsuario {
    public static JSONObject getLogin(String login,String pass){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql = "from Usuario where login=:login and pass=:pass";
        Query q = session.createQuery(hql);
        q.setString("login", login);
        q.setString("pass", pass);
        List<Usuario> usuarios = q.list();
        
        session.getTransaction().commit();
        session.flush();
        session.close();
        
        JSONObject obj=new JSONObject();
        if(usuarios.isEmpty()){
            obj.put("r",false);
        }else{
            obj.put("r",true);
        }
        return obj;
    }
}
