/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Clases.Client;
import Clases.Order;
import Clases.Product;
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
import java.util.Calendar;
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
        ArrayList<Product> productos = new ArrayList<>();
        //cargarArchivo("C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\ordenes.csv");
        //formatearTelefono("+1234567");
        //cargarArchivoClientesManuales("C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\clientes3.csv");
       // datos = cargarArchivoClientes("C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\clientes.csv");
       // escribirArchivo("c:\\Users\\diego\\Desktop\\ordenes.csv");
       //cargarArchivoRutasOR("C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\routesOP.csv");
       // escribirArchivoProductos("C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\products.csv");
         //escribirArchivoRutas("C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\rutas.csv");
         //insertarAHistorial();
        // escribirArchivoHOrdersXFecha("14/05/2021", "14/08/2021", "C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\horders.csv");
       // escribirArchivoClientes("C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\Clientes.csv");
      //  escribirArchivoClientesCambio("C:\\Users\\diego\\Desktop\\Archivos varios\\datosAvocados\\clientesCambio.csv");
       /* productos = llenarTablaProductos();
        
       
        for (Product producto : productos) {
            System.out.println(producto.getNombre()+";"+producto.getCantidad()+";"+producto.getValor());
        }
       */
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
        Conexion cn1 = new Conexion();
        cn = new Conexion();

        if (object instanceof Client) {
            
            Statement st;
            ResultSet rs;
            boolean existe = false;
            String phone = ((Client) object).getShippingPhone();
            try {
                st = (Statement) cn1.con.createStatement();
                 rs = st.executeQuery("select * from clients WHERE shippingPhone = '"+phone+"'");
                 
                while (rs.next()) {
                    existe = true;
                 }
                cn1.con.close();
                } catch (Exception e) {
                    System.out.println("Error");
            }
            
            if(!existe)
            {
                try {
                    PreparedStatement PS = cn.con.prepareStatement("insert into clients (shippingPhone, shopifyCode, name, address, address2, city, postalCode, email, totalSpent, class) values (?,?,?,?,?,?,?,?,?,? )");
                    PS.setString(1, ((Client) object).getShippingPhone());
                    PS.setString(2, ((Client) object).getShopifyCode());
                    PS.setString(3, ((Client) object).getName());
                    PS.setString(4, ((Client) object).getAddress());
                    PS.setString(5, ((Client) object).getAddress2());
                    PS.setString(6, ((Client) object).getCity());
                    PS.setString(7, ((Client) object).getPostalCode());
                    PS.setString(8, ((Client) object).getEmail());
                    PS.setDouble(9, ((Client) object).getTotalSpent());
                    PS.setString(10, ((Client) object).getClasse());
                    PS.executeUpdate();

                    cn.con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try {
                    cn.con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (object instanceof Order) {
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into orders ( id, codeSP, stop, shippingPhone, shippingName, address, address2, city, postalCode, itemName, cant, value, total, payment, comments, date) values (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
                PS.setString(1, ((Order) object).getCodeSP());
                PS.setInt(2, ((Order) object).getStop());
                PS.setString(3, ((Order) object).getShippingPhone());
                PS.setString(4, ((Order) object).getShippingName());
                PS.setString(5, ((Order) object).getAddress());
                PS.setString(6, ((Order) object).getAddress2());
                PS.setString(7, ((Order) object).getCity());
                PS.setString(8, ((Order) object).getPostalCode());
                PS.setString(9, ((Order) object).getItemName());
                PS.setInt(10, ((Order) object).getCant());
                PS.setDouble(11, ((Order) object).getValue());
                PS.setDouble(12, ((Order) object).getTotal());
                PS.setString(13, ((Order) object).getPayment());
                PS.setString(14, ((Order) object).getComments());
                PS.setString(15, ((Order) object).getDate());
                PS.executeUpdate();

                cn.con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ( object instanceof Product){
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into products ( id, name, purchaseValue, saleValue, quantity) values (null,?,?,?,? )");
                PS.setString(1, ((Product) object).getNombre());
                PS.setDouble(2, ((Product) object).getPurchaseValue());
                PS.setDouble(3, ((Product) object).getSaleValue());
                PS.setInt(4, ((Product) object).getCantidad());
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
            int campos[] = validarCampos(encabezadosVector, "order");
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
                        String campoTabla = buscarEnFieldConfigure(encabezadosVector[campos[x]], cn, "order");
                        if(campoTabla.equalsIgnoreCase("ErrorBase")){
                            break;
                        }
                        if (campoTabla.equalsIgnoreCase("itemName")
                                || campoTabla.equalsIgnoreCase("cant")
                                || campoTabla.equalsIgnoreCase("value")) {
                            vectorDatosCopia[0][contadorTmp] = vectorDatos[0][campos[x]];
                           // System.out.println( vectorDatosCopia[0][contadorTmp]);
                            contadorTmp++;
                        } else if(campoTabla.equalsIgnoreCase("total")){
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
                        
                        if(buscarEnFieldConfigure(encabezadosVector[campos[j]], cn, "order").equalsIgnoreCase("shippingPhone")){
                            vectorDatosTmp[0][contadorTmp] = formatearTelefono(vectorDatos[0][campos[j]]);
                        }
                        else if(buscarEnFieldConfigure(encabezadosVector[campos[j]], cn, "order").equalsIgnoreCase("date")){
                            vectorDatosTmp[0][contadorTmp] = formatearFecha(vectorDatos[0][campos[j]]);
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
             JOptionPane.showMessageDialog(null, "An error has occurred reading the file, please check the file path");
             return null;
        }
        JOptionPane.showMessageDialog(null, "The orders file has been uploaded succesfully");
        //insertarAHistorial();
        return datos;
    }

    public static int[] validarCampos(String campos[], String clase) {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        String shoppingName;
        String dbName;
        int posicionCampos[] = null;
        int contadorCampos = 0;
        int j = 0;
        String sql;
        
         if(clase.equalsIgnoreCase("order"))
               sql = "select * from fieldconfigure";
         else
               sql = "select * from fieldconfigureclient";
        

        try {
            st = (Statement) cn.con.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                contadorCampos++;
                // System.out.println(rs.getInt("id")+" " +rs.getString("nombre")+" ");
            }
            posicionCampos = new int[contadorCampos];
            st = (Statement) cn.con.createStatement();
            rs = st.executeQuery(sql);
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
             JOptionPane.showMessageDialog(null, "An error has occurred connecting with database to check the fields");
        }
        return posicionCampos;
    }

    public static void crearOrden(String encabezados[], /*ArrayList<String[][]>*/ String[][] datos,  Conexion cn) {
        cn = new Conexion();
        Order order;
        String campoAddress = "";
        String campoTabla = "";
        String cadDia = "";
        String cadMes = "";
        /*
        Calendar fecha = Calendar.getInstance();
        Integer dia = fecha.get(Calendar.DATE);
        if(dia.toString().length() == 1)
            cadDia = "0"+dia;
        else
            cadDia = dia.toString();
            
        Integer mes = fecha.get(Calendar.MONTH)+1;
        if(mes.toString().length() == 1)
            cadMes = "0"+mes;
        else
            cadMes = mes.toString();
            
        int annio = fecha.get(Calendar.YEAR);
        String date = cadDia+"/"+cadMes+"/"+annio;
        */
        /* for (int i = 0; i < encabezados.length; i++) {
            System.out.print(encabezados[i]+" ");
        }
        
        System.out.println("-------------------------");*/

        order = new Order();
        // System.out.println("Copia"+datos[0][0]+datos[0][1]+datos[0][2]+datos[0][3]+datos[0][4]+datos[0][5]+datos[0][6]+datos[0][7]+datos[0][8]+datos[0][9]+datos[0][10]+datos[0][11]);
        for (int i = 0; i < datos.length; i++) {

            for (int j = 0; j < encabezados.length; j++) {
                campoTabla = buscarEnFieldConfigure(encabezados[j], cn, "order");
                //System.out.println("FieldConfigure: " + buscarEnFieldConfigure(encabezados[j], cn));
                if(campoTabla.equalsIgnoreCase("ErrorBase")){
                    return;
                }else if (campoTabla.equalsIgnoreCase("shippingPhone")) {
                    order.setShippingPhone(datos[0][j]);
                    //      System.out.println("datos: "+datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("shippingName")) {
                    order.setShippingName(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("address")) {
                    order.setAddress(datos[0][j]);
                    campoAddress = datos[0][j];
                } else if (campoTabla.equalsIgnoreCase("address2")) {
                    order.setAddress2(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("city")) {
                    order.setCity(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("postalCode")) {
                    order.setPostalCode(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("itemName")) {
                    order.setItemName(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("cant")) {
                    order.setCant(Integer.parseInt(datos[0][j]));
                } else if (campoTabla.equalsIgnoreCase("value")) {
                    order.setValue(Double.parseDouble(datos[0][j]));
                } else if (campoTabla.equalsIgnoreCase("total")) {
                    order.setTotal(Double.parseDouble(datos[0][j]));
                } else if (campoTabla.equalsIgnoreCase("payment")) {
                    order.setPayment(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("comments")) {
                    order.setComments(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("codeSP")) {
                    order.setCodeSP(datos[0][j]);
                }else if (campoTabla.equalsIgnoreCase("date")) {
                    order.setDate(datos[0][j]);
                }
            }

           /* for (int j = 0; j < direcciones.size(); j++) {
                // System.out.println("Campo: " +campoAddress+ " direccion: "+ direcciones.get(j)[0]);
                if (direcciones.get(j)[1].equalsIgnoreCase(campoAddress)) {
                    order.setStop(Integer.parseInt(direcciones.get(j)[0]));
                }
            }
*/
      //     order.setDate(date);
            insertarDatos(order, cn);
            actualizarClientes(order);
            // cn.con.close();
        }
        // return order;
    }

    public static ArrayList<String[]> cargarArchivoRutasOR(String ruta) {
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
                    actualizarRutas( Integer.parseInt(datoGuardar[0]), datoGuardar[1]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error has occurred reading the routes file, please check the file path");
             return null;
        }
        
        JOptionPane.showMessageDialog(null, "The routes file has been uploaded succesfully");
        return direcciones;
    }

    
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
             JOptionPane.showMessageDialog(null, "An error has occurred reading the routes file, please check the file path");
             return null;
        }
        
        JOptionPane.showMessageDialog(null, "The routes file has been uploaded succesfully");
        return direcciones;
    }
     
     
    public static String buscarEnFieldConfigure(String campo, Conexion cn, String tipo) {
        //cn=new Conexion();
        Statement st;
        ResultSet rs;
        if(tipo.equalsIgnoreCase("order"))
        {
            try {
                st = (Statement) cn.con.createStatement();
                rs = st.executeQuery("select * from fieldconfigure where shippingName = '" + campo + "'");

                while (rs.next()) {
                    return rs.getString("dbName");
                }
              //  cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Search error trying to get the fields from database");
                 return "ErrorBase";
            }

            return "error";
        }
        else
        {
            try {
                st = (Statement) cn.con.createStatement();
                rs = st.executeQuery("select * from fieldconfigureclient where shippingName = '" + campo + "'");

                while (rs.next()) {
                    return rs.getString("dbName");
                }
              //  cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Search error trying to get the fields from database");
                 return "ErrorBase";
            }

            return "error";
        }
    }

    public static int escribirArchivo(String ruta) {

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
            String encabezado = "Stop;Phone;Name;Address;Address2;City;Zip;Item;Quantity;Price;Total;Notes;PM;\n";
           bw.write(encabezado);

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
                    contenido.append(rs.getString("comments"));
                    contenido.append(";");
                    if(rs.getString("payment").equalsIgnoreCase("Bank Deposit")){
                        contenido.append("BD");
                    }else if(rs.getString("payment").equalsIgnoreCase("Shopify Payments")){
                        contenido.append("SP");
                    }else if(rs.getString("payment").equalsIgnoreCase("Cash on Delivery (COD)")){
                        contenido.append("COD");
                    }
                    //contenido.append(rs.getString("payment"));
                    contenido.append("\n");
                    bw.write(contenido.toString());
                    contenido = new StringBuilder();
                    
                }
                cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "An error has occurred trying to connect to database");
                 return 0;
            }
            
            bw.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to write the file, please check the export path");
             return 0;
        }
        
        return 1;
    }
    
     public static int actualizarRutas( int stop, String direccion) {
        Statement st;
        ResultSet rs;
        String address;
        int  id;
        String postalCode;
        PreparedStatement PS;
        try {
            Conexion cn = new Conexion();
            st = (Statement) cn.con.createStatement();
            rs = st.executeQuery("select * from orders");
            while (rs.next()) {
                address = rs.getString("address");
                id = rs.getInt("id");
                postalCode = rs.getString("postalCode");
                if(direccion.equalsIgnoreCase(address+" "+postalCode)){
                    PS = cn.con.prepareStatement("UPDATE orders SET stop = '"+stop+"' WHERE id ="+id);
                    PS.execute(); 
                    PS.close();
                }
                
            }
            
            cn.con.close();
            
            return 1;
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to update the routes in database");
             return 0;
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
              JOptionPane.showMessageDialog(null, "An error has occurred while the system was trying to clean the orders table");
         }
     }
     
     public static String formatearTelefono(String telefono){
         StringBuilder  telefonoSB = new StringBuilder();
         System.out.println("Telefono: "+telefono);
         
         
         for (int i = 0; i < telefono.length(); i++) {
             
             if(telefono.charAt(i) == '+')
                i = i+2;
             
             if((telefono.charAt(i) == '0') || (telefono.charAt(i) == '1')
                     || (telefono.charAt(i) == '2') || (telefono.charAt(i) == '3')
                     || (telefono.charAt(i) == '4') || (telefono.charAt(i) == '5')
                     || (telefono.charAt(i) == '6') || (telefono.charAt(i) == '7')
                     || (telefono.charAt(i) == '8') || (telefono.charAt(i) == '9')){
                 telefonoSB.append(telefono.charAt(i));
             }
         }
         
         System.out.println("TelefoboSB :"+ telefonoSB.toString());
         return telefonoSB.toString();
     }
     
     
     
     public static String formatearFecha(String fecha){
         String[] fechaSplit;
         String[] fechaSplitRever;
         
         fechaSplit = fecha.split(" ");
         
         fechaSplitRever = fechaSplit[0].split("-");
         
         String fechaValida = fechaSplitRever[2]+"/"+fechaSplitRever[1]+"/"+fechaSplitRever[0];
         
         
         System.out.println("fechaValida"+fechaValida);
         return fechaValida;
     }
     
      public static int escribirArchivoProductos(String ruta) {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        StringBuilder contenido = new StringBuilder();
        Product producto;
        ArrayList<Product> listaProductos = new ArrayList<>();
        String nombre;
        int    cantidad;
        
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
                    nombre = rs.getString("itemName");
                    cantidad = rs.getInt("cant");
                    boolean primerDato = true;
                    
                    for (int i = 0; i < listaProductos.size(); i++) {
                        if(listaProductos.get(i).getNombre().equalsIgnoreCase(nombre)){
                            cantidad = cantidad + listaProductos.get(i).getCantidad();
                            listaProductos.get(i).setCantidad(cantidad);
                            primerDato = false;
                        }
                    }
                    
                    if(primerDato){
                        producto = new Product(nombre, cantidad, 0, 0);
                        listaProductos.add(producto);
                    }
 
                }
                bw.write("Item;Quantity\n");
                for (Product listaProducto : listaProductos) {
                    bw.write(listaProducto.getNombre()+";"+listaProducto.getCantidad()+"\n");
                    
                }
                cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Error conecting to the data base");
                 return 0;
            }
            
            bw.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to write the products file, please check the path");
             return 0;
        }
        
        return 1;
    }
      
      public static int escribirArchivoRutas(String ruta) {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        StringBuilder contenido = new StringBuilder();
        ArrayList<String> listaProductos = new ArrayList<>();
        String address;
        String postalCode;
        
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
                    address = rs.getString("address");
                    postalCode = rs.getString("postalCode");
                    boolean primerDato = true;
                    
                    for (int i = 0; i < listaProductos.size(); i++) {
                        if(listaProductos.get(i).equalsIgnoreCase(address+" "+postalCode)){
                            primerDato = false;
                        }
                    }
                    
                    if(primerDato){
                        listaProductos.add(address+" "+postalCode);
                    }
 
                }
                bw.write("Address\n");
                for (int i = 0; i < listaProductos.size(); i++) {
                    bw.write(listaProductos.get(i)+"\n");
                    
                }
                cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Error conecting to the data base");
                 return 0;
            }
            
            bw.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to write the products file, please check the path");
             return 0;
        }
        
        return 1;
    }
    
      
      public static void actualizarClientes( Order order) {
        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        Client client = new Client();
        boolean existe = false;
        try {
            st = (Statement) cn.con.createStatement();
            //rs = st.executeQuery("insert into products (id, nombre, precio, cantidad) values (3,'cereza', 900,5 )");
            rs = st.executeQuery("select * from clients WHERE shippingPhone = '"+order.getShippingPhone()+"'");
            while (rs.next()) {
                existe = true;
            }
            cn.con.close();
        } catch (Exception e) {
        }
        
        if(!existe){
            client.setShippingPhone(order.getShippingPhone());
            client.setName(order.getShippingName());
            client.setAddress(order.getAddress());
            client.setAddress2(order.getAddress2());
            client.setCity(order.getCity());
            client.setPostalCode(order.getPostalCode());
            client.setShopifyCode(order.getCodeSP());
            client.setSubscribe(0);
            client.setClasse("");
            insertarDatos(client, cn);
        }
    }
      
      public static int insertarAHistorial() {
        Statement st;
        ResultSet rs;
        String address;
        int  id;
        String postalCode;
        PreparedStatement PS;
        try {
            Conexion cn = new Conexion();
            //PS = cn.con.prepareStatement("INSERT INTO horders SELECT * FROM orders");
            PS = cn.con.prepareStatement("insert into horders ( codeSP, stop, shippingPhone, shippingName, address, address2, city, postalCode,"
                    + " itemName, cant, value, total, payment, comments, date)"
                    + "SELECT O.codeSP, O.stop, O.shippingPhone, O.shippingName, O.address, O.address2, O.city, O.postalCode, O.itemName, O.cant, O.value, O.total,"
                    + "O.payment, O.comments, O.date FROM orders O");

            PS.execute(); 
            PS.close();            
            cn.con.close();
            
            return 1;
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to create history orders in database"+e);
             return 0;
        }
        
    }
      
      public static int escribirArchivoHOrdersXFecha(String fechaDesde, String fechaHasta, String ruta) {

       Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        StringBuilder contenido = new StringBuilder();
        String [] vecFechaDesde = fechaDesde.split("/");
        String [] vecFechaHasta = fechaHasta.split("/");
        String cadLongFechaDesde = vecFechaDesde[2]+vecFechaDesde[1]+vecFechaDesde[0];
        String cadLongFechaHasta = vecFechaHasta[2]+vecFechaHasta[1]+vecFechaHasta[0];
        
        long   longFechaDesde = Long.parseLong(cadLongFechaDesde);
        long   longFechaHasta = Long.parseLong(cadLongFechaHasta);
        
        try {
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            String encabezado = "Stop;Phone;Name;Address;Address2;City;Zip;Item;Quantity;Price;Total;Notes;PM;Date\n";
           bw.write(encabezado);

            try {
                st = (Statement) cn.con.createStatement();
                rs = st.executeQuery("select * from horders");
                while (rs.next()) {
                    String fechaOrder = rs.getString("date");
                    String [] vecFechaOrder = fechaOrder.split("/");
                    String cadLongFechaOrder = vecFechaOrder[2]+vecFechaOrder[1]+vecFechaOrder[0];
                    long   longFechaOrder = Long.parseLong(cadLongFechaOrder);
                    
                    if(longFechaOrder >= longFechaDesde && longFechaOrder <= longFechaHasta)
                    {
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
                        contenido.append(rs.getString("comments"));
                        contenido.append(";");
                        if(rs.getString("payment").equalsIgnoreCase("Bank Deposit")){
                            contenido.append("BD");
                        }else if(rs.getString("payment").equalsIgnoreCase("Shopify Payments")){
                            contenido.append("SP");
                        }else if(rs.getString("payment").equalsIgnoreCase("Cash on Delivery (COD)")){
                            contenido.append("COD");
                        }
                        contenido.append(";");
                        contenido.append(fechaOrder);
                        //contenido.append(rs.getString("payment"));
                        contenido.append("\n");
                        bw.write(contenido.toString());
                        contenido = new StringBuilder();
                    }
                    
                }
                cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "An error has occurred trying to connect to database");
                 return 0;
            }
            
            bw.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to write the file, please check the export path");
             return 0;
        }
        
        return 1;
      }
      
      public static int escribirArchivoClientes(String ruta) {

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
            String encabezado = "Phone;SPCode;Name;Address;Address2;City;Zip;Class;\n";
            bw.write(encabezado);

            try {
                st = (Statement) cn.con.createStatement();
                rs = st.executeQuery("select * from clients");
                while (rs.next()) {
                    contenido.append(rs.getString("shippingPhone"));
                    contenido.append(";");
                    contenido.append(rs.getString("shopifyCode"));
                    contenido.append(";");
                    contenido.append(rs.getString("name"));
                    contenido.append(";");
                    contenido.append(rs.getString("address"));
                    contenido.append(";");
                    contenido.append(rs.getString("address2"));
                    contenido.append(";");
                    contenido.append(rs.getString("city"));
                    contenido.append(";");
                    contenido.append(rs.getString("postalCode"));
                    contenido.append(";");
                    contenido.append(rs.getString("class"));
                    contenido.append(";");
                    //contenido.append(rs.getString("payment"));
                    contenido.append("\n");
                    bw.write(contenido.toString());
                    contenido = new StringBuilder();
                    
                }
                cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "An error has occurred trying to connect to database"+e);
                 return 0;
            }
            
            bw.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to write the file, please check the export path");
             return 0;
        }
        
        return 1;
    }
 
      public static ArrayList<Product> llenarTablaProductos() {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        StringBuilder contenido = new StringBuilder();
        Product producto;
        ArrayList<Product> listaProductos = new ArrayList<>();
        String nombre;
        int    cantidad;
        Double valor;
        
            try {
                st = (Statement) cn.con.createStatement();
                rs = st.executeQuery("select * from horders");
                while (rs.next()) {
                    nombre = rs.getString("itemName");
                    cantidad = rs.getInt("cant");
                    valor = rs.getDouble("value");
                    //System.out.println("Value: "+valor);
                    //valor = valor * cantidad;
                    boolean primerDato = true;
                    
                    for (int i = 0; i < listaProductos.size(); i++) {
                        
                        if(listaProductos.get(i).getNombre().equalsIgnoreCase(nombre)){
                            
                            cantidad = cantidad + listaProductos.get(i).getCantidad();
                            listaProductos.get(i).setCantidad(cantidad);
                            //valor = valor + listaProductos.get(i).getSaleValue();
                            //listaProductos.get(i).setSaleValue(valor);
                            primerDato = false;
                        
                        }
                    }
                    
                    if(primerDato){
                        producto = new Product(nombre, cantidad, 0, valor);
                     //   insertarDatos(producto, cn);
                        listaProductos.add(producto);
                    }
 
                }
                
                for (Product listaProducto : listaProductos) {
                    //insertarDatos(listaProducto, cn);
                    actualizarProductos(listaProducto);
                    System.out.println(listaProducto.getCantidad());
                }
                
                
                cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Error conecting to the data base");
                 return null;
            }
            
          
        
        return listaProductos;
    }
      
      public static ArrayList<Product> actualizarProductos(Product producto) {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        StringBuilder contenido = new StringBuilder();
        ArrayList<Product> listaProductos = new ArrayList<>();
        String nombre;
        int    cantidad;
        Double valor;
        String sql;
        
        int    busqueda = buscarProducto(producto);
        if( 0 == busqueda )
        {
            try {
               
                    PreparedStatement PS = cn.con.prepareStatement("insert into products ( id, name, saleValue, quantity) values (null,?,?,?,? )");
                    PS.setString(1, producto.getNombre());
                    PS.setDouble(2, producto.getSaleValue());
                    PS.setInt(3, producto.getCantidad());
                    PS.executeUpdate();
                    //actualizarProductos(listaProductos);
                cn.con.close();
               
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Error conecting to the data base"+e);
                 return null;
            }
        }
        else
        {
            try {
              Statement prepStat = (Statement) cn.con.createStatement(); 
              
              int   quantity = busqueda+producto.getCantidad();
              
                sql = "UPDATE products SET "
                    + "name = '"+producto.getNombre()+"', "
                    + "saleValue = '"+producto.getSaleValue()+"', "
                    + "quantity = '"+quantity+"' "
                    + "WHERE name = '"+producto.getNombre()+"'";
            
           // System.out.println(sql);
            
                prepStat.executeUpdate(sql);
                cn.con.close();
               
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Error conecting to the data base"+e);
                 return null;
            }
        }
        
        
        return listaProductos;
    }
      
    public static int buscarProducto(Product producto) {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        int       retorno = 0;
        
            try {
                st = (Statement) cn.con.createStatement();
                rs = st.executeQuery("select * from products WHERE name = '"+producto.getNombre()+"'");
                while (rs.next()) {
                    retorno = rs.getInt("quantity");
                    break;
                }                
                
                cn.con.close();
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Error conecting to the data base");
                 return 2;
            }
            
        return retorno;
    }
      
      public static ArrayList<String[][]> cargarArchivoClientes(String ruta) {
        
        //vaciarTabla();
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
            int campos[] = validarCampos(encabezadosVector,"");
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
                
                    // vectorDatosTmp = new String[1][numeroDatos];
                    //Se recorre la lista de campos para agregar solo los campos necesarios
                    for (int j = 0; j < campos.length; j++) {
                        
                        if(buscarEnFieldConfigure(encabezadosVector[campos[j]], cn, "client").equalsIgnoreCase("shippingPhone")){
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
                    crearCliente(encabezadoCampos, vectorDatosTmp, cn);
                    // System.out.println("vector"+vectorDatosTmp[0][2]);
                    contadorDatos = 0;
                    nameAnterior = vectorDatos[0][0];
                    vectorDatos = new String[1][numeroDatos];
                
            }
        } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "An error has occurred reading the file, please check the file path");
             return null;
        }
        JOptionPane.showMessageDialog(null, "The clients file has been uploaded succesfully");
        return datos;
    }
      
      
      
       public static void crearCliente(String encabezados[], /*ArrayList<String[][]>*/ String[][] datos,  Conexion cn) {
        cn = new Conexion();
        Client client;
        String campoAddress = "";
        String campoTabla = "";
        String cadDia = "";
        String cadMes = "";
        StringBuilder nombre = new StringBuilder();
        Calendar fecha = Calendar.getInstance();
        Integer dia = fecha.get(Calendar.DATE);
        if(dia.toString().length() == 1)
            cadDia = "0"+dia;
        else
            cadDia = dia.toString();
            
        Integer mes = fecha.get(Calendar.MONTH)+1;
        if(mes.toString().length() == 1)
            cadMes = "0"+mes;
        else
            cadMes = mes.toString();
            
        int annio = fecha.get(Calendar.YEAR);
        String date = cadDia+"/"+cadMes+"/"+annio;
        /* for (int i = 0; i < encabezados.length; i++) {
            System.out.print(encabezados[i]+" ");
        }
        System.out.println("-------------------------");*/

        client = new Client();
        //   System.out.println("Encabezados"+encabezados[0]+encabezados[1]+encabezados[2]+encabezados[3]+encabezados[4]+encabezados[5]+encabezados[6]+encabezados[7]+encabezados[8]);
        // System.out.println("Copia"+datos[0][0]+";"+datos[0][1]+";"+datos[0][2]+";"+datos[0][3]+";"+datos[0][4]+";"+datos[0][5]+";"+datos[0][6]+";"+datos[0][7]+";"+datos[0][8]+datos[0][9]+datos[0][10]+datos[0][11]);
        for (int i = 0; i < datos.length; i++) {

            for (int j = 0; j < encabezados.length-1; j++) {
                campoTabla = buscarEnFieldConfigure(encabezados[j], cn, "client");
                //System.out.println("FieldConfigure: " + buscarEnFieldConfigure(encabezados[j], cn, "cliente"));
                if(campoTabla.equalsIgnoreCase("ErrorBase")){
                    return;
                }else if (campoTabla.equalsIgnoreCase("shippingPhone")) {
                    client.setShippingPhone(datos[0][j]);
                         // System.out.println("datos: "+datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("name")) {
                    nombre.append(" "+datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("address")) {
                    client.setAddress(datos[0][j]);
                    campoAddress = datos[0][j];
                } else if (campoTabla.equalsIgnoreCase("address2")) {
                    client.setAddress2(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("city")) {
                    client.setCity(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("postalCode")) {
                    client.setPostalCode(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("email")) {
                    client.setEmail(datos[0][j]);
                } else if (campoTabla.equalsIgnoreCase("totalSpent")) {
                    client.setTotalSpent(Double.parseDouble(datos[0][j]));
            }
            }
           /* for (int j = 0; j < direcciones.size(); j++) {
                // System.out.println("Campo: " +campoAddress+ " direccion: "+ direcciones.get(j)[0]);
                if (direcciones.get(j)[1].equalsIgnoreCase(campoAddress)) {
                    order.setStop(Integer.parseInt(direcciones.get(j)[0]));
                }
            }
*/
           client.setName(nombre.toString());
               // System.out.println(client.getShippingPhone());
            if(client.getShippingPhone().equalsIgnoreCase("") || client.getShippingPhone().isEmpty())
            {
                continue;
            }
            
            Statement st;
            ResultSet rs;
          //  Client client = new Client();
            boolean existe = false;
            try {
                st = (Statement) cn.con.createStatement();
                //rs = st.executeQuery("insert into products (id, nombre, precio, cantidad) values (3,'cereza', 900,5 )");
                rs = st.executeQuery("select * from clients WHERE shippingPhone = '"+client.getShippingPhone()+"'");
                while (rs.next()) {
                    existe = true;
                }
                cn.con.close();
            } catch (Exception e) {
            }

            System.out.println(encabezados.length);
            if(!existe)
            {
                insertarDatos(client, cn);
                System.out.println("inserto");
            }
            // cn.con.close();
        
        // return order;
    }
 }
      
   public static ArrayList<String[]> cargarArchivoClientesManuales(String ruta) {
        Path filePath = Paths.get(ruta);
        ArrayList<String[]> direcciones = new ArrayList<>();
        Client client;
        int lineas = 0;
        Conexion cn = new Conexion();
        try {
            BufferedReader bf = Files.newBufferedReader(filePath);
            String linea;
            String[] encabezadosVector;
            String[] datosLinea;
            String nombre;
            boolean primeraLinea = true;
            int posicionAddress = 0;
            int posicionStop = 0;

            //Recorremos las lineas del archivo
            while ((linea = bf.readLine()) != null) {
                lineas++;
                System.out.println(lineas);
                if (primeraLinea) {
                    encabezadosVector = linea.split(";");
                    
                    primeraLinea = false;
                } else {
                    client = new Client();
                    nombre = "";
                    datosLinea = linea.split(";");
                    client.setShippingPhone(datosLinea[1]);
                    nombre = datosLinea[3]+datosLinea[4];
                    client.setName(nombre);
                    client.setAddress(datosLinea[5]);
                    client.setPostalCode(datosLinea[7]);
                    client.setCity(datosLinea[8]);
                    
                    //System.out.println("Stop:"+datoGuardar[0]+"Addres:"+datoGuardar[1]);
                    insertarDatos(client, cn);
                }
            }
        } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "An error has occurred reading the clients file, please check the file path"+e);
             return null;
        }
        
        JOptionPane.showMessageDialog(null, "The clients file has been uploaded succesfully");
        return null;
    }     
      
      
      public static int escribirArchivoClientesCambio(String ruta) {

        Conexion cn = new Conexion();
        Conexion cn1 = new Conexion();
        Statement st;
        ResultSet rs;
        Statement stCli;
        ResultSet rsCli;
        StringBuilder contenido = new StringBuilder();
        ArrayList<String> listaPhones = new ArrayList<>();
        String prevClass = "";
        String lastClass = "";
        String phone;
        String name;
        
        try {
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

                bw.write("shippingPhone;name;prevClass;lastClass\n");
            
                st = (Statement) cn.con.createStatement();
                rs = st.executeQuery("select * from class");
                while (rs.next()) {
                   
                
                    prevClass = rs.getString("prevClass");
                    lastClass = rs.getString("lastClass");
                    phone = rs.getString("shippingPhone");
                    name = rs.getString("name");
                    
                    if(!prevClass.equalsIgnoreCase(lastClass))
                    {
                           
                     bw.write(phone+";"+name+";"+prevClass+";"+lastClass+"\n");
                    }
                }
                
            
            bw.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to write the clients file, please check the path"+e);
             return 0;
        }
        
        return 1;
    }
      
      
        public static int validarClientes() {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        String    name;
        String    address;
        String    phone;
        
        
        try {
            st = (Statement) cn.con.createStatement();
            rs = st.executeQuery("select * from clients");
            while (rs.next()) {
                
                name = rs.getString("name");
                address = rs.getString("address");
                phone = rs.getString("shippingPhone");
                
                validarClientesRepetidos(name, address, phone);
            }
                
            cn.con.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to read the clients table"+e);
             return 0;
        }
        
        return 1;
    }
      
      public static int validarClientesRepetidos(String name, String address, String phone) {

        Conexion cn = new Conexion();
        Statement st;
        ResultSet rs;
        String phoneRepet;

        try {
            st = (Statement) cn.con.createStatement();
            rs = st.executeQuery("select * from clients where name = \""+name+"\" and address = \""+address+"\" and shippingPhone <> \""+phone+"\"");
            while (rs.next()) {
                System.out.println(name);
                phoneRepet = rs.getString("shippingPhone");
                
                actualizarOrdenes(phone, phoneRepet);
                //eliminarCliente(phoneRepet);
            }
                
            cn.con.close();
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to validate the clients table"+e);
             System.out.println(e);
             return 0;
        }
        
        return 1;
    }
      
     
    public static int actualizarOrdenes( String phoneIni, String phoneRepet) {
        PreparedStatement PS;
        Conexion cn = new Conexion();
        try {
           
            PS = cn.con.prepareStatement("UPDATE horders SET shippingPhone = '"+phoneIni+"' WHERE shippingPhone = '"+phoneRepet+"'");
            PS.execute(); 
            PS.close();
                
            
            cn.con.close();
            
            return 1;
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to update the horders in database"+e);
             return 0;
        }
    }
    
    public static int eliminarCliente( String phone) {
        PreparedStatement PS;
        Conexion cn = new Conexion();
        try {
           
            PS = cn.con.prepareStatement("DELETE from clients WHERE shippingPhone = '"+phone+"'");
            PS.execute(); 
            PS.close();
               
            cn.con.close();
            
            return 1;
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error trying to delete the client in database"+e);
             return 0;
        }
    }
      
      
}


