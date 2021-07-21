package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.FacilityDto;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.PaymentRepository;
import com.enigma.bookit.service.FacilityService;
import com.enigma.bookit.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    FacilityService facilityService;

    @Override
    //harus cek jam ini udah dipesan atau belum
    public Payment save(Payment payment) {
        payment.setDueTime(new Date(System.currentTimeMillis() + 3600000));
        //get facility
        String facilityId = payment.getFacility().getId();
        FacilityDto facility = facilityService.getFacilityById(facilityId);
        switch(payment.getPackageChosen()){
            //once rent, the price determined by the duration
            case ONCE:
                Integer price = facility.getRentPriceOnce();
                //get duration
                Long duration = timeDiffer(payment.getBookingStart(), payment.getBookingEnd());
                BigInteger amount = new BigInteger(String.valueOf(duration * price));
                payment.setAmount(amount);
            case WEEKLY:
                BigInteger amount
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getById(String id) {
        validatePresent(id);
        return paymentRepository.findById(id).get();
    }

    @Override
    public void deleteById(String id) {
        validatePresent(id);
        paymentRepository.deleteById(id);
    }

    @Override
    //harus cek jam ini udah dipesan atau belum dari tabel book
    public Payment pay(String id) {
        validatePresent(id);
        Payment payment = getById(id);
        payment.setPaymentStatus(true);
        payment.setPaymentDate(new Date(System.currentTimeMillis()));
        return paymentRepository.save(payment);
        //bikin book
    }

    @Override
    public Page<Payment> getAllPerPage(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    private void validatePresent(String id){
        if(!paymentRepository.findById(id).isPresent()){
            String message = "id not found";
            throw new DataNotFoundException(message);
        }
    }

    private Long timeDiffer(Date timeStart, Date timeStop){
        Long difference = timeStop.getTime() - timeStart.getTime();
        return (difference/(1000 * 3600)) % 24;
    }
}
