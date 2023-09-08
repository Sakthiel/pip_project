package com.thoughtworks.sample.cart;

import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.cart.view.models.CartResponse;
import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
import com.thoughtworks.sample.users.repository.User;
import com.thoughtworks.sample.users.repository.UserRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;

    private ProductRepository productRepository;
@Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart addToCart(String userName , Integer productId , Integer quantity){
        Optional<User> userOptional  = userRepository.findByUsername(userName);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()){
            throw new RuntimeException("Product not found");
        }
        Product product = productOptional.get();

        Optional<Cart> cartItem = cartRepository.getCartByUserNameAndProductId(user.getId(),product.getId());
        if(cartItem.isPresent()) {
            return updateToCart(quantity, cartItem.get());
        }
        Cart cart = new Cart(product, user , quantity);
        return cartRepository.save(cart);
    }

    private Cart updateToCart(Integer quantity, Cart cart) {
            int oldQuantity = cart.getQuantity();
            cart.setQuantity(quantity + oldQuantity);
            return cartRepository.save(cart);
    }

    private boolean checkProductAlreadyAddedtoCart(User user, Product product) {
            Optional<Cart> cart = cartRepository.getCartByUserNameAndProductId(user.getId(),product.getId());
            return cart.isPresent();
    }

    public List<Cart> getCartItems() {
            return cartRepository.findAll();
    }

    public String delete(Long cartId) {
            Optional<Cart> cartItem = cartRepository.findById(cartId);
            if(cartItem.isEmpty()){
                throw new RuntimeException("Cart Item not found");
            }
            cartRepository.delete(cartItem.get());
            return "Deleted cartItem with id " + cartId;
    }

    public Cart decrementalUpdate(Cart cart , Long cartId){
        Optional<Cart> cartItem = cartRepository.findById(cartId);
        if(cartItem.isEmpty()){
            throw new RuntimeException("Cart Item not found");
        }
        return cartRepository.save(cart);
    }


}
