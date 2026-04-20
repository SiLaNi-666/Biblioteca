package Dao;

import BD.config.ConfigMySQL;
import Excepciones.BDException;
import Modelo.Libro;
import Modelo.Prestamo;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccesoLibro {

    /**
     * Consultar un libro de la base de datos
     * @param
     * @return
     * @throws BDException
     */
    public static List<Libro> consultarLibro() throws BDException{
        List<Libro> listaLibros = new ArrayList<Libro>();
        PreparedStatement ps = null;
        Connection conexion = null;

        try{
            conexion = ConfigMySQL.abrirConexion();

            String consulta = "SELECT * FROM libro ORDER BY titulo;";
            ps = conexion.prepareStatement(consulta);

            ResultSet resultado = ps.executeQuery();
            while(resultado.next()){
                Libro libro = new Libro (resultado.getInt("codigo"),
                        resultado.getString("isbn"), resultado.getString("titulo"),
                        resultado.getString("escritor"), resultado.getInt("año_publicacion"),
                        resultado.getInt("puntuacion"));
                listaLibros.add(libro);
            }
        }catch (SQLException e){
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        }
        finally {
            {
                if(conexion != null){
                    ConfigMySQL.cerrarConexion(conexion);
                }
            }
        }
        return listaLibros;
    }

    public static boolean insertarLibro (Libro libro) throws BDException{
        Connection conexion = null;
        int insertado = 0;

        try{

            conexion = ConfigMySQL.abrirConexion();
            String insercion = "INSERT INTO Libro (codigo, isbn, titulo, escritor, anio_publicacion, puntuacion) " +
                    "VALUES (?,?,?,?,?,?);";
            PreparedStatement sentencia = conexion.prepareStatement(insercion);
            int codigo = libro.getCodigo();
            String isbn = libro.getIsbn();
            String titulo = libro.getTitulo();
            String escritor = libro.getEscritor();
            int anio_publicacion = libro.getAnioo_publicacion();
            int puntuacion = libro.getPuntuacion();

            sentencia.setInt(1, codigo);
            sentencia.setString(2, isbn);
            sentencia.setString(3, titulo);
            sentencia.setString(4, escritor);
            sentencia.setInt(5, anio_publicacion);
            sentencia.setInt(6, puntuacion);

            insertado = sentencia.executeUpdate();

        }catch (SQLException e){
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        }
        finally {
            if(conexion != null){
                ConfigMySQL.cerrarConexion(conexion);
            }
        }

        return insertado > 0;
    }

    public static boolean eliminarLibro (String codigo) throws BDException{
        Connection conexion = null;
        int filasAfectadas;

        try{
            conexion = ConfigMySQL.abrirConexion();
            String eliminar = "DELETE  FROM Libro WHERE codigo = ?;";
            PreparedStatement sentencia = conexion.prepareStatement(eliminar);

            sentencia.setString(1, codigo);
            filasAfectadas = sentencia.executeUpdate();

        }catch (SQLException e){
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        }
        finally {
            if(conexion != null){
                ConfigMySQL.cerrarConexion(conexion);
            }
        }
        return filasAfectadas > 0;
    }


}
