package org.spectra.hangbookmarket.user.repository;

import java.util.Optional;

import org.spectra.hangbookmarket.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByNameAndPassword(String name, String password);
}
