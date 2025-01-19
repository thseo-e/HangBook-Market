package org.spectra.hangbookmarket.rent.repository;

import org.spectra.hangbookmarket.rent.domain.Rent;
import org.spectra.hangbookmarket.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentJpaRepository extends JpaRepository<Rent, Long>
{
    List<Rent> findAllByRentedUser(Users rentedUser);
}
