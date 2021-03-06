package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.metadata.GenericTableMetaDataProvider;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;

public class ComptabiliteManagerImplTest extends BusinessTestCase{

	private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
	private  ComptabiliteManager managerIntegration  = getBusinessProxy().getComptabiliteManager() ;
	EcritureComptable vEcritureComptable = new EcritureComptable();

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnit() throws Exception {
		Date vCurrentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String vCurrentYear = sdf.format(vCurrentDate);
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(vCurrentDate);
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.setReference("VE-" + vCurrentYear + "/00004");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitViolation() throws Exception {
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG2() throws Exception {
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(1234)));
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG3() throws Exception {
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	 @Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG5() throws Exception {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String vCurrentYear = sdf.format(new Date());
	        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
	        vEcritureComptable.setDate(new Date());
	        vEcritureComptable.setLibelle("Libelle");
	        vEcritureComptable.setReference("VE-" + vCurrentYear + "/00004");
	        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
	                null, new BigDecimal(123),
	                null));
	        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
	                null, null,
	                new BigDecimal(123)));

	            manager.checkEcritureComptableUnit(vEcritureComptable);

	      
	    }
	 
	@Test
	public void addReference() throws FunctionalException, NotFoundException {
		vEcritureComptable.setId(-1);
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		try {
			vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2016/05/31"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		vEcritureComptable.setLibelle("Fourniture bureau");
		vEcritureComptable.getListLigneEcriture().add(
				new LigneEcritureComptable(new CompteComptable(606), "Fourniture bureau", new BigDecimal(43), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(4456), "TVA 20%", new BigDecimal(8), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(401), "Facture F110001", null, new BigDecimal(51)));

		managerIntegration.addReference(vEcritureComptable);
		
	}
	

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptable() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		Date vCurrentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String vCurrentYear = sdf.format(vCurrentDate);
		// System.out.println(" year "+vCurrentYear);
		vEcritureComptable.setJournal(new JournalComptable("VE", "Vente"));
		vEcritureComptable.setDate(vCurrentDate);
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.setReference("VE-" + vCurrentYear + "/00004");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		manager.checkEcritureComptable(vEcritureComptable);
	}

}
