package uz.pdp.lesson1task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1task1.entity.Address;
import uz.pdp.lesson1task1.entity.Department;
import uz.pdp.lesson1task1.entity.Worker;
import uz.pdp.lesson1task1.payload.ApiResponse;
import uz.pdp.lesson1task1.payload.WorkerDto;
import uz.pdp.lesson1task1.repository.AddressRepository;
import uz.pdp.lesson1task1.repository.DepartmentRepository;
import uz.pdp.lesson1task1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    /**
     * In this method we are returning all workers
     *
     * @return Workers
     */

    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }

    /**
     * In this method we are returning a worker with given id
     *
     * @param id Integer
     * @return Worker
     */

    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    /**
     * In this method we are creating new worker
     *
     * @return ApiResponse
     * We are getting Json object.
     * putting Validation
     */

    public ApiResponse addWorker(WorkerDto workerDto) {
        boolean exists = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (exists) {
            return new ApiResponse("Worker with this phone number already exist, please enter other number", false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        optionalAddress.ifPresent(worker::setAddress);
        Address address = worker.getAddress();
        addressRepository.save(address);
        optionalDepartment.ifPresent(worker::setDepartment);
        Department department = worker.getDepartment();
        departmentRepository.save(department);
        workerRepository.save(worker);
        return new ApiResponse("Worker successfully added", true);
    }

    /**
     * Worker editing method
     *
     * @param id
     * @param workerDto
     * @return ApiResponse
     * We are getting id and WorkerDto Json Object.
     */

    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()) {
            Worker editingWorker = optionalWorker.get();
            Address address = editingWorker.getAddress();
            Department department=editingWorker.getDepartment();
            editingWorker.setName(workerDto.getName());
            editingWorker.setPhoneNumber(workerDto.getPhoneNumber());
            Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
            optionalAddress.ifPresent(editingWorker::setAddress);
            addressRepository.save(address);
            Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
            optionalDepartment.ifPresent(editingWorker::setDepartment);
            departmentRepository.save(department);
            workerRepository.save(editingWorker);
            return new ApiResponse("Successfully edited", true);
        }
        return new ApiResponse("Worker not found", false);
    }

    /**
     * Deleting worker by Id
     *
     * @param id
     * @return ApiResponse
     */

    public ApiResponse deleteWorker(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error!!!", false);

        }


    }


}
