package practicadatosi;

import java.util.LinkedList;

/**
 * @gestionarCarpeta, aquí se gestiona lo relacionado con las carpetas y los
 * archivos, está asociada a la enumeracion de TipoArchivo
 */
public class GestionCarpeta {

    /*
    *Aquí definimos la ruta donde estará la carpeta o el archivo.
    *El órden el el siguiente:
    *{...,rutaAbuelo(principal},rutaPadre,rutaActual}
     */
    private LinkedList<String> direccion;//nombre asignado a la lista

    /**
     * Este es el nombre del dato (tipo de carpeta o un archivo)
     */
    private String nombre;

    /**
     * Después declaramos el usuario
     */
    private String usuario;

    /**
     * El tamañno (los primeros 3 dígitos son números) El último dígito son las
     * unidades de medida El tamaño, ya que con la letra ñ es complicado
     * trabajar lo definiremos como longitud
     */
    private String longitud;

    /**
     * la enumeracion (TipoArchivo)nos dice que puede ser de 2 tipos, carpeta o
     * archivo si es carpeta entonces puede tener hijos, si es archivo no puede.
     * Para eso definimos 'tipo', este atributo es de tipo 'TipoArchivo'
     */
    private TipoArchivo tipo;

    /**
     * contenido son los hijos de la carpeta. Si es un archivo esta variable
     * 'contendo' no se inicializa
     */
    private LinkedList<GestionCarpeta> contenido;

    /**
     * Es importante definir un padre, de esta manera será mas sencillo
     * gestionar el médoto modificarDireccion 'padre' es definido de la clase
     * GestionCarpeta
     */
    private GestionCarpeta padre;

    /**
     * Definimos el método constructor, aquí es necesario usar el método
     * modificarDireccion
     *
     * @param padre la ruta del padre
     * @param usuario usuario del archivo gestionado
     * @param nombre nombre del archivo
     * @param longitud tamaño del archivo
     * @param tipo, puede ser archivo o carpeta segun enum 'TipoArchivo'
     */
    public GestionCarpeta(GestionCarpeta padre, String usuario, String nombre, String longitud, TipoArchivo tipo) {
        this.padre = padre;
        this.usuario = usuario;
        this.nombre = nombre;
        this.longitud = longitud;
        this.tipo = tipo;
        direccion = new LinkedList<>();
        if (tipo == TipoArchivo.Carpeta) {
            contenido = new LinkedList<>();
        }
        if (padre != null) {
            padre.contenido.add(this);
        }
        modificarDireccion();
    }

    /**
     * el metodo add es el que nos va a permitir añadir una carpeta a la
     * linkedlist de contendos
     *
     */
    private void add(GestionCarpeta gestioncarpeta) {
        contenido.add(gestioncarpeta);
    }

    /**
     * modificarDireccion es el metodo que usamos para contruir la ruta del
     * archivo
     */
    private void modificarDireccion() {
        GestionCarpeta actual = this;
        while (actual != null) {
            direccion.addFirst(actual.nombre);
            actual = actual.padre;
        }
    }

    /**
     * métodos getters
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    public String getLongitud() {
        return longitud;
    }

    public TipoArchivo getTipo() {
        return tipo;
    }

    /**
     * metodo que permite cambiar de tipo Archivo a tipo carpeta, la importancia
     * de este método la determinamos en el main, porque no sabemos el tipo de
     * un objeto hasta que leemos el siguiente
     */
    public void cambiarCarpeta() {
        this.tipo = TipoArchivo.Carpeta;
        contenido = new LinkedList<>();
    }

    public LinkedList<String> getDireccion() {
        return direccion;
    }

    /**
     * metodo para listar todos los elementos de una carpeta
     */
    public LinkedList<String> listarContenido() {
        LinkedList<String> hijos = new LinkedList<>();
        for (GestionCarpeta carpeta : contenido) {
            hijos.add(carpeta.getNombre());

        }
        return hijos;
    }

    /**
     * comparar valores para buscar el mayor
     *
     * @param tam1
     * @param tam2
     */
    private static boolean auxPesado(String tam1, String tam2) {
        return Double.parseDouble(tam1) > Double.parseDouble(tam2);
    }

    /**
     * metodo que compara si un tamaño es mayor al otro
     *
     * @param tam
     * @param lim
     */
    private static boolean pesado(String tam, String lim) {
        Character cpta1 = tam.charAt(tam.length() - 1);
        Character cpta2 = lim.charAt(lim.length() - 1);
        if (Character.isDigit(cpta1)) {
            if (Character.isDigit(cpta2)) {
                return auxPesado(tam, lim);
            } else {
                return false;
            }
        } else if (cpta1 == 'K' || cpta1 == 'k') {
            if (Character.isDigit(cpta2)) {
                return true;
            } else if (cpta2 == 'K' || cpta2 == 'k') {
                return auxPesado(tam.substring(0, tam.length() - 1), lim.substring(0, lim.length() - 1));
            } else {
                return false;
            }
        } else if (cpta1 == 'M' || cpta1 == 'm') {
            if (Character.isDigit(cpta2) || cpta2 == 'K' || cpta2 == 'k') {
                return true;
            } else if (cpta2 == 'M' || cpta2 == 'm') {
                return auxPesado(tam.substring(0, tam.length() - 1), lim.substring(0, lim.length() - 1));
            } else {
                return false;
            }
        } else {
            if (Character.isDigit(cpta2) || cpta2 == 'K' || cpta2 == 'M' || cpta2 == 'k' || cpta2 == 'm') {
                return true;
            } else {
                return auxPesado(tam.substring(0, tam.length() - 1), lim.substring(0, lim.length() - 1));
            }
        }
    }

    /**
     * método que permite listar contenidos si pesan mas que el límite ingresado
     * por parámetro
     *
     * @param lim se listan los contenidos que pesen mas que este parametro
     *
     */
    public LinkedList<String> listarMayor(String lim) {
        LinkedList<String> hijos = new LinkedList<>();
        for (GestionCarpeta carpeta : contenido) {
            if (pesado(carpeta.longitud, lim)) {
                hijos.add(carpeta.getNombre());
            }
        }
        return hijos;
    }

    /**
     * metodo que permite listar contenidos por usuario
     *
     * @param usuario, es el propietario del directorio a buscar
     */
    public LinkedList<String> listarUsuario(String usuario) {
        LinkedList<String> hijos = new LinkedList<>();
        for (GestionCarpeta carpeta : contenido) {
            if (carpeta.usuario.equals(usuario)) {
                hijos.add(carpeta.getNombre());
            }
        }
        return hijos;
    }

    /**
     * Directorio padre
     */
    public GestionCarpeta getPadre() {
        return padre;
    }

    @Override
    public String toString() {
        return "Carpeta{" + "direccion=" + direccion + " nombre=" + nombre + " usuario=" + usuario + ", tamano=" + longitud + ", tipo=" + tipo + '}';
    }

}
