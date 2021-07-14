package uz.pdp.lesson1task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    @NotNull(message = "Company's name should not be empty")
    private String corpName;

    @NotNull(message = "Director's name should not be empty")
    private String directorName;

    @NotNull(message = "Address id should not be empty")
    private Integer addressId;
}
