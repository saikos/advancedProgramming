package gr.uoa.di.aspect_proxy

import spock.lang.Specification
import spock.util.concurrent.BlockingVariable
import spock.util.concurrent.BlockingVariables

import java.util.function.Consumer

class AspectProxySpec extends Specification {

    def "01-Basic usage of the machinery"() {
        given:
        Factory factory = new Factory()

        when:
        Aspect aspect = factory.newAspectBuilder().build()

        then:
        noExceptionThrown()
    }
}
