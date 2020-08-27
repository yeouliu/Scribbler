/*
* @author  Yeou Liu
 */
package com.yeou.scribbler.controller;

import com.yeou.scribbler.errorhandler.DefaultExceptionHandler;
import com.yeou.scribbler.json.JsonConverter;
import com.yeou.scribbler.model.Whiteboard;
import com.yeou.scribbler.model.attribute.StrokeStyle;
import com.yeou.scribbler.model.base.AbstractElement;
import com.yeou.scribbler.model.transfer.RestoredWhiteboard;
import com.yeou.scribbler.utils.FacesAccessor;
import com.yeou.scribbler.utils.WhiteboardUtils;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Managed bean for White Board
 *
 * @author Yeou Liu
 */
public class DisplayWhiteboard implements Serializable {

    private static final long serialVersionUID = 20110501L;

    private static final Logger LOG = Logger.getLogger(DisplayWhiteboard.class.getName());

    private Whiteboard whiteboard;
    private WhiteboardsManager whiteboardsManager;
    private String senderId;
    private List<SelectItem> fontFamilies;
    private List<SelectItem> lineStyles;

    @PostConstruct
    
    /**
     * Initialize this bean. This method is called automatically by JSF
     * facility.
     */
    protected void initialize() {
        String whiteboardId = FacesAccessor.getRequestParameter("whiteboardId");

        if (whiteboardId != null) {
            whiteboard = whiteboardsManager.getWhiteboard(whiteboardId);
        } else {
            DefaultExceptionHandler.doRedirect(FacesContext.getCurrentInstance(), "/views/error.jsf?statusCode=601");
            return;
        }

        if (whiteboard == null) {
            DefaultExceptionHandler.doRedirect(FacesContext.getCurrentInstance(), "/views/error.jsf?statusCode=602");
            return;
        }

        senderId = FacesAccessor.getRequestParameter("senderId");

        if (senderId == null) {
            DefaultExceptionHandler.doRedirect(FacesContext.getCurrentInstance(), "/views/error.jsf?statusCode=603");
        }
    }

    public Whiteboard getWhiteboard() {
        return whiteboard;
    }

    public WhiteboardsManager getWhiteboardsManager() {
        return whiteboardsManager;
    }

    public void setWhiteboardsManager(WhiteboardsManager whiteboardsManager) {
        this.whiteboardsManager = whiteboardsManager;
    }

    public String getUser() {
        if (whiteboard == null) {
            return "";
        }

        return whiteboard.getUserData(senderId).getUserName();
    }

    public String getPubSubTransport() {
        if (whiteboard == null) {
            return "";
        }

        return whiteboard.getPubSubTransport();
    }

    public String getTitle() {
        if (whiteboard == null) {
            return "";
        }

        return whiteboard.getTitle();
    }

    public String getCreator() {
        if (whiteboard == null) {
            return "";
        }

        return whiteboard.getCreator();
    }

    public String getCreationDate() {
        if (whiteboard == null) {
            return "";
        }

        return WhiteboardUtils.formatDate(whiteboard.getCreationDate(), true);
    }

    public int getWidth() {
        if (whiteboard == null) {
            return 0;
        }

        return whiteboard.getWidth();
    }

    public int getHeight() {
        if (whiteboard == null) {
            return 0;
        }

        return whiteboard.getHeight();
    }

    public int getUsersCount() {
        if (whiteboard == null) {
            return 0;
        }

        return whiteboard.getUserData().size();
    }

    /**
     * Generates invitation link.
     *
     * @return String invitation link
     */
    public String getInvitationLink() {
        if (whiteboard == null) {
            return "";
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String scheme = ec.getRequestScheme();
        int port = ec.getRequestServerPort();

        String serverPort;
        if (("http".equalsIgnoreCase(scheme) && port != 80) || ("https".equalsIgnoreCase(scheme) && port != 443)) {
            serverPort = ":" + port;
        } else {
            serverPort = "";
        }

        return ec.encodeResourceURL(scheme + "://" + ec.getRequestServerName() + serverPort + ec.getRequestContextPath() + "/whiteboard/join/" + whiteboard.getUuid() + "/");
    }

    public String getMonitoringMessage() {
        if (whiteboard == null) {
            return "";
        }

        if (whiteboard.getUserData().size() < 2) {
            return "This Scribbler session has been created by :: "+ getCreator() +"!";
        } else {
            return "The Scribbler session was just refreshed or joined by ::"+ getUser();
        }
    }

    /**
     * Gets white board elements in JSON format.
     *
     * @return String elements as JSON
     */
    public String getElementsAsJson() {
        if (whiteboard == null || whiteboard.getCount() < 1) {
            // empty whiteboard
            return "{}";
        }

        RestoredWhiteboard reBoard = new RestoredWhiteboard();
        for (AbstractElement abs : whiteboard.getElements().values()) {
            reBoard.addElement(abs);
        }

        if (whiteboard.getCount() == 1) {
            reBoard.setMessage("A whiteboard element has been restored");
        } else {
            reBoard.setMessage(whiteboard.getCount() + " whiteboard elements have been restored");
        }

        return JsonConverter.getGson().toJson(reBoard);
    }

    /**
     * Gets URL for bidirectional communication.
     *
     * @return String URL for bidirectional communication
     */
    public String getPubSubUrl() {
        if (whiteboard == null) {
            return "";
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String scheme = ec.getRequestScheme();
        int port = ec.getRequestServerPort();

        String serverPort;
        if (("http".equalsIgnoreCase(scheme) && port != 80) || ("https".equalsIgnoreCase(scheme) && port != 443)) {
            serverPort = ":" + port;
        } else {
            serverPort = "";
        }

        return ec.encodeResourceURL(scheme + "://" + ec.getRequestServerName() + serverPort + ec.getRequestContextPath() + "/pubsub/" + whiteboard.getUuid() + "/" + senderId + ".topic");
    }

    /**
     * Gets available font families.
     *
     * @return List font families
     */
    public List getFontFamilies() {
        if (fontFamilies == null) {
            fontFamilies = new ArrayList<SelectItem>();
            fontFamilies.add(new SelectItem("Arial", "Arial"));
            fontFamilies.add(new SelectItem("Arial Black", "Arial Black"));
            fontFamilies.add(new SelectItem("Book Antiqua", "Book Antiqua"));
            fontFamilies.add(new SelectItem("Century Gothic", "Century Gothic"));
            fontFamilies.add(new SelectItem("Comic Sans MS", "Comic Sans MS"));
            fontFamilies.add(new SelectItem("Courier", "Courier"));
            fontFamilies.add(new SelectItem("Courier New", "Courier New"));
            fontFamilies.add(new SelectItem("Garamond", "Garamond"));
            fontFamilies.add(new SelectItem("Geneva", "Geneva"));
            fontFamilies.add(new SelectItem("Georgia", "Georgia"));
            fontFamilies.add(new SelectItem("Helvetica", "Helvetica"));
            fontFamilies.add(new SelectItem("Impact", "Impact"));
            fontFamilies.add(new SelectItem("Lucida Console", "Lucida Console"));
            fontFamilies.add(new SelectItem("Lucida Sans Unicode", "Lucida Sans Unicode"));
            fontFamilies.add(new SelectItem("Palatino Linotype", "Palatino Linotype"));
            fontFamilies.add(new SelectItem("Sans-Serif", "Sans-Serif"));
            fontFamilies.add(new SelectItem("Tahoma", "Tahoma"));
            fontFamilies.add(new SelectItem("Times New Roman", "Times New Roman"));
            fontFamilies.add(new SelectItem("Trebuchet MS", "Trebuchet MS"));
            fontFamilies.add(new SelectItem("Verdana", "Verdana"));
        }

        return fontFamilies;
    }

    /**
     * Gets available line styles.
     *
     * @return List line styles
     */
    public List getLineStyles() {
        if (lineStyles == null) {
            lineStyles = new ArrayList<SelectItem>();
            lineStyles.add(new SelectItem(StrokeStyle.No.name(), StrokeStyle.No.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.Dash.name(), StrokeStyle.Dash.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.Dot.name(), StrokeStyle.Dot.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.DashDot.name(), StrokeStyle.DashDot.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.DashDotDot.name(), StrokeStyle.DashDotDot.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.DotBlank.name(), StrokeStyle.DotBlank.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.DashBlank.name(), StrokeStyle.DashBlank.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.DashDash.name(), StrokeStyle.DashDash.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.DashBlankDot.name(), StrokeStyle.DashBlankDot.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.DashDashDot.name(), StrokeStyle.DashDashDot.getStyle()));
            lineStyles.add(new SelectItem(StrokeStyle.DashDashDotDot.name(), StrokeStyle.DashDashDotDot.getStyle()));
        }

        return lineStyles;
    }
    
    public void saveBoard(){
        System.out.println("com.yeou.scribbler.controller.DisplayWhiteboard.saveBoard()");
    }
}
