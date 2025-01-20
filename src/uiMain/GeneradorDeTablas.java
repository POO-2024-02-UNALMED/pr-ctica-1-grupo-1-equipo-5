/* INTERFACE PARA LA GENERACION DE TABLAS
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package uiMain;
import java.util.ArrayList;
import gestorAplicacion.adminVuelos.*;
import gestorAplicacion.alojamiento.*;

// SU PROPOSITO ES HACER QUE LAS CLASES QUE IMPLEMENTEN LA INTERFACE, MUESTREN TABLAS DE DATOS EN EL FORMATO QUE SE LE QUIERA DAR A LA TABLA.
public interface GeneradorDeTablas{
	// METODOS DE LA INTERFACE.
	public abstract void mostrarTablaDeVuelosDisponiblesPorAerolineas(ArrayList<Aerolinea> aerolineas);
	public abstract void mostrarTablaDeVuelosPorAerolineas(ArrayList<Aerolinea> aerolineas);
	public abstract void mostrarTablaDeVuelos(Aerolinea aerolineas, ArrayList<Vuelo> vuelos);
	public abstract void mostrarTablaDePasajeros(ArrayList<Tiquete> tiquetes);
	public abstract void mostrarTablaDeAerolineas(ArrayList<Aerolinea> aerolineas);
	public abstract void mostrarTablaDeAlojamientos(ArrayList<Alojamiento> alojamientos);

	// METODO ESTATICO.
	static void printSeparador()
	{
		System.out.println("--------------------------------------------------------------------------------------------------");

	}

	/* METODO POR DEFAULT QUE LAS CLASES QUE IMPLEMENTEN LA INTERFACE PUEDEN O NO USARLAS.
	SE ENCARGA DE RECORRER LOS VUELOS DE UNA AEROLINEA PARA IR IMPRIMIENDO, LINEA POR LINEA, LA INFORMACION PERTINENTE DE CADA UNO. */
	public default void printVuelos(ArrayList<Vuelo> vuelos)
	{
		for (int j = 0; j < vuelos.size(); j++)
		{
			System.out.format("%5s %12s %13s %13s %15s %11s %21s", vuelos.get(j).getID(), vuelos.get(j).getPrecio(), vuelos.get(j).getOrigen(),vuelos.get(j).getDestino(), vuelos.get(j).getFecha_de_salida(), vuelos.get(j).getHora_de_salida(), vuelos.get(j).getAeronave()+"\n");
		}
	}
}
