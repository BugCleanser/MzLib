package mz.mzlib.util.placeholder.primitive;

public interface SubPlaceholder<T,K> extends PlaceholderParser<T> {
    K getPlaceholderCore();
}
