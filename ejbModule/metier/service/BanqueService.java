package metier.service;

import java.util.Date;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import metier.entities.Compte;
import metier.session.IBanqueLocal;
@Stateless
@WebService
public class BanqueService {
	@EJB
	private IBanqueLocal metier;
	@WebMethod
	public void addCompte(@WebParam(name="solde") double soldeInitiale) {
		Compte cp =new Compte(soldeInitiale,new Date(),true);
		metier.addCompte(cp);
	}
	@WebMethod
	public List<Compte> listComptes() {
		return metier.getAllComptes();
	}
	@WebMethod
	public Compte getCompte(@WebParam(name="code") Long code) {
		return metier.getCompte(code);
	}
	@WebMethod
	public void verser(@WebParam(name="montant")
	double montant, @WebParam(name="code") Long code) {
		metier.verser(montant, code);;
	}
	@WebMethod
	public void retirer(@WebParam(name="montant")
	double montant, @WebParam(name="code") Long code) {
		metier.retirer(montant, code);
	}
	
	@WebMethod
	public void virement(
			@WebParam(name="montant")
	double montant, @WebParam(name="cpte1") Long cpte1,
	@WebParam(name="cpte2") Long cpte2) {
		metier.virement(montant, cpte1, cpte2);
	}

}
