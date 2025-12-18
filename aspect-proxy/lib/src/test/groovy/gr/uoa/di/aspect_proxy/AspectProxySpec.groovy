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

    def "02-Simple example"() {
        given:
        Factory factory = new Factory()
        int callCount = 0

        when:
        AspectWeaver weaver = factory.newAspectWeaver()
        Aspect aspect = factory
            .newAspectBuilder()
            .withTargets(List.class)
            .withBeforeAdviceFor( ()->callCount++, List.class.getMethods())
            .withAfterAdviceFor( ()->callCount++, List.class.getMethods())
            .build()
        List<String> list = new ArrayList<>()
        List<String> wovenList = weaver.weave(list) as List<String>
        wovenList.size()
        wovenList.add("FOO")

        then:
        callCount == 4
    }
}
