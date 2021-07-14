package uz.pdp.lesson1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.lesson1.entity.Customer;
import uz.pdp.lesson1.payload.ApiResponse;
import uz.pdp.lesson1.payload.CustomerDto;
import uz.pdp.lesson1.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    /**
     * Bu yerda barcha mijozlar ro'yxatini qaytaramiz
     *
     * @return Customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    /**
     * Id orqali bitta mijozni qaytaramiz
     *
     * @param id Integer
     * @return Customer
     * Agar id orqali topmasa null qaytadi
     */
    public Customer getCustomer(Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElse(null);
    }


    /**
     * Mijoz qo'shadigan method
     *
     * @return ApiResponse
     * Bizga CustomerDto tipida Json object keladi.
     * Validatsiya qo'ydik
     */
    public ApiResponse addCustomer(CustomerDto customerDto) {
        boolean exists = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (exists) {
            return new ApiResponse("Bunday mijoz mavjud", false);
        }
        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);
        return new ApiResponse("Mijoz saqlandi", true);


    }


    /**
     * Mijozni tahrirlash methodi
     *
     * @param id
     * @param customerDto
     * @return ApiResponse
     * Bizga yo'lda Id va Request bodyda CustomerDto tipida Json object beradi.
     */

    public ApiResponse editCustomer(Integer id, CustomerDto customerDto) {
        boolean existsByPhoneNumberAndIdNot = customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot) {
            return new ApiResponse("Bunday telefon raqamli mijoz mavjud", false);
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()) {
            return new ApiResponse("Bunday mijoz mavjud emas", false);
        }

        Customer customer = optionalCustomer.get();
        customer.setFullName(customerDto.getFullName());
        customer.setAddress(customerDto.getAddress());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customerRepository.save(customer);
        return new ApiResponse("Mijoz tahrirlandi", true);
    }

    /**
     * Mijozni o'chirish methodi Id orqali, yo'lda id beradi
     *
     * @param id
     * @return ApiResponse
     */

    public ApiResponse deleteCustomer(Integer id) {
        try {
            customerRepository.deleteById(id);
            return new ApiResponse("Mijoz o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik", false);

        }


    }
}
