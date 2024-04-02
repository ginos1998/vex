package vex.batch.services;

import vex.batch.models.enums.DollarOperation;
import vex.batch.models.enums.DollarType;

import java.math.BigDecimal;

public interface DollarService {
    BigDecimal getDollarValue(DollarType type, DollarOperation operation);
}
