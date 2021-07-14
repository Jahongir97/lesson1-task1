package uz.pdp.lesson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson1.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);

    boolean existsById(Integer id);
}
