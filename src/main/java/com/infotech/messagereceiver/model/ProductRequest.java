package com.infotech.messagereceiver.model;

import com.infotech.messagereceiver.enums.PaymentMethod;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;
    private Integer quantity;
    private PaymentMethod paymentMethod;

}
