package org.spectra.hangbookmarket.user.repository;

import java.util.Optional;

import org.spectra.hangbookmarket.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long>
{
    Optional<Users> findByNameAndPassword(String name, String password);
}
