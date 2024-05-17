package com.infotech.messagereceiver.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {

    CURRENCY(1),
    PIX(2),
    CARD(3),
    CRYPTO(4);

    private final int codigoMetodo;

    PaymentMethod(int codigo) {
        codigoMetodo = codigo;
    }

}
