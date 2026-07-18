// Deprecated

#import "fp.typ": *;

/// 
/// repr: undefined
/// 
/// -> any
#let undefined = named_function("undefined", self => panic(self));

#let get = named_function("get", (get, object, data, key, default: undefined) => {
    let absence = () => {};
    let d = data;
    let result = absence;
    while result == absence {
        if d == absence {
            return default;
        }
        result = d.at(key, default: absence);
        d = d.at("__proto__", default: absence);
    }
    result
});
#let has = named_function("has", (has, object, data, key, default: undefined) => {
    let d = data;
    while d != none {
        if key in d {
            return true;
        }
        d = d.at("__proto__", default: none);
    }
    false
});
#let has_own = named_function("has_own", (has_own, object, data, key, default: undefined) => {
    key in data
});
#let with = named_function("with", (with, object, data, key, default: undefined, value) => {
    object(data + dictionary_item(key, value))
});
#let get_and_owner = named_function("get_and_owner", (get_and_owner, object, data, key, default: undefined) => {
    let absence = () => {};
    let d = data;
    let result = absence;
    let owner;
    while result == absence {
        if d == absence {
            return (default, none);
        }
        owner = d;
        result = d.at(key, default: absence);
        d = d.at("__proto__", default: absence);
    }
    (result, owner)
});
#let delete = named_function("delete", (delete, object, data, key, default: undefined) => {
    let absence = () => {};
    let data = data;
    if data.remove(key, default: absence) == absence {
        panic_args(..ops); // Field not found
    }
    object(data)
});

#let get_data = named_function("get_data", (get_data, object, data) => {
    data
});
#let get_proto = named_function("get_proto", (get_proto, object, data) => {
    let result = data.at("__proto__", default: none);
    if result == none {
        return none;
    }
    object(result)
});
#let get_bound = named_function("get_bound", panic.with("donot call"));
#let super = named_function("super", panic.with("donot call"));

/// 
/// repr: __object__
#let bind0(obj, owner) = {
    let __object__(..ops) = {
        if ops.named().len() > 0 {
            let (key, op) = ops.named().pairs().first();
            if op == super {
                let (m, o) = owner(get_proto)(..dictionary_item(key, get_and_owner));
                if o == none {
                    panic(..ops); // Method not found
                }
                return m.with(bind0(obj, o));
            }
        } else {
            let op = ops.at(0);
            if op == get_bound {
                return owner;
            }
        }
        obj(..ops)
    }
    __object__
}
#let bind = named_function("bind", (bind, object, data, key, default: undefined) => {
    let (result, owner) = get_and_owner(object, data, key, default: default);
    if type(result) != function {
        panic_args(..ops); // Method not found
    }
    result.with(bind0(object(data), object(owner)))
});

/// 
/// repr: __object__
///
/// - data (dictionary): 
/// -> object
#let object(data) = {
    ///
    /// - default (any):
    /// -> any
    let __object__(..ops, default: undefined) = {
        let absence = () => {};
        if ops.named().len() > 0 {
            let (key, op) = ops.named().pairs().first();
            if type(op) != function {
                panic_args(..ops);
            }
            return op(object, data, key, default: default, ..ops.pos());
        }
        if ops.len() != 1 {
            panic_args(..ops);
        }
        let op = ops.at(0);
        if type(op) == str {
            return bind(object, data, op, default: default);
        }
        if op == get_bound {
            return none;
        }
        return op(object, data);
    }
    __object__
}

#let Object_create(proto, data) = {
    object(("__proto__": proto(get_data)) + data)
}

/// 
///
/// - proto (object): 
/// -> function
#let new(proto) = {
    let result = object(("__proto__": proto(get_data)));
    result("init", default: identity)
}

/// 
///
/// - value (any): 
/// -> bool
#let is_object(value) = {
    type(value) == function and function_name(value) == function_name(object(none))
}

/// 
///
/// - value (any): 
/// -> str
#let to_str(value) = {
    if is_object(value) {
        return value(toString: bind, default: self => "Object" + to_str(self(get_data)))();
    } else if type(value) == dictionary {
        if value.len() == 0 {
            return "(:)";
        }
        return "(" + value.pairs().map(((k, v)) => to_str(k) + ": " + to_str(v)).join(", ") + ")";
    } else if type(value) == array {
        if value.len() == 1 {
            return "(" + value.first() + ",)";
        }
        return "(" + value.map(to_str).join(", ") + ")";
    } else if type(value) in (int, float, decimal, str, label, bytes, version, type) {
        return str(value);
    } else {
        return repr(value);
    }
    panic();
}

/// 
///
/// - value (any): 
/// -> content
#let to_content(value) = {
    if is_object(value) {
        return value(toContent: bind, default: self => text(to_str(self)))();
    } else if type(value) == content {
        return value;
    } else {
        return [#value];
    }
    panic();
}

/// 
///
/// - a (any): 
/// - b (any): 
/// -> bool
#let equal(a, b) = {
    if is_object(a) {
        let absence = () => {};
        let result = a(equals: bind, default: self => absence)();
        if result != absence {
            return result;
        }
    }
    if is_object(b) {
        return equal(b, a);
    }
    return a == b;
}

#let Object = object((
    init: self => {
        self
    },
    toString: self => {
        "Object" + to_str(self(get_data))
    },
    toContent: self => {
        text(to_str(self))
    },
    equals: (self, other) => {
        self == other
    },
));

#let abstract = panic.with("abstract");

// test
#{
    let o = new(Object)();
    assert(is_object(o));
    assert(not o(awa: has));

    o = o(awa: with, 114);
    assert(o(awa: has));
    assert(o(awa: has_own));
    assert.eq(o(awa: get), 114);

    o = o(awa: delete);
    assert(not o(awa: has));
}
