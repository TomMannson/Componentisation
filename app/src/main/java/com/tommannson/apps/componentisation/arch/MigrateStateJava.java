package com.tommannson.apps.componentisation.arch;

import com.tommannson.apps.componentisation.arch.component.UIComponent;

public class MigrateStateJava {

    public static void setState(UIComponent oldComponent, UIComponent newComponent) {
        oldComponent.setLocalState(newComponent.getLocalState());
    }
}
