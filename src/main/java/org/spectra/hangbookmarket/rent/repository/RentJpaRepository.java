package org.spectra.hangbookmarket.rent.repository;

import org.spectra.hangbookmarket.rent.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentJpaRepository extends JpaRepository<Rent, Long>
{
}
