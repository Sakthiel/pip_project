package com.thoughtworks.sample.cart;

import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.cart.utility.EmailService;
import com.thoughtworks.sample.cart.utility.PdfGenerator;
import com.thoughtworks.sample.cart.view.models.CartResponse;
import com.thoughtworks.sample.customer.repository.Customer;
import com.thoughtworks.sample.customer.repository.CustomerRepository;
import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
import com.thoughtworks.sample.users.UserPrincipalService;
import com.thoughtworks.sample.users.repository.User;
import com.thoughtworks.sample.users.repository.UserRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;

    private ProductRepository productRepository;

    private UserPrincipalService userPrincipalService;

    private EmailService emailService;

    private CustomerRepository customerRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, UserPrincipalService userPrincipalService, EmailService emailService, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userPrincipalService = userPrincipalService;
        this.emailService = emailService;
        this.customerRepository = customerRepository;
    }


    public Cart addToCart(String userName, Integer productId, Integer quantity) {
        Optional<User> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        Product product = productOptional.get();

        Optional<Cart> cartItem = cartRepository.getCartByUserNameAndProductId(user.getId(), product.getId());
        if (cartItem.isPresent()) {
            return updateToCart(quantity, cartItem.get());
        }
        Cart cart = new Cart(product, user, quantity);
        return cartRepository.save(cart);
    }

    private Cart updateToCart(Integer quantity, Cart cart) {
        int oldQuantity = cart.getQuantity();
        cart.setQuantity(quantity + oldQuantity);
        return cartRepository.save(cart);
    }

    private boolean checkProductAlreadyAddedtoCart(User user, Product product) {
        Optional<Cart> cart = cartRepository.getCartByUserNameAndProductId(user.getId(), product.getId());
        return cart.isPresent();
    }

    public List<Cart> getCartItems(String userName) {

        User user = userPrincipalService.findUserByUsername(userName);

        Long user_id = user.getId();

        return cartRepository.findByUserId(user_id);
    }

    public String delete(Long cartId) {
        Optional<Cart> cartItem = cartRepository.findById(cartId);
        if (cartItem.isEmpty()) {
            throw new RuntimeException("Cart Item not found");
        }
        cartRepository.delete(cartItem.get());
        return "Deleted cartItem with id " + cartId;
    }

    public Cart decrementalUpdate(Cart cart, Long cartId) {
        Optional<Cart> cartItem = cartRepository.findById(cartId);
        if (cartItem.isEmpty()) {
            throw new RuntimeException("Cart Item not found");
        }
        return cartRepository.save(cart);
    }


    public void sendMail(List<Cart> cartItems) {
        PdfGenerator generator = new PdfGenerator();

        try {
            generator.generate(cartItems);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No items found in the cart");
        }
        Cart item = cartItems.get(0);
        User user = item.getUser();

        List<Customer> customerList = customerRepository.getDetailsByUserid(user.getId());

        if(customerList.isEmpty()){
            throw new RuntimeException("No Customer Found");
        }
        Customer customer = customerList.get(0);

        emailService.sendMailWithBillPdf(customer);
    }

    public ResponseEntity<?> deleteAllByUser(String userName) {
        User user = userPrincipalService.findUserByUsername(userName);

        Long user_id = user.getId();

        try {
            cartRepository.deleteItemsByUserId(user_id);
            return ResponseEntity.ok("Cart items deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting cart items");
        }
    }
}
