@file:Suppress("NOTHING_TO_INLINE")

package mz.mzlib.util

inline operator fun <T> Box<T>.component1(): T = get()
