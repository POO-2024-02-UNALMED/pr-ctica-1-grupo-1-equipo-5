/* CLASE AVIONETA
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package gestorAplicacion.hangar;

import gestorAplicacion.adminVuelos.*;


public class Avioneta extends Aeronave {
	private final static int NUM_SILLAS_ECONOMICAS = 6;
	private final static int NUM_SILLAS_EJECUTIVAS = 4;
	private static final long serialVersionUID = -4031985613776446276L;

	//CONSTRUCTORES.
	public Avioneta(String nombre){
		super(nombre);
	}
	public Avioneta(String nombre, Aerolinea aerolinea) {
		super(nombre, aerolinea);
	}

	//GET Y SET.
	public static int getNumSillasEconomicas() {
		return NUM_SILLAS_ECONOMICAS;
	}

	public static int getNumSillasEjecutivas() {
		return NUM_SILLAS_EJECUTIVAS;
	}

	/* ESTE MÉTODO RECORRERAN LOS ARREGLOS DE SILLAS EJECUTIVOS Y ECONOMICAS DE CADA AVIÓN Y AVIONETA
    PARA VERIFICAR LA CANTIDAD DE SILLAS QUE ESTAN OCUPADAS Y RETORNARAN DICHA CANTIDAD. */
	public String Calcular_Sillas_Ocupadas() {
		int cont = 0;
		for (Silla i : this.getSILLASECONOMICAS()) {
			if (i.isEstado()) {
				cont += 1;
			}
		}
		for (Silla j : this.getSILLASEJECUTIVAS()) {
			if (j.isEstado()) {
				cont += 1;
			}
		}
		return "Sillas ocupadas en la avioneta"+cont;
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
