package com.startup.bedok.advertisment.validator;

import com.startup.bedok.advertisment.model.request.AdvertisementMultisearch;
import com.startup.bedok.exceptions.InvalidDateRangeException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, AdvertisementMultisearch> {

    @Override
    public boolean isValid(AdvertisementMultisearch value, ConstraintValidatorContext context) {
        if (value.getDateFrom() != null && value.getDateTo() != null) {
            if(value.getDateFrom().isAfter(value.getDateTo())) {
                throw new InvalidDateRangeException();
            }

        }
        return true; // null values are considered valid
    }
}
