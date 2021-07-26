package com.enigma.bookit.service;

import com.enigma.bookit.dto.PaymentSearchDTO;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.repository.PaymentRepository;
import com.enigma.bookit.specification.PaymentSpecification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentReportService {

    @Autowired
    private PaymentRepository paymentRepository;

    public String generateReport(PaymentSearchDTO paymentSearchDTO){
        try{
            Specification<Payment> paymentSpecification = PaymentSpecification.getSpecification(paymentSearchDTO);
            List<Payment> payments = paymentRepository.findAll(paymentSpecification);
            String reportPath = "C:\\Users\\Argast\\IdeaProjects\\bookit\\src\\main\\resources";
            String path = "C:\\Users\\Argast\\Downloads";
            JasperReport jasperReport = JasperCompileManager
                    .compileReport(reportPath + "\\payment-report.jrxml");
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(payments);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "book.it");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\payment.pdf");
            return"Report generated at " + reportPath;
        }catch(Exception e){
            e.printStackTrace();
            return "Failed to generate report, error " + e;
        }
    }
}
