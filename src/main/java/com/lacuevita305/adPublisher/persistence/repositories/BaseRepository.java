package com.lacuevita305.adPublisher.persistence.repositories;

import com.lacuevita305.adPublisher.persistence.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity, ID extends Serializable> extends JpaRepository<E,ID> {

}
