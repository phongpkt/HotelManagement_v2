package com.example.HotelManagement.service;

import com.example.HotelManagement.model.Payment;
import com.example.HotelManagement.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment save(Payment newPayment){
        return paymentRepository.save(newPayment);
    }
    public Optional<Payment> findById(Long id){
        return paymentRepository.findById(id);
    }
    public Payment update(Payment newPayment, Long id){
        Payment updatedPayment = paymentRepository.findById(id).map(pay -> {
            pay.setTotalAmount(newPayment.getTotalAmount());
            pay.setPayment_date(newPayment.getPayment_date());
            pay.setPayment_method(newPayment.getPayment_method());
            return paymentRepository.save(pay);
        }).orElseGet(() -> {
            newPayment.setId(id);
            return paymentRepository.save(newPayment);
        });
        return updatedPayment;
    }
    public boolean delete(Long id) {
        boolean exists = paymentRepository.existsById(id);
        if(exists) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
