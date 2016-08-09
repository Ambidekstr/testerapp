package com.ambidekstr.testerapp.repository;

import com.ambidekstr.testerapp.domain.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by anatolii on 09.08.2016.
 */
public interface MetaDataRepository extends JpaRepository<MetaData,UUID>{
}
