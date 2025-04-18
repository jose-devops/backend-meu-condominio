package com.api.app.repositories;

import com.api.app.models.InquilinoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InquilinoRepository extends JpaRepository<InquilinoModel, Long> {
    Optional<InquilinoModel> findByEmail(String email);
}
