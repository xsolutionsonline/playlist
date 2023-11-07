package com.quipux.playlist.repositories;

import com.quipux.playlist.models.entities.Playlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Playlist findByNameIgnoreCase(String listname);

    @Transactional
    void deleteByNameIgnoreCase(String listname);
}

