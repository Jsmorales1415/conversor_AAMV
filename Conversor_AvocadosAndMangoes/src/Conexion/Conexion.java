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
        cargarArchivoRutas("c:\\Users\\diego\\Desktop\\Routes.csv", direcciones);
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
 
    public static void insertarDatos(Object object){
        Conexion cn=new Conexion();
        
        if(object instanceof Client)
        {
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into clients (id, shippingPhone, name, address, address2, city, postalCode) values (?,?,?,?,?,?,? )");
                PS.setString(2, ((Client) object).getShippingPhone());
                PS.setString(3, ((Client) object).getName());
                PS.setString(4, ((Client) object).getAddress());
                PS.setString(5, ((Client) object).getAddress2());
                PS.setString(6, ((Client) object).getCity());
                PS.setString(7, ((Client) object).getPostalCode());
                PS.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(object instanceof Order)
        {
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into orders (id, stop, shippingPhone, shippingName, address, address2, city, postalCode, itemName, cant, value, total, payment, comments) values (?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
                PS.setInt(2, ((Order) object).getStop());
                PS.setString(2, ((Order) object).getShippingPhone());
                PS.setString(3, ((Order) object).getShippingName());
                PS.setString(4, ((Order) object).getAddress());
                PS.setString(5, ((Order) object).getAddress2());
                PS.setString(6, ((Order) object).getCity());
                PS.setString(7, ((Order) object).getPostalCode());
                PS.setInt(2, ((Order) object).getCant());
                PS.setDouble(2, ((Order) object).getValue());
                PS.setDouble(2, ((Order) object).getTotal());
                PS.setString(7, ((Order) object).getPayment());
                PS.setString(7, ((Order) object).getComments());
                PS.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public static void cargarArchivo(String ruta, ArrayList<String[][]> datos){
        Path filePath = Paths.get(ruta);
        String vectorDatos[][];
        String vectorDatosTmp[][];
        try{
            BufferedReader bf = Files.newBufferedReader(filePath);
            String linea;
            String encabezados="";
            String [] encabezadosVector;
            StringBuilder dato;
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
            int campos[] = validarCampos(encabezados, posicionAddress);
            vectorDatos = new String[1][numeroDatos];
             vectorDatosTmp = new String[1][numeroDatos];
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
                                dato.append(linea.charAt(i));
                            }
                        }
                    }
                    else
                    {   
                         dato.append(linea.charAt(i));
                    }
                }
               //order.setShippingName(vector[15]);
               //agreagarDatos(order);
               // String[] datosLinea = linea.split(",");
               // System.out.println(datos.get(0));
               int  contadorTmp = 0;
                for (int j = 0; j< campos.length; j++) {
                    vectorDatosTmp[0][contadorTmp] = vectorDatos[0][campos[j]];
                    contadorTmp++;
                }
               datos.add(vectorDatosTmp);
               //System.out.println(vectorDatos[0][25]);
               contadorDatos = 0;
               vectorDatos = new String[1][numeroDatos];
               vectorDatosTmp = new String[1][numeroDatos];
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static int[] validarCampos(String linea, int posicionAddress){
        
        Conexion cn=new Conexion();
        Statement st;
        ResultSet rs;
        String shoppingName;
        String dbName;
        int posicionCampos[] = null;
        int contadorCampos = 0;
        String campos[] = linea.split(","); 
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
               shoppingName = rs.getString("shoppingName");
               // dbName = rs.getString("dbName");
                for (int i = 0; i < campos.length; i++) {
                    if(campos[i].equalsIgnoreCase(shoppingName)){
                        posicionCampos[j] = i;
                        j++;
                        if(shoppingName.equalsIgnoreCase("Address1"))
                            posicionAddress = i;
                    }
                }
                return posicionCampos;
               
            }
            cn.con.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return posicionCampos;
    }
    
    public Order crearOrden(int[]campos, String encabezados[], ArrayList<String[][]> datos, ArrayList<String> direcciones){
        
        Order order = new Order();
        int   campoAddress = 0;
        
        for (int i = 0; i < encabezados.length; i++) {
            if(encabezados[i].equalsIgnoreCase("Address1")){
                campoAddress = i;
            }
        }
        for (int i = 0; i < direcciones.size(); i++) {
          if(direcciones.get(i).equalsIgnoreCase(datos.get(i)[0][campoAddress])){
           for (int k = 0; k < campos.length; k++) {
            String [][] datosLista = datos.get(i);
            if(encabezados[campos[i]].equalsIgnoreCase("shipping Phone")){
                order.setShippingPhone(datosLista[0][campos[i]]);
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("shipping Name")){
                order.setShippingName(datosLista[0][campos[i]]);
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("Address1")){
                order.setAddress(datosLista[0][campos[i]]);
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("Address2")){
                order.setAddress(datosLista[0][campos[i]]);
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("city")){
                order.setCity(datosLista[0][campos[i]]);
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("postalCode")){
                order.setPostalCode(datosLista[0][campos[i]]);
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("itemName")){
                order.setItemName(datosLista[0][campos[i]]);
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("cant")){
                order.setCant(Integer.parseInt(datosLista[0][campos[i]]));
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("value")){
                order.setValue(Double.parseDouble(datosLista[0][campos[i]]));
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("total")){
                order.setTotal(Double.parseDouble(datosLista[0][campos[i]]));
            }
            else if(encabezados[campos[i]].equalsIgnoreCase("payment")){
                order.setPayment(datosLista[0][campos[i]]);
            }
            //else if(encabezados[campos[i]].equalsIgnoreCase("comments")){
              //  order.setShippingName(vectorDatos[0][campos[i]]);
            //}
            
           // insertarDatos(order);
        }
            }
        }
        
        
        return order;
    }
    
    
    public static void cargarArchivoRutas(String ruta, ArrayList<String> direcciones){
        Path filePath = Paths.get(ruta);
        try{
            BufferedReader bf = Files.newBufferedReader(filePath);
            String linea;
            String [] encabezadosVector;
            String [] datosLinea;
            boolean primeraLinea = true;
            int posicionAGuardar = 0;
            
            //Recorremos las lineas del archivo
            while((linea = bf.readLine())!= null){
                
                if(primeraLinea){
                    encabezadosVector = linea.split(";");
                    for (int i = 0; i < encabezadosVector.length; i++) {
                        if(encabezadosVector[i].equalsIgnoreCase("Address")){
                            posicionAGuardar = i;
                        }
                    }
                    primeraLinea = false;
                }
                else
                {
                   datosLinea = linea.split(";");
                   direcciones.add(datosLinea[posicionAGuardar]);
                    System.out.println(datosLinea[posicionAGuardar]);

                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
