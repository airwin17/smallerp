package main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.models.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

}
