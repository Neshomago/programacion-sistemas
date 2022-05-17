/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Conexionbd {
    private final String bd="progsistemas";
    private final String url="jdbc:mysql://localhost:3306/";
    protected String user="root";
    protected String password="";
    String driver="com.mysql.cj.jdbc.Driver";
    Connection cx;
    static PreparedStatement query;
    static ResultSet resultado;
    int loggeo;

    public Connection conectar(){
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url+bd,user,password);
            System.out.println("Conectado a la base.");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Problemas al conectarse.");
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void desconectar(){
        try {
            cx.close();
            System.out.println("Desconectado de la  base.");
        } catch (SQLException ex) {
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int loginUser(String userLogin, String passwordLogin){
        try {
            conectar();
            query = cx.prepareStatement("SELECT * FROM `usuarios` WHERE user = '"+userLogin+"' AND password = '"+passwordLogin+"';");
            resultado = query.executeQuery();
            while(resultado.next()){
                String nombreUsuario = resultado.getString("nombre");
                String userSistema = resultado.getString("user");
                String passSistema = resultado.getString("password");
                if(userLogin.equals(userSistema) &&  passwordLogin.equals(passSistema)){
                JOptionPane.showMessageDialog(null, "Bienvenido "+ nombreUsuario+". \n En su rol de "+ resultado.getString("rol"));
                loggeo = 1;
                System.out.println("log correcto: " + loggeo);
                } else {
                    loggeo = 0;
                    System.out.println("log incorrecto: " + loggeo);
                }
            }
            /*
            SELECT * FROM `usuarios` WHERE user = 'admin' AND password = 'admin';
            */
        } catch (SQLException ex) {
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
            loggeo = 0;
            System.out.println("log incorrecto: " + loggeo);
            desconectar();
        }

        return loggeo;
    }
    
    /*Seccion de Usuarios */
    public void crearUser(String name, String email, String user,
            String password, String phone, String role){
        conectar();
        try {
            query = cx.prepareStatement("INSERT INTO usuarios (id_usuario, nombre, correo, user, password, telefono, rol, fecha_creacion) VALUES (NULL, 'Andres Torres', 'magoyde@gmail.com', 'admin', 'admin', '0963509104', 'admin', current_timestamp())");
            /*
            INSERT INTO `usuarios` (`id_usuario`, `nombre`, `correo`, `user`, `password`, `telefono`, `rol`, `fecha_creacion`)
            VALUES (NULL, 'Andres Torres', 'magoyde@gmail.com', 'admin', 'admin', '0963509104', 'admin', current_timestamp());
            */
        } catch (SQLException ex) {
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void consultarUsuarios(){
    /*
        INSERT INTO `usuarios` (`id_usuario`, `nombre`, `correo`, `user`, `password`,
        `telefono`, `rol`, `fecha_creacion`) VALUES (NULL, 'Joaquin Torres',
        'joaco@correo.com', 'vendedor', 'vendedor', '', 'vendedor', current_timestamp()),
        (NULL, 'Federico Torres', 'fede@correo.com', 'fede', 'fede', '', 'bodeguero', current_timestamp());*/
    }
    
    /*Seccion de Clientes*/
    public void crearCliente (String ced, String nombre, String mail, String phone, String dir){
        try {
            conectar();
            query = cx.prepareStatement("INSERT INTO clientes (cedula, nombre, correo, telefono, direccion, cantidad_compras, fecha_ingreso) VALUES ('"+ced+"', '"+nombre+"', '"+mail+"', '"+phone+"', '"+dir+"', NULL, current_timestamp())");
            System.out.println(query);
            query.executeUpdate();
            System.out.println("Cliente registrado con éxito.");
            JOptionPane.showMessageDialog(null, "Cliente registrado con éxito.");
            /*
            INSERT INTO `clientes` (`id_cliente`, `cedula`, `nombre`, `correo`, `telefono`, `direccion`,
            `cantidad_compras`, `fecha_ingreso`) VALUES (NULL, '0922441199', 'Margareth Carvajal',
            'marga@correo.com', NULL, NULL, NULL, current_timestamp())*/
        } catch (SQLException ex) {
            System.out.println("Cliente con problemas en registro.");
            JOptionPane.showMessageDialog(null, "Cliente con problemas en registro.");
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Object consultaClientes(){
        Object[] object = new Object[7];
        try {
            conectar();
            query = cx.prepareStatement("SELECT * FROM clientes");
            resultado = query.executeQuery();
            while(resultado.next()){
                object[0] = resultado.getString("id_cliente");
                object[1] = resultado.getString("cedula");
                object[2] = resultado.getString("nombre");
                object[3] = resultado.getString("correo");
                object[4] = resultado.getString("telefono");
                object[5] = resultado.getString("direccion");
                object[6] = resultado.getString("cantidad_compras");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("Objeto: "+object);
        return object;
    }
    
    public void consultarCliente(int idCliente){
        try {
            conectar();
            query = cx.prepareStatement("SELECT * FROM clientes WHERE id="+idCliente);
            resultado = query.executeQuery();
            System.out.println("Consulta: \n"+resultado);
        } catch (SQLException ex) {
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizarCliente(){
        try {
            conectar();
            //query = cx.prepareStatement("INSERT INTO clientes (cedula, nombre, correo, telefono, direccion, cantidad_compras, fecha_ingreso) VALUES ('"+ced+"', '"+nombre+"', '"+mail+"', '"+phone+"', '"+dir+"', NULL, current_timestamp())");
            System.out.println(query);
            query.executeUpdate();
            System.out.println("Cliente registrado con éxito.");
            JOptionPane.showMessageDialog(null, "Cliente registrado con éxito.");
            /*
            INSERT INTO `clientes` (`id_cliente`, `cedula`, `nombre`, `correo`, `telefono`, `direccion`,
            `cantidad_compras`, `fecha_ingreso`) VALUES (NULL, '0922441199', 'Margareth Carvajal',
            'marga@correo.com', NULL, NULL, NULL, current_timestamp())*/
        } catch (SQLException ex) {
            System.out.println("Cliente con problemas en registro.");
            JOptionPane.showMessageDialog(null, "Cliente con problemas en registro.");
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarCliente(){
    }
    
    /*Seccion de Productos */
    public void crearProducto (){
    }
    
    public void consultarProducto(){
    }
    
    public void actualizarProducto(){
    }
    
    public void eliminarProducto(){
    }
    
    /*Seccion de Proveedores */
    public void crearProveedor (){
    }
    
    public void consultarProveedor(){
    }
    
    public void actualizarProveedor(){
    }
    
    public void eliminarProveedor(){
    }
    
    /*Seccion de Ventas */
    public void crearVenta (String idvendedor, String nofactura, String idcliente, String detalle,String totalventa){
        try {
            conectar();
            query = cx.prepareStatement("INSERT INTO ventas (id_venta, id_vendedor, no_factura, id_cliente, detalle, total_venta, fecha_venta, pagada) VALUES (NULL, '"+idvendedor+"', '"+nofactura+"', '"+idcliente+"', '"+detalle+"', '"+totalventa+"', current_timestamp(), '1');");
            System.out.println(query);
            query.executeUpdate();
            System.out.println("Venta registrada con éxito.");
            JOptionPane.showMessageDialog(null, "Venya registrada con éxito.");
        } catch (SQLException ex) {
            System.out.println("Venta con problemas en registro.");
            JOptionPane.showMessageDialog(null, "Venta con problemas en registro.");
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void consultarVentas(){
    }
    
    public void eliminarVenta(){
    }
}
