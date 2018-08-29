import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Console {
    private static final Logger LOGGER = LoggerFactory.getLogger(Console.class);

    public Console() {
      super();
    }

    public void debug(String statement, Object... args) {
      LOGGER.debug(statement, args);
    }

    public void debug(String statement, Object error) {
      LOGGER.debug(statement, error);
    }

    public void log(String statement) {
      LOGGER.info(statement);
    }

    public void log(String statement, Object error) {
      LOGGER.info(statement, error);
    }

    public void info(String statement) {
      LOGGER.info(statement);
    }

    public void info(String statement, Object error) {
      LOGGER.info(statement, error);
    }

    public void error(String statement) {
      LOGGER.error(statement);
    }

    public void error(String statement, Object error) {
      LOGGER.error(statement, error);
    }

    public void warn(String statement) {
      LOGGER.warn(statement);
    }

    public void warn(String statement, Object error) {
      LOGGER.warn(statement, error);
    }

}
