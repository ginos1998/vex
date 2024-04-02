package vex.batch.controllers;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vex.batch.models.dtos.JobSummaryDTO;
import vex.batch.models.enums.Parameters;

import java.util.Objects;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final JobLauncher jobLauncher;
    private final Job modifyProductPriceJob;
    private final Job importProductsCsv;

    @PostMapping("/import")
    public ResponseEntity<String> importProducts(@RequestParam("csvFilePath") String csvFilePath) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong(Parameters.START_AT.name(), System.currentTimeMillis())
                .addString(Parameters.INPUT_FILE_NAME.name(), csvFilePath)
                .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(importProductsCsv, jobParameters);
            String job = String.format("Job %s has been started with id=%s", importProductsCsv.getName() ,jobExecution.getJobId());
            return ResponseEntity.ok(job);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing products");
        }
    }

    @PutMapping("/modify-price")
    public ResponseEntity<JobSummaryDTO> modifyProductPrice(@RequestParam Integer branchId, @RequestParam Integer percentage) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong(Parameters.START_AT.name(), System.currentTimeMillis())
                .addLong(Parameters.BRANCH_ID.name(), branchId.longValue())
                .addLong(Parameters.PERCENTAGE.name(), percentage.longValue())
                .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(modifyProductPriceJob, jobParameters);
            return ResponseEntity.ok(JobSummaryDTO.builder()
                    .jobId(jobExecution.getJobId())
                    .jobName(jobExecution.getJobInstance().getJobName())
                    .startTime(Objects.requireNonNull(jobExecution.getStartTime()).toString())
                    .params(jobExecution.getJobParameters().getParameters().keySet())
                    .status(jobExecution.getStatus().name())
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(JobSummaryDTO.builder()
                    .status("FAILED")
                    .build()
                );
        }
    }

}
