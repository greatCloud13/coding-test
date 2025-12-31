package com.seowon.coding.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private static final BigDecimal HUNDRED = new BigDecimal("100");
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String description;

    private BigDecimal price;
    
    private int stockQuantity;
    
    private String category;
    
    // Business logic
    public boolean isInStock() {
        return stockQuantity > 0;
    }
    
    public void decreaseStock(int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive: " + quantity);
        }
        if (quantity > stockQuantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        stockQuantity -= quantity;
    }
    
    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        stockQuantity += quantity;
    }


    public BigDecimal getSubtotal(Integer qty){
        return price.multiply(BigDecimal.valueOf(qty));
    }

    public void validIds(List<Long> productIds){
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("empty productIds");
        }
    }

    public void priceChange(BigDecimal percentage, BigDecimal vat, boolean includeTax){
        BigDecimal base = getPrice() == null ? BigDecimal.ZERO : getPrice();


        BigDecimal changeRate = percentage.divide(HUNDRED, 4, RoundingMode.HALF_UP);

        BigDecimal changed = base.add(base.multiply(changeRate));

        if(includeTax){
            changed = changed.multiply(vat);
        }

        BigDecimal newPrice = changed.setScale(2, RoundingMode.HALF_UP);

        setPrice(newPrice);
    }


}