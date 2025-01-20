/* CLASE SILLA
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package gestorAplicacion.hangar;
import gestorAplicacion.adminVuelos.*;
import java.io.Serializable;
public class Silla implements Serializable {

	//ATRIBUTOS.
	private Clase clase;
	private int numero_de_silla;
	private Pasajero pasajero;
	private boolean estaDisponible = true;
	private Ubicacion ubicacion;

	//CONSTRUCTOR.
	public Silla(Clase clase, int numero_de_silla,Ubicacion ubicacion) {
		this.clase = clase;
		this.numero_de_silla = numero_de_silla;
		this.ubicacion = ubicacion;
	}

	//GETTERS Y SETTERS.
	public Clase getClase() {
		return clase;
	}
	public void setClase(Clase clase) {
		this.clase = clase;
	}
	public int getNumero_de_silla() {
		return numero_de_silla;
	}
	public void setNumero_de_silla(int numero_de_silla) {
		this.numero_de_silla = numero_de_silla;
	}
	public Pasajero getPasajero() {
		return pasajero;
	}
	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}
	public boolean isEstado() {
		return estaDisponible;
	}
	public void setEstado(boolean estado) {
		this.estaDisponible = estado;
	}
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}


}
