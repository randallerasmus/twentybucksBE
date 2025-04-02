package za.co.byteservices.twentybucksbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BetRequestDto {
    private String title;
    private String description;
    private String category;
    private String option1;
    private String option2;

    @JsonProperty("bet_amount")
    private double betAmount;

    @JsonProperty("expiry_days")
    private Integer duration;

    @JsonProperty("is_private")
    private boolean isPrivate;

    @JsonProperty("created_by")
    private String createdBy;

    // Getters & Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getOption1() { return option1; }
    public void setOption1(String option1) { this.option1 = option1; }

    public String getOption2() { return option2; }
    public void setOption2(String option2) { this.option2 = option2; }

    public double getBetAmount() { return betAmount; }
    public void setBetAmount(double betAmount) { this.betAmount = betAmount; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public boolean isPrivate() { return isPrivate; }
    public void setPrivate(boolean aPrivate) { isPrivate = aPrivate; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}
