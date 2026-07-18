#import "class.typ": *;
#import "iterator.typ": *;

#let Map = trait("Map", 
    get: Self => (self, key, default: none) => abstract(self),
    put: Self => (self, key, value) => abstract(self),
    map_key: Self => (self, action) => abstract(self),
);

#let MapArray = class(
    init: (self, pairs: ()) => {
        self.pairs = pairs;
        return self;
    },
    (Map, (
        get: (self, key, default: none) => {
            for i in range(self.pairs.len()) {
                if equal(self.pairs.at(i).at(0), key) {
                    return self.pairs.at(i).at(1);
                }
            }
            return default;
        },
        put: (self, key, value) => {
            for i in range(self.pairs.len()) {
                if equal(self.pairs.at(i).at(0), key) {
                    self.pairs.at(i).at(1) = value;
                    return self;
                }
            }
            self.pairs.push((key, value));
            return self;
        },
        map_key: (self, action) => {
            for i in range(self.pairs.len()) {
                self.pairs.at(i).at(0) = action(self.pairs.at(i).at(0));
            }
            return self;
        },
    )),
    (Iterable, (
        iter: self => {
            return array_iterator(self.pairs);
        },
    )),
);=

#let MapDict_Iterator = class(
    init: (self, data) => {
        self.data = data;
        self.cnt = none;
        self.keys_it = array_iterator(data.keys());
        while self.cnt == none or not Iterator.has_next.with(self.cnt)() {
            (self.cnt, self.keys_it) = Iterable.next.with(self.keys_it)();
        }
        return self;
    },
    (Iterator, (
        has_next: Self => self => {
            return self.cnt != none;
        },
        next: Self => self => {
            let result;
            (result, self.cnt) = Iterable.next.with(self.cnt)();
            while not Iterator.has_next.with(self.cnt)() {
                if not Iterator.has_next.with(self.keys_it)() {
                    self.cnt = none;
                    break;
                }
                (self.cnt, self.keys_it) = Iterable.next.with(self.keys_it)();
            }
            return (result, self);
        },
    )),
);
#let MapDict = class(
    init: (self, data: (:)) => {
        self.data = data;
        return self;
    },
    (Map, (
        get: (self, key, default: none) => {
            let bucket = self.data.at(to_str(key), default: none);
            if bucket == none {
                return default;
            }
            return Map.get.with(bucket)(key, default: default);
        },
        put: (self, key, value) => {
            let sk = to_str(key);
            let bucket = self.data.at(sk, default: none);
            if bucket != none {
                self.data.at(sk) = Map.put.with(bucket)(key, value);
            } else {
                self.data.insert(sk, new(MapArray)(pairs: ((key, value),)));
            }
            return self;
        },
        map_key: (self, action) => {
            let next = new(self.class)();
            for (_, bucket) in self.data {
                for (k, v) in bucket.pairs {
                    next = Map.put.with(next)(action(k), v);
                }
            }
            return next;
        },
    )),
    (Iterable, (
        iter: self => {
            return new(MapDict_Iterator)(self.data);
        },
    )),
);

#let mod(a, b) = {
    let r = calc.rem-euclid(a, b);
    if b < 0 { 
        return -r; 
    } else { 
        return r;
    }
}

// todo
#let MapHash = class(
    init: (self, capacity: 10) => {
        self.size = 0;
        self.data = ((new(MapArray)(),),) * capacity;
        return self;
    },
    (Map, (
        get: (self, key, default: none) => {
            return Map.get.with(self.data.at(mth.mod(hash(key), self.data.len())))(key, default: default);
        },
        put: (self, key, value) => {
            if (self.size + 1) >= self.data.len() / 2 {
                let last = self;
                self = new(self.class, capacity: self.data.len() * 2);
                self = Map.put_all(self, last);
            }
            self.size += 1;
            let ha = mth.mod(hash(key), self.data.len());
            self.data.at(ha) = Map.put.with(self.data.at(ha))(key, value);
            return self;
        },
    )),
);
