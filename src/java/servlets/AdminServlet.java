/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Cover;
import entity.CoverModel;
import entity.Model;
import entity.Person;
import entity.Role;
import entity.User;
import facade.CoverFacade;
import facade.CoverModelFacade;
import facade.ModelFacade;
import facade.PersonFacade;
import facade.RoleFacade;
import facade.RolePersonFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Danja
 */
@WebServlet(name = "AdminServlet", urlPatterns = {
    "/adminPanel",
    "/changeRole",
    "/showEditShoesList",
    "/showEditShoes",
    "/editModel"
})
public class AdminServlet extends HttpServlet {
    @EJB private RolePersonFacade rolePersonFacade;
    @EJB private CoverFacade coverFacade;
    @EJB private CoverModelFacade coverModelFacade;
    @EJB private ModelFacade modelFacade;
    @EJB private RoleFacade roleFacade;
    @EJB private PersonFacade personFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if(session == null){
            request.setAttribute("info", "Авторизуйтесь!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        Person authPerson = (Person) session.getAttribute("authPerson");
        if(authPerson == null){
            request.setAttribute("info", "Авторизуйтесь!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        if(!rolePersonFacade.isRole("ADMINISTRATOR",authPerson)){
            request.setAttribute("info", "У вас нет прав!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        
        String path = request.getServletPath();
        switch (path) {
            case "/adminPanel":
                request.setAttribute("activeAdminPanel", true);
                Map<Person,String> mapPersons = new HashMap<>();
                List<Person> persons = personFacade.findAll();
                for(Person p : persons){
                    String topRole = rolePersonFacade.getTopRole(p);
                    mapPersons.put(p, topRole);
                }
                request.setAttribute("mapPersons", mapPersons);
                List<Role> roles = roleFacade.findAll();
                request.setAttribute("roles", roles);
                request.getRequestDispatcher("/WEB-INF/adminPanel.jsp").forward(request, response);
                break;
            case "/changeRole":
                String personId = request.getParameter("personId");
                String roleId = request.getParameter("roleId");
                Person person = personFacade.find(Long.parseLong(personId));
                    if("admin".equals(person.getLogin())){
                            request.setAttribute("info", "Вы не можете изменить роль этому пользователю");
                            request.getRequestDispatcher("/adminPanel").forward(request, response);
                        break;
                    }
                Person p = personFacade.find(Long.parseLong(personId));
                Role r = roleFacade.find(Long.parseLong(roleId));
                rolePersonFacade.setRoleToUser(r,p);
                request.setAttribute("info", "Роль изменена");
                request.getRequestDispatcher("/adminPanel").forward(request, response);
                break;
            case "/showEditShoesList":
                request.setAttribute("activeShowEditShoesList", true);
                List<Model> models = modelFacade.findAll();
                Map<Model,Cover> mapModels = new HashMap<>();
                for(Model m : models){
                    CoverModel coverModel = coverModelFacade.findCoverByModel(m);
                    mapModels.put(m, coverModel.getCover());
                }
                request.setAttribute("mapModels", mapModels);
                request.getRequestDispatcher("/WEB-INF/showEditShoesList.jsp").forward(request, response);
                break;
            case "/showEditShoes":
                Model model=modelFacade.find(Long.parseLong(request.getParameter("modelId")));
                List<Cover> cover=coverFacade.findAll();
                request.setAttribute("covers2", cover);
                request.setAttribute("model2", model.getName());
                request.setAttribute("brand2", model.getBrand());
                request.setAttribute("size2", model.getSize());
                request.setAttribute("quantity2", model.getQuantity());
                request.setAttribute("price2", model.getPrice());
                request.setAttribute("id", model.getId());
                request.getRequestDispatcher("/WEB-INF/showEditShoes.jsp").forward(request, response);
                break;
            case "/editModel":
                String model2=request.getParameter("model2");
                String brand2=request.getParameter("brand2");
                String size2=request.getParameter("size2");
                String price2=request.getParameter("price2");
                String covers2=request.getParameter("coverId2");
                String quantity2=request.getParameter("quantity2");
                String id=request.getParameter("id");
                model = modelFacade.find(Long.parseLong(id));
                model.setBrand(brand2);
                model.setName(model2);
                model.setPrice(Integer.parseInt(price2));
                model.setSize(Integer.parseInt(size2));
                model.setQuantity(Integer.parseInt(quantity2));
                CoverModel coverModel=coverModelFacade.findCoverByModel(model);
                Cover cov=coverFacade.find(Long.parseLong(covers2));
                coverModel.setCover(cov);
                modelFacade.edit(model);
                coverModelFacade.edit(coverModel);
                request.setAttribute("info", "Данные изменены и сохранены!");
                request.getRequestDispatcher("/showEditShoesList").forward(request, response);
                break;
        }       
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
