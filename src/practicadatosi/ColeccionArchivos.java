package practicadatosi;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author luisGiraldo and Melissa Gomez
 *
 * clase que contiene la tabla de hash que permite buscar eficientemente los
 * datos
 */
public class ColeccionArchivos {

    private HashMap tabla;

    /**
     * Constructor
     */
    public ColeccionArchivos() {
        tabla = new HashMap<>();
    }

    /**
     * metodo que perime ingresar un "dato" a la tabla de hash
     *
     * @param clave, la clave es el nombre que asociamos a la carpeta o al
     * archivo, cuando busque la calve se obtiene rápidamente ese valor.
     */
    public void put(String clave, GestionCarpeta carpeta) {
        LinkedList<GestionCarpeta> aux = (LinkedList<GestionCarpeta>) tabla.get(clave);
        if (aux == null) {
            aux = new LinkedList<>();
        }
        aux.add(carpeta);
        tabla.put(clave, aux);
    }

    /**
     * metodo que permite obtener una lista de la tabla de hash
     * @param clave, valor asociado a tipo carpeta o archivo
     * retorna una linkend list con todas las carpetas que coincidan con la busqueda
     * @return 
     * @throws java.lang.Exception 
     * @throws si no se encuentra archivo
     */
    
    public LinkedList<GestionCarpeta> get(String clave) throws Exception{
        LinkedList<GestionCarpeta> retornar = (LinkedList<GestionCarpeta>) tabla.get(clave);
        if (retornar == null) {
            throw  new Exception("No such file or directory");
        }
        return retornar;
    }

    /**
     * método que permite retorar una carpeta usando el parametro direccion
     * no retorna nada si no lo encuentra
     * @param clave
     * @throws si no encuentra el archivo
     */

    public GestionCarpeta get(String clave, String direccion) throws Exception{
        String dividir[] = direccion.split("/");
        
        LinkedList<GestionCarpeta> retornar = (LinkedList<GestionCarpeta>) tabla.get(clave);
        LinkedList<String> dir = new LinkedList<>();
        for (int i = 0; i < dividir.length; i++) {
            dir.add(dividir[i]);
        }
        if(retornar == null){
            throw new Exception("No such file or directory");
        }
        for (GestionCarpeta carpeta: retornar) {
            if (carpeta.getDireccion().hashCode() == dir.hashCode()) {
                return carpeta;
            }
        }
        return null;
    }

    /**
     * este método imprime todas las rutas de una lista de carpetas
     */
    
    public void direcciones(String clave) throws Exception{
        LinkedList<GestionCarpeta> coincidencia = get(clave);
        for (GestionCarpeta carpeta: coincidencia) {
            System.out.println(carpeta.getDireccion());
        }
    }
    /**
     * este metodo imprime los contenidos de las carpetas que se llamen igual
     */
    
    public void contenido(String clave) throws Exception{
        LinkedList<GestionCarpeta> coincidencia = get(clave);
        if (coincidencia != null) {
            for (GestionCarpeta carpeta: coincidencia) {
                if (carpeta.getTipo() == TipoArchivo.Carpeta) {
                    System.out.println(carpeta.listarContenido());
                }
            }
        }
    }
    
    /**
     * metodo que imprime los contenidos de un tamaño mayor al ingresado
     * por parametro de una lista de carpetas que se llaman igual
     * @param clave
     * @param mayor
     */

    public void contenidosMayores(String clave, String tamMayor) throws  Exception{
        LinkedList<GestionCarpeta> coincidencia = get(clave);
        if (coincidencia != null) {
            for (GestionCarpeta carpeta: coincidencia) {
                if (carpeta.getTipo() == TipoArchivo.Carpeta) {
                    System.out.println(carpeta.listarMayor(tamMayor));
                }
            }
        }
    }
            
    /**
     * Este método imprime todo los contenidos del usuario ingresado por 
     * parametros de una lista de carpetas que se llaman igual.
     * @param clave
     * @param usuario
     * @throws java.lang.Exception
     */        
            
     public void contenidosUsuario(String clave, String usuario) throws  Exception{
         LinkedList<GestionCarpeta> coincidencias = get(clave);
         if (coincidencias != null) {
             for (GestionCarpeta carpeta: coincidencias) {
                 if (carpeta.getTipo() == TipoArchivo.Carpeta) {
                     System.out.println(carpeta.listarUsuario(usuario));
                 }
             }
         }
     }
            
     /**
      * usamos el metodo toString de la clase carpeta para imprimir
     * @param clave
     * @throws java.lang.Exception
      */       
     
     public void imprimirCarpetas(String clave) throws Exception{
         LinkedList<GestionCarpeta> coincidencias = get(clave);
         for (GestionCarpeta carpeta: coincidencias) {
             System.out.println(carpeta);
         }
     }
                       
}





