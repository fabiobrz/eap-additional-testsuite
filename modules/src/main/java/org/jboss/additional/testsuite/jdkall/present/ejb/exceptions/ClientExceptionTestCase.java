package org.jboss.additional.testsuite.jdkall.present.ejb.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;


import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.transaction.Synchronization;
import javax.transaction.TransactionSynchronizationRegistry;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * @author bmaxwell
 *
 */

@RunWith(Arquillian.class)
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/ejb/src/main/java", "modules/testcases/jdkAll/Eap72x/ejb/src/main/java", "modules/testcases/jdkAll/Eap72x-Proposed/ejb/main/java","modules/testcases/jdkAll/Eap7/ejb/src/main/java"})
public class ClientExceptionTestCase {

    @EJB
    Hello bean;

    private static final String ARCHIVE_NAME = "ClientExceptionTestCase";

    @Deployment
    public static Archive<?> deploy() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, ARCHIVE_NAME + ".jar");
        jar.addClasses(ClientExceptionTestCase.class, Hello.class, HelloBean.class, MyApplicationExceptionNoRollbackRuntimeException.class);
        return jar;
    }

    @Test
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void checkException() {
	try {
	    bean.hello();
	} catch(Exception ex) {
	    if(ex.getCause() instanceof MyApplicationExceptionNoRollbackRuntimeException)
                fail("Exception should be different...");
	} 
    }
}
