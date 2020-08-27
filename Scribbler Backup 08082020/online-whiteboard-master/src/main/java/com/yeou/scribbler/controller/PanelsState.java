/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.controller;

import javax.faces.event.ActionEvent;

/**
 * Managed Bean for storing panel states pin/unpin
 *
 * @author Yeou Liu
 */
public class PanelsState
{
    private boolean pinned = true;

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void tooglePinUnpin(ActionEvent e) {
        this.pinned = !pinned;
    }
}
