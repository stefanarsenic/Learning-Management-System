package org.example.address.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.address.dtos.CountryDto;
import org.example.address.mappers.CountryMapper;
import org.example.address.model.Country;
import org.example.address.services.CountryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/address/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    private final CountryMapper countryMapper;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<CountryDto> dtos = countryService.findAll(pageable).map(countryMapper::toDto);
        return ResponseEntity.ok(dtos.getContent());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CountryDto countryDto){
        Country country = countryMapper.toEntity(countryDto);
        CountryDto saved =  countryMapper.toDto(countryService.save(country));

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
