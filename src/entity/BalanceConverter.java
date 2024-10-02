package entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BalanceConverter implements AttributeConverter<Double, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Double attribute) {
        return (int) (attribute * 100);
    }

    @Override
    public Double convertToEntityAttribute(Integer dbData) {
        return dbData / 100.0;
    }


}
