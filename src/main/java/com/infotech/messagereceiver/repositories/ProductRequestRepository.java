package com.infotech.messagereceiver.repositories;

import com.infotech.messagereceiver.entities.ProductRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequestEntity, UUID> {
}
