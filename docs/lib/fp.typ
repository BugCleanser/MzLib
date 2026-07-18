
#let todo = panic.with("todo");

#let panic_args(args) = (() => panic())(..args);

#let filter_for_named(args) = if args.pos().len() == 0 { args } else {
    eval("(" + range(0, args.pos().len()).map(i => "a" + str(i)).join(", ") + ", ..args) => args")(..args)
}

#let filter_for_pos(args) = if args.named().len() == 0 { args } else {
    eval("(" + args.named().keys().map(i => i + ": none").join(", ") + ", ..args) => args")(..args)
}

#let panic_invalid_arg(args, key) = {
    if type(key) == str {
        args = filter_for_named(args);
        eval("(" + args.named().keys().filter(i => i != key).map(i => i + ": none").join(", ") + ") => {}")(..args);
    } else {
        args = filter_for_pos(args);
        eval("(" + range(0, calc.min(args.pos().len(), key)).map(i => "a" + str(i)).join(", ") + ") => {}")(..args);
    }
}


/// 
///
/// - path ([source]): 
/// -> module
#let require(path) = {
    import path as result;
    result
}
/// 
///
/// - args (arguments): 
/// -> none
#let assert_no_pos(args) = if args.pos().len() > 0 {
    panic_args(filter_for_pos(args));
}

/// 
///
/// - args (arguments): 
/// -> none
#let assert_no_named(args) = if args.named().len() > 0 {
    panic(..args);
}

#let identity(it) = it;
#let negate(predicate) = it => not predicate(it);

/// 
///
/// - funcs (array): 
/// -> function
#let pipe(..funcs) = {
    assert_no_named(funcs);
    funcs = funcs.pos();
    if funcs.len() == 0 {
        return identity;
    } else if funcs.len() == 1 {
        return funcs.first();
    }
    let f0;
    (f0, ..funcs) = funcs;
    (..args) => {
        let it = f0(..args);
        for i in funcs {
            it = i(it);
        }
        it
    }
}
#{ //test
    let f(it) = it + "a";
    assert.eq(pipe()(f), f);
    assert.eq(pipe(f)(""), "a");
    assert.eq(pipe(f, f)(""), "aa");
    assert.eq(pipe(f, f, f)(""), "aaa");
}

#let named_function(name, func) = {
    if name == "f" {
        let f(..args) = func(f, ..args);
        f
    } else {
        eval("let " + name + "(..args) = f(" + name + ", ..args); " + name, scope: ("f": func))
    }
}

#let function_name(func) = {
    let result = repr(func);
    if result == repr(() => {}) { // lambda
        return none;
    }
    result
}

/// 
///
/// - key (str): 
/// - value (): 
/// -> dictionary
#let dictionary_item(key, value) = {
    let result = (:);
    result.insert(key, value);
    result
}

#let std_cmp(a, b) = if a == b { 0 } else if a < b { -1 } else { 1 };

#let bool_or(a, b) = a or b;
#let bool_and(a, b) = a and b;

#let INT_QUARTER = int.max.bit-rshift(1) + 1;
#let int_inc(i) = i + 1;
#let int_plus(a, b) = {
    let a_lo = a.bit-and(0xFFFFFFFF);
    let a_hi = a.bit-rshift(32).bit-and(0xFFFFFFFF);
    let b_lo = b.bit-and(0xFFFFFFFF);
    let b_hi = b.bit-rshift(32).bit-and(0xFFFFFFFF);
    
    let lo_sum = a_lo + b_lo;
    let carry = lo_sum.bit-rshift(32).bit-and(0x1);
    let hi_sum = (a_hi + b_hi + carry).bit-and(0xFFFFFFFF);
    
    return hi_sum.bit-lshift(32).bit-or(lo_sum.bit-and(0xFFFFFFFF));
}
#let int_minus(a, b) = {
    int_plus(a, int_plus(b.bit-not(), 1))
}
#let i32_mul(x, y) = {
    let x0 = x.bit-and(0xFFFF);
    let x1 = x.bit-rshift(16).bit-and(0xFFFF);
    let y0 = y.bit-and(0xFFFF);
    let y1 = y.bit-rshift(16).bit-and(0xFFFF);
    
    let result = x0 * y0;
    result = int_plus(result, (x0 * y1).bit-lshift(16));
    result = int_plus(result, (x1 * y0).bit-lshift(16));
    result = int_plus(result, (x1 * y1).bit-and(0xFFFFFFFF).bit-lshift(32));
    return result;
}
#let int_mul(a, b) = {
    let a_lo = a.bit-and(0xFFFFFFFF);
    let a_hi = a.bit-rshift(32).bit-and(0xFFFFFFFF);
    let b_lo = b.bit-and(0xFFFFFFFF);
    let b_hi = b.bit-rshift(32).bit-and(0xFFFFFFFF);
    
    let p1 = i32_mul(a_lo, b_lo);
    let p2 = i32_mul(a_lo, b_hi).bit-and(0xFFFFFFFF).bit-lshift(32);
    let p3 = i32_mul(a_hi, b_lo).bit-and(0xFFFFFFFF).bit-lshift(32);
    
    let result = int_plus(p1, p2);
    result = int_plus(result, p3);
    
    return result;
}

/// 
/// - a (dictionary): 
/// - b (dictionary): 
/// -> bool
#let dictionary_cmp(a, b, cmp: std_cmp) = {
    if a == b {
        return 0;
    }
    let result = a.len() - b.len();
    if result != 0 {
        return result;
    }
    for key in (a.keys() + b.keys()).dedup().sorted() {
        let result = int(key in a) - int(key in b);
        if result != 0 {
            return result;
        }
        let result = cmp(a.at(key), b.at(key));
        if result != 0 {
            return result;
        }
    }
    panic();
}
#{ // test
    assert(dictionary_cmp((a: 1), ()) > 0)
    assert(dictionary_cmp((a: 1), (b: 1)) > 0)
    assert(dictionary_cmp((a: 0), (a: 1)) < 0)
}
