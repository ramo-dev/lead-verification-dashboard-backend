package org.realresourcesafrica.rsa.tradeverification.config;

import org.realresourcesafrica.rsa.tradeverification.entity.TradeInquiry;
import org.realresourcesafrica.rsa.tradeverification.repository.TradeInquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TradeInquiryRepository repository;

    @Autowired
        public DataInitializer(TradeInquiryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only initialize data if database is empty
        if (repository.count() == 0) {
            initializeSampleData();
            System.out.println("Sample data initialized successfully!");
        }
    }

    private void initializeSampleData() {
        // Sample inquiries for testing
        TradeInquiry inquiry1 = new TradeInquiry(
                "Inquiry for Kenyan Avocados",
                "Request for 500 tons of premium Hass avocados from Kenya for export to European markets. Quality certification and organic standards compliance required.",
                "KEPROBA",
                new BigDecimal("250000.00")
        );

        TradeInquiry inquiry2 = new TradeInquiry(
                "Uganda Coffee Export Deal",
                "High-quality Arabica coffee beans from Ugandan highlands. Looking for certification and export facilitation for 200 tons.",
                "Ministry of Trade - Uganda",
                new BigDecimal("180000.00")
        );
        inquiry2.setStatus("VERIFIED");

        TradeInquiry inquiry3 = new TradeInquiry(
                "South African Wine Export",
                "Premium wine collection from Western Cape vineyards. Seeking certification for international distribution.",
                "SA Wine Exporters Association",
                new BigDecimal("75000.00")
        );

        TradeInquiry inquiry4 = new TradeInquiry(
                "Ethiopian Textile Products",
                "Traditional Ethiopian textiles and garments for cultural export program. Hand-woven products with authentic designs.",
                "Ethiopian Textile Council",
                new BigDecimal("45000.00")
        );
        inquiry4.setStatus("REJECTED");

        TradeInquiry inquiry5 = new TradeInquiry(
                "Moroccan Argan Oil Bulk Order",
                "Certified organic argan oil from Morocco. Bulk order for international cosmetic manufacturers.",
                "Moroccan Argan Cooperative",
                new BigDecimal("120000.00")
        );

        // Save all sample data
        repository.save(inquiry1);
        repository.save(inquiry2);
        repository.save(inquiry3);
        repository.save(inquiry4);
        repository.save(inquiry5);
    }
}