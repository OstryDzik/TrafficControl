package Model;

import java.io.Serializable;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class Module implements Serializable {
    public static enum ModuleType {GUI, SIM, LIGHTS};

    public Module(ModuleType type) {
        this.type = type;
    }

    public ModuleType getType() {
        return type;
    }

    private ModuleType type;
}
