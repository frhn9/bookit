package com.enigma.bookit.security.services.customer;

import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return CustomerDetailsImpl.build(customer);
    }
}
