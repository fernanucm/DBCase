package vista.icons;

import java.awt.Cursor;
import javax.swing.Icon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class IconLabel extends JLabel{
	
	public IconLabel(Icon icon){
		super(icon);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}
