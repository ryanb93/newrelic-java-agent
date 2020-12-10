package com.nr.agent.instrumentation.httpclient;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;

public class InboundWrapper implements InboundHeaders {
    @Override
    public HeaderType getHeaderType() {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }
}
