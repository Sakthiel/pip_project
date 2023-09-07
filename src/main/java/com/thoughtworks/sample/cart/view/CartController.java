package com.thoughtworks.sample.cart.view;

import com.thoughtworks.sample.cart.CartService;
import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.view.models.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    CartService cartService;
@Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public Cart addToCart(@RequestBody CartRequest cartRequest){

        String userName = cartRequest.getUserName();
        Integer quantity = cartRequest.getQuantity();
        Integer productId = cartRequest.getProductId();

        return cartService.addToCart(userName,productId,quantity);

    }

    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public List<Cart> cartItems(){
            return cartService.getCartItems();
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(code = HttpStatus.OK)
    public String delete(@PathVariable Long cartId){
        return cartService.delete(cartId);
}

    @PutMapping("/{cartId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Cart update(@RequestBody Cart cartItem , @PathVariable Long cartId){
        return cartService.decrementalUpdate(cartItem , cartId);
    }
}
