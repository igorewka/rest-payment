package eu.isakels.rest;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

// To hide exception stacktrace in the default generated error json
@Component
public class CusomErrorAttrs extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest, final boolean includeStackTrace) {
        final var errAttrs = super.getErrorAttributes(webRequest, includeStackTrace);
        final var msg = (String) errAttrs.get("message");
        if (msg.contains("exception")) errAttrs.put("message", "error");

        return errAttrs;
    }
}
