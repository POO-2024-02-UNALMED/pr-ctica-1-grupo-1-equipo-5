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
    public boolean isDescompuesto() {
		return descompuesto;
	}

	public void setDescompuesto(boolean descompuesto) {
		this.descompuesto = descompuesto;
	}

	public String toString() {
		return this.nombre;
	}

	/* BUSCAR SILLAS POR UBICACION Y TIPO: EN ESTE METODO SE RECIBE UNA UBICACION(UBICACION) Y UN TIPO(STRING),
	LOS CUALES SE UTILIZAN PARA BUSCAR DENTRO DE LAS LISTAS DE LAS SILLAS DE LA AERONAVE QUE LO LLAMA
	Y SI ENCUENTRA UNA CON ESTAS ESPECIFICACIONES LA DEVULVE DE LO CONTRARIO RETORNA NULL. */
	public Silla buscarSillaPorUbicacionyTipo(Ubicacion ubicacion, String tipo) {

		if (tipo.equalsIgnoreCase("ECONOMICA")) {
			for (Silla i : SILLAS_ECONOMICAS) {
				if (i.isEstado() & i.getUbicacion().equals(ubicacion)) {
					return i;
				}
			}
		} else if (tipo.equalsIgnoreCase("EJECUTIVA")) {
			for (Silla i : SILLAS_EJECUTIVAS) {
				if (i.isEstado() & i.getUbicacion().equals(ubicacion)) {
					return i;
				}
			}
		}
		return null;
	}

	/* ESTE METODO RECORRE LOS ARREGLOS DE SILLAS EJECUTIVAS Y ECONOMICAS DE LA AERONAVE QUE LO INVOQUE
	PARA VERIFICAR LA CANTIDAD DE SILLAS QUE ESTAN OCUPADAS Y RETORNAR DICHA CANTIDAD. */
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
		return "Esta es la cantidad de silla ocupadas:"+cont;
	}

	/* ESTE METODO RECORRE LOS ARREGLOS DE SILLAS EJECUTIVAS Y ECONOMICAS DE LA AERONAVE QUE LO INVOQUE PARA VERIFICAR LA
	CANTIDAD DE SILLAS QUE ESTAN OCUPADAS Y SI ESTAN TODAS OCUPADAS DEVUELVE TRUE QUE SIMBOLIZA QUE EL VUELO ESTA LLENO. */
	public Boolean cambiarEstado(){
		Boolean completo= false;
		int cont = 0;
		for (Silla i : this.getSILLASECONOMICAS()) {
			if (!i.isEstado()) {
				cont += 1;
			}
		}
		for (Silla j : this.getSILLASEJECUTIVAS()) {
			if (!j.isEstado()) {
				cont += 1;
			}
		}
		return completo =(cont==(this.getSILLASECONOMICAS().length+this.getSILLASEJECUTIVAS().length));
	}

	/* ESTE METODO RECIBE UNA AERONAVE Y UN IDENTIFICADOR, SI ESTE ES 1 QUIERE DECIR QUE ES UN AVION POR LO QUE
	CREARA TODOS LOS OBJETOS SILLAS SEGUN LA CAPACIDAD DEL AVION Y DEPENDIENDO DE LA CLASE Y EL NUMERO DARA LA UBICACION
	Y SI ES DISTINTO DE UNO QUIERE DECIR QUE CORRESPONDE A UNA AVIONETA Y HARA LO MISMO QUE PARA EL AVION SOLO QUE SEGUN
	LA CAPACIDAD DE LA AVIONETA. */
	public static void asignarParamatrosSilla(Aeronave aeronave,int identificador){
		if(identificador==1){
		aeronave.setSILLASECONOMICAS(new Silla[Avion.getNumSillasEconomicas()]);
		aeronave.setSILLASEJECUTIVAS(new Silla[Avion.getNumSillasEjecutivas()]);

		/* LA VARIABLE UBICACION VA CAMBIANDO SU VALOR SEGUN LOS SIGUIENTES PROCESOS, SE
		USA PARA LA ASIGNACION DEL ATRIBUTO UBICACION DE LAS SILLAS. */
		Ubicacion ubicacion = null;

		/* EL SIGUIENTE PROCESO CREA Y AGREGA SILLAS A LA LISTA DE SILLAS EJECUTIVAS QUE POSEE LA CLASE AVION("HEREDA LA LISTA DE AERONAVE")
		NOTA: LAS SILLAS DE TIPO EJECUTIVA SE REPARTEN EN GRUPOS DE 4 EN FILA POR UN PASILLO.(POR TANTO NO HAY UBICACION CENTRAL). */
		for (int numPosicion = 0; numPosicion < Avion.getNumSillasEjecutivas(); numPosicion++) {
			if (numPosicion % 4 == 0 || numPosicion % 4 == 3) {
				ubicacion = Ubicacion.VENTANA;
			} else {
				ubicacion = Ubicacion.PASILLO;
			}

			aeronave.getSILLASEJECUTIVAS()[numPosicion] = new Silla(Clase.EJECUTIVA, numPosicion, ubicacion);
		}

		/* EL SIGUIENTE PROCESO CREA Y AGREGA SILLAS A LA LISTA DE SILLAS ECONOMICAS QUE POSEE LA CLASE
		AVION("HEREDA LA LISTA DE AERONAVE")NOTA: LAS SILLAS DE TIPO ECONOMICA SE REPARTEN EN GRUPOS DE
		6 EN FILA SEPARADAS POR UN PASILLO. */
		for (int numPosicion = 0; numPosicion < Avion.getNumSillasEconomicas(); numPosicion++) {
			if (numPosicion % 6 == 0 || numPosicion % 6 == 5) {
				ubicacion = Ubicacion.VENTANA;
			} else if (numPosicion % 6 == 1 || numPosicion % 6 == 4) {
				ubicacion = Ubicacion.CENTRAL;
			} else if (numPosicion % 6 == 2 || numPosicion % 6 == 3) {
				ubicacion = Ubicacion.PASILLO;
			}
			aeronave.getSILLASECONOMICAS()[numPosicion] = new Silla(Clase.ECONOMICA, numPosicion, ubicacion);
		}}
		else{
		aeronave.setSILLASECONOMICAS(new Silla[Avioneta.getNumSillasEconomicas()]);
		aeronave.setSILLASEJECUTIVAS(new Silla[Avioneta.getNumSillasEjecutivas()]);

		Ubicacion ubicacion = null;

		for (int numPosicion = 0; numPosicion < Avioneta.getNumSillasEjecutivas(); numPosicion++) {
			if (numPosicion % 4 == 0 || numPosicion % 4 == 3) {
				ubicacion = Ubicacion.VENTANA;
			} else {
				ubicacion = Ubicacion.PASILLO;
			}

			aeronave.getSILLASEJECUTIVAS()[numPosicion] = new Silla(Clase.EJECUTIVA, numPosicion, ubicacion);
		}

		for (int numPosicion = 0; numPosicion < Avioneta.getNumSillasEconomicas(); numPosicion++) {
			if (numPosicion % 6 == 0 || numPosicion % 6 == 5) {
				ubicacion = Ubicacion.VENTANA;
			} else if (numPosicion % 6 == 1 || numPosicion % 6 == 4) {
				ubicacion = Ubicacion.CENTRAL;
			} else if (numPosicion % 6 == 2 || numPosicion % 6 == 3) {
				ubicacion = Ubicacion.PASILLO;
			}
			aeronave.getSILLASECONOMICAS()[numPosicion] = new Silla(Clase.ECONOMICA, numPosicion, ubicacion);
		}
		}