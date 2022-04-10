package app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Usuario;

public class Demo07 {
	public static void main(String[] args) {
		
		// listado de Usuario segun tipo
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		//select from where tipo es
		TypedQuery<Usuario> consulta = em.createQuery("select a from Usuario a where a.tipo = :xtipo", Usuario.class);
		consulta.setParameter("xtipo", 2);
		List<Usuario> lstUsuarios = consulta.getResultList();

		for (Usuario u:lstUsuarios) {
			System.out.println(u);
		}

		em.close();
	}
}
