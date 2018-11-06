package eu.isakels.rest.model;

import eu.isakels.rest.model.payment.PaymentFactory;
import eu.isakels.rest.model.payment.PaymentT1;
import eu.isakels.rest.model.payment.PaymentT2;
import eu.isakels.rest.model.payment.PaymentT3;
import eu.isakels.rest.util.TestUtil;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ModelTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void payment() throws Exception {
        {
            final var payment = PaymentFactory.forReq(TestUtil.paymentReqT1());
            Assert.assertTrue(payment instanceof PaymentT1);
        }
        {
            final var payment = PaymentFactory.forReq(TestUtil.paymentReqT2());
            Assert.assertTrue(payment instanceof PaymentT2);
        }
        {
            final var payment = PaymentFactory.forReq(TestUtil.paymentReqT3());
            Assert.assertTrue(payment instanceof PaymentT3);
        }

        // TODO: complete test
//        thrown.expect(IllegalArgumentException.class);
    }

}
