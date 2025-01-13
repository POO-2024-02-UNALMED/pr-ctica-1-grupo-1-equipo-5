/* CLASE TIQUETE
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package gestorAplicacion.adminVuelos;
import java.io.Serializable;
import gestorAplicacion.hangar.*;
import java.util.ArrayList;

// CONTIENE LA INFORMACION PERTINENTE DE UN VUELO, ADEMAS DE LA LISTA DE TIQUETES QUE FUERON ASOCIADOS AL MISMO.
public class Vuelo implements Serializable{

	private static final long serialVersionUID = 1L;

	// ATRIBUTOS.
	private int ID;
	private int precio;
	private String origen;
	private String destino;
	private Aeronave aeronave;
	private double distancia_en_km;
	private String fecha_de_salida;
	private String hora_de_salida;
	private boolean estaCompleto;
	private ArrayList<Tiquete> tiquetes = new ArrayList<Tiquete>();

	//CONSTRUCTORES.
	public Vuelo(int iD, int precio, String origen, String destino, Aeronave aeronave, double distancia, String fecha_de_salida) {
		ID = iD;
		this.precio = precio;
		this.origen = origen;
		this.destino = destino;
		this.aeronave = aeronave;
		this.distancia_en_km = distancia;
		this.fecha_de_salida = fecha_de_salida;

	}
	public Vuelo(int iD, int precio, String origen, String destino, Aeronave aeronave, double distancia, String fecha_de_salida, String hora_de_salida) {
		ID = iD;
		this.precio = precio;
		this.origen = origen;
		this.destino = destino;
		this.aeronave = aeronave;
		this.distancia_en_km = distancia;
		this.fecha_de_salida = fecha_de_salida;
		this.setHora_de_salida(hora_de_salida);
		this.getAeronave().getAerolinea().agregarVuelo(this);
	}

	/* RECIBE UNA LISTA DE TIQUETES (ARRAYLIST<TIQUETE>) Y UN ID DE UN TIQUETE (INT), Y SE ENCARGA DE RECORRER ESA LISTA DE
	TIQUETES. SI ENCUENTRA UNO QUE TENGA EL MISMO ID QUE EL PASADO COMO PARAMETRO, LO RETORNA.SI NO ENCUENTRA NINGUNO RETORNA NULL.*/
	public Tiquete buscarTiquetePorID(ArrayList<Tiquete> tiquetes, int ID)
	{
		for(int i = 0; i < tiquetes.size(); i++)
		{
			if (tiquetes.get(i).getId() == ID)
			{
				return tiquetes.get(i);
			}
		}
		return null;
	}


	//GETTERS Y SETTERS.
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public Aeronave getAeronave() {
		return aeronave;
	}
	public void setAeronave(Aeronave aeronave) {
		this.aeronave = aeronave;
	}
	public double getDistancia_en_km() {
		return distancia_en_km;
	}
	public void setDistancia_en_km(double distancia_en_km) {
		this.distancia_en_km = distancia_en_km;
	}
	public String getFecha_de_salida() {
		return fecha_de_salida;
	}
	public void setFecha_de_salida(String fecha_de_salida) {
		this.fecha_de_salida = fecha_de_salida;
	}
	public ArrayList<Tiquete> getTiquetes() {
		return tiquetes;
	}
	public void setTiquetes(ArrayList<Tiquete> tiquetes) {
		this.tiquetes = tiquetes;
	}
	public String getHora_de_salida() {
		return hora_de_salida;
	}
	public void setHora_de_salida(String hora_de_salida) {
		this.hora_de_salida = hora_de_salida;
	}
	public boolean isEstaCompleto() {
		return estaCompleto;
	}
	public void setEstaCompleto(boolean estaCompleto) {
		this.estaCompleto = estaCompleto;
	}
}
