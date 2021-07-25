package com.enigma.bookit.service;

import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.repository.PaymentRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentReportService {

    @Autowired
    private PaymentRepository paymentRepository;

    public String generateReport(){
        try{
            List<Payment> payments = paymentRepository.findAll();
            String reportPath = "C:\\Users\\Argast\\IdeaProjects\\bookit\\src\\main\\resources";
            JasperReport jasperReport = JasperCompileManager
                    .compileReport(reportPath + "\\payment-report.jrxml");
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(payments);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Our sadness and sorrow");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "\\payment.pdf");
            return"SO FKIN DONE";
        }catch(Exception e){
            e.printStackTrace();
            return "WHYYYYYYYYYYYYY";
        }
    }
}
