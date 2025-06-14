#!/bin/bash
# Trade Verification API Testing Examples
# Make sure your Spring Boot application is running on http://localhost:8080

echo "=================================="
echo "Trade Verification API Test Suite"
echo "=================================="

# 1. Health Check
echo "1. Testing Health Check..."
curl -X GET "http://localhost:8080/api/inquiries/health" \
  -H "Content-Type: application/json" | jq '.'

echo -e "\n\n"

# 2. Get All Inquiries
echo "2. Getting all inquiries..."
curl -X GET "http://localhost:8080/api/inquiries" \
  -H "Content-Type: application/json" | jq '.'

echo -e "\n\n"

# 3. Filter by Status - Pending
echo "3. Getting pending inquiries..."
curl -X GET "http://localhost:8080/api/inquiries?status=PENDING_VERIFICATION" \
  -H "Content-Type: application/json" | jq '.'

echo -e "\n\n"

# 4. Filter by Status - Verified
echo "4. Getting verified inquiries..."
curl -X GET "http://localhost:8080/api/inquiries?status=VERIFIED" \
  -H "Content-Type: application/json" | jq '.'

echo -e "\n\n"

# 5. Create New Inquiry
echo "5. Creating new inquiry..."
curl -X POST "http://localhost:8080/api/inquiries" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Inquiry - Ghanaian Cocoa",
    "description": "Premium cocoa beans from Ghana for chocolate manufacturing",
    "submittingPartner": "Ghana Cocoa Board",
    "estimatedValue": 95000.50
  }' | jq '.'

echo -e "\n\n"

# 6. Get Specific Inquiry (assuming ID 1 exists)
echo "6. Getting inquiry by ID..."
curl -X GET "http://localhost:8080/api/inquiries/1" \
  -H "Content-Type: application/json" | jq '.'

echo -e "\n\n"

# 7. Update Inquiry Status
echo "7. Updating inquiry status..."
curl -X PUT "http://localhost:8080/api/inquiries/1/status" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "VERIFIED"
  }' | jq '.'

echo -e "\n\n"

# 8. Get Statistics
echo "8. Getting inquiry statistics..."
curl -X GET "http://localhost:8080/api/inquiries/statistics" \
  -H "Content-Type: application/json" | jq '.'

echo -e "\n\n"

# 9. Test Validation Error
echo "9. Testing validation (should fail)..."
curl -X POST "http://localhost:8080/api/inquiries" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "submittingPartner": ""
  }' | jq '.'

echo -e "\n\n"

# 10. Test Invalid Status Update
echo "10. Testing invalid status update..."
curl -X PUT "http://localhost:8080/api/inquiries/1/status" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "INVALID_STATUS"
  }' | jq '.'

echo -e "\n\n"
echo "=================================="
echo "Test Suite Complete!"
echo "=================================="