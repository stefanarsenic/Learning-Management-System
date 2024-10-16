package org.example.address.services;

import lombok.RequiredArgsConstructor;
import org.example.address.model.Country;
import org.example.address.repositories.CountryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public Page<Country> findAll(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    public Country save(Country country){
        return countryRepository.save(country);
    }
}
