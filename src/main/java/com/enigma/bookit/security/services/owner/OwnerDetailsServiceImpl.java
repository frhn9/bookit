package com.enigma.bookit.security.services.owner;

import com.enigma.bookit.entity.user.Owner;
import com.enigma.bookit.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    OwnerRepository ownerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return OwnerDetailsImpl.build(owner);
    }
}
