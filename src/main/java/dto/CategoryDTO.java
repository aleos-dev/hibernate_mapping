package dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {

    private long id;
    private String name;
}
