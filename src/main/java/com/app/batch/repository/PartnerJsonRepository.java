package com.app.batch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.batch.model.PartnerJson;

@Repository
public interface PartnerJsonRepository extends JpaRepository<PartnerJson, Integer> {

	Optional<PartnerJson> findByPartnerName(String partnerName);
}
