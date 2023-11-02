package com.quipux.playlist.repositories;

import com.quipux.playlist.models.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query("SELECT p FROM Playlist p WHERE p.nombre = :listname")
    Playlist findByName(@Param("listname") String listname);

    void deleteByNombre(String listname);
}

