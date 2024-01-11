package gr.uoa.di.aspect_proxy;

import java.lang.reflect.Method;

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
}
