package Modelo;

public class Libro {

    private int codigo;
    private String isbn;
    private String titulo;
    private String escritor;
    private int anio_publicacion;
    private int puntuacion;


    public Libro(int codigo, String isbn, String titulo, String escritor, int año_publicacion, int puntuacion) {
        this.codigo = codigo;
        this.isbn = isbn;
        this.titulo = titulo;
        this.escritor = escritor;
        this.anio_publicacion = año_publicacion;
        this.puntuacion = puntuacion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEscritor() {
        return escritor;
    }

    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }

    public int getAnioo_publicacion() {
        return anio_publicacion;
    }

    public void setAño_publicacion(int año_publicacion) {
        this.anio_publicacion = año_publicacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "codigo=" + codigo +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", escritor='" + escritor + '\'' +
                ", año_publicacion=" + anio_publicacion +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
