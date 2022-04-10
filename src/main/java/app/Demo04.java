package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo04 {
	public static void main(String[] args) {
		
		// obtener datos de un usuario---> JPA
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		em.getTransaction().begin();

		Usuario u = em.find(Usuario.class, 3);
		System.out.println(u);
		// confirmar la transaccion
		em.getTransaction().commit();
		em.close();
	}
}
