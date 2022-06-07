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
import entity.RolePerson;
import entity.User;
import facade.CoverModelFacade;
import facade.ModelFacade;
import facade.PersonFacade;
import facade.RoleFacade;
import facade.RolePersonFacade;
import facade.UserFacade;
import java.io.IOException;
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
@WebServlet(name = "LoginServlet",loadOnStartup=1, urlPatterns = {
    "/showLogin",
    "/login",
    "/logout",
    "/listShoes",
    "/showRegistration",
    "/registration"
    
    
})
public class LoginServlet extends HttpServlet {
    @EJB private CoverModelFacade coverModelFacade;
    @EJB private ModelFacade modelFacade;
    @EJB private PersonFacade personFacade;
    @EJB private RoleFacade roleFacade;
    @EJB private RolePersonFacade rolePersonFacade;
    @EJB private UserFacade userFacade;
    
    @Override
    public void init() throws ServletException{
        super.init();
        if(personFacade.count()!=0)return;
        User user=new User();
        user.setName("Daniil");
        user.setSurname("Mozgov");
        user.setTel("24812874208");
        user.setAmountMoney(9999999);
        userFacade.create(user);
        Person person=new Person();
        person.setLogin("admin");
        PasswordProtected pp=new PasswordProtected();
        String salt=pp.getSalt();
        person.setSalt(salt);
        String password=pp.passwordEncript("12345", salt);
        person.setPassword(password);
        person.setUser(user);
        personFacade.create(person);
        Role role =new Role();
        role.setRoleName("USER");
        roleFacade.create(role);
        RolePerson rolePerson=new RolePerson();
        rolePerson.setRole(role);
        rolePerson.setPerson(person);
        rolePersonFacade.create(rolePerson);
        role= new Role();
        role.setRoleName("MANAGER");
        roleFacade.create(role);
        rolePerson=new RolePerson();
        rolePerson.setRole(role);
        rolePerson.setPerson(person);
        rolePersonFacade.create(rolePerson);
        role= new Role();
        role.setRoleName("ADMINISTRATOR");
        roleFacade.create(role);
        rolePerson=new RolePerson();
        rolePerson.setRole(role);
        rolePerson.setPerson(person);
        rolePersonFacade.create(rolePerson);
    }
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
        String path=request.getServletPath();
        switch(path){
            case "/showLogin":
                request.setAttribute("activeShowLogin", true);
                request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                break;
            case "/login":
                String login=request.getParameter("login");
                String password=request.getParameter("password");
                Person authPerson=personFacade.findByLogin(login);
                if(authPerson==null){
                request.setAttribute("info", "Неверный логин или пароль");
                request.getRequestDispatcher("/showLogin").forward(request, response);
                break;
                }
                PasswordProtected pp=new PasswordProtected();
                String salt=authPerson.getSalt();
                String sequrePassword=pp.passwordEncript(password, salt);
                if(!sequrePassword.equals(authPerson.getPassword())){
                request.setAttribute("info", "Неверный логин или пароль");
                request.getRequestDispatcher("/showLogin").forward(request, response);
                break;
                }
                HttpSession session=request.getSession(true);
                session.setAttribute("authPerson",authPerson);
                String topRoleAuthPerson=rolePersonFacade.getTopRole(authPerson);
                session.setAttribute("topRole", topRoleAuthPerson);
                request.setAttribute("info","Здравствуйте "+ authPerson.getUser().getName());
                request.getRequestDispatcher("/listShoes").forward(request, response);
                break;
            case "/logout":
                session=request.getSession(false);
                if(session!= null){
                    session.invalidate();
                }
                request.setAttribute("info", "Вы вышли!");
                request.setAttribute("activeLogout", true);
                request.getRequestDispatcher("/showLogin").forward(request, response);
                break;
            case "/listShoes":
                Map<Model,Cover> mapModels = new HashMap<>();
                List<Model> models = modelFacade.findAll();
                for(Model m : models){
                    CoverModel coverModel = coverModelFacade.findCoverByModel(m);
                    mapModels.put(m, coverModel.getCover());
                }
                request.setAttribute("mapModels", mapModels);
                request.setAttribute("activeListShoes", true);
                request.getRequestDispatcher("/listShoes.jsp").forward(request, response);
                break;
                
            case "/showRegistration":
                request.getRequestDispatcher("/showRegistration.jsp").forward(request, response);
                break;
            case "/registration":
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String phone = request.getParameter("phone");
                login = request.getParameter("login");
                String money=request.getParameter("money");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");
                if(!password1.equals(password2)){
                    request.setAttribute("firstname", firstname);
                    request.setAttribute("lastname", lastname);
                    request.setAttribute("phone", phone);
                    request.setAttribute("login", login);
                    request.setAttribute("money", money);
                    request.setAttribute("info", "Не совпадают пароли");
                    request.getRequestDispatcher("/showRegistration").forward(request, response);
                    break;
                }
                if("".equals(firstname) 
                        || "".equals(lastname)
                        || "".equals(phone)
                        || "".equals(login)
                        || "".equals(password1)
                        || "".equals(password2)
                        ){
                    request.setAttribute("firstname", firstname);
                    request.setAttribute("lastname", lastname);
                    request.setAttribute("phone", phone);
                    request.setAttribute("login", login);
                    request.setAttribute("money", money);
                    request.setAttribute("info", "Заполните все поля ( Количество денег не обязательно )");
                    request.getRequestDispatcher("/showRegistration").forward(request, response);
                    break;
                }
                User user = new User();
                user.setName(firstname);
                user.setSurname(lastname);
                user.setTel(phone);
                try {
                    user.setAmountMoney(Integer.parseInt(money));
                } catch (Exception e) {
                    request.setAttribute("firstname", firstname);
                    request.setAttribute("lastname", lastname);
                    request.setAttribute("phone", phone);
                    request.setAttribute("login", login);
                    request.setAttribute("money", money);
                    request.setAttribute("info", "Количество денег - пишите цифрами!");
                    request.getRequestDispatcher("/showRegistration").forward(request, response);
                    break;
                }
                userFacade.create(user);
                Person person = new Person();
                person.setLogin(login);
                pp = new PasswordProtected();
                salt = pp.getSalt();
                person.setSalt(salt);
                sequrePassword = pp.passwordEncript(password1, salt);
                person.setPassword(sequrePassword);
                person.setUser(user);
                personFacade.create(person);
                
                Role userRole = roleFacade.findByRoleName("USER");
                if(userRole == null){
                    request.setAttribute("firstname", firstname);
                    request.setAttribute("lastname", lastname);
                    request.setAttribute("phone", phone);
                    request.setAttribute("login", login);
                    request.setAttribute("money", money);
                    request.setAttribute("info", "Не найдена роль!");
                    request.getRequestDispatcher("/showRegistration").forward(request, response);
                    break;
                }
                RolePerson rolePerson = new RolePerson();
                rolePerson.setRole(userRole);
                rolePerson.setPerson(person);
                rolePersonFacade.create(rolePerson);
                request.setAttribute("info", "Добавлен новый пользователь");
                request.getRequestDispatcher("/listShoes").forward(request, response);
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
