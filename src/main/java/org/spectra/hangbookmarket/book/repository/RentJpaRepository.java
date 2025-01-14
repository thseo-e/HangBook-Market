package org.spectra.hangbookmarket.book.repository;

import org.spectra.hangbookmarket.book.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentJpaRepository extends JpaRepository<Rent, Long>
{
}
