package ru.gbcart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gbcart.controller.CartController;
import ru.gbcart.dao.CartDao;
import ru.gbcart.model.Cart;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartDao cartDao;

    public Cart save(Cart cart) {
        cartDao.save(cart);
        return cart;
    }

    public void deleteCartById(Long cartId) {
        cartDao.deleteById(cartId);
    }

    public Optional<Cart> findById(Long cartId) {
        return cartDao.findById(cartId);
    }

    public void deleteProductById(Cart cart, Long id) {
        cart.getProducts().removeIf(product -> product.getId().equals(id));
        cartDao.save(cart);
    }
}