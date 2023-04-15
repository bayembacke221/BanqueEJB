package metier.session;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import metier.entities.Compte;
@Stateless(name="BK")
public class BanqueEJBImpl implements IBanqueLocal,IBanqueRemote {
	/**
	 * si on a plusieurs bases dans notre fichier
	 * persistence.xml 
	 * on utilise le name dans notre persistence-unit
	 * pour specifier le persistence context
	 * */
	@PersistenceContext(unitName = "UP_BANQUE")
	private EntityManager em;
	@Override
	public void addCompte(Compte c) {
		em.persist(c);
	}
	@Override
	public List<Compte> getAllComptes() {
		Query req = em.
				createQuery("select c from Compte c");
		return req.getResultList();
	}
	@Override
	public Compte getCompte(Long id) {
		Compte cp = em.find(Compte.class, id);
		if(cp==null) throw new RuntimeException("Compte introuvable");
		return cp;
	}
	@Override
	public void verser(double mt, Long code) {
		Compte cp = getCompte(code);
		cp.setSolde(cp.getSolde() +mt);
		em.persist(cp);
	}
	@Override
	public void retirer(double mt, Long code) {
		Compte cp = getCompte(code);
		cp.setSolde(cp.getSolde() -mt);		
	}
	@Override
	public void virement(double mt, Long cpte1, Long cpte2) {
		retirer(mt, cpte1);
		verser(mt, cpte2);
	}
	@Override
	public void updateCompte(Compte c) {
		em.merge(c);
	}
	@Override
	public void supprimerCompte(Long code) {
		Compte cp = getCompte(code);
		em.remove(cp);
	}

}
