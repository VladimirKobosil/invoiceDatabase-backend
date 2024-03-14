package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatistics;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;

import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceMapper invoiceMapper; // Mapper pro mapování mezi DTO a entitami
    @Autowired
    private PersonRepository personRepository; // Repozitář pro osoby
    @Autowired
    private InvoiceRepository invoiceRepository; // Repozitář pro faktury

    // Metoda pro přidání nové faktury
    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        // Mapování DTO na entitu
        InvoiceEntity entity = invoiceMapper.toEntity(invoiceDTO);
        // Mapování osob na fakturu
        mapPersonsToInvoice(entity, invoiceDTO);
        // Uložení entity faktury
        InvoiceEntity saved = invoiceRepository.save(entity);
        // Mapování entity na DTO a návrat
        return invoiceMapper.toDTO(saved);
    }

    // Metoda pro odebrání faktury
    @Override
    public InvoiceDTO removeInvoice(long id) {
        // Nalezení faktury podle ID
        InvoiceEntity invoice = invoiceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        // Mapování entity na DTO
        InvoiceDTO model = invoiceMapper.toDTO(invoice);
        // Smazání faktury z databáze
        invoiceRepository.delete(invoice);
        // Návrat DTO
        return model;
    }

    // Metoda pro získání faktury podle ID
    @Override
    public InvoiceDTO getById(Long id) {
        // Nalezení faktury podle ID
        InvoiceEntity invoice = fetchInvoiceById(id);
        // Mapování entity na DTO a návrat
        return invoiceMapper.toDTO(invoice);
    }

    // Privátní metoda pro načtení faktury podle ID
    private InvoiceEntity fetchInvoiceById(long id) {
        // Nalezení faktury podle ID nebo vyhození výjimky, pokud není nalezena
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " wasn't found in the database."));
    }

    // Metoda pro získání všech faktur s možností filtru
    @Override
    public List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter) {
        // Vytvoření specifikace filtru faktury
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);
        // Získání seznamu faktur podle specifikace a stránkování
        return invoiceRepository.findAll(invoiceSpecification, PageRequest.of(0, invoiceFilter.getLimit()))
                .stream()
                .map(b -> invoiceMapper.toDTO(b))
                .collect(Collectors.toList());
    }

    // Metoda pro nalezení vydaných faktur podle identifikačního čísla
    @Override
    public List<InvoiceDTO> findIssuedInvoices(String identificationNumber) {
        // Získání osob podle identifikačního čísla
        List<PersonEntity> persons = personRepository.findByIdentificationNumber(identificationNumber);
        // Získání seznamu vydaných faktur osob a mapování na DTO
        return persons
                .stream()
                .map(PersonEntity::getSales)
                .flatMap(Collection::stream)
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    // Metoda pro nalezení přijatých faktur podle identifikačního čísla
    @Override
    public List<InvoiceDTO> findReceivedInvoices(String identificationNumber) {
        // Získání osob podle identifikačního čísla
        List<PersonEntity> persons = personRepository.findByIdentificationNumber(identificationNumber);
        // Získání seznamu přijatých faktur osob a mapování na DTO
        return persons
                .stream()
                .map(PersonEntity::getPurchase)
                .flatMap(Collection::stream)
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    // Metoda pro mapování osob na fakturu
    public void mapPersonsToInvoice(InvoiceEntity invoice, InvoiceDTO invoiceDTO) {
        // Získání prodávajícího a kupujícího a nastavení na fakturu
        PersonEntity seller = personRepository.getReferenceById(invoiceDTO.getSeller().getId());
        PersonEntity buyer = personRepository.getReferenceById(invoiceDTO.getBuyer().getId());
        invoice.setSeller(seller);
        invoice.setBuyer(buyer);
    }

    // Metoda pro úpravu faktury
    @Override
    public InvoiceDTO editInvoice(long id, InvoiceDTO invoiceDTO) {
        // Nastavení ID faktury
        invoiceDTO.setId(id);
        // Získání reference na fakturu
        InvoiceEntity entity = invoiceRepository.getReferenceById(id);
        // Aktualizace entity faktury
        invoiceMapper.updateEntity(invoiceDTO, entity);
        // Mapování osob na fakturu
        mapPersonsToInvoice(entity, invoiceDTO);
        // Uložení faktury do databáze
        InvoiceEntity saved = invoiceRepository.save(entity);
        // Mapování entity na DTO a návrat
        return invoiceMapper.toDTO(saved);
    }

    // Metoda pro získání statistik faktur
    @Override
    public List<InvoiceStatistics> getStatistics() {
        // Získání statistik faktur z repozitáře
        return invoiceRepository.getInvoiceStatistics();
    }
}

