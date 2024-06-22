package com.bmlgold.challenge_literatura.repository;

import com.bmlgold.challenge_literatura.model.DatosLibro;
import com.bmlgold.challenge_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libro,Long> {

    @Query("SELECT l FROM Libro l WHERE l.titulo ILIKE %:tituloLibro%")
    Optional<Libro> buscarLibro(String tituloLibro);

    @Query("SELECT l FROM Libro l WHERE l.idiomas LIKE %:idioma% ")
    Optional<List<Libro>> BuscarLibrosPorIdioma(String idioma);
}
