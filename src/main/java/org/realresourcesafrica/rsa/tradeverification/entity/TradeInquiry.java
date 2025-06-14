package org.realresourcesafrica.rsa.tradeverification.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade_inquiries")
public class TradeInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Status is required")
    @Column(nullable = false, length = 50)
    private String status;

    @NotBlank(message = "Submitting partner is required")
    @Column(name = "submitting_partner", nullable = false, length = 255)
    @JsonProperty("submittingPartner")
    private String submittingPartner;

    @PositiveOrZero(message = "Estimated value must be positive or zero")
    @Column(name = "estimated_value", precision = 15, scale = 2)
    @JsonProperty("estimatedValue")
    private BigDecimal estimatedValue;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public TradeInquiry() {
        this.createdAt = LocalDateTime.now();
        this.status = InquiryStatus.PENDING_VERIFICATION.name();
    }

    public TradeInquiry(String title, String description, String submittingPartner, BigDecimal estimatedValue) {
        this();
        this.title = title;
        this.description = description;
        this.submittingPartner = submittingPartner;
        this.estimatedValue = estimatedValue;
    }

    // Lifecycle callbacks
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmittingPartner() {
        return submittingPartner;
    }

    public void setSubmittingPartner(String submittingPartner) {
        this.submittingPartner = submittingPartner;
    }

    public BigDecimal getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(BigDecimal estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "TradeInquiry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", submittingPartner='" + submittingPartner + '\'' +
                ", estimatedValue=" + estimatedValue +
                ", createdAt=" + createdAt +
                '}';
    }
}

// Enum for inquiry status
enum InquiryStatus {
    PENDING_VERIFICATION,
    VERIFIED,
    REJECTED
}