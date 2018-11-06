package eu.isakels.rest.model;

import eu.isakels.rest.model.payment.PaymentT1;
import eu.isakels.rest.model.payment.Types;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelTest {

    private static final Logger logger = LoggerFactory.getLogger(MarshallingTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void payment() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        final var payment = new PaymentT1(null, Types.Currency.USD, "DBTRIBAN", "CRDTRIBAN", "type1");


    }

}
