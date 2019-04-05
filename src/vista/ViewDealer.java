package vista;

import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;


/*
 * Clase que maneja que paneles se ven y cuales no
 * */
public class ViewDealer {
	
	private Container mainPanel;
	private JPanel diagrama;
	private JPanel codigos;
	private JSplitPane diagramaSplitCode;
	private JSplitPane splitCodigos;
	private JSplitPane infoSplitMapa;
	private JSplitPane programmerSplit;
	private JTabbedPane infoPanel;
	private byte modo;
		
	public ViewDealer(Container mainPanel, JPanel diagrama, JPanel codigos, JTabbedPane infoPanel){
		this.mainPanel = mainPanel;
		this.diagrama = diagrama;
		this.codigos = codigos;
		this.infoPanel = infoPanel;
		this.splitCodigos = ((JSplitPane) codigos.getComponent(0));
		this.infoSplitMapa = (JSplitPane) (((JSplitPane) diagrama.getComponent(0)).getComponent(2));
		this.diagramaSplitCode = new JSplitPane();
		this.programmerSplit = new JSplitPane();
	}
	
	public void loadDefaultView() {
		if(modo==0)modoVerTodo();
		else if(modo==1)modoDiseno();
		else if(modo==2)modoProgramador();
		else modoVerTodo();
	}
	/*
	 * Muestra todos los paneles
	 * */
	public void modoVerTodo() {
		mainPanel.removeAll();
		infoSplitMapa.add(infoPanel, JSplitPane.RIGHT);
		infoSplitMapa.setResizeWeight(0.9);
		mainPanel.add(diagramaSplitCode);
		splitCodigos.setOrientation(JSplitPane.VERTICAL_SPLIT);
		diagramaSplitCode.add(codigos, JSplitPane.RIGHT);
		diagramaSplitCode.add(diagrama, JSplitPane.LEFT);
		diagramaSplitCode.setResizeWeight(0.9);
		diagramaSplitCode.setVisible(true);
		programmerSplit.setVisible(false);
		mainPanel.revalidate();		
		mainPanel.repaint();
		
		modo = 0;
	}
	
	/*
	 * Muestra solo los paneles de diseno del diagrama
	 * */
	public void modoDiseno() {
		mainPanel.removeAll();
		infoSplitMapa.add(infoPanel, JSplitPane.RIGHT);
		mainPanel.add(diagrama);
		diagramaSplitCode.setVisible(false);
		programmerSplit.setVisible(false);
		mainPanel.revalidate();
		mainPanel.repaint();
		modo = 1;
	}
	
	/*
	 * Muestra solo los paneles de edicion de codigo
	 * */
	public void modoProgramador() {
		mainPanel.removeAll();
		splitCodigos.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		programmerSplit.add(infoPanel, JSplitPane.LEFT);
		programmerSplit.add(codigos, JSplitPane.RIGHT);
		mainPanel.add(programmerSplit);
		diagramaSplitCode.setVisible(false);
		programmerSplit.setVisible(true);
		mainPanel.revalidate();
		mainPanel.repaint();
		modo = 2;
	}
	
	/*
	 * Muestra / oculta el panel de diseño
	 * */
	public void toggleDiseno() {
		if(modo == 2) modoVerTodo();
		else if(modo == 0) modoProgramador();
	}
	
	/*
	 * Muestra / oculta el panel de codigos
	 * */
	public void toggleCodigos() {
		if(modo == 1) modoVerTodo();
		else if(modo == 0) modoDiseno();
	}

	public int getPanelsMode() {
		return modo;
	}
}
