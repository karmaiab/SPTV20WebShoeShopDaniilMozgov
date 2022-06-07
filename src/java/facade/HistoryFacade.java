/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.History;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Danja
 */
@Stateless
public class HistoryFacade extends AbstractFacade<History> {

    @PersistenceContext(unitName = "SPTV20WebShoeShopDaniilMozgovPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoryFacade() {
        super(History.class);
    }
    
        public List<History> historyOfPurchaseModel(User user) {
        try {
            return em.createQuery("SELECT h FROM History h WHERE h.user = :user")
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
