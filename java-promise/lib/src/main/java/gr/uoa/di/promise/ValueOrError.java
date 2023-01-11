package gr.uoa.di.promise;

public interface ValueOrError<V>  {
    boolean hasError();
    V value();
    Throwable error();

    static class Value<V> implements ValueOrError<V> {

        private final V value;

        private Value(V value) {
            this.value = value;
        }

        @Override
        public V value() {
            return value;
        }

        @Override
        public Throwable error() {
            return null;
        }

        @Override
        public boolean hasError() {
            return false;
        }

        @Override
        public String toString() {
            return "Value{" +
                    "value=" + value +
                    '}';
        }

        static <T> ValueOrError<T> of(T t) { return new Value<>(t);}

    }

    static class Error<V> implements ValueOrError<V> {

        private final Throwable throwable;

        private Error(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public V value() {
            return null;
        }

        @Override
        public Throwable error() {
            return throwable;
        }

        @Override
        public boolean hasError() {
            return true;
        }

        @Override
        public String toString() {
            return "Error{" +
                    "throwable=" + throwable +
                    '}';
        }

        static <T> ValueOrError<T> of(Throwable t) { return new Error<>(t);}
    }

}
