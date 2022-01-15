package ru.gbcart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbcart.model.Cart;

public interface CartDao  extends JpaRepository<Cart, Long> {
}