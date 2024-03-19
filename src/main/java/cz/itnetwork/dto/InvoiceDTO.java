package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceDTO {

    @JsonProperty("_id")
    private long id;

    private int invoiceNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date issued;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;

    private String product;

    private Long price;

    private int vat;

    private String note;

    private PersonDTO buyer;

    private PersonDTO seller;


}
