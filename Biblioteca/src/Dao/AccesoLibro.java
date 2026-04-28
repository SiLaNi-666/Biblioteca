package Dao;

import BD.ConfigMySQL;
import Excepciones.BDException;
import Modelo.Libro;
import Modelo.Prestamo;

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

    public static List<Libro> consultarPorEscritor(String escritor) throws BDException {
        Connection conexion = null;
        List<Libro> lista = new ArrayList<>();

        try {
            conexion = ConfigMySQL.abrirConexion();
            String consulta = "SELECT * FROM libro WHERE escritor = ? ORDER BY puntuacion DESC;";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, escritor);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String isbn = rs.getString("isbn");
                String titulo = rs.getString("titulo");
                String esc = rs.getString("escritor");
                int anio = rs.getInt("anio_publicacion");
                int puntuacion = rs.getInt("puntuacion");

                Libro libro = new Libro(codigo, isbn, titulo, esc, anio, puntuacion);
                lista.add(libro);
            }

        } catch (SQLException e) {
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySQL.cerrarConexion(conexion);
            }
        }
        return lista;
    }

    public static List<Libro> consultarLibrosNoPrestados() throws BDException {
        Connection conexion = null;
        List<Libro> lista = new ArrayList<>();

        try {
            conexion = ConfigMySQL.abrirConexion();
            // SQL: Selecciona libros que no tienen registros activos (sin fecha de devolución) en préstamo
            String consulta = "SELECT * FROM libro WHERE codigo NOT IN (SELECT codigo_libro FROM prestamo WHERE fecha_devolucion IS NULL);";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String isbn = rs.getString("isbn");
                String titulo = rs.getString("titulo");
                String escritor = rs.getString("escritor");
                int anio = rs.getInt("anio_publicacion");
                int puntuacion = rs.getInt("puntuacion");

                lista.add(new Libro(codigo, isbn, titulo, escritor, anio, puntuacion));
            }

        } catch (SQLException e) {
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySQL.cerrarConexion(conexion);
            }
        }

        return lista;
    }

    public static List<Libro> consultarLibrosDevueltosFecha(String fechaDevolucion) throws BDException {
        Connection conexion = null;
        List<Libro> lista = new ArrayList<>();

        try {
            conexion = ConfigMySQL.abrirConexion();
            // SQL: Unimos libro y prestamo para filtrar por la fecha de devolución
            String consulta = "SELECT l.* FROM libro l INNER JOIN prestamo p ON l.codigo = p.codigo_libro WHERE p.fecha_devolucion = ?;";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, fechaDevolucion);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String isbn = rs.getString("isbn");
                String titulo = rs.getString("titulo");
                String escritor = rs.getString("escritor");
                int anio = rs.getInt("anio_publicacion");
                int puntuacion = rs.getInt("puntuacion");

                lista.add(new Libro(codigo, isbn, titulo, escritor, anio, puntuacion));
            }

        } catch (SQLException e) {
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySQL.cerrarConexion(conexion);
            }
        }

        return lista;
    }
}
