package org.realresourcesafrica.rsa.tradeverification.controller;

import org.realresourcesafrica.rsa.tradeverification.dto.ApiResponse;
import org.realresourcesafrica.rsa.tradeverification.dto.CreateInquiryRequest;
import org.realresourcesafrica.rsa.tradeverification.dto.StatusUpdateRequest;
import org.realresourcesafrica.rsa.tradeverification.entity.TradeInquiry;
import org.realresourcesafrica.rsa.tradeverification.service.TradeInquiryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@Validated
@CrossOrigin(origins = "*") // Configure this properly for production
public class TradeInquiryController {

    private final TradeInquiryService inquiryService;

    @Autowired
    public TradeInquiryController(TradeInquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    /**
     * Submit a new trade inquiry
     * POST /api/inquiries
     */
    @PostMapping
    public ResponseEntity<ApiResponse<TradeInquiry>> createInquiry(
            @Valid @RequestBody CreateInquiryRequest request) {

        // Create TradeInquiry entity from request
        TradeInquiry inquiry = new TradeInquiry(
                request.getTitle(),
                request.getDescription(),
                request.getSubmittingPartner(),
                request.getEstimatedValue()
        );

        TradeInquiry createdInquiry = inquiryService.createInquiry(inquiry);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Inquiry created successfully", createdInquiry));
    }

    /**
     * Get all inquiries, optionally filtered by status
     * GET /api/inquiries?status={status}
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<TradeInquiry>>> getInquiries(
            @RequestParam(value = "status", required = false) String status) {

        List<TradeInquiry> inquiries = inquiryService.getInquiries(status);

        String message = status != null ?
                "Inquiries retrieved successfully for status: " + status :
                "All inquiries retrieved successfully";

        return ResponseEntity.ok(ApiResponse.success(message, inquiries));
    }

    /**
     * Get a specific inquiry by ID
     * GET /api/inquiries/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TradeInquiry>> getInquiryById(@PathVariable Long id) {
        TradeInquiry inquiry = inquiryService.getInquiryById(id);
        return ResponseEntity.ok(ApiResponse.success("Inquiry retrieved successfully", inquiry));
    }

    /**
     * Update the status of an inquiry
     * PUT /api/inquiries/{id}/status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TradeInquiry>> updateInquiryStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest statusRequest) {

        TradeInquiry updatedInquiry = inquiryService.updateInquiryStatus(id, statusRequest);

        return ResponseEntity.ok(
                ApiResponse.success("Inquiry status updated successfully", updatedInquiry)
        );
    }

    /**
     * Get inquiry statistics
     * GET /api/inquiries/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<TradeInquiryService.InquiryStatistics>> getStatistics() {
        TradeInquiryService.InquiryStatistics stats = inquiryService.getStatistics();
        return ResponseEntity.ok(ApiResponse.success("Statistics retrieved successfully", stats));
    }

    /**
     * Delete an inquiry (admin function)
     * DELETE /api/inquiries/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(@PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok(ApiResponse.success("Inquiry deleted successfully", null));
    }

    /**
     * Health check endpoint
     * GET /api/inquiries/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(
                ApiResponse.success("Trade Inquiry Verification API is running", "OK")
        );
    }
}
