import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

enum ActionStatus {
    READY,
    RUNNING,
    FINISHED,
    FAILED
}

interface Action {
    ActionStatus getStatus();
    void execute();
}

abstract class AbstractAction implements Action {

    protected ActionStatus status;

    AbstractAction() {
        this.status = ActionStatus.READY;
    }

    @Override
    public ActionStatus getStatus() {
        return status;
    }

    @Override
    public void execute() {
        status = ActionStatus.RUNNING;
        try {
            doExecute();
            status = ActionStatus.FINISHED;
        }
        catch(Throwable t) {
            status = ActionStatus.FAILED;
        }
    }

    protected abstract void doExecute();
}

class SleepAction extends AbstractAction {

    long millis;

    SleepAction(long millis) {
        this.millis = millis;
    }

    @Override
    protected void doExecute() {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}

class LoggingInvocationHandler implements InvocationHandler {

    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Invoking " + method + " on object " + target);
        Object returnValue = method.invoke(target, args);
        System.out.println("Result is " + returnValue);
        return returnValue;
    }
}

class MagicActionHandler implements InvocationHandler {

    private ActionStatus status;
    private final Runnable runnable;

    public MagicActionHandler(Runnable runnable) {
        this.status = ActionStatus.READY;
        this.runnable = runnable;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("getStatus".equals(methodName)) {
            return status;
        }
        if ("execute".equals(methodName)) {
            status = ActionStatus.RUNNING;
            try {
                runnable.run();
                status = ActionStatus.FINISHED;
                return null;
            }
            catch(Throwable t) {
                status = ActionStatus.FAILED;
                throw t;
            }
        }
        if ("toString".equals(methodName)) {
            return "MagicAction";
        }
        throw new NoSuchMethodException(methodName);
    }
}

public class DynamicProxyExample {

    public static void main(String[] args) {
        Action action1 = (Action) Proxy.newProxyInstance(
            DynamicProxyExample.class.getClassLoader(),
            new Class[] { Action.class },
            new LoggingInvocationHandler(new SleepAction(1000))
        );
        action1.execute();
        
        Action action2 = (Action) Proxy.newProxyInstance(
            DynamicProxyExample.class.getClassLoader(),
            new Class[] { Action.class },
            new MagicActionHandler( () -> System.out.println("RUN"))
       );
        action2.execute();
        
        Action action3 = (Action) Proxy.newProxyInstance(
            DynamicProxyExample.class.getClassLoader(),
            new Class[] { Action.class },
            new LoggingInvocationHandler(action2)
        );
        action3.execute();

    }
}