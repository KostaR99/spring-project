package com.example.demo.controller;

import java.awt.PageAttributes.MediaType;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.PrimaryKeyJoinColumn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.helper.KorisniciIBrojPrijava;
import com.example.demo.repository.BagPrijavaRepository;
import com.example.demo.repository.JavnaKonfiguracijaRepository;
import com.example.demo.repository.KomponentaRepository;
import com.example.demo.repository.KorisnikPrijaveRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.PracenjaRepository;
import com.example.demo.repository.PratiociRepository;
import com.example.demo.repository.PrivatnaKonfiguracijaRepository;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.TipKomponenteRepository;
import com.example.demo.security.GeneratePassword;

import model.Bagprijave;
import model.Javnakonfiguracija;
import model.Komponenta;
import model.Korisnik;
import model.Korisnikprijave;
import model.Pracenja;
import model.Pratioci;
import model.Privatnakonfiguracija;
import model.Status;
import model.Tipkomponente;
import net.bytebuddy.asm.Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value = "/")
public class UlogeController {
	@Autowired
	JavnaKonfiguracijaRepository jkr;
	@Autowired
	BagPrijavaRepository br;
	@Autowired
	KorisnikPrijaveRepository kpr;
	@Autowired
	 KorisnikRepository kr;
	@Autowired
	TipKomponenteRepository tkr;
	@Autowired
	KomponentaRepository kmpr;
	@Autowired
	PrivatnaKonfiguracijaRepository pkr;
	@Autowired
	StatusRepository sr;
	@Autowired
	PracenjaRepository prr;
	@Autowired
	PratiociRepository prtr;
	@ModelAttribute
	public void getRoles(Model m) {
		List<Tipkomponente> tipovi = tkr.findAll();
		m.addAttribute("tipovi", tipovi);
	}
	// svi
	@RequestMapping(value = "/pretraziKorisnika",method = RequestMethod.GET)
	public String pretraziKorisnika(Principal p,Model m,String username) {
		Optional<Korisnik> k = kr.findByusername(username);
		if(k.isEmpty() || k.isPresent() && k.get()==null) {
			return "/errors/nepostojeciKorisnik";
		}
		Korisnik ja =  kr.findByusername(p.getName()).get();
		Korisnik korisnik = k.get();
		if(ja.getUsername().equals(korisnik.getUsername()))
				return "redirect:/mojProfil";
		if(korisnik.getUloga().getIduloga()==2 && korisnik.getStatuses().size()>0) {
			Status status = korisnik.getStatuses().get(korisnik.getStatuses().size()-1);
			m.addAttribute("status", status);
		}
		boolean t = false;
		for(Pracenja pracenja:ja.getPracenjas()) {
			if(pracenja.getIdDrugogKorisnika()==korisnik.getIdkorisnik())
				t=true;
		}
		if(!t) {
			m.addAttribute("ja", ja);
		}
		m.addAttribute("jesamLiAdmin", ja);
		m.addAttribute("korisnik",korisnik);
		return "/korisnik/korisnikInfo";
	}
	@RequestMapping(value="/prikazSvihKonfKorisnika",method = RequestMethod.GET)
	public String korKonfInfo(HttpServletRequest request, Model m) {
		Korisnik k = kr.findByusername(request.getParameter("username")).get();
		List<Privatnakonfiguracija> konfiguracije = pkr.findAllBykorisnik(k);
		m.addAttribute("konfiguracije", konfiguracije);
		m.addAttribute("korisnik", k);
		return "/konfiguracije/konfiguracijeKorisnika";
	}
	@RequestMapping(value = "/prikazKonfKorisnika",method = RequestMethod.GET)
	public String prikazKonfKorisnika(int idKonf, Model m) {
		Privatnakonfiguracija konf  = pkr.findById(idKonf).get();
		m.addAttribute("konfiguracija", konf);
		return "/konfiguracije/KorisnikKonfShow";
	}
	@RequestMapping(value = "/prikazJavnihKonfiguracija",method = RequestMethod.GET)
	public String getJavneKonfiguracije(Model m) {
		List<Javnakonfiguracija> konfiguracije = jkr.findAll();
		m.addAttribute("konfiguracije",konfiguracije);
		return "/konfiguracije/prikazJavnihKonfiguracija";
	}
	@RequestMapping(value = "/mojProfil", method = RequestMethod.GET)
	public String getInfo(Principal p,Model m) {
		Korisnik k = kr.findByusername(p.getName()).get();
		if(k.getUloga().getIduloga()==2 && k.getStatuses().size()>0) {
			Status s = k.getStatuses().get(k.getStatuses().size()-1);
			m.addAttribute("status", s);
		}
		m.addAttribute("korisnik",k);
		return "/logedIn/profil";
	}
	@GetMapping(value="/javKonfInfo")
	public String detaljnijeOJavnojKonf(Model m,int idKonf) {
		Javnakonfiguracija k = jkr.findById(idKonf).get();
		m.addAttribute("konfiguracija",k);
		return "/konfiguracije/javKonfShow";
	}
	@RequestMapping(value="/prikazKomponenti",method = RequestMethod.GET)
	public String prikazKomponenti(Model m) {
		List<Komponenta> lista = kmpr.findAll();
		m.addAttribute("komponente",lista);
		return "/komponente/pregledSvihKomponenti";
	}
	@RequestMapping(value="/kompInfo",method = RequestMethod.GET)
	public String kompInfo(Model m, int idKonf){
		Komponenta k = kmpr.findById(idKonf).get();
		m.addAttribute("komponenta", k);
		return "/komponente/prikazKomponente";
	}
	@RequestMapping(value = "/novaSifra",method = RequestMethod.GET)
	public String newPasswordPage() {
		return "/loginRegister/novaSifra";
	}
	@RequestMapping(value = "/sacuvajNovuSifru",method = RequestMethod.GET)
	public String promenaLozinke(Principal p,String password) {
		System.out.println(password);
		Korisnik k = kr.findByusername(p.getName()).get();
		k.setPassword(GeneratePassword.encryptPassword(password));
		kr.flush();
		return "redirect:/mojProfil";
	}
	@RequestMapping(value = "/sviPratiociKorisnika",method = RequestMethod.GET)
	public String sviPratiociKorisnika(Model m,String username) {
		Korisnik k = kr.findByusername(username).get();
		List<Pratioci> p = prtr.findAllBykorisnik(k);
		List<Korisnik> pratioci = new LinkedList<Korisnik>();
		for(Pratioci pr:p) {
			pratioci.add(kr.findById(pr.getIdDrugogKorisnika()).get());
		}
		m.addAttribute("korisnik",k);
		m.addAttribute("pratioci", pratioci);
		return "/following/pratioci";
	}
	
	@RequestMapping(value = "/svaPracenjaKorisnika",method = RequestMethod.GET)
	public String svaPracenjaKorisnika(Model m,String username) {
		Korisnik k = kr.findByusername(username).get();
		List<Pracenja> p = prr.findAllBykorisnik(k);
		List<Korisnik> pracenja = new LinkedList<Korisnik>();
		for(Pracenja pr:p) {
			pracenja.add(kr.findById(pr.getIdDrugogKorisnika()).get());
		}
		m.addAttribute("korisnik",k);
		m.addAttribute("pracenja", pracenja);
		return "/following/pracenja";
	}
	
	//----------------------------------------------------user-------------------------------------------------------
	
	
	@RequestMapping(value = "/user/bagPage", method = RequestMethod.GET)
	public String bagPage(Model m) {
		Bagprijave prijava = new Bagprijave();
		m.addAttribute("bagPrijava",prijava);
		return "/prijave/prijaviBag";
	}
	@RequestMapping(value = "/user/prijavaBaga", method = RequestMethod.POST)
	public String bagPrijava(@ModelAttribute("bagPrijava")Bagprijave bp, Principal p) {
		bp.setKorisnik(kr.findByusername(p.getName()).get());
		br.save(bp);
		return "redirect:/pocetna";
	}
	
	@RequestMapping(value = "/user/korisnikPrijavaPage", method = RequestMethod.GET)
	public String korisnikPrijavaPage(Model m) {
		Korisnikprijave prijava = new Korisnikprijave();
		m.addAttribute("korisnikPrijava",prijava);
		return "/prijave/prijaviKorisnika";
	}
	@RequestMapping(value = "/user/prijavaKorisnika", method = RequestMethod.POST)
	public String korisnikPrijava(@ModelAttribute("korisnikPrijava")Korisnikprijave kp, Principal p) {
		kp.setKorisnik(kr.findByusername(p.getName()).get());
		kpr.save(kp);
		return "redirect:/pocetna";
	}
	@RequestMapping(value="/user/savePrivKonfPage",method = RequestMethod.GET)
	public String savePrivKonfPage(Model m) {
		List<Komponenta> gpu = kmpr.getPoTipu(1);
		List<Komponenta> cpu = kmpr.getPoTipu(2);
		List<Komponenta> maticna = kmpr.getPoTipu(7);
		List<Komponenta> ssd = kmpr.getPoTipu(4);
		List<Komponenta> hdd = kmpr.getPoTipu(5);
		List<Komponenta> kuciste = kmpr.getPoTipu(6);
		List<Komponenta> ram = kmpr.getPoTipu(3);
		m.addAttribute("gpus", gpu);
		m.addAttribute("cpus", cpu);
		m.addAttribute("mboard", maticna);
		m.addAttribute("ssds", ssd);
		m.addAttribute("hdds", hdd);
		m.addAttribute("rams", ram);
		m.addAttribute("cases", kuciste);
		return "/konfiguracije/inicijalizacijaKonfiguracije";
	}
	@RequestMapping(value = "/user/saveConv",method = RequestMethod.POST)
	public String sacuvajPrivatnuKonf(Principal p,Model m,HttpServletRequest request) {
		Privatnakonfiguracija konfiguracja = new Privatnakonfiguracija();
		Korisnik k = kr.findByusername(p.getName()).get();
		konfiguracja.setKorisnik(k);
		String naziv = request.getParameter("nazivKonfiguracije");
		if(pkr.findAllBynazivPrivKonf(naziv).size()>0) {
			System.out.println(pkr.findAllBynazivPrivKonf(naziv).size());
			return "/errors/greskaKodPravljenjaKonfiguracije";
			}
		konfiguracja.setNazivPrivKonf(naziv);
		konfiguracja.setNamena(request.getParameter("namenaKonfiguracije"));
		Komponenta gpu = kmpr.findById(Integer.parseInt(request.getParameter("idGPU"))).get();
		Komponenta cpu = kmpr.findById(Integer.parseInt(request.getParameter("idCPU"))).get();
		Komponenta hdd = kmpr.findById(Integer.parseInt(request.getParameter("idHDD"))).get();
		Komponenta ssd = kmpr.findById(Integer.parseInt(request.getParameter("idSSD"))).get();
		Komponenta maticna = kmpr.findById(Integer.parseInt(request.getParameter("idMaticna"))).get();
		Komponenta kuciste = kmpr.findById(Integer.parseInt(request.getParameter("idKuciste"))).get();
		Komponenta ram = kmpr.findById(Integer.parseInt(request.getParameter("idRam"))).get();
		List<Komponenta> lista = new LinkedList<Komponenta>();
		lista.add(gpu);
		lista.add(cpu);
		lista.add(hdd);
		lista.add(ssd);
		lista.add(maticna);
		lista.add(kuciste);
		lista.add(ram);
		konfiguracja.setKomponentas(lista);
		List<Privatnakonfiguracija> listaK = new LinkedList<Privatnakonfiguracija>();
		listaK.add(konfiguracja);
		if(gpu.getPrivatnakonfiguracijas()==null) {
			gpu.setPrivatnakonfiguracijas(listaK);
		}else {
			gpu.getPrivatnakonfiguracijas().add(konfiguracja);
		}
		
		if(cpu.getPrivatnakonfiguracijas()==null) {
			cpu.setPrivatnakonfiguracijas(listaK);
		}else {
			cpu.getPrivatnakonfiguracijas().add(konfiguracja);
		}
		
		if(hdd.getPrivatnakonfiguracijas()==null) {
			hdd.setPrivatnakonfiguracijas(listaK);
		}else {
			hdd.getPrivatnakonfiguracijas().add(konfiguracja);
		}
		
		if(ssd.getPrivatnakonfiguracijas()==null) {
			ssd.setPrivatnakonfiguracijas(listaK);
		}else {
			ssd.getPrivatnakonfiguracijas().add(konfiguracja);
		}
		
		if(maticna.getPrivatnakonfiguracijas()==null) {
			maticna.setPrivatnakonfiguracijas(listaK);
		}else {
			maticna.getPrivatnakonfiguracijas().add(konfiguracja);
		}
		
		if(ram.getPrivatnakonfiguracijas()==null) {
			ram.setPrivatnakonfiguracijas(listaK);
		}else {
			ram.getPrivatnakonfiguracijas().add(konfiguracja);
		}
		
		if(kuciste.getPrivatnakonfiguracijas()==null) {
			kuciste.setPrivatnakonfiguracijas(listaK);
		}else {
			kuciste.getPrivatnakonfiguracijas().add(konfiguracja);
		}
		
		pkr.saveAndFlush(konfiguracja);
		kmpr.flush();
		m.addAttribute("konfiguracija",konfiguracja);
		return "redirect:/user/prikazPrivatnihKonfiguracija";
	}
	@RequestMapping(value = "/user/prikazPrivatnihKonfiguracija",method = RequestMethod.GET)
	public String getMojeKonfiguracije(Principal p,Model m) {
		Korisnik t = kr.findByusername(p.getName()).get();
		List<Privatnakonfiguracija> konfiguracije = pkr.findAllBykorisnik(t);
		m.addAttribute("konfiguracije",konfiguracije);
		return "/konfiguracije/mojeKonfiguracije";
	}
	@GetMapping(value="/user/privKonfInfo")
	public String detaljnijeOPrivatnojKonf(Model m,int idKonf) {
		Privatnakonfiguracija k = pkr.findById(idKonf).get();
		m.addAttribute("konfiguracija",k);
		return "/konfiguracije/privKonfShow";
	}
	@RequestMapping(value = "/user/napraviStatusPage",method = RequestMethod.GET)
	public String statusPage(Model m) {
		Status status = new Status();
		m.addAttribute("status", status);
		return "/status/napisiStatus";
	}
	@RequestMapping(value = "/user/napraviStatus",method = RequestMethod.POST)
	public String addStatus(Principal p,@ModelAttribute("status")Status s) {
		s.setKorisnik(kr.findByusername(p.getName()).get());
		sr.save(s);
		return "redirect:/mojProfil";
	}
	@RequestMapping(value = "/user/brisanjeKonfiguracijePage",method = RequestMethod.GET)
	public String brisanjePrivKonfPage(Principal p,Model m) {
		Korisnik k = kr.findByusername(p.getName()).get();
		m.addAttribute("korisnik",k);
		List<Privatnakonfiguracija> konfiguracije = pkr.findAllBykorisnik(kr.findByusername(p.getName()).get());
		m.addAttribute("konfiguracije", konfiguracije);
		return "/konfiguracije/izbrisiKonfiguraciju";
	}
	@RequestMapping(value = "/user/brisanjeKonfiguracije",method = RequestMethod.POST)
	public String brisanjePrivKonf(HttpServletRequest request) {
		int idKonf = Integer.parseInt(request.getParameter("idKonf"));
		Privatnakonfiguracija konf = pkr.findById(idKonf).get();
		for(Komponenta k : konf.getKomponentas()) {
			k.getPrivatnakonfiguracijas().remove(konf);
		}
		konf.setKomponentas(null);
		pkr.flush();
		kmpr.flush();
		pkr.delete(konf);
		return "redirect:/user/prikazPrivatnihKonfiguracija";
	}
	@RequestMapping(value = "/user/zaprati",method = RequestMethod.POST)
	public String zaprati(Principal p,int idK,Model m) {
		Korisnik ja =kr.findByusername(p.getName()).get();
		Pracenja pracenje = new Pracenja();
		pracenje.setKorisnik(ja);
		pracenje.setIdDrugogKorisnika(idK);
		Korisnik k = kr.findById(idK).get();
		Pratioci pratioc = new Pratioci();
		pratioc.setKorisnik(k);
		pratioc.setIdDrugogKorisnika(ja.getIdkorisnik());
		prr.saveAndFlush(pracenje);
		prtr.saveAndFlush(pratioc);
		if(ja.getPracenjas()==null) {
			ja.setPracenjas(new LinkedList<Pracenja>());
		}
		ja.getPracenjas().add(pracenje);
		if(k.getPratiocis()==null) {
			k.setPratiocis(new LinkedList<Pratioci>());
			k.getPratiocis().add(pratioc);
		}
		kr.flush();
		//m.addAttribute("korisnik", k);
		//if(k.getUloga().getIduloga()==2 && k.getStatuses().size()>0) {
			//Status status = k.getStatuses().get(k.getStatuses().size()-1);
			//m.addAttribute("status", status);
		//}
		//return "/korisnik/korisnikInfo";
		return "redirect:/pretraziKorisnika?username="+k.getUsername();
	}
	@RequestMapping(value = "/user/mojiPratioci",method = RequestMethod.GET)
	public String pogledajMojePratioce(Principal p,Model m) {
		Korisnik ja = kr.findByusername(p.getName()).get();
		List<Pratioci> pr = prtr.findAllBykorisnik(ja);
		List<Korisnik> pratioci = new LinkedList<Korisnik>();
		for (Pratioci pratioc:pr) {
			pratioci.add(kr.findById(pratioc.getIdDrugogKorisnika()).get());
		}
		m.addAttribute("ja", ja);
		m.addAttribute("pratioci",pratioci);
		return "/following/pratioci";
	}
	@RequestMapping(value = "/user/mojaPracenja",method = RequestMethod.GET)
	public String pogledajPracenja(Principal p,Model m) {
		Korisnik ja = kr.findByusername(p.getName()).get();
		List<Pracenja> pr = prr.findAllBykorisnik(ja);
		List<Korisnik> pracenja = new LinkedList<Korisnik>();
		for (Pracenja pra:pr) {
			pracenja.add(kr.findById(pra.getIdDrugogKorisnika()).get());
		}
		m.addAttribute("ja", ja);
		m.addAttribute("pracenja",pracenja);
		return "/following/pracenja";
	}
	
	@RequestMapping(value = "/user/otprati",method = RequestMethod.POST)
	public String otprati(Principal p,int idK,Model m) {
		Korisnik ja =kr.findByusername(p.getName()).get();
		Korisnik k = kr.findById(idK).get();
		List<Pracenja> pracenja = prr.findAllBykorisnik(ja);
		List<Pratioci> pratioci = prtr.findAllBykorisnik(k);
		for(Pracenja pr:pracenja) {
			if(pr.getIdDrugogKorisnika()==idK) {
				prr.delete(pr);
				prr.flush();
			}
		}
		for(Pratioci pr:pratioci) {
			if(pr.getIdDrugogKorisnika()==ja.getIdkorisnik()) {
				prtr.delete(pr);
				prtr.flush();
			}
		}
		kr.flush();
		return "redirect:/pretraziKorisnika?username="+k.getUsername();
	}
	
	
	//----------------------------------------------------admin-------------------------------------------------------
	
	
	
	@RequestMapping(value = "/admin/prikazBagPrijava",method = RequestMethod.GET)
	public String getBagPrijave(Model m) {
		List<Bagprijave> bagPrijave = br.findAll();
		m.addAttribute("bagPrijave",bagPrijave);
		return "/prijave/bagPrijave";
	}
	@RequestMapping(value = "/admin/prikazPrijavaKorisnika",method = RequestMethod.GET)
	public String getUserPrijave(Model m) {
		List<Korisnikprijave> prijaveKorisnika = kpr.findAll();
		m.addAttribute("prijaveKorisnika",prijaveKorisnika);
		return "/prijave/prijaveKorisnika";
	}
	@RequestMapping(value="/admin/saveJavKonfPage",method = RequestMethod.GET)
	public String saveJavKonfPage(Model m) {

		List<Komponenta> gpu = kmpr.getPoTipu(1);
		List<Komponenta> cpu = kmpr.getPoTipu(2);
		List<Komponenta> maticna = kmpr.getPoTipu(7);
		List<Komponenta> ssd = kmpr.getPoTipu(4);
		List<Komponenta> hdd = kmpr.getPoTipu(5);
		List<Komponenta> kuciste = kmpr.getPoTipu(6);
		List<Komponenta> ram = kmpr.getPoTipu(3);
		m.addAttribute("gpus", gpu);
		m.addAttribute("cpus", cpu);
		m.addAttribute("mboard", maticna);
		m.addAttribute("ssds", ssd);
		m.addAttribute("hdds", hdd);
		m.addAttribute("rams", ram);
		m.addAttribute("cases", kuciste);
		return "/konfiguracije/inicijalizacijaKonfiguracije";
	}
	@RequestMapping(value = "/admin/saveConv",method = RequestMethod.POST)
	public String sacuvajJavnuKonf(Model m,HttpServletRequest request) {
		Javnakonfiguracija konfiguracja = new Javnakonfiguracija();
		String naziv = request.getParameter("nazivKonfiguracije");
		if(jkr.findAllBynazivKonfiguracije(naziv).size()>0)
			return "/errors/greskaKodPravljenjaKonfiguracije";
		konfiguracja.setNazivKonfiguracije(naziv);
		konfiguracja.setNamena(request.getParameter("namenaKonfiguracije"));
		Komponenta gpu = kmpr.findById(Integer.parseInt(request.getParameter("idGPU"))).get();
		Komponenta cpu = kmpr.findById(Integer.parseInt(request.getParameter("idCPU"))).get();
		Komponenta hdd = kmpr.findById(Integer.parseInt(request.getParameter("idHDD"))).get();
		Komponenta ssd = kmpr.findById(Integer.parseInt(request.getParameter("idSSD"))).get();
		Komponenta maticna = kmpr.findById(Integer.parseInt(request.getParameter("idMaticna"))).get();
		Komponenta kuciste = kmpr.findById(Integer.parseInt(request.getParameter("idKuciste"))).get();
		Komponenta ram = kmpr.findById(Integer.parseInt(request.getParameter("idRam"))).get();
		List<Komponenta> lista = new LinkedList<Komponenta>();
		lista.add(gpu);
		lista.add(cpu);
		lista.add(hdd);
		lista.add(ssd);
		lista.add(maticna);
		lista.add(kuciste);
		lista.add(ram);
		konfiguracja.setKomponentas(lista);
		List<Javnakonfiguracija> listaK = new LinkedList<Javnakonfiguracija>();
		listaK.add(konfiguracja);
		if(gpu.getJavnakonfiguracijas()==null) {
			gpu.setJavnakonfiguracijas(listaK);
		}else {
			gpu.getJavnakonfiguracijas().add(konfiguracja);
		}
		
		if(cpu.getJavnakonfiguracijas()==null) {
			cpu.setJavnakonfiguracijas(listaK);
		}else {
			cpu.getJavnakonfiguracijas().add(konfiguracja);
		}
		
		if(hdd.getJavnakonfiguracijas()==null) {
			hdd.setJavnakonfiguracijas(listaK);
		}else {
			hdd.getJavnakonfiguracijas().add(konfiguracja);
		}
		
		if(ssd.getJavnakonfiguracijas()==null) {
			ssd.setJavnakonfiguracijas(listaK);
		}else {
			ssd.getJavnakonfiguracijas().add(konfiguracja);
		}
		
		if(maticna.getJavnakonfiguracijas()==null) {
			maticna.setJavnakonfiguracijas(listaK);
		}else {
			maticna.getJavnakonfiguracijas().add(konfiguracja);
		}
		
		if(ram.getJavnakonfiguracijas()==null) {
			ram.setJavnakonfiguracijas(listaK);
		}else {
			ram.getJavnakonfiguracijas().add(konfiguracja);
		}
		
		if(kuciste.getJavnakonfiguracijas()==null) {
			kuciste.setJavnakonfiguracijas(listaK);
		}else {
			kuciste.getJavnakonfiguracijas().add(konfiguracja);
		}
		
		jkr.saveAndFlush(konfiguracja);
		kmpr.flush();
		m.addAttribute("konfiguracija",konfiguracja);
		return "redirect:/prikazJavnihKonfiguracija";
	}
	@RequestMapping(value = "/admin/prikazBagPrijave", method = RequestMethod.GET)
	public String prikazBagPrijave(Model m,int idBagPrijave,String username) {
		Bagprijave prijava = br.findById(idBagPrijave).get();
		m.addAttribute("prijava",prijava);
		m.addAttribute("username",username);
		return "/prijave/prikazPrijaveBaga";
	}
	@RequestMapping(value = "/admin/prikazPrijaveKorisnika", method = RequestMethod.GET)
	public String prikazPrijaveKorisnika(Model m,int idPrijave,String username) {
		Korisnikprijave prijava = kpr.findById(idPrijave).get();
		m.addAttribute("prijava",prijava);
		m.addAttribute("username",username);
		return "/prijave/prikazPrijaveKorisnika";
	}
	@RequestMapping(value = "/admin/dodajKomponentuPage",method = RequestMethod.GET)
	public String ubaciKomponentu(Model m) {
		Komponenta k = new Komponenta();
		m.addAttribute("komponenta", k);
		return "/komponente/dodajKomponentu";
	}

	@RequestMapping(value="/admin/dodajKomponentu",method = RequestMethod.POST)
	public String addKomponenta(Model m,@ModelAttribute("komponenta")Komponenta komp) {
		if(kmpr.findAllBynazivKomponente(komp.getNazivKomponente()).size()>0)
			return "/errors/greskaKodPravljenjaKomponente";
		kmpr.save(komp);
		return "redirect:/prikazKomponenti";
	}
	@RequestMapping(value = "/admin/prijavePage")
	public String prijavePage() {
		return "/prijave/prijavePage";
	}
	
	@RequestMapping(value = "/admin/banUser",method = RequestMethod.POST)
	public String banUser(int idK) {
		Korisnik k = kr.findById(idK).get();
		List<Privatnakonfiguracija> konfiguracije = k.getPrivatnakonfiguracijas();
		List<Status> statusi = k.getStatuses();
		List<Bagprijave> prijaveBagova = k.getBagprijaves();
		List<Korisnikprijave> prijaveKorisnika = k.getKorisnikprijaves();
		
		List<Pratioci> pratioci = prtr.findAllBykorisnik(k);
		List<Pracenja> pracenja = prr.findAllBykorisnik(k);
		
		if(pratioci!=null && pratioci.size()>0) {
			for(Pratioci p:pratioci) {
				Korisnik krs = kr.findById(p.getIdDrugogKorisnika()).get();
				for(Pracenja prc:krs.getPracenjas()) {
					if(prc.getIdDrugogKorisnika()==idK) {
						prr.delete(prc);
						prr.flush();
					}
				}
				prtr.delete(p);
				prtr.flush();
			}
			k.setPratiocis(null);
		}
		
		if(pracenja!=null && pracenja.size()>0) {
			for(Pracenja p:pracenja) {
				Korisnik krs = kr.findById(p.getIdDrugogKorisnika()).get();
				for(Pratioci prc:krs.getPratiocis()) {
					if(prc.getIdDrugogKorisnika()==idK) {
						prtr.delete(prc);
						prtr.flush();
					}
				}
				prr.delete(p);
				prr.flush();
			}
			k.setPracenjas(null);
		}
		
		if(konfiguracije!=null && konfiguracije.size()>0) {
			for(Privatnakonfiguracija konf:konfiguracije) {
				for(Komponenta km : konf.getKomponentas()) {
					km.getPrivatnakonfiguracijas().remove(konf);
				}
				kmpr.flush();
				pkr.flush();
				pkr.delete(konf);
			}
		}
		if(prijaveBagova!=null && prijaveBagova.size()>0) {
			for(Bagprijave p:prijaveBagova) {
				br.delete(p);
				br.flush();
			}
			k.setBagprijaves(null);
		}
		if(prijaveKorisnika!=null && prijaveKorisnika.size()>0) {
			for(Korisnikprijave p:prijaveKorisnika) {
				kpr.delete(p);
				kpr.flush();
			}
			k.setKorisnikprijaves(null);
		}
		if(statusi!=null && statusi.size()>0) {
			for(Status s:statusi) {
				sr.delete(s);
				sr.flush();
			}
			k.setStatuses(null);
		}
		kr.flush();
		kr.delete(k);
		
		return "redirect:/mojProfil";
	}
	@RequestMapping(value = "/admin/brisanjeKonfiguracijePage",method = RequestMethod.GET)
	public String brisanjeJavKonfPage(Principal p,Model m) {
		List<Javnakonfiguracija> konfiguracije = jkr.findAll();
		Korisnik k = kr.findByusername(p.getName()).get();
		m.addAttribute("korisnik",k);
		m.addAttribute("konfiguracije", konfiguracije);
		return "/konfiguracije/izbrisiKonfiguraciju";
	}
	@RequestMapping(value = "/admin/brisanjeKonfiguracije",method = RequestMethod.POST)
	public String brisanjeJavKonf(HttpServletRequest request) {
		int idKonf = Integer.parseInt(request.getParameter("idKonf"));
		Javnakonfiguracija konf = jkr.findById(idKonf).get();
		for(Komponenta k : konf.getKomponentas()) {
			k.getJavnakonfiguracijas().remove(konf);
		}
		konf.setKomponentas(null);
		pkr.flush();
		kmpr.flush();
		jkr.delete(konf);
		return "redirect:/prikazJavnihKonfiguracija";
	}
	
	@RequestMapping(value = "/admin/reportProcenatPrijava",method = RequestMethod.GET)
	public void showReport1(HttpServletResponse response) throws Exception{
		int brojKorisnikaSaViseOdPet = 0;
		int procenat=0;
		List<Korisnik> korisnici = kr.findAll();
		List<KorisniciIBrojPrijava> kibrp = new LinkedList<KorisniciIBrojPrijava>();
		for(Korisnik k:korisnici) {
			if(k.getUloga().getNazivUloge().equals("Korisnik")) {
				kibrp.add(new KorisniciIBrojPrijava(k));
				if(k.getBagprijaves().size()+k.getKorisnikprijaves().size()>5) {
					brojKorisnikaSaViseOdPet++;
				}
			}
		}
		procenat = Math.round(brojKorisnikaSaViseOdPet*100/kibrp.size());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("procenat",String.valueOf( procenat)+"%");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(kibrp);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasper/Simple_Blue.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=ReportZaPrijave.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
	@RequestMapping(value = "/admin/reportProcenatKonf",method = RequestMethod.GET)
	public void showReport2(HttpServletResponse response) throws Exception{
		int brojKorisnikaSaKonfiguracijama = 0;
		int procenat=0;
		List<Korisnik> korisnici = kr.findAll();
		List<KorisniciIBrojPrijava> kibrp = new LinkedList<KorisniciIBrojPrijava>();
		for(Korisnik k:korisnici) {
			if(k.getUloga().getNazivUloge().equals("Korisnik")) {
				kibrp.add(new KorisniciIBrojPrijava(k));
				if(k.getPrivatnakonfiguracijas().size()>0) {
					brojKorisnikaSaKonfiguracijama++;
				}
			}
		}
		procenat = Math.round(brojKorisnikaSaKonfiguracijama*100/kibrp.size());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("procenat",String.valueOf( procenat)+"%");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(kibrp);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasper/ReportZaKonfiguracije.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=ReportZaKonfiguracije.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	


	@RequestMapping(value = "/pocetna",method = RequestMethod.GET)
	public String goToPocetna() {
		return "/logedIn/pocetna";
	}
}
