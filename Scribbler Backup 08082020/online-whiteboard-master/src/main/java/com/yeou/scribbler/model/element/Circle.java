/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.model.element;

import com.yeou.scribbler.model.base.Positionable;

import java.io.Serializable;

/**
 * Model class for circle element.
 *
 * @author Yeou Liu
 */
public class Circle extends Positionable implements Serializable
{
    private static final long serialVersionUID = 20110506L;

    private int radius;
    private String backgroundColor;
    private String borderColor;
    private int borderWidth;
    private String borderStyle;
    private double backgroundOpacity;
    private double borderOpacity;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public String getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(String borderStyle) {
        this.borderStyle = borderStyle;
    }

    public double getBackgroundOpacity() {
        return backgroundOpacity;
    }

    public void setBackgroundOpacity(double backgroundOpacity) {
        this.backgroundOpacity = backgroundOpacity;
    }

    public double getBorderOpacity() {
        return borderOpacity;
    }

    public void setBorderOpacity(double borderOpacity) {
        this.borderOpacity = borderOpacity;
    }
}
