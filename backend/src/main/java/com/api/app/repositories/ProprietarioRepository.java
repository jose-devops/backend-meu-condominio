package com.api.app.repositories;

import com.api.app.models.ProprietarioModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProprietarioRepository extends JpaRepository<ProprietarioModel, Long> {
    @EntityGraph(attributePaths = {"usuario"})
    List<ProprietarioModel> findAll();
}
