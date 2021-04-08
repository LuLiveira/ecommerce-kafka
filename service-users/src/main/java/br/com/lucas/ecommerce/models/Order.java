package br.com.lucas.ecommerce.models;

import java.math.BigDecimal;

public class Order {

    private String orderId;
    private BigDecimal amount;
    private String email;

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", amount=" + amount +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }
}
