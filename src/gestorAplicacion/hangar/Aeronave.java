/* CLASE AERONAVE
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package gestorAplicacion.hangar;
import gestorAplicacion.adminVuelos.*;
import java.io.Serializable;

//CLASE ABSTRACTA.
public abstract class Aeronave implements Serializable{

	private static final long serialVersionUID = 1L;

	// ATRIBUTOS.
	protected final  int PRECIO_GASTO_GASOLINA = 10000;
	private String nombre;
	private Aerolinea aerolinea;
	private boolean descompuesto;
	private Silla[] SILLAS_ECONOMICAS;
	private Silla[] SILLAS_EJECUTIVAS;

	// CONTRUCTORES.
	protected Aeronave(String nombre, Aerolinea aerolinea) {
		this.nombre = nombre;
		this.aerolinea = aerolinea;
	}
	protected Aeronave(String nombre){
		this.nombre=nombre;
	}


	// METODOS