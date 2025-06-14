package org.realresourcesafrica.rsa.tradeverification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CreateInquiryRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Submitting partner is required")
    @JsonProperty("submittingPartner")
    private String submittingPartner;

    @PositiveOrZero(message = "Estimated value must be positive or zero")
    @JsonProperty("estimatedValue")
    private BigDecimal estimatedValue;

    public CreateInquiryRequest() {}

    public CreateInquiryRequest(String title, String description, String submittingPartner, BigDecimal estimatedValue) {
        this.title = title;
        this.description = description;
        this.submittingPartner = submittingPartner;
        this.estimatedValue = estimatedValue;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubmittingPartner() { return submittingPartner; }
    public void setSubmittingPartner(String submittingPartner) { this.submittingPartner = submittingPartner; }

    public BigDecimal getEstimatedValue() { return estimatedValue; }
    public void setEstimatedValue(BigDecimal estimatedValue) { this.estimatedValue = estimatedValue; }

    @Override
    public String toString() {
        return "CreateInquiryRequest{" +
                "title='" + title + '\'' +
                ", submittingPartner='" + submittingPartner + '\'' +
                ", estimatedValue=" + estimatedValue +
                '}';
    }
}

