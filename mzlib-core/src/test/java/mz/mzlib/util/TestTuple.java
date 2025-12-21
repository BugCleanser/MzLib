package mz.mzlib.util;

import org.junit.jupiter.api.Test;

public class TestTuple
{
    @Test
    public void test()
    {
        Tuple<Tuple<Tuple<?, ?>, Integer>, String> tuple = Tuple.builder().append(1234).append("hello").build();
        String str = tuple.last();
        Integer i = tuple.prefix().last();

        Tuple<Tuple<Tuple<Tuple<Tuple<Tuple<Tuple<Tuple<Tuple<Tuple<?, ?>, Integer>, Boolean>, Float>, Double>, String>, Object>, Long>, Short>, Byte>
            tuple2 = Tuple.builder()
            .append(114)
            .append(true)
            .append(514.f)
            .append(3.14)
            .append("world")
            .append(new Object())
            .append(998244353L)
            .append((short) 32767)
            .append((byte) 127)
            .build();

        boolean n2 = Tuple.sub1(Tuple.sub2(Tuple.sub4(tuple2))).last();
    }
}
