package vex.batch.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vex.batch.exceptions.BatchException;
import vex.batch.exceptions.ExceptionType;
import vex.batch.models.dtos.DollarDTO;
import vex.batch.models.enums.DollarOperation;
import vex.batch.models.enums.DollarType;
import vex.batch.services.DollarService;

import java.math.BigDecimal;

@Service
@Slf4j
public class DollarServiceImpl implements DollarService {

    private final RestTemplate restTemplate;

    public DollarServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public BigDecimal getDollarValue(DollarType type, DollarOperation operation) {
        try {
            String url = "https://dolarapi.com/v1/dolares/" + type.getName();
            DollarDTO dollarDTO = restTemplate.getForObject(url, DollarDTO.class);

            if (dollarDTO == null) return null;

            return getDollar(dollarDTO, operation);
        } catch (Exception e) {
            throw new BatchException(ExceptionType.DOLLAR_API_EXCEPTION, null, e);
        }
    }

    private BigDecimal getDollar(DollarDTO dollarDTO, DollarOperation operation) {
        return switch (operation) {
            case BUY -> dollarDTO.getCompra();
            case PURCHASE -> dollarDTO.getVenta();
        };
    }

}
