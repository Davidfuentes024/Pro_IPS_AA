package Modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caki-
 */
public class DAOUSUARIO extends Conexion {

    public Usuario identificarConCifrado(Usuario user) throws Exception {
        Usuario usu = null;
        ResultSet rs = null;

        try {
            String claveCifrada = cifrarClave(user.getClave());
            String sql = "SELECT U.IDUSUARIO, C.NOMBRECARGO, U.IDCARGO FROM usuario "
                    + "U INNER JOIN cargo C ON U.IDCARGO = C.IDCARGO "
                    + "WHERE U.ESTADO = 1 AND U.NOMBREUSUARIO =  '" + user.getNombreUsuario() + "'"
                    + "AND U.CLAVE = '" + claveCifrada + "'";
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usu = new Usuario();
                usu.setId_usuario(rs.getInt("IDUSUARIO"));
                usu.setNombreUsuario(user.getNombreUsuario());
                usu.setCargo(new Cargo());
                usu.getCargo().setNombreCargo(rs.getString("NOMBRECARGO"));
                usu.getCargo().setCodigo(rs.getInt("IDCARGO"));
                usu.setEstado(true);
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            this.cerrar(false);
        }

        return usu;
    }

    public static String cifrarClave(String clave) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = clave.getBytes();
            byte[] hash = md.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario obtenerDoctor(int Especialidad, int Residencia) throws Exception {
        Usuario doc = null;
        ResultSet rs = null;
        String sql = "SELECT IDUSUARIO, NOMBREUSUARIO "
                + "FROM usuario "
                + "WHERE IDCARGO = 3 "
                + "AND ID_ESPECIALIDAD = " + Especialidad + " "
                + "AND ID_RESIDENCIA = " + Residencia + " "
                + "AND ESTADO "
                + "LIMIT 1";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                doc = new Usuario();
                doc.setId_usuario(rs.getInt("IDUSUARIO"));
                doc.setNombreUsuario("NOMBREUSUARIO");
            }
            rs.close();
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return doc;
    }

    public List<Usuario> listarUsuarios() throws Exception {
        List<Usuario> usuarios;
        Usuario usu;
        ResultSet rs = null;
        String sql = "SELECT U.IDUSUARIO, U.NOMBREUSUARIO, U.CLAVE, U.ESTADO, "
                + "C.NOMBRECARGO FROM usuario U INNER JOIN cargo C "
                + "ON C.IDCARGO = U.IDCARGO ORDER BY U.IDUSUARIO";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            usuarios = new ArrayList<>();
            while (rs.next() == true) {
                usu = new Usuario();
                usu.setId_usuario(rs.getInt("IDUSUARIO"));
                usu.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
                usu.setClave(rs.getString("CLAVE"));
                usu.setEstado(rs.getBoolean("ESTADO"));
                usu.setCargo(new Cargo());
                usu.getCargo().setNombreCargo(rs.getString("NOMBRECARGO"));
                usuarios.add(usu);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return usuarios;
    }

    public Usuario registrarUsuarios(Usuario usu) throws Exception {
        String sql;
        String claveCifrada = cifrarClave(usu.getClave());
        usu.setClave(claveCifrada);
        if (usu.getCargo().getCodigo() == 3) {
            sql = "INSERT INTO usuario (NOMBREUSUARIO, CLAVE, ESTADO, IDCARGO, "
                    + "ID_ESPECIALIDAD, ID_RESIDENCIA) "
                    + "VALUES ('" + usu.getNombreUsuario() + "', '"
                    + usu.getClave() + "', "
                    + (usu.isEstado() == true ? "1" : "0")
                    + ", " + usu.getCargo().getCodigo() + ", " 
                    + usu.getId_especialidad() + ", " 
                    + usu.getId_residencia() + ")";
        } else {
            sql = "INSERT INTO usuario (NOMBREUSUARIO, CLAVE, ESTADO, IDCARGO) "
                    + "VALUES ('" + usu.getNombreUsuario() + "', '"
                    + usu.getClave() + "', "
                    + (usu.isEstado() == true ? "1" : "0")
                    + ", " + usu.getCargo().getCodigo() + ")";
        }

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
        ResultSet rs = null;
        sql = "SELECT IDUSUARIO from usuario "
                + "where NOMBREUSUARIO = '" + usu.getNombreUsuario() + "'";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {

                usu.setId_usuario(rs.getInt("IDUSUARIO"));
            }
            rs.close();
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
        return usu;
    }

    public Usuario leerUsuario(Usuario usu) throws Exception {
        Usuario usus = null;
        ResultSet rs = null;
        String sql = "SELECT U.IDUSUARIO, U.NOMBREUSUARIO, U.CLAVE, U.ESTADO, U.IDCARGO "
                + "FROM usuario U WHERE U.IDUSUARIO = " + usu.getId_usuario();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usus = new Usuario();
                usus.setId_usuario(rs.getInt("IDUSUARIO"));
                usus.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
                usus.setClave(rs.getString("CLAVE"));
                usus.setEstado(rs.getBoolean("ESTADO"));
                usus.setCargo(new Cargo());
                usus.getCargo().setCodigo(rs.getInt("IDCARGO"));
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
        return usus;
    }

    public void actualizarUsuarios(Usuario usu) throws Exception {
        String claveCifrada = cifrarClave(usu.getClave());
        usu.setClave(claveCifrada);
        String sql = "UPDATE usuario SET NOMBREUSUARIO = '"
                + usu.getNombreUsuario() + "', CLAVE = '"
                + usu.getClave() + "', ESTADO = "
                + (usu.isEstado() == true ? "1" : "0")
                + ", IDCARGO = "
                + usu.getCargo().getCodigo()
                + " WHERE IDUSUARIO = " + usu.getId_usuario();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public void cambiarVigencia(Usuario usus) throws Exception {
        String sql = "UPDATE usuario SET estado = "
                + (usus.isEstado() == true ? "1" : "0")
                + " WHERE IDUSUARIO = " + usus.getId_usuario();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }
}
