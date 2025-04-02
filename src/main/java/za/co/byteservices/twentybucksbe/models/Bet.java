package za.co.byteservices.twentybucksbe.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String category;

    private String option1;

    private String option2;

    @Column(name = "bet_amount")
    private Double amount;

    @Column(name = "is_private")
    private Boolean isPublic;

    @Column(name = "expiry_days")
    private Integer duration; // in days


    private LocalDateTime endDate;

    @JoinColumn(name = "created_at")
    private LocalDateTime createdAt;

//    private String status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
