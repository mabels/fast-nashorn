import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * This might work
 */
public class JavascriptEngineEval implements JavascriptEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(Console.class);

    private boolean initialized;

    private ScriptEngine engine;
//  private CompiledScript compiledScript;

//  private ThreadLocal<ScriptObjectMirror> mirrorHolder = new ThreadLocal<>();

    private BlockingQueue<Bindings> bindingsQueue = new LinkedBlockingDeque<Bindings>();


    public JavascriptEngineEval() {
        this.initialized = false;
    }

    public void initialize() {
        if (this.initialized) {
            return;
        }

//        engine = new jdk.nashorn.api.scripting.NashornScriptEngineFactory().getScriptEngine(
//                new String[]{
//                        // "-doe",
//                        // "--optimistic-types",
//                        // "--class-cache-size=5000"
//                }
//        );

    ScriptEngineManager scriptEngineManager = new ScriptEngineManager(null);
    engine = (NashornScriptEngine)scriptEngineManager.getEngineByName("nashorn");
//    engine.
        engine.getContext().setErrorWriter(new Print());
        engine.getContext().setWriter(new Print());
        this.initialized = true;
    }

    public String exec(Bindings binding, String method, Object... args) {
        if (!this.initialized) {
            throw new IllegalStateException("JavascriptEngine is not initialized");
        }
        try {
            LOGGER.error("method=", method, args);
//      Bindings my = (Bindings)binding.get(method);
            ScriptObjectMirror entry = (ScriptObjectMirror) (binding.get(method));
            Object ret = entry.call(null, args);
            return (String) ret;
            // LOGGER.error("method=", method, ((Bindings)binding.get("global")).get(method));
//      return ((Bindings)(((ScriptObjectMirror)(((Bindings)binding.get("global")).get(method))).call(null, args)));
//      return ((ScriptObjectMirror)binding.get(method)).call(null, args).toString();
        } catch (Exception e) {
            LOGGER.error("error", e);
            throw new RuntimeException("cannot render react on server", e);
        }
    }

    public Bindings takeBindings() {
        try {
            return bindingsQueue.take();
        } catch (Exception e) {
            LOGGER.error("takeBindings", e);
            return null;
        }
    }

    public void putBindings(Bindings p) {
        try {
            bindingsQueue.put(p);
        } catch (Exception e) {
            LOGGER.error("putBindings", e);
        }
    }

    public void createBindings(int count, String[] scripts) {
        try {
            LOGGER.info("createBindings:", count, scripts);
            for (int i = 0; i < count; ++i) {
                bindingsQueue.put(createBinding(scripts));
            }
        } catch (Exception e) {
            LOGGER.error("createBindings:", e);
        }
    }

    private Bindings createBinding(String[] scripts) {

        StringBuilder source = new StringBuilder();
        for (int i = 0; i < scripts.length; ++i) {
            LOGGER.info("Scriptloading:", scripts[i]);
            source.append(getScript(scripts[i]));
        }

        try {
            Bindings bindings = this.engine.createBindings();
            engine.put("console", new Console());
            engine.eval("function setTimeout(a) { a() }", bindings);
            engine.eval(source.toString(), bindings);
//      LOGGER.debug("JavascriptEngine.loadJavascriptLibrary took: " + (System.currentTimeMillis() - start) + "ms");
            return bindings;
        } catch (ScriptException e) {
            throw new RuntimeException("cannot eval library script", e);
        }
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
