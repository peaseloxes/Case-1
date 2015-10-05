package Case_1.data.access.concrete;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.util.i18n.LangUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alex on 10/5/15.
 */
public class InputStreamDataConnection implements DataConnection<InputStream, String> {
    private static final Logger LOGGER = LogManager.getLogger(InputStreamDataConnection.class.getName());
    private InputStream stream;

    @Override
    public boolean open() throws DataConnectionException {
        return true;
    }

    @Override
    public InputStream getConnection() {
        return stream;
    }

    @Override
    public void setConnection(final InputStream connection) {
        this.stream = connection;
    }

    @Override
    public DataResult execute(final String query) throws DataConnectionException {
        DataResult result = new DataResult();
        result.addNewRow(new LinkedHashMap<>());

        List<String> lines = new BufferedReader(
                new InputStreamReader(stream))
                .lines()
                .collect(Collectors.toList());
        int count = 0;
        for (final String line : lines) {
            result.getRow(0).put("line_" + count, line);
            count++;
        }
        return result;
    }

    @Override
    public boolean executeUpdate(final String query) throws DataConnectionException {
        // one way street
        return false;
    }

    @Override
    public boolean close() throws DataConnectionException {
        try {
            stream.close();
            return true;
        } catch (IOException e) {
            stream = null;
            String message = LangUtil.labelFor("error.dataConnection.connectionClosedFailure");
            LOGGER.error(message);
            throw new DataConnectionException(message);
        }
    }
}
