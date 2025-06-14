package org.realresourcesafrica.rsa.tradeverification.repository;

import org.realresourcesafrica.rsa.tradeverification.entity.TradeInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeInquiryRepository extends JpaRepository<TradeInquiry, Long> {

    /**
     * Find all inquiries by status
     * @param status The status to filter by
     * @return List of trade inquiries with the specified status
     */
    List<TradeInquiry> findByStatusIgnoreCase(String status);

    /**
     * Find all inquiries by submitting partner
     * @param submittingPartner The partner name
     * @return List of trade inquiries from the specified partner
     */
    List<TradeInquiry> findBySubmittingPartnerIgnoreCase(String submittingPartner);

    /**
     * Find inquiries by status ordered by creation date (newest first)
     * @param status The status to filter by
     * @return List of trade inquiries ordered by creation date descending
     */
    @Query("SELECT t FROM TradeInquiry t WHERE UPPER(t.status) = UPPER(:status) ORDER BY t.createdAt DESC")
    List<TradeInquiry> findByStatusOrderByCreatedAtDesc(@Param("status") String status);

    /**
     * Count inquiries by status
     * @param status The status to count
     * @return Number of inquiries with the specified status
     */
    long countByStatusIgnoreCase(String status);
}
