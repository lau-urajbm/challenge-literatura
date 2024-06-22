package com.bmlgold.challenge_literatura.principal;

import com.bmlgold.challenge_literatura.model.*;
import com.bmlgold.challenge_literatura.repository.AutoresRepository;
import com.bmlgold.challenge_literatura.repository.LibrosRepository;
import com.bmlgold.challenge_literatura.service.ConsumoApi;
import com.bmlgold.challenge_literatura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private String URL_BASE = "https://gutendex.com/books";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibrosRepository repositorioLibros;
    private AutoresRepository repositorioAutores;


    public void mostrarMenu() {
        var opcion = 0;
        while (opcion != 6) {
            String menu = """
                    *****************************
                    1- Buscar y registrar Libro
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idioma
                    6- Salir
                    *****************************
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println("buscar y registrar");
                    buscarLibroYGuardar();
                    break;
                case 2:
                    System.out.println("listar libros");
                    listarLibrosRegistrados();
                    break;
                case 3:
                    System.out.println("listar autores");
                    listarAutores();
                    break;
                case 4:
                    System.out.println("listar autores vivos por año");
                    listarAutoresPorFechas();
                    break;
                case 5:
                    System.out.println("listar por idioma");
                    listarLibrosPorIdiomas();
                    break;
                case 6:
                    System.out.println("Finalizando la aplicación...");
                    break;
                default:
                    System.out.println("verifica tu solicitud");
                    break;
            }

        }
    }

    public Principal(LibrosRepository librosRepository, AutoresRepository autoresRepository) {
        this.repositorioLibros = librosRepository;
        this.repositorioAutores = autoresRepository;
    }

    private Resultados getDatosLibros() {
        System.out.println("Escriba el título del libro que desea buscar");
        String tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "/?search=" + tituloLibro.replace(" ", "%20"));
        System.out.println("json" + json);
        Resultados datos = conversor.obtenerDatos(json, Resultados.class);
        System.out.println(datos.resultados());
        return datos;

    }

    private void buscarLibroYGuardar() {
        var datos = getDatosLibros();
        if (!datos.resultados().isEmpty()) {
            System.out.println(datos.resultados().get(0));
            Optional<Libro> buscarLibro = repositorioLibros.buscarLibro(datos.resultados().get(0).titulo());
            if (buscarLibro.isEmpty()) {
                Optional<Autor> buscarAutor = repositorioAutores.buscarAutor(datos.resultados().get(0).autor().get(0).nombre());
                if (buscarAutor.isEmpty()) {
                    Autor nuevoAutor = new Autor(datos.resultados().get(0).autor().get(0));
                    repositorioAutores.save(nuevoAutor);
                    Libro libroEncontrado = new Libro(datos.resultados().get(0), nuevoAutor);
                    repositorioLibros.save(libroEncontrado);
                    nuevoAutor.setLibros(libroEncontrado);
                    System.out.println("libros guardados del autor" + nuevoAutor.getLibros());
                    System.out.println("nuevo autor: " + nuevoAutor);
                    System.out.println("libro" + libroEncontrado);
                } else {
                    Libro libroEncontrado = new Libro(datos.resultados().get(0), buscarAutor.get());
                    repositorioLibros.save(libroEncontrado);
                    buscarAutor.get().setLibros(libroEncontrado);
                    System.out.println("libro de autor existente agregado: " + libroEncontrado);
                    System.out.println("este autor ya existe" + buscarAutor);
                }

            } else {
                System.out.println("Este libro ya ha sido agregado, intenta agregar otro");
            }
        }else{
            System.out.println("Libro no encontrado");
        }
    }

    private void imprimirDatosLibroEnOrden(Libro libro){
        System.out.println("*****************************");
        System.out.println(libro.getTitulo());
        System.out.println("Autor: "+libro.getAutor().getNombre());
        System.out.println("Idiomas: "+libro.getIdiomas());
        System.out.println("Número de descargas: "+libro.getNumeroDeDescargas());
        System.out.println("*****************************");
    }
    private void listarLibrosRegistrados(){
        List<Libro> librosRegistrados = repositorioLibros.findAll();
        librosRegistrados.stream()
                .sorted(Comparator.comparing(libro -> libro.getAutor().getNombre()))
                .forEach(this::imprimirDatosLibroEnOrden);

    }

    private void imprimirDatosAutoresEnOrden(Autor autor){
        System.out.println("*****************************");
        System.out.println(autor.getNombre());
        System.out.println("Año de nacimientoo: "+autor.getFechaDeNacimiento());
        System.out.println("Año de fallecimiento: "+autor.getFechaDeMuerte());
        System.out.println("*****************************");
    }

    private void listarAutores(){
        List<Autor> autoresRegistrados = repositorioAutores.findAll();
        autoresRegistrados.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(this::imprimirDatosAutoresEnOrden);
    }

    private void listarAutoresPorFechas() {
        System.out.println("Indica la fecha en la que quieres ver qué autores estaban vivos: ");
        var fecha = teclado.nextLine();
        Optional<List<Autor>> autoresVivosOp = repositorioAutores.BuscarAutoresPorFechas(fecha);
        if (autoresVivosOp.isPresent() && !autoresVivosOp.get().isEmpty()) {
            List<Autor> autoresVivos = autoresVivosOp.get();
            autoresVivos.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(a -> imprimirDatosAutoresEnOrden(a));
        }else{
            System.out.println("No se encontraron autores vivos en esta fecha.");
        }

    }

    private void listarLibrosPorIdiomas(){
        System.out.println("""
                Indica la sigla del idioma en el que deseas ver libros disponibles:
                En - ingles
                Fr - frances
                Es - español 
                """);
        var idioma = teclado.nextLine();

        if (idioma.equalsIgnoreCase("en") | idioma.equalsIgnoreCase("fr") | idioma.equalsIgnoreCase("es")){
            Optional<List<Libro>> librosEncontradosIdiomaOp = repositorioLibros.BuscarLibrosPorIdioma(idioma.toLowerCase());
            if (librosEncontradosIdiomaOp.isPresent() && !librosEncontradosIdiomaOp.get().isEmpty()){
                List<Libro> librosEncontradosIdioma = librosEncontradosIdiomaOp.get();
                librosEncontradosIdioma.stream()
                        .sorted(Comparator.comparing(Libro::getTitulo))
                        .forEach(this::imprimirDatosLibroEnOrden);
            }else{
                System.out.println("No se encontraron libros en el idioma solicitado, intenta con otra opción.");
            }
        }else{
            System.out.println("La información ingresada no está disponible o es incorrecta, intenta de nuevo");
        }
    }


}
