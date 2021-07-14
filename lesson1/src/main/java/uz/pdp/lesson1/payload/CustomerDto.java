package uz.pdp.lesson1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @NotNull(message = "FullName bo'sh bo'lmasligi kerak")
    private String fullName;

    @NotNull(message = "PhoneNumber bo'sh bo'lmasligi kerak")
    private String phoneNumber;

    @NotNull(message = "Address bo'sh bo'lmasligi kerak")
    private String address;
}

