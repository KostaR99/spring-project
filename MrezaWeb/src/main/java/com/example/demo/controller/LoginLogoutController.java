package com.example.demo.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.UlogaRepository;
import com.example.demo.security.GeneratePassword;

import model.Korisnik;
import model.Uloga;

@Controller
@RequestMapping("/auth")
public class LoginLogoutController {
	@Autowired
	KorisnikRepository kr;
	@Autowired
	UlogaRepository ur;
	@RequestMapping(value="/loginPage", method = RequestMethod.GET)
	public String loginPage() {
		return "/loginRegister/login";
	}
	@RequestMapping(value = "registerUser",method = RequestMethod.GET)
	public String noviKorisnik(Model m) {
		Korisnik k = new Korisnik();
		m.addAttribute("korisnik",k);
		return "/loginRegister/register";
	}
	@RequestMapping(value="register",method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("korisnik")Korisnik k, Model m) {
	if(kr.findByusername(k.getUsername()).isPresent() && kr.findByemail(k.getEmail()).isPresent() && kr.findByusername(k.getUsername()).get() != null && kr.findByemail(k.getEmail()).get() != null ) {
		m.addAttribute("poruka","korisnickim imenom i emailom");
		return "/errors/vecPostoji";
	}
	else if(kr.findByusername(k.getUsername()).isPresent() &&kr.findByusername(k.getUsername()).get()!=null) {
			m.addAttribute("poruka","korisnickim imenom");
			return "/errors/vecPostoji";
		}
		else if(kr.findByemail(k.getEmail()).isPresent() && kr.findByemail(k.getEmail()).get()!=null) {
			m.addAttribute("poruka","emailom");
			return "/errors/vecPostoji";
		}
		k.setPassword(GeneratePassword.encryptPassword(k.getPassword()));
		Uloga u = ur.findById(2).get();
		k.setUloga(u);
		kr.save(k);
		return "/loginRegister/login";
	}
	
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/auth/loginPage";
    }

    @RequestMapping(value="/pocetna", method = RequestMethod.GET)
    public String getPocetna (){
        return "/logedIn/pocetna";
    }

	
}