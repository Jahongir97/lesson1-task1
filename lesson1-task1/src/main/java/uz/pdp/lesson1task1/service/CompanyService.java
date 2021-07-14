package uz.pdp.lesson1task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1task1.entity.Address;
import uz.pdp.lesson1task1.entity.Company;
import uz.pdp.lesson1task1.payload.ApiResponse;
import uz.pdp.lesson1task1.payload.CompanyDto;
import uz.pdp.lesson1task1.repository.AddressRepository;
import uz.pdp.lesson1task1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    /**
     * In this method we are returning all companies
     *
     * @return Companies
     */

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    /**
     * In this method we are returning a company with given id
     *
     * @param id Integer
     * @return Company
     */

    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    /**
     * In this method we are creating new company
     *
     * @return ApiResponse
     * We are getting Json object.
     * putting Validation
     */

    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists) {
            return new ApiResponse("Company with this name already exist, please choose other name", false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        optionalAddress.ifPresent(company::setAddress);
        Address address = company.getAddress();
        addressRepository.save(address);
        companyRepository.save(company);
        return new ApiResponse("Company successfully added", true);
    }

    /**
     * Company editing method
     *
     * @param id
     * @param companyDto
     * @return ApiResponse
     * We are getting id and CompanyDto Json Object.
     */

    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company editingCompany = optionalCompany.get();
            Address address = editingCompany.getAddress();
            editingCompany.setCorpName(companyDto.getCorpName());
            editingCompany.setDirectorName(companyDto.getDirectorName());
            Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
            optionalAddress.ifPresent(editingCompany::setAddress);
            addressRepository.save(address);
            companyRepository.save(editingCompany);
            return new ApiResponse("Successfully edited", true);
        }
        return new ApiResponse("Company not found", false);
    }

    /**
     * Deleting company by Id
     *
     * @param id
     * @return ApiResponse
     */

    public ApiResponse deleteCompany(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error!!!", false);

        }


    }


}
