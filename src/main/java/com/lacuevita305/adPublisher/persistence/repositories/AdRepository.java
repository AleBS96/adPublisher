package com.lacuevita305.adPublisher.persistence.repositories;



import com.lacuevita305.adPublisher.persistence.entities.Ad;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface AdRepository extends BaseRepository<Ad,Long>{

    boolean existsByTitleAndBody(String title, String body);
    List<Ad> findByProductName(String productName, Pageable pageable);
    List<Ad> findByProductNameAndPublishStatus(String productName,String status, Pageable pageable);
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE ad_to_publish SET publish_status = :status WHERE id = :id",
            nativeQuery = true
    )
    void updatePublishStatusById(Long id, String status);
}
