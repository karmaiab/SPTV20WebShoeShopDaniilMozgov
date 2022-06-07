/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.CoverModel;
import entity.Model;
import java.awt.print.Book;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Danja
 */
@Stateless
public class CoverModelFacade extends AbstractFacade<CoverModel> {

    @PersistenceContext(unitName = "SPTV20WebShoeShopDaniilMozgovPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CoverModelFacade() {
        super(CoverModel.class);
    }
    
        public CoverModel findCoverByModel(Model model) {
        try {
            return (CoverModel) em.createQuery("SELECT cm FROM CoverModel cm WHERE cm.model=:model")
                    .setParameter("model", model)
                    .getSingleResult();
        } catch (Exception e) {
            return new CoverModel();
        }
    }
    
}
