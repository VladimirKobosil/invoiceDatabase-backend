package cz.itnetwork.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name = "invoice")
@Setter
@Getter
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int invoiceNumber;

    private Date issued;

    private Date dueDate;

    private String product;

    private Long price;

    private int vat;

    private String note;

    @ManyToOne
    private PersonEntity buyer;

    @ManyToOne
    private PersonEntity seller;

    private boolean hidden;


}
