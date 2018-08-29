import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

public class Print extends Writer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Console.class);
    
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
      LOGGER.error(new String(cbuf, off, len));
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
  }

