package fxrestclient.components;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RESTException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final int status;
    private final Map<String, List<String>> errorData;

    public RESTException(int status, Map<String, List<String>> errorData) {
        this.status = status;
        this.errorData = errorData;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, List<String>> getErrorData() {
        return errorData;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
