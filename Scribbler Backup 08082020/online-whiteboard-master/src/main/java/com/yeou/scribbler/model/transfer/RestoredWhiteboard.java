/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.model.transfer;

import com.yeou.scribbler.model.base.AbstractElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for current whiteboard elements passing to new joint user.
 *
 * @author Yeou Liu
 */
public class RestoredWhiteboard
{
    private List<AbstractElement> elements = new ArrayList<AbstractElement>();
    private String message;

    public List<AbstractElement> getElements() {
        return elements;
    }

    public void setElements(List<AbstractElement> elements) {
        this.elements = elements;
    }

    public void addElement(AbstractElement element) {
        this.elements.add(element);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
