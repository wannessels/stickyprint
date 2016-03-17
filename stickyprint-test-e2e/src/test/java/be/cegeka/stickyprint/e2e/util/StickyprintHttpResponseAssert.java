package be.cegeka.stickyprint.e2e.util;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class StickyprintHttpResponseAssert extends AbstractAssert<StickyprintHttpResponseAssert, StickyprintHttpResponse> {
    protected StickyprintHttpResponseAssert(StickyprintHttpResponse actual) {
        super(actual, StickyprintHttpResponseAssert.class);
    }

    public static StickyprintHttpResponseAssert assertThat(StickyprintHttpResponse actual) {
        return new StickyprintHttpResponseAssert(actual);
    }

    public StickyprintHttpResponseAssert hasHttpStatusCodeOk() {
        isNotNull();
        Assertions.assertThat(actual.isOkResponse()).withFailMessage("httpstatuscode is not OK").isTrue();
        return this;
    }
}
