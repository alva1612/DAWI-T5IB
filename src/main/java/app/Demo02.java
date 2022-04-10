package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo02 {
	
	public static void main(String[] args) {
		// valores del nuevo usuario
		Usuario u = new Usuario(3, "Carklaa", "Toro", "toro@gmail.com", "325", "2008/01/15", 2, 1);
		
		// ---> JPA
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		em.getTransaction().begin();
		//proceso --> actualizar en la tabla
		em.merge(u);	// actualiza si existe, sino lo inserta
		// confirmar la transaccion
		em.getTransaction().commit();
		em.close();
	}
}
