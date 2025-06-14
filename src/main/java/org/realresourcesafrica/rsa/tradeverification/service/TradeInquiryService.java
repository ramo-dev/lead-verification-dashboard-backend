package org.realresourcesafrica.rsa.tradeverification.service;

import org.realresourcesafrica.rsa.tradeverification.entity.TradeInquiry;
import org.realresourcesafrica.rsa.tradeverification.repository.TradeInquiryRepository;
import org.realresourcesafrica.rsa.tradeverification.dto.StatusUpdateRequest;
import org.realresourcesafrica.rsa.tradeverification.exception.InquiryNotFoundException;
import org.realresourcesafrica.rsa.tradeverification.exception.InvalidStatusException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TradeInquiryService {

    private final TradeInquiryRepository repository;

    // Valid status values
    private static final Set<String> VALID_STATUSES = Set.of(
            "PENDING_VERIFICATION",
            "VERIFIED",
            "REJECTED"
    );

    @Autowired
    public TradeInquiryService(TradeInquiryRepository repository) {
        this.repository = repository;
    }

    /**
     * Create a new trade inquiry
     * @param inquiry The inquiry to create
     * @return The created inquiry with generated ID
     */
    public TradeInquiry createInquiry(TradeInquiry inquiry) {
        // Ensure status is set to PENDING_VERIFICATION for new inquiries
        inquiry.setStatus("PENDING_VERIFICATION");
        return repository.save(inquiry);
    }

    /**
     * Get all inquiries, optionally filtered by status
     * @param status Optional status filter
     * @return List of inquiries
     */
    @Transactional(readOnly = true)
    public List<TradeInquiry> getInquiries(String status) {
        if (StringUtils.hasText(status)) {
            validateStatus(status);
            return repository.findByStatusOrderByCreatedAtDesc(status.toUpperCase());
        }
        return repository.findAll();
    }

    /**
     * Get inquiry by ID
     * @param id The inquiry ID
     * @return The inquiry if found
     * @throws InquiryNotFoundException if inquiry not found
     */
    @Transactional(readOnly = true)
    public TradeInquiry getInquiryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new InquiryNotFoundException("Inquiry not found with id: " + id));
    }

    /**
     * Update the status of an inquiry
     * @param id The inquiry ID
     * @param statusRequest The new status
     * @return The updated inquiry
     * @throws InquiryNotFoundException if inquiry not found
     * @throws InvalidStatusException if status is invalid
     */
    public TradeInquiry updateInquiryStatus(Long id, StatusUpdateRequest statusRequest) {
        TradeInquiry inquiry = getInquiryById(id);

        String newStatus = statusRequest.getStatus().toUpperCase();
        validateStatus(newStatus);

        // Business logic: prevent certain status transitions if needed
        if ("VERIFIED".equals(inquiry.getStatus()) && "PENDING_VERIFICATION".equals(newStatus)) {
            throw new InvalidStatusException("Cannot change status from VERIFIED back to PENDING_VERIFICATION");
        }

        inquiry.setStatus(newStatus);
        return repository.save(inquiry);
    }

    /**
     * Get inquiry statistics
     * @return Statistics about inquiries by status
     */
    @Transactional(readOnly = true)
    public InquiryStatistics getStatistics() {
        long pending = repository.countByStatusIgnoreCase("PENDING_VERIFICATION");
        long verified = repository.countByStatusIgnoreCase("VERIFIED");
        long rejected = repository.countByStatusIgnoreCase("REJECTED");
        long total = repository.count();

        return new InquiryStatistics(total, pending, verified, rejected);
    }

    /**
     * Delete an inquiry (admin function)
     * @param id The inquiry ID
     * @throws InquiryNotFoundException if inquiry not found
     */
    public void deleteInquiry(Long id) {
        if (!repository.existsById(id)) {
            throw new InquiryNotFoundException("Inquiry not found with id: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Validate status value
     * @param status The status to validate
     * @throws InvalidStatusException if status is invalid
     */
    private void validateStatus(String status) {
        if (!VALID_STATUSES.contains(status.toUpperCase())) {
            throw new InvalidStatusException("Invalid status: " + status +
                    ". Valid statuses are: " + String.join(", ", VALID_STATUSES));
        }
    }

    /**
     * Inner class for inquiry statistics
     */
    public static class InquiryStatistics {
        private final long total;
        private final long pending;
        private final long verified;
        private final long rejected;

        public InquiryStatistics(long total, long pending, long verified, long rejected) {
            this.total = total;
            this.pending = pending;
            this.verified = verified;
            this.rejected = rejected;
        }

        public long getTotal() { return total; }
        public long getPending() { return pending; }
        public long getVerified() { return verified; }
        public long getRejected() { return rejected; }
    }
}