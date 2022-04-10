package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo05 {
	public static void main(String[] args) {
		
		// eliminar datos de un usuario buscado---> JPA
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		em.getTransaction().begin();

		Usuario u = em.find(Usuario.class, 3);
		if (u != null) {
		em.remove(u);
		System.out.println("Se elimino");
		}
		System.out.println(u);
		// confirmar la transaccion
		em.getTransaction().commit();
		em.close();
	}
}
