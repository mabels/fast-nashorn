import javax.script.Bindings;

public interface JavascriptEngine {
  String exec(Bindings bind, String method, Object... args);
  void initialize();
  void createBindings(int count, String[] scripts);
  Bindings takeBindings();
  void putBindings(Bindings took);
//  Bindings createBinding(String[] scripts);
}
