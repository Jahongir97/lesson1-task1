package uz.pdp.lesson1task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1task1.entity.Company;
import uz.pdp.lesson1task1.entity.Department;
import uz.pdp.lesson1task1.payload.ApiResponse;
import uz.pdp.lesson1task1.payload.DepartmentDto;
import uz.pdp.lesson1task1.repository.CompanyRepository;
import uz.pdp.lesson1task1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    /**
     * In this method we are returning all departments
     *
     * @return Departments
     */

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * In this method we are returning a department with given id
     *
     * @param id Integer
     * @return Department
     */

    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    /**
     * In this method we are creating new department
     *
     * @return ApiResponse
     * We are getting Json object.
     * putting Validation
     */

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (exists) {
            return new ApiResponse("Department with this name and companyId already exist, please choose other name", false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        Department department = new Department();
        department.setName(departmentDto.getName());
        optionalCompany.ifPresent(department::setCompany);
        Company company = department.getCompany();
        companyRepository.save(company);
        departmentRepository.save(department);
        return new ApiResponse("Department successfully added", true);
    }

    /**
     * Department editing method
     *
     * @param id
     * @param departmentDto
     * @return ApiResponse
     * We are getting id and departmentDto Json Object.
     */

    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            Department editingDepartment = optionalDepartment.get();
            Company company = editingDepartment.getCompany();
            editingDepartment.setName(departmentDto.getName());
            Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
            optionalCompany.ifPresent(editingDepartment::setCompany);
            companyRepository.save(company);
            departmentRepository.save(editingDepartment);
            return new ApiResponse("Successfully edited", true);
        }
        return new ApiResponse("Department not found", false);
    }

    /**
     * Deleting department by Id
     *
     * @param id
     * @return ApiResponse
     */

    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error!!!", false);

        }


    }


}
