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
				System.out.println("Ingrese el nombre del avion:");
			String nombreAvion = sc.next();
			System.out.println();

			Aeronave avion = new Avion(nombreAvion, Aerolinea.buscarAerolineaPorNombre(nombreAerolinea));
			avion.asignarParamatrosSilla(avion, 1);
			Vuelo vuelo = new Vuelo(iD, precio, origen, destino, avion, distancia, fechaSalida, horaSalida);
			System.out.println("#######################################");
			System.out.println("SU VUELO SE HA REGISTRADO CORRECTAMENTE");
			System.out.println("#######################################\n");

		} else if (aeronave == 2) {
			System.out.println("INGRESE EL NOMBRE DE LA AVIONETA:");
			String nombreAvioneta = sc.next();
			System.out.println();
			Aeronave avioneta = new Avioneta(nombreAvioneta, Aerolinea.buscarAerolineaPorNombre(nombreAerolinea));
			avioneta.asignarParamatrosSilla(avioneta, 2);
			Vuelo vuelo = new Vuelo(iD, precio, origen, destino, avioneta, distancia, fechaSalida, horaSalida);
			System.out.println("#######################################");
			System.out.println("SU VUELO SE HA REGISTRADO CORRECTAMENTE");
			System.out.println("#######################################\n");
		} else {
			System.out.println("No manejamos ese tipo de aeronave");

		}
	}

	/* CASE 3: ESTE CÓDIGO PERMITE AL USUARIO CANCELAR UN VUELO EXISTENTE. PRIMERO, MUESTRA UNA LISTA DE LOS VUELOS
	DISPONIBLES AGRUPADOS POR AEROLÍNEAS. LUEGO, SE SOLICITA AL USUARIO EL NOMBRE DE UNA AEROLÍNEA Y EL ID DEL VUELO
	QUE DESEA CANCELAR. SI SE ENCUENTRA UNA AEROLÍNEA Y UN VUELO CON EL ID PROPORCIONADO, EL VUELO SE CANCELA Y SE
	MUESTRA UN MENSAJE DE CONFIRMACIÓN. SI NO SE ENCUENTRA EL VUELO O LA AEROLÍNEA, SE INFORMA AL USUARIO QUE NO
	EXISTE NINGÚN VUELO O AEROLÍNEA CON ESE NOMBRE O ID. */
	public static void cancelarVuelos() {
		System.out.println("Estos son los vuelos que tenemos:\n");
		ArrayList<Aerolinea> aerolineas = Aerolinea.getAerolineas();
		generadorDeTablas.mostrarTablaDeVuelosPorAerolineas(aerolineas);
		System.out.println("Ingrese el nombre de la aerolinea del vuelo a eliminar:");
		String nombre = sc.next();
		Aerolinea aero= Aerolinea.buscarAerolineaPorNombre(nombre);
		if(aero!=null){
		System.out.println("Ingrese el ID del vuelo a eliminar:");
		int id = sc.nextInt();
			if (aero.buscarVueloPorID(aero.getVuelos(), id) != null) {
				aero.cancelarVuelo(id);
				System.out.println("El vuelo se ha eliminado correctamente.");
				return;
			}
			else{
				System.out.println("No tenemos un vuelo identificado con ese ID \n");
			}
		}else{
			System.out.println("No tenemos una aerolinea identificada con ese nombre \n");
		}
	}

	/* CASE 4: ESTE CÓDIGO PERMITE AL USUARIO RETIRAR UNA AERONAVE DESCOMPUESTA DE UNA AEROLÍNEA Y CANCELAR EL VUELO
	ASOCIADO A ELLA. PRIMERO, MUESTRA UNA LISTA DE AEROLÍNEAS Y SUS VUELOS ASOCIADOS, INCLUYENDO LOS NOMBRES DE LAS
	AERONAVES. LUEGO, SE SOLICITA AL USUARIO INGRESAR EL NOMBRE DE LA AERONAVE QUE DESEA RETIRAR. SI SE ENCUENTRA
	UNA AERONAVE CON EL NOMBRE INGRESADO, SE MARCA COMO "DESCOMPUESTA" Y SE CANCELA EL VUELO ASOCIADO. SI NO SE
	ENCUENTRA UNA AERONAVE CON ESE NOMBRE, SE INFORMA AL USUARIO QUE NO SE PUDO ENCONTRAR LA AERONAVE. */
	public static void retirarAvion() {
		ArrayList<Aerolinea> aerolineasDisponibles = Aerolinea.getAerolineas();
		for (int index = 0; index < aerolineasDisponibles.size(); index++) {
			ArrayList<Vuelo> vuelos = aerolineasDisponibles.get(index).getVuelos();
			System.out.println();
			System.out.println(aerolineasDisponibles.get(index).getNombre().toUpperCase());
			System.out.println();
			for (int j = 0; j < aerolineasDisponibles.get(index).getVuelos().size(); j++) {
				System.out.println(vuelos.get(j).getAeronave().getNombre());
			}
			System.out.println();
		}
		boolean aeronave_encontrada = false;
		System.out.println("Ingrese el nombre de la Aeronave que se desea retirar:");
		String nombre_aeronave = sc.next();
		for (int i = 0; i < aerolineasDisponibles.size(); i++) {
			Aerolinea aerolinea = aerolineasDisponibles.get(i);
			Vuelo vuelo = aerolinea.buscarVueloPorAeronave(aerolinea.getVuelos(), nombre_aeronave);
			if (vuelo != null) {
				vuelo.getAeronave().setDescompuesto(true);
				aerolinea.cancelarVuelo(vuelo.getID());
				System.out.println("Se ha retirado la aeronave descompuesta y el vuelo asociado a este.");
				System.out.println();
				aeronave_encontrada = true;
				break;
			}
		}
		if (!aeronave_encontrada) {
			System.out.println("Lo sentimos, no encontramos una aeronave asociada al nombre que ingreso.");
			System.out.println();
		}
	}

	/* CASE 5: ESTE CÓDIGO PERMITE AL USUARIO AGREGAR UN NUEVO ALOJAMIENTO A UNA LISTA. PRIMERO, SE SOLICITAN LOS DATOS
	DEL ALOJAMIENTO, COMO EL NOMBRE, LA UBICACIÓN, EL PRECIO POR DÍA Y EL NÚMERO DE ESTRELLAS (DE 1 A 5). LUEGO, SE CREA
	UN OBJETO ALOJAMIENTO CON LOS DATOS INGRESADOS Y SE MUESTRA UN MENSAJE DE CONFIRMACIÓN INFORMANDO QUE EL ALOJAMIENTO
	SE HA AGREGADO CORRECTAMENTE A LA LISTA. */
	public static void nuevoAlojamiento()
	{
		System.out.println("Ingrese el nombre del alojamiento que desea agregar a nuestra lista:");
		String nombre = sc.next();
		System.out.println();

		System.out.println("Ingrese la locacion:");
		String locacion = sc.next();
		System.out.println();

		System.out.println("Ingrese el precio por dia:");
		long precio = sc.nextLong();
		System.out.println();

		System.out.println("Ingrese el numero de estrellas (1-5):");
		int estrellas = sc.nextInt();
		System.out.println();

		Alojamiento nuevoAlojamiento = new Alojamiento(nombre, locacion, precio, estrellas);
		System.out.println("Perfecto! El alojamiento " + nuevoAlojamiento.getNombre() + " se ha agregado a nuestra lista.");
		}

	/* CASE 6: ESTE CÓDIGO PERMITE AL USUARIO RETIRAR UN ALOJAMIENTO DE UNA LISTA. PRIMERO, MUESTRA UNA TABLA CON LOS
	ALOJAMIENTOS DISPONIBLES Y LUEGO SOLICITA AL USUARIO EL NOMBRE DEL ALOJAMIENTO QUE DESEA RETIRAR. SI EL ALOJAMIENTO
	EXISTE EN LA LISTA, SE ELIMINA Y SE MUESTRA UN MENSAJE DE CONFIRMACIÓN. SI NO SE ENCUENTRA EL ALOJAMIENTO CON EL
	NOMBRE INGRESADO, SE INFORMA AL USUARIO QUE NO EXISTE UN ALOJAMIENTO CON ESE NOMBRE. */
	public static void retirarAlojamiento()
	{
		System.out.println("Estos son los alojamientos que tenemos asociados:");
		generadorDeTablas.mostrarTablaDeAlojamientos(Alojamiento.getAlojamientos());

		System.out.println("Ingrese el nombre del alojamiento que desea retirar de nuestra lista:");
		String nombre = sc.next();

		if (Alojamiento.buscarAlojamientoPorNombre(nombre) != null)
		{
			for (int i = 0; i < Alojamiento.getAlojamientos().size(); i++ )
			{
				if (Alojamiento.getAlojamientos().get(i).getNombre().equalsIgnoreCase(nombre))
				{
					Alojamiento.getAlojamientos().remove(i);
					System.out.println("El alojamiento " + nombre + " se ha eliminado correctamente.");
					System.out.println();
				}
			}
		}
		else
		{
			System.out.println("Lo sentimos, no tenemos un alojamiento con este nombre.");
			System.out.println();
		}
	}

	/* CASE 7: ESTE CÓDIGO PERMITE AL USUARIO AGREGAR UNA NUEVA AEROLÍNEA. PRIMERO, SE SOLICITA AL USUARIO EL NOMBRE
	DE LA AEROLÍNEA QUE DESEA AGREGAR Y SE VERIFICA SI YA EXISTE UNA AEROLÍNEA CON ESE NOMBRE. SI YA EXISTE, SE INFORMA
	AL USUARIO; DE LO CONTRARIO, SE CREA UNA NUEVA AEROLÍNEA. A CONTINUACIÓN, SE LE PREGUNTA AL USUARIO SI DESEA
	AGREGAR VUELOS A ESA AEROLÍNEA. SI ELIGE "SÍ", SE LLAMA AL MÉTODO agregarNuevoVuelo PARA AGREGAR VUELOS A LA NUEVA
	AEROLÍNEA. ESTE PROCESO SE REPITE HASTA QUE EL USUARIO ELIGA "NO". */
	private static void agregarAerolinea(){
		System.out.println("AGREGAR NUEVA AEROLINEA \n");
		System.out.println("Ingrese el nombre de la aerolinea que desea agregar:");
		String nombre = sc.next();
		Aerolinea i= Aerolinea.buscarAerolineaPorNombre(nombre);
		if (i!=null) {
			System.out.println("Ya existe una aerolinea con ese nombre.");
		}else{
			Aerolinea nuevAerolinea= new Aerolinea(nombre);
			int opcion;
			do {
				System.out.println("Desea agregar vuelos a alguna aerolinea:");
				System.out.println("1. SI");
				System.out.println("2. NO");
				opcion= sc.nextInt();
				if (opcion==1){
					agregarNuevoVuelo();
				}
			}while(opcion!=2);
		}
	}

	/* CASE 8: ESTE CÓDIGO PERMITE AL USUARIO RETIRAR UNA AEROLÍNEA DE LA LISTA. PRIMERO, SE MUESTRA UNA TABLA CON LAS AEROLÍNEAS
	DISPONIBLES Y SE SOLICITA AL USUARIO EL NOMBRE DE LA AEROLÍNEA QUE DESEA ELIMINAR. SI SE ENCUENTRA UNA AEROLÍNEA CON ESE NOMBRE,
	SE ELIMINA DE LA LISTA Y SE MUESTRA UN MENSAJE DE CONFIRMACIÓN. SI NO SE ENCUENTRA UNA AEROLÍNEA CON EL NOMBRE INGRESADO,
	SE INFORMA AL USUARIO QUE NO EXISTE UNA AEROLÍNEA CON ESE NOMBRE. */
	private static void retirarAerolinea(){
		generadorDeTablas.mostrarTablaDeAerolineas(Aerolinea.getAerolineas());
		System.out.println("RETIRAR UNA AEROLINEA \n");
		System.out.println("Ingrese el nombre de la aerolinea que desea retirar:");
		String nombre = sc.next();
		Aerolinea i= Aerolinea.buscarAerolineaPorNombre(nombre);
		if (i!=null) {
			for(int j = 0; j < Aerolinea.getAerolineas().size(); j++){
				if(Aerolinea.getAerolineas().get(j).getNombre()==i.getNombre()){
					Aerolinea.getAerolineas().remove(j);
				}
			}
			System.out.println("Se ha eliminado correctamente la aerolinea " +nombre);

		}else{
			System.out.println("No tenemos una aerolinea con este nombre.");
		}
	}

	/* CASE 9: ESTE CÓDIGO PERMITE AL USUARIO AGREGAR UNA NUEVA AEROLÍNEA JUNTO CON SUS VUELOS ASOCIADOS. PRIMERO,
	SE SOLICITA EL NOMBRE DE LA AEROLÍNEA Y SE VERIFICA QUE NO EXISTAN AEROLÍNEAS CON ESE NOMBRE. LUEGO, SE SOLICITAN
	LOS DATOS DE AL MENOS UN VUELO (ID, PRECIO, ORIGEN, DESTINO, DISTANCIA, FECHA Y HORA DE SALIDA). SE PIDE AL USUARIO
	QUE ESPECIFIQUE EL TIPO DE AERONAVE (AVIÓN O AVIONETA) Y SE CREA EL VUELO CORRESPONDIENTE. SI EL USUARIO DESEA
	AGREGAR MÁS VUELOS, EL PROCESO SE REPITE HASTA QUE EL USUARIO ELIGA NO AGREGAR MÁS. AL FINAL, SE CREA UNA NUEVA
	AEROLÍNEA CON LOS VUELOS INGRESADOS Y SE ASOCIA CADA VUELO CON SU AERONAVE Y AEROLÍNEA. SE MUESTRAN MENSAJES DE
	CONFIRMACIÓN A LO LARGO DEL PROCESO.*/
	private static void agregarAerolineaConVuelos(){
		ArrayList<Vuelo> vuelos=new ArrayList<Vuelo>();
		System.out.println("Por favor ingrese el nombre de la nueva aerolinea");
		String nombre = sc.next();
		Aerolinea nueva_Aerolinea= Aerolinea.buscarAerolineaPorNombre(nombre);
		while (nueva_Aerolinea !=null) {
			System.out.println("La Aerolinea ya existe por favor ingrese otro nombre");
			nombre=sc.next();
			nueva_Aerolinea=Aerolinea.buscarAerolineaPorNombre(nombre);
		}
		System.out.println("La Aerolinea debe tener por lo menos un vuelo");
		System.out.println();
		int opcio;
		do{System.out.println("Ingrese el ID del nuevo vuelo (3 cifras):");
		int iD = sc.nextInt();
		while (Integer.toString(iD).length() != 3) {
			System.out.println("Por favor ingrese un ID de 3 cifras.");
			iD = sc.nextInt();
		}
		Boolean esta=true;
		while (esta) {
			int conta=0;
			for(Vuelo vu: vuelos){
				if(vu.getID()==iD){
					conta+=1;
				}
			}
			if(conta==0){
				esta=false;
			}else{
				System.out.println("Este ID ya se encuentra registrado en un vuelo de esta aerolinea.");
				System.out.println("Ingrese otro ID por favor:");
				iD = sc.nextInt();
			}
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
			System.out.println("Ingrese el nombre del avion:");
			String nombreAvion = sc.next();
			System.out.println();

			Aeronave avion = new Avion(nombreAvion);
			avion.asignarParamatrosSilla(avion, 1);
			Vuelo vuelo = new Vuelo(iD, precio, origen, destino, avion, distancia, fechaSalida);
			vuelo.setHora_de_salida(horaSalida);
			vuelos.add(vuelo);
			System.out.println("#######################################");
			System.out.println("SU VUELO SE HA REGISTRADO CORRECTAMENTE");
			System.out.println("#######################################\n");

		} else if (aeronave == 2) {
			System.out.println("INGRESE EL NOMBRE DE LA AVIONETA:");
			String nombreAvioneta = sc.next();
			System.out.println();
			Aeronave avioneta = new Avioneta(nombreAvioneta);
			avioneta.asignarParamatrosSilla(avioneta, 2);
			Vuelo vuelo = new Vuelo(iD, precio, origen, destino, avioneta, distancia, fechaSalida);
			vuelo.setHora_de_salida(horaSalida);
			vuelos.add(vuelo);
			System.out.println("#######################################");
			System.out.println("SU VUELO SE HA REGISTRADO CORRECTAMENTE");
			System.out.println("#######################################\n");
		} else {
			System.out.println("No manejamos ese tipo de aeronave");

		}System.out.println("Desea agregar otro vuelo a esta aerolinea:");
		System.out.println("1. Si.");
		System.out.println("2. No.");
		opcio=sc.nextInt();
		}while(opcio!=2);
		nueva_Aerolinea=new Aerolinea(nombre, vuelos);
		for(Vuelo j:nueva_Aerolinea.getVuelos()){
			j.getAeronave().setAerolinea(nueva_Aerolinea);
		}
	}

	/* CASE 10: ESTE CÓDIGO MUESTRA UN MENSAJE DE DESPEDIDA CUANDO EL USUARIO DECIDE SALIR DE LAS OPCIONES DEL ADMINISTRADOR.
	SIMPLE Y DIRECTO, SE INFORMA AL USUARIO QUE SE HA FINALIZADO SU SESIÓN COMO ADMINISTRADOR. */
	private static void salirDelAdministrador() {
		System.out.println("Gracias por usar nuestras opciones de administrador! \n");
	}

	/* CASE 6 MAIN: ESTE CÓDIGO MUESTRA UN MENSAJE DE DESPEDIDA AL USUARIO Y GUARDA EL ESTADO DEL SISTEMA MEDIANTE UN MÉTODO DE
	SERIALIZACIÓN ANTES DE CERRAR EL PROGRAMA. SE LLAMA AL MÉTODO `Serializador.serializar()` PARA GUARDAR LOS DATOS Y LUEGO
	SE UTILIZA `System.exit(0)` PARA TERMINAR LA EJECUCIÓN DEL PROGRAMA DE MANERA CORRECTA. */
	private static void salirDelSistema() {
		System.out.println("Gracias por usar nuestro servicio!");
		Serializador.serializar();
		System.exit(0);
	}

/* METODOS AUXILIARES

	OPCION 1: CONSULTAR VUELO POR DESTINO
	ESTE METODO RECIBE COMO PARAMETRO UN DESTINO (STRING) Y RECORRE CADA AEROLINEA EJECUTANDO EL METODO DE AEROLINEA
	buscarVueloPorDestino() PARA ALMACENAR ESTOS VUELOS EN UNA LISTA Y MOSTRARLOS POR PANTALLA CON
	generadorDeTablas.mostrarTablaDeVuelos(). SI ENCONTRO AL MENOS UN VUELO EN ALGUNA AEROLINEA QUE TUVIERA ASOCIADO
	ESTE DESTINO RETORNA LA VARIABLE boolean HAYVUELOS CON EL VALOR true, DE LO CONTRARIO RETORNA false. */
	static boolean consultarVuelosPorDestino(String destino)
	{
		int c=0;
		boolean hayVuelos = false;

		ArrayList<Aerolinea> aerolineasDisponibles = Aerolinea.getAerolineas();
		for (int i = 0; i < aerolineasDisponibles.size(); i++)
		{
			Aerolinea aerolinea = aerolineasDisponibles.get(i);
			ArrayList<Vuelo> vuelosPorDestino = aerolinea.buscarVueloPorDestino(aerolinea.vuelosDisponibles(aerolinea.getVuelos()), destino);
			if (vuelosPorDestino.size() != 0)
			{if (c==0) {
				System.out.println();
				System.out.println("Estos son los vuelos disponibles hacia " + destino + " por nuestras aerolineas:" );
				c+=1;
			}
				generadorDeTablas.mostrarTablaDeVuelos(aerolinea, vuelosPorDestino);
				hayVuelos = true;
			}
		}