/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversor_avocadosandmangoes;

import Clases.Client;
import Clases.Order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        listarDatos();
        
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
}
