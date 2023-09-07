package com.thoughtworks.sample.cart;

import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
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

    Cart cart;
    Product product;

    User user;

    int quantity = 2;
    @BeforeEach
    public void beforeEach(){
        cartRepository = mock(CartRepository.class);

        userRepository = mock(UserRepository.class);

        productRepository = mock(ProductRepository.class);

        product = new Product("Apple" , "Fruit" , BigDecimal.valueOf(100));

        user = new User("testUser" , "User123");

        cart = new Cart(product , user , quantity);

        cartService = new CartService(cartRepository,userRepository,productRepository);

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
        when(cartRepository.findAll()).thenReturn(cartList);

        List<Cart> actualList = cartService.getCartItems();

        verify(cartRepository).findAll();
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
}
