package com.bmlgold.challenge_literatura.repository;

import com.bmlgold.challenge_literatura.model.Autor;
import com.bmlgold.challenge_literatura.model.DatosAutor;
import com.bmlgold.challenge_literatura.model.DatosLibro;
import com.bmlgold.challenge_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT a FROM Autor a WHERE a.nombre ILIKE %:nombreAutor%")
    Optional<Autor> buscarAutor(String nombreAutor);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeMuerte >= :fecha AND a.fechaDeNacimiento <= :fecha")
    Optional<List<Autor>> BuscarAutoresPorFechas(String fecha);

}
