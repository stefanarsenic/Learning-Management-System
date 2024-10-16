package org.example.address.mappers;

import org.example.address.dtos.CountryDto;
import org.example.address.model.Country;
import org.springframework.stereotype.Service;

@Service
public class CountryMapper implements MapperInterface<Country, CountryDto>{
    @Override
    public CountryDto toDto(Country entity) {
        return new CountryDto(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public Country toEntity(CountryDto dto) {
        Country country = new Country();
        country.setName(dto.getName());
        country.setId((dto.getId() != null) ? dto.getId() : null);

        return country;
    }
}
