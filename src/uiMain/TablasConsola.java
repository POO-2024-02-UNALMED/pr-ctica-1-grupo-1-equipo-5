/* CLASE PARA LA IMPRESION POR PANTALLA DE TABLAS DE DATOS
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package uiMain;

import java.util.ArrayList;

import gestorAplicacion.alojamiento.Alojamiento;
import gestorAplicacion.hangar.Avioneta;
import gestorAplicacion.adminVuelos.Aerolinea;
import gestorAplicacion.adminVuelos.Tiquete;
import gestorAplicacion.adminVuelos.Vuelo;

/* IMPLEMENTA LA INTERFACE GENERADORDETABLAS, PARA IMPRIMIR POR PANTALLA LOS DATOS PASADOS A LOS METODOS EN DETERMINADO
FORMATO HACIENDO USO DE System.out.printf() (SE DETALLA EN LOS COMENTARIOS DE printEncabezadoAerolinea()). */
public class TablasConsola implements GeneradorDeTablas {

	/* RECIBE UNA LISTA DE AEROLINEAS E IMPRIME POR PANTALLA LOS VUELOS DISPONIBLES (QUE NO ESTEN COMPLETOS) DE CADA
	AEROLINEA HACIENDO USO DE LOS METODOS printEncabezadoAerolinea(), printVuelos() Y printSeparador(). */
	@Override
	public void mostrarTablaDeVuelosDisponiblesPorAerolineas(ArrayList<Aerolinea> aerolineas)
	{
		for (int i = 0; i < aerolineas.size(); i++)
		{
			Aerolinea aerolinea = aerolineas.get(i);
			printEncabezadoAerolinea(aerolinea);
			printVuelos(aerolinea.vuelosDisponibles(aerolinea.getVuelos()));
			GeneradorDeTablas.printSeparador();
		}
	}

	/* RECIBE UNA LISTA DE AEROLINEAS E IMPRIME POR PANTALLA TODOS LOS VUELOS DE CADA AEROLINEA HACIENDO
	USO DE LOS METODOS printEncabezadoAerolinea(), printVuelos() Y printSeparador(). */
	@Override
	public void mostrarTablaDeVuelosPorAerolineas(ArrayList<Aerolinea> aerolineas)
	{
		for (int i = 0; i < aerolineas.size(); i++)
		{
			Aerolinea aerolinea = aerolineas.get(i);
			printEncabezadoAerolinea(aerolinea);
			printVuelos(aerolinea.getVuelos());
			GeneradorDeTablas.printSeparador();
		}
	}

	/* RECIBE UNA AEROLINEA Y SUS VUELOS, E IMPRIME POR PANTALLA ESTOS VUELOS HACIENDO
	USO DE LOS METODOS printEncabezadoAerolinea(), printVuelos() Y printSeparador(). */
	@Override
	public void mostrarTablaDeVuelos(Aerolinea aerolinea, ArrayList<Vuelo> vuelos)
	{
		if (vuelos.size() != 0)
		{
			printEncabezadoAerolinea(aerolinea);
			printVuelos(vuelos);
			GeneradorDeTablas.printSeparador();
		}
	}

	//RECIBE UNA LISTA DE TIQUETES Y SE ENCARGA DE MOSTRAR POR PANTALLA CADA UNO DE LOS PASAJEROS ASOCIADOS A ESA LISTA DE TIQUETES.
	@Override
	public void mostrarTablaDePasajeros(ArrayList<Tiquete> tiquetes)