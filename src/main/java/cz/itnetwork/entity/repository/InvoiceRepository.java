package cz.itnetwork.entity.repository;


import cz.itnetwork.dto.InvoiceStatistics;
import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {
    /**
     * Metoda pro získání statistik faktur.
     *
     * Tento dotaz používá JPQL (Java Persistence Query Language) a vytváří nové instance třídy InvoiceStatistics
     * na základě součtu cen všech faktur, součtu cen faktur v aktuálním roce a celkového počtu faktur.
     *
     * @return Seznam objektů InvoiceStatistics obsahujících statistiky faktur.
     */
    @Query(value = """
        SELECT NEW cz.itnetwork.dto.InvoiceStatistics(
        SUM(allTime.price),
        SUM(currentYear.price),
        COUNT(allTime.id))
        FROM invoice allTime
        LEFT JOIN invoice currentYear
        ON allTime.id = currentYear.id
        AND FUNCTION('YEAR', currentYear.issued) = FUNCTION('YEAR', CURRENT_DATE)
        """)
    List<InvoiceStatistics> getInvoiceStatistics();

}


