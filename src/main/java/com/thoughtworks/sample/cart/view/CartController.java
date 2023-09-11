package com.thoughtworks.sample.cart.view;

import com.thoughtworks.sample.cart.CartService;
import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.view.models.CartRequest;
import com.thoughtworks.sample.handlers.model.ErrorResponse;
import com.thoughtworks.sample.handlers.model.SuccessResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{userName}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Cart> cartItems(@PathVariable String userName){
            return cartService.getCartItems(userName);
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(code = HttpStatus.OK)
    public String delete(@PathVariable Long cartId){
        return cartService.delete(cartId);
}

    @PutMapping("/{cartId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Cart update(@RequestBody Cart cartItem , @PathVariable Long cartId) {
        return cartService.decrementalUpdate(cartItem, cartId);
    }

    @PostMapping("/sendMail")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    public ResponseEntity<SuccessResponse> sendMail(@RequestBody List<Cart> cartItems){
        cartService.sendMail(cartItems);
        return new ResponseEntity<SuccessResponse>(new SuccessResponse("Email sent successfully!", null), HttpStatus.OK);
    }

    @DeleteMapping("/all/{userName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> deleteAll(@PathVariable String userName){
            return cartService.deleteAllByUser(userName);
    }
}
