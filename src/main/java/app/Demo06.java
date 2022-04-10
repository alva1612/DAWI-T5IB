package app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Usuario;

public class Demo06 {
	public static void main(String[] args) {
		//listado de usuarios
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		TypedQuery<Usuario> consulta = em.createQuery("select a from Usuario a", Usuario.class);
		List<Usuario> lstUsuarios = consulta.getResultList();
		
		for (Usuario u:lstUsuarios) {
			System.out.println(u);
		}
		
		em.close();
	}
}
