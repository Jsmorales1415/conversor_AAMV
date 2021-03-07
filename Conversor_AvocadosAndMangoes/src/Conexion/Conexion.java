/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Clases.Client;
import Clases.Order;
import java.io.BufferedReader;
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
    Connection con;
    public Conexion(){
      
        
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(ipServer+baseDatos,user,contrasena);
        } catch (Exception e) {
            System.err.println("Error:" +e);
        }
    }
    public static void main(String[] args) {
        //cargarArchivo("c:\\Users\\diego\\Desktop\\archivo.csv");
        ArrayList<String> direcciones = new ArrayList<>();
        ArrayList<String[][]> datos = new ArrayList<>();
       datos = cargarArchivo("c:\\Users\\diego\\Desktop\\archivo.csv","c:\\Users\\diego\\Desktop\\Routes.csv");
    }
    
    public static void listarDatos(){
        Conexion cn=new Conexion();
        Statement st;
        ResultSet rs;
        try {
            st=(Statement) cn.con.createStatement();
            //rs = st.executeQuery("insert into products (id, nombre, precio, cantidad) values (3,'cereza', 900,5 )");
            rs=st.executeQuery("select * from products");
            while (rs.next()) {                
                System.out.println(rs.getInt("id")+" " +rs.getString("nombre")+" ");
            }
            cn.con.close();
        } catch (Exception e) {
        }
    }
 
    public static void insertarDatos(Object object, Conexion cn){
       // Conexion cn=new Conexion();
        
        if(object instanceof Client)
        {
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into clients (shippingPhone, name, address, address2, city, postalCode) values (?,?,?,?,?,? )");
                PS.setString(1, ((Client) object).getShippingPhone());
                PS.setString(2, ((Client) object).getName());
                PS.setString(3, ((Client) object).getAddress());
                PS.setString(4, ((Client) object).getAddress2());
                PS.setString(5, ((Client) object).getCity());
                PS.setString(6, ((Client) object).getPostalCode());
                PS.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(object instanceof Order)
        {
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into orders ( stop, shippingPhone, shippingName, address, address2, city, postalCode, itemName, cant, value, total, payment, comments) values (?,?,?,?,?,?,?,?,?,?,?,?,? )");
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

            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public static ArrayList<String[][]> cargarArchivo(String ruta, String rutaRoutes){
        Path filePath = Paths.get(ruta);
        String vectorDatos[][];
        String vectorDatosTmp[][];
        String vectorDatosCopia[][];
        ArrayList<String[][]> datos = new ArrayList<>();
        ArrayList<String[]> datosRutas = new ArrayList<>();
                datosRutas = cargarArchivoRutas(rutaRoutes);
                //System.out.println(datosRutas.get(0)[0]);
        Conexion cn=new Conexion();
        try{
            BufferedReader bf = Files.newBufferedReader(filePath);
            String linea;
            String encabezados="";
            String [] encabezadosVector;
            String [] encabezadoCampos;
            StringBuilder dato;
            String nameAnterior = "";
            int posicionAddress = 0;
            boolean primeraLinea = true;
            int numeroDatos = 0;
            if(primeraLinea){
                encabezados = bf.readLine();
                for (int i = 0; i < encabezados.length(); i++) {
                    if(encabezados.charAt(i) == ',')
                        numeroDatos++;
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
            while((linea = bf.readLine())!= null){
                dato = new StringBuilder("");
                //Recorremos los caracteres de la linea leida
                for (int i = 0; i < linea.length(); i++) {
                    //Si la linea leida es igual a una coma es por que viene un nuevo dato
                    if(linea.charAt(i) == ','){
                        //Agregamos el dato al array y formateamos la cadena
                        vectorDatos[0][contadorDatos] = dato.toString();
                        contadorDatos++;
                        dato = new StringBuilder("");
                        //Se pregunta que el siguiente caracter este dentro de la variable linea
                        if(i+1 < linea.length()){
                            //Se pregunta si el caracter que sigue a la coma es un '"' esto nos indica que leeremos una cadena
                            //que puede contener comas en su interior
                            if(linea.charAt(i+1) == '"'){
                                i = i+2;
                                while(linea.charAt(i) != '"'){
                                  dato.append(linea.charAt(i));
                                  i++;      
                                }
                                
                            }
                            else
                            {   
                                if(linea.charAt(i) != ',')
                                    dato.append(linea.charAt(i));
                            }
                        }
                    }
                    else
                    {   
                        if(linea.charAt(i) != ',')
                         dato.append(linea.charAt(i));
                    }
                }
               //order.setShippingName(vector[15]);
               //agreagarDatos(order);
               // String[] datosLinea = linea.split(",");
               // System.out.println(datos.get(0));
               int  contadorTmp = 0;
               if(nameAnterior.equalsIgnoreCase(vectorDatos[0][0]))/*(vectorDatosTmp[0][0] != null && vectorDatosTmp[0][0].equalsIgnoreCase(vectorDatos[0][campos[0]])) || (vectorDatosCopia[0][0] != null && vectorDatosCopia[0][0].equalsIgnoreCase(vectorDatos[0][campos[0]]))*/
                  {
                   vectorDatosCopia = vectorDatosTmp;
                  
                   for (int i = 0; i < campos.length; i++) {
                       if( buscarEnFieldConfigure(encabezadosVector[campos[i]], cn).equalsIgnoreCase("itemName")
                               || buscarEnFieldConfigure(encabezadosVector[campos[i]], cn).equalsIgnoreCase("cant")
                               || buscarEnFieldConfigure(encabezadosVector[campos[i]], cn).equalsIgnoreCase("value"))
                       {    
                           vectorDatosCopia[0][contadorTmp] = vectorDatos[0][campos[i]];
                           contadorTmp++;
                       }
                       else
                       {
                           contadorTmp++;
                       }
                       
                   }
                   
                   datos.add(vectorDatosCopia);
                   nameAnterior = vectorDatos[0][0];
                   vectorDatos = new String[1][numeroDatos]; 
                   contadorDatos = 0;
                  // System.out.println("Copia"+vectorDatosCopia[0][0]+vectorDatosCopia[0][1]+vectorDatosCopia[0][2]+vectorDatosCopia[0][3]+vectorDatosCopia[0][4]+vectorDatosCopia[0][5]+vectorDatosCopia[0][6]+vectorDatosCopia[0][7]+vectorDatosCopia[0][8]+vectorDatosCopia[0][9]+vectorDatosCopia[0][10]+vectorDatosCopia[0][11]);
                   crearOrden(encabezadoCampos, vectorDatosCopia, datosRutas, cn);
               }
               else
               {
               // vectorDatosTmp = new String[1][numeroDatos];
               //Se recorre la lista de campos para agregar solo los campos necesarios
                for (int j = 0; j< campos.length; j++) {
                    vectorDatosTmp[0][contadorTmp] = vectorDatos[0][campos[j]];
                    
                   // System.out.println(vectorDatos[0][campos[j]]);
                    contadorTmp++;
                }
               datos.add(vectorDatosTmp);
               crearOrden(encabezadoCampos, vectorDatosTmp, datosRutas, cn);
               // System.out.println("vector"+vectorDatosTmp[0][2]);
               contadorDatos = 0;
               nameAnterior = vectorDatos[0][0];
               vectorDatos = new String[1][numeroDatos];
               }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return datos;
    }
    
    public static int[] validarCampos(String campos[]){
        
        Conexion cn=new Conexion();
        Statement st;
        ResultSet rs;
        String shoppingName;
        String dbName;
        int posicionCampos[] = null;
        int contadorCampos = 0;
        int j = 0;
        
        try {
            st=(Statement) cn.con.createStatement();
            rs=st.executeQuery("select * from fieldconfigure");
            while (rs.next()) { 
                contadorCampos++;
               // System.out.println(rs.getInt("id")+" " +rs.getString("nombre")+" ");
            }
          posicionCampos = new int[contadorCampos];
            st=(Statement) cn.con.createStatement();
            rs=st.executeQuery("select * from fieldconfigure");
             while (rs.next()) { 
               shoppingName = rs.getString("shippingName");
               // dbName = rs.getString("dbName");
                for (int i = 0; i < campos.length; i++) {
                    if(campos[i].equalsIgnoreCase(shoppingName)){
                        posicionCampos[j] = i;
                        j++;
                    }
                }
               
            }
            cn.con.close();
            return posicionCampos;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return posicionCampos;
    }
    
    public static void crearOrden(String encabezados[], /*ArrayList<String[][]>*/String[][] datos, ArrayList<String[]> direcciones,  Conexion cn){
        
        Order order;
        String   campoAddress = "";
   
        for (int i = 0; i < datos.length; i++) {
            order = new Order();
            for (int j = 0; j < encabezados.length; j++) {
                if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("shippingPhone")){
                order.setShippingPhone(datos[0][j]);
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("shippingName")){
                    order.setShippingName(datos[0][j]);
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("address")){
                    order.setAddress(datos[0][j]);
                    campoAddress = datos[0][j];
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("address2")){
                    order.setAddress2(datos[0][j]);
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("city")){
                    order.setCity(datos[0][j]);
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("postalCode")){
                    order.setPostalCode(datos[0][j]);
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("itemName")){
                    order.setItemName(datos[0][j]);
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("cant")){
                    order.setCant(Integer.parseInt(datos[0][j]));
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("value")){
                    order.setValue(Double.parseDouble(datos[0][j]));
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("total")){
                    order.setTotal(Double.parseDouble(datos[0][j]));
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("payment")){
                    order.setPayment(datos[0][j]);
                }
                else if(buscarEnFieldConfigure(encabezados[j], cn).equalsIgnoreCase("comments")){
                    order.setComments(datos[0][j]);
                }
            }
            
           for (int j = 0; j < direcciones.size(); j++) {
               System.out.println("Campo: " +campoAddress+ " direccion: "+ direcciones.get(j)[0]);
                if(direcciones.get(j)[1].equalsIgnoreCase(campoAddress))
                {
                    order.setStop(Integer.parseInt(direcciones.get(j)[0]));
                }
            } 
           
           insertarDatos(order, cn);
            
        }
       // return order;
    }
            
        
        
        
    
    
    
    public static ArrayList<String[]> cargarArchivoRutas(String ruta){
        Path filePath = Paths.get(ruta);
        ArrayList<String[]> direcciones = new ArrayList<>();
        try{
            BufferedReader bf = Files.newBufferedReader(filePath);
            String linea;
            String [] encabezadosVector;
            String [] datosLinea;
            String [] datoGuardar = new String[2];
            boolean primeraLinea = true;
            int posicionAddress = 0;
            int posicionStop = 0;
            
            //Recorremos las lineas del archivo
            while((linea = bf.readLine())!= null){
               datoGuardar = new String[2];
                if(primeraLinea){
                    encabezadosVector = linea.split(";");
                    for (int i = 0; i < encabezadosVector.length; i++) {
                        if(encabezadosVector[i].equalsIgnoreCase("Address")){
                            posicionAddress = i;
                        }
                        else if(encabezadosVector[i].equalsIgnoreCase("Stop Number")){
                            posicionStop = i;
                        }
                    }
                    primeraLinea = false;
                }
                else
                {
                   datosLinea = linea.split(";");
                   datoGuardar[0] = datosLinea[posicionStop];
                   datoGuardar[1] = datosLinea[posicionAddress];
                   //System.out.println("Stop:"+datoGuardar[0]+"Addres:"+datoGuardar[1]);
                   direcciones.add(datoGuardar);
                  
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return direcciones;
    }
    
     public static String buscarEnFieldConfigure(String campo,  Conexion cn){
       // Conexion cn=new Conexion();
        Statement st;
        ResultSet rs;
        try {
            st=(Statement) cn.con.createStatement();
            rs=st.executeQuery("select * from fieldconfigure where shippingName = '"+campo+"'");
            
            while (rs.next()) {                
                return rs.getString("dbName");
            }
            cn.con.close();
        } catch (Exception e) {
        }
        
        return "";
    }
}
