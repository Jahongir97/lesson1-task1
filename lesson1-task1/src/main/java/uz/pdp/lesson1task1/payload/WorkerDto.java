package uz.pdp.lesson1task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDto {

    @NotNull(message = "Name should not be empty")
    private String name;

    @NotNull(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotNull(message = "Address id should not be empty")
    private Integer addressId;

    @NotNull(message = "Department id should not be empty")
    private Integer departmentId;
}
