package Presentacion.GUIPanels;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controlador.TC;
import LogicaNegocio.Transfers.TransferAtributo;
import LogicaNegocio.Transfers.TransferConexion;
import LogicaNegocio.Transfers.TransferDominio;
import LogicaNegocio.Transfers.TransferEntidad;
import LogicaNegocio.Transfers.TransferRelacion;
import Presentacion.Grafo.PanelGrafo;
import Presentacion.Grafo.PanelThumbnail;
import Presentacion.Lenguajes.Lenguaje;
import Presentacion.Theme.Theme;
import Utilidades.ImagePath;

@SuppressWarnings("serial")
public class menuPrincipal extends JMenuBar{

	private TransferConexion conexionActual = null;
	private boolean scriptGeneradoCorrectamente = false;
	// Listas
	private Vector<TransferConexion> listaConexiones;
	private Vector<TransferEntidad> listaEntidades;
	private Vector<TransferAtributo> listaAtributos;
	private Vector<TransferRelacion> listaRelaciones;
	private Vector<TransferDominio> listaDominios;
	//variables de escritura
	private String acumulador="";
	// Componentes
	private TablaVolumenes tablaVolumenes;
	private JPanel panelTablas;
	private JMenu menuSistema;
	private JScrollPane panelScrollSucesos;
	private JButton botonLimpiarPantalla;
	private PanelThumbnail panelGrafo;
	private JTree arbol;
	private JTree arbolDom;
	private JScrollPane panelArbol;
	private JPanel panelInfo;
	private JSplitPane split3;
	private PanelGrafo panelDiseno;
	private JScrollPane panelArbolDom;
	private JPanel panelDom;
	private JTextPane areaTextoSucesos;
	private JPanel panelSucesos;
	private JMenuItem submenuContenidos;
	private JMenuItem submenuAcercaDe;
	private JMenuItem menuVista;
	private JMenu menuAyuda;
	private JMenuItem submenuExportarJPEG;
	private JMenu menuOpciones;
	private JCheckBoxMenuItem menuPanelSucesos;	
	private JMenu menuSGBDActual;
	private Vector<JCheckBoxMenuItem> elementosMenuSGBDActual;
	private JMenu menuLenguajes;
	private Vector<JCheckBoxMenuItem> elementosMenuLenguajes;
	@SuppressWarnings("rawtypes")
	private JComboBox cboSeleccionDBMS;
	private JMenuItem submenuImprimir;
	private JButton botonExportarArchivo;
	private JButton botonScriptSQL;
	private JButton botonModeloRelacional;
	private JButton botonValidar;
	private JButton botonEjecutarEnDBMS;
	private JPanel splitDiagrama;
	private JSplitPane split1;
	private JPanel panelGeneracion;
	private JPanel panelDiagrama;
	private JMenuItem submenuSalir;
	//private JMenuItem submenuWorkSpace;
	private JMenuItem submenuAbrir;
	private JMenuItem submenuGuardar;
	private JMenuItem submenuNuevo;
	private JMenuItem submenuCerrar;
	private JMenuItem submenuGuardarComo;
	private JMenuBar barraDeMenus;
	private static JPopupMenu popup;
	private JRadioButton salvado;
	private boolean mostrarPanelSucesos;
	private Theme theme;
	private JMenu themeMenu;
	private JScrollPane scrollPanelTablas;
	protected JTextPane codigoText;
	protected JTextPane modeloText;
	
	
	public menuPrincipal() {
		add(Box.createRigidArea(new Dimension(0,30)));
		setOpaque(true);
		setBorder(BorderFactory.createCompoundBorder(
				null, 
				null));
		
		{	//File
			menuSistema = new JMenu();
			add(menuSistema);
			menuSistema.setText(Lenguaje.getMensaje(Lenguaje.FILE));
			menuSistema.setMnemonic(Lenguaje.getMensaje(Lenguaje.FILE).charAt(0));
			
			{//File/new
				submenuNuevo = new JMenuItem();
				menuSistema.add(submenuNuevo);
				submenuNuevo.setText(Lenguaje.getMensaje(Lenguaje.NEW));
				submenuNuevo.setMnemonic(Lenguaje.getMensaje(Lenguaje.NEW).charAt(0));
				submenuNuevo.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.N_NUEVO)));
				submenuNuevo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuNuevoActionPerformed(evt);
					}
				});
			}
			{//File/open
				submenuAbrir = new JMenuItem();
				menuSistema.add(submenuAbrir);
				submenuAbrir.setText(Lenguaje.getMensaje(Lenguaje.OPEN)+"...");
				submenuAbrir.setMnemonic(Lenguaje.getMensaje(Lenguaje.OPEN).charAt(0));
				submenuAbrir.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.N_ABRIR)));
				submenuAbrir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuAbrirActionPerformed(evt);
					}
				});
			}
			{//File/close
				submenuCerrar = new JMenuItem();
				menuSistema.add(submenuCerrar);
				submenuCerrar.setText(Lenguaje.getMensaje(Lenguaje.CLOSE));
				submenuCerrar.setMnemonic(Lenguaje.getMensaje(Lenguaje.CLOSE).charAt(0));
				submenuCerrar.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.N_CERRAR)));
				submenuCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuCerrarActionPerformed(evt);
					}
				});
			}//File/separator
			menuSistema.add(new JSeparator());
			{//File/save
				submenuGuardar = new JMenuItem();
				menuSistema.add(submenuGuardar);
				submenuGuardar.setText(Lenguaje.getMensaje(Lenguaje.SAVE));
				submenuGuardar.setMnemonic(Lenguaje.getMensaje(Lenguaje.SAVE).charAt(0));
				submenuGuardar.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.N_GUARDAR)));
				submenuGuardar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuGuardarActionPerformed(evt);
					}
				});
			}
			{//File/save as...
				submenuGuardarComo = new JMenuItem();
				menuSistema.add(submenuGuardarComo);
				submenuGuardarComo.setText(Lenguaje.getMensaje(Lenguaje.SAVE_AS)+"...");
				submenuGuardarComo.setMnemonic(Lenguaje.getMensaje(Lenguaje.SAVE_AS).charAt(1));
				//submenuGuardarComo.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.DIRECTORIO)));
				submenuGuardarComo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuGuardarComoActionPerformed(evt);
					}
				});
			}//File/separator2
			menuSistema.add(new JSeparator());
			{//File/imprimir
				submenuImprimir = new JMenuItem();
				menuSistema.add(submenuImprimir);
				submenuImprimir.setText(Lenguaje.getMensaje(Lenguaje.PRINT_DIAGRAM)+"...");
				submenuImprimir.setMnemonic(Lenguaje.getMensaje(Lenguaje.PRINT_DIAGRAM).charAt(0));
				submenuImprimir.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.IMPRESORA)));
				submenuImprimir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuImprimirActionPerformed(evt);
					}
				});
			}
			{//File/Export
				submenuExportarJPEG = new JMenuItem();
				menuSistema.add(submenuExportarJPEG);
				submenuExportarJPEG.setText(Lenguaje.getMensaje(Lenguaje.EXPORT_DIAGRAM));
				submenuExportarJPEG.setMnemonic(Lenguaje.getMensaje(Lenguaje.EXPORT_DIAGRAM).charAt(0));
				submenuExportarJPEG.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.EXPORTARIMAGEN)));
				submenuExportarJPEG.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuExportarJPEGActionPerformed(evt);
					}
				});
			}//File/Separator
			menuSistema.add(new JSeparator());
			{//File/salir
				submenuSalir = new JMenuItem();
				menuSistema.add(submenuSalir);
				submenuSalir.setText(Lenguaje.getMensaje(Lenguaje.EXIT_MINCASE));
				submenuSalir.setMnemonic(Lenguaje.getMensaje(Lenguaje.EXIT_MINCASE).charAt(0));
				submenuSalir.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.SALIR)));
				submenuSalir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuSalirActionPerformed(evt);
					}
				});
			}
		}
		{//Opciones
			menuOpciones = new JMenu();
			barraDeMenus.add(menuOpciones);
			menuOpciones.setText(Lenguaje.getMensaje(Lenguaje.OPTIONS));
			menuOpciones.setMnemonic(Lenguaje.getMensaje(Lenguaje.OPTIONS).charAt(0));
			{
				// Menú GESTORES DE BASES DE DATOS
				menuSGBDActual = new JMenu();
				menuSGBDActual.setFont(new java.awt.Font("Avenir", 0, 16));
				menuOpciones.add(menuSGBDActual);
				menuSGBDActual.setText(Lenguaje.getMensaje(Lenguaje.CURRENT_DBMS));
				menuSGBDActual.setMnemonic(Lenguaje.getMensaje(Lenguaje.CURRENT_DBMS).charAt(0));
				menuSGBDActual.setIcon(new ImageIcon(
						getClass().getClassLoader().getResource(
								ImagePath.SELECCIONARSGBD)));
				elementosMenuSGBDActual = new Vector<JCheckBoxMenuItem>(0,1);
				
				// Añadir las conexiones listadas
				if(listaConexiones!=null) {
					for (int i=0; i < listaConexiones.size(); i++){
						TransferConexion tc = listaConexiones.get(i);
						
						JCheckBoxMenuItem menuConexion = new JCheckBoxMenuItem();
						menuConexion.setText(tc.getRuta());
						menuConexion.setSelected(i == 0);
						
						menuConexion.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// Obtener el checkBox que ha sido pulsado
								JCheckBoxMenuItem check = (JCheckBoxMenuItem) e.getSource();
								System.out.println("He pulsado la opción " + check.getText());
								
								// Cambiar la conexión actual
								cambiarConexion(check.getText());
							}
						});
						
						menuSGBDActual.add(menuConexion);
						elementosMenuSGBDActual.add(menuConexion);
					}
				}
				// Menu SELECCIONAR LENGUAJE
				menuLenguajes = new JMenu();
				menuLenguajes.setFont(new java.awt.Font("Avenir", 0, 16));
				menuOpciones.add(menuLenguajes);
				menuLenguajes.setText(Lenguaje.getMensaje(Lenguaje.SELECT_LANGUAGE));
				menuLenguajes.setMnemonic(Lenguaje.getMensaje(Lenguaje.SELECT_LANGUAGE).charAt(0));
				menuLenguajes.setIcon(new ImageIcon(
						getClass().getClassLoader().getResource(
								ImagePath.SELECCIONARLENGUAJE)));
				
				elementosMenuLenguajes = new Vector<JCheckBoxMenuItem>(0,1);
				
				Vector<String> lenguajes = Lenguaje.obtenLenguajesDisponibles();
				for (int m=0; m<lenguajes.size(); m++){
					JCheckBoxMenuItem lenguaje = new JCheckBoxMenuItem();
					lenguaje.setText(lenguajes.get(m));
					lenguaje.setSelected(lenguajes.get(m).equalsIgnoreCase(
							Lenguaje.getIdiomaActual()));
					
					lenguaje.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// Obtener el checkBox que ha sido pulsado
							JCheckBoxMenuItem check = (JCheckBoxMenuItem) e.getSource();
							System.out.println("Lenguajes: He pulsado la opción " + check.getText());
							
							// Cambiar el lenguaje
							controlador.mensajeDesde_GUIPrincipal(
									TC.GUI_Principal_CambiarLenguaje, 
									check.getText());
							
							// Actualizar los checkBox
							for (int k=0; k<elementosMenuLenguajes.size(); k++){
								JCheckBoxMenuItem l = elementosMenuLenguajes.get(k);
								l.setSelected(l.getText().equalsIgnoreCase(Lenguaje.getIdiomaActual()));
							}
						}
					});
					
					menuLenguajes.add(lenguaje);
					elementosMenuLenguajes.add(lenguaje);
				}
				
			}	
		}
		{
			menuVista = new JMenu();
			barraDeMenus.add(menuVista);
			menuVista.setText("View");
			menuVista.setMnemonic(Lenguaje.getMensaje(Lenguaje.HELP).charAt(0));
			{		
				
				JCheckBoxMenuItem model = new JCheckBoxMenuItem();
				JCheckBoxMenuItem er = new JCheckBoxMenuItem();
				JCheckBoxMenuItem code = new JCheckBoxMenuItem();
				menuPanelSucesos = new JCheckBoxMenuItem();
				themeMenu = new JMenu();
				for(String s : this.theme.getAvaiableThemes()) {
					JCheckBoxMenuItem item = new JCheckBoxMenuItem();
					//if(theme.getThemeName().equals(s))item.setSelected(true);
					item.setText(s);
					item.setFont(new java.awt.Font("Avenir", 0, 16));
					item.setActionCommand(s);
					item.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							cambiaTema(e);
						}
						
					});
					themeMenu.add(item);
				}
				
				themeMenu.setFont(new java.awt.Font("Avenir", 0, 16));
				menuVista.add(er);
				menuVista.add(model);
				menuVista.add(code);
				menuVista.add(new JSeparator());
				menuVista.add(themeMenu);
				
				
				er.setText(Lenguaje.getMensaje(Lenguaje.CONC_MODEL));
				er.setSelected(true);
				er.setFont(new java.awt.Font("Avenir", 0, 16));
				model.setText(Lenguaje.getMensaje(Lenguaje.LOGIC_MODEL));
				model.setSelected(false);
				model.setFont(new java.awt.Font("Avenir", 0, 16));
				code.setText(Lenguaje.getMensaje(Lenguaje.PHYS_MODEL));
				code.setSelected(false);
				code.setFont(new java.awt.Font("Avenir", 0, 16));
				themeMenu.setText("Tema");
				
				menuPanelSucesos.setText(Lenguaje.getMensaje(Lenguaje.SHOW_EVENTS_PANEL));
				menuPanelSucesos.setMnemonic(Lenguaje.getMensaje(Lenguaje.SHOW_EVENTS_PANEL).charAt(0));
				menuPanelSucesos.setFont(new java.awt.Font("Avenir", 0, 16));
				
				menuPanelSucesos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						menuPanelSucesosActionPerformed(evt);
					}
				});
					
			}
		}
		{//Ayuda
			menuAyuda = new JMenu();
			barraDeMenus.add(menuAyuda);
			menuAyuda.setText(Lenguaje.getMensaje(Lenguaje.HELP));
			menuAyuda.setMnemonic(Lenguaje.getMensaje(Lenguaje.HELP).charAt(0));
			{//Ayuda/contenidos
				submenuContenidos = new JMenuItem();
				menuAyuda.add(submenuContenidos);
				submenuContenidos.setText(Lenguaje.getMensaje(Lenguaje.CONTENTS));
				submenuContenidos.setMnemonic(Lenguaje.getMensaje(Lenguaje.CONTENTS).charAt(0));
				submenuContenidos.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.AYUDA)));
				submenuContenidos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuContenidosActionPerformed(evt);
					}
				});
			}
			{//Ayuda/acerca de
				submenuAcercaDe = new JMenuItem();
				menuAyuda.add(submenuAcercaDe);
				submenuAcercaDe.setText(Lenguaje.getMensaje(Lenguaje.ABOUT));
				submenuAcercaDe.setMnemonic(Lenguaje.getMensaje(Lenguaje.ABOUT).charAt(0));
				submenuAcercaDe.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.CREDITOS)));
				submenuAcercaDe.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						submenuAcercaDeActionPerformed(evt);
					}
				});
			}
		}
	}
	
	{
		salvado = new JRadioButton();
		//barraDeMenus.add(salvado);
		salvado.setSelected(true);
		salvado.setFocusable(false);
		salvado.setForeground(Color.GREEN);
		salvado.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(salvado.isSelected()==false){
					salvado.setSelected(true);
				}
			} 
		});
	}
	}
	
}
