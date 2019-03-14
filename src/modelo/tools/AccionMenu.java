package modelo.tools;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import controlador.TC;
import modelo.transfers.TransferDominio;
import vista.GUIPrincipal;
import vista.Lenguajes.Lenguaje;

public class AccionMenu extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String textoOpcion;
	private TransferDominio dominio;
	private GUIPrincipal principal;

	/**
	 * Se le pasa el nombre que se quiere que se muestre
	 * 
	 * @param textoOpcion
	 */
	public AccionMenu(String textoOpcion, GUIPrincipal principal, TransferDominio dominio) {
		this.textoOpcion = textoOpcion;
		this.putValue(Action.NAME, textoOpcion);
		this.principal= principal;
		this.dominio = dominio;
	}

	public void actionPerformed(ActionEvent e) {
		if(textoOpcion.equals(Lenguaje.getMensaje(Lenguaje.DOM_MENU_RENAME))){
			TransferDominio clon_dominio = dominio.clonar();
			principal.getPopUp().setVisible(false);
			principal.getControlador().mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_RenombrarDominio,clon_dominio);
			principal.actualizaArbolDominio(clon_dominio.getNombre());
		}
		if(textoOpcion.equals(Lenguaje.getMensaje(Lenguaje.DOM_MENU_DELETE))){
			TransferDominio clon_dominio = dominio.clonar();
			principal.getPopUp().setVisible(false);
			principal.getControlador().mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EliminarDominio,clon_dominio);
			principal.actualizaArbolDominio(null);
		}
		if(textoOpcion.equals(Lenguaje.getMensaje(Lenguaje.DOM_MENU_MODIFY))){
			TransferDominio clon_dominio = dominio.clonar();
			principal.getPopUp().setVisible(false);
			principal.getControlador().mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_ModificarDominio,clon_dominio);
			principal.actualizaArbolDominio(clon_dominio.getNombre());
		}
		if(textoOpcion.equals(Lenguaje.getMensaje(Lenguaje.DOM_MENU_ADD))){
			principal.getPopUp().setVisible(false);
			principal.getControlador().mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_CrearDominio, null);
			principal.actualizaArbolDominio(null);
		}
		if(textoOpcion.equals(Lenguaje.getMensaje(Lenguaje.DOM_MENU_IN_ORDER))){
			TransferDominio clon_dominio = dominio.clonar();
			principal.getPopUp().setVisible(false);
			principal.getControlador().mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_OrdenarValoresDominio,clon_dominio);
			principal.actualizaArbolDominio(clon_dominio.getNombre());
		}
		
		
	}
	public TransferDominio getDominio() {
		return dominio;
	}

	public void setDominio(TransferDominio dominio) {
		this.dominio = dominio;
	}
}
