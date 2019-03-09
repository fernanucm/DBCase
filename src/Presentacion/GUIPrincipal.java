package Presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import Controlador.Controlador;
import Controlador.TC;
import LogicaNegocio.Transfers.Transfer;
import LogicaNegocio.Transfers.TransferAtributo;
import LogicaNegocio.Transfers.TransferConexion;
import LogicaNegocio.Transfers.TransferDominio;
import LogicaNegocio.Transfers.TransferEntidad;
import LogicaNegocio.Transfers.TransferRelacion;
import Presentacion.GUIPanels.TablaVolumenes;
import Presentacion.GUIPanels.addTransfersPanel;
import Presentacion.GUIPanels.customTreeCellRenderer;
import Presentacion.GUIPanels.reportPanel;
import Presentacion.Grafo.PanelGrafo;
import Presentacion.Grafo.PanelThumbnail;
import Presentacion.Lenguajes.Lenguaje;
import Presentacion.Theme.Theme;
import Utilidades.AccionMenu;
import Utilidades.ApplicationLauncher;
import Utilidades.ImagePath;

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
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GUIPrincipal extends JFrame implements WindowListener, KeyListener{

	private static final long serialVersionUID = 1L;
	// Controlador de la aplicacion
	private Controlador controlador;
	// Variables lógicas
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
	private JButton botonLimpiarPantalla;
	private PanelThumbnail panelGrafo;
	private JTree arbol;
	private JTree arbolDom;
	private JScrollPane panelArbol;
	private JPanel panelInfo;
	private PanelGrafo panelDiseno;
	private JScrollPane panelArbolDom;
	private JPanel panelDom;
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
	private JComboBox cboSeleccionDBMS;
	private JMenuItem submenuImprimir;
	private JButton botonExportarArchivo;
	private JButton botonScriptSQL;
	private JButton botonModeloRelacional;
	private JButton botonValidar;
	private JButton botonEjecutarEnDBMS;
	private JSplitPane splitDisenoInfo;
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
	protected reportPanel codigoText;
	protected reportPanel modeloText;
	
	public GUIPrincipal(Theme theme){
		this.theme = theme;
	}

	/*
	 * Activar y desctivar la ventana
	 */

	public void setActiva(){
		controlador.mensajeDesde_GUIPrincipal(TC.GUIPrincipal_ObtenDBMSDisponibles, null);
		conexionActual = listaConexiones.get(0);
		mostrarPanelSucesos =false;
		
		setLookAndFeel();
		this.initComponents();
		changeFont(this,new java.awt.Font("Avenir", 0, 16));
		this.setVisible(true);

	}
	
	public void setInactiva(){
		this.setVisible(false);
	}
	
	public static void changeFont (Component component, Font font){
	    component.setFont(font);
	    if (component instanceof Container)
	        for(Component child : ((Container) component).getComponents())
	        	changeFont (child, font);
	}
	private void setLookAndFeel(){
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		UIManager.put("Button.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("ToggleButton.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("RadioButton.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("CheckBox.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("ColorChooser.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("ComboBox.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("Label.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("List.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("MenuBar.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("MenuItem.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("RadioButtonMenuItem.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("CheckBoxMenuItem.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("Menu.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("PopupMenu.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("OptionPane.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("Panel.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("ProgressBar.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("ScrollPane.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("Viewport.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("TabbedPane.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("Table.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("TableHeader.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("TextField.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("PasswordField.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("TextArea.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("TextPane.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("EditorPane.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("TitledBorder.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("ToolBar.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("ToolTip.font",  new java.awt.Font("Avenir", 0, 16));
		UIManager.put("Tree.font",  new java.awt.Font("Avenir", 0, 16));
		
		UIManager.put("nimbusBase", theme.main());
		UIManager.put("control", theme.control());
		UIManager.put("nimbusSelectionBackground", theme.SelectionBackground());
		UIManager.put("text", theme.fontColor());
		UIManager.put("nimbusSelectedText", theme.fontColor());
		UIManager.put("nimbusFocus", theme.SelectionBackground());
		
		UIManager.put("menu", theme.background());
		UIManager.put("menuText", theme.background());
		UIManager.put("nimbusBlueGrey", theme.background());
		UIManager.put("nimbusBorder", theme.background());
		UIManager.put("nimbusSelection", theme.SelectionBackground());
		
		UIManager.put("Tree.collapsedIcon", false);
		UIManager.put("Tree.expandedIcon", false);
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
            if ("Nimbus".equals(info.getName())) {
            	try { javax.swing.UIManager.setLookAndFeel(info.getClassName());} 
            	catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e) {e.printStackTrace();}
            break;
        }
	}
	

	private void initComponents() {
		try{
			this.setTitle(Lenguaje.getMensaje(Lenguaje.DBCASE));
			this.setSize(800, 600);
			this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			initMenu();
			initDiagrama();
			initCodes();
		}catch(Exception e) {e.printStackTrace();}
		popup = new JPopupMenu();
	}
	
	
	private void initMenu() {
		{
			barraDeMenus = new JMenuBar();
			setJMenuBar(barraDeMenus);
			barraDeMenus.add(Box.createRigidArea(new Dimension(0,30)));
			barraDeMenus.setOpaque(true);
			barraDeMenus.setBorder(BorderFactory.createCompoundBorder(
					null, 
					null));
			
			{	//File
				menuSistema = new JMenu();
				barraDeMenus.add(menuSistema);
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
				menuVista.setText(Lenguaje.getMensaje(Lenguaje.VIEW));
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
						if(s.equals(theme.getThemeName()))item.setSelected(true);
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
					themeMenu.setText(Lenguaje.getMensaje(Lenguaje.THEME));
					
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
	}//initMenu
	
	private void initDiagrama() {
		panelDiagrama = new JPanel();
		BorderLayout panelDiagramaLayout = new BorderLayout();
		panelDiagrama.setLayout(panelDiagramaLayout);
		
		splitDisenoInfo = new JSplitPane();
		panelDiagrama.add(splitDisenoInfo, BorderLayout.CENTER);
		splitDisenoInfo.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitDisenoInfo.setOneTouchExpandable(false);
		splitDisenoInfo.setResizeWeight(0.85);
		
		JTabbedPane tabPanelDcha = new JTabbedPane();
		JSplitPane splitTabMapa = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitTabMapa.setResizeWeight(0.155);
		splitTabMapa.add(tabPanelDcha, JSplitPane.RIGHT);
		tabPanelDcha.setFocusable(false);
		
		
	
		// Actualizacion de listas y creacion del grafo
		controlador.mensajeDesde_GUIPrincipal(TC.GUIPrincipal_ActualizameLaListaDeEntidades, null);
		controlador.mensajeDesde_GUIPrincipal(TC.GUIPrincipal_ActualizameLaListaDeAtributos, null);
		controlador.mensajeDesde_GUIPrincipal(TC.GUIPrincipal_ActualizameLaListaDeRelaciones, null);
		panelDiseno = new PanelGrafo(listaEntidades,listaAtributos,listaRelaciones);
		panelDiseno.setControlador(this.getControlador());
		//panelDiseno.setLayout(new BorderLayout());
		

		panelInfo = new JPanel();
		BorderLayout panelInfoLayout = new BorderLayout();
		panelInfo.setLayout(panelInfoLayout);
		panelInfo.addMouseListener(mls);
		tabPanelDcha.addTab(Lenguaje.getMensaje(Lenguaje.ELEMENTS), null, panelInfo ,null);
		
		panelArbol = new JScrollPane();
		panelArbol.setBackground(theme.background());
		panelInfo.add(panelArbol, BorderLayout.CENTER);
		panelArbol.setBorder(null);
		panelArbol.setVisible(false);

		panelDom = new JPanel();
		panelDom.setLayout(new BorderLayout());
		panelDom.setBackground(theme.background());
		tabPanelDcha.addTab(Lenguaje.getMensaje(Lenguaje.DOM_PANEL), null, panelDom ,null);
		
		panelArbolDom = new JScrollPane();
		panelArbolDom.setBackground(theme.background());
		panelDom.add(panelArbolDom, BorderLayout.CENTER);
		panelArbolDom.setBorder(null);
		panelArbolDom.addMouseListener(ml);
		panelArbolDom.setVisible(false);
		JButton nuevoDom = new JButton(Lenguaje.getMensaje(Lenguaje.ADD_DOMAIN));
		JPanel panelBoton = new JPanel();
		nuevoDom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_CrearDominio, 0);
			}
		});		
		nuevoDom.setFocusable(false);
		panelBoton.add(nuevoDom);
		panelDom.add(panelBoton, BorderLayout.NORTH);
		this.actualizaArbolDominio(null);
		
		panelTablas = new JPanel();
		panelTablas.setLayout(new BorderLayout());
		panelTablas.setBackground(theme.background());
		tablaVolumenes = new TablaVolumenes();
		tablaVolumenes.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_ActualizarDatosEnTablaDeVolumenes, e);
		}});
		tablaVolumenes.setBackground(theme.background());
		scrollPanelTablas = new JScrollPane(tablaVolumenes);
		scrollPanelTablas.setBackground(theme.background());
		panelTablas.add(scrollPanelTablas);
		tabPanelDcha.addTab(Lenguaje.getMensaje(Lenguaje.TABLES_SECTION), null, panelTablas ,null);
		panelGrafo = new PanelThumbnail(panelDiseno);
		splitTabMapa.add(panelGrafo, JSplitPane.LEFT);
		JPanel diagrama = new JPanel();
		diagrama.setLayout(new BorderLayout());
		diagrama.add(panelDiseno);
		
		JPanel tituloDiseno = new JPanel();
		tituloDiseno.setLayout(new BorderLayout());
		JLabel title = new JLabel("<html><span style='font-size:20px'>"+Lenguaje.getMensaje(Lenguaje.CONC_MODEL)+"</span></html>");
		title.setHorizontalAlignment(JTextField.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		tituloDiseno.add(title, BorderLayout.CENTER);
		JPanel botonesAnadir = new addTransfersPanel(controlador);
		
		splitDisenoInfo.setBorder(null);
		diagrama.add(botonesAnadir, BorderLayout.WEST);
		diagrama.add(tituloDiseno, BorderLayout.NORTH);
		splitDisenoInfo.add(diagrama, JSplitPane.LEFT);
		splitDisenoInfo.add(splitTabMapa,JSplitPane.RIGHT);
		
		
	}
	
	
	private void initCodes() {
		JSplitPane diagramaCode = new JSplitPane();
		diagramaCode.setResizeWeight(0.8);
		getContentPane().add(diagramaCode);
		modeloText = new reportPanel(theme);
		diagramaCode.add(panelDiagrama, JSplitPane.LEFT);
		panelGeneracion = new JPanel();
		BorderLayout panelGeneracionLayout = new BorderLayout();
		panelGeneracion.setLayout(panelGeneracionLayout);
		diagramaCode.add(panelGeneracion, JSplitPane.RIGHT);
		JSplitPane codesSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		codesSplit.setResizeWeight(0.5);
		codesSplit.setDividerSize(10);
		codesSplit.setBorder(null);
		codesSplit.setEnabled(true);
		codesSplit.setBackground(Color.black);
		panelGeneracion.add(codesSplit, BorderLayout.CENTER);
		JPanel modeloPanel = new JPanel();
		modeloPanel.setBackground(theme.background());
		modeloPanel.setLayout(new BorderLayout());
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.X_AXIS));
		JLabel text = new JLabel("<html><span style='font-size:20px'>"+Lenguaje.getMensaje(Lenguaje.LOGIC_MODEL)+"</span></html>");
		JButton generaModelo = new JButton(Lenguaje.getMensaje(Lenguaje.GENERATE));
		generaModelo.setToolTipText("Genera el modelo relacional a partir del diagrama entidad relacion.");
		generaModelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				acumulador = "";
				botonModeloRelacionalActionPerformed(evt);
			}
		});
		JButton exportarModelo = new JButton(Lenguaje.getMensaje(Lenguaje.SAVE_AS));
		exportarModelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				botonExportarArchivoActionPerformed(evt,false);
			}
		});
		textPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		textPanel.add(text);
		textPanel.add(generaModelo);
		textPanel.add(exportarModelo);
		modeloPanel.add(textPanel, BorderLayout.NORTH);

		
		
		modeloPanel.add(modeloText.getPanel(), BorderLayout.CENTER);
		/***********************/
		
		JPanel codePanel = new JPanel();
		codePanel.setBackground(theme.background());
		codePanel.setLayout(new BorderLayout());
		
		cboSeleccionDBMS = new JComboBox();
		for (int i=0; i < listaConexiones.size(); i++)
			cboSeleccionDBMS.insertItemAt(listaConexiones.get(i).getRuta(),
					listaConexiones.get(i).getTipoConexion());
		
		cboSeleccionDBMS.setSelectedIndex(0);
		cboSeleccionDBMS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JComboBox cbo = (JComboBox) e.getSource();
				cambiarConexion((String)cbo.getSelectedItem());// Cambiar la conexionActual
			}
		});
		cboSeleccionDBMS.setMaximumSize(new Dimension(500,40));
		
		JPanel textPanel2 = new JPanel();
		textPanel2.setLayout(new BoxLayout(textPanel2,BoxLayout.X_AXIS));
		JLabel text2 = new JLabel("<html><span style='font-size:20px'>"+Lenguaje.getMensaje(Lenguaje.PHYS_MODEL)+"</span></html>");
		JButton generaCodigo = new JButton(Lenguaje.getMensaje(Lenguaje.GENERATE));
		generaCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				acumulador = "";
				botonScriptSQLActionPerformed(evt);
			}
		});
		JButton exportarCodigo = new JButton(Lenguaje.getMensaje(Lenguaje.SAVE_AS));
		exportarCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				botonExportarArchivoActionPerformed(evt,true);
			}
		});
		JButton ejecutarCodigo = new JButton(Lenguaje.getMensaje(Lenguaje.EXECUTE));
		ejecutarCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				botonEjecutarEnDBMSActionPerformed(evt);
			}
		});
		JPanel accionesCodigo = new JPanel();
		accionesCodigo.setLayout(new BoxLayout(accionesCodigo,BoxLayout.Y_AXIS));
		textPanel2.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		textPanel2.add(text2);
		accionesCodigo.add(cboSeleccionDBMS);
		JPanel botonesCodigo = new JPanel();
		botonesCodigo.setLayout(new BoxLayout(botonesCodigo,BoxLayout.X_AXIS));
		accionesCodigo.add(botonesCodigo);
		botonesCodigo.add(generaCodigo);
		botonesCodigo.add(exportarCodigo);
		botonesCodigo.add(ejecutarCodigo);
		textPanel2.add(accionesCodigo);
		codePanel.add(textPanel2, BorderLayout.NORTH);
		
		codesSplit.add(modeloPanel,JSplitPane.TOP);
		codesSplit.add(codePanel,JSplitPane.BOTTOM);
				
		codigoText = new reportPanel(theme);

		codePanel.add(codigoText.getPanel(), BorderLayout.CENTER);
		
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		this.addWindowListener(this);
        this.addKeyListener(this);
	}
	
	/*
	 * Oyentes de la barra de menus
	 */
	
	private void cambiaTema(ActionEvent evt) {
		controlador.mensajeDesde_GUIPrincipal(
				TC.GUI_Principal_CambiarTema, evt.getActionCommand());
		int i = 0;
		for(String s : this.theme.getAvaiableThemes()) {
			if(theme.getThemeName().equals(s))themeMenu.getItem(i).setSelected(true);
			else themeMenu.getItem(i).setSelected(false);
			i++;
		}
	}
	
	private void submenuNuevoActionPerformed(ActionEvent evt) {
		this.controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_Submenu_Nuevo, null);
		//this.panelPrincipal.setVisible(true);
	}
	
	private void submenuCerrarActionPerformed(ActionEvent evt) {
		this.controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_Submenu_Cerrar, null);
	}
	
	private void submenuGuardarComoActionPerformed(ActionEvent evt) {
		this.controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_Submenu_GuardarComo, null);
	}
	
	private void submenuAbrirActionPerformed(ActionEvent evt) {
		this.controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_Submenu_Abrir, null);
	}
	
	private void submenuGuardarActionPerformed(ActionEvent evt) {
		this.controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_Submenu_Guardar, null);
	}

	private void submenuSalirActionPerformed(ActionEvent evt) {
		this.controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_Submenu_Salir, null);
	}
	
	private void menuPanelSucesosActionPerformed(ActionEvent evt) {
		mostrarPanelSucesos= !mostrarPanelSucesos;
		if(!mostrarPanelSucesos){
			panelSucesos.setVisible(false);
			splitDisenoInfo.setOneTouchExpandable(false);
		}else{
			panelSucesos.setVisible(true);
			splitDisenoInfo.setOneTouchExpandable(true);
			splitDisenoInfo.setResizeWeight(0.8);
			splitDisenoInfo.resetToPreferredSizes();
		}
		//this.panelSucesos.setVisible(!panelSucesos.isVisible());
		//this.menuPanelSucesos.setSelected().setSelected(!menuPanelSucesos.isSelected());
	}
	

	/*
	 * Oyentes de la toolbar de generacion
	 */

	private void botonModeloRelacionalActionPerformed(ActionEvent evt) {
		Thread hilo = new Thread(new Runnable(){
			public void run() {
				controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_BotonGenerarModeloRelacional, null);
				modeloText.goToTop();
			}
		});
		hilo.start();
	}

	private void botonScriptSQLActionPerformed(ActionEvent evt) {
		Thread hilo = new Thread(new Runnable(){
			public void run() {
				conexionActual.setDatabase("");
				controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_BotonGenerarScriptSQL, conexionActual);
				
				// Restaurar el sistema
				conexionActual.setDatabase("");
				codigoText.goToTop();
			}
		});
		hilo.start();
	}
	/*
	 * boolean texto:
	 * false: panel de modelo
	 * true: panel de codigo
	 * */
	private void botonExportarArchivoActionPerformed(ActionEvent evt, boolean texto) {
		Thread hilo = new Thread(new Runnable(){
			public void run() {
				controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_BotonGenerarArchivoScriptSQL, texto);
			}
		});
		hilo.start();
	}

	private void botonEjecutarEnDBMSActionPerformed(ActionEvent evt) {
		Thread hilo = new Thread(new Runnable(){
			public void run() {
				// Comprobar si hay codigo
				if (!scriptGeneradoCorrectamente){
					JOptionPane.showMessageDialog(null,
							Lenguaje.getMensaje(Lenguaje.ERROR)+".\n" +
							Lenguaje.getMensaje(Lenguaje.MUST_GENERATE_SCRIPT_EX),
							Lenguaje.getMensaje(Lenguaje.DBCASE),
							JOptionPane.PLAIN_MESSAGE,
							new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
					return;
				}
				
				// Ejecutar en DBMS
				TransferConexion tc = new TransferConexion(
						cboSeleccionDBMS.getSelectedIndex(),
						cboSeleccionDBMS.getSelectedItem().toString());
				
				controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_BotonEjecutarEnDBMS, tc);
			}
		});
		hilo.start();
	}
	
	/*
	 * OYENTES DE TECLADO
	 * */
	public void keyPressed( KeyEvent e ) {
		System.out.println("tecla");
	} 
	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}
	
	/*
	 * Mensajes que le manda el controlador a la GUIPrincipal
	 */
	public void mensajesDesde_Controlador(TC mensaje, Object datos){
		switch(mensaje){
		case Controlador_InsertarEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.anadirNodo(te);
			break;
		}
		case Controlador_RenombrarEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_DebilitarEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_EliminarEntidad:{
			TransferEntidad te = (TransferEntidad) ((Vector)datos).get(0);
			Vector<TransferRelacion> vectorRelacionesModificadas = (Vector<TransferRelacion>) ((Vector)datos).get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha eliminado la entidad "+te.getNombre()+".");
			panelDiseno.eliminaNodo(te);
			int cont = 0;
			while (cont < vectorRelacionesModificadas.size()){
				TransferRelacion tr = vectorRelacionesModificadas.get(cont);
				System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modificado la relacion "+tr.getNombre()+".");
				panelDiseno.ModificaValorInterno(tr);
				cont++;
			}
			break;
		}
		case Controlador_AnadirRestriccionEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_QuitarRestriccionEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_setRestriccionesEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_AnadirRestriccionRelacion:{
			TransferRelacion te = (TransferRelacion) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_QuitarRestriccionRelacion:{
			TransferRelacion te = (TransferRelacion) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_setRestriccionesRelacion:{
			TransferRelacion tr = (TransferRelacion) datos;
			panelDiseno.ModificaValorInterno(tr);
			break;	
		}
		case Controlador_AnadirRestriccionAtributo:{
			TransferAtributo te = (TransferAtributo) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_QuitarRestriccionAtributo:{
			TransferAtributo te = (TransferAtributo) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_setRestriccionesAtributo:{
			TransferAtributo te = (TransferAtributo) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_AnadirUniqueEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_QuitarUniqueEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_setUniquesEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_setUniqueUnitarioEntidad:{
			TransferEntidad te = (TransferEntidad) datos;
			panelDiseno.ModificaValorInterno(te);
			break;	
		}
		case Controlador_AnadirUniqueRelacion:{
			TransferRelacion tr = (TransferRelacion) datos;
			panelDiseno.ModificaValorInterno(tr);
			break;	
		}
		case Controlador_QuitarUniqueRelacion:{
			TransferRelacion tr = (TransferRelacion) datos;
			panelDiseno.ModificaValorInterno(tr);
			break;	
		}
		case Controlador_setUniquesRelacion:{
			TransferRelacion tr = (TransferRelacion) datos;
			panelDiseno.ModificaValorInterno(tr);
			break;	
		}
		case Controlador_setUniqueUnitarioRelacion:{
			TransferRelacion tr = (TransferRelacion) datos;
			panelDiseno.ModificaValorInterno(tr);
			break;	
		}
		case Controlador_EliminarAtributo:{
			Vector<Transfer> vectorAtributoYElemMod = (Vector<Transfer>)datos;
			TransferAtributo ta = (TransferAtributo) vectorAtributoYElemMod.get(0);
			Transfer t_elemMod = (Transfer) vectorAtributoYElemMod.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha eliminado el atributo "+ta.getNombre()+
					" y que se ha modificado el elemento "+t_elemMod.toString());
			panelDiseno.eliminaNodo(ta);
			panelDiseno.ModificaValorInterno(t_elemMod);
			break;
		}
		case Controlador_AnadirAtributoAEntidad:{
			Vector<Transfer> vt = (Vector<Transfer>) datos;
			TransferEntidad te = (TransferEntidad) vt.get(0);
			TransferAtributo ta = (TransferAtributo) vt.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha añadido el atributo "+ta.getNombre()+".");
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modificado la entidad "+te.getNombre()+
					" añadiendole la referencia al atributo "+ta.getNombre()+".");

			panelDiseno.anadirNodo(ta);
			panelDiseno.ModificaValorInterno(te);
			break;
		}
		case Controlador_RenombrarAtributo:{
			TransferAtributo ta = (TransferAtributo) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha renombrado el atributo "+ta.getNombre()+".");
			panelDiseno.ModificaValorInterno(ta);
			break;
		}
		case Controlador_EditarDominioAtributo:{
			TransferAtributo ta = (TransferAtributo) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha editado el dominio del atributo "+ta.getNombre()+"." +
					" Ahora es "+ ta.getDominio());

			panelDiseno.ModificaValorInterno(ta);
			break;	
		}
		case Controlador_EditarCompuestoAtributo:{
			TransferAtributo ta = (TransferAtributo) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha editado el caracter compuesto del atributo "+ta.getNombre()+"." +
					" Ahora es "+ ta.getCompuesto());
			panelDiseno.ModificaValorInterno(ta);
			break;
		}
		case Controlador_EditarMultivaloradoAtributo:{
			TransferAtributo ta = (TransferAtributo) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha editado el caracter multivalorado del atributo "+ta.getNombre()+"." +
					" Ahora es "+ ta.isMultivalorado());
			panelDiseno.ModificaValorInterno(ta);
			break;
		}
		case Controlador_EditarNotNullAtributo:{
			TransferAtributo ta = (TransferAtributo) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha editado el caracter not null del atributo "+ta.getNombre()+"." +
					" Ahora es "+ ta.getNotnull());
			panelDiseno.ModificaValorInterno(ta);
			break;
		}
		case Controlador_EditarUniqueAtributo:{
			TransferAtributo ta = (TransferAtributo) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha editado el caracter unique del atributo "+ta.getNombre()+"." +
					" Ahora es "+ ta.getUnique());
			panelDiseno.ModificaValorInterno(ta);
			break;
		}

		case Controlador_AnadirSubAtributoAAtributo:{
			Vector<Transfer> vt = (Vector<Transfer>) datos;
			TransferAtributo ta_padre = (TransferAtributo) vt.get(0);
			TransferAtributo ta_hijo = (TransferAtributo) vt.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha añadido el atributo "+ta_hijo.getNombre()+".");
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modificado el atributo "+ta_padre.getNombre()+
					" añadiendole la referencia al atributo "+ta_hijo.getNombre()+".");
			panelDiseno.anadirNodo(ta_hijo);
			panelDiseno.ModificaValorInterno(ta_padre);
			break;	
		}

		case Controlador_InsertarRelacion:{
			TransferRelacion tr = (TransferRelacion) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha añadido la nueva relacion "+tr.getNombre()+".");
			panelDiseno.anadirNodo(tr);
			break;
		}

		case Controlador_MoverEntidad_HECHO:{
			TransferEntidad te = (TransferEntidad) datos;

			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha movido la entidad "+ te.getNombre() +".");
			panelDiseno.ModificaValorInterno(te);
			break;
		}
		case Controlador_MoverAtributo_HECHO:{
			TransferAtributo ta = (TransferAtributo) datos;

			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha movido el atributo "+ ta.getNombre() +".");
			panelDiseno.ModificaValorInterno(ta);
			break;
		}
		case Controlador_MoverRelacion_HECHO:{
			TransferRelacion tr = (TransferRelacion) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha movido la relacion "+ tr.getNombre() +".");
			panelDiseno.ModificaValorInterno(tr);
			break;
		}
		case Controlador_RenombrarRelacion:{
			TransferRelacion tr = (TransferRelacion) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha renombrado la relacion "+ tr.getNombre() +".");
			panelDiseno.ModificaValorInterno(tr);
			break;	
		}
		case Controlador_DebilitarRelacion:{
			TransferRelacion tr = (TransferRelacion) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha debilitado/fortalecido la relacion "+ tr.getNombre() +".");
			panelDiseno.ModificaValorInterno(tr);
			break;
		}
		case Controlador_EliminarRelacion:{
			TransferRelacion tr = (TransferRelacion) datos;
			panelDiseno.ModificaValorInterno(tr);
			break;	
		}
		case Controlador_EditarClavePrimariaAtributo:{
			Vector<Transfer> vt = (Vector<Transfer>) datos;
			TransferAtributo ta = (TransferAtributo) vt.get(0);
			TransferEntidad te = (TransferEntidad) vt.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modidicado el atributo "+ta.getNombre()+".");
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modificado la entidad "+te.getNombre()+
					" añadiendo o quitando el atributo "+ta.getNombre()+" de su lista de claves primarias.");
			panelDiseno.ModificaValorInterno(ta);
			panelDiseno.ModificaValorInterno(te);
			break;
		}
		case Controlador_EstablecerEntidadPadre:{
			Vector<Transfer> vt = (Vector<Transfer>) datos;
			TransferRelacion tr = (TransferRelacion) vt.get(0);
			TransferEntidad te = (TransferEntidad) vt.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modidicado el la relacion IsA con id="+tr.getIdRelacion()+"" +
					" estableciendole como entidad padre la entidad \""+te.getNombre()+"\".");
			panelDiseno.ModificaValorInterno(tr);
			break;
		}
		case Controlador_QuitarEntidadPadre:{
			TransferRelacion tr = (TransferRelacion) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modidicado el la relacion IsA con id="+tr.getIdRelacion()+"" +
			" quitandole la entidad padre y las posibles entidades hijas que tuviera");
			panelDiseno.ModificaValorInterno(tr);
			break;
		}
		case Controlador_AnadirEntidadHija:{
			Vector<Transfer> vt = (Vector<Transfer>) datos;
			TransferRelacion tr = (TransferRelacion) vt.get(0);
			TransferEntidad te = (TransferEntidad) vt.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modidicado el la relacion IsA con id="+tr.getIdRelacion()+"" +
					" estableciendole como nueva entidad hija la entidad \""+te.getNombre()+"\".");
			panelDiseno.ModificaValorInterno(tr);
			break;
		}
		case Controlador_QuitarEntidadHija:{
			Vector<Transfer> vt = (Vector<Transfer>) datos;
			TransferRelacion tr = (TransferRelacion) vt.get(0);
			TransferEntidad te = (TransferEntidad) vt.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha quitado de la relacion IsA con id="+tr.getIdRelacion()+"" +
					" la entidad hija \""+te.getNombre()+"\".");
			panelDiseno.ModificaValorInterno(tr);
			break;
		}
		case Controlador_EliminarRelacionIsA:{
			TransferRelacion tr = (TransferRelacion) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha eliminado la relacion IsA con id="+tr.getIdRelacion()+".");
			panelDiseno.eliminaNodo(tr);
			break;
		}
		case Controlador_EliminarRelacionNormal:{
			TransferRelacion tr = (TransferRelacion) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha eliminado la relacion "+tr.getNombre()+".");
			panelDiseno.eliminaNodo(tr);
			break;
		}
		case Controlador_InsertarRelacionIsA:{
			TransferRelacion tr = (TransferRelacion) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha insertado una relacion IsA con id="+tr.getIdRelacion()+".");
			panelDiseno.anadirNodo(tr);
			break;
		}
		case Controlador_AnadirEntidadARelacion:{
			Vector v = (Vector) datos;
			TransferRelacion tr = (TransferRelacion) v.get(0);
			TransferEntidad te = (TransferEntidad) v.get(1);
			String inicio = String.valueOf(v.get(2));
			String fin = String.valueOf(v.get(3));
			String rol = String.valueOf(v.get(4));
			if (rol.equals(""))
				System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha añadido a la relacion \""+tr.getNombre()+"\" la entidad \"" 
						+ te.getNombre()+"\" con una aridad de "+inicio+" a "+fin+"\".");
				
			else
				System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha añadido a la relacion \""+tr.getNombre()+"\" la entidad \"" 
						+ te.getNombre()+"\" con una aridad de "+inicio+" a "+fin+" cuyo rol es \""+rol+"\".");
			
			panelDiseno.ModificaValorInterno(tr);
			break;
		}
		case Controlador_QuitarEntidadARelacion:{
			Vector v = (Vector) datos;
			TransferRelacion tr = (TransferRelacion) v.get(0);
			TransferEntidad te = (TransferEntidad) v.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modificado la relacion \""+tr.getNombre()+"\" quitandole" +
					" la entidad \"" + te.getNombre()+"\".");
			panelDiseno.ModificaValorInterno(tr);
			break; 
		}
		case Controlador_EditarCardinalidadEntidad:{
			Vector v = (Vector) datos;
			TransferRelacion tr = (TransferRelacion) v.get(0);
			TransferEntidad te = (TransferEntidad) v.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modificado la relacion \""+tr.getNombre()+"\" editando la aridad " +
					" de la entidad \"" + te.getNombre()+"\".");
			panelDiseno.ModificaValorInterno(tr);
			break;
		}
		case Controlador_CardinalidadUnoUno:{
			Vector v = (Vector) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha fijado la aridad de la entidad a 1 1\".");
			panelDiseno.ModificaValorInterno1a1(v);
			break;
		}
		
		case Controlador_AnadirAtributoARelacion:{
			Vector<Transfer> v = (Vector<Transfer>) datos;
			TransferRelacion tr = (TransferRelacion) v.get(0);
			TransferAtributo ta = (TransferAtributo) v.get(1);
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha añadido el atributo "+ta.getNombre()+".");
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modificado la relacion "+tr.getNombre()+
					" añadiendole la referencia al atributo "+ta.getNombre()+".");
			panelDiseno.anadirNodo(ta);
			panelDiseno.ModificaValorInterno(tr);
			break;
		}
		case Controlador_LimpiarPanelInformacion:{
			if (this.arbol != null){
				//this.panelArbol.setVisible(false);
			}
			break;
		}
		case Controlador_MostrarDatosEnPanelDeInformacion:{
			this.panelArbol.setVisible(true);
			this.arbol = (JTree) datos;
			this.arbol.addMouseListener(mls);
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		    renderer.setOpenIcon(null);
		    renderer.setClosedIcon(null);
		    renderer.setLeafIcon(null);
		    arbol.setCellRenderer(new customTreeCellRenderer());
			this.arbol.setFont(new java.awt.Font("Avenir", 0, 15));
			this.arbol.setBackground(theme.background());
			this.panelArbol.setViewportView(arbol);
			this.repaint();
			break;
		}
		case Controlador_MostrarDatosEnTablaDeVolumenes:{
			this.tablaVolumenes.refresh((String[][]) datos);
			break;
		}
		case Controlador_ActualizarDatosEnTablaDeVolumenes:{
			this.panelDiseno.refreshTables((TableModelEvent) datos);
			break;
		}
		case Controlador_LimpiarPanelDominio:{
			if (this.arbolDom != null){
				this.panelArbolDom.setVisible(false);
			}
			break;
		}
		case Controlador_MostrarDatosEnPanelDominio:{
			this.panelArbolDom.setVisible(true);
			this.arbolDom = (JTree) datos;
			this.arbolDom.setFont(new java.awt.Font("Avenir", 0, 15));
			this.arbolDom.setBackground(theme.background());
			this.panelArbolDom.setViewportView(arbolDom);
			this.repaint();
			break;
		}
		case Controlador_InsertarDominio:{
			TransferDominio td = (TransferDominio) datos;
			String nombre = td.getNombre();
			this.actualizaArbolDominio(nombre);
			break;
		}
		case Controlador_EliminarDominio:{
			TransferDominio td = (TransferDominio) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha eliminado el dominio "+td.getNombre()+".");
			break;
		}
		case Controlador_RenombrarDominio:{
			TransferDominio td = (TransferDominio) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha renombrado el dominio "+ td.getNombre() +".");
			break;	
		}
		case Controlador_ModificarTipoBaseDominio:{
			TransferDominio td = (TransferDominio) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modificado el dominio "+ td.getNombre() +".");
			break;	
		}
		case Controlador_ModificarValoresDominio:{
			TransferDominio td = (TransferDominio) datos;
			System.out.println("GUIPrincipal dice: El controlador me ha dicho que se ha modificado el dominio "+ td.getNombre() +".");
			break;	
		}
		default:
			break;
		
		} // mensajesDesde_Controlador	
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
	
	public void setScriptGeneradoCorrectamente(boolean valor){
		scriptGeneradoCorrectamente = valor;
	}

	public Vector getListaConexiones() {
		return listaConexiones;
	}
	
	public void setListaConexiones(Vector<TransferConexion> listaConexiones) {
		this.listaConexiones = listaConexiones;
	}

	public Vector getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(Vector<TransferEntidad> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}
	
	public Vector getListaAtributos() {
		return listaAtributos;
	}

	public void setListaAtributos(Vector<TransferAtributo> listaAtributos) {
		this.listaAtributos = listaAtributos;
	}

	public Vector getListaRelaciones() {
		return listaRelaciones;
	}

	public void setListaRelaciones(Vector<TransferRelacion> listaRelaciones) {
		this.listaRelaciones = listaRelaciones;
	}

	public Vector getListaDominios() {
		return listaDominios;
	}

	public TransferConexion getConexionActual(){
		return conexionActual;
	}
	
	public void setListaDominios(Vector<TransferDominio> listaDominios) {
		this.listaDominios = listaDominios;
	}
	
	public void escribeEnModelo(String mensaje){
		acumulador+=mensaje;
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run() {
					modeloText.setText(acumulador);
				}});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	public void escribeEnCodigo(String mensaje){
		acumulador+=mensaje;
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
			public void run() {codigoText.setText(acumulador);}
		});} 
		catch (InterruptedException e) {e.printStackTrace();} 
		catch (InvocationTargetException e) {e.printStackTrace();}
		
	}
	
	/*
	 * ARBOL DOMINIOS
	 */
	
	public void actualizaArbolDominio(String expandir){
		controlador.mensajeDesde_GUIPrincipal(TC.GUIPrincipal_ActualizameLaListaDeDominios, null);
		this.panelArbolDom.setVisible(true);
		this.arbolDom = generaArbolDominio(this.listaDominios, expandir);
		this.panelArbolDom.setViewportView(arbolDom);
		this.repaint();
	}
	
	private JTree generaArbolDominio(Vector<TransferDominio> listaDominios, String expandir){
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.DOM_TREE_CREATED_DOMS));
		for (Iterator<TransferDominio> it = listaDominios.iterator(); it.hasNext();){
			TransferDominio td = it.next();
			//Nombre
			DefaultMutableTreeNode nodoNombre = new DefaultMutableTreeNode(td.getNombre());
			root.add(nodoNombre);
			//TipoBase
			nodoNombre.add(new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.DOM_TREE_TYPE)+" \""+td.getTipoBase()+"\""));
			// Valores
			if (td.getListaValores()!=null && td.getListaValores().size()>0){
				DefaultMutableTreeNode nodo_valores = new DefaultMutableTreeNode(Lenguaje.getMensaje(Lenguaje.DOM_TREE_VALUES));
				nodoNombre.add(nodo_valores);
				Vector lista = td.getListaValores();
				for (int cont=0; cont<lista.size(); cont++ )
					nodo_valores.add(new DefaultMutableTreeNode(lista.get(cont)));

			}
		}
		
		JTree arbolDom = new JTree(root);
		arbolDom.setRootVisible(false);
		arbolDom.setShowsRootHandles(true);
		arbolDom.setToggleClickCount(1);
		arbolDom.addMouseListener(ml);
		arbolDom.setBackground(theme.background());
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	    renderer.setOpenIcon(null);
	    renderer.setClosedIcon(null);
	    renderer.setLeafIcon(null);
	    arbolDom.setCellRenderer(renderer);
		// Expandimos todas las ramas
		 for(int cont=0; cont<arbolDom.getRowCount(); cont++){
			 try{ 
				 if (arbolDom.getPathForRow(cont).getLastPathComponent().toString().contains(Lenguaje.getMensaje(Lenguaje.DOMAIN)+" "+'"'+expandir+'"')){ 
					 arbolDom.expandRow(cont);
					 }
				 else if(arbolDom.getPathForRow(cont).getParentPath().getLastPathComponent().toString().contains(Lenguaje.getMensaje(Lenguaje.DOMAIN)+" "+'"'+expandir+'"')){
					 arbolDom.expandRow(cont);
				 }
			 }catch(Exception e){
				 //expandir== null o getParentPath == null
			 }
		 }
		return arbolDom;
	}
	
	/*
	 * LISTENER DEL ARBOL DOMINIOS
	 * */
	MouseListener ml = new MouseAdapter() {
	     @Override
		public void mousePressed(MouseEvent e) {
	         int selRow = arbolDom.getRowForLocation(e.getX(), e.getY());
	         TreePath selPath = arbolDom.getPathForLocation(e.getX(), e.getY());
	         if(selRow != -1) {
	        	 if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
	        	     muestraMenu(e, selPath);
	             }
	        	 else{
		        	 getPopUp().setVisible(false);
		         }
	         }
	         else{
	        	 if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
	        		 popup.removeAll();
		 			 GUIPrincipal p = getThePrincipal();
		        	 popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_ADD),p,null));
		        	 popup.setLocation(e.getLocationOnScreen());
		        	 getPopUp().setVisible(true);
	             }
	        	 else{
		        	 getPopUp().setVisible(false);
		         }
	        	 
	         }
	         
	         
	     }		
		 private void muestraMenu(MouseEvent e, TreePath selPath) {
			popup.removeAll();
			GUIPrincipal p = getThePrincipal();
			if(selPath.getPathCount()==2){//Nodo principal de un dominio
				//buscamos el transfer
				String nombre= selPath.getLastPathComponent().toString();
				nombre= nombre.replace('"', '*');
				nombre= nombre.replaceAll(Lenguaje.getMensaje(Lenguaje.DOMAIN)+" ", "");
				nombre= nombre.replace("*", "");
				
				int index=-1;
				for (int i=0; i<listaDominios.size();i++){
					if(listaDominios.get(i).getNombre().equals(nombre)){
					 index=i;	
					}
					
				}
				TransferDominio dominio = listaDominios.get(index);
				
				popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_RENAME),p, dominio));
				popup.addSeparator();
				popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_DELETE),p,dominio));
				popup.addSeparator();
				popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_MODIFY),p,dominio));
				
			}
			else if(selPath.getPathCount()==1){//Nodo "Dominios"
				popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_ADD),p,null));
			}
			else if(selPath.getLastPathComponent().toString().startsWith(Lenguaje.getMensaje(Lenguaje.DOM_TREE_TYPE)+" ") && (selPath.getPathCount()==3)){
				//buscamos el transfer
				String nombre= selPath.getParentPath().getLastPathComponent().toString();
				nombre= nombre.replace('"', '*');
				nombre= nombre.replaceAll(Lenguaje.getMensaje(Lenguaje.DOMAIN)+" ", "");
				nombre= nombre.replace("*", "");
				
				int index=-1;
				for (int i=0; i<listaDominios.size();i++){
					if(listaDominios.get(i).getNombre().equals(nombre)){
					 index=i;	
					}
					
				}
				TransferDominio dominio = listaDominios.get(index);
				popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_MODIFY),p,dominio));
			}
			else if(selPath.getLastPathComponent().toString().equals(Lenguaje.getMensaje(Lenguaje.DOM_TREE_TYPE)) && (selPath.getPathCount()==3)){
				//buscamos el transfer
				String nombre= selPath.getParentPath().getLastPathComponent().toString();
				nombre= nombre.replace('"', '*');
				nombre= nombre.replaceAll(Lenguaje.getMensaje(Lenguaje.DOMAIN)+" ", "");
				nombre= nombre.replace("*", "");
				
				int index=-1;
				for (int i=0; i<listaDominios.size();i++){
					if(listaDominios.get(i).getNombre().equals(nombre)){
					 index=i;	
					}
					
				}
				TransferDominio dominio = listaDominios.get(index);
				popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_MODIFY),p,dominio));
				popup.addSeparator();
				popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_IN_ORDER),p,dominio));
			}
			else if(selPath.getParentPath().getLastPathComponent().toString().equals(Lenguaje.getMensaje(Lenguaje.DOM_TREE_VALUES)) && (selPath.getPathCount()==4)){
				//buscamos el transfer
				String nombre= selPath.getParentPath().getParentPath().getLastPathComponent().toString();
				nombre= nombre.replace('"', '*');
				nombre= nombre.replaceAll(Lenguaje.getMensaje(Lenguaje.DOMAIN)+" ", "");
				nombre= nombre.replace("*", "");
				
				int index=-1;
				for (int i=0; i<listaDominios.size();i++){
					if(listaDominios.get(i).getNombre().equals(nombre)){
					 index=i;	
					}
					
				}
				TransferDominio dominio = listaDominios.get(index);
				popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_MODIFY),p,dominio));
				popup.addSeparator();
				popup.add(new AccionMenu(Lenguaje.getMensaje(Lenguaje.DOM_MENU_IN_ORDER),p,dominio));
			}
						
			popup.setLocation(e.getLocationOnScreen());
			popup.setVisible(true);
		}
		 
	 };
	 
	 
	/*
	* LISTENER DEL ARBOL INFORMACION
	* */
	MouseListener mls = new MouseAdapter() {
	     @Override
		public void mousePressed(MouseEvent e){
	         int selRow = arbol.getRowForLocation(e.getX(), e.getY());
	         TreePath selPath = arbol.getPathForLocation(e.getX(), e.getY());
	         if(selRow != -1) {
	        	 if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
	        	     muestraMenu(e, selPath);
	             }
	        	 else{
		        	 getPopUp().setVisible(false);
		         }
	         }
	         else{
	        	 getPopUp().setVisible(false);
	         }
	         
	         
	     }		
		private void muestraMenu(MouseEvent e, TreePath selPath) {
			popup.removeAll();
			controlador.mensajeDesde_GUIPrincipal(TC.GUIPrincipal_ActualizameLaListaDeEntidades, null);
			controlador.mensajeDesde_GUIPrincipal(TC.GUIPrincipal_ActualizameLaListaDeAtributos, null);
			controlador.mensajeDesde_GUIPrincipal(TC.GUIPrincipal_ActualizameLaListaDeRelaciones, null);
			
			//if(selPath.getPathCount()==1){//Nodo The entity, the attribute, the relation...
				String nombre= selPath.getPathComponent(0).toString();
				if(nombre.contains(Lenguaje.getMensaje(Lenguaje.ENTITY))){
					//buscamos el transfer
					nombre= nombre.replace('"', '*');
					nombre= nombre.replaceAll(Lenguaje.getMensaje(Lenguaje.THE_ENTITY)+" ", "");
					nombre= nombre.replace("*", "");
					
					int index=-1;
					
					for (int i=0; i<listaEntidades.size();i++){
						if(listaEntidades.get(i).getNombre().equals(nombre)){
						 index=i;	
						}
						
					}
					final TransferEntidad entidad = listaEntidades.get(index);
					
					// Anadir un atributo a una entidad
					JMenuItem j3 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_ATTRIBUTE));
					j3.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Añadir un nuevo atributo a la entidad: " + entidad);
							TransferEntidad clon_entidad = entidad.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirAtributoEntidad,clon_entidad);
						}	
					});
					popup.add(j3);
					popup.add(new JSeparator());
					// Renombrar la entidad
					JMenuItem j1 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RENAME_ENTITY));
					j1.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Cambiar nombre a entidad: " + entidad);
							TransferEntidad clon_entidad = entidad.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_RenombrarEntidad,clon_entidad);
							
						}	
					});
					popup.add(j1);
					// Eliminar una entidad 
					JMenuItem j4 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE_ENT));
					j4.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Eliminar la entidad: " + entidad);
								TransferEntidad clon_entidad = entidad.clonar();
								Vector<Object> v = new Vector<Object>();
								v.add(clon_entidad);
								v.add(true);
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarEntidad,v);
							}	
					});
					popup.add(j4);
					popup.add(new JSeparator());
					//Añadir restricciones			
					JMenuItem j5 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RESTRICTIONS));
					j5.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Añadir una restriccion a la entidad: " + entidad);
							TransferEntidad clon_entidad = entidad.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirRestriccionAEntidad,clon_entidad);
						}	
					});
					popup.add(j5);
					//Añadir restricciones	Unique		
					JMenuItem j6 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.TABLE_UNIQUE));
					j6.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Tabla 'unique' entidad: " + entidad);
							TransferEntidad clon_entidad = entidad.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_TablaUniqueAEntidad,clon_entidad);
						}	
					});
					popup.add(j6);
				
						
				//ATRIBUTO		
				}else if(nombre.contains(Lenguaje.getMensaje(Lenguaje.ATTRIBUTE))){
					//buscamos el transfer
					nombre= nombre.replace('"', '*');
					nombre= nombre.replaceAll(Lenguaje.getMensaje(Lenguaje.THE_ATTRIBUTE)+" ", "");
					nombre= nombre.replace("*", "");
					int ind = nombre.indexOf("(", 0);
					nombre = nombre.substring(0,ind-1);
					// y el transfer de la entidad, relacion o atributo compuesto a la que pertenece
					String nombreE = selPath.getPathComponent(0).toString();
					int ind1, ind2;
					ind1= nombreE.indexOf("(",0);
					ind2= nombreE.indexOf(")",0);
					nombreE= nombreE.substring(ind1+1, ind2);
					
					
					int pertenece=-1; //0 entidad, 1 relacion, 2 atributo compuesto.
					if (nombreE.contains("ent: ")){
						pertenece=0;
						nombreE=nombreE.replace("ent: ", "");  /*TRADUCIR*/
						nombreE= nombreE.replace('"', '*');
						nombreE= nombreE.replace("*", "");
					}else if(nombreE.contains("rel: ")){
						pertenece=1;
						nombreE=nombreE.replace("rel: ", "");  /*TRADUCIR*/
						nombreE= nombreE.replace('"', '*');
						nombreE= nombreE.replace("*", "");
					}else if(nombreE.contains("attr: ")){
						pertenece=2;
						nombreE=nombreE.replace("attr: ", "");  /*TRADUCIR*/
					}
					
					int numAtributo=-1;
					int index=-1;
					int idAtributo=-1;
										
					if(pertenece==0){
						for (int i=0; i<listaEntidades.size();i++){
							if(listaEntidades.get(i).getNombre().equals(nombreE)){
								index=i;
							}
						}
						final TransferEntidad entidad = listaEntidades.get(index);
						for (int i=0; i<listaAtributos.size();i++){
							if(listaAtributos.get(i).getNombre().equals(nombre) && 
									entidad.getListaAtributos().contains(Integer.toString((listaAtributos.get(i).getIdAtributo())))){
								numAtributo=i;
								idAtributo=listaAtributos.get(i).getIdAtributo();
							}
						}
					}else if(pertenece==1){
						for (int i=0; i<listaRelaciones.size();i++){
							if(listaRelaciones.get(i).getNombre().equals(nombreE)){
								index=i;
							}
						}
						final TransferRelacion relacion = listaRelaciones.get(index);
						for (int i=0; i<listaAtributos.size();i++){
							if(listaAtributos.get(i).getNombre().equals(nombre) && 
									relacion.getListaAtributos().contains(Integer.toString((listaAtributos.get(i).getIdAtributo())))){
								numAtributo=i;	
							}
						}
					}else if(pertenece==2){
						for (int i=0; i<listaAtributos.size();i++){
							if(listaAtributos.get(i).getIdAtributo() == Integer.parseInt(nombreE)){
								index=i;
							}
						}
						final TransferAtributo atributoC = listaAtributos.get(index);
						for (int i=0; i<listaAtributos.size();i++){
							if(listaAtributos.get(i).getNombre().equals(nombre) && 
									atributoC.getListaComponentes().contains(Integer.toString((listaAtributos.get(i).getIdAtributo())))){
								numAtributo=i;	
							}
						}
					}
					
					final TransferAtributo atributo = listaAtributos.get(numAtributo);
					
					if (pertenece==0)//si es atributo de entidad miramos si es clave primaria
						for (int j=0; j<listaEntidades.get(index).getListaClavesPrimarias().size();j++){ 
							System.out.println(listaEntidades.get(index).getListaClavesPrimarias().get(j).toString());
							System.out.println(idAtributo);
							if(Integer.parseInt(listaEntidades.get(index).getListaClavesPrimarias().get(j).toString())==idAtributo){
								atributo.setClavePrimaria(true);
							}
						}
					// Editar el dominio del atributo
					JMenuItem j2 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.EDIT_DOMAIN));
					j2.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Cambiar el dominio del atributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarDominioAtributo,clon_atributo);
						}	
					});
					popup.add(j2);
					
					// Renombrar un atributo
					JMenuItem j1 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RENAME_ATTRIB));
					j1.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Cambiar nombre del atributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_RenombrarAtributo,clon_atributo);
						}	
					});
					popup.add(j1);

					// Eliminar un atributo
					JMenuItem j7 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE_ATTRIB));
					j7.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Eliminar atributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							Vector<Object> v = new Vector<Object>();
							v.add(clon_atributo);
							v.add(true);
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarAtributo,v);
						}	
					});
					popup.add(j7);					
					popup.add(new JSeparator());

					// Establecer clave primaria
					// Solamente estara activo cuando sea un atributo directo de una entidad
					final TransferEntidad ent = esAtributoDirecto(atributo);
					if (ent != null){
						JCheckBoxMenuItem j6 = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.IS_PRIMARY_KEY)+" \""+ent.getNombre()+"\"");
						if (atributo.isClavePrimaria()) j6.setSelected(true);
						j6.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Editar clave primaria etributo: " + atributo);
								TransferAtributo clon_atributo = atributo.clonar();
								TransferEntidad clon_entidad = ent.clonar();
								Vector<Transfer> vector = new Vector<Transfer>();
								vector.add(clon_atributo);
								vector.add(clon_entidad);
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarClavePrimariaAtributo,vector);	
							}	
						});
						popup.add(j6);
					}
					
					// Es un atributo compuesto
					JCheckBoxMenuItem j3 = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.COMPOSED));
					final boolean notnul= atributo.getNotnull();
					final boolean unique = atributo.getUnique();
					if (atributo.getCompuesto()) j3.setSelected(true);
					else j3.setSelected(false);
					j3.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Cambiar el carácter de compuesto del atributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							if (notnul){
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarNotNullAtributo,clon_atributo);
							}
							if (unique){
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarUniqueAtributo,clon_atributo);
							}
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarCompuestoAtributo,clon_atributo);	
						}	
					});
					popup.add(j3);
					
					// Si es compuesto
					if (atributo.getCompuesto()){
						JMenuItem j4 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_SUBATTRIBUTE));
						j4.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Anadir subatributo: " + atributo);
								TransferAtributo clon_atributo = atributo.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirSubAtributoAAtributo,clon_atributo);
							}	
						});
						popup.add(j4);
					}
					//popup.add(new JSeparator());

					// Es un atributo NotNull
					if(!atributo.getCompuesto() && !atributo.isClavePrimaria()){
						JCheckBoxMenuItem j3a = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.NOT_NULL));
						if (atributo.getNotnull()) j3a.setSelected(true);
						else j3a.setSelected(false);
						j3a.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Cambiar el carácter de 'Not Null' del atributo: " + atributo);
								TransferAtributo clon_atributo = atributo.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarNotNullAtributo,clon_atributo);	
							}	
						});
						popup.add(j3a);
						//popup.add(new JSeparator());
					}
					// Es un atributo Unique
					if(!atributo.getCompuesto() && !atributo.isClavePrimaria()){
					JCheckBoxMenuItem j3b = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.UNIQUE));
					if (atributo.getUnique()) j3b.setSelected(true);
					else j3b.setSelected(false);
					j3b.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Cambiar el carácter de 'Unique' del atributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarUniqueAtributo,clon_atributo);	
						}	
					});
					popup.add(j3b);
					//popup.add(new JSeparator());
					}
					
					// Es un atributo multivalorado
					if( !atributo.isClavePrimaria()){
					JCheckBoxMenuItem j5 = new JCheckBoxMenuItem(Lenguaje.getMensaje(Lenguaje.IS_MULTIVALUATED));
					if (atributo.isMultivalorado()) j5.setSelected(true);
					else j5.setSelected(false);
					j5.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Cambiar el carácter de multivalorado: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarMultivaloradoAtributo,clon_atributo);	
						}	
					});
					popup.add(j5);
					//popup.add(new JSeparator());
					}
					
					
					popup.add(new JSeparator());
					//Añadir restricciones			
					JMenuItem j8 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RESTRICTIONS));
					j8.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Añadir una restriccion al atributo: " + atributo);
							TransferAtributo clon_atributo = atributo.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirRestriccionAAtributo,clon_atributo);
						}	
					});
					popup.add(j8);
								
					
				}else if(nombre.contains(Lenguaje.getMensaje(Lenguaje.RELATION)) || 
						 nombre.contains(Lenguaje.getMensaje(Lenguaje.RELATION).toLowerCase())){
					//buscamos el transfer
					nombre= nombre.replace('"', '*');
					nombre= nombre.replaceAll(Lenguaje.getMensaje(Lenguaje.THE_RELATION)+" ", "");
					nombre= nombre.replace("*", "");
					
					int index=-1;
					for (int i=0; i<listaRelaciones.size();i++){
						if(listaRelaciones.get(i).getNombre().equals(nombre)){
						 index=i;	
						}
					}
					
					if (index == -1){
						nombre= nombre.substring(nombre.indexOf('(')+1, nombre.length()-1);
						int indice = Integer.parseInt(nombre);
						for (int i=0; i<listaRelaciones.size();i++){
							if(listaRelaciones.get(i).getIdRelacion()==indice){
							 index=i;	
							}
						}
					}
					
					final TransferRelacion relacion= listaRelaciones.get(index);
										
					if (!(relacion.getTipo().equals("IsA"))){
						// Anadir una entidad
						JMenuItem j3 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_ENT));
						j3.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Añadir una entidad a la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirEntidadARelacion,clon_relacion);	
							}	
						});
						popup.add(j3);

						// Quitar una entidad
						JMenuItem j4 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.REMOVE_ENTITY));
						j4.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Quitar una entidad a la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_QuitarEntidadARelacion,clon_relacion);	
							}	
						});
						popup.add(j4);

						// Editar la aridad de una entidad
						JMenuItem j5 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.EDIT_CARD_ROL));
						j5.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Editar aridad de entidad de la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarCardinalidadEntidad,clon_relacion);	
							}	
						});
						popup.add(j5);
						popup.add(new JSeparator());

						// Anadir un atributo a la relacion
						JMenuItem j6 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.ADD_ATTRIBUTE));
						if (relacion.getTipo().equals("Debil"))
							j6.setEnabled(false);
						else{
							j6.setEnabled(true);
							j6.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(ActionEvent e) {
									popup.setVisible(false);
									System.out.println("Añadir atributo a la relación: " + relacion);
									TransferRelacion clon_relacion = relacion.clonar();
									controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirAtributoRelacion,clon_relacion);	
								}	
							});
						}
						popup.add(j6);	
						popup.add(new JSeparator());
						
						// Renombrar la relacion
						JMenuItem j1 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RENAME_RELATION));
						j1.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Renombrar la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_RenombrarRelacion,clon_relacion);	
							}	
						});
						popup.add(j1);
						
						// Eliminar la relacion
							JMenuItem j7 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.DELETE_REL));
							j7.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(ActionEvent e) {
									popup.setVisible(false);
									System.out.println("Eliminar la relación: " + relacion);
									TransferRelacion clon_relacion = relacion.clonar();
									Vector<Object> v = new Vector<Object>();
									v.add(clon_relacion);
									v.add(true);
									controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarRelacionNormal,v);	
								}	
							});
							popup.add(j7);				
							popup.add(new JSeparator());

						//Añadir restricciones			
						JMenuItem j8 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.RESTRICTIONS));
						j8.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Añadir una restriccion a la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirRestriccionARelacion,clon_relacion);
							}	
						});
						popup.add(j8);
						//Añadir restricciones	Unique		
						JMenuItem j9 = new JMenuItem(Lenguaje.getMensaje(Lenguaje.TABLE_UNIQUE));
						j9.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Tabla 'unique' relacion: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_TablaUniqueARelacion,clon_relacion);
							}	
						});
						popup.add(j9);
						
					} else {// if no Isa
					//buscamos el transfer
					
					/*nombre= nombre.replace(Lenguaje.getMensaje(Lenguaje.ISA_RELATION)+" (", "");
					nombre= nombre.replace(")", "");
					int id=0;
					try{
						id= Integer.parseInt(nombre);
					}catch(Exception e1){}
					
					int index=-1;
					for (int i=0; i<listaRelaciones.size();i++){
						if(listaRelaciones.get(i).getIdRelacion()==id){
						 index=i;	
						}
						
					}
					final TransferRelacion relacion= listaRelaciones.get(index);*/
					
					popup.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.SET_PARENT_ENT)){
						private static final long serialVersionUID = 8766595520619916135L;
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Establecer entidad padre: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EstablecerEntidadPadre,clon_relacion);
						}
					}));
					popup.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.REMOVE_PARENT_ENT)){
						private static final long serialVersionUID = 8766595520619916135L;
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Quitar entidad padre: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_QuitarEntidadPadre,clon_relacion);
						}
					}));

					popup.add(new JSeparator());

					popup.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.ADD_CHILD_ENT)){
						private static final long serialVersionUID = 8766595520619916135L;
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Añadir entidad hija: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_AnadirEntidadHija,clon_relacion);
						}
					}));

					popup.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.REMOVE_CHILD_ENT)){
						private static final long serialVersionUID = 8766595520619916135L;
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
							System.out.println("Quitar entidad hija: " + relacion);
							TransferRelacion clon_relacion = relacion.clonar();
							controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_QuitarEntidadHija,clon_relacion);
						}
					}));

					popup.add(new JSeparator());
					//Eliminal la relacion
					
						popup.add(new JMenu().add(new AbstractAction(Lenguaje.getMensaje(Lenguaje.DELETE_REL)){
							private static final long serialVersionUID = -218800914185538588L;
							public void actionPerformed(ActionEvent e) {
								popup.setVisible(false);
								System.out.println("Eliminar la relación: " + relacion);
								TransferRelacion clon_relacion = relacion.clonar();
								Vector<Object> v = new Vector<Object>();
								v.add(clon_relacion);
								v.add(true);
								controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarRelacionIsA,v);
							}
						}));
				}
			}		
				
	//	}//path count=1
			
		popup.setLocation(e.getLocationOnScreen());
		popup.setVisible(true);
	}
			
			
	};
	 
	private TransferEntidad esAtributoDirecto(TransferAtributo ta){
				//Collection<TransferEntidad> listaEntidades = this.entidades.values();
				for (Iterator<TransferEntidad> it = listaEntidades.iterator(); it.hasNext();){
					TransferEntidad te = it.next();
					if (te.getListaAtributos().contains(String.valueOf(ta.getIdAtributo()))) return te;
				}
				return null;
	}
		 
		 
	public void activaBotones(){
		botonLimpiarPantalla.setEnabled(true);
		botonValidar.setEnabled(true);
		botonModeloRelacional.setEnabled(true);
		botonScriptSQL.setEnabled(true);
		botonExportarArchivo.setEnabled(true);
		botonEjecutarEnDBMS.setEnabled(true);
	}
	
	
	/**
	 * Oyentes de los botones de la barra de menus
	 */
	
	private void submenuExportarJPEGActionPerformed(ActionEvent evt) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(Lenguaje.getMensaje(Lenguaje.DBCASE));
		jfc.setFileFilter(new FileNameExtensionFilter(Lenguaje.getMensaje(Lenguaje.JPEG_FILES), "jpg"));
		int resul = jfc.showSaveDialog(null);
		if (resul == 0){
			File ruta = jfc.getSelectedFile();
			this.panelDiseno.writeJPEGGraph(ruta);
			JOptionPane.showMessageDialog(
					null,
					Lenguaje.getMensaje(Lenguaje.INFO)+"\n"+
					Lenguaje.getMensaje(Lenguaje.OK_EXPORT)+".\n" +
					Lenguaje.getMensaje(Lenguaje.FILE)+": "+ruta,
					Lenguaje.getMensaje(Lenguaje.DBCASE),
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(getClass().getClassLoader().getResource(ImagePath.OK)));
		}
	}
	
	private void submenuImprimirActionPerformed(ActionEvent evt) {
		this.panelDiseno.printGraph();
	}
	
	/*labelBarraEstado = new JLabel();
						panelBarraEstado.add(labelBarraEstado, BorderLayout.CENTER);
						labelBarraEstado.setText(Lenguaje.getMensaje(Lenguaje.WORKSPACE_IS));*/
	
	private void submenuAcercaDeActionPerformed(ActionEvent evt) {
		JOptionPane.showMessageDialog(
				null,
				"\n"+
				Lenguaje.getMensaje(Lenguaje.DB_CASE_TOOL)+"\n" +
				"\""+Lenguaje.getMensaje(Lenguaje.TOOL_FOR_DESING)+"\"\n" +
				"\n" +
				Lenguaje.getMensaje(Lenguaje.SS_II)+"\n" +
				Lenguaje.getMensaje(Lenguaje.COLLEGE)+"\n" +
				Lenguaje.getMensaje(Lenguaje.UNIVERSITY)+"\n" +
				"\n" +
				Lenguaje.getMensaje(Lenguaje.DIRECTOR)+"\n" +
				Lenguaje.getMensaje(Lenguaje.TEACHER_NAME)+"\n"+
				"\n"+
				Lenguaje.getMensaje(Lenguaje.AUTHORS)+"\n" +
				Lenguaje.getMensaje(Lenguaje.AUTHOR1)+"\n" +
				Lenguaje.getMensaje(Lenguaje.AUTHOR2)+"\n" +
				Lenguaje.getMensaje(Lenguaje.AUTHOR3)+"\n"+
				"\n"+
				Lenguaje.getMensaje(Lenguaje.BASED)+"\n"+
				"\n"+
				Lenguaje.getMensaje(Lenguaje.CONTACT) +
				"\n" +
				"\n"
				,
				Lenguaje.getMensaje(Lenguaje.DBCASE_LABEL),
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGOFDI)));
	}
	
	private void submenuContenidosActionPerformed(ActionEvent evt) {
		// Abrimos el explorador los ficheros HTML de ayuda
		String ruta = getClass().getClassLoader().getResource("doc/DBDT/index.htm").getPath();
		if (ruta.lastIndexOf('!') != -1){ // Si se ejecuta desde JAR busca el la carpeta /doc/ externa
			ruta = ruta.substring(0, ruta.indexOf('!'));
			String dir = ruta.substring(0, ruta.lastIndexOf('/'));
			System.out.println(dir);
			dir += "/doc/DBDT/index.htm";
			System.out.println(dir);
			dir = dir.substring(5);
			System.out.println(dir);
			ApplicationLauncher.launchDefaultViewer(dir);
		}
		else { // Si es binario usual no realiza cambios
			System.out.println(ruta);
			ApplicationLauncher.launchDefaultViewer(ruta);
		}
	}
	
	public GUIPrincipal getThePrincipal(){
		return this;
	}
	public JPopupMenu getPopUp(){
		return popup;
	}
	public PanelGrafo getPanelDiseno(){
		return panelDiseno;
	}
	
	
	// --- --- --- METODOS AUXILIARES --- --- ---

	public void cambiarConexion(String nombreConexion) {
		if(listaConexiones==null) return;
		// Obtener conexión indicada
		TransferConexion tc = null;
		int i=0;
		boolean encontrado = false;
		
		while (!encontrado && i<listaConexiones.size()){
			tc = listaConexiones.get(i);
			encontrado = tc.getRuta().equalsIgnoreCase(nombreConexion);
			i++;
		}
		// Cambiar conexión actual
		conexionActual = tc;
		cboSeleccionDBMS.setSelectedIndex(tc.getTipoConexion());
	
		for (int k=0; k<elementosMenuSGBDActual.size(); k++){
			JCheckBoxMenuItem elem = elementosMenuSGBDActual.get(k);
			elem.setSelected(elem.getText().equalsIgnoreCase(tc.getRuta()));
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		this.controlador.mensajeDesde_GUIPrincipal(TC.GUI_Principal_Click_Salir, null);
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	
	public void enableCerrar(boolean c){
		this.submenuCerrar.setEnabled(c);
	}
	public void enableGuardar(boolean c){
		this.submenuGuardar.setEnabled(c);
	}
	public void enableGuardarComo(boolean c){
		this.submenuGuardarComo.setEnabled(c);
	}
	public void visiblePrincipal(boolean b) {
		//this.panelPrincipal.setVisible(b);
	}
	
	public boolean getEnableCerrar(){
		return submenuCerrar.isEnabled();
	}
	public boolean getEnableGuardar(){
		return submenuGuardar.isEnabled();
	}
	public boolean getEnableGuardarComo(){
		return submenuGuardarComo.isEnabled();
	}
	public boolean getVisiblePrincipal() {
		//return panelPrincipal.isVisible();
		return true;
	}
	public void setSalvado(boolean b){
		if (b) salvado.setForeground(Color.GREEN);
		else salvado.setForeground(Color.RED);
	}
	public boolean getSalvado(){
		return (this.salvado.getForeground() == Color.GREEN);
	}

	public void loadInfo() {
		controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_MostrarDatosEnPanelDeInformacion, getPanelDiseno().generaArbolInformacion());
		controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_MostrarDatosEnTablaDeVolumenes, getPanelDiseno().generaTablaVolumenes());
	}

}
	


