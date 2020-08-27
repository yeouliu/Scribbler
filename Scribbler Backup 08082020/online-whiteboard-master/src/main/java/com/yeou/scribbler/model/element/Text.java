/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.model.element;

import com.yeou.scribbler.model.base.Positionable;

import java.io.Serializable;

/**
 * Model class for text element.
 *
 * @author Yeou Liu
 */
public class Text extends Positionable implements Serializable
{
    private static final long serialVersionUID = 20110506L;

    private String text;
    private String fontFamily;
    private int fontSize;
    private String fontWeight;
    private String fontStyle;
    private String color;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
