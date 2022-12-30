package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.gui.actions.InventoryGUIAction;

public class InventoryGUIActionHandler<A extends InventoryGUIAction> {

    private final Class<A> actionType;

    public InventoryGUIActionHandler(Class<A> actionToHandle) {
        this.actionType = actionToHandle;
    }

    public static <T extends InventoryGUIAction> InventoryGUIActionHandler<T> of(Class<T> type) {
        return new InventoryGUIActionHandler<T>(type);
    }

    public Class<A> getActionType() {
        return actionType;
    }

    public void onAction(A action) {}
}
