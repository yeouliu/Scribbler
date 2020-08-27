/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.model.transfer;

/**
 * Enum for client actions.
 *
 * @author Yeou Liu
 */
public enum ClientAction
{
    Join("join"), Create("create"), Update("update"), Remove("remove"), Clone("clone"), Move("move"), BringToFront("toFront"), BringToBack("toBack"), Clear("clear"), Resize("resize");

    private String action;

    ClientAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static ClientAction getEnum(String action) {
        for (ClientAction ca : ClientAction.values()) {
            if (ca.action.equals(action)) {
                return ca;
            }
        }

        return null;
    }
}
