package vista.GUIFrames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.ConfiguradorInicial;
import controlador.Controlador;
import controlador.TC;
import modelo.conectorDBMS.FactoriaConectores;
import modelo.tools.ImagePath;
import modelo.transfers.TransferConexion;
import modelo.transfers.TransferEntidad;
import modelo.transfers.TransferRelacion;
import vista.lenguaje.Lenguaje;

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
public class GUI_Conexion extends javax.swing.JDialog implements KeyListener, MouseListener{
 

	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private Vector<TransferEntidad> listaEntidades;
	private boolean _crear;
	// Variables declaration - do not modify
	private JButton botonCancelar;
	private JLabel usuario;
	private JTextField cajaPuerto;
	private JLabel password;
	private JTextField cajaBase;
	private JButton botonAnadir;
	private JTextField cajaServer;
	private JLabel puerto;
	private JLabel labelIcono;
	private JTextPane server;
	private TransferRelacion relacion;
	private JTextPane base;
	private JTextField cajaUsuario;
	private JButton btnExaminar;
	private JButton btnComprobar;
	private JButton btnLimpiar;
	private JButton btnPista;
	private JTextField textoNombre;
	private JLabel nombre;
	private JPasswordField cajaPass;
	
	protected TransferConexion _conexion;

	public GUI_Conexion() {
		initComponents();
	}

	private void initComponents() {
		setTitle(Lenguaje.getMensaje(Lenguaje.SHAPE_DBMS));
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
        getContentPane().setLayout(null);
        getContentPane().add(getserver());
        getContentPane().add(getLabelIcono());
        getContentPane().add(getpuerto());
        getContentPane().add(getcajaServer());
        getContentPane().add(getcajaPuerto());
        getContentPane().add(getusuario());
        getContentPane().add(getbase());
        getContentPane().add(getcajaUsuario());
        getContentPane().add(getBotonCancelar());
        getContentPane().add(getBotonAnadir());
        getContentPane().add(getcajaBase());
        getContentPane().add(getpassword());
        getContentPane().add(getCajaPass());
        getContentPane().add(getNombre());
        getContentPane().add(getTextoNombre());
        getContentPane().add(getBtnPista());
        getContentPane().add(getBtnLimpiar());
        getContentPane().add(getBtnComprobar());
        getContentPane().add(getBtnExaminar());
        this.setSize(682, 339);
        this.addMouseListener(this);
		this.addKeyListener(this);
    }

	/*
	 * Activar y desactivar el dialogo
	 */
	
	public void setActiva(boolean crear, String nombre, TransferConexion tc){
		this._crear = crear;
		if (crear) {
			this.cajaPass.setText("");
			this.textoNombre.setText("");
			this.textoNombre.setEnabled(true);
			this.cajaServer.setText("");
			this.cajaPuerto.setText("");
			this.cajaUsuario.setText("");
			this.cajaBase.setText("");
		
			if (_conexion.getTipoConexion() == FactoriaConectores.CONECTOR_MSACCESS_MDB){
				this.server.setEnabled(false);
				this.puerto.setEnabled(false);
				this.usuario.setEnabled(false);
				this.password.setEnabled(false);
				
				this.cajaServer.setEnabled(false);
				this.cajaPuerto.setEnabled(false);
				this.cajaBase.setEnabled(true);
				this.btnExaminar.setVisible(true);
				this.btnExaminar.setEnabled(true);
				this.cajaUsuario.setEnabled(false);
				this.cajaPass.setEnabled(false);
			}else{
				this.server.setEnabled(true);
				this.puerto.setEnabled(true);
				this.usuario.setEnabled(true);
				this.password.setEnabled(true);
				
				this.cajaServer.setEnabled(true);
				this.cajaPuerto.setEnabled(true);
				this.cajaBase.setEnabled(true);
				this.btnExaminar.setVisible(false);
				this.btnExaminar.setEnabled(false);
				this.cajaUsuario.setEnabled(true);
				this.cajaPass.setEnabled(true);
			}
		} else {
			
			// EDITAR
			this.textoNombre.setText(nombre);
			this.textoNombre.setEnabled(false);
			
			if (tc.getTipoConexion() == FactoriaConectores.CONECTOR_MSACCESS_MDB){
				this.server.setEnabled(false);
				this.puerto.setEnabled(false);
				this.usuario.setEnabled(false);
				this.password.setEnabled(false);
				
				this.cajaServer.setEnabled(false);
				this.cajaServer.setText("");
				this.cajaPuerto.setEnabled(false);
				this.cajaPuerto.setText("");
				this.cajaBase.setEnabled(true);
				this.cajaBase.setText(tc.getRuta());
				this.cajaUsuario.setEnabled(false);
				this.cajaUsuario.setText("");
				this.cajaPass.setEnabled(false);
				this.cajaPass.setText("");
			}else{
				String server = tc.getRuta();
				String port = "";
				if (server.lastIndexOf(":") > 0){
					port = server.substring(server.indexOf(":")+1);
					server = server.substring(0, server.indexOf(":"));
				}
				this.cajaBase.setText(tc.getDatabase());
				this.cajaBase.setEnabled(true);
				this.cajaServer.setText(server);
				this.cajaServer.setEnabled(true);
				this.cajaPuerto.setText(port);
				this.cajaPuerto.setEnabled(true);
				this.cajaUsuario.setText(tc.getUsuario());
				this.cajaUsuario.setEnabled(true);
				this.cajaPass.setText(tc.getPassword());
				this.cajaPass.setEnabled(true);
			}
			
			_conexion = tc;
		}
		
		this.centraEnPantalla();
		SwingUtilities.invokeLater(doFocus);
		this.setVisible(true);	
	}
	
	private Runnable doFocus = new Runnable() {
	     public void run() {
	         cajaServer.grabFocus();
	     }
	 };
	
	public void setInactiva(){
		this.setVisible(false);
	}

	/*
	 * Oyentes de los botones
	 */

	private void botonConnectActionPerformed(java.awt.event.ActionEvent evt) {
		// Extraer datos
		String txtServer = this.cajaServer.getText();
		String txtPuerto = this.cajaPuerto.getText();
		String txtDatabase = this.cajaBase.getText();
		String txtUsuario = this.cajaUsuario.getText();
		String txtPassword = new String(this.cajaPass.getPassword());
		
		if (_crear){
			// CREAR
	
			// Comprobar datos
			ConfiguradorInicial conf = new ConfiguradorInicial();
			conf.leerFicheroConfiguracion();
			if (!conf.estaDisponibleNombreConexion(textoNombre.getText())){
				// Notificar error
				JOptionPane.showMessageDialog(
						null,
						(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
						"Ya existe una conexión con ese nombre",
						(Lenguaje.getMensaje(Lenguaje.DBDT)),
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
				return;
			}
			
			boolean faltanDatos = false;
			faltanDatos &= txtDatabase.equals("");
			faltanDatos &= cajaBase.getText().equalsIgnoreCase("");
			if (faltanDatos){
				// Notificar error
				JOptionPane.showMessageDialog(
					null,
					(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
					(Lenguaje.getMensaje(Lenguaje.INFORMATION_INCOMPLETE)),
					(Lenguaje.getMensaje(Lenguaje.DBDT)),
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
				return;
				
			}else{
				// Guardar conexion
				TransferConexion guardada = new TransferConexion(			
						this._conexion.getTipoConexion(), 
						txtServer + ":" + txtPuerto,
						false,
						txtDatabase,
						txtUsuario,
						txtPassword);
				conf.anadeConexion(textoNombre.getText(), guardada);
				conf.guardarFicheroCofiguracion();
			}
			
			controlador.getTheGuiSeleccionarConexion().rellenaTabla();
			
			this.setInactiva();
		}else{
			
			// EDITAR
			// Comprobar datos
			boolean faltanDatos = false;
			faltanDatos &= txtDatabase.equals("");
			faltanDatos &= cajaBase.getText().equalsIgnoreCase("");
			if (faltanDatos){
				// Notificar error
				JOptionPane.showMessageDialog(
					null,
					(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
					(Lenguaje.getMensaje(Lenguaje.INFORMATION_INCOMPLETE)),
					(Lenguaje.getMensaje(Lenguaje.DBDT)),
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
				return;
				
			}else{
				// Guardar conexion
				TransferConexion guardada = new TransferConexion(			
						this._conexion.getTipoConexion(), 
						txtServer + ":" + txtPuerto,
						false,
						txtDatabase,
						txtUsuario,
						txtPassword);
				
				ConfiguradorInicial conf = new ConfiguradorInicial();
				conf.leerFicheroConfiguracion();
				conf.quitaConexion(textoNombre.getText());
				conf.anadeConexion(textoNombre.getText(), guardada);
				conf.guardarFicheroCofiguracion();
			}
			
			controlador.getTheGuiSeleccionarConexion().rellenaTabla();
			
			this.setInactiva();
		}
	}

	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		this.setVisible(false);
	}
	public void keyPressed( KeyEvent e ) {
		switch (e.getKeyCode()){
			case 27: {
				this.setInactiva();
				break;
			}
			case 10:{
				this.botonConnectActionPerformed(null);
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
	
	//Oyente para todos los elementos
	private KeyListener general = new KeyListener() {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==10){botonConnectActionPerformed(null);}
			if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	
	/*
	 * Interfaz
	 */
	
	private JTextPane getserver() {
		if(server == null) {
			server = new JTextPane();
			server.setText(Lenguaje.getMensaje(Lenguaje.SERVER));
			//server.setText("Servidor");
			server.setEditable(false);
			server.setOpaque(false);
			server.setBounds(118, 51, 89, 21);
			server.setFocusable(false);
		}
		return server;
	}
	
	private JLabel getLabelIcono() {
		if(labelIcono == null) {
			labelIcono = new JLabel();
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.TECLADO)));
			labelIcono.setBounds(12, 15, 100, 87);
		}
		return labelIcono;
	}

	
	
	private JLabel getpuerto() {
		if(puerto == null) {
			puerto = new JLabel();
			puerto.setText(Lenguaje.getMensaje(Lenguaje.PORT));
			//puerto.setText("Puerto");
			puerto.setBounds(122, 96, 75, 21);
		}
		return puerto;
	}
	
	private JTextField getcajaServer() {
		if(cajaServer == null) {
			cajaServer = new JTextField();
			cajaServer.setBounds(219, 52, 321, 21);
			cajaServer.addKeyListener(general);
		}
		return cajaServer;
	}
	
	private JTextField getcajaPuerto() {
		if(cajaPuerto == null) {
			cajaPuerto = new JTextField();
			cajaPuerto.setBounds(220, 92, 320, 21);
			cajaPuerto.addKeyListener(general);
		}
		return cajaPuerto;
	}
	
	private JLabel getusuario() {
		if(usuario == null) {
			usuario = new JLabel();
			usuario.setText(Lenguaje.getMensaje(Lenguaje.USER));
			//usuario.setText("Usuario");
			usuario.setBounds(122, 178, 79, 21);
		}
		return usuario;
	}
	
	private JTextPane getbase() {
		if(base == null) {
			base = new JTextPane();
			base.setText(Lenguaje.getMensaje(Lenguaje.DATA_BASE));
			//base.setText("Base de datos");
			base.setEditable(false);
			base.setOpaque(false);
			base.setBounds(118, 129, 102, 21);
			base.setFocusable(false);
		}
		return base;
	}
	
	private JTextField getcajaUsuario() {
		if(cajaUsuario == null) {				
			cajaUsuario = new JTextField();
			cajaUsuario.setBounds(219, 175, 320, 21);
		}
		cajaUsuario.addKeyListener(general);
		return cajaUsuario;
	}
	
	
	private JButton getBotonCancelar() {
		if(botonCancelar == null) {
			botonCancelar = new JButton();
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(459, 274, 80, 25);
			botonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonCancelarActionPerformed(evt);
				}
			});
			botonCancelar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonAnadir.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonCancelar.setMnemonic(	Lenguaje.getMensaje(Lenguaje.CANCEL).charAt(0));
		}
		return botonCancelar;
	}
	
	private JButton getBotonAnadir() {
		if(botonAnadir == null) {
			botonAnadir = new JButton();
			botonAnadir.setText(Lenguaje.getMensaje(Lenguaje.ACCEPT));
			botonAnadir.setBounds(358, 274, 80, 25);
			botonAnadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonConnectActionPerformed(evt);
				}
			});
			botonAnadir.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonConnectActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonCancelar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonAnadir.setMnemonic(Lenguaje.getMensaje(Lenguaje.ACCEPT).charAt(0));
		}
		return botonAnadir;
	}
	
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
	 * Getters y setters
	 */

	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public Vector<TransferEntidad> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(Vector<TransferEntidad> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public TransferRelacion getRelacion() {
		return relacion;
	}

	public void setRelacion(TransferRelacion relacion) {
		this.relacion = relacion;
	}
	
	private JTextField getcajaBase() {
		if(cajaBase == null) {
			cajaBase = new JTextField();
			cajaBase.setBounds(219, 133, 320, 21);
		}
		return cajaBase;
	}
	
	private JLabel getpassword() {
		if(password == null) {
			password = new JLabel();
			password.setText(Lenguaje.getMensaje(Lenguaje.PASSWORD));
			//password.setText("Contraseña");
			password.setBounds(119, 225, 88, 16);
		}
		return password;
	}
	
	private JPasswordField getCajaPass() {
		if(cajaPass == null) {
			cajaPass = new JPasswordField();
			cajaPass.setBounds(219, 219, 320, 21);
		}
		return cajaPass;
	}
	

	public void setConexion(TransferConexion con){
		this._conexion = con;
	}
	
	private JLabel getNombre() {
		if(nombre == null) {
			nombre = new JLabel();
			//nombre.setText("Nombre");
			nombre.setText(Lenguaje.getMensaje(Lenguaje.NAME));
			nombre.setBounds(124, 19, 49, 27);
		}
		return nombre;
	}
	
	private JTextField getTextoNombre() {
		if(textoNombre == null) {
			textoNombre = new JTextField();
			textoNombre.setBounds(219, 12, 321, 23);
		}
		return textoNombre;
	}
	
	private JButton getBtnPista() {
		if(btnPista == null) {
			btnPista = new JButton();
			btnPista.setText(Lenguaje.getMensaje(Lenguaje.HINT));
			btnPista.setBounds(12, 274, 81, 26);
		}
		
		btnPista.addActionListener(new ActionListener() {
			// Completar los cuadros con información de ejemplo
			public void actionPerformed(ActionEvent evt) {
				switch(_conexion.getTipoConexion()){
				case FactoriaConectores.CONECTOR_MYSQL:
					cajaServer.setText("localhost");
					cajaPuerto.setText("");
					cajaBase.setText("test");
					cajaUsuario.setText("root");
					cajaPass.setText("");
					break;
				case FactoriaConectores.CONECTOR_MSACCESS_MDB:
					cajaServer.setText("");
					cajaPuerto.setText("");
					cajaBase.setText("c:\\hlocal\\mydatabase.mdb");
					cajaUsuario.setText("");
					cajaPass.setText("");
					break;
				case FactoriaConectores.CONECTOR_ORACLE:
					cajaServer.setText("tania.fdi.ucm.es");
					cajaPuerto.setText("1521");
					cajaBase.setText("db003");
					cajaUsuario.setText("ges00");
					cajaPass.setText("");
					break;
				}
			}
		});
		
		return btnPista;
	}
	
	private JButton getBtnLimpiar() {
		if(btnLimpiar == null) {
			btnLimpiar = new JButton();
			btnLimpiar.setText(Lenguaje.getMensaje(Lenguaje.CLEAN_FIELDS));
			btnLimpiar.setBounds(112, 273, 81, 27);
		}
		
		btnLimpiar.addActionListener(new ActionListener() {
			// Limpiar los cuadros de texto
			public void actionPerformed(ActionEvent evt) {
				textoNombre.setText("");
				cajaServer.setText("");
				cajaPuerto.setText("");
				cajaBase.setText("");
				cajaUsuario.setText("");
				cajaPass.setText("");
			}
		});
		
		return btnLimpiar;
	}
	
	private JButton getBtnComprobar() {
		if(btnComprobar == null) {
			btnComprobar = new JButton();
			btnComprobar.setText(Lenguaje.getMensaje(Lenguaje.TEST_DATA));
			btnComprobar.setBounds(212, 274, 129, 25);
		}
		
		btnComprobar.addActionListener(new ActionListener() {
			// Comprueba si los datos de conexión son correctos
			public void actionPerformed(ActionEvent evt) {
				// Extraer datos
				String txtServer = cajaServer.getText();
				String txtPuerto = cajaPuerto.getText();
				String txtDatabase = cajaBase.getText();
				String txtUsuario = cajaUsuario.getText();
				String txtPassword = new String(cajaPass.getPassword());
				
				// Comprobar datos
				boolean faltanDatos = false;
				faltanDatos &= txtDatabase.equals("");
				faltanDatos &= cajaBase.getText().equalsIgnoreCase("");
				if (faltanDatos){
					// Notificar error
					JOptionPane.showMessageDialog(
						null,
						(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
						(Lenguaje.getMensaje(Lenguaje.INFORMATION_INCOMPLETE)),
						(Lenguaje.getMensaje(Lenguaje.DBDT)),
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
					return;
					
				}else{
					// Generar la connectionString
					String connectionString = "";
					
					switch(_conexion.getTipoConexion()){
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
					
					connectionString += txtServer;
					if (!txtPuerto.equalsIgnoreCase("")){
						connectionString += ":" + txtPuerto;
					}
					
					if (_conexion.getTipoConexion() != FactoriaConectores.CONECTOR_ORACLE){
						connectionString += "/";
					}else{
						connectionString += "#" + txtDatabase;
					}
					
					if (_conexion.getTipoConexion() == 
									FactoriaConectores.CONECTOR_MSACCESS_MDB){
						connectionString = _conexion.getDatabase();
					}
					
					// Probar conexion
					TransferConexion con = new TransferConexion(			
							_conexion.getTipoConexion(), 
							connectionString,
							false,
							txtDatabase,
							txtUsuario,
							txtPassword);
					
					controlador.mensajeDesde_GUI(TC.GUIConexionDBMS_PruebaConexion, con);
				}
			}
		});
		
		return btnComprobar;
	}
	
	private JButton getBtnExaminar() {
		if(btnExaminar == null) {
			btnExaminar = new JButton();
			btnExaminar.setText(Lenguaje.getMensaje(Lenguaje.EXPLORE));
			btnExaminar.setBounds(558, 131, 92, 26);
		}
		
		btnExaminar.addActionListener(new ActionListener() {
			// Comprueba si los datos de conexión son correctos
			public void actionPerformed(ActionEvent evt) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Access databases (*.mdb)", "mdb");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       cajaBase.setText(chooser.getSelectedFile().getPath());
			    }
			}
		});	
		
		return btnExaminar;
	}

}

