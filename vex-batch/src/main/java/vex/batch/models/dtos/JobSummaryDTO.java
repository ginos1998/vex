package vex.batch.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSummaryDTO {
    private String jobName;
    private String startTime;
    private Long jobId;
    private Set<String> params;
    private String status;
}
