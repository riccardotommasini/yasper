package it.polimi.jasper;

import it.polimi.jasper.engine.JenaRSPQLEngineImpl;
import it.polimi.jasper.engine.query.formatter.ResponseFormatterFactory;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
<<<<<<< HEAD
import it.polimi.yasper.core.stream.Stream;
=======
import it.polimi.yasper.core.utils.EngineConfiguration;
>>>>>>> d92d421... I noticed a mismatch between how the naming of the attribute is handle in the query and in the system.
import it.polimi.yasper.core.utils.QueryConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.jena.riot.system.IRIResolver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Riccardo on 03/08/16.
 */
public class TestConfig {

    public static void main(String[] args) throws InterruptedException, IOException, ConfigurationException {

        URL resource = TestConfig.class.getResource("/jasper.properties");
        QueryConfiguration config = new QueryConfiguration(resource.getPath());
        EngineConfiguration ec = new EngineConfiguration(resource.getPath());

<<<<<<< HEAD
        GraphStream painter = new GraphStream("Painter", "http://streamreasoning.org/iminds/massif/stream1", 1);
        GraphStream writer = new GraphStream("Writer", "stream2", 5);
=======
        JenaRSPQLEngineImpl sr = new JenaRSPQLEngineImpl(0, ec);

        IRIResolver resolver = sr.getResolver();

        GraphStream painter = new GraphStream("Painter", resolver.resolveToString("streams/stream1"), 1);
        GraphStream writer = new GraphStream("Writer", resolver.resolveToString("streams/stream2"), 5);
>>>>>>> d92d421... I noticed a mismatch between how the naming of the attribute is handle in the query and in the system.

        painter.setRSPEngine(sr);
        writer.setRSPEngine(sr);

        Stream painter_registered = sr.register(painter);
        Stream writer_registered = sr.register(writer);

        painter.setRegistered_stream_uri(painter_registered.getURI());
        writer.setRegistered_stream_uri(writer_registered.getURI());

        String query = getQuery();
        ContinuousQuery q = sr.parseQuery(query);
        ContinuousQueryExecution ceq = sr.register(q, config);
        ContinuousQuery cq = ceq.getContinuousQuery();

        sr.register(cq, ResponseFormatterFactory.getGenericResponseSysOutFormatter(true)); // attaches a new *RSP-QL query to the SDS

        sr.startProcessing();

        //In real application we do not have to start the stream.
        (new Thread(painter)).start();
        (new Thread(writer)).start();


        sr.unregister(cq);
        sr.unregister(painter);
        sr.unregister(writer);

        System.out.println("Unregistered");
    }

    public static String getQuery() throws IOException {
        File file = new File("/Users/riccardo/_Projects/RSP/yasper/src/test/resources/q52.rspql");
        return FileUtils.readFileToString(file);
    }
}
