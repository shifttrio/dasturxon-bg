package org.example.tezkor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.example.tezkor.dto.CreateProductRequest;
import org.example.tezkor.enums.Category;
import org.example.tezkor.enums.Product;
import org.example.tezkor.enums.Shop;
import org.example.tezkor.enums.User;
import org.example.tezkor.repository.UserRepository;
import org.example.tezkor.service.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;

    // ✅ Admin o‘z shoplarini ko‘radi
    @GetMapping("/shops")
    @Operation(summary = "Get my shops")
    public List<Shop> getMyShops() {
        User admin = getCurrentAdmin();
        return adminService.getAdminShops(admin.getId());
    }

//    // ✅ Admin o‘z shop buyurtmalarini ko‘radi
//    @GetMapping("/shop/orders")
//    public List<?> getMyShopOrders() {
//        User admin = getCurrentAdmin();
//
//        Shop shop = adminService.getAdminShops(admin.getId())
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("No shop assigned to admin"));
//
//        return adminService.getShopOrders(shop.getId(), admin.getId());
//    }
//
//    // ✅ Buyurtma statusini o‘zgartirish
//    @PostMapping("/order/{orderId}/status")
//    public String updateOrderStatus(@PathVariable Long orderId,
//                                    @RequestParam String status) {
//        User admin = getCurrentAdmin();
//        return adminService.updateOrderStatus(orderId, admin.getId(), status);
//    }

    // ✅ Product qo‘shish (image + category bilan)
    @PostMapping(value = "/product", consumes = {"multipart/form-data"})
    @Operation(
            summary = "Add product to shop",
            description = "Admin uploads a product with image",
            requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data"))
    )
    public Product addProduct(@ModelAttribute CreateProductRequest request) {
        User admin = getCurrentAdmin();
        return adminService.addProductToShop(request, admin.getId());
    }

    // ✅ Barcha productlar
    @GetMapping("/products")
    @Operation(summary = "Get all products")
    public List<Product> getAllProducts() {
        return adminService.getAllProducts();
    }

    // ✅ Category yaratish
    @PostMapping("/category")
    @Operation(summary = "Create category")
    public Category createCategory(@RequestParam String name) {
        return adminService.createCategory(name);
    }

    @GetMapping("/categories")
    @Operation(summary = "Get all categories")
    public List<Category> getAllCategories() {
        return adminService.getAllCategories();
    }


    // 🔐 Hozirgi adminni JWT orqali olish
    private User getCurrentAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String phone = (String) auth.getPrincipal();

        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }
}


