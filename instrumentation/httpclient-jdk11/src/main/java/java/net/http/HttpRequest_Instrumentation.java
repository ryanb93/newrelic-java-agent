package java.net.http;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.agent.instrumentation.httpclient.OutboundWrapper;
import com.nr.agent.instrumentation.httpclient.Util;


@Weave(originalName = "java.net.http.HttpRequest", type = MatchType.Interface)
public abstract class HttpRequest_Instrumentation {

    @Weave(originalName = "java.net.http.HttpRequest$Builder", type = MatchType.Interface)
    public static class HttpRequestBuilder_Instrumentation {
        public HttpRequest build() {
            //this  = the builder.  Take it and add NR header to it.
            Object thisTemp = this;
            if (thisTemp instanceof HttpRequest.Builder) {
                //java.net.http has no issues calling NewRelic even though NewRelic is not a module
                System.out.println(NewRelic.getAgent().getTracedMethod());
                //java.net.http will throw an exception here because a module code is trying to access non-module code
                Util.stop();
                NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(new OutboundWrapper((HttpRequest.Builder) thisTemp));
            }
            return Weaver.callOriginal();
        }
    }
}
