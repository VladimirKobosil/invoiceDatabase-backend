package cz.itnetwork.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatistics {

    private Long allTime;
    private Long currentYear;
    private Long allTimeCount;

}
