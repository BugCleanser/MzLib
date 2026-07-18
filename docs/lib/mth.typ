#import "object.typ": *;
#import "map.typ": *;
#import "contents.typ": *;

#let mod(a, b) = {
    let r = calc.rem-euclid(a, b);
    if b < 0 { 
        return -r; 
    } else { 
        return r;
    }
}

#let to_decimal(value) = {
    if type(value) == float and value.is-infinite() {
        return value;
    } else {
        return decimal(value);
    }
}
#let from_decimal(value) = {
    if str(value).contains(".") {
        return float(value);
    } else {
        return int(value);
    }
}

#let ops = new(MapDict)();
#let ops_put(ops, op, p, f) = {
    if type(op) == content {
        if op.func() == math.equation {
            op = op.body;
        }
    } else if type(op) == symbol {
        op = [#op];
    } else {
        panic(op);
    }
    return Map.put.with(ops)(op, (p, f));
}
#let ops = ops_put(ops, math.plus, 1, (a, b) => a + b);
#let ops = ops_put(ops, math.minus, 1, (a, b) => a - b);
#let ops = ops_put(ops, math.times, 2, (a, b) => a * b);
#let ops = ops_put(ops, math.div, 2, (a, b) => a / b);
#let ops = ops_put(ops, math.mod, 2, mod);

#let calc0(exp, where) = {
    let func = exp.func();
    if func == math.equation {
        return calc0(exp.body, where);
    } else if func == sequence {
        exp = exp.children.filter(it => it != [ ]);
        let stack_num = ();
        let stack_op = ();
        let neg = false;
        for i in exp {
            if i == [#math.minus] and stack_num.len() == stack_op.len() {
                neg = not neg;
                continue;
            }
            let op = ops(get: bind)(i);
            if op != none {
                while stack_op.len() > 0 and stack_op.last().at(0) >= op.at(0) {
                    let last = stack_num.pop();
                    stack_num.push(
                        stack_op.pop().at(1)(stack_num.pop(), last)
                    );
                }
                stack_op.push(op);
            } else {
                i = calc0(i, where);
                if neg {
                    i = -i;
                    neg = false;
                }
                if stack_num.len() > stack_op.len() {
                    stack_num.push(stack_num.pop() * i);
                } else {
                    stack_num.push(i);
                }
            }
        }
        if neg {
            panic(exp);
        }
        while stack_op.len() > 0 {
            let last = stack_num.pop();
            stack_num.push(
                stack_op.pop().last()(stack_num.pop(), last)
            );
        }
        assert(stack_num.len() == 1);
        return stack_num.first();
    } else if func == math.frac {
        return calc0(exp.num, where) / calc0(exp.denom, where);
    } else if func == math.root {
        return to_decimal(std.calc.root(from_decimal(calc0(exp.radicand, where)), if exp.has("index") {from_decimal(calc0(exp.index, where))} else {2}));
    } else if func == math.attach and exp.fields().keys().contains("t") {
        let fields = exp.fields();
        _ = fields.remove("base");
        _ = fields.remove("t");
        let base = exp.base;
        if fields.len() > 0 {
            base = math.attach(base, ..fields)
        }
        base = calc0(base, where);
        let t = calc0(exp.t, where);
        if type(t) == decimal {
            t = from_decimal(t);
        }
        if type(t) == float {
            base = float(base);
        }
        return to_decimal(std.calc.pow(base, t));
    } else if func == math.lr { // brackets
        return calc0(exp.body.children.slice(1, -1).join(), where);
    } else if func == math.text { // constant
        return to_decimal(exp.text);
    } else {
        let result = Map.get.with(where)(exp);
        if result == none {
            panic(exp, where);
        }
        return to_decimal(result);
    }
}

/// 
///
/// - exp (content): 
/// - where (dictionary, array, none):
/// -> 
#let calc(exp, where: none) = {
    if type(exp) != content {
        panic(exp);
    }
    if type(where) == dictionary {
        where = where.pairs();
    }
    else if where == none {
        where = ();
    }
    where = new(MapArray)(pairs: where);
    where = Map.map_key.with(where)(k => {
        if type(k) == str {
            k = eval(k, mode: "math");
        }
        assert(k.func() == math.equation);
        k.body
    });
    return calc0(exp, where);
}

// test
#{
    assert.eq(calc($x^2$, where: (x: 2)), 4);
}
