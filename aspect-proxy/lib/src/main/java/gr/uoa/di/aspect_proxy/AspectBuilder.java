package gr.uoa.di.aspect_proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

public interface AspectBuilder {
    AspectBuilder withTargets(Class<?>[] targets);
    AspectBuilder withBeforeAdviceFor(Runnable beforeAdvice, Method... methods);
    AspectBuilder withAfterAdviceFor(Runnable afterAdvice, Method... methods);
    AspectBuilder withAroundAdviceFor(Runnable aroundAdvice, Method... methods);
    Aspect build();
}

class AspectBuilderImpl implements AspectBuilder {

    private final Consumer<Aspect> aspectConsumer;
    private final AspectImpl aspect = new AspectImpl();

    public AspectBuilderImpl(Consumer<Aspect> aspectConsumer) {
        this.aspectConsumer = aspectConsumer;
    }


    public AspectBuilder withTargets(Class<?>[] targets) {
        aspect.targets.addAll(Arrays.asList(targets));
        return this;
    }
    
    public AspectBuilder withBeforeAdviceFor(Runnable beforeAdvice, Method... methods) {
        populateMap(aspect.beforeAdices, beforeAdvice, methods);
        return this;
    }

    public AspectBuilder withAfterAdviceFor(Runnable afterAdvice, Method... methods) {
        populateMap(aspect.afterAdvices, afterAdvice, methods);
        return this;
    }
    public AspectBuilder withAroundAdviceFor(Runnable aroundAdvice, Method... methods) {
        populateMap(aspect.aroundAdvices, aroundAdvice, methods);
        return this;
    }

    private static void populateMap(Map<Method, Runnable> map, Runnable r, Method... methods) {
        for (Method method: methods) {
            map.put(method, r);
        }
    }

    public Aspect build() {
        aspectConsumer.accept(aspect);
        return aspect;

    }
}
