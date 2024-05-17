package com.infotech.messagereceiver.entities;

import com.infotech.messagereceiver.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_PRODUCT")
public class ProductRequestEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private Integer quantity;
    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

}
