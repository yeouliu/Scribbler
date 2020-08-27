/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.model.base;

/**
 * Base class of all rotatable elements.
 *
 * @author Yeou Liu
 */
public abstract class Rotatable extends AbstractElement
{
    private int rotationDegree;

    public int getRotationDegree() {
        return rotationDegree;
    }

    public void setRotationDegree(int rotationDegree) {
        this.rotationDegree = rotationDegree;
    }
}
