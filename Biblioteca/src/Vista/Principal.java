package Vista;

import entrada.Teclado;

public class Principal {

    public static int escribirMenuOpciones() {
        int opcion;

        System.out.println("0) Salir del programa.");
        System.out.println("1) Insertar un libro en la base de datos.");
        System.out.println("2) Eliminar un libro, por código, de la base de datos.");
        System.out.println("3) Consultar todos los libros de la base de datos.");
        System.out.println("4) Consultar varios libros, por escritor, de la base de datos, ordenados por puntuacion descendente.");
        System.out.println("5) Consultar los libros no prestados de la base de datos.");
        System.out.println("6) Consultar los libros devueltos, en una fecha, de la base de datos.");
        opcion = Teclado.leerEntero("Elige una opcion:");
        System.out.println();

        return opcion;
    }

    public static void main(String[] args) {

        int opcion;

        do{
            opcion = escribirMenuOpciones();

            switch (opcion){
                case 1:

                case 2:

                case 3:

                case 4:

                case 5:

                case 6:

                default:
            }


        }while (opcion != 0);


    }
}
