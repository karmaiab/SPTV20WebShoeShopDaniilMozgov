/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Person;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Danja
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "SPTV20WebShoeShopDaniilMozgovPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }
    
    public Person findByLogin(String login){
        try{
            return (Person)em.createQuery("SELECT p FROM Person p WHERE p.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
        }
        catch (Exception e){
            return null;
        }
    }
    
}
