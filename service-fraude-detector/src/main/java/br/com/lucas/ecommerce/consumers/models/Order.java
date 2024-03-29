package br.com.lucas.ecommerce.consumers.models;

import java.math.BigDecimal;

public class Order {

    private final String email;
    private final String orderId;
    private final BigDecimal amount;

    public Order(String email, String orderId, BigDecimal amount) {
        this.email = email;
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
