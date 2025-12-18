package gr.uoa.di.aspect_proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

class AspectImpl implements Aspect {

    final List<Class<?>> targets = new ArrayList();
    final Map<Method, Runnable> beforeAdices = new HashMap<>();
    final Map<Method, Runnable> afterAdvices = new HashMap<>();
    final Map<Method, Runnable> aroundAdvices = new HashMap<>();

    public AspectImpl() {
        
    }
        
    public Class<?>[] getTargets() {
        return targets.toArray(new Class[0]);
    }

    public Runnable beforeAdviceFor(Method method) {
        return beforeAdices.get(method);
    }

    public Runnable afterAdviceFor(Method method) {
        return afterAdvices.get(method);
    }

    public Runnable aroundAdviceFor(Method method) {
        return aroundAdvices.get(method);
    }
}
