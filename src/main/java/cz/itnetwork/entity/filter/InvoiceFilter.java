package cz.itnetwork.entity.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Setter
public class InvoiceFilter {

    private Long buyerID;
    private Long sellerID;
    private String product;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer limit = 10;


}
