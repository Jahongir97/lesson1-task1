package uz.pdp.lesson1task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson1task1.entity.Address;
import uz.pdp.lesson1task1.entity.Company;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    boolean existsByStreetAndHouseNumber(String street, String houseNumber);
}
