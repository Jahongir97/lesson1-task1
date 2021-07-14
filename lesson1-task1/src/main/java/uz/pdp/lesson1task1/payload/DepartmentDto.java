package uz.pdp.lesson1task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    @NotNull(message = "Department name should not be empty")
    private String name;

    @NotNull(message = "Company id should not be empty")
    private Integer companyId;
}
