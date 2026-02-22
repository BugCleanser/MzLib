package mz.mzlib.util

operator fun <T1, T2> Pair<T1, T2>.component1(): T1 = first
operator fun <T1, T2> Pair<T1, T2>.component2(): T2 = second
