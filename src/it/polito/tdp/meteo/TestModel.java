package it.polito.tdp.meteo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		//System.out.println(m.getRilevamentiPerMese(6, "Genova"));
		//System.out.println(m.getUmiditaMedia(12));(new Rilevamento ("torino", new Date (), 82)
		List <Rilevamento> r1 = new LinkedList <Rilevamento>();
		List <Rilevamento> r2 = new LinkedList <Rilevamento>();
		List <Rilevamento> r3 = new LinkedList <Rilevamento>();
		r1.add(new Rilevamento ("torino", new Date (), 84));
		r1.add(new Rilevamento ("Bologna", new Date (), 83));
		r1.add(new Rilevamento ("torino", new Date (), 81));
		List <SimpleCity> c = new LinkedList<SimpleCity>();
		c.add(new SimpleCity ("torino", 56));
		c.add(new SimpleCity ("torino", 44));
		c.add(new SimpleCity ("torino", 55));
		c.add(new SimpleCity ("torino", 55));
		
		m.controllaParziale(c);
		 System.out.println(m.trovaSequenza(5));
		 System.out.println(m.controllaParziale(c));
		 System.out.println(m.punteggioSoluzione(c));
//		System.out.println(m.trovaSequenza(4));
		 
	}

}
