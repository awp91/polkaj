package io.emeraldpay.polkaj.scale.writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.emeraldpay.polkaj.scale.ScaleCodecWriter;
import io.emeraldpay.polkaj.scale.ScaleWriter;
import io.emeraldpay.polkaj.scale.UnionValue;

public class UnionWriter<T> implements ScaleWriter<UnionValue<T>> {

    private final List<ScaleWriter<T>> mapping;

    @SuppressWarnings("unchecked")
    public UnionWriter(List<ScaleWriter<? extends T>> mapping) {
        this.mapping = new ArrayList<>(mapping.size());
        for (ScaleWriter<? extends T> t: mapping) {
            this.mapping.add((ScaleWriter<T>) t);
        }
    }

    @SuppressWarnings("unchecked")
    public UnionWriter(ScaleWriter<? extends T>... mapping) {
        this(Arrays.asList(mapping));
    }

    @Override
    public void write(ScaleCodecWriter wrt, UnionValue<T> value) throws IOException {
        wrt.directWrite(value.getIndex());
        T actual = value.getValue();
        if (actual != null) {
            mapping.get(value.getIndex()).write(wrt, actual);
        }
    }

}
