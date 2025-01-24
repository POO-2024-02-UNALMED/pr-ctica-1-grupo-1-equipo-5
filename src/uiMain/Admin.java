/* CLASE ADMIN PARA LA INTERACCION DEL USUARIO CON EL SISTEMA
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package uiMain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import baseDatos.Deserializador;
import baseDatos.Serializador;
import gestorAplicacion.*;
import gestorAplicacion.alojamiento.Alojamiento;
import gestorAplicacion.adminVuelos.*;
import gestorAplicacion.hangar.*;
import java.lang.Math;

public class Admin {
	static Scanner sc = new Scanner(System.in);
	static GeneradorDeTablas generadorDeTablas = new TablasConsola();

	public static void main(String[] args) {
		Deserializador.deserializar();

//		MENU PRINCIPAL
		int opcion;
		do {
			System.out.println();
			System.out.println("----- TURBINA TOURS AND RESORT -----");
			System.out.println("Que operacion desea realizar?");
			System.out.println("1. Ver todos los vuelos disponibles por Aerolinea");
			System.out.println("2. Comprar tiquete para un vuelo por destino y fecha");
			System.out.println("3. Agregar alojamiento en el destino del vuelo");
			System.out.println("4. Modificar tiquete comprado");
			System.out.println("5. Ver opciones de administrador");
			System.out.println("6. Terminar");
			System.out.println("Por favor escoja una opcion: ");
			opcion = sc.nextInt();

			switch (opcion) {
				case 1:
					;
					mostrarVuelosPorAerolineas();
					break;
				case 2:
					generarTiquete();
					break;
				case 3:
					agregarAlojamiento();
					break;
				case 4:
					modificarTiquete();
					break;
				case 5:
					opcionesAdministrador();
					break;
				case 6:
					salirDelSistema();

					break;
			}
		} while (opcion != 6);
	}
	
	/* CASE 1 MAIN: VER TODOS LOS VUELOS DISPONIBLES POR AEROLINEAS
	MUESTRA UNA TABLA POR CADA AEROLINEA CON LOS VUELOS QUE SE TIENEN
	DISPONIBLES, HACIENDO USO DEL generadorDeTablas. */
	static void mostrarVuelosPorAerolineas() {
		ArrayList<Aerolinea> aerolineasDisponibles = Aerolinea.getAerolineas();
		for(Aerolinea i:aerolineasDisponibles){
			for(Vuelo j: i.getVuelos()){
				j.setEstaCompleto(j.getAeronave().cambiarEstado());
			}
		}
		generadorDeTablas.mostrarTablaDeVuelosDisponiblesPorAerolineas(aerolineasDisponibles);
	}

	/* CASE 2 MAIN: GENERAR TIQUETE DE COMPRA DE VUELO
	EL METODO PERMITE GENERAR UN TIQUETE DE COMPRA DE UN VUELO AL BUSCAR POR DESTINO O POR DESTINO Y FECHA
	LUEGO DE ELEGIR UN VUELO SE TOMAN LOS DATOS DEL PASAJERO Y SE ELIGE UNA SILLA EN LA AERONAVE
	AL FINAL SE IMPRIME UN RESUMEN DE LA COMPRA. */
	static void generarTiquete() {
		for(Aerolinea i: Aerolinea.getAerolineas()){
			for(Vuelo j: i.getVuelos()){
				j.setEstaCompleto(j.getAeronave().cambiarEstado());
			}
		}
		System.out.println("Quieres buscar un vuelo por:");
		System.out.println("1. Destino");
		System.out.println("2. Destino y fecha");
		System.out.println("3. Regresar");
		int opcion = sc.nextInt();
		while (opcion != 1 & opcion != 2 & opcion != 3) {
			System.out.println("Por favor ingresa una opcion valida");
			opcion = sc.nextInt();
		}

		if (opcion == 1) {
			System.out.println("Por favor ingrese un destino:");
			String destino_1 = sc.next();
			boolean hayVuelos = consultarVuelosPorDestino(destino_1);
			if (!hayVuelos) {
				return;
			}
		} else if (opcion == 2) {
			System.out.println("Por favor ingrese un destino");
			String destino_2 = sc.next();
			System.out.println("Por favor ingrese una fecha (dd-mm-aaaa):");
			String fecha_2 = sc.next();
			boolean hayVuelos = consultarVuelosPorDestinoYFecha(destino_2, fecha_2);
			if (!hayVuelos) {
				return;
			}
		} else {
			return;
		}
		System.out.println();
		System.out.println("Por favor ingrese el nombre de la aerolinea con la que desea viajar");
		String nombre_aerolinea = sc.next();
		Aerolinea aerolinea = Aerolinea.buscarAerolineaPorNombre(nombre_aerolinea);

		while (aerolinea == null) {
			System.out.println("Por favor ingrese un nombre valido");
			nombre_aerolinea = sc.next();
			aerolinea = Aerolinea.buscarAerolineaPorNombre(nombre_aerolinea);
		}

		System.out.println("Por favor ingrese el ID del vuelo que desea comprar");
		int ID = sc.nextInt();
		Vuelo vuelo = aerolinea.buscarVueloPorID(aerolinea.getVuelos(), ID);
		while (vuelo == null) {
			System.out.println("Por favor ingrese un ID valido");
			ID = sc.nextInt();
			vuelo = aerolinea.buscarVueloPorID(aerolinea.getVuelos(), ID);
		}

		double ID_tiquete = 100 + Math.random() * 900; // DEVUELVE UN NUMERO ALEATORIO DE 3 CIFRAS
		while (Aerolinea.BuscarTiquete((int) ID_tiquete) != null) {
			ID_tiquete = 100 + Math.random() * 900;
		}

		// SECUENCIA DE PASOS PARA ELEGIR UNA SILLA
		System.out.println("Que tipo de silla desea comprar?");
		Silla silla = elegirSilla(vuelo);
		if (silla == null) {
			System.out.println("Lo sentimos no se encuentran sillas disponibles con esas caracteristicas\n");
			return;
		}
		Tiquete tiquete = new Tiquete((int) ID_tiquete, vuelo.getPrecio(), vuelo);
		tiquete.setSilla(silla);

		// TOMAR DATOS DEL PASAJERO
		System.out.println("DATOS DEL PASAJERO:");
		System.out.println("Ingrese el nombre:");
		String nombre = sc.next();
		System.out.println("Ingrese su edad:");
		int edad = sc.nextInt();
		System.out.println("Ingrese el numero de su pasaporte:");
		String pasaporte = sc.next();
		System.out.println("Ingrese un e-mail");
		String correo = sc.next();

		//SE CREA EL OBJETO PASAJERO Y SE LE ASIGNA AL TIQUETE GENERADO EN EL METODO
		Pasajero pasajero = new Pasajero(pasaporte, nombre, tiquete, edad, correo);

		// IMPRIME RESUMEN DE LA COMPRA
		tiquete.asignarPrecio();
		System.out.println(tiquete);

	}

	/* CASE 3 MAIN: AGREGAR ALOJAMIENTO EN EL DESTINO DEL VUELO COMPRADO
	EL METODO PERMITE AGREGAR UN ALOJAMIENTO A UN TIQUETE COMPRADO PREVIAMENTE, VERIFICANDO QUE NO SE TENGA UN ALOJAMIENTO COMPRADO
	NI SE QUIERA AGREGAR UN ALOJAMIENTO EN UNA LOCACION DISTINTA A LA DEL DESTINO ASOCIADO AL TIQUETE.
	AL INGRESAR EL NOMBRE DEL ALOJAMIENTO QUE SE DESEA AGREGAR SE SOLICITA EL NUMERO DE DIAS QUE SE QUIERE QUEDAR
	PARA RECALCULAR EL PRECIO DEL TIQUETE, Y AL FINAL MOSTRAR EL RESUMEN DE LA COMPRA. */
	static void agregarAlojamiento() {
		System.out.println("Deseas agregar un alojamiento a tu compra?");
		System.out.println("Por favor ingresa el ID del tiquete que se genero al comprar su vuelo:");
		int tiqueteID = sc.nextInt();
		Tiquete tiquete_solicitado = Aerolinea.BuscarTiquete(tiqueteID);

		if (tiquete_solicitado == null) {
			System.out.println("Lo sentimos, no tenemos un tiquete identificado con ese ID");
			System.out.println();
		}else if(tiquete_solicitado.getAlojamiento() != null) {
			System.out.println("El tiquete ya posee un alojamiento, si quiere cambiarlo hagalo desde la opcion 4.\n");
			return;
		} else {
			String destino = tiquete_solicitado.getVuelo().getDestino();
			boolean hayAlojamientos = mostrarAlojamientosPorUbicacion(destino); //ESTE METODO SE DETALLA MAS ABAJO
			if (!hayAlojamientos) {
				return;
			}
			System.out.println("Por favor ingresa el nombre del alojamiento que desea anadir a su compra:");
			String alojamiento = sc.next();
			Alojamiento alojamiento_solicitado = Alojamiento.buscarAlojamientoPorNombre(alojamiento);
			if (alojamiento_solicitado == null) {
				System.out.println("Lo sentimos, no tenemos un alojamiento con ese nombre");
				System.out.println();
			}else if(!alojamiento_solicitado.getLocacion().equalsIgnoreCase(destino) ){
				System.out.println("Lo sentimos, no tenemos un alojamiento con ese nombre en esa locacion\n");
				return; }
			else {
				System.out.println("Cuantos dias desea quedarse en el alojamiento?");
				int num_dias = sc.nextInt();
				tiquete_solicitado.setAlojamiento(alojamiento_solicitado);
				tiquete_solicitado.asignarPrecio(num_dias);

				System.out.println("Perfecto! el alojamiento " + alojamiento_solicitado.getNombre()
						+ " se ha agregado correctamente a su tiquete de compra.");
						System.out.println();
						System.out.println(tiquete_solicitado);
					}
				}
			}
		
			/* CASE 4 MAIN: MODIFICAR TIQUETE COMPRADO
			NOS PERMITE MODIFICAR EL ALOJAMIENTO Y LA SILLA DE UN TIQUETE
			PRIMERO SOLICITANDO UN ID DE TIQUETE Y VERIFICAR QUE SI EXISTE,
			LUEGO CON UN SWITCH LE PRESENTADOS LAS 2 OPCIONES MODIFICAR ALOJAMIENTO O MODIFICAR SILLA
			Y SEGUN LO QUE ESCOJA EJECUTAREMOS EL METODO modificarAlojamiento o modificarSilla. */
			static void modificarTiquete() {
				System.out.println("Ingrese el ID del tiquete que desea modificar.");
				int ID = sc.nextInt();
				Tiquete tiquete = Aerolinea.BuscarTiquete(ID);
				if (tiquete == null) {
					System.out.println("El ID ingresado no se encuentra\n");
				} else {
					System.out.println("Que aspectos de su tiquete desea modificar?");
					System.out.println("1: Modificar alojamiento");
					System.out.println("2: Modificar Silla");
		
					int opcion = sc.nextInt();
		
					switch (opcion) {
		
						case 1:
							int dias = modificarAlojamiento(tiquete);
							if (dias > 0) {
								tiquete.asignarPrecio(dias);
								System.out.println(tiquete);
							}
							break;
						case 2:
							modificarSilla(tiquete);
					}
				}
			}
		
			/* METODOS DE MODIFICAR TIQUETE
		
			ESTE METODO RECIBE UN TIQUETE AL CUAL SE LE VA A MODIFICAR EL ATRIBUTO SILLA:
			LO HACE CAMBIANDO EL ATRIBUTO estaDisponible DE SU SILLA ACTUAL A true Y
			ASIGNANDO OTRA SILLA HACIENDO USO DEL METODO elegirSilla: */
			private static void modificarSilla(Tiquete tiquete) {
		
				System.out.println("A que tipo de silla desea cambiar?");
				Silla silla = elegirSilla(tiquete.getVuelo());
				if (silla == null) {
					System.out.println("Lo sentimos no se encuentran sillas disponibles con esas caracteristicas\n");
					return;
				}
				tiquete.getSilla().setEstado(true);
				tiquete.setSilla(silla);
		
				System.out.println("#####################################");
				System.out.println("SU SILLA HA SIDO MODIFICADA CON EXITO");
				System.out.println("#####################################\n");
				tiquete.asignarPrecio();
				System.out.println(tiquete);
		
			}
		
			/* ESTE METODO RECIBE UN TIQUETE AL CUAL SE LE VA A MODIFICAR EL ATRIBUTO ALOJAMIENTO (DEBE DE TENER UNO YA ASIGANDO
			EN CASO CONTRARIO NO LE PERMITITRA CONTINUAR Y LO REGRESARA AL MENU DE ADMINISTRADOR )
			SI SI POSEE UN ALOJAMIENTO, EXTRAERA EL DESTINO DEL VUELO DEL TIQUETE E IMPRIMIRA UNA TABLA CON LOS ALOJAMIENTOS
			QUE POSEEN UNA LOCACION IGUAL A ESTE, LUEGO RECIBE EL NOMBRE DEL ALOJAMIENTO QUE DESEE Y BUSCARA UN ALOJAMIENTO
			POR ESE NOMBRE Y EN ESA LOCACION EN CASO DE ENCONTRARLO SE LO ASIGNARA AL ATRIBUTO alojamiento DEL TIQUETE. */
			private static int modificarAlojamiento(Tiquete tiquete_solicitado) {
				if (tiquete_solicitado.getAlojamiento() == null) {
					System.out.println("Aun no tiene un alojamiento asociado a su tiquete, puede agregar uno en la opcion 3.");
					System.out.println();
					return 0;
				}
				String destino = tiquete_solicitado.getVuelo().getDestino();
				mostrarAlojamientosPorUbicacion(destino);
				System.out.println("Por favor ingresa el nombre del alojamiento al que desea cambiar");
				String alojamiento = sc.next();
				Alojamiento alojamiento_solicitado = Alojamiento.buscarAlojamientoPorNombre(alojamiento);
				if (alojamiento_solicitado == null) {
					System.out.println("Lo sentimos, no tenemos un alojamiento con ese nombre\n");
					return -1;
				}else if(!alojamiento_solicitado.getLocacion().equals(destino) ){
					System.out.println("Lo sentimos, no tenemos un alojamiento con ese nombre en esa locacion\n");
					return -1;
		
				}else {
					System.out.println("Por favor ingrese el numero de dias que se va a quedar en el alojamiento");
					int dias = sc.nextInt();
					tiquete_solicitado.setAlojamiento(alojamiento_solicitado);
					System.out.println("Perfecto! su alojamiento ha sido modificado a " + alojamiento_solicitado.getNombre()
							+ " exitosamente.");
					System.out.println();
					return dias;
				}
			}
		
			/* CASE 5 MAIN: OPCIONES DE ADMINISTRADOR
			 EN ESTE MENU PARA EL ADMINISTRADOR VAN A INTERACTUAR TODAS LAS CLASES PARA PERMITIR
			 FUNCIONALIDADES ESPECIFICAS PARA CONTROLAR LOS VUELOS Y LOS ALOJAMIENTOS. */
			static void opcionesAdministrador() {
		
				int opcion;
				do {
					System.out.println();
					System.out.println("Que opcion desea realizar como administrador?");
					System.out.println("1. Listar Pasajeros.");
					System.out.println("2. Agregar Vuelo.");
					System.out.println("3. Cancelar vuelo.");
					System.out.println("4. Retirar un avion.");
					System.out.println("5. Agregar Alojamiento.");
					System.out.println("6. Eliminar Alojamiento.");
					System.out.println("7. Agregar Aerolinea");
					System.out.println("8. Eliminar Aerolinea.");
					System.out.println("9. Agregar una aerolinea con vuelos");
					System.out.println("10. Salir del administrador.");
					System.out.println("Por favor escoja una opcion: ");
		
					opcion = sc.nextInt();
		
					switch (opcion) {
						case 1:
							listarPasajeros();
							break;
						case 2:
							agregarNuevoVuelo();
							break;
						case 3:
							cancelarVuelos();
							break;
						case 4:
							retirarAvion();
							break;
						case 5:
							nuevoAlojamiento();
							break;
						case 6:
							retirarAlojamiento();
							break;
						case 7:
							agregarAerolinea();
							break;
						case 8:
							retirarAerolinea();
							break;
						case 9:
							agregarAerolineaConVuelos();
							break;
						case 10:
						salirDelAdministrador();
						break;
				}
			} while (opcion != 10);
		}
	
		// METODOS DE LAS OPCIONES DE ADMINISTRADOR
	
		/* CASE 1: ESTE CÓDIGO PERMITE AL USUARIO LISTAR LOS PASAJEROS DE UN VUELO DE UNA AEROLÍNEA ESPECÍFICA. PRIMERO,
		 MUESTRA TODAS LAS AEROLÍNEAS DISPONIBLES Y SOLICITA AL USUARIO QUE INGRESE EL NOMBRE DE UNA AEROLÍNEA. LUEGO,
		 PIDE UN ID DE VUELO Y MUESTRA LA LISTA DE PASAJEROS ASOCIADOS A ESE VUELO. SI NO SE ENCUENTRAN PASAJEROS O EL VUELO
		 NO EXISTE, INFORMA AL USUARIO Y LE PERMITE INTENTARLO DE NUEVO. EL PROCESO SE REPITE HASTA QUE EL USUARIO INGRESE
		 UNA AEROLÍNEA Y UN VUELO VÁLIDOS. */
		private static void listarPasajeros() {
			ArrayList<Aerolinea> aerolineas = Aerolinea.getAerolineas();
			generadorDeTablas.mostrarTablaDeVuelosPorAerolineas(aerolineas);
			Aerolinea ae=null;
			do {
				System.out.println();
				System.out.println("Ingrese el nombre de la aerolinea: ");
				ae=Aerolinea.buscarAerolineaPorNombre(sc.next());
				System.out.println();
				if(ae!=null){
					ArrayList<Tiquete> tiquetes = new ArrayList<Tiquete>();
					Vuelo vuelo = null;
					int IDBusqueda = 0;
					do {
						System.out.println("Ingrese el ID del vuelo: ");
						IDBusqueda = sc.nextInt();
						System.out.println();
						vuelo = ae.buscarVueloPorID(ae.getVuelos(), IDBusqueda);
						if (vuelo == null) {
							System.out.println("No tenemos vuelos con ese ID.\n");
						}
					} while (vuelo==null);
	
					tiquetes = vuelo.getTiquetes();
					System.out.println("LISTA DE PASAJEROS PARA EL VUELO " + IDBusqueda);
	
					if (tiquetes.size() == 0) {
						System.out.println("El vuelo aun no tiene pasajeros asociados \n");
						System.out.println();
					} else {
						generadorDeTablas.mostrarTablaDePasajeros(tiquetes);
					}
					}
				else{
					System.out.println("No tenemos una aerolinea con ese nombre.");
				}
			} while (ae==null);
			}
	
		/* CASE 2: ESTE CÓDIGO PERMITE AL USUARIO AGREGAR UN NUEVO VUELO A UNA AEROLÍNEA EXISTENTE. PRIMERO, SE MUESTRA
		UNA LISTA DE AEROLÍNEAS DISPONIBLES Y SE SOLICITA AL USUARIO INGRESAR EL NOMBRE DE UNA AEROLÍNEA.
		SI LA AEROLÍNEA NO EXISTE, EL PROCESO SE REPITE HASTA QUE SE INGRESE UNA AEROLÍNEA VÁLIDA. LUEGO, SE SOLICITA UN ID
		DE VUELO DE 3 CIFRAS Y SE VALIDA QUE NO ESTÉ EN USO Y QUE TENGA LA LONGITUD CORRECTA. A CONTINUACIÓN, SE SOLICITAN
		VARIOS DATOS DEL VUELO, COMO EL PRECIO, ORIGEN, DESTINO, DISTANCIA, FECHA Y HORA DE SALIDA. EL USUARIO TAMBIÉN DEBE
		SELECCIONAR EL TIPO DE AERONAVE (AVIÓN O AVIONETA) Y SE CREA EL OBJETO CORRESPONDIENTE. FINALMENTE, SE REGISTRA EL
		VUELO Y SE MUESTRA UN MENSAJE DE CONFIRMACIÓN. SI EL USUARIO INGRESA UN TIPO DE AERONAVE NO VÁLIDO, SE INFORMA QUE
		NO SE MANEJA ESE TIPO DE AERONAVE.*/
		private static void agregarNuevoVuelo() {
			ArrayList<Aerolinea> aerolineas = Aerolinea.getAerolineas();
			System.out.println("AGREGAR NUEVO VUELO \n");
			generadorDeTablas.mostrarTablaDeAerolineas(aerolineas);
			System.out.println("Ingrese el nombre de la aerolinea para agregar vuelo\n");
			String nombreAerolinea = sc.next();
	
			Aerolinea aerolinea_busqueda = Aerolinea.buscarAerolineaPorNombre(nombreAerolinea);
	
			while (aerolinea_busqueda == null) {
				System.out.println("\nESA AEROLINEA NO EXISTE");
				generadorDeTablas.mostrarTablaDeAerolineas(aerolineas);
				System.out.println("Ingrese un nombre del listado anterior\n");
				String nombreAerolinean = sc.next();
				aerolinea_busqueda = Aerolinea.buscarAerolineaPorNombre(nombreAerolinean);
			}
			System.out.println();
	
			System.out.println("Ingrese el ID del nuevo vuelo (3 cifras):");
			int iD = sc.nextInt();
			while (Integer.toString(iD).length() != 3) {
				System.out.println("Por favor ingrese un ID de 3 cifras.");
				iD = sc.nextInt();
			}
			while (aerolinea_busqueda.buscarVueloPorID(aerolinea_busqueda.getVuelos(), iD) != null) {
				System.out.println("El ID que ingreso ya esta en uso, por favor ingrese uno distinto.");
				iD = sc.nextInt();
			}
	
			System.out.println("\nIngrese el precio:");
			int precio = sc.nextInt();
			System.out.println();
	
			System.out.println("Ingrese el origen:");
			String origen = sc.next();
			System.out.println();
	
			System.out.println("Ingrese el destino:");
			String destino = sc.next();
			System.out.println();
	
			System.out.println("Ingrese la distancia (KM):");
			double distancia = sc.nextDouble();
			System.out.println();
	
			System.out.println("Ingrese fecha de salida (DD-MM-AAAA):");
			String fechaSalida = sc.next();
			System.out.println();
	
			System.out.println("Ingrese hora de salida (24:00):");
			String horaSalida = sc.next();
			System.out.println();
	
			System.out.println("Que tipo de aeronave es?");
			System.out.println("Ingrese 1 para avion" + "\n" + "Ingrese 2 para avioneta");
			int aeronave = sc.nextInt();
	
			if (aeronave == 1) {