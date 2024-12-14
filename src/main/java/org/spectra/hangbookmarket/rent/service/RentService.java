package org.spectra.hangbookmarket.rent.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.rent.domain.Rent;
import org.spectra.hangbookmarket.rent.repository.RentJpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentService
{
    private final RentJpaRepository rentJpaRepository;

    public void rentBook(Long bookId, Long userId)
    {

    }
}
