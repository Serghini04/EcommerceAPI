package com.serghini.store.repositories;

import org.springframework.data.repository.CrudRepository;
import com.serghini.store.entities.Cart;
import java.util.UUID;

public interface CartRepository extends CrudRepository<Cart, UUID> {
    
}