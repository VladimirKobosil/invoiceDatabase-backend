package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatistics;
import cz.itnetwork.entity.filter.InvoiceFilter;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    InvoiceDTO removeInvoice(long id);

    InvoiceDTO getById(Long id);

    public List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter);

    List<InvoiceDTO> findIssuedInvoices(String identificationNumber);

    List<InvoiceDTO> findReceivedInvoices(String identificationNumber);

    InvoiceDTO editInvoice(long id, InvoiceDTO invoiceDTO);

    public List<InvoiceStatistics> getStatistics();


}
