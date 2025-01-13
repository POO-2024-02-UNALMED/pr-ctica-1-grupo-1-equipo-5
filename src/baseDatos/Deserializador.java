/* CLASE DESERIALIZADOR QUE PERMITE LA LECTURA DE LOS OBJETOS GUARDADOS SIMULANDO UNA BASE DE DATOS
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package baseDatos;

import java.io.*;
import java.util.ArrayList;

import gestorAplicacion.alojamiento.Alojamiento;
import gestorAplicacion.adminVuelos.*;
import gestorAplicacion.hangar.*;

// SE ENCARGA DE CARGAR DESDE LOS ARCHIVOS TEMP EL ESTADO DE TODOS LOS OBJETOS GUARDADOS DEL SISTEMA
public class Deserializador {

	/* File.separator SE USA PARA DIVIDIR LA RUTA A UN ARCHIVO ESPECIFICO SEGUN EL SISTEMA OPERATIVO
	LA CLASE File SE USA PARA EL MANEJO DE ARCHIVOS */
	private static File rutaArchivosTemp = new File("src"+File.separator+"basedatos"+File.separator+"temp");

	// DESERLIZAMOS LA LISTA DE AEROLINEAS Y ALOJAMIENTOS
	public static void deserializar() {
		// CREAMOS UNA LISTA PARA ALMACENAR LOS archivos.txt  QUE ESTAN EN  rutaArchivosTemp
		File[] ficheros = rutaArchivosTemp.listFiles();
		// DECLARAMOS LOS PUNTEROS FileInputStream y ObjectInputSream QUE PERMITIRAN LA DESERIALIZACION DE LOS OBJETOS
		FileInputStream archivo;
		ObjectInputStream guardado;

		// RECORREMOS LOS ARCHIVOS QUE ESTAN EN LA LISTA ficheros
		for (File file : ficheros) {

			// VERIFICA SI LA RUTA DEL ARCHIVO CONTIENE LA PALABRA Aerolineas(DE DONDE EXTRAEREMOS LA LISTA  DE AEROLINEAS Y TODA SU INFO)
			if (file.getAbsolutePath().contains("Aerolineas")) {
				try {
					// SE LEE EL ARCHIVO Aerolineas.txt DE LA LISTA ficheros
					archivo = new FileInputStream(file);
					// PROCESA LOS DATOS CONTENIDOS EN EL OBJETO archivo Y SE VINCULA A EL
					guardado = new ObjectInputStream(archivo);
					/* SE LEEN LOS OBJETOS EN EL MISMO ORDEN EN QUE HABIAN SIDO ESCRITOS Y
					SE HACE EL CASTEO DEL APUNTADOR OBJECT A ArrayList<Aerolinea>
					ESTE ArrayList DE AEROLINEAS SE ASIGNA AL ATRIBUTO DE CLASE Aerolineas DE LA CLASE Aerolinea */
					Aerolinea.setAerolineas((ArrayList<Aerolinea>) guardado.readObject());
				}catch(FileNotFoundException e) {
					e.printStackTrace();
				}catch(IOException e) {
					e.printStackTrace();
				}catch(ClassNotFoundException e) {
					e.printStackTrace();;
				}

			/* VERIFICA SI LA RUTA DEL ARCHIVO CONTIENE LA PALABRA Alojamientos(DE DONDE EXTRAEREMOS LA LISTA DE ALOJAMIENTOS Y TODA SU INFO)
			SE COMPORTA DE IGUAL FORMA QUE EL ANTERIOR, PERO DESERIALIZANDO UNA LISTA DE ALOJAMIENTOS */
			}else if (file.getAbsolutePath().contains("Alojamientos")) {
				try {
				archivo = new FileInputStream(file);
				guardado = new ObjectInputStream(archivo);
				Alojamiento.setAlojamientos((ArrayList<Alojamiento>) guardado.readObject());
				}catch(FileNotFoundException e) {
					e.printStackTrace();
				}catch(IOException e) {
					e.printStackTrace();
				}catch(ClassNotFoundException e) {
					e.printStackTrace();;
				}
			}
		}
	}
}
