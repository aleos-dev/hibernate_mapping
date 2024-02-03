package dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isActive;
    private long addressId;
    private long storeId;
}
