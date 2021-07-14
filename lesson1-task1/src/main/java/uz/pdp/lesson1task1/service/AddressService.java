package uz.pdp.lesson1task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1task1.entity.Address;
import uz.pdp.lesson1task1.entity.Address;
import uz.pdp.lesson1task1.payload.ApiResponse;
import uz.pdp.lesson1task1.payload.AddressDto;
import uz.pdp.lesson1task1.repository.AddressRepository;
import uz.pdp.lesson1task1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    /**
     * In this method we are returning all addresses
     *
     * @return Addresses
     */

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    /**
     * In this method we are returning a address with given id
     *
     * @param id Integer
     * @return Address
     */

    public Address getAddressById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }

    /**
     * In this method we are creating new address
     *
     * @return ApiResponse
     * We are getting Json object.
     * putting Validation
     */

    public ApiResponse addAddress(AddressDto addressDto) {
        boolean exists = addressRepository.existsByStreetAndHouseNumber(addressDto.getStreet(), addressDto.getHouseNumber());
        if (exists) {
            return new ApiResponse("This address already exist", false);
        }
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHouseNumber(addressDto.getHouseNumber());
        addressRepository.save(address);
        return new ApiResponse("Address successfully added", true);
    }

    /**
     * Address editing method
     *
     * @param id
     * @param addressDto
     * @return ApiResponse
     * We are getting id and AddressDto Json Object.
     */

    public ApiResponse editAddress(Integer id, AddressDto addressDto) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address editingAddress = optionalAddress.get();
            editingAddress.setStreet(addressDto.getStreet());
            editingAddress.setHouseNumber(addressDto.getHouseNumber());
            addressRepository.save(editingAddress);
            return new ApiResponse("Successfully edited", true);
        }
        return new ApiResponse("Address not found", false);
    }

    /**
     * Deleting address by Id
     *
     * @param id
     * @return ApiResponse
     */

    public ApiResponse deleteAddress(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error!!!", false);

        }


    }


}
