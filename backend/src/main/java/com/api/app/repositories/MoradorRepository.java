package com.api.app.repositories;

import com.api.app.models.MoradorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MoradorRepository extends JpaRepository<MoradorModel, Long> {

}
