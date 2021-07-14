package uz.pdp.lesson1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson1.entity.Customer;
import uz.pdp.lesson1.payload.ApiResponse;
import uz.pdp.lesson1.payload.CustomerDto;
import uz.pdp.lesson1.service.CustomerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerControllerSecond {

    @Autowired
    CustomerService customerService;

    /**
     * Bu yerda barcha mijozlar ro'yxatini qaytaramiz
     *
     * @return Customers
     */

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Id orqali bitta mijozni qaytaramiz
     *
     * @param id Integer
     * @return Customer
     */

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Integer id) {
        return customerService.getCustomer(id);
    }

    /**
     * Mijoz qo'shadigan method
     *
     * @return ApiResponse
     * Bizga CustomerDto tipida Json object keladi.
     * Validatsiya qo'ydik
     */

    @PostMapping
    public HttpEntity<ApiResponse> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        ApiResponse apiResponse = customerService.addCustomer(customerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Mijozni tahrirlash methodi
     *
     * @param id
     * @param customerDto
     * @return ApiResponse
     * Bizga yo'lda Id va Request bodyda CustomerDto tipida Json object beradi.
     */

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerDto customerDto) {
        ApiResponse apiResponse = customerService.editCustomer(id, customerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Mijozni o'chirish methodi Id orqali, yo'lda id beradi
     *
     * @param id
     * @return ApiResponse
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        ApiResponse apiResponse = customerService.deleteCustomer(id);

        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }
}
