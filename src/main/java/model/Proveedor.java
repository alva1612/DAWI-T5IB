package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@Table(name="tb_proveedor")
public class Proveedor {
	@Id
	private int idprovedor;
	private String nombre_rs;
	private String telefono;
	private String email;
}
