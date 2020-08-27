/*
* @author  Yeou Liu
 */
package com.yeou.scribbler.controller;

import com.yeou.scribbler.errorhandler.DefaultExceptionHandler;
import com.yeou.scribbler.hash.Hash;
import com.yeou.scribbler.model.LoginDAO;
import com.yeou.scribbler.model.SessionUtils;
import com.yeou.scribbler.model.UserData;
import com.yeou.scribbler.model.Whiteboard;
import com.yeou.scribbler.utils.FacesAccessor;


import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import javax.imageio.ImageIO;

/**
 * Managed Bean responsible for managing new users who have joined the session
 * @author Yeou Liu
 */
public class JoinWhiteboard implements Serializable {

    private static final long serialVersionUID = 20110506L;

    private String user = "";
    private Whiteboard whiteboard;
    private WhiteboardsManager whiteboardsManager;

    private String pwd;

    @PostConstruct
    /**
     * This function finds the current whiteboard session using getRequestParameter(whiteboardId)
     * It is called automatically
     */
    protected void initialize() {
        String uuid = FacesAccessor.getRequestParameter("whiteboardId");

        if (uuid != null) {
            whiteboard = whiteboardsManager.getWhiteboard(uuid);
        } else {
            DefaultExceptionHandler.doRedirect(FacesContext.getCurrentInstance(), "/views/error.jsf?statusCode=601");
            return;
        }

        if (whiteboard == null) {
            DefaultExceptionHandler.doRedirect(FacesContext.getCurrentInstance(), "/views/error.jsf?statusCode=602");
        }
    }

    public String getTitle() {
        if (whiteboard == null) {
            return "";
        }

        return whiteboard.getTitle();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setWhiteboardsManager(WhiteboardsManager whiteboardsManager) {
        this.whiteboardsManager = whiteboardsManager;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * Joins to the existing whiteboard session
     * 
     * @return string outcome for navigation
     */
    public String join() {
        String senderId = UUID.randomUUID().toString();

        whiteboard.addUserData(new UserData(senderId, getUser()));
        whiteboardsManager.updateWhiteboard(whiteboard);

        WhiteboardIdentifiers wi = ((WhiteboardIdentifiers) FacesAccessor.getManagedBean("whiteboardIdentifiers"));
        wi.setSenderId(senderId);
        wi.setWhiteboardId(whiteboard.getUuid());

        return "pretty:wbWorkplace";
    }

    //    Only Users registered to the Application can join the session. If they are not registered members, they will be redirected to register page
//    public String joinSession() {
//        String senderId = UUID.randomUUID().toString(); //whiteboardid
//
//        boolean status = LoginDAO.checkUname(getUser(), getPwd());
//
//        if (status) { 
//
//            whiteboard.addUserData(new UserData(senderId, getUser()));
//            whiteboardsManager.updateWhiteboard(whiteboard);
//           // String findOwner = whiteboard.getUserData(senderId).getUserName();
//
//            WhiteboardIdentifiers wi = ((WhiteboardIdentifiers) FacesAccessor.getManagedBean("whiteboardIdentifiers"));
//            wi.setSenderId(senderId);
//            wi.setWhiteboardId(whiteboard.getUuid());
//
////            if (getUser().equals(findOwner)) {
////                FacesContext.getCurrentInstance().addMessage(
////                        null,
////                        new FacesMessage(FacesMessage.SEVERITY_WARN,
////                                "User is already present in the session",
////                                ""));
////                return "pretty:wbJoin";
////            }
//
//            //return "pretty:wbWorkplace";
//        } else {
//            FacesContext.getCurrentInstance().addMessage(
//                    null,
//                    new FacesMessage(FacesMessage.SEVERITY_WARN,
//                            "The entered credentials do not exist in the database. Please register yourself or try again",
//                            "Please register yourself"));
//            return "pretty:wbJoin";
//        }
//    }
    
     /**
     * Joins the user to the existing whiteboard session
     * 
     * @return string outcome for navigation
     */
    public String joinSession() {

        String senderId = UUID.randomUUID().toString();
        //String checkuser =  whiteboard.getUserData(senderId).getUserName();
        //System.out.print(checkuser);
        
        Hash hash = new Hash();
        // encrypt password to store
        String passwordHash = hash.encryptThisString(getPwd());
        boolean status = LoginDAO.checkUname(getUser(), passwordHash);

        //        HttpSession session = SessionUtils.getSession();
        //        String owner = (String) session.getAttribute("creator");
        //        setUser(owner);
        if (status) {

            whiteboard.addUserData(new UserData(senderId, getUser()));
            whiteboardsManager.updateWhiteboard(whiteboard);

            WhiteboardIdentifiers wi = ((WhiteboardIdentifiers) FacesAccessor.getManagedBean("whiteboardIdentifiers"));
            wi.setSenderId(senderId);
            wi.setWhiteboardId(whiteboard.getUuid());
            System.out.println(senderId);
            System.out.println(whiteboard.getUuid());

            return "pretty:wbWorkplace";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    "joinForm",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "The entered credentials do not exist in the database. Please register yourself or try again",
                            "Please register yourself"));
            return "pretty:wbJoin";
        }
    }
    
    public String joinRegister(){
        return "pretty:wbJoinRegister";
    }
    
    //    *************************register user to the application, if the user already exists then error************************
    public String validateUserRegisterJoin() {

        Hash hash = new Hash();
        // hash password to store
        String passwordHash = hash.encryptThisString(getPwd());
        boolean status = LoginDAO.register(getUser(), passwordHash);
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
}
