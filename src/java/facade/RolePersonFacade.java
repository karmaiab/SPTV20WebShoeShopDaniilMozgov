/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Person;
import entity.Role;
import entity.RolePerson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Danja
 */
@Stateless
public class RolePersonFacade extends AbstractFacade<RolePerson> {
    @EJB private RoleFacade roleFacade;

    @PersistenceContext(unitName = "SPTV20WebShoeShopDaniilMozgovPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolePersonFacade() {
        super(RolePerson.class);
    }
    public boolean isRole(String roleName, Person authPerson) {
        List<String> listRoleNames = em.createQuery("SELECT rp.role.roleName FROM RolePerson rp WHERE rp.person = :authPerson")
                .setParameter("authPerson", authPerson)
                .getResultList();
        if(listRoleNames.contains(roleName)){
            return true;
        }else{
            return false;
        }
    }

    public String getTopRole(Person person) {
        List<String> listRoleNames = em.createQuery("SELECT rp.role.roleName FROM RolePerson rp WHERE rp.person = :person")
                .setParameter("person", person)
                .getResultList();
        if(listRoleNames.contains("ADMINISTRATOR"))return "ADMINISTRATOR";
        if(listRoleNames.contains("MANAGER"))return "MANAGER";
        if(listRoleNames.contains("USER"))return "USER";
        return null;
    }
    
    private void removeAllPersonRoles(Person person){
        em.createQuery("DELETE FROM RolePerson rp WHERE rp.person = :person")
                .setParameter("person", person)
                .executeUpdate();
    }
    
    public void setRoleToUser(Role role, Person person) {
        removeAllPersonRoles(person);
        RolePerson rolePerson = null;
        if("ADMINISTRATOR".equals(role.getRoleName())){
            Role roleUSER = roleFacade.findByRoleName("USER");
            rolePerson = new RolePerson();
            rolePerson.setRole(roleUSER);
            rolePerson.setPerson(person);
            this.create(rolePerson);
            Role roleMANAGER = roleFacade.findByRoleName("MANAGER");
            rolePerson = new RolePerson();
            rolePerson.setRole(roleMANAGER);
            rolePerson.setPerson(person);
            this.create(rolePerson);
            Role roleADMINISTRATOR = roleFacade.findByRoleName("ADMINISTRATOR");
            rolePerson = new RolePerson();
            rolePerson.setRole(roleADMINISTRATOR);
            rolePerson.setPerson(person);
            this.create(rolePerson);
        }
        if("MANAGER".equals(role.getRoleName())){
            Role roleUSER = roleFacade.findByRoleName("USER");
            rolePerson = new RolePerson();
            rolePerson.setRole(roleUSER);
            rolePerson.setPerson(person);
            this.create(rolePerson);
            Role roleMANAGER = roleFacade.findByRoleName("MANAGER");
            rolePerson = new RolePerson();
            rolePerson.setRole(roleMANAGER);
            rolePerson.setPerson(person);
            this.create(rolePerson);
        }
        if("USER".equals(role.getRoleName())){
            Role roleUSER = roleFacade.findByRoleName("USER");
            rolePerson = new RolePerson();
            rolePerson.setRole(roleUSER);
            rolePerson.setPerson(person);
            this.create(rolePerson);
        }
        
    }
    
}
