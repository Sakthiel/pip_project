package com.thoughtworks.sample.cart;

import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.cart.utility.EmailService;
import com.thoughtworks.sample.cart.utility.PdfGenerator;
import com.thoughtworks.sample.customer.repository.Customer;
import com.thoughtworks.sample.customer.repository.CustomerRepository;
import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
import com.thoughtworks.sample.users.UserPrincipalService;
import com.thoughtworks.sample.users.repository.User;
import com.thoughtworks.sample.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    CartService cartService;

    CartRepository cartRepository;

    UserRepository userRepository;

    ProductRepository productRepository;

    UserPrincipalService userPrincipalService;

    CustomerRepository customerRepository;

    EmailService emailService;
    Cart cart;
    Product product;

    User user;

    Customer customer;
    int quantity = 2;
    @BeforeEach
    public void beforeEach(){
        cartRepository = mock(CartRepository.class);

        userRepository = mock(UserRepository.class);

        productRepository = mock(ProductRepository.class);

        userPrincipalService = mock(UserPrincipalService.class);

        emailService = mock(EmailService.class);

        customerRepository = mock(CustomerRepository.class);


        product = new Product("Apple" , "Fruit" , BigDecimal.valueOf(100));

        user = new User("testUser" , "User123");

        customer = new Customer("testUser" , "1234567890" , "testUser@gmail.com" , user );
        cart = new Cart(product , user , quantity);

        cartService = new CartService(cartRepository,userRepository,productRepository , userPrincipalService , emailService , customerRepository) ;

    }

    @Test
    public void should_save_cartItem(){
            Cart mockItem = mock(Cart.class);
            when(cartRepository.save(cart)).thenReturn(mockItem);
            when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
            when(productRepository.findById(1)).thenReturn(Optional.of(product));
            when(cartRepository.getCartByUserNameAndProductId(user.getId() , product.getId())).thenReturn(Optional.empty());

            Cart actualCartItem = cartService.addToCart("testUser" , 1 , quantity);

            verify(cartRepository).save(any(Cart.class));


    }

    @Test
    public void should_update_cartItem_if_already_exist(){
        Cart mockItem = mock(Cart.class);
        when(cartRepository.save(cart)).thenReturn(mockItem);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartRepository.getCartByUserNameAndProductId(user.getId() , product.getId())).thenReturn(Optional.of(cart));

        Cart actualCartItem = cartService.addToCart("testUser" , 1 , quantity);

        verify(cartRepository).save(cart);
        assertThat(actualCartItem , is(equalTo(mockItem)));
    }

    @Test
    public void should_return_all_cart_Items(){
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        when(userPrincipalService.findUserByUsername("testUser")).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(cartList);

        List<Cart> actualList = cartService.getCartItems("testUser");

        verify(cartRepository).findByUserId(user.getId());
        assertThat(actualList , is(equalTo(cartList))) ;


    }

    @Test
    public void should_delete_given_cart_item(){
        Long id = 1l;
        Cart mockCartItem = mock(Cart.class);
        when(cartRepository.findById(id)).thenReturn(Optional.of(mockCartItem));

        String message = cartService.delete(id);

        verify(cartRepository).delete(mockCartItem);
        assertThat(message , is(equalTo("Deleted cartItem with id " + id)));
    }

    @Test
    public void should_send_mail_with_pdf_to_user(){
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);

        PdfGenerator generator = mock(PdfGenerator.class);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        when(customerRepository.getDetailsByUserid(user.getId())).thenReturn(customers);

        cartService.sendMail(cartList);

        verify(emailService).sendMailWithBillPdf(customer);
    }
}
