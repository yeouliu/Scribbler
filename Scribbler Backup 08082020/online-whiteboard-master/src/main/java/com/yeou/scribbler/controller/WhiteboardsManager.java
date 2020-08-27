/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.controller;

import com.yeou.scribbler.model.Whiteboard;
import org.apache.commons.configuration.ConfigurationException;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a managed bean for managing the White boards
 * @author Yeou Liu
 */
public class WhiteboardsManager
{
    private Map<String, Whiteboard> whiteboards = new HashMap<String, Whiteboard>();
    public WhiteboardsManager() throws ConfigurationException {
    }

    public synchronized void addWhiteboard(Whiteboard whiteboard) {
        whiteboards.put(whiteboard.getUuid(), whiteboard);
    }

    public synchronized Whiteboard updateWhiteboard(Whiteboard whiteboard) {
        return whiteboards.put(whiteboard.getUuid(), whiteboard);
    }

    public synchronized void removeWhiteboard(Whiteboard whiteboard) {
        whiteboards.remove(whiteboard.getUuid());
    }

    public Whiteboard getWhiteboard(String uuid) {
        return whiteboards.get(uuid);
    }
}
