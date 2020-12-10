package java.net.http;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import java.io.IOException;


@Weave(type = MatchType.BaseClass)
public abstract class HttpClient {

    @Trace()
    public <T> HttpResponse<T> send(HttpRequest req, HttpResponse.BodyHandler<T> responseHandler)
            throws IOException, InterruptedException {

            return Weaver.callOriginal();
    }
}



