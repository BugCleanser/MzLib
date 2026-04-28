@file:Suppress("NOTHING_TO_INLINE")

package mz.mzlib.util

inline operator fun <T1, T2> Pair<T1, T2>.component1(): T1 = getFirst()
inline operator fun <T1, T2> Pair<T1, T2>.component2(): T2 = getSecond()
