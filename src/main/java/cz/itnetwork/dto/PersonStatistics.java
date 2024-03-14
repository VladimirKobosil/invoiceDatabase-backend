package cz.itnetwork.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStatistics {
    private Long personId;
    private String personName;
    private BigDecimal revenue;
    private BigDecimal turnoverLastYear;
}
