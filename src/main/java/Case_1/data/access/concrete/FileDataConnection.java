package Case_1.data.access.concrete;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.util.i18n.LangUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class FileDataConnection implements DataConnection<String, String> {
    private static final Logger LOGGER = LogManager
            .getLogger(FileDataConnection.class.getName());
    private String filePath;

    @Override
    public boolean open() throws DataConnectionException {
        return true;
    }

    @Override
    public String getConnection() {
        return filePath;
    }

    @Override
    public void setConnection(final String filePath) {
        this.filePath = filePath;
    }

    @Override
    public DataResult execute(final String query) throws
            DataConnectionException {
        if (filePath == null | "".equals(filePath)) {
            String message = LangUtil
                    .labelFor("error.dataConnection.connectionFailure") +
                    filePath;
            LOGGER.error(message);
            throw new DataConnectionException(message);
        }
        DataResult result = new DataResult();
        // add one row
        result.addNewRow(new LinkedHashMap<>());

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            int lineNum = 1;
            for (final String line : lines) {
                result.addToRow(0, "line " + lineNum, line);
                lineNum++;
            }
        } catch (IOException e) {
            String message = LangUtil
                    .labelFor("error.dataConnection.connectionFailure") +
                    filePath;
            LOGGER.error(message);
            e.printStackTrace();
            throw new DataConnectionException(message);
        }
        return result;
    }

    @Override
    public boolean executeUpdate(final String query) throws
            DataConnectionException {
        // nope, one way street over here
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean close() throws DataConnectionException {
        return true;
    }
}
