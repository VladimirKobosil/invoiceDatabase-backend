package cz.itnetwork.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatisticsDTO {

    private Long allTime;
    private Long currentYear;
    private Long allTimeCount;

}
