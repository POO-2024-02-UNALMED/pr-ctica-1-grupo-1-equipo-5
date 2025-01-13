/* CLASE SERIALIZADOR QUE PERMITE LA PERSISTENCIA DE DATOS
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package baseDatos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import gestorAplicacion.alojamiento.Alojamiento;
import gestorAplicacion.adminVuelos.Aerolinea;

// SE ENCARGA DE GUARDAR EN ARCHIVOS EL ESTADO DE TODOS LOS OBJETOS DEL MODELO CUANDO EL SISTEMA SE VA A CERRAR
public class Serializador {

	// File.separator SE USA PARA DIVIDIR LA RUTA A UN ARCHIVO ESPECIFICO SEGUN EL SISTEMA OPERATIVO
	// LA CLASE File SE USA PARA EL MANEJO DE ARCHIVOS
	private static File rutaArchivosTemp = new File("src"+File.separator+"basedatos"+File.separator+"temp");

	// SERIALIZAMOS LA LISTA DE AEROLINEAS Y ALOJAMIENTOS
	public static void serializar() {

		// DECLARAMOS LOS PUNTEROS FileOutputStream y ObjectOutputStream QUE PERMITIRAN LA SERILIZACION DE LOS OBJETOS
		FileOutputStream rutaArchivo;
		ObjectOutputStream fichero_objeto;
		// CREAMOS UNA LISTA PARA ALMACENAR LOS archivos.txt  QUE ESTAN EN  rutaArchivosTemp
		File[] ficheros = rutaArchivosTemp.listFiles();
		// DECLARAMOS UN PUNTERO DE TIPO PrintWriter QUE NOS PERMITE CREAR UN ARCHIVO Y ESCRBIR DATOS DENTRO DEL ARCHIVO
		PrintWriter pw;

		// CON PRINTWRITER BORRAMOS EL CONTENIDO PARA EVITAR SOBREESCRITURAS
		for (File archivo : ficheros) {
			try {
				// BORRA LO QUE HAY EN EL ARCHIVO QUE LE PASAMOS COMO PARAMETRO (SEGUN LOS VISTO EN CLASE)
				pw = new PrintWriter(archivo);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		// RECORREMOS LOS ARCHIVOS QUE ESTAN EN LA LISTA ficheros
		for (File archivo1 : ficheros) {

			// VERIFICA SI LA RUTA DEL ARCHIVO CONTIENE LA PALABRA Aerolineas(DONDE ALMACENAREMOS LA LISTA DE AEROLINEAS Y TODA SU INFO)
			if (archivo1.getAbsolutePath().contains("Aerolineas")) {
				try {
					// APORTA LA INFORMACION PARA IDENTIFICAR EL FICHERO
					rutaArchivo = new FileOutputStream(archivo1);
					// PROCESA OBJETOS JAVA Y SE VINCULA A UN OBJETO DE LA CLASE FileOutputStream
					fichero_objeto = new ObjectOutputStream(rutaArchivo);
					// CODIFICA LA LISTA QUE CONTIENE LAS AEROLINEAS Y LAS GUARDA EN EL ARCHIVO AL QUE ESTA VINCULADO fichero_objeto
					fichero_objeto.writeObject(Aerolinea.getAerolineas());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block e.printStackTrace();
					e.printStackTrace();
				}

			// VERIFICA SI LA RUTA DEL ARCHIVO CONTIENE LA PALABRA Alojamientos(DONDE ALMACENAREMOS LA LISTA DE ALOJAMIENTOS Y TODA SU INFO)
			// SE COMPORTA DE IGUAL FORMA QUE EL ANTERIOR, PERO SERIALIZANDO UNA LISTA DE ALOJAMIENTOS
			}else if(archivo1.getAbsolutePath().contains("Alojamientos")) {
				try {
					rutaArchivo = new FileOutputStream(archivo1);
					fichero_objeto = new ObjectOutputStream(rutaArchivo);
					fichero_objeto.writeObject(Alojamiento.getAlojamientos());
				}catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block e.printStackTrace();
					e.printStackTrace();
				}
			}
		}
	}
}