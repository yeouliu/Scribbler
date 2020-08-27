/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.model.transfer;

/**
 * Container keeping only line path.
 *
 * @author Yeou Liu
 */
public class TruncatedLine extends TruncatedElement
{
    private String path;

    public TruncatedLine(String uuid, String className, String path) {
        super(uuid, className);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
