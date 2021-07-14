package uz.pdp.lesson1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson1.entity.Customer;
import uz.pdp.lesson1.payload.ApiResponse;
import uz.pdp.lesson1.payload.CustomerDto;
import uz.pdp.lesson1.service.CustomerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * Bu yerda barcha mijozlar ro'yxatini qaytaramiz
     *
     * @return Customers
     */

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
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
    public ApiResponse addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.addCustomer(customerDto);
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
    public ApiResponse editCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerDto customerDto) {
        return customerService.editCustomer(id, customerDto);
    }

    /**
     * Mijozni o'chirish methodi Id orqali, yo'lda id beradi
     *
     * @param id
     * @return ApiResponse
     */

    @DeleteMapping("/{id}")
    public ApiResponse deleteCustomer(@PathVariable Integer id) {
        return customerService.deleteCustomer(id);
    }
}
