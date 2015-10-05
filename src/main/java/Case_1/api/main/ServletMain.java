package Case_1.api.main;

import Case_1.util.pref.PrefUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

/**
 * Created by alex on 10/5/15.
 */
public class ServletMain  implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ServletMain.class.getName());


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            PrefUtil.setPropertiesFileName("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PrefUtil.PropertyNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
