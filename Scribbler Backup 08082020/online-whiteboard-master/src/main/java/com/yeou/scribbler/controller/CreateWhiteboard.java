/*
 * @author  Yeou Liu
 */
package com.yeou.scribbler.controller;

import com.yeou.scribbler.hash.Hash;
import com.yeou.scribbler.model.LoginDAO;
import com.yeou.scribbler.model.SessionUtils;
import com.yeou.scribbler.model.UserData;
import com.yeou.scribbler.model.Whiteboard;
import com.yeou.scribbler.utils.FacesAccessor;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Managed bean for the login dialog if a new whiteboard is created.
 *
 * @author Yeou Liu
 */
public class CreateWhiteboard implements Serializable {

    //~ Static fields/initializers ---------------------------------------------
    private static final long serialVersionUID = 20110501L;

    //~ Instance fields --------------------------------------------------------
    private Whiteboard whiteboard;
    private WhiteboardsManager whiteboardsManager;
    private String pubSubTransport = "websocket";
    private List<SelectItem> pubSubTransports;

    static {
        /* works fine! ! */
        System.setProperty("java.awt.headless", "false");
        System.out.println(java.awt.GraphicsEnvironment.isHeadless());
        /* ---> prints true */
    }

    //~ Methods ----------------------------------------------------------------
    @PostConstruct
    /**
     * Creates and initialize a new whiteboard. This method is called
     * automatically by JSF facility.
     */
    protected void initialize() {
        // create an empty whiteboard and uuid container of the current whiteboard
        whiteboard = new Whiteboard();
    }

    public String getTitle() {
        return whiteboard.getTitle();
    }

    public void setTitle(String title) {
        whiteboard.setTitle(title);
    }

    public String getCreator() {
        return whiteboard.getCreator();
    }

    public void setCreator(String userName) {
        whiteboard.setCreator(userName);
    }

    public int getWidth() {
        return whiteboard.getWidth();
    }

    public void setWidth(int width) {
        whiteboard.setWidth(width);
    }

    public int getHeight() {
        return whiteboard.getHeight();
    }

    public void setHeight(int height) {
        whiteboard.setHeight(height);
    }

    public void setWhiteboardsManager(WhiteboardsManager whiteboardsManager) {
        this.whiteboardsManager = whiteboardsManager;
    }

    public String getPubSubTransport() {
        return pubSubTransport;
    }

    public void setPubSubTransport(String pubSubTransport) {
        this.pubSubTransport = pubSubTransport;
    }

    public String getWhiteboardId() {
        return whiteboard.getUuid();
    }

    public void setWhiteboardId(String whiteboardId) {
        this.whiteboard.setUuid(whiteboardId);
    }

    public void setPassword(String pass) {
        whiteboard.setPassword(pass);
    }

    public String getPassword() {
        return whiteboard.getPassword();
    }

    /**
     * Creates a new whiteboard
     *
     * @return string outcome for navigation
     */
//    public String create() {
//        String senderId = UUID.randomUUID().toString();
//
//        whiteboard.setCreationDate(new Date());
//        whiteboard.addUserData(new UserData(senderId, getCreator()));
//        whiteboard.setPubSubTransport(pubSubTransport);
//        whiteboardsManager.addWhiteboard(whiteboard);
//
//        WhiteboardIdentifiers wi = ((WhiteboardIdentifiers) FacesAccessor.getManagedBean("whiteboardIdentifiers"));
//        wi.setSenderId(senderId);
//        wi.setWhiteboardId(whiteboard.getUuid());
//
//        return "pretty:wbWorkplace";
//    }
    public List getTransports() {
        if (pubSubTransports == null) {
            pubSubTransports = new ArrayList<SelectItem>();
            pubSubTransports.add(new SelectItem("websocket", "WebSocket"));
            pubSubTransports.add(new SelectItem("long-polling", "Long-Polling"));
            pubSubTransports.add(new SelectItem("streaming", "Streaming"));
        }

        return pubSubTransports;
    }

    //    *********************************Redirect to registration Page************************************
    public String register() {
        return "pretty:wbRegister";
    }

    //************************************login register and logout methods*************************************
    public String validateUserLogin() {

        Hash hash = new Hash();
        // hash password to store
        String passwordHash = hash.encryptThisString(whiteboard.getPassword());

//        boolean valid = LoginDAO.validate(whiteboard.getCreator(), whiteboard.getPassword());
        boolean valid = LoginDAO.validate(whiteboard.getCreator(), passwordHash);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("creator", whiteboard.getCreator());
            return "pretty:wbCreate";
            //return "createWhiteboard";wbCreate
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    "loginForm",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Entered User Name or Password is incorrect",
                            "Please enter correct Login Credentials"));
            return "login";
        }
    }

    //    *************************register user to the application, if the user already exists then error************************
    public String validateUserRegister() {

        Hash hash = new Hash();
        // hash password to store
        String passwordHash = hash.encryptThisString(whiteboard.getPassword());
        boolean status = LoginDAO.register(whiteboard.getCreator(), passwordHash);
        if (status) {
//            HttpSession session = SessionUtils.getSession();
//            session.setAttribute("emailid", emailid);
            return "pretty:wbLogin";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    "registrationForm",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Entered User Name is incorrect or already in use",
                            "Please enter correct Login Credentials"));
            return "register";
        }
    }

    //    ****************************Create the whiteboard*************************
    public String create() {

        HttpSession session = SessionUtils.getSession();
        String owner = (String) session.getAttribute("creator");
        setCreator(owner);

        String senderId = UUID.randomUUID().toString();
        //Save whiteboard data in the database
        LoginDAO.addWbData(whiteboard.getUuid(), whiteboard.getTitle());
        //Save whiteboard data corresponding to its owner
        LoginDAO.saveUserBoardData(whiteboard.getUuid(), whiteboard.getCreator());

        whiteboard.setCreationDate(new Date());
        //     whiteboard.addUserData(new UserData(senderId, getCreator()));
        whiteboard.addUserData(new UserData(senderId, getCreator()));
        whiteboard.setPubSubTransport(pubSubTransport);
        whiteboardsManager.addWhiteboard(whiteboard);

        WhiteboardIdentifiers wi = ((WhiteboardIdentifiers) FacesAccessor.getManagedBean("whiteboardIdentifiers"));
        wi.setSenderId(senderId);
        wi.setWhiteboardId(whiteboard.getUuid());

        //set whiteboard id as a session attribute
        session.setAttribute("whiteboardId", whiteboard.getUuid());
        //String wbsessionId = String()
        System.out.println("Board ID: " + (String) session.getAttribute("whiteboardId"));

        return "pretty:wbWorkplace";
    }

    /**
     * Creates a new whiteboard
     *
     * @return string outcome for navigation
     */
    public String joinSessionBoard() {
        String senderId = UUID.randomUUID().toString(); //whiteboardid

        boolean status = LoginDAO.checkUname(getCreator(), getPassword());

        if (status) {

            HttpSession session = SessionUtils.getSession();
            String owner = (String) session.getAttribute("creator");
            //setUser(owner);

            if (owner.equals(getCreator())) {
                FacesContext.getCurrentInstance().addMessage(
                        "joinForm",
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "The user is already present in the session.",
                                "Please enter using different credentials"));
                return "pretty:wbJoin";
            } else {
                whiteboard.addUserData(new UserData(senderId, getCreator()));
                whiteboardsManager.updateWhiteboard(whiteboard);
                WhiteboardIdentifiers wi = ((WhiteboardIdentifiers) FacesAccessor.getManagedBean("whiteboardIdentifiers"));
                wi.setSenderId(senderId);
                wi.setWhiteboardId(whiteboard.getUuid());
                System.out.println("senderid:" + senderId);
                System.out.println("uuid join:" + whiteboard.getUuid());
                return "pretty:wbWorkplace";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    "joinForm",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "The entered credentials do not exist in the database. Please register yourself or try again",
                            "Please register yourself"));
            return "pretty:wbJoin";
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "pretty:wbLogin";
    }

//    Hash hash = new Hash();
//            // hash password to compare
//            String passwordHash = hash.encryptThisString(password);
       //    *************************register user to the application, if the user already exists then error************************
    public String validateUserRegisterJoin() {

        Hash hash = new Hash();
        // hash password to store
        String passwordHash = hash.encryptThisString(whiteboard.getPassword());
        boolean status = LoginDAO.register(whiteboard.getCreator(), passwordHash);
        if (status) {
//            HttpSession session = SessionUtils.getSession();
//            session.setAttribute("emailid", emailid);
            return "pretty:wbJoin";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    "registrationForm",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Entered User Name is incorrect or already in use",
                            "Please enter correct Login Credentials"));
            return "register";
        }
    }
    
     public String loginRedirect() {
        return "pretty:wbLogin";
    }
     
     public String joinRedirect() {
        return "pretty:wbJoin";
    }
}
