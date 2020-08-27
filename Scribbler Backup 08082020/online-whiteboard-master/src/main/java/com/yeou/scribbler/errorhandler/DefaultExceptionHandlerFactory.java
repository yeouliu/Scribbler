/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.errorhandler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Factory class to create JSF exception handler {@link DefaultExceptionHandler}.
 *
 * @author Yeou Liu
 */
public class DefaultExceptionHandlerFactory extends ExceptionHandlerFactory
{
    private ExceptionHandlerFactory parent;

    public DefaultExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    /**
     * Creates {@link DefaultExceptionHandler}.
     *
     * @return ExceptionHandler
     */
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler eh = parent.getExceptionHandler();
        eh = new DefaultExceptionHandler(eh);

        return eh;
    }
}
