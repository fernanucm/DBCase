package Presentacion.GUIPanels;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import Presentacion.Theme.Theme;
/*
 * 
 * Es un JTextPanel con estilo personalizado
 * 
 * */
@SuppressWarnings("serial")
public class reportPanel extends JTextPane{

	private JScrollPane scroll;
	public reportPanel(Theme theme) {
		setContentType("text/html");
		setBorder(null);
		//css
		setText("<style>"
				+ "strong{color:"+theme.header().hexValue()+";}"
				+ "body{background-color:"+theme.background().hexValue()+";margin:0}"
				+ "h1{padding:10;padding-left:30px;background-color:"+theme.header().hexValue()+";color:white;font-size:20px}"
				+ "div{padding:15px}"
				+ "p{font-family:monospaced;padding-left:30px}"
				+ "</style><p></p>");
		scroll = new JScrollPane(this);
	}
	public JScrollPane getPanel() {
		return scroll;
	}
	public void goToTop() {
		this.setCaretPosition(0);
	}
}
