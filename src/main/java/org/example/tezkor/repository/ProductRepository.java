package org.example.tezkor.repository;

import org.example.tezkor.enums.Category;
import org.example.tezkor.enums.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByShopId(Long shopId);
    List<Product> findByShopIdAndCategoryId(Long shopId, Long categoryId);
    // ✅ YANGI
    Product findByIdAndShopId(Long productId, Long shopId);
    List<Product> findByCategory(Category category);
}
