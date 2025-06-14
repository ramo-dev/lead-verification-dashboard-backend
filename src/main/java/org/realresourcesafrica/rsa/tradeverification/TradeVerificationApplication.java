package org.realresourcesafrica.rsa.tradeverification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TradeVerificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeVerificationApplication.class, args);
        System.out.println("=================================");
        System.out.println("Trade Verification API Started!");
        System.out.println("API Documentation:");
        System.out.println("POST   /api/inquiries           - Create new inquiry");
        System.out.println("GET    /api/inquiries           - Get all inquiries");
        System.out.println("GET    /api/inquiries?status=X  - Filter by status");
        System.out.println("GET    /api/inquiries/{id}      - Get specific inquiry");
        System.out.println("PUT    /api/inquiries/{id}/status - Update status");
        System.out.println("GET    /api/inquiries/statistics - Get statistics");
        System.out.println("GET    /api/inquiries/health    - Health check");
        System.out.println("=================================");
    }
}
