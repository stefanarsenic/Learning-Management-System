package org.example.address.mappers;

import org.example.address.dtos.CityDto;
import org.example.address.dtos.CountryDto;
import org.example.address.model.City;
import org.springframework.stereotype.Service;

@Service
public class CityMapper implements MapperInterface<City, CityDto> {
    @Override
    public CityDto toDto(City entity) {
        return new CityDto(
                entity.getId(),
                entity.getName(),
                new CountryDto(
                        entity.getCountry().getId(),
                        entity.getCountry().getName()
                )
        );
    }

    @Override
    public City toEntity(CityDto dto) {
        return new City(
                dto.getId(),
                dto.getName(),
                null
        );
    }
}
