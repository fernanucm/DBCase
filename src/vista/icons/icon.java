package vista.icons;

import javax.swing.Icon;

public abstract class icon implements Icon{
	
	private int size;
	private boolean pintarMas;
	private final int DEFAULTSIZE = 60;
	private final int MINISIZE = 30;
	
	public icon() {
		super();
		pintarMas = true;
		size = DEFAULTSIZE;
	}
	
	public icon(String tipo) {
		super();
		switch(tipo) {
			case "mini": size = MINISIZE;pintarMas = false;break;
			default: size = DEFAULTSIZE;pintarMas = true;
		}
	}
	protected boolean pintarMas() {
		return pintarMas;
	}
	@Override
    public int getIconWidth() {
      return size;
    }
    @Override
    public int getIconHeight() {
      return size/2;
    }
}
