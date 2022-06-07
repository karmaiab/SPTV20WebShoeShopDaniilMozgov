/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Cover;
import entity.CoverModel;
import entity.History;
import entity.Model;
import entity.Person;
import entity.User;
import facade.CoverModelFacade;
import facade.HistoryFacade;
import facade.ModelFacade;
import facade.PersonFacade;
import facade.RolePersonFacade;
import facade.UserFacade;
import java.io.IOException;
import java.util.Calendar;
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
import tools.PasswordProtected;

/**
 *
 * @author Danja
 */
@WebServlet(name = "UserServlet", urlPatterns = {
    "/showTakeOnModel",
    "/takeOnModel",
    "/showEditMe",
    "/editMe",
    "/showEditPassword",
    "/editPassword"
})
public class UserServlet extends HttpServlet {
    @EJB private PersonFacade personFacade;
    @EJB private UserFacade userFacade;
    @EJB private ModelFacade modelFacade;
    @EJB private HistoryFacade historyFacade;
    @EJB private RolePersonFacade rolePersonFacade;
    @EJB private CoverModelFacade coverModelFacade;
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
        if(authPerson== null){
            request.setAttribute("info", "Авторизуйтесь!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        if(!rolePersonFacade.isRole("USER",authPerson)){
            request.setAttribute("info", "У вас нет прав!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        session.setAttribute("topRole", rolePersonFacade.getTopRole(authPerson));
        String path = request.getServletPath();
        switch (path) {
            case "/showTakeOnModel":
                request.setAttribute("activeShowTakeOnModel", true);
                List<Model> models = modelFacade.findAll();
                Map<Model,Cover> mapModels = new HashMap<>();
                for(Model m : models){
                    CoverModel coverModel = coverModelFacade.findCoverByModel(m);
                    mapModels.put(m, coverModel.getCover());
                }
                request.setAttribute("mapModels", mapModels);
                request.getRequestDispatcher("/WEB-INF/showTakeOnModel.jsp").forward(request, response);
                break;
            case "/takeOnModel":
                String modelId = request.getParameter("modelId");
                Model selectedModel = modelFacade.find(Long.parseLong(modelId));
                if(selectedModel.getQuantity()!=0 & authPerson.getUser().getAmountMoney()>=selectedModel.getPrice()){
                    selectedModel.setQuantity(selectedModel.getQuantity()-1);
                    authPerson.getUser().setAmountMoney(authPerson.getUser().getAmountMoney()-selectedModel.getPrice());
                    userFacade.edit(authPerson.getUser());
                    modelFacade.edit(selectedModel);
                    History history = new History();
                    history.setModel(selectedModel);
                    User user = userFacade.find(authPerson.getUser().getId());
                    history.setUser(user);
                    history.setPurchaseModel(Calendar.getInstance().getTime());
                    historyFacade.create(history);
                    request.setAttribute("info", "Покупка произведена");
                    request.getRequestDispatcher("/showTakeOnModel").forward(request, response);
                    break;
                }else{
                    request.setAttribute("info", "У вас не хватает денежных средств!");
                    request.getRequestDispatcher("/showTakeOnModel").forward(request, response);
                    break;
                }
            case "/showEditMe":
                request.setAttribute("activeShowEditMe", true);
                Person person=personFacade.find(authPerson.getId());
                request.setAttribute("login2", person.getLogin());
                request.setAttribute("password2",person.getPassword());
                request.setAttribute("name2", person.getUser().getName());
                request.setAttribute("surname2", person.getUser().getSurname());
                request.setAttribute("tel2", person.getUser().getTel());
                request.setAttribute("money2", person.getUser().getAmountMoney());
                request.setAttribute("salt", person.getSalt());
                request.setAttribute("id", person.getId());
                request.getRequestDispatcher("/WEB-INF/showEditMe.jsp").forward(request, response);
                break;
            case "/editMe":
                String login2=request.getParameter("login2");
                String name2=request.getParameter("name2");
                String surname2=request.getParameter("surname2");
                String tel2=request.getParameter("tel2");
                String money2=request.getParameter("money2");
                String id=request.getParameter("id");
                Person persons=personFacade.find(Long.parseLong(id));
                User users=userFacade.find(Long.parseLong(id));
                persons.setLogin(login2);
                users.setName(name2);
                users.setSurname(surname2);
                users.setTel(tel2);
                users.setAmountMoney(Integer.parseInt(money2));
                personFacade.edit(persons);
                userFacade.edit(users);
                request.setAttribute("info", "Данные изменены и сохранены!");
                request.getRequestDispatcher("/showEditMe").forward(request, response);
                break;
            case "/showEditPassword":
                request.getRequestDispatcher("/WEB-INF/showEditPassword.jsp").forward(request, response);
                break;
            case "/editPassword":
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");
                if(!password1.equals(password2)){
                    request.setAttribute("info", "Не совпадают пароли");
                    request.getRequestDispatcher("/showEditPassword").forward(request, response);
                    break;
                }
                if("".equals(password1)|| "".equals(password2)
                        ){
                    request.setAttribute("info", "Заполните все поля ");
                    request.getRequestDispatcher("/showEditPassword").forward(request, response);
                    break;
                }
                PasswordProtected pp = new PasswordProtected();
                Long id2=authPerson.getId();
                Person perso=personFacade.find(id2);
                String salt = pp.getSalt();
                perso.setSalt(salt);
                String sequrePassword = pp.passwordEncript(password1, salt);
                perso.setPassword(sequrePassword);
                personFacade.edit(perso);
                request.setAttribute("info", "Данные изменены и сохранены!");
                request.getRequestDispatcher("/showEditMe").forward(request, response);
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
