package gr.uoa.di.aspect_proxy;

import java.lang.reflect.Method;

public interface AspectBuilder {
    AspectBuilder withTargets(Class<?>[] targets);
    AspectBuilder withBeforeAdviceFor(Runnable beforeAdvice, Method... methods);
    AspectBuilder withAfterAdviceFor(Runnable afterAdvice, Method... methods);
    AspectBuilder withAroundAdviceFor(Runnable aroundAdvice, Method... methods);
    Aspect build();
}
