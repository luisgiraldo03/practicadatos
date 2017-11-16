package practicadatosi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Scanner;

public class ClasePrincipal {

    static ColeccionArchivos tabla;
    static BufferedReader leer;

    public static void main(String[] args) {
        leerArchivo("treeEtc.txt");
        try {
            menu();
        } catch (Exception e) {
        }
    }

    public static void menu() throws Exception {
        leer = new BufferedReader(new InputStreamReader(System.in));
        String opcion;
        do {
            System.out.println("MENÚ PRINCIPAL\n"
                    + "1. Buscar con el nombre de la carpeta o archivo\n"
                    + "2. Buscar con la ruta\n"
                    + "3. salir");
            opcion = leer.readLine();
            switch (opcion) {
                case "1":
                    menu1();
                    break;
                case "2":
                    menu2();
                    break;
            }

        } while (!"3".equals(opcion));

    }

    public static void menu1() {

        System.out.println("MENU DE BUSQUEDAS POR NOMBRE: BUSQUEDA GENERAL\n"
                + "Ingrese 1 para imprimir información de una carpeta o archivo\n"
                + "Ingrese 2 para imprimir los contenidos de una carpeta\n"
                + "Ingrese 3 para imprimir los contenidos de una carpeta por usuario\n"
                + "Ingrese 4 para imprimir los contenidos de una carpeta que pesen más de un tamaño dado\n"
                + "Ingrese 5 para imprimir las posibles rutas de una carpeta o archivo\n"
                + "Ingrese 0 para salir");
        String nombre, usuario, tamano;
        try {
            switch (leer.readLine()) {
                case "1":
                    System.out.println("Ingrese el nombre de la carpeta");
                    nombre = leer.readLine();
                    tabla.imprimirCarpetas(nombre);
                    System.out.println();
                    break;
                case "2":
                    System.out.println("Ingrese el nombre de la carpeta");
                    nombre = leer.readLine();
                    tabla.contenido(nombre);
                    System.out.println();
                    break;
                case "3":
                    System.out.println("Ingrese el nombre de la carpeta");
                    nombre = leer.readLine();
                    System.out.println("Ingrese el nombre del usuario");
                    usuario = leer.readLine();
                    tabla.contenidosUsuario(nombre, usuario);
                    System.out.println();
                    break;
                case "4":
                    System.out.println("Ingrese el nombre de la carpeta");
                    nombre = leer.readLine();
                    System.out.println("Ingrese el tamano");
                    tamano = leer.readLine();
                    tabla.contenidosMayores(nombre, tamano);
                    System.out.println();
                    break;
                case "5":
                    System.out.println("Ingrese el nombre de la carpeta");
                    nombre = leer.readLine();
                    tabla.direcciones(nombre);
                    System.out.println();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void menu2() {

        System.out.println("MENU DE BUSQUEDAS POR RUTA: BUSQUEDA ESPECIFICA\n"
                + "Ingrese 0 para volver al menu principal\n"
                + "Ingrese 1 para imprimir información de una carpeta o archivo\n"
                + "Ingrese 2 para imprimir los contenidos de una carpeta\n"
                + "Ingrese 3 para imprimir los contenidos de una carpeta por usuario\n"
                + "Ingrese 4 para imprimir los contenidos de una carpeta que pesen más de un tamaño dado\n");
        String nombre, usuario, tamano, direccion;
        GestionCarpeta carpeta;
        try {
            switch (leer.readLine()) {
                case "1":
                    System.out.println("Ingrese la direccion la carpeta");
                    direccion = leer.readLine();
                    nombre = direccion.substring(direccion.lastIndexOf("/") + 1);
                    carpeta = tabla.get(nombre, direccion);
                    System.out.println(carpeta);
                    System.out.println();
                    break;
                case "2":
                    System.out.println("Ingrese la direccion la carpeta");
                    direccion = leer.readLine();
                    nombre = direccion.substring(direccion.lastIndexOf("/") + 1);
                    carpeta = tabla.get(nombre, direccion);
                    System.out.println(carpeta.listarContenido());
                    System.out.println();
                    break;
                case "3":
                    System.out.println("Ingrese la direccion la carpeta");
                    direccion = leer.readLine();
                    nombre = direccion.substring(direccion.lastIndexOf("/") + 1);
                    carpeta = tabla.get(nombre, direccion);

                    System.out.println("Ingrese el usuario de la carpeta");
                    usuario = leer.readLine();
                    System.out.println(carpeta.listarUsuario(usuario));
                    System.out.println();
                    break;
                case "4":
                    System.out.println("Ingrese la direccion la carpeta");
                    direccion = leer.readLine();
                    nombre = direccion.substring(direccion.lastIndexOf("/") + 1);
                    carpeta = tabla.get(nombre, direccion);

                    System.out.println("Ingrese el tamano");
                    tamano = leer.readLine();
                    System.out.println(carpeta.listarMayor(tamano));
                    System.out.println();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * método que permite leer un archivo txt y hacer búsquedas acerca de este.
     * la primera linea es el directorio raiz. (en las direcciones no será
     * tomado enc cuenta)
     *
     */
    public static void leerArchivo(String nombreArchivo) {
        try {
            BufferedReader archivo = new BufferedReader(new FileReader(nombreArchivo));
            String nombreRaiz = archivo.readLine();
            tabla = new ColeccionArchivos();
            String linea;
            int nivel = 4;
            GestionCarpeta padre = new GestionCarpeta(null, "", nombreRaiz.substring(1), "", TipoArchivo.Carpeta);
            tabla.put(nombreRaiz, padre);
            GestionCarpeta ultimoAgregado = null;

            while ((linea = archivo.readLine()) != null) {
                Scanner leerLinea = new Scanner(linea);
                if (leerLinea.hasNext()) {
                    leerLinea.useDelimiter("[a-z]");
                    String actual = leerLinea.next();

                    int numNivel = actual.length() - 1;
                    if (numNivel > nivel) {
                        ultimoAgregado.cambiarCarpeta();
                        padre = ultimoAgregado;
                        nivel += 4;
                    } else if (numNivel < nivel) {
                        padre = padre.getPadre();
                        nivel -= 4;
                    }
                    leerLinea.reset();
                    String usuario = leerLinea.next();
                    StringBuilder tamano = new StringBuilder(leerLinea.next());
                    tamano.deleteCharAt(tamano.length() - 1); //Para eliminar el ]
                    leerLinea.useDelimiter("  ");
                    String nombre = leerLinea.next();
                    ultimoAgregado = new GestionCarpeta(padre, usuario, nombre, tamano.toString(), TipoArchivo.Archivo);
                    tabla.put(nombre, ultimoAgregado);
                } else {
                    archivo.close();
                    leerLinea.close();
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("No se ha encontrado el archivo treeEtc.txt en el directorio del programa o el archivo esta corrupto");
        }
    }
}
