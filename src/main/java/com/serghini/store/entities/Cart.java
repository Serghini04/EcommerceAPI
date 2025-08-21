package com.serghini.store.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="carts")
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID    id;
    
    @Column(name = "date_created", insertable=false, updatable=false)
    private Date    dateCreated;

    @OneToMany(mappedBy="cart", cascade= CascadeType.MERGE, orphanRemoval=true, fetch= FetchType.EAGER)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal   getTotalPrice() {
        return items.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Information Expert Principle :
    public CartItem getItem(Long productId) {
        return items.stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElse(null);
    }
    public CartItem addItem(Product product, Integer quantity){
        CartItem cartItem = getItem(product.getId());
        if (cartItem != null)
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(this);
            items.add(cartItem);
        }
        return cartItem;
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

}
