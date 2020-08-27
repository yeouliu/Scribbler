/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.model.attribute;

/**
 * Enum for font weights "normal" and "bold".
 *
 * @author Yeou Liu
 */
public enum FontWeight
{
    Normal("normal"), Bold("bold");

    private String weight;

    FontWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }

    public static FontWeight getEnum(String weight) {
        for (FontWeight fw : FontWeight.values()) {
            if (fw.weight.equals(weight)) {
                return fw;
            }
        }

        return null;
    }
}
