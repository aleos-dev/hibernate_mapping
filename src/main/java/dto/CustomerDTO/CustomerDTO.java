package dto.CustomerDTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isActive;
    private long addressId;
    private long storeId;
}
