package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {

    private final InvoiceFilter filter;

    /**
     * Metoda převádějící objekt filtru na kritéria (predikáty) pro dotazování faktur.
     *
     * @param root            kořenový objekt dotazu
     * @param query           dotaz
     * @param criteriaBuilder objekt pro vytváření kritérií
     * @return výsledná podmínka (predikát) pro dotaz
     */
    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        // Seznam predikátů pro podmínky filtru
        List<Predicate> predicates = new ArrayList<>();

        // Přidání podmínky pro minimální cenu faktury, pokud je zadána ve filtru
        if (filter.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
        }

        // Přidání podmínky pro maximální cenu faktury, pokud je zadána ve filtru
        if (filter.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
        }

        // Přidání podmínky pro identifikaci kupce, pokud je zadána ve filtru
        if (filter.getBuyerID() != null) {
            Join<PersonEntity, InvoiceEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), filter.getBuyerID()));
        }

        // Přidání podmínky pro identifikaci prodávajícího, pokud je zadána ve filtru
        if (filter.getSellerID() != null) {
            Join<PersonEntity, InvoiceEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), filter.getSellerID()));
        }

        // Přidání podmínky pro filtraci produktů v fakturách, pokud je zadán ve filtru
        if (filter.getProduct() != null && !filter.getProduct().isEmpty()) {
            Expression<String> productExpression = root.get("product");
            predicates.add(criteriaBuilder.like(productExpression, "%" + filter.getProduct() + "%"));
        }

        // Kombinace všech podmínek pomocí logického operátoru AND
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}

