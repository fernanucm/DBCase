package Presentacion.icons;

import javax.swing.Icon;

public abstract class icon  implements Icon{
	
	private int tam = 70;
	
	@Override
    public int getIconWidth() {
      return tam;
    }
    @Override
    public int getIconHeight() {
      return tam/2;
    }
}
