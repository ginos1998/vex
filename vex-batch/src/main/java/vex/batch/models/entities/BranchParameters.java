package vex.batch.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "branch_parameters")
public class BranchParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_parameter_id")
    private Integer branchParameterId;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private Parameters parameters;

    @Column(name = "active", length = 1)
    private String active;
}
