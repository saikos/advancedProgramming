# Implementation of a simple aspect weaver using Java dynamic proxies

You can find the basic interfaces (Aspect, AspectBuilder, AspectWeaver) in the `lib/src/main/java/gr/uoa/di/aspect_proxy` 
directory, along with the Factory class, which acts as the "entry-point" to the aspect-oriented mechanism under definition.

What you need to do:

1. Define your own concrete implementations of the Aspect, AspectBuilder, AspectWeaver interfaces.
2. Define a proper implementation of the Factory class to utilize these implementations.
3. Ensure that your overall implementation passes all the tests contained in
the `lib/src/test/groovy/gr/uoa/di/aspect_proxy/AspectProxySpec.groovy` file.

To verify that your implementation compiles and passes the tests, invoke the following command from the project root directory:

```
> ./gradlew build
```

or 

```
> ./gradlew test
```

After each execution, you can find the test reports in the `lib/build/reports/tests/test/index.html` file.