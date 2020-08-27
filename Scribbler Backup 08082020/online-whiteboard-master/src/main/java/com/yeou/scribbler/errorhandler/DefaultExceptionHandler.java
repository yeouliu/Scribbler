/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.errorhandler;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;

/**
 * JSF exception handler.
 *
 * @author Yeou Liu
 */
public class DefaultExceptionHandler extends ExceptionHandlerWrapper
{
    //public static final String MESSAGE_DETAIL_KEY = "com.yeou.scribbler.messageDetail";
    public static final String MESSAGE_KEY = "com.yeou.scribbler.messageDetail";
    private ExceptionHandler wrapped;

    public DefaultExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    /**
     * Handles all unchecked and unexpected exceptions.
     *
     * @throws FacesException
     */
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> itr = getUnhandledExceptionQueuedEvents().iterator(); itr.hasNext(); ) {
            ExceptionQueuedEvent event = itr.next();
            
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            String redirectTo = null;
            FacesContext fcon = FacesContext.getCurrentInstance();
            Throwable t = context.getException();

            try {
                if (fcon == null) {
                    return;
                } else if (t instanceof ViewExpiredException) {
                    HttpSession session = (HttpSession) fcon.getExternalContext().getSession(false);
                    if (session != null) {
                        //Invalidate session
                        session.invalidate();
                    }

                    redirectTo = "/views/error.jsf?statusCode=600";
                    if (!fcon.getExternalContext().isResponseCommitted()) {
                        fcon.getExternalContext().getSessionMap().put(DefaultExceptionHandler.MESSAGE_KEY, ((ViewExpiredException) t).getViewId());
                    }
                } else {
                    redirectTo = "/views/error.jsf?statusCode=699";
                    if (!fcon.getExternalContext().isResponseCommitted()) {
                        fcon.getExternalContext().getSessionMap().put(DefaultExceptionHandler.MESSAGE_KEY, t.getLocalizedMessage());
                    }
                }
            } finally {
                itr.remove();
            }

            doRedirect(fcon, redirectTo);

            break;
        }
    }

    public static void doRedirect(FacesContext fcon, String redirect) throws FacesException {
        ExternalContext econtext = fcon.getExternalContext();

        try {
            if (econtext.isResponseCommitted()) {
                // redirect is not possible
                return;
            }

            econtext.redirect(econtext.getRequestContextPath() + (redirect != null ? redirect : ""));
        } catch (IOException e) {
            throw new FacesException(e);
        }
    }
}
