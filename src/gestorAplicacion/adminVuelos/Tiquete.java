/* CLASE TIQUETE
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package gestorAplicacion.adminVuelos;
import java.io.Serializable;
import gestorAplicacion.hangar.*;


import gestorAplicacion.alojamiento.Alojamiento;

/* LA CLASE TIQUETE COTIENE LA INFORMACION ASOCIADA AL VUELO, SILLA Y ALOJAMIENTOS SELECCIONADOS
TAMBIEN CONTIENE UN ID PARA TIQUETE Y EL VALOR DEL PRECIO.
SUS METODOS NOS PERMITEN CONSOLIDAR EL RESUMEN DEL TIQUETE QUE TIENE UN PASAJARO ASOCIADO A UN VUELO. */
public class Tiquete implements Serializable {

	//ATRIBUTOS.
	private static final long serialVersionUID = 1L;
	private int id;
	private int precio;
	private Vuelo vuelo;
	private Silla silla;
	private Pasajero pasajero;
	private Alojamiento alojamiento;

	//CONSTRUCTORES.
	public Tiquete(int id, int precio, Vuelo vuelo) {
		this.id = id;
		this.precio = precio;
		this.vuelo = vuelo;
		vuelo.getTiquetes().add(this);
	}

	public Tiquete(int id, int precio, Vuelo vuelo, Silla silla, Pasajero pasajero, Alojamiento alojamiento) {
		this.id = id;
		this.precio = precio;
		this.vuelo = vuelo;
		this.silla = silla;
		this.pasajero = pasajero;
		this.alojamiento = alojamiento;
		vuelo.getTiquetes().add(this);
	}

	// METODOS.

	/* ESTE METODO NO TIENE PARAMETROS DE ENTRADA PERO RETORNA UN BOOLEANO, YA QUE
	SU OBJETIVO ES ASIGNARLE EL PRECIO A CADA INSTANCIA DE TIQUETE OBTENIENDO LOS PRECIOS
	DEL VUELO Y SILLA SELECCIONADOS ADEMAS DE UN PEQUEÑO SOBRECARGO QUE CORRESPONDE A UN PORCENTAJE DEL COSTO
	DE GASOLINA DEL TRAYECTO, CON UN DESCUENTO POR CONFORME AL ATRIBUTO EDAD DEL PASAJERO. */
	public boolean asignarPrecio() {
		boolean hayDescuento = false;
		int precio_total=vuelo.getPrecio() + this.getSilla().getClase().getPrecio()+(int)(this.getVuelo().getAeronave().calcularPrecioConsumoGasolina(this.getVuelo().getDistancia_en_km())*0.0025);
		if (pasajero.getEdad()<5) {
			hayDescuento = true;
			this.precio = (int) (precio_total - (precio_total*0.25));
		}else if (pasajero.getEdad()>5 && pasajero.getEdad()<=10){
			this.precio = (int) (precio_total - (precio_total*0.15));
			hayDescuento = true;
		}else {
			this.precio = precio_total;
		}
		return hayDescuento;
	}

	/* ESTE METODO SOBRECARGADO ASGINAR PRECIO RECIBE COMO PARAMETRO DE ENTRADA
	UN ENTERO Y NO RETORNA NADA. SU FUNCION SUMARLE A CADA INSTANCIA DE TIQUETE EL PRECIO DEL ALOJAMIENTO
	SELECCIONADO DE ACUERDO AL NUMERO DE DIAS INGRESADO JUNTO A LOS PRECIOS DEL VUELO Y SILLA. */
	public void asignarPrecio(int num_dias) {
		int precio_total=vuelo.getPrecio()+ alojamiento.calcularPrecio(num_dias) + this.getSilla().getClase().getPrecio()+(int)(this.getVuelo().getAeronave().calcularPrecioConsumoGasolina(this.getVuelo().getDistancia_en_km())*0.0025);
		if (pasajero.getEdad()<5) {
			this.precio = (int) (precio_total - (precio_total*0.25));
		}else if (pasajero.getEdad()>5 && pasajero.getEdad()<=10){
			this.precio = (int) (precio_total - (precio_total*0.15));
		}else {
			this.precio = precio_total;
		}
	}

	/* VAMOS A UTILIZAR EL METEDO TOSTRING PARA IMPRIR EL RESUMEN DEL TIQUETE ADQUIRIDO
	DEPENDIENDO SI EL TIQUETE TIENE O NO ASOCIADO UN ALOJOMIENTO. */
	public String toString()
	{
		if (this.alojamiento==null) {
			return  "#####################################\n"+
					"      Su compra ha sido exitosa\n"+
					"    Gracias por confiar en nosotros\n"+
					"#####################################\n"+

					"------------------------------------\n"+
					"      Tiquete No."+ this.id + "\n"+
					"------------------------------------\n"+
					"Nombre Pasajero: " + pasajero.nombre + "\n" +
					"Fecha: " + vuelo.getFecha_de_salida() + "\n" +
					"Vuelo: " + vuelo.getID() + "\n" +
					"Num Silla: " + silla.getNumero_de_silla() + " "  + silla.getUbicacion() + "\n" +
					"Origen: " + vuelo.getOrigen() + "\n" +
					"Destino: " + vuelo.getDestino() + "\n" +
					"Precio Total: " + this.getPrecio() + "\n" +
					"------------------------------------\n";


		}else {

			return  "#####################################\n"+
					"      Su compra ha sido exitosa\n"+
					"    Gracias por confiar en nosotros\n"+
					"#####################################\n"+
					"------------------------------------\n"+
					"      Tiquete No."+ this.id + "\n"+
					"------------------------------------\n"+
					"Nombre Pasajero: " + pasajero.nombre + "\n" +
					"Fecha: " + vuelo.getFecha_de_salida() + "\n" +
					"Vuelo: " + vuelo.getID() + "\n" +
					"Silla: " + silla.getNumero_de_silla() + " - "  + silla.getUbicacion()  + "\n" +
					"Origen: " + vuelo.getOrigen() + "\n" +
					"Destino: " + vuelo.getDestino() + "\n" +
					"Alojamiento: " + alojamiento.getNombre() + "\n" +
					"Precio Total: " + this.getPrecio() + "\n" +
					"------------------------------------\n";

		}
	}

	//GETTERS Y SETTERS.

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public Vuelo getVuelo() {
		return vuelo;
	}
	public void setVuelo(Vuelo vuelo) {
		this.vuelo = vuelo;
	}
	public Silla getSilla() {
		return silla;
	}