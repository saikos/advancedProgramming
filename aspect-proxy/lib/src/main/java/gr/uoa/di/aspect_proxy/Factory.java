package gr.uoa.di.aspect_proxy;

import java.util.ArrayList;
import java.util.List;

public class Factory {

    private final List<Aspect> aspects = new ArrayList<>();

    public AspectBuilder newAspectBuilder() {
        //throw new UnsupportedOperationException("Implement me");
        //return new AspectBuilderImpl( (aspect) -> aspects.add(aspect));
        return new AspectBuilderImpl(aspects::add);
    }

    public AspectWeaver newAspectWeaver() {
        return new AspectWeaverImpl(() -> aspects);
    }

}

