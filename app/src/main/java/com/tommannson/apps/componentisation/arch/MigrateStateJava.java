package com.tommannson.apps.componentisation.arch;

import com.tommannson.apps.componentisation.arch.component.UIComponent;

public class MigrateStateJava {

    public static void setState(UIComponent componentOne, UIComponent componentTwo){
        componentOne.setLocalState$app_debug(componentTwo.getLocalState$app_debug());
    }
}
