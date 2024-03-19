/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper; // Mapper pro mapování mezi DTO a entitami osob
    @Autowired
    private PersonRepository personRepository; // Repozitář pro osoby

    // Metoda pro přidání nové osoby
    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        // Mapování DTO na entitu
        PersonEntity entity = personMapper.toEntity(personDTO);
        // Uložení entity osoby do databáze
        entity = personRepository.save(entity);
        // Mapování entity na DTO a návrat
        return personMapper.toDTO(entity);
    }

    // Metoda pro odstranění osoby
    @Override
    public void removePerson(long personId) {
        try {
            // Nalezení osoby podle ID
            PersonEntity person = fetchPersonById(personId);
            // Nastavení atributu "hidden" na true pro skrytí osoby
            person.setHidden(true);
            // Uložení změn do databáze
            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // Ignorování výjimky, pokud osoba nebyla nalezena
        }
    }

    // Metoda pro získání všech ne-skrytých osob
    @Override
    public List<PersonDTO> getAllPersons() {
        // Získání seznamu osob, které nejsou skryté, a jejich mapování na DTO
        return personRepository.findByHidden(false)
                .stream()
                .map(i -> personMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    // Metoda pro získání osoby podle ID
    @Override
    public PersonDTO getPerson(long id) {
        // Nalezení osoby podle ID a její mapování na DTO
        PersonEntity personEntity = fetchPersonById(id);
        return personMapper.toDTO(personEntity);
    }

    // Metoda pro úpravu osoby
    @Override
    public PersonDTO editPerson(Long personId, PersonDTO personDTO) {

        // Nalezení osoby podle ID
        PersonEntity person = fetchPersonById(personId);
        // Nastavení atributu "hidden" na true pro skrytí osoby
        person.setHidden(true);
        // Uložení změn do databáze
        personRepository.save(person);

        // Vytvoření nové entity na základě DTO
        personDTO.setId(null);
        PersonEntity entity = personMapper.toEntity(personDTO);
        // Uložení entity do databáze a mapování na DTO pro návrat
        PersonEntity saved = personRepository.save(entity);
        return personMapper.toDTO(saved);
    }

    // Metoda pro získání statistik osob
    @Override
    public List<PersonStatisticsDTO> getPersonStatistics() {
        // Získání statistik osob z repozitáře
        return personRepository.getSellerStatistics();
    }

    // Privátní metoda pro načtení osoby podle ID
    private PersonEntity fetchPersonById(long id) {
        // Nalezení osoby podle ID nebo vyhození výjimky, pokud není nalezena
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
}