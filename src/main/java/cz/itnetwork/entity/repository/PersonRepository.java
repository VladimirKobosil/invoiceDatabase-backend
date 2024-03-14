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
package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.PersonStatistics;
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    /**
     * Metoda pro hledání osob podle skrytého stavu.
     *
     * @param hidden hodnota skrytého stavu
     * @return seznam osob odpovídajících hledání
     */
    List<PersonEntity> findByHidden(boolean hidden);

    /**
     * Metoda pro hledání osob podle identifikačního čísla.
     *
     * @param identificationNumber identifikační číslo osoby
     * @return seznam osob odpovídajících hledání
     */
    List<PersonEntity> findByIdentificationNumber(String identificationNumber);

    /**
     * Metoda pro získání statistik prodejců osob.
     *
     * Tento dotaz používá JPQL (Java Persistence Query Language) a vytváří nové instance třídy PersonStatistics
     * na základě součtu cen faktur za minulý a aktuální rok.
     *
     * @return Seznam objektů PersonStatistics obsahujících statistiky prodejců osob.
     */
    @Query(value = """
        SELECT NEW cz.itnetwork.dto.PersonStatistics
            (person.id, person.name, COALESCE(SUM(CAST(invoice.price AS java.math.BigDecimal)), 0),
            COALESCE(SUM(CASE WHEN YEAR(invoice.issued) = YEAR(CURRENT_DATE()) - 1 THEN CAST(invoice.price AS java.math.BigDecimal) ELSE 0 END), 0))
        FROM person person
        LEFT JOIN invoice invoice
        ON person.id = invoice.seller.id
        WHERE person.hidden = 0
        GROUP BY person.id, person.name
        """)
    List<PersonStatistics> getSellerStatistics();

}
