package com.example.demo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.repository.TipKomponenteRepository;

import model.Tipkomponente;

public class TipkomponenteConverter implements Converter<String, Tipkomponente>{
	TipKomponenteRepository tkr;
	public TipkomponenteConverter(TipKomponenteRepository tkr) {
		// TODO Auto-generated constructor stub
		this.tkr=tkr;
	}
	@Override
	public Tipkomponente convert(String source) {
		int tipId = -1;
		try {
			tipId = Integer.parseInt(source);
		}catch (NumberFormatException e) {
			throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(Tipkomponente.class),tipId, null);
		}
		Tipkomponente tip = tkr.findById(tipId).get();
		return tip;
	}

}
