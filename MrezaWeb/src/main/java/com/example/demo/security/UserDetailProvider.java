package com.example.demo.security;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.KorisnikRepository;

import model.Korisnik;

@Service
public class UserDetailProvider implements UserDetailsService {
	
	@Autowired
	KorisnikRepository kr;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Korisnik> k = kr.findByusername(username);
		
		k.orElseThrow(() -> new UsernameNotFoundException("Nije pronadjeno korisnicko ime: " + username));
		
		return k.map(UserDetailsImpl::new).get();
	}
}