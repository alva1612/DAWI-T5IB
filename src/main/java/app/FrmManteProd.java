package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Categoria;
import model.Producto;
import model.Proveedor;
import model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class FrmManteProd extends JFrame {

	private JPanel contentPane;
	private JTextArea txtSalida;
	private JTextField txtCódigo;
	JComboBox<String> cboCategorias;
	JComboBox<String> cboProveedores;
	private JTextField txtDescripcion;
	private JTextField txtStock;
	private JTextField txtPrecio;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmManteProd frame = new FrmManteProd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmManteProd() {
		setTitle("Mantenimiento de Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnNewButton.setBounds(446, 9, 117, 23);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 553, 176);
		contentPane.add(scrollPane);
		
		txtSalida = new JTextArea();
		scrollPane.setViewportView(txtSalida);
		
		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(332, 70, 89, 23);
		contentPane.add(btnListado);
		
		txtCódigo = new JTextField();
		txtCódigo.setBounds(122, 11, 246, 20);
		contentPane.add(txtCódigo);
		txtCódigo.setColumns(10);
		
		JLabel lblCodigo = new JLabel("Id. Producto :");
		lblCodigo.setBounds(10, 14, 102, 14);
		contentPane.add(lblCodigo);
		
		cboCategorias = new JComboBox();
		cboCategorias.setBounds(122, 73, 198, 20);
		contentPane.add(cboCategorias);
		
		JLabel lblCategora = new JLabel("Categoría :");
		lblCategora.setBounds(10, 74, 102, 14);
		contentPane.add(lblCategora);
		
		JLabel lblNomProducto = new JLabel("Producto :");
		lblNomProducto.setBounds(10, 45, 102, 14);
		contentPane.add(lblNomProducto);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(122, 42, 299, 20);
		contentPane.add(txtDescripcion);
		
		JLabel lblStock = new JLabel("Stock :");
		lblStock.setBounds(10, 135, 102, 14);
		contentPane.add(lblStock);
		
		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(122, 135, 77, 20);
		contentPane.add(txtStock);
		
		JLabel lblPrecio = new JLabel("Precio :");
		lblPrecio.setBounds(266, 135, 102, 14);
		contentPane.add(lblPrecio);
		
		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(344, 133, 77, 20);
		contentPane.add(txtPrecio);
		
		cboProveedores = new JComboBox();
		cboProveedores.setBounds(122, 104, 198, 20);
		contentPane.add(cboProveedores);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Producto a = buscarProductoId();
				Producto n = leerDatos();
				if (a.equals(null)) {
					JOptionPane.showMessageDialog(rootPane, "No se pudo actualizar. Código equivocado.");
				}
				else {
					actualizar(n);
					JOptionPane.showMessageDialog(rootPane, a.getId_prod()+" - "+n.getDes_prod()+"\nActualizado");
				}
			}
		});
		btnActualizar.setBounds(446, 41, 117, 23);
		contentPane.add(btnActualizar);
		
		JButton btnB = new JButton("B");
		btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Producto b = buscarProductoId();
				txtSalida.setText("");
				txtDescripcion.setText(b.getDes_prod());
				cboCategorias.setSelectedIndex(b.getCategoria().getIdcategoria());
				cboProveedores.setSelectedIndex(b.getProveedor().getIdprovedor());
				txtStock.setText(b.getStk_prod()+"");
				txtPrecio.setText(b.getPre_prod()+"");
			}
		});
		btnB.setBounds(388, 5, 33, 30);
		contentPane.add(btnB);
		
		JLabel lblProveedor = new JLabel("Proveedor :");
		lblProveedor.setBounds(12, 100, 102, 15);
		contentPane.add(lblProveedor);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		btnEliminar.setBounds(446, 71, 117, 25);
		contentPane.add(btnEliminar);
		
		llenaComboCat();
		llenaComboProv();
	}
	
	EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = fabrica.createEntityManager();
	
	void llenaComboCat() {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		
		TypedQuery<Categoria> consulta = em.createQuery("select c from Categoria c", Categoria.class);
		List<Categoria> lstCategorias = consulta.getResultList();
		
		cboCategorias.addItem("Seleccione");
		for (Categoria c:lstCategorias) {
			//System.out.println(c);
			cboCategorias.addItem(c.getIdcategoria() + "-"+ c.getDescripcion());
		}
		
		em.close();
	}
	
	void llenaComboProv() {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		
		TypedQuery<Proveedor> consulta = em.createQuery("select p from Proveedor p", Proveedor.class);
		List<Proveedor> lstCategorias = consulta.getResultList();
		
		cboProveedores.addItem("Seleccione");
		for (Proveedor p:lstCategorias) {
			//System.out.println(c);
			cboProveedores.addItem(p.getNombre_rs());
		}
		em.close();
	}
	
	void listado() {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		//select from where tipo es
		TypedQuery<Producto> consulta = em.createQuery("select p from Producto p where p.idcategoria = :xcat", Producto.class);
		consulta.setParameter("xcat", cboCategorias.getSelectedIndex());
		List<Producto> lstProductos = consulta.getResultList();
		
		txtSalida.setText("");
		for (Producto p:lstProductos) {
			txtSalida.append(p+"\n");
			txtSalida.append("Categoria: "+ p.getCategoria().getDescripcion() +"\n");
			txtSalida.append("Proveedor: "+ p.getProveedor().getNombre_rs() +"\n");
		}

		em.close();
	}
	
	private Producto leerDatos() {
		Producto lectura = new Producto();
		lectura.setId_prod(txtCódigo.getText());
		lectura.setDes_prod(txtDescripcion.getText());
		lectura.setIdcategoria(cboCategorias.getSelectedIndex());
		lectura.setStk_prod(Integer.parseInt(txtStock.getText()));
		lectura.setPre_prod(Double.parseDouble(txtPrecio.getText()));
		lectura.setIdprovedor(cboProveedores.getSelectedIndex());
		return lectura;
	}
	
	void registrar() {
		try {
			EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
			EntityManager em = fabrica.createEntityManager();
			
			Producto p = leerDatos();		
			em.getTransaction().begin();
			//proceso --> grabar en la tabla
			em.persist(p);
			// confirmar la transaccion
			em.getTransaction().commit();
			em.close();
			txtSalida.setText("Agregado exitosamente");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			txtSalida.setText("No se pudo agregar");
		}
	}
	
	void actualizar(Producto p) {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
		em.close();
		
	}
	
	void eliminar() {
		try {
			EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
			EntityManager em = fabrica.createEntityManager();
			
			//Supongo que hay que buscar el objeto a borrar en el mismo ¿EntityManager?
			em.getTransaction().begin();
			TypedQuery<Producto> consulta = em.createQuery("select p from Producto p where p.id_prod = :xid", Producto.class);
			consulta.setParameter("xid", txtCódigo.getText());
			Producto resultado = consulta.getSingleResult();
			
			em.remove(resultado);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Producto buscarProductoId() {
		try {
			EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
			EntityManager em = fabrica.createEntityManager();
			//select from where tipo es
			TypedQuery<Producto> consulta = em.createQuery("select p from Producto p where p.id_prod = :xid", Producto.class);
			consulta.setParameter("xid", txtCódigo.getText());
			Producto resultado = consulta.getSingleResult();
			
			em.close();
			return resultado;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}