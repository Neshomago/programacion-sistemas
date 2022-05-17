/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import conexion.Conexionbd;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Clientes {
    Conexionbd cx = new Conexionbd();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List ListaClientes(){
        List<Cliente> ListaCliente = new ArrayList();
        String sql = "SELECT * FROM clientes";
        try{
            con = cx.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Cliente cl = new Cliente();
                cl.setId(rs.getInt("id_cliente"));
                cl.setCedula(rs.getString("cedula"));
                cl.setNombre(rs.getString("nombre"));
                cl.setCorreo(rs.getString("correo"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setCant_compras(rs.getInt("cantidad_compras"));
                ListaCliente.add(cl);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListaCliente;
    }
    
}
