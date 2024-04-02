package vex.batch.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Parameters {
    INPUT_FILE_NAME("input.file.name"),
    UNIQUENESS("uniqueness"),
    START_AT("startAt"),
    BRANCH_ID("branchId"),
    PERCENTAGE("percentage");

    private final String value;
}
