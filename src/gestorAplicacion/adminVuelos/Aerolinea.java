/* CLASE AEROLINEA
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package gestorAplicacion.adminVuelos;
import java.io.Serializable;
import java.util.ArrayList;
import gestorAplicacion.hangar.*;

/* ALMACENA LA INFORMACION DE TODAS LAS AEROLINEAS CREADAS, ADEMAS DE LOS VUELOS Y AERONAVES ASOCIADOS A CADA UNA DE ELLAS
 CON LOS METODOS NECESARIOS PARA ACCEDER A ESTA INFORMACION A TRAVES DE DISTINTOS PARAMETROS. */
public class Aerolinea implements Serializable{

	private static final long serialVersionUID = 1L;

	// ATRIBUTOS.
	private String nombre;
	private ArrayList<Aeronave> aeronaves = new ArrayList<Aeronave>();
	private ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
	private static ArrayList<Aerolinea> aerolineas = new ArrayList<Aerolinea>();

	// CONSTRUCTORES.
	public Aerolinea(String nombre, ArrayList<Vuelo> vuelo){
		this(nombre);
		setVuelos(vuelo);

	}
	public Aerolinea(String nombre) {
		this.nombre = nombre;
		aerolineas.add(this);
	}

	// CUANDO SE LLAME A IMPRIMIR UN OBJETO AEROLINEA RETORNARA EL NOMBRE DE ESTA.
	@Override
	public String toString() {
		return this.nombre;
	}

	/* BUSCAR AEROLINEA: METODO DE CLASE QUE RECIBE UN NOMBRE DE AEROLINEA Y BUSCA ENTRE EL ARREGLO QUE CONTIENE TODOS LOS OBJETOS
	DE TIPO AEROLINEA CREADOS, SI HAY ALGUNA CON ESTE NOMBRE RETORNA ESE OBJETO AEROLINEA, SI NO, DEVUELVE NULL. */
	public static Aerolinea buscarAerolineaPorNombre(String nombre2)
	{
		Aerolinea retorno = null;
		for (int i = 0; i < Aerolinea.getAerolineas().size(); i++)
		{
			if( Aerolinea.getAerolineas().get(i).getNombre().equalsIgnoreCase(nombre2))
			{
				// SI HAY UNA AEROLINEA CUYO NOMBRE COINCIDA CON EL PARAMETRO PASADO, IGNORANDO MINUSCULAS Y MAYUSCULAS,RETORNA ESA AEROLINEA.
				retorno=  Aerolinea.getAerolineas().get(i);
			}
		}
		return retorno;
	}

	// BUSCAR VUELO POR...

	/* ID: RECIBE UNA LISTA DE VUELOS (ARRAYLIST<VUELO>) Y UN ID (INT) Y SE ENCARGA DE RETORNAR EL OBJETO
	VUELO QUE TENGA EL ID QUE PASAMOS COMO PARAMETRO, SI NO LO ENCUENTRA DENTRO DE LA LISTA RETORNA NULL. */
	public Vuelo buscarVueloPorID (ArrayList<Vuelo> vuelos, int ID)
	{
		for (int i = 0; i < vuelos.size(); i++)
		{
		  if (vuelos.get(i).getID() == ID )
		  {
			  return vuelos.get(i);
		  }
		}
		return null;
	}

	/* AERONAVE: RECIBE UNA LISTA DE VUELOS (ARRAYLIST<VUELO>) Y UN NOMBRE_AERONAVE(STRING) Y SE ENCARGA DE RETORNAR EL OBJETO VUELO QUE TENGA
	ASOCIADA LA AERONAVE QUE TIENE EL NOMBRE QUE PASAMOS COMO PARAMETRO, SI NO LO ENCUENTRA EN ESA LISTA DE VUELOS RETORNA NULL. */
	public Vuelo buscarVueloPorAeronave (ArrayList<Vuelo> vuelos, String nombre_Aeronave)
	{
		for (int i = 0; i < vuelos.size(); i++)
		{
		  if (vuelos.get(i).getAeronave().getNombre().equals(nombre_Aeronave.toUpperCase()) )
		  {
			  return vuelos.get(i);
		  }
		}
		return null;
	}

	/* DESTINO: RECIBE UNA LISTA DE VUELOS (ARRAYLIST<VUELO>) Y UN DESTINO(STRING) Y SE ENCARGA DE RETORNAR UNA LISTA DE VUELOS,
	 CON TODOS LOS VUELOS QUE TENGAN ASOCIADO EL DESTINO QUE PASAMOS COMO PARAMETRO.*/
	public ArrayList<Vuelo> buscarVueloPorDestino (ArrayList<Vuelo> vuelos, String destino)
	{
		ArrayList<Vuelo> vuelosPorDestino = new ArrayList<Vuelo>();
		for (int i = 0; i < vuelos.size(); i++)
		{
		  if (vuelos.get(i).getDestino().equalsIgnoreCase(destino)) //IGNORANDO MAYUSCULAS Y MINUSCULAS.
		  {
			  vuelosPorDestino.add(vuelos.get(i));
		  }
		}
		return vuelosPorDestino;
	}

	/* FECHA: RECIBE UNA LISTA DE VUELOS (ARRAYLIST<VUELO> ) Y UNA FECHA (STRING) Y SE ENCARGA DE RETORNAR UNA LISTA DE VUELOS,
	CON TODOS LOS VUELOS QUE TENGAN ASOCIADA LA FECHA QUE PASAMOS COMO PARAMETRO. */
	public ArrayList<Vuelo> buscarVueloPorFecha (ArrayList<Vuelo> vuelos, String fecha)
	{
		ArrayList<Vuelo> vuelosPorFecha = new ArrayList<Vuelo>();
		for (int i = 0; i < vuelos.size(); i++)
		{
		  if (vuelos.get(i).getFecha_de_salida().equals(fecha))
		  {
			  vuelosPorFecha.add(vuelos.get(i));
		  }
		}
		return vuelosPorFecha;
	}

	/* VUELOS DISPONIBLES: RECIBE UNA ARREGLO DE VUELOS (ARRAYLIST<VUELO>) Y SE ENCARGA DE RETORNAR UNA LISTA DE VUELOS, CON
	TODOS LOS VUELOS DE LA AEROLINEA QUE NO ESTEN COMPLETOS, ES DECIR QUE SU ATRIBUTO ESTACOMPLETO SEA IGUAL A false. */
	public ArrayList<Vuelo> vuelosDisponibles(ArrayList<Vuelo> vuelos)
	{
		ArrayList<Vuelo> vuelosDisponibles = new ArrayList<Vuelo>();
		for (int i = 0; i < vuelos.size(); i++)
		{
			if (!vuelos.get(i).isEstaCompleto())
			{
				vuelosDisponibles.add(vuelos.get(i));
			}
		}
		return vuelosDisponibles;
	}

	// AGREGAR UN VUELO: RECIBE COMO PARAMETRO UN VUELO (VUELO) Y SE ENCARGA DE ANADIRLO A LA LISTA DE VUELOS DE LA AEROLINEA.
	public void agregarVuelo(Vuelo vuelo)
	{
		vuelos.add(vuelo);
	}

	/* CANCELAR UN VUELO: RECIBE COMO PARAMETRO UN ENTERO, QUE ES EL ID DEL VUELO A ELIMINAR, Y SE ENCARGA DE RECORRER
	SU LISTA DE VUELOS PARA ELIMINAR EL VUELO QUE CONTENGA EL ID QUE COINCIDE CON EL PARAMETRO, SI LO ENCONTRO Y LO ELIMINO,
	RETORNA true, EN CASO CONTRARIO RETORNA false. */
	public Boolean cancelarVuelo(int vuelo_a_eliminar)
	{
		for (int i = 0; i < vuelos.size(); i++)
		{
		  if (vuelos.get(i).getID() == vuelo_a_eliminar )
		  {
			  vuelos.remove(i);
			  return true;
		  }
		}
		return false;
	}

	/* BUSCAR TIQUETE POR ID : METODO DE CLASE QUE RECIBE UN ID (INT) Y SE ENCARGA DE BUSCAR ENTRE CADA AEROLINEA
	Y EN CADA VUELO DE ESTA AEROLINEA, SI HAY UN TIQUETE ASOCIADO EN LA LISTA DE TIQUETES DE CADA VUELO QUE TENGA POR ID
	EL QUE LE PASAMOS COMO PARAMETRO Y RETORNAR ESTE TIQUETE, HACIENDO USO DEL METODO BUSCARTIQUETEPORID() DE VUELO. */
	public static Tiquete BuscarTiquete(int ID)
	{
		ArrayList<Aerolinea> aerolineasDisponibles = Aerolinea.getAerolineas();
		for (int i = 0; i < aerolineasDisponibles.size(); i++)
		{
			Aerolinea aerolinea = aerolineasDisponibles.get(i);
			for (int j = 0; j < aerolinea.getVuelos().size(); j++)
			{
				Vuelo vuelo = aerolinea.getVuelos().get(j);
				Tiquete tiquete_buscado = vuelo.buscarTiquetePorID(vuelo.getTiquetes(), ID);
				if (tiquete_buscado != null)
				{
					return tiquete_buscado;
				}
			}
		}
		return null;
	}

	// SETTERS Y GETTERS.
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Vuelo> getVuelos() {
		return vuelos;
	}
	public void setVuelos(ArrayList<Vuelo> vuelos) {
		this.vuelos = vuelos;
	}
	public ArrayList<Aeronave> getAeronaves() {
		return aeronaves;
	}
	public void setAviones(ArrayList<Aeronave> aviones) {
		this.aeronaves = aviones;
	}
	public static ArrayList<Aerolinea> getAerolineas() {
		return aerolineas;
	}
	public static void setAerolineas(ArrayList<Aerolinea> aerolineas) {
		Aerolinea.aerolineas = aerolineas;
	}

}
