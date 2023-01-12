import java.lang.reflect.Method;
/*
  A simple aspect-oriented mechanism that supports
  the basic AOP feature of applying before/after/around
  advice on selected methods of objects that implement
  specific interfaces.
  The mechanism employs runtime aspect weaving
  implemented through the dynamic proxy API.
 */
public interface Aspect {
    /*
     Returns the array of Interfaces
     that this aspect should be applied upon
    */
    Class<?>[] getTargets();
    /*
     Returns the Runnable, if any,
     that should be run as before advice for the given method
    */
    Runnable beforeAdviceFor(Method method);
    /*
     Returns the Runnable, if any,
     that should be run as after advice for the given method
    */
    Runnable afterAdviceFor(Method method);
    /*
     Returns the Runnable, if any,
     that should be run as before advice for the given method
    */
    Runnable aroundAdviceFor(Method method);

    /* The Aspect builder */
    interface Builder {
        Builder withTargets(Class<?>[] targets);
        Builder withBeforeAdviceFor(Runnable beforeAdvice, Method... methods);
        Builder withAfterAdviceFor(Runnable afterAdvice, Method... methods);
        Builder withAroundAdviceFor(Runnable aroundAdvice, Method... methods);
        Aspect build();
    }

    /* The Aspect weaver */
    interface Weaver {
        Object weave(Object target);
    }

    /* Helper Factory */
    interface Factory {
        Builder newBuilder();
        Weaver newWeaver();
    }
}
