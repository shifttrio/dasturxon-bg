package org.example.tezkor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tezkor.dto.ContactRequestDto;
import org.example.tezkor.dto.ContactResponseDto;
import org.example.tezkor.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    /**
     * Telefon raqam bo'yicha kontaktni tekshirish
     * GET /api/contacts/check?phone=+998901234567
     */
    @GetMapping("/check")
    public ResponseEntity<ContactResponseDto> checkContact(@RequestParam String phone) {
        log.info("Checking contact with phone number: {}", phone);
        ContactResponseDto response = contactService.getContactByPhoneNumber(phone);
        return ResponseEntity.ok(response);
    }

    /**
     * Kontakt yaratish yoki yangilash
     * POST /api/contacts
     * Body: { "phoneNumber": "+998901234567", "firstName": "John", "lastName": "Doe" }
     */
    @PostMapping
    public ResponseEntity<ContactResponseDto> createOrUpdateContact(@RequestBody ContactRequestDto requestDto) {
        log.info("Creating or updating contact with phone number: {}", requestDto.getPhoneNumber());

        if (requestDto.getPhoneNumber() == null || requestDto.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ContactResponseDto response = contactService.saveOrUpdateContact(requestDto);
        HttpStatus status = response.isNewContact() ? HttpStatus.CREATED : HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }

    /**
     * Telefon raqamni yangilash
     * PATCH /api/contacts/update-phone
     * Body: { "oldPhone": "+998901234567", "newPhone": "+998909876543" }
     */
    @PatchMapping("/update-phone")
    public ResponseEntity<ContactResponseDto> updatePhoneNumber(
            @RequestParam String oldPhone,
            @RequestParam String newPhone) {

        log.info("Updating phone number from {} to {}", oldPhone, newPhone);

        try {
            ContactResponseDto response = contactService.updatePhoneNumber(oldPhone, newPhone);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error updating phone number: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Last login yangilash
     * POST /api/contacts/login?phone=+998901234567
     */
    @PostMapping("/login")
    public ResponseEntity<Void> updateLastLogin(@RequestParam String phone) {
        log.info("Updating last login for phone number: {}", phone);

        try {
            contactService.updateLastLogin(phone);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Error updating last login: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Kontakt mavjudligini tekshirish
     * GET /api/contacts/exists?phone=+998901234567
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> contactExists(@RequestParam String phone) {
        log.info("Checking if contact exists with phone number: {}", phone);
        boolean exists = contactService.contactExists(phone);
        return ResponseEntity.ok(exists);
    }
}