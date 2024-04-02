package vex.batch.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DollarType {
    BLUE("blue"),
    CCL("contadoconliqui"),
    OFICIAL("oficial"),
    MEP("bolsa"),
    CRIPTO("cripto"),
    WHOLESALER("mayorista");

    private final String name;
}
