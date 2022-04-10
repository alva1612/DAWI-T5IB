package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo03 {
	public static void main(String[] args) {
		// valores del nuevo usuario
		Usuario u = new Usuario(3, "Carklaa", "Toro", "toro@gmail.com", "325", "2008/01/15", 2, 1);
		// ---> JPA
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		em.getTransaction().begin();
		//proceso --> borrar en la tabla
		//borrado logico --> actualizar estado, flag...
		//em.merge(u);	// actualiza si existe, sino lo inserta
		//borrado fisico
		em.remove(u);
		// confirmar la transaccion
		em.getTransaction().commit();
		em.close();
	}
}
