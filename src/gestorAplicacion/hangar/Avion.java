/* CLASE AVION
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package gestorAplicacion.hangar;
import gestorAplicacion.adminVuelos.*;

public class Avion extends Aeronave {
	private final static int NUM_SILLAS_ECONOMICAS = 24;
	private final static int NUM_SILLAS_EJECUTIVAS = 12;
	private static final long serialVersionUID = 5930658813769177257L;


	// CONSTRUCTORES
	public Avion(String nombre){
		super(nombre);
	}
	public Avion(String nombre, Aerolinea aerolinea) {
		super(nombre, aerolinea);
	}

	// SOBREESCRITURA DEL METODO TOSTRING DEL PADRE AERONAVE
	@Override
	public String toString() {
		return this.getNombre() + "_A";
	}

	//GET Y SET.
	public static int getNumSillasEconomicas() {
		return NUM_SILLAS_ECONOMICAS;
	}
	public static int getNumSillasEjecutivas() {
		return NUM_SILLAS_EJECUTIVAS;
	}

	// METODOS.

	/* ESTE METODO RECIBE UN TIPO DE DATO DOUBLE DE LA DISTANCIA QUE HAY DESDE EL LUGAR DE ORIGEN AL LUGAR DE DESTINO
      Y RETORNARA EL COSTO EN PESOS TOTAL DE GASOLINA POR RECORRER EL TRAYECTO. */
	public double calcularPrecioConsumoGasolina(double distancia_en_km) {
		double consumido;
		consumido = this.getGastoGasolina() * distancia_en_km;
		return consumido;
	}
}
