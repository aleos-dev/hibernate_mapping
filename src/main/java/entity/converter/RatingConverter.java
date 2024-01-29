package entity.converter;

import entity.enums.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {


    @Override
    public String convertToDatabaseColumn(Rating attribute) {

        return Objects.isNull(attribute) ? null : attribute.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {

        return Rating.fromString(dbData);
    }
}
