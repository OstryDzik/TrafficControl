package Model;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class Module {
    public static enum ModuleType {GUI, SIM, LIGHTS};

    public Module(ModuleType type) {
        this.type = type;
    }

    private ModuleType type;
}
