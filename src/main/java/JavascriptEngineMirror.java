import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import javax.script.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * This might work
 *
 */
public class JavascriptEngineMirror implements JavascriptEngine {
  private boolean initialized;

  private ScriptEngine engine;
  private CompiledScript compiledScript;

  private static final Logger LOGGER = LoggerFactory.getLogger(JavascriptEngineMirror.class);

  private ThreadLocal<ScriptObjectMirror> mirrorHolder = new ThreadLocal<>();

  public JavascriptEngineMirror() {
    this.initialized = false;
  }

  public void initialize() {
    if(this.initialized) {
      return;
    }

    ScriptEngineManager scriptEngineManager = new ScriptEngineManager(null);
    engine = scriptEngineManager.getEngineByName("nashorn");

    engine.getContext().setErrorWriter(new Print());
    engine.getContext().setWriter(new Print());

    engine.put("console", new Console());

    this.initialized = true;
  }

  @Override
  public void createBindings(int count, String[] scripts) {
    
  }

  @Override
  public Bindings takeBindings() {
    return null;
  }

  @Override
  public void putBindings(Bindings took) {

  }

  public String exec(Bindings bind, String method, Object... args) {
//    long startTime = System.currentTimeMillis();

    if(!this.initialized) {
      throw new IllegalStateException("JavascriptEngine is not initialized");
    }

    try {
      ScriptObjectMirror mirror = mirrorHolder.get();
      if (mirror == null) {
        Bindings bindings = compiledScript.getEngine().createBindings();
        compiledScript.eval(bindings);
        mirror = (ScriptObjectMirror) bindings.get(method);
        mirrorHolder.set(mirror);
      }
//
      Object value = mirror.call(null, args );

//      LOGGER.info("JavascriptEngine.render took: " + (System.currentTimeMillis() - startTime) + "ms");
//      return value.toString();
      return null;
    } catch (Exception e) {
      LOGGER.error("error", e);
      throw new RuntimeException("cannot render react on server", e);
    }
  }

  public Bindings createBinding(String[] scripts) {
    long start = System.currentTimeMillis();

    String source = "";
    for (int i=0; i < scripts.length; ++i) {
      source += getScript(scripts[i]);
    }

    try {
      compiledScript = ((Compilable) engine).compile(source);
    } catch (ScriptException e) {
      throw new RuntimeException("cannot eval library script", e);
    }

    LOGGER.debug("JavascriptEngine.loadJavascriptLibrary took: " + (System.currentTimeMillis() - start) + "ms");
    return null;
  }

  private String getScript(String name) {
    URL scriptLocation = this.getClass().getClassLoader().getResource(name);
    if (scriptLocation == null) {
      throw new RuntimeException("script '" + name + "' not found.");
    }

    try {
      InputStreamReader reader = new InputStreamReader(scriptLocation.openStream(), "UTF-8");
      return IOUtils.toString(reader);
    } catch (IOException e) {
      throw new RuntimeException("cannot red script:" + name, e);
    }

  }
}
