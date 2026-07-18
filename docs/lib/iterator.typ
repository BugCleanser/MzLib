#import "class.typ": *;

// Iterator<T>
#let Iterator = trait("Iterator",
    has_next: Self => self => abstract(self),
    next: Self => self => abstract(self),
    for_each: Self => (self, action) => {
        let it = self;
        while Self.has_next.with(it)() {
            let value;
            (value, it) = Self.next.with(it)();
            action(value);
        }
    },
    to_array: Self => self => {
        let result = ();
        let it = self;
        while Self.has_next.with(it)() {
            let value;
            (value, it) = Self.next.with(it)();
            result.push(value);
        }
        return result;
    },
    fold: Self => (self, init, f) => {
        let result = init;
        let it = self;
        while Self.has_next.with(it)() {
            let value;
            (value, it) = Self.next.with(it)();
            result = f(result, value);
        }
        return result;
    },
    reduce: Self => (self, f) => {
        let (value, it) = Self.next.with(self)();
        return Self.fold.with(it)(value, f);
    },
    find: Self => (self, predicate) => {
        let it = self;
        while Self.has_next.with(it)() {
            let value;
            (value, it) = Self.next.with(it)();
            if predicate(value) {
                return (value,);
            }
        }
        return ();
    },
    any: Self => (self, predicate) => {
        let it = self;
        while Self.has_next.with(it)() {
            let value;
            (value, it) = Self.next.with(it)();
            if predicate(value) {
                return true;
            }
        }
        return false;
    },
    all: Self => (self, predicate) => {
        return not Self.any.with(self)(negate(predicate));
    },
    count: Self => self => {
        return Self.fold(self)(0, int_inc);
    },
    map: Self => (self, f) => {
        return new(class((Self, (
            has_next: s => Self.has_next.with(self)(),
            next: s => {
                let (result, it) = Self.next.with(self)();
                return (f(result), Self.map.with(it)(f));
            },
        ))),)();
    },
    enumerate: Self => (self, index: 0) => {
        return new(class((Self, (
            has_next: s => Self.has_next.with(self)(),
            next: s => {
                let (result, it) = Self.next.with(self)();
                return ((index, result), Self.enumerate.with(it)(index: index +  1));
            },
        ))),)();
    },
);

#let Iterable = trait("Iterable",
    iter: Self => self => abstract(self),
);

#let ArrayIterator = class(
    init: (self, arr, index: 0) => {
        self.arr = arr;
        self.index = index;
        return self;
    },
    (Iterator, (
        has_next: self => {
            return self.index < self.arr.len();
        },
        next: self => {
            let result = self.arr.at(self.index);
            self.index += 1;
            return (result, self);
        }
    )),
);
/// 
///
/// - arr (array): self
/// - index (int): 
/// -> ArrayIterator
#let array_iterator(arr, index: 0) = new(ArrayIterator)(arr, index: index);

#{
    let it = Iterator.map.with(array_iterator((1, 2, 3)))(int_inc);
    Iterator.for_each.with(it)(it => [hello #it \ ])
}
