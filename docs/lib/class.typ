#import "fp.typ": *;

/// 
///
/// - id (str): Identifier
/// -> dictionary
#let trait(id, ..methods) = {
    let is_auto = false;
    for (key, i) in methods.pos().enumerate() {
        if i == auto {
            is_auto = true;
        } else {
            panic_invalid_arg(..methods, key);
        }
    }
    let trait() = { // count on optimizer
        let result = ("_id": id);
        for (key, method) in methods.named() {
            result.insert(key, (self, ..args) => {
                let impl = self.class._traits.at(id, default: none);
                if impl == none {
                    if not is_auto {
                        panic_args(args);
                    }
                } else {
                    impl = impl.at(key, default: none);
                }
                if impl == none {
                    impl = method(trait());
                }
                impl(self, ..args)
            });
        }
        result
    }
    trait()
}

#let abstract = panic.with("abstract");

/// 
///
/// - args (): 
/// -> function<constructor>
#let class(..args) = {
    let result = args.named();
    result._traits = (:);
    for (trait, impl) in args.pos() {
        result._traits.insert(trait._id, impl);
    }
    result
}

#let __object_tag__() = panic("Do not call");

/// 
///
/// - cls (dictionary<class>): 
/// -> function
#let new(cls) = {
    cls.at("init", default: identity).with(("__object_tag__": __object_tag__, "class": cls))
}

#let is_object(value) = type(value) == dictionary and value.at("__object_tag__", default: none) == __object_tag__;

#let to_str0(object_to_str, value) = {
    if is_object(value) {
        return object_to_str(value);
    }
    let ty = type(value);
    if type(value) == dictionary {
        if value.len() == 0 {
            return "(:)";
        }
        return "(" + value.pairs().map(((k, v)) => to_str(object_to_str, k) + ": " + to_str(object_to_str, v)).join(", ") + ")";
    } else if type(value) == array {
        if value.len() == 1 {
            return "(" + value.first() + ",)";
        }
        return "(" + value.map(to_str.with(object_to_str)).join(", ") + ")";
    } else if type(value) in (int, float, decimal, str, label, bytes, version, type) {
        return str(value);
    }
    return repr(value);
}
#let ToStr = trait("ToStr", auto,
    to_str: Self => self => {
        self.remove("__object_tag__");
        "object" + to_str0(Self.to_str, self)
    },
);
#let to_str(value) = to_str0(ToStr.to_str, value);

#let ToContent = trait("ToContent", auto,
    to_content: Self => self => {
        text(ToStr.to_str.with(self)())
    },
);
#let to_content(value) = {
    if is_object(value) {
        return ToContent.to_content.with(value)();
    } else if type(value) == content {
        return value;
    }
    return [#value];
}

#let Eq = trait("Eq", auto, 
    eq: Self => (self, other) => {
        self == other
    },
);
#let equal(a, b) = {
    if is_object(a) {
        return Eq.eq.with(a)(b);
    } else if is_object(b) {
        return Eq.eq.with(b)(a);
    }
    return a == b;
}

#let cmp0(object_cmp, a, b) = {
    if is_object(a) {
        return object_cmp(a, b);
    } else if is_object(b) {
        return -object_cmp(b, a);
    } else if type(a) != type(b) {
        return cmp0(panic, type(a), type(b));
    }
    let ty = type(a);
    if ty == type {
        return std_cmp(str(a), str(b));
    } else if ty == dictionary {
        return dictionary_cmp(a, b, cmp: cmp0.with(object_cmp));
    }
    return std_cmp(a, b);
}
#let Ord = trait("Ord", auto,
    cmp: Self => (self, other) => {
        if type(other) != dictionary {
            return cmp0(panic, dictionary, type(other));
        }
        dictionary_cmp(self, other, cmp: cmp0.with(Self.cmp))
    }
);
#let cmp(a, b) = cmp0(Ord.cmp, a, b);

#let dictionary_hash(value, hash: panic) = {
    let result = 0;
    for key in value.keys().sorted() {
        result = int_plus(int_mul(result, 31), hash(key));
        result = int_plus(int_mul(result, 31), hash(value.at(key)));
    }
    return result;
}
#let hash0(object_hash, value) = {
    if is_object(value) {
        return object_hash(value);
    }
    let ty = type(value);
    if ty == int {
        return value;
    } else if ty == str {
        return hash0(panic, bytes(value));
    } else if ty in (type, decimal, label) {
        return hash0(panic, str(value));
    } else if ty == float {
        return hash0(panic, value.to-bytes());
    } else if ty == bytes {
        let result = 0;
        for i in value {
            result = int_plus(int_mul(result, 31), i);
        }
        return result;
    } else if ty == array {
        let result = 0;
        for i in value {
            result = int_plus(int_mul(result, 31), hash0(object_hash, i));
        }
        return result;
    } else if ty == dictionary {
        return dictionary_hash(value, hash: hash0.with(object_hash));
    } else if ty == function {
        return hash0(panic, repr(value));
    }
    todo(ty);
}
#let Hash = trait("Hash", auto,
    hash: Self => self => {
        dictionary_hash(self, hash: hash0.with(Self.hash))
    }
);
#let hash(value) = hash0(Hash.hash, value);

#{ // test
    let Test = class(
        init: (self, data) => {
            self.data = data;
            self
        },
        (Eq, (
            eq: (self, other) => true,
        )),
        (ToStr, (
            to_str: self => "test",
        )),
    );
    let test0 = new(Test)(0);
    let test1 = new(Test)(1);
    assert(equal(test0, test1)); // override
    assert(cmp(test0, test1) < 0);
    assert.ne(hash(test0), hash(test1));
    assert.eq(to_str(test0), "test");
}
