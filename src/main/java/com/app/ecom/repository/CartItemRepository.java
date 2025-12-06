package com.app.ecom.repository;

import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    CartItem findByUserAndProduct(User user, Product product);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from CartItem c where c.user = :user and c.product = :product")
    void deleteByUserAndProduct(@Param("user") User user, @Param("product") Product product);

    void deleteByUser(User user);
}
