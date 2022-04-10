package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Login {
	public static void main(String[] args) {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		
		Query consulta = em.createNativeQuery("call usp_validaAcceso(:usuario,:clave)", Usuario.class);
		consulta.setParameter("usuario","admin@ciberfarma.com");
		consulta.setParameter("clave","super");
		
		Usuario u = (Usuario) consulta.getSingleResult();	
		
		if (u ==null) {
			System.err.println("incorrecto");	
		}
		else {
			System.out.println("Bienvenido");
			System.out.println(u);	
		}
		
		em.close();
	}
}
