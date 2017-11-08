package it.polimi.jasper.engine.stream;

import it.polimi.yasper.core.stream.StreamImpl;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by riccardo on 10/07/2017.
 */
@Setter
@Getter
public class RDFStream extends StreamImpl {

    protected String name;

    public RDFStream(String name, String stream_uri) {
        super(stream_uri);
        this.name = name;
    }

    @Override
    public String getURI() {
        return stream_uri;
    }
}
