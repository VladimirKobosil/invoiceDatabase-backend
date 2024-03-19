package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;

import java.util.List;

public interface PersonService {

    PersonDTO addPerson(PersonDTO personDTO);

    void removePerson(long id);

    List<PersonDTO> getAllPersons();

    PersonDTO getPerson(long id);

    PersonDTO editPerson(Long personId, PersonDTO personDTO);

    public List<PersonStatisticsDTO> getPersonStatistics();

}
