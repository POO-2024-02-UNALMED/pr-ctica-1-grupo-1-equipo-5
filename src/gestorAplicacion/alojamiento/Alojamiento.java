/* CLASE ALOJAMIENTO
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package gestorAplicacion.alojamiento;
import java.io.Serializable;
import java.util.ArrayList;

/* LA CLASE ALOJAMIENTO POSEE LA INFORMACION DE TODOS LOS ALOJAMIENTOS CREADOS, CON LOS ATRIBUTOS NOMBRE,
LOCACION, PRECIO POR DIA Y NUMERO DE ESTRELLAS. */
public class Alojamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	//ATRIBUTOS.
	private String nombre;
	private String locacion;
	private long precio_dia;
	private int estrellas;
	private static ArrayList<Alojamiento> alojamientos = new ArrayList<Alojamiento>();

	//CONSTRUCTOR.
	public Alojamiento(String nombre, String locacion, long precio_dia, int estrellas) {
		this.nombre = nombre;
		this.locacion = locacion;
		this.precio_dia = precio_dia;
		this.estrellas = estrellas;
		alojamientos.add(this);
	}

	/* EL METODO RECIBE UN PARAMETRO DIAS (int) Y RETORNA EL PRECIO RESULTANTE AL MULTIPLICAR EL PRECIO
	POR DIA DEL ALOJAMIENTO CON EL PARAMETRO DIAS QUE SE LE PASO. */
	public int calcularPrecio(int dias) {
		return (int)( dias * this.precio_dia);
	}

	// BUSCAR ALOJAMIENTOS POR...

	/* UBICACION: METODO DE CLASE QUE RECIBE UNA UBICACION(String) Y BUSCA ENTRE LAS ALOJAMIENTOS DISPONIBLES
	SI HAY ALGUNO EN ESTA LOCACION, SI ES ASI, RETORNA ESE OBJETO ALOJAMIENTO, SI NO, DEVUELVE NULL. */
	public static ArrayList<Alojamiento> buscarAlojamientoPorUbicacion (String ubicacion) {
		ArrayList<Alojamiento> alojamientosEnUbicacion = new ArrayList<Alojamiento>();
		for (int i = 0; i < alojamientos.size(); i++)
		{
		  if (alojamientos.get(i).getLocacion().equalsIgnoreCase(ubicacion))
		  {
			  alojamientosEnUbicacion.add(alojamientos.get(i));
		  }
		}
		return alojamientosEnUbicacion;
	}

	/* NOMBRE: METODO DE CLASE QUE RECIBE UNA NOBRE(String) Y BUSCA ENTRE LAS ALOJAMIENTOS DISPONIBLES SI
	HAY ALGUNO CON ESTE NOMBRE, SI ES ASI, RETORNA ESE OBJETO ALOJAMIENTO, SI NO, DEVUELVE NULL. */
	public static Alojamiento buscarAlojamientoPorNombre(String nombre) {
		for (int i = 0; i < alojamientos.size(); i++)
		{
		  if (alojamientos.get(i).getNombre().equalsIgnoreCase(nombre))
		  {
			  return alojamientos.get(i);
		  }
		}
		return null;
	}

	//GETTERS Y SETTERS.
	public void setLocacion(String locacion) {
		this.locacion = locacion;
	}
	public void setPrecio_dias(long precio_dias) {
		this.precio_dia = precio_dias;
	}
	public static ArrayList<Alojamiento> getAlojamientos() {
		return alojamientos;
	}
	public static void setAlojamientos(ArrayList<Alojamiento> alojamientos) {
		Alojamiento.alojamientos = alojamientos;
	}
	public long getPrecio_dia() {
		return precio_dia;
	}
	public void setPrecio_dia(long precio_dia) {
		this.precio_dia = precio_dia;
	}
	public String getLocacion() {
		return locacion;
	}
	public int getEstrellas() {
		return estrellas;
	}
	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	}

