/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.controller;

import com.yeou.scribbler.errorhandler.DefaultExceptionHandler;
import com.yeou.scribbler.utils.MessageUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 * This is a managed bean for error messages
 * 
 * @author Yeou Liu
 */
public class ErrorMessagePreparer
{
    public void prepareErrorMessage(ComponentSystemEvent event) {
        FacesContext fcontext = FacesContext.getCurrentInstance();

        String message;
        String detail = null;
        String messageKey = fcontext.getExternalContext().getRequestParameterMap().get("statusCode");

        if ("403".equals(messageKey)) {
            message = "403 Forbidden: Server not responding to the request.";
        } else if ("404".equals(messageKey)) {
            message = "404 Not Found: Resource not found.";
        } else if ("500".equals(messageKey)) {
            message = "500 Internal Server Error: Internal Server Error!";
        } else if ("600".equals(messageKey)) {
            String view = (String) fcontext.getExternalContext().getSessionMap().get(DefaultExceptionHandler.MESSAGE_KEY);
            if (view != null) {
                message = "View '" + detail + "' is expired (Session timed out). Please try again.";
            } else {
                message = "Session Timed out. Please try again.";
            }
        } else if ("601".equals(messageKey)) {
            message = "Whiteboard not found in the request parameter!";
        } else if ("602".equals(messageKey)) {
            message = "Whiteboard object not found (Invalid UUID)! Please try again";
        } else if ("603".equals(messageKey)) {
            message = "Sender Id not found in the request parameter!";
        } else if ("699".equals(messageKey)) {
            message = "Unexpected error occurred. Please try again";
            detail = (String) fcontext.getExternalContext().getSessionMap().get(DefaultExceptionHandler.MESSAGE_KEY);
        } else {
            message = "Unexpected error occurred. Please try again.";
        }

        if (detail == null) {
            MessageUtils.addFacesMessage(fcontext, null, FacesMessage.SEVERITY_ERROR, message); //Faces Messages
        } else {
            MessageUtils.addFacesMessage(fcontext, null, FacesMessage.SEVERITY_ERROR, message, detail);
        }

        fcontext.getExternalContext().getSessionMap().remove(DefaultExceptionHandler.MESSAGE_KEY);
    }
}
