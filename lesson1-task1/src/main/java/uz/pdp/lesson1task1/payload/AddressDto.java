package uz.pdp.lesson1task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotNull(message = "Street should not be empty")
    private String street;

    @NotNull(message = "House number should not be empty")
    private String houseNumber;

}
