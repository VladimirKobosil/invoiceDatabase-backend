package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatistics;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Metoda pro přidání nové faktury
    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }

    // Metoda pro získání všech faktur s volitelným filtrováním
    @GetMapping("/invoices")
    public List<InvoiceDTO> getAllInvoices(@ModelAttribute InvoiceFilter invoiceFilter) {
        return invoiceService.getAllInvoices(invoiceFilter);
    }

    // Metoda pro odstranění faktury podle ID
    @DeleteMapping("/invoices/{invoiceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeInvoice(@PathVariable Long invoiceId) {
        invoiceService.removeInvoice(invoiceId);
    }

    // Metoda pro získání faktury podle ID
    @GetMapping("/invoices/{invoiceId}")
    public InvoiceDTO getById(@PathVariable Long invoiceId) {
        return invoiceService.getById(invoiceId);
    }

    // Metoda pro získání nákupů spojených s identifikačním číslem
    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getPurchases(@PathVariable String identificationNumber) {
        return invoiceService.findReceivedInvoices(identificationNumber);
    }

    // Metoda pro získání prodejů spojených s identifikačním číslem
    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getSales(@PathVariable String identificationNumber) {
        return invoiceService.findIssuedInvoices(identificationNumber);
    }

    // Metoda pro úpravu existující faktury
    @PutMapping("/invoices/{invoiceId}")
    public InvoiceDTO editInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.editInvoice(invoiceId, invoiceDTO);
    }

    // Metoda pro získání statistik faktur
    @GetMapping("/invoices/statistics")
    public List<InvoiceStatistics> getInvoiceStatistics() {
        return invoiceService.getStatistics();
    }


}
