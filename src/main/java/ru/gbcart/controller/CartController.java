package ru.gbcart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gbcart.model.Cart;
import ru.gbcart.model.ProductDto;
import ru.gbcart.service.CartService;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> createCart() {
        Cart cart = new Cart();
        cartService.save(cart);
        cart.setStatus("From microservice " + cart.getId());
        cartService.save(cart);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/cart" + cart.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{cartId}")
    public void deleteCart(@PathVariable Long cartId) {
        cartService.deleteCartById(cartId);
    }

    @PostMapping(path = "/{cartId}", consumes = "application/json;charset=UTF-8")
    public void addProductToCart(@RequestBody ProductDto productDto, @PathVariable Long cartId) {
        Cart cart = cartService.findById(cartId).orElseThrow(NoSuchElementException::new);
        cart.addProduct(productDto);
        cartService.save(cart);
    }

    @DeleteMapping("/{cartId}/{productId}")
    public void deleteProductById(@PathVariable Long cartId, @PathVariable Long productId) {
        Cart cart = cartService.findById(cartId).orElseThrow(NoSuchElementException::new);
        cartService.deleteProductById(cart, productId);
        cartService.save(cart);
    }
}