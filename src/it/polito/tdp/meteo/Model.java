package it.polito.tdp.meteo;

import java.text.SimpleDateFormat;
import java.util.*;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
    MeteoDAO dao;
    Set <Rilevamento> r;
    Set <Citta> c;
	public Model() {
		dao= new MeteoDAO();
        r = new HashSet <Rilevamento>(dao.getAllRilevamenti());
        c = creaListaCitta();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat ("yyyy-MM-dd");
        sf.format(date);
        
	}

	private Set<Citta> creaListaCitta() {
		Set <Citta> setc = new HashSet <Citta>();
		Set <String> sets = new HashSet <String>();
		Set <Rilevamento> setr = new HashSet <Rilevamento>(r);
		String citta ;
		for (Rilevamento ril: setr) {
			if (!sets.contains(ril.getLocalita())) {
				citta=ril.getLocalita();
				Citta c1 =new Citta(citta);
				setc.add(c1);
				sets.add(citta);
			} 
		}
	for (Citta ci : setc) {
		ci.setRilevamenti(dao.getRilevamentiCitta(ci));
	}
		return setc;
		
	}

	public String getUmiditaMedia(int mese) {
    String s="L' umidita media nelle citta controllate nel mese  '"+mese+"' e':\n";
    for (Citta citta : c) {
    	
    	s+= citta.getNome()+":  "+dao.getAvgRilevamentiLocalitaMese(mese, citta.getNome())+"\n";
    	
    }
    
    
		return s;
	}

	public String trovaSequenza(int mese) {
		List <SimpleCity> best =new LinkedList<SimpleCity> ();
		List <SimpleCity> parziale = new LinkedList<SimpleCity> () ;
		cerca (parziale, 1 , best, mese) ;
        
		return best.toString();
	}
	

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		for (SimpleCity sc : soluzioneCandidata) {
			score+=sc.getCosto();
		}
		return score;
	}

	public boolean controllaParziale(List<SimpleCity> parziale) {
		SimpleCity sc1 = parziale.get(0);
		int cont =0;
        for (SimpleCity sc : parziale) {
        	if (cont==0) {
        		
        	}else if(sc1.equals(sc)) {
        		cont++;
        		if (cont==3)
        			return false;
        	}
        	else {
        		sc.setCosto(sc.getCosto()+100);
        		cont =0;
        	}
        	
        }
		return true;
	}
	/*public List <Rilevamento> getRilevamentiPerMese(int mese, String localita){
		List <Rilevamento> lista = new LinkedList <Rilevamento>(dao.getAllRilevamentiLocalitaMese(mese, localita));
		return lista;
		
	}*/
   public void cerca(List <SimpleCity> parziale, int livello, List <SimpleCity> best, int mese) {
	   if (livello>16) {
		   if(controllaParziale(parziale) && punteggioSoluzione(parziale)> punteggioSoluzione(best)) {
			   best = parziale;
			   return;
		   }else return;
	   }
	   
	   
	   
	   
	   for (Citta citta : c) {
		       if (contatore(parziale, citta.getNome())<3){
			   int c1 =citta.getUD(mese,livello);
			   int c2 =citta.getUD(mese,livello+1);
			   int c3 =citta.getUD(mese,livello+2);
			   //int c2 =citta.getUD((2013-mese-livello+1));
			   //int c3 = citta.getUD((2013-mese-livello+2));
			   int c = c1+c2+c3;
			   SimpleCity sc= new SimpleCity(citta.getNome(), c);
			   parziale.add(sc);
			   cerca(parziale, livello+3, best, mese);
			   parziale.remove(sc);
			   System.out.println(parziale);}
		   
	   }
	   
   }



private int contatore(List<SimpleCity> parziale, String nome) {
	int cont =1;
	for (SimpleCity sc :parziale) {
		if (sc.getNome().equals(nome)) {
			cont++;
		}
	}
	return cont;
}
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}
