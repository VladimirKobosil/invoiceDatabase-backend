package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
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

    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {

        return invoiceService.addInvoice(invoiceDTO);
    }

    @GetMapping("/invoices")
    public List<InvoiceDTO> getAllInvoices(@ModelAttribute InvoiceFilter invoiceFilter) {
        return invoiceService.getAllInvoices(invoiceFilter);
    }

    @DeleteMapping("/invoices/{invoiceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeInvoice(@PathVariable Long invoiceId) {

        invoiceService.removeInvoice(invoiceId);
    }

    @GetMapping("/invoices/{invoiceId}")
    public InvoiceDTO getByInvoiceId(@PathVariable Long invoiceId) {

        return invoiceService.getByInvoiceId(invoiceId);
    }

    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getPurchases(@PathVariable String identificationNumber) {

        return invoiceService.findReceivedInvoices(identificationNumber);
    }

    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getSales(@PathVariable String identificationNumber) {

        return invoiceService.findIssuedInvoices(identificationNumber);
    }

    @PutMapping("/invoices/{invoiceId}")
    public InvoiceDTO editInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO) {

        return invoiceService.editInvoice(invoiceId, invoiceDTO);
    }

    @GetMapping("/invoices/statistics")
    public List<InvoiceStatisticsDTO> getInvoiceStatistics() {

        return invoiceService.getInvoiceStatistics();
    }

}
