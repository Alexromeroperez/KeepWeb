package gestion;

import hibernate.HibernateUtil;
import hibernate.Keep;
import hibernate.Usuario;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestorKeep {
    
    public static JSONObject addKeep(Keep nota,String login){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Usuario u=(Usuario)session.get(Usuario.class, login);
        nota.setUsuario(u);
        session.save(nota);
        Long id = ((BigInteger) session.createSQLQuery("select last_insert_id()").uniqueResult()).longValue();
        session.getTransaction().commit();
        session.flush();
        session.close();
        JSONObject obj=new JSONObject();
        obj.put("r", id);
        return obj;
    }
    
    public static List<Keep> getKeeps(String login){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql = "from Keep where login=:login";
        Query q = session.createQuery(hql);
        q.setString("login", login);
        List<Keep> notas = q.list();
        
        session.getTransaction().commit();
        session.flush();
        session.close();
        return notas;
    }
    
    public static Keep getKeepsId(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Keep k=(Keep)session.get(Keep.class, id);
        session.getTransaction().commit();
        session.flush();
        session.close();
        return k;
    }
    
    public static void deleteKeep(int id){
    Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Keep k=(Keep) session.load(Keep.class, id);
        session.delete(k);
        
        session.getTransaction().commit();
        session.flush();
        session.close();
    }
    
    public static void insertKeep(Keep nota,String login){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Usuario u=(Usuario)session.get(Usuario.class, login);
        nota.setUsuario(u);
        session.save(nota);
        
        session.getTransaction().commit();
        session.flush();
        session.close();
    }
    
    public static void updateKeep(Keep nota,int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Keep oldk=(Keep)session.get(Keep.class,id);
        oldk.setContenido(nota.getContenido());
        session.update(oldk); 
        
        session.getTransaction().commit();
        session.flush();
        session.close();
    }
    
}
