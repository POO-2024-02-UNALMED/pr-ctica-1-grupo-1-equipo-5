/* ENUM
AUTORES: JHONEYKER DELGADO, EMMANUEL VALENCIA, SIMON GUARIN, CAMILO MIRANDA Y JACOBO LEAL. */
package gestorAplicacion.hangar;

// CONTIENE LAS UNICAS DOS CLASES PARA LOS PASAJEROS CON EL PRECIO DE CADA UNO.
public enum Clase {
	ECONOMICA(10000),EJECUTIVA(70000);
	private int precio;
	private Clase(int precio) {
		this.precio = precio;
	}
	public int getPrecio() {
		return this.precio;
	}
	public void setPrecio(int precio) {
		this.precio =precio;
	}
}
