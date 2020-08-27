/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.model.transfer;

import com.yeou.scribbler.model.base.AbstractElement;

/**
 * Base container for all truncated informations.
 *
 * @author Yeou Liu
 */
public class TruncatedElement extends AbstractElement
{
    private String className;

    public TruncatedElement(String uuid, String className) {
        super(uuid);
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
