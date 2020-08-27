/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.controller;

/**
 * This is a Managed Bean for storing Identifiers - White Board ID and Sender ID
 * @author Yeou Liu
 */
public class WhiteboardIdentifiers
{
    private String whiteboardId;
    private String senderId;
    
    //Getters and setters for Identifiers - SenderID and Whiteboard ID
    public String getWhiteboardId() {
        return whiteboardId;
    }

    public void setWhiteboardId(String whiteboardId) {
        this.whiteboardId = whiteboardId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
