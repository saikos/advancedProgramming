import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {        
        ModuleLayer bootLayer = ModuleLayer.boot();
        Configuration bootConfig = bootLayer.configuration();
        AtomicInteger totalModules = new AtomicInteger(0);
        AtomicInteger totalClasses = new AtomicInteger(0);
        bootLayer.modules().stream()
        .filter( m -> m.getName().startsWith("java.")) // filter the SE modules only
        .forEach( m -> {            
            Set<String> packages = m.getPackages();
            Optional<ResolvedModule> resolved = bootConfig.findModule(m.getName());
            resolved.ifPresent( rm -> {                
                ModuleReference ref = rm.reference();                
                System.out.println("Resolved module: " + rm.name() + " with " + packages.size() + " packages: " + String.join(", ", packages));
                totalModules.incrementAndGet();
                try (ModuleReader reader = ref.open()) {
                    reader.list().forEach( s -> {

                        try {
                            if (s.endsWith(".class") && !s.equals("module-info.class")) { // exclude non-class resources & the module-info class
                                String packageName = s.substring(0, s.lastIndexOf('/')).replace('/', '.');
                                // System.out.println("package name: " + packageName);
                                if (m.isExported(packageName)) {
                                    String className = s.replace('/', '.').substring(0, s.length() - ".class".length());                                    
                                    // System.out.println("m: " + m + " - n: " + s + " c: " + className);
                                    Class cl = Class.forName(className);
                                    System.out.println("Loaded class: " + cl);
                                    totalClasses.incrementAndGet();
                                }
                            }
                        }
                        catch(ClassNotFoundException e) {                            
                            System.out.println(e.getMessage());
                            e.printStackTrace(System.out);
                        }
                        catch(Exception ex) {
                            System.out.println(ex.getMessage());
                            ex.printStackTrace(System.out);
                        }
                    });                        
                }                
                catch(IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace(System.out);
                }                                
            });
        });
        System.out.println("Done: " + totalModules.get() + " modules, " + totalClasses.get() + " classes");
        
    }
}