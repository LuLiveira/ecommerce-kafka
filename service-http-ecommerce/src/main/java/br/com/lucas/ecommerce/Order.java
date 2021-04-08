package br.com.lucas.ecommerce;

import java.math.BigDecimal;

public class Order {

    private final String email, orderId;
    private final BigDecimal amount;

    public Order(String email, String orderId, BigDecimal amount) {
        this.email = email;
        this.orderId = orderId;
        this.amount = amount;
    }
}
