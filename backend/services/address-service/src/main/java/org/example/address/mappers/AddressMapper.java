package org.example.address.mappers;

import org.example.address.dtos.AddressDto;
import org.example.address.dtos.CityDto;
import org.example.address.dtos.CountryDto;
import org.example.address.model.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper implements MapperInterface<Address, AddressDto>{

    @Override
    public AddressDto toDto(Address entity) {
        return new AddressDto(
                entity.getId(),
                entity.getUlica(),
                entity.getBroj(),
                new CityDto(
                        entity.getCity().getId(),
                        entity.getCity().getName(),
                        new CountryDto(
                                entity.getCity().getCountry().getId(),
                                entity.getCity().getCountry().getName()
                        )
                )
        );
    }

    @Override
    public Address toEntity(AddressDto dto) {
        return new Address(
                dto.getId(),
                dto.getUlica(),
                dto.getBroj(),
                null
        );
    }
}
