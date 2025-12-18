package gr.uoa.di.aspect_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public interface AspectWeaver {
    Object weave(Object target);
}

class AspectWeaverImpl implements AspectWeaver {

    private final Supplier<List<Aspect>> aspectsSupplier;

    public AspectWeaverImpl(Supplier<List<Aspect>> aspectsSupplier) {
        this.aspectsSupplier = aspectsSupplier;
    }

    public Object weave(Object target) {
        List<Aspect> aspects = this.aspectsSupplier.get();
        List<Aspect> appliableAspects = aspects.stream().filter( aspect -> isAspectAppliable(aspect, target)).toList();

        if (appliableAspects.isEmpty()) {
            return target;
        }

        List<Class<?>> targetClasses = appliableAspects
            .stream()
            .flatMap(aspect -> Arrays.asList(aspect.getTargets()).stream())
            .toList();
        return Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            targetClasses.toArray(new Class[0]), 
            new AspectInvocationHandler(target, appliableAspects)
        );
    }

    private static boolean isAspectAppliable(Aspect aspect, Object target) {
        for (Class<?> c: aspect.getTargets()) {
            if (c.isAssignableFrom(target.getClass())) {
                return true;
            }
        }
        return false;
    }
}

class AspectInvocationHandler implements InvocationHandler {

    private final Object target;
    private final List<Aspect> aspects;

    public AspectInvocationHandler(Object target, List<Aspect> aspects) {
        this.target = target;
        this.aspects = aspects;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Runnable beforeAdvice = beforeAdviceOf(method);
        beforeAdvice.run();

        Object result = method.invoke(target, args);

        Runnable afterAdvice = afterAdviceOf(method);
        afterAdvice.run();

        return result;
    }

    private Runnable beforeAdviceOf(Method method) {
        List<Runnable> runnables = new ArrayList();
        for (Aspect a: aspects) {
            Runnable beforeAdvice = a.beforeAdviceFor(method);
            if (beforeAdvice != null) {
                runnables.add(beforeAdvice);
            }
        }
        return of(runnables);
    }

    private Runnable afterAdviceOf(Method method) {
        List<Runnable> runnables = new ArrayList();
        for (Aspect a: aspects) {
            Runnable afterAdvice = a.afterAdviceFor(method);
            if (afterAdvice != null) {
                runnables.add(afterAdvice);
            }
        }
        return of(runnables);
    } 

    private static Runnable of(List<Runnable> runnables) {
        // return new Runnable() {
        //     public void run() {
        //         for (Runnable r: runnables) {
        //             r.run();
        //         }
        //     }
        // };
        return () -> {
            for (Runnable r: runnables) {
                r.run();
            }
        };
    }
    
}
