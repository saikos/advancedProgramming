import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

public class ReflectionExample {

    public static void main(String[] args) {
        if (args != null && args.length == 1) {
            try {
                reflectOnType(args[0]);
            }
            catch(ClassNotFoundException cnfe) {
                System.out.println("Class not found: " + cnfe.getMessage());
            }
        }
        else {
            System.out.println("Provide a full qualified class name");
        }
    }

    private static void reflectOnType(String typeName) throws ClassNotFoundException {
        Class c = Class.forName(typeName);
        Method[] methods = c.getDeclaredMethods();
        System.out.println("The " + typeName + " has " + methods.length + " methods");
        System.out.println("These are the following: " + mapMethodsToString(methods));
    }

    private static String mapMethodsToString(Method[] methods) {
        List<String> methodStrings = new ArrayList<>();
        for (Method m: methods) {
            String methodStr = mapMethodToString(m);
            methodStrings.add(methodStr);
        }
        return String.join(", ", methodStrings);
    } 

    private static String mapMethodToString(Method m) {
        return m.getName();
    }
    
}
