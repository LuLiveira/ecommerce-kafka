package br.com.lucas.ecommerce.models;

import java.math.BigDecimal;

public class Order {

    private String userId, orderId;
    private BigDecimal amount;

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getEmail() {
        return "";
    }
}
