package com.java.backend.mapper;

import com.java.backend.dto.PersonDTO;
import com.java.backend.model.Person;
import com.java.backend.model.Address;
import org.springframework.stereotype.Component;


@Component
public class PersonMapper {
    public PersonDTO toDto(Person person) {
        if (person == null) {
            return null;
        }

        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setEmail(person.getEmail());
        dto.setUserName(person.getUserName());
        dto.setContactNumber(person.getContactNumber());

        // Mapping Nested Address fields to Flattened DTO fields
        if (person.getAddress() != null) {
            Address address = person.getAddress();
            dto.setStreetAddress(address.getStreetAddress()); // Assuming Address has getStreet()
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setCountry(address.getCountry());
        }

        // Mapping Role object to a simple String
        dto.setRoleName(person.getRole().getName()); // Assuming Role has getName()
        dto.setAge(person.getAge());
        return dto;
    }
}