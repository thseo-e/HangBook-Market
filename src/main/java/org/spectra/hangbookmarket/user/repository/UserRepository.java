package org.spectra.hangbookmarket.user.repository;

import org.spectra.hangbookmarket.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByNameAndPassword(String name, String password);
}
