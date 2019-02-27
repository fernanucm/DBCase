package Presentacion.GUIPanels;

import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;

import Presentacion.Theme.Theme;
/*
 * 
 * Es un JTextPanel con estilo personalizado
 * 
 * */
@SuppressWarnings("serial")
public class reportPanel extends JTextPane{

	public reportPanel(Theme theme) {
		setContentType("text/html");
		setBorder(null);
		DefaultCaret caret = (DefaultCaret)this.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		//css
		setText("<style>"
				+ ".blue{color:"+theme.blueFont().hexValue()+";}"
				+ "strong{color:"+theme.relation().hexValue()+";}"
				+ "body{background-color:"+theme.background().hexValue()+";margin:0}"
				+ "h1{padding:10;padding-left:30px;background-color:"+theme.header().hexValue()+";color:white;font-size:20px}"
				+ "div{padding:15px}"
				+ "p{font-family:monospaced;padding-left:30px}"
				+ "</style><p></p>");
	}
	
}
