/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Clases.Client;
import Clases.Order;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author diego
 */
public class Conexion {

    private String driver = "com.mysql.cj.jdbc.Driver";
    private String ipServer = "jdbc:mysql://localhost:3306/";
    private String baseDatos = "avocadosandmangoes";
    private String user = "root";
    private String contrasena = "root";
    public Connection con;

    public Conexion() {

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(ipServer + baseDatos, user, contrasena);
        } catch (Exception e) {
            System.err.println("Error:" + e);
        }
    }

    public static void main(String[] args) {
        //cargarArchivo("c:\\Users\\diego\\Desktop\\archivo.csv");
        ArrayList<String> direcciones = new ArrayList<>();
        ArrayList<String[][]> datos = new ArrayList<>();
        datos = cargarArchivo( "C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\ordenes.csv");
        //escribirArchivo("c:\\Users\\diego\\Desktop\\ordenes.txt");
        //cargarArchivoRutas("C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\routes.csv");
    }

    public static void listarDatos() {
        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        try {
            st = (Statement) cn.con.createStatement();
            //rs = st.executeQuery("insert into products (id, nombre, precio, cantidad) values (3,'cereza', 900,5 )");
            rs = st.executeQuery("select * from products");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("nombre") + " ");
            }
            cn.con.close();
        } catch (Exception e) {
        }
    }

    public static void insertarDatos(Object object, Conexion cn) {
        cn = new Conexion();

        if (object instanceof Client) {
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into clients (shippingPhone, name, address, address2, city, postalCode) values (?,?,?,?,?,? )");
                PS.setString(1, ((Client) object).getShippingPhone());
                PS.setString(2, ((Client) object).getName());
                PS.setString(3, ((Client) object).getAddress());
                PS.setString(4, ((Client) object).getAddress2());
                PS.setString(5, ((Client) object).getCity());
                PS.setString(6, ((Client) object).getPostalCode());
                PS.executeUpdate();

                cn.con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (object instanceof Order) {
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into orders ( id, stop, shippingPhone, shippingName, address, address2, city, postalCode, itemName, cant, value, total, payment, comments) values (null,?,?,?,?,?,?,?,?,?,?,?,?,? )");
                PS.setInt(1, ((Order) object).getStop());
                PS.setString(2, ((Order) object).getShippingPhone());
                PS.setString(3, ((Order) object).getShippingName());
                PS.setString(4, ((Order) object).getAddress());
                PS.setString(5, ((Order) object).getAddress2());
                PS.setString(6, ((Order) object).getCity());
                PS.setString(7, ((Order) object).getPostalCode());
                PS.setString(8, ((Order) object).getItemName());
                PS.setInt(9, ((Order) object).getCant());
                PS.setDouble(10, ((Order) object).getValue());
                PS.setDouble(11, ((Order) object).getTotal());
                PS.setString(12, ((Order) object).getPayment());
                PS.setString(13, ((Order) object).getComments());
                PS.executeUpdate();

                cn.con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static ArrayList<String[][]> cargarArchivo(String ruta) {
        
        vaciarTabla();
        Path filePath = Paths.get(ruta);
        String vectorDatos[][];
        String vectorDatosTmp[][];
        String vectorDatosCopia[][];
        ArrayList<String[][]> datos = new ArrayList<>();
        //ArrayList<String[]> datosRutas = new ArrayList<>();
        //datosRutas = cargarArchivoRutas(rutaRoutes);
        //System.out.println(datosRutas.get(0)[0]);
        Conexion cn = new Conexion();
        try {
            BufferedReader bf = Files.newBufferedReader(filePath);
            String linea;
            String encabezados = "";
            String[] encabezadosVector;
            String[] encabezadoCampos;
            StringBuilder dato;
            String nameAnterior = "";
            int posicionAddress = 0;
            boolean primeraLinea = true;
            int numeroDatos = 0;
            if (primeraLinea) {
                encabezados = bf.readLine();
                for (int i = 0; i < encabezados.length(); i++) {
                    if (encabezados.charAt(i) == ',') {
                        numeroDatos++;
                    }
                }
            }
            encabezadosVector = encabezados.split(",");
            int campos[] = validarCampos(encabezadosVector);
            encabezadoCampos = new String[campos.length];
            for (int i = 0; i < campos.length; i++) {
                encabezadoCampos[i] = encabezadosVector[campos[i]];
            }

            vectorDatos = new String[1][numeroDatos];
            vectorDatosTmp = new String[1][numeroDatos];
            vectorDatosCopia = new String[1][numeroDatos];
            int contadorDatos = 0;
            //Recorremos las lineas del archivo
            while ((linea = bf.readLine()) != null) {
                dato = new StringBuilder("");
                //Recorremos los caracteres de la linea leida
                for (int i = 0; i < linea.length(); i++) {
                    //Si la linea leida es igual a una coma es por que viene un nuevo dato
                    if (linea.charAt(i) == ',') {
                        //Agregamos el dato al array y formateamos la cadena
                        vectorDatos[0][contadorDatos] = dato.toString();
                        contadorDatos++;
                        dato = new StringBuilder("");
                        //Se pregunta que el siguiente caracter este dentro de la variable linea
                        if (i + 1 < linea.length()) {
                            //Se pregunta si el caracter que sigue a la coma es un '"' esto nos indica que leeremos una cadena
                            //que puede contener comas en su interior
                            if (linea.charAt(i + 1) == '"') {
                                i = i + 2;
                                while (linea.charAt(i) != '"') {
                                    dato.append(linea.charAt(i));
                                    i++;
                                }

                            } else {
                                if (linea.charAt(i) != ',') {
                                    dato.append(linea.charAt(i));
                                }
                            }
                        }
                    } else {
                        if (linea.charAt(i) != ',') {
                            dato.append(linea.charAt(i));
                        }
                    }
                }
                //order.setShippingName(vector[15]);
                //agreagarDatos(order);
                // String[] datosLinea = linea.split(",");
                // System.out.println(datos.get(0));
                int contadorTmp = 0;
                if (nameAnterior.equalsIgnoreCase(vectorDatos[0][0])){/*(vectorDatosTmp[0][0] != null && vectorDatosTmp[0][0].equalsIgnoreCase(vectorDatos[0][campos[0]])) || (vectorDatosCopia[0][0] != null && vectorDatosCopia[0][0].equalsIgnoreCase(vectorDatos[0][campos[0]]))*/ 
                    vectorDatosCopia = vectorDatosTmp;
                    

                    for (int x = 0; x < campos.length; x++) {
                        if (buscarEnFieldConfigure(encabezadosVector[campos[x]], cn).equalsIgnoreCase("itemName")
                                || buscarEnFieldConfigure(encabezadosVector[campos[x]], cn).equalsIgnoreCase("cant")
                                || buscarEnFieldConfigure(encabezadosVector[campos[x]], cn).equalsIgnoreCase("value")) {
                            vectorDatosCopia[0][contadorTmp] = vectorDatos[0][campos[x]];
                           // System.out.println( vectorDatosCopia[0][contadorTmp]);
                            contadorTmp++;
                        } else if(buscarEnFieldConfigure(encabezadosVector[campos[x]], cn).equalsIgnoreCase("total")){
                            vectorDatosCopia[0][contadorTmp] = "0.0";
                            contadorTmp++;
                        }else{
                            
                            contadorTmp++;
                        }

                    }

                    datos.add(vectorDatosCopia);
                    nameAnterior = vectorDatos[0][0];
                    vectorDatos = new String[1][numeroDatos];
                    contadorDatos = 0;
                    // System.out.println("Copia"+vectorDatosCopia[0][0]+vectorDatosCopia[0][1]+vectorDatosCopia[0][2]+vectorDatosCopia[0][3]+vectorDatosCopia[0][4]+vectorDatosCopia[0][5]+vectorDatosCopia[0][6]+vectorDatosCopia[0][7]+vectorDatosCopia[0][8]+vectorDatosCopia[0][9]+vectorDatosCopia[0][10]+vectorDatosCopia[0][11]);
                    crearOrden(encabezadoCampos, vectorDatosCopia, cn);
                } else {
                    // vectorDatosTmp = new String[1][numeroDatos];
                    //Se recorre la lista de campos para agregar solo los campos necesarios
                    for (int j = 0; j < campos.length; j++) {
                        
                        if(buscarEnFieldConfigure(encabezadosVector[campos[j]], cn).equalsIgnoreCase("shippingPhone")){
                            vectorDatosTmp[0][contadorTmp] = formatearTelefono(vectorDatos[0][campos[j]]);
                        }
                        else
                        {
                             vectorDatosTmp[0][contadorTmp] = vectorDatos[0][campos[j]];
                        }
                        // System.out.println(vectorDatos[0][campos[j]]);
                        contadorTmp++;
                    }
                    datos.add(vectorDatosTmp);
                    crearOrden(encabezadoCampos, vectorDatosTmp, cn);
                    // System.out.println("vector"+vectorDatosTmp[0][2]);
                    contadorDatos = 0;
                    nameAnterior = vectorDatos[0][0];
                    vectorDatos = new String[1][numeroDatos];
                }
            }
        } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Error al leer el archivo, por favor verifique la ruta");
        }

        return datos;
    }

    public static int[] validarCampos(String campos[]) {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        String shoppingName;
        String dbName;
        int posicionCampos[] = null;
        int contadorCampos = 0;
        int j = 0;

        try {
            st = (Statement) cn.con.createStatement();
            rs = st.executeQuery("select * from fieldconfigure");
            while (rs.next()) {
                contadorCampos++;
                // System.out.println(rs.getInt("id")+" " +rs.getString("nombre")+" ");
            }
            posicionCampos = new int[contadorCampos];
            st = (Statement) cn.con.createStatement();
            rs = st.executeQuery("select * from fieldconfigure");
            while (rs.next()) {
                shoppingName = rs.getString("shippingName");
                // dbName = rs.getString("dbName");
                for (int i = 0; i < campos.length; i++) {
                    if (campos[i].equalsIgnoreCase(shoppingName)) {
                        posicionCampos[j] = i;
                        j++;
                    }
                }

            }
            cn.con.close();
            return posicionCampos;
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error al leer conectarse con la base de datos para validar los campos");
        }
        return posicionCampos;
    }

    public static void crearOrden(String encabezados[], /*ArrayList<String[][]>*/ String[][] datos,  Conexion cn) {
        cn = new Conexion();
        Order order;
        String campoAddress = "";
        /* for (int i = 0; i < encabezados.length; i++) {
            System.out.print(encabezados[i]+" ");
        }
        System.out.println("-------------------------");*/

        order = new Order();
        // System.out.println("Copia"+datos[0][0]+datos[0][1]+datos[0][2]+datos[0][3]+datos[0][4]+datos[0][5]+datos[0][6]+datos[0][7]+datos[0][8]+datos[0][9]+datos[0][10]+datos[0][11]);
        for (int i = 0; i < datos.length; i++) {

            for (int j = 0; j < encabezados.length; j++) {
                //System.out.println("FieldConfigure: " + buscarEnFieldConfigure(encabezados[j], cn));
                if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("shippingPhone")) {
                    order.setShippingPhone(datos[0][j]);
                    //      System.out.println("datos: "+datos[0][j]);
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("shippingName")) {
                    order.setShippingName(datos[0][j]);
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("address")) {
                    order.setAddress(datos[0][j]);
                    campoAddress = datos[0][j];
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("address2")) {
                    order.setAddress2(datos[0][j]);
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("city")) {
                    order.setCity(datos[0][j]);
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("postalCode")) {
                    order.setPostalCode(datos[0][j]);
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("itemName")) {
                    order.setItemName(datos[0][j]);
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("cant")) {
                    order.setCant(Integer.parseInt(datos[0][j]));
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("value")) {
                    order.setValue(Double.parseDouble(datos[0][j]));
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("total")) {
                    order.setTotal(Double.parseDouble(datos[0][j]));
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("payment")) {
                    order.setPayment(datos[0][j]);
                } else if (buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("comments")) {
                    order.setComments(datos[0][j]);
                }
            }

           /* for (int j = 0; j < direcciones.size(); j++) {
                // System.out.println("Campo: " +campoAddress+ " direccion: "+ direcciones.get(j)[0]);
                if (direcciones.get(j)[1].equalsIgnoreCase(campoAddress)) {
                    order.setStop(Integer.parseInt(direcciones.get(j)[0]));
                }
            }
*/
            insertarDatos(order, cn);
            // cn.con.close();
        }
        // return order;
    }
/*
    public static ArrayList<String[]> cargarArchivoRutas(String ruta) {
        Path filePath = Paths.get(ruta);
        ArrayList<String[]> direcciones = new ArrayList<>();
        try {
            BufferedReader bf = Files.newBufferedReader(filePath);
            String linea;
            String[] encabezadosVector;
            String[] datosLinea;
            String[] datoGuardar = new String[2];
            boolean primeraLinea = true;
            int posicionAddress = 0;
            int posicionStop = 0;

            //Recorremos las lineas del archivo
            while ((linea = bf.readLine()) != null) {
                datoGuardar = new String[2];
                if (primeraLinea) {
                    encabezadosVector = linea.split(";");
                    for (int i = 0; i < encabezadosVector.length; i++) {
                        if (encabezadosVector[i].equalsIgnoreCase("Address")) {
                            posicionAddress = i;
                        } else if (encabezadosVector[i].equalsIgnoreCase("Stop Number")) {
                            posicionStop = i;
                        }
                    }
                    primeraLinea = false;
                } else {
                    datosLinea = linea.split(";");
                    datoGuardar[0] = datosLinea[posicionStop];
                    datoGuardar[1] = datosLinea[posicionAddress];
                    //System.out.println("Stop:"+datoGuardar[0]+"Addres:"+datoGuardar[1]);
                    direcciones.add(datoGuardar);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return direcciones;
    }
*/
    
     public static ArrayList<String[]> cargarArchivoRutas(String ruta) {
        Path filePath = Paths.get(ruta);
        ArrayList<String[]> direcciones = new ArrayList<>();
        try {
            BufferedReader bf = Files.newBufferedReader(filePath);
            String linea;
            String[] encabezadosVector;
            String[] datosLinea;
            String[] datoGuardar = new String[2];
            boolean primeraLinea = true;
            int posicionAddress = 0;
            int posicionStop = 0;

            //Recorremos las lineas del archivo
            while ((linea = bf.readLine()) != null) {
                datoGuardar = new String[2];
                if (primeraLinea) {
                    encabezadosVector = linea.split(";");
                    
                    primeraLinea = false;
                } else {
                    datosLinea = linea.split(";");
                    datoGuardar[0] = datosLinea[1];
                    datoGuardar[1] = datosLinea[0];
                    //System.out.println("Stop:"+datoGuardar[0]+"Addres:"+datoGuardar[1]);
                    direcciones.add(datoGuardar);
                    actualizarRutas( Integer.parseInt(datoGuardar[0]), datoGuardar[1]);
                }
            }
        } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Error al leer el archivo de rutas, por favor verifique el directorio");
        }
        return direcciones;
    }
     
     
    public static String buscarEnFieldConfigure(String campo, Conexion cn) {
        //cn=new Conexion();
        Statement st;
        ResultSet rs;
        try {
            st = (Statement) cn.con.createStatement();
            rs = st.executeQuery("select * from fieldconfigure where shippingName = '" + campo + "'");

            while (rs.next()) {
                return rs.getString("dbName");
            }
          //  cn.con.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error al buscar los campos en la base de datos");
        }

        return "error";
    }

    public static void escribirArchivo(String ruta) {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        StringBuilder contenido = new StringBuilder();
        try {
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
           

            try {
                st = (Statement) cn.con.createStatement();
                rs = st.executeQuery("select * from orders");
                while (rs.next()) {
                    contenido.append(rs.getString("stop"));
                    contenido.append(";");
                    contenido.append(rs.getString("shippingPhone"));
                    contenido.append(";");
                    contenido.append(rs.getString("shippingName"));
                    contenido.append(";");
                    contenido.append(rs.getString("address"));
                    contenido.append(";");
                    contenido.append(rs.getString("address2"));
                    contenido.append(";");
                    contenido.append(rs.getString("city"));
                    contenido.append(";");
                    contenido.append(rs.getString("postalCode"));
                    contenido.append(";");
                    contenido.append(rs.getString("itemName"));
                    contenido.append(";");
                    contenido.append(rs.getInt("cant"));
                    contenido.append(";");
                    contenido.append(rs.getDouble("value"));
                    contenido.append(";");
                    contenido.append(rs.getDouble("total"));
                    contenido.append(";");
                    contenido.append(rs.getString("payment"));
                    contenido.append(";");
                    contenido.append(rs.getString("comments"));
                    contenido.append("\n");
                    bw.write(contenido.toString());
                    contenido = new StringBuilder();
                    
                }
                cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos");
            }
            
            bw.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error al escribir el archivo, por favor verifique la ruta");
        }
    }
    
     public static void actualizarRutas( int stop, String direccion) {
        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        String address;
        int  id;
        PreparedStatement PS;
        try {
            st = (Statement) cn.con.createStatement();
            rs = st.executeQuery("select * from orders");
            while (rs.next()) {
                address = rs.getString("address");
                id = rs.getInt("id");
                if(direccion.equalsIgnoreCase(address)){
                    PS = cn.con.prepareStatement("UPDATE orders SET stop = '"+stop+"' WHERE id ="+id);
                    PS.execute(); 
                    PS.close();
                }
                
            }
           
            
            cn.con.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos para actualizar rutas");
        }
    }
     
     public static void vaciarTabla(){
         Conexion cn = new Conexion();
         Statement st;
         ResultSet rs;
         PreparedStatement PS;
         try{
            PS = cn.con.prepareStatement("TRUNCATE TABLE orders ");  
            PS.execute(); 
            PS.close();
            cn.con.close();
         }catch (Exception e){
              JOptionPane.showMessageDialog(null, "Error al vaciar la tabla de ordenes");
         }
     }
     
     public static String formatearTelefono(String telefono){
         StringBuilder  telefonoSB = new StringBuilder();
         for (int i = 0; i < telefono.length(); i++) {
             if((telefono.charAt(i) == '0') || (telefono.charAt(i) == '1')
                     || (telefono.charAt(i) == '2') || (telefono.charAt(i) == '3')
                     || (telefono.charAt(i) == '4') || (telefono.charAt(i) == '5')
                     || (telefono.charAt(i) == '6') || (telefono.charAt(i) == '7')
                     || (telefono.charAt(i) == '8') || (telefono.charAt(i) == '9')){
                 telefonoSB.append(telefono.charAt(i));
             }
         }
         
         return telefonoSB.toString();
     }
    
}


