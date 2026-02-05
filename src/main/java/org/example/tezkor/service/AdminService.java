package org.example.tezkor.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tezkor.dto.CreateProductRequest;
import org.example.tezkor.enums.Category;
import org.example.tezkor.enums.Product;
import org.example.tezkor.enums.Shop;
import org.example.tezkor.repository.CategoryRepository;
import org.example.tezkor.repository.ProductRepository;
import org.example.tezkor.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    // Admin o'z shoplarini oladi
    public List<Shop> getAdminShops(Long adminId) {
        return shopRepository.findByAdminId(adminId);
    }



    @Transactional
    public Product addProductToShop(CreateProductRequest request, Long adminId) {

        Shop shop = shopRepository.findFirstByAdminId(adminId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        Category category = categoryRepository.findByName(request.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setWeight(request.getWeight());
        product.setPrepareTime(request.getPrepareTime());

        product.setImageUrl(request.getImageUrl());
        product.setShop(shop);
        product.setCategory(category);

        return productRepository.save(product);
    }



    @Transactional
    public Category createCategory(String name) {
        categoryRepository.findByName(name).ifPresent(c -> {
            throw new RuntimeException("Category already exists");
        });

        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    // Barcha productlarni olish
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private Shop getAdminShop(Long adminId) {
        return shopRepository.findByAdminId(adminId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin has no shop"));
    }

}
