package Presentacion.GUIFrames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import Controlador.ConfiguradorInicial;
import Controlador.Controlador;
import Controlador.TC;
import LogicaNegocio.Transfers.TransferConexion;
import Presentacion.Lenguajes.Lenguaje;
import Utilidades.ImagePath;
import Utilidades.ConectorDBMS.FactoriaConectores;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/

public class GUI_SeleccionarConexion extends javax.swing.JDialog  implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private TransferConexion _conexion;
	// Variables declaration - do not modify
	private JButton botonCancelar;
	private JLabel labelIcono;
	private JScrollPane jScrollPane1;
	private JButton botonNueva;
	private JButton botonAceptar;
	private JTable tablaConjuntos;
	private JButton botonBorrar;
	private JButton botonEditar;
	// End of variables declaration

	public GUI_SeleccionarConexion() {
		initComponents();
	}

	private void initComponents() {

	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		this.setSize(605, 261);
		{
			labelIcono = new JLabel();
			getContentPane().add(labelIcono);
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.RATON)));
			labelIcono.setBounds(12, 12, 100, 100);
		}
		{
			jScrollPane1 = new JScrollPane();
			getContentPane().add(jScrollPane1);
			jScrollPane1.setBounds(149, 34, 415, 126);
			{
				TableModel tablaModel = 
					new DefaultTableModel(
							new String[][] { { "" } },
							new String[] { Lenguaje.getMensaje(Lenguaje.SELECT_CONNECTION)+":" });
				tablaConjuntos = new JTable();
				jScrollPane1.setViewportView(tablaConjuntos);
				tablaConjuntos.setModel(tablaModel);
				tablaConjuntos.setBounds(58, 16, 307, 81);
				tablaConjuntos.setPreferredSize(new java.awt.Dimension(309, 106));
			}
			
			
		}
		{
			botonAceptar = new JButton();
			getContentPane().add(botonAceptar);
			botonAceptar.setText(Lenguaje.getMensaje(Lenguaje.CONNECT));
			botonAceptar.setBounds(383, 180, 80, 25);
			botonAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonAceptarActionPerformed(evt);
				}
			});
			botonAceptar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonCancelar.grabFocus();}
					else if(e.getKeyCode()==37){botonEditar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
						
		}
		{
			botonCancelar = new JButton();
			getContentPane().add(botonCancelar);
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(484, 180, 80, 25);
			botonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonCancelarActionPerformed(evt);
				}
			});
			botonCancelar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonAceptar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonCancelar.setMnemonic(Lenguaje.getMensaje(Lenguaje.CANCEL).charAt(0));
		}
		{
			botonNueva = new JButton();
			getContentPane().add(botonNueva);
			botonNueva.setText(Lenguaje.getMensaje(Lenguaje.NEW2));
			botonNueva.setBounds(80, 180, 80, 25);
			botonNueva.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {}
				@Override
				public void keyReleased(KeyEvent e) {}
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonBorrar.grabFocus();}
				}
			});
			botonNueva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonNuevaActionPerformed(evt);
				}
			});
			botonNueva.setMnemonic(Lenguaje.getMensaje(Lenguaje.NEW2).charAt(0));
		}
		{
			botonBorrar = new JButton();
			getContentPane().add(botonBorrar);
			botonBorrar.setText(Lenguaje.getMensaje(Lenguaje.DELETE));
			botonBorrar.setBounds(181, 180, 80, 25);
			
			botonBorrar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonNueva.grabFocus();}
					else if(e.getKeyCode()==39){botonEditar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonBorrarActionPerformed(evt);
				}
			});
			//botonBorrar.setMnemonic(Lenguaje.getMensaje(Lenguaje.DELETE).charAt(0));
		}
		{
			botonEditar = new JButton();
			getContentPane().add(botonEditar);
			botonEditar.setText(Lenguaje.getMensaje(Lenguaje.ACCEPT));
			botonEditar.setBounds(283, 180, 80, 25);
			
			botonEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonEditarActionPerformed(evt);
				}
			});
			botonEditar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonAceptar.grabFocus();}
					else if(e.getKeyCode()==37){botonBorrar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonEditar.setMnemonic(Lenguaje.getMensaje(Lenguaje.ACCEPT).charAt(0));
		}

		this.addKeyListener(this);
		TableModel tablaModel = 
			new DefaultTableModel(
					new String[][] { { "" } },
					new String[] { Lenguaje.getMensaje(Lenguaje.EXISTING_CONN) });
		tablaConjuntos = new JTable();
		jScrollPane1.setViewportView(tablaConjuntos);
		tablaConjuntos.setModel(tablaModel);
		this.addKeyListener(this);	
	}

	/*
	 * Oyentes de los botones 
	 */
	private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {                                              
		String elegido = getElementoSeleccionado();		
		if (elegido == null){
			JOptionPane.showMessageDialog(
							null, 
							Lenguaje.getMensaje(Lenguaje.CHOOSE_CONN), 
							Lenguaje.getMensaje(Lenguaje.ERROR), 
							0);
			return;
		}
		elegido = elegido.substring(0, elegido.indexOf("(") - 1);
		
		ConfiguradorInicial config = new ConfiguradorInicial();
		config.leerFicheroConfiguracion();
		
		TransferConexion tc = config.obtenConexion(elegido);
		
		// Conectar a base de datos
		String connectionString = "";
		
		switch(tc.getTipoConexion()){
		case (FactoriaConectores.CONECTOR_MYSQL):
			connectionString += "jdbc:mysql://";
			break;
		case (FactoriaConectores.CONECTOR_ORACLE):
			connectionString += "";
			break;
		case (FactoriaConectores.CONECTOR_MSACCESS_ODBC):
			connectionString += "jdbc:odbc:";
			break;
		}
		
		int dosPuntos = tc.getRuta().indexOf(":");
		if ( dosPuntos > 0 
				&&
				dosPuntos == tc.getRuta().length() - 1){
			connectionString += tc.getRuta().substring(0, dosPuntos);
		}else{
			connectionString += tc.getRuta();
		}
		
		if (_conexion.getTipoConexion() != FactoriaConectores.CONECTOR_ORACLE){
			connectionString += "/";
		}else{
			connectionString += "#" + tc.getDatabase();
		}
		
		if (this._conexion.getTipoConexion() == 
						FactoriaConectores.CONECTOR_MSACCESS_MDB){
			connectionString = tc.getDatabase();
		}
		
		tc.setRuta(connectionString);
		
		// Enviar datos para la conexión
		controlador.mensajeDesde_GUI(TC.GUIConfigurarConexionDBMS_Click_BotonEjecutar, tc);
		
		this.setInactiva();
	}                                       
		
	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                              
		this.setInactiva();
	}
	
	private void botonNuevaActionPerformed(ActionEvent evt) {
		controlador.getTheGUIConfigurarConexionDBMS().setConexion(_conexion);
		controlador.getTheGUIConfigurarConexionDBMS().setActiva(true, "", null);
	}
	
	private void botonBorrarActionPerformed(ActionEvent evt) {
		// <connection name="Conexion 3" type="0" path="localhost" database="test2" user="root" password="123456"  /> 
		String elegido = getElementoSeleccionado();		
		if (elegido == null){
			JOptionPane.showMessageDialog(
							null, 
							Lenguaje.getMensaje(Lenguaje.CHOOSE_CONN), 
							Lenguaje.getMensaje(Lenguaje.ERROR), 
							0);
			return;
		}
		elegido = elegido.substring(0, elegido.indexOf("(") - 1);
		
		ConfiguradorInicial config = new ConfiguradorInicial();
		config.leerFicheroConfiguracion();
		config.quitaConexion(elegido);
		config.guardarFicheroCofiguracion();
		
		rellenaTabla();
	}
	
	private void botonEditarActionPerformed(ActionEvent evt) {
		String elegido = getElementoSeleccionado();		
		if (elegido == null){
			JOptionPane.showMessageDialog(
							null, 
							Lenguaje.getMensaje(Lenguaje.CHOOSE_CONN), 
							Lenguaje.getMensaje(Lenguaje.ERROR), 
							0);
			return;
		}
		elegido = elegido.substring(0, elegido.indexOf("(") - 1);
		
		ConfiguradorInicial config = new ConfiguradorInicial();
		config.leerFicheroConfiguracion();
		TransferConexion tc = config.obtenConexion(elegido);
		
		controlador.getTheGUIConfigurarConexionDBMS().setActiva(false, elegido, tc);
	}

	private String getElementoSeleccionado() {
		int fila = tablaConjuntos.getSelectedRow();
		int col = tablaConjuntos.getSelectedColumn();

		String elegido = null;
		try {
			elegido = tablaConjuntos.getModel().getValueAt(fila, col).toString();	
		}catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
		
		return elegido;
	}
	
	public void rellenaTabla() {
		// Obtener las conexiones a mostrar
		ConfiguradorInicial config = new ConfiguradorInicial();
		config.leerFicheroConfiguracion();
		
		Hashtable<String, TransferConexion> conexiones;
		conexiones = config.obtenConexiones();
		
		String[][] valores = new String[conexiones.size()][1];
		
		Enumeration<String> keys = conexiones.keys();
		
		int i = 0;
		while (keys.hasMoreElements()){
			String nombre = keys.nextElement();
			TransferConexion conexion = conexiones.get(nombre);
			String usuario = conexion.getUsuario();
			String database = conexion.getDatabase();
			String dbms = FactoriaConectores.obtenerTodosLosConectores().get(
								conexion.getTipoConexion());
			
			// Añadir a la lista el nombre de cada conexión
			valores[i][0] = nombre + " (" + usuario + "@" + database + ", " + dbms + ")";
			i ++;
		}
		
		TableModel tablaModelo = new DefaultTableModel(valores, new String[] {
										Lenguaje.getMensaje(Lenguaje.TABLE_TITLE)});
		tablaConjuntos.setModel(tablaModelo);
	}
	
			
	/*
	 * Activar y desactivar el dialogo
	 */
	public void setActiva(){
		this.centraEnPantalla();
		this.setTitle(Lenguaje.getMensaje(Lenguaje.SELECT_CONNECTION));
		rellenaTabla();
		SwingUtilities.invokeLater(doFocus);
		this.setVisible(true);
	}
	
	private Runnable doFocus = new Runnable() {
	     public void run() {
	         botonNueva.grabFocus();
	     }
	 };
	
	public void setInactiva(){
		this.setVisible(false);
		this.dispose();
	}

	public void keyPressed( KeyEvent e ) {
		switch (e.getKeyCode()){
			case 27: {
				this.setInactiva();
				break;
			}
			case 10:{
				this.botonAceptarActionPerformed(null);
				break;
			}
		}
	} 
	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}

	public void mouseEntered( MouseEvent e ) {} 
	
	public void mouseClicked(MouseEvent arg0) {
		this.requestFocus();
	}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}
	
	
	
	/*
	 * Utilidades
	 */
	private void centraEnPantalla(){
		// Tamano de la pantalla
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		// Alto
		String altoString = String.valueOf(this.getSize().getWidth());
		altoString = altoString.substring(0,altoString.indexOf("."));
		int altoInt = Integer.parseInt(altoString);
		// Ancho
		String anchoString = String.valueOf(this.getSize().getHeight());
		anchoString = anchoString.substring(0,anchoString.indexOf("."));
		int anchoInt = Integer.parseInt(anchoString);

		setBounds((screenSize.width-altoInt)/2, (screenSize.height-anchoInt)/2, altoInt, anchoInt);
	}
		
	/*
	 * Getters y Setters
	 */
	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	
	public void setConexion(TransferConexion con){
		this._conexion = con;
	}
}
