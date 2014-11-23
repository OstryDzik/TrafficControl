package Model;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class Module {
    public enum ModuleType {GUI, SIM, LIGHTS};

    public Module(ModuleType type) {
        self.type = type;
    }

    private ModuleType type;
}
