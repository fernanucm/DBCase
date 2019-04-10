package vista.components.GUIPanels;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import vista.tema.Theme;
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
				+ "body{background-color:"+theme.background().hexValue()+";margin:0}"
				+ "h2{padding-left:15px;font-size:18px}"
				+ "h3{padding-left:15px;font-size:14px}"
				+ "li{padding-top:10px}"
				+ ".card{border:1px solid "+theme.toolBar().hexValue()+";padding:5px;background:"+theme.control().hexValue()+";margin:15px;}"
				+ "p{padding-left:30px;color:"+theme.paragraph().hexValue()+"}"
				+ ".warning{background:"+theme.entity().hexValue()+";padding:5px;margin:15px;}"
				+ ".error{background:"+theme.relation().hexValue()+";padding:5px;margin:15px;}"
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
