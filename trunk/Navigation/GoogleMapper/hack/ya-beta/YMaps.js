(function(U) {
    try {
        undefined
    } catch(Y) {
        window.
        undefined = void 0
    }
    try {
        if (!document.getElementsByTagName("head")[0]) {
            document.getElementsByTagName("html")[0].appendChild(document.createElement("head"))
        }
    } catch(Y) {
    }
    (function() {
        var i = Array.prototype,j = {push:function() {
            var u = arguments;
            for (var v = 0,t = u.length; v < t; v++) {
                this[this.length] = u[v]
            }
            return this.length
        },pop:function() {
            var t,u = this.length;
            if (u != 0) {
                t = this[u - 1];
                this.length--
            }
            return t
        },unshift:function() {
            var x,v,u = arguments,y = u.length,w = this.length,t = y + w;
            this.length = t;
            for (x = w - 1,v = 0; x >= v; x--) {
                this[x + y] = this[x]
            }
            for (x = 0,v = y; x < v; x++) {
                this[x] = u[x]
            }
            return this.length
        },splice:function(AA, y) {
            var x,w = arguments,u = this.length,z = [];
            AA = AA || 0;
            y = y || 0;
            AA = AA < 0 ? Math.max(u + AA, 0) : Math.min(AA, u);
            y = Math.min(Math.max(y, 0), u - AA);
            if (y) {
                z = this.slice(AA, AA + y);
                u -= y;
                for (x = AA; x < u; x++) {
                    this[x] = this[x + y]
                }
                this.length = u
            }
            if (w.length > 2) {
                var v;
                var t = this.slice(AA);
                this.length = AA;
                for (x = 2,v = w.length; x < v; x++) {
                    this.push(w[x])
                }
                for (x = 0,v = t.length; x < v; x++) {
                    this.push(t[x])
                }
            }
            return z
        },shift:function() {
            var t;
            if (this.length !== 0) {
                t = this[0];
                this.splice(0, 1)
            }
            return t
        },indexOf:function(t, u) {
            u = u || 0;
            for (var v = this.length; u < v; u++) {
                if (this[u] === t) {
                    return u
                }
            }
            return -1
        },lastIndexOf:function(t, u) {
            var v = this.length;
            u = u || v - 1;
            if (u < 0) {
                u += v
            }
            for (; u >= 0; u--) {
                if (this[u] == t) {
                    return u
                }
            }
            return -1
        },every:function(w, t) {
            t = t || window;
            var u = 0,v = this.length;
            for (; u < v; u++) {
                if (!w.apply(t, [this[u],u,this])) {
                    break
                }
            }
            return(u == v)
        },filter:function(y, t) {
            t = t || window;
            var x = this.length,w = 0,v = new Array(x);
            for (var u = 0; u < x; u++) {
                if (y.apply(t, [this[u],u,this])) {
                    v[w++] = this[u]
                }
            }
            v.length = w;
            return v
        },forEach:function(w, t) {
            t = t || window;
            for (var u = 0,v = this.length; u < v; u++) {
                w.apply(t, [this[u],u,this])
            }
        },map:function(x, t) {
            t = t || window;
            var u = 0,v = this.length,w = new Array(v);
            for (; u < v; u++) {
                w[u] = x.apply(t, [this[u],u,this])
            }
            return w
        },some:function(w, t) {
            t = t || window;
            var u = 0,v = this.length;
            for (; u < v; u++) {
                if (w.apply(t, [this[u],u,this])) {
                    break
                }
            }
            return(u != v)
        },reduce:function(w) {
            var t = this.length;
            if (typeof w != "function") {
                throw new TypeError()
            }
            if (t == 0 && arguments.length == 1) {
                throw new TypeError()
            }
            var u = 0,v;
            if (arguments.length >= 2) {
                v = arguments[1]
            } else {
                do{
                    if (typeof this[u] != "undefined") {
                        v = this[u++];
                        break
                    }
                    if (++u >= t) {
                        throw new TypeError()
                    }
                } while (true)
            }
            for (; u < t; u++) {
                if (typeof this[u] != "undefined") {
                    v = w.call(null, v, this[u], u, this)
                }
            }
            return v
        },reduceRight:function(w) {
            var t = this.length;
            if (typeof w != "function") {
                throw new TypeError()
            }
            if (t == 0 && arguments.length == 1) {
                throw new TypeError()
            }
            var u = t - 1,v;
            if (arguments.length >= 2) {
                v = arguments[1]
            } else {
                do{
                    if (typeof this[u] != "undefined") {
                        v = this[u--];
                        break
                    }
                    if (--u >= t) {
                        throw new TypeError()
                    }
                } while (true)
            }
            for (; u >= 0; u--) {
                if (typeof this[u] != "undefined") {
                    v = w.call(null, v, this[u], u, this)
                }
            }
            return v
        }};
        for (var e in j) {
            if (!i[e]) {
                i[e] = j[e]
            }
        }
        var g = String.prototype,r = "";
        if (r.indexOf(r) != 0) {
            g.indexOfBug = g.indexOf;
            g.indexOf = function(t) {
                if (this.toString() == r && t === r) {
                    return 0
                }
                return this.indexOfBug(t)
            };
            g.lastIndexOfBug = g.lastIndexOf;
            g.lastIndexOf = function(t) {
                var u = this.lastIndexOfBug(t);
                if (t === r) {
                    u++
                }
                return u
            }
        }
        if (r.replace(/^/, String)) {
            var q = /(g|gi)$/,f = g.replace;
            g.replace = function(z, w) {
                if (typeof w == "function") {
                    var y,x,v,u = this,t = r;
                    if (z && z.constructor == RegExp) {
                        y = z;
                        x = y.global;
                        if (x == null) {
                            x = q.test(y)
                        }
                        if (x) {
                            y = new RegExp(y.source)
                        }
                    } else {
                        y = new RegExp(rescape(z))
                    }
                    while (u && (v = y.exec(u))) {
                        t += u.slice(0, v.index) + w.apply(this, v);
                        u = u.slice(v.index + v[0].length);
                        if (!x) {
                            break
                        }
                    }
                    return t + u
                }
                return f.apply(this, arguments)
            }
        }
        var s = String.fromCharCode;
        if (!g.charCodeAt) {
            g.charCodeAt = function(AB) {
                var x = 0,z = escape(this).match(/(%[\da-fA-F]{2}|%u[\da-fA-F]{4}|.)/g);
                for (var AA = 0,w = z.length; AA < w; AA++) {
                    var u = z[AA];
                    if (!u) {
                        continue
                    }
                    if (u.indexOf("%u") == 0) {
                        u = parseInt(u.replace("%u", r), 16)
                    } else {
                        if (u.indexOf("%") == 0) {
                            u = parseInt(u.replace("%", r), 16)
                        } else {
                            var v = 0,y = 256,AC = 0,t = r;
                            while (y - v > 1) {
                                AC = (y + v) >> 1;
                                t = s(AC);
                                if (t > u) {
                                    y = AC
                                } else {
                                    if (t < u) {
                                        v = AC
                                    } else {
                                        u = AC;
                                        break
                                    }
                                }
                            }
                        }
                    }
                    if (x == AB) {
                        return u
                    }
                    x++
                }
                return NaN
            }
        }
        if (!window.encodeURIComponent) {
            var l = "0123456789ABCDEF".split("");
            function p(t) {
                return l[t >> 4] + l[t & 15]
            }
            function h(u, v) {
                u = u.toString().replace(/\r\n/g, "\n");
                var w = "",z,y = 0,x = u.length,t = "!'()*-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~";
                if (v) {
                    t += "#$&+,-/:;=?@"
                }
                for (; y < x; y++) {
                    z = u.charCodeAt(y);
                    if (z < 128) {
                        if (t.indexOf(u.charAt(y)) != -1) {
                            w += s(z)
                        } else {
                            w += "%" + p(z)
                        }
                    } else {
                        if ((z > 127) && (z < 2048)) {
                            w += escape(s((z >> 6) | 192) + s((z & 63) | 128))
                        } else {
                            w += escape(s((z >> 12) | 224) + s(((z >> 6) & 63) | 128) + s((z & 63) | 128))
                        }
                    }
                }
                return w
            }
            window.encodeURIComponent = h;
            window.encodeURI = function(t) {
                return h(t, true)
            }
        }
        if (!window.decodeURIComponent) {
            var o = 6,n = 2,k = 14,d = "malformed URI sequence";
            function m(y) {
                y = unescape(y.toString());
                var t = "",AA = y.length,v = 0,x,z = 0,w = 0,u = 0;
                while (v < AA) {
                    z = y.charCodeAt(v);
                    if (z < 128) {
                        x = s(z);
                        v++
                    } else {
                        if ((z > 191) && (z < 224)) {
                            w = y.charCodeAt(v + 1);
                            if (!((z >> 5) == o && (w >> 6) == n)) {
                                throw d
                            }
                            x = s(((z & 31) << 6) | (w & 63));
                            v += 2
                        } else {
                            w = y.charCodeAt(v + 1);
                            u = y.charCodeAt(v + 2);
                            if (!((z >> 4) == k && (w >> 6) == n && (u >> 6) == n)) {
                                throw d
                            }
                            x = s(((z & 15) << 12) | ((w & 63) << 6) | (u & 63));
                            v += 3
                        }
                    }
                    t += x
                }
                return t
            }
            window.decodeURIComponent = window.decodeURI = m
        }
        if (!window.Node) {
            window.Node = {}
        }
        if (!Node.ELEMENT_NODE) {
            ("ELEMENT,ATTRIBUTE,TEXT,CDATA_SECTION,ENTITY_REFERENCE,ENTITY,PROCESSING_INSTRUCTION,COMMENT,DOCUMENT,DOCUMENT_TYPE,DOCUMENT_FRAGMENT,NOTATION").split(",").forEach(function(u, t) {
                Node[u + "_NODE"] = t + 1
            })
        }
    })();
    var B = (function() {
        var p = {},f = [],AE = false,AH = {},j = /^(\{([^\}]+)\}\.|([^:]+):)?(.+)$/,AG = {},l = "y5",v = [],AC = document.getElementsByTagName("script"),z,h,x;
        function AI(AL) {
            if (typeof AL == "function") {
                AL()
            }
        }
        function e(AL) {
            return !!p[AL]
        }
        function g(AL) {
            return !p[AL]
        }
        function n(AL) {
            return AL.every(e)
        }
        function AB(AN) {
            for (var AM = 0,AL = f.length; AM < AL; AM++) {
                if (AN.indexOf(f[AM]) == 0) {
                    return true
                }
            }
            return false
        }
        function w(AL) {
            p[AL] = 1;
            if (!z) {
                z = B.Events.notify("y5:moduleLoaded", B, false)
            }
            z.dispatch(AL);
            if (AE) {
                AD(AL)
            }
        }
        function q(AL) {
            return !!AH[AL]
        }
        function k(AL) {
            AH[AL] = 1;
            if (!h) {
                h = B.Events.notify("y5:moduleRequired", B, false)
            }
            h.dispatch(AL)
        }
        function d(AL) {
            delete AH[AL]
        }
        function u(AL, AM) {
            AG[AL] = AM;
            if (!x) {
                x = B.Events.notify("y5:namespaceAdded", B, false)
            }
            x.dispatch(AM);
            AF()
        }
        function r(AL) {
            return AG[AL]
        }
        function AJ(AL) {
            var AM = AL.match(j);
            return[AM[3] || AM[2] || l,AM[4]]
        }
        function m(AL) {
            var AM = AJ(AL);
            return AM[0] + ":" + AM[1]
        }
        function AK(AL, AN) {
            var AO = AJ(AL);
            var AM = r(AO[0]);
            if (!AM) {
                return false
            }
            return[(AM.path + AO[1].replace(/\./g, "/") + "." + (AN || "js") + AM.query),AM.charset]
        }
        function i(AM) {
            var AO = 0,AL = AM.length,AN;
            for (; AO < AL; AO++) {
                AN = AM[AO];
                if (e(AN) || q(AN) || AB(AN)) {
                    continue
                }
                var AP = AK(AN);
                if (AP) {
                    k(AN);
                    B.Loader.loadScript(AP[0], AP[1])
                }
            }
        }
        function y(AL, AM) {
            AL = AL.filter(g);
            if (AL.length == 0) {
                AI(AM);
                return false
            }
            v.push({modules:AL,callback:AM});
            return AL
        }
        function AF() {
            for (var AL = 0; AL < v.length; AL++) {
                var AM = v[AL];
                if (n(AM.modules)) {
                    v.splice(AL, 1);
                    AI(AM.callback);
                    AL--
                } else {
                    i(AM.modules)
                }
            }
        }
        function t(AO, AS) {
            for (var AN = 0,AL = AC.length; AN < AL; AN++) {
                var AM = AC[AN],AR = AM.getAttribute("src");
                if (AR && AR.lastIndexOf(AO) >= 0) {
                    var AQ = {path:AR.substring(0, AR.lastIndexOf("/") + 1),charset:AS || AM.getAttribute("charset") || "utf-8"};
                    var AP = AR.lastIndexOf("?");
                    if (AP >= 0) {
                        AQ.query = AR.substring(AP, AR.length)
                    }
                    return AQ
                }
            }
            return null
        }
        function AA(AL, AM, AN) {
            if (typeof AM == "string") {
                AM = {path:AM,charset:AN}
            }
            if (AM.path.lastIndexOf("/") != AM.path.length - 1) {
                AM.path += "/"
            }
            if (!AM.charset) {
                AM.charset = "utf-8"
            }
            if (!AM.query) {
                AM.query = ""
            }
            u(AL, AM)
        }
        function o(AM, AL) {
            try {
                return AG[AM][AL]
            } catch(AN) {
                return null
            }
        }
        function AD(AM) {
            var AN,AL;
            switch (AM) {case"y5:Arrays":AL = Array.prototype;AM = B.Arrays;break;case"y5:Strings":AL = String.prototype;AM = B.Strings;break;case"y5:Dates":AL = Date.prototype;AM = B.Dates;break;default:return }
            if (AM) {
                for (AN in AM) {
                    if (typeof AM[AN] == "function" && !AL[AN]) {
                        AL[AN] = (function(AO, AP) {
                            return function() {
                                var AQ = Array.prototype.slice.call(arguments);
                                AQ.unshift(this);
                                return AP.apply(AO, AQ)
                            }
                        })(AM, AM[AN])
                    }
                }
            }
        }
        var s = {version:1.5,domloaded:false,require:function() {
            var AO = arguments,AN = [],AR = B.VOID,AQ = B.Types;
            for (var AP = 0,AM = AO.length; AP < AM; AP++) {
                var AL = AO[AP];
                switch (AQ.type(AL)) {case AQ.ARRAY:AN = AN.concat(AL);break;case AQ.STRING:AN.push(AL);break;case AQ.FUNCTION:AR = AL;break}
            }
            AN = AN.map(m);
            AN = y(AN, AR);
            if (AN) {
                i(AN)
            }
        },loaded:function(AL) {
            AL = m(AL);
            d(AL);
            w(AL);
            AF()
        },registerNamespace:function(AO, AN, AQ) {
            var AP = null,AL = 0;
            function AM() {
                if (AL < 1000) {
                    var AR = t(AN, AQ);
                    if (AR) {
                        window.clearTimeout(AP);
                        AA(AO, AR);
                        return true
                    }
                    AL++
                }
                return false
            }
            if (!AM()) {
                AP = window.setInterval(AM, 1)
            }
        },registerNamespaceByData:AA,namespacePath:function(AL) {
            return o(AL, "path")
        },namespaceCharset:function(AL) {
            return o(AL, "charset")
        },moduleURL:function(AL, AM) {
            return AK(AL, AM)[0]
        },moduleName:m,moduleNamespace:function(AL) {
            return AJ(AL)[0]
        },moduleObject:function(AO) {
            var AP = window;
            var AN = m(AO).split(/[:\.]/g);
            for (var AM = 0,AL = AN.length; AM < AL; AM++) {
                AP = AP[AN[AM]];
                if (typeof AP == B.UNDEF) {
                    return null
                }
            }
            return AP
        },blockLoad:function(AM, AL) {
            if (AM.indexOf(":") != (AM.length - 1)) {
                AM = m(AM);
                if (!AL) {
                    AM += "."
                }
            }
            if (AM && f.indexOf(AM) == -1) {
                f.push(AM);
                return true
            }
            return false
        },unblockLoad:function(AM) {
            if (AM.indexOf(":") != (AM.length - 1)) {
                AM = m(AM)
            }
            if (AM) {
                var AL = f.indexOf(AM);
                if (AL == -1) {
                    AL = f.indexOf(AM + ".")
                }
                if (AL != -1) {
                    f.splice(AL, 1);
                    return true
                }
            }
            return false
        },extend:function() {
            AE = true;
            AD("y5:Arrays");
            AD("y5:Dates");
            AD("y5:Strings")
        }};
        s.getBase = t;
        s.getBaseAndSetAlias = s.registerNamespace;
        s.setAlias = AA;
        s.constructURL = s.moduleURL;
        s.getAlias = s.moduleNamespace;
        s.charsets = {};
        s.setAliasCharset = function() {
        };
        return s
    })();
    B.Vars = {DEBUG:false,UNDEF:"undefined",FALSE:function() {
        return false
    },TRUE:function() {
        return true
    },NULL:function() {
        return null
    },VOID:function() {
    }};
    B.Browser = {get:function(g) {
        var f = g.userAgent.toLowerCase(),d = {};
        function h(j, i) {
            if (j) {
                var k = f.match(i);
                return k ? parseFloat(k[1]) : 0
            }
            return 0
        }
        function e(i) {
            return f.indexOf(i) != -1
        }
        d.is_win = e("windows");
        d.is_mac = e("mac");
        d.is_linux = e("linux");
        d.is_safari = e("safari");
        d.is_iphone = d.is_safari && e("iphone");
        d.is_opera = e("opera");
        d.is_konq = e("konqueror");
        d.is_ie = !d.is_opera && e("msie");
        d.is_khtml = !d.is_safari && e("khtml");
        d.is_gecko = e("gecko/");
        d.ie_ver = h(d.is_ie, /msie (\d+\.\d)/);
        d.gecko_ver = h(d.is_gecko, /rv:(\d+\.\d)/);
        d.opera_ver = h(d.is_opera, /opera[\/ ](\d+\.\d)/);
        d.safari_ver = h(d.is_safari, /safari\/(\d+)/);
        d.is_ie5 = d.ie_ver == 5;
        d.is_ie55 = d.ie_ver == 5.5;
        d.is_ie5up = d.ie_ver > 4.9;
        d.is_ie55up = d.ie_ver > 5.4;
        d.is_ie6up = d.ie_ver > 5.9;
        d.is_ie7up = d.ie_ver > 6.9;
        d.is_ie6down = d.is_ie && d.ie_ver < 6;
        d.is_ie7down = d.is_ie && d.ie_ver < 7;
        d.cookieEnabled = g.cookieEnabled;
        return d
    }};
    (function() {
        var e;
        var d = B.Browser.get(window.navigator);
        for (e in d) {
            B.Vars[e] = d[e]
        }
        for (e in B.Vars) {
            B[e] = B.Vars[e]
        }
    })();
    B.Loader = (function() {
        function e(j, i) {
            for (var k in i) {
                var l = i[k];
                if (l) {
                    j.setAttribute(k, l)
                }
            }
        }
        function h(i, j) {
            i.insertBefore(j, i.firstChild)
        }
        var g;
        if (B.is_opera && B.opera_ver < 8) {
            g = function(j, i) {
                if (!document.body) {
                    return null
                }
                var l = document.createElement("span");
                l.style.display = "none";
                l.innerHTML = "<" + j + "></" + j + ">";
                var k = l.getElementsByTagName(j).item(0);
                e(k, i);
                h(document.body, l);
                return k
            }
        } else {
            var f = document.getElementsByTagName("head")[0];
            g = function(j, i) {
                var k = document.createElement(j);
                e(k, i);
                h(f, k);
                return k
            }
        }
        function d(j, m) {
            var k = 10,l = null;
            function i() {
                if (!--k) {
                    window.clearTimeout(l);
                    return false
                }
                var n = g("script", j);
                if (n) {
                    if (typeof m == "function") {
                        m(n)
                    }
                    window.clearTimeout(l);
                    return true
                }
                return false
            }
            if (!i()) {
                l = window.setInterval(i, 10)
            }
        }
        return{loadScript:function(i, l, k, j) {
            d({src:i,charset:l,type:"text/javascript",id:j}, k)
        },loadObject:g}
    })();
    B.Scripts = {createScript:function(d, f, e) {
        B.Loader.loadScript(d, f, e)
    }};
    (function() {
        var d = B.VOID;
        B.Console = {log:d,info:d,warn:d,error:d,trace:d,dir:d,dirxml:d,group:d,groupEnd:d}
    })();
    B.Exception = function(g, i, f) {
        if (!B.DEBUG) {
            return true
        }
        var d = "y5." + f + "." + i + ": " + g;
        var h = new Error(d);
        if (h.stack) {
            h.message += "\nStack:\t" + h.stack.replace(/\n/ig, "\n\t")
        }
        return h
    };
    B.Exception.prototype = new Error();
    (function() {
        var e = B.UNDEF;
        function d(g, f) {
            return(g && g.nodeType && g.nodeType == f) || false
        }
        B.Types = {UNDEF:1 << 0,UNDEFINED:1 << 0,OBJECT:1 << 1,FUNCTION:1 << 2,NUMBER:1 << 3,STRING:1 << 4,BOOLEAN:1 << 5,DATE:1 << 10,REGEXP:1 << 11,ARRAY:1 << 12,NULL:1 << 13,EVENT:1 << 14,NODE:1 << 15,TYPES:{"undefined":1 << 0,object:1 << 1,"function":1 << 2,number:1 << 3,string:1 << 4,"boolean":1 << 5},type:function(g) {
            var f = this.TYPES[typeof g];
            if (g === null) {
                return this.NULL
            }
            if (f == this.OBJECT) {
                if (g.nodeName || this.document(g)) {
                    return this.NODE
                }
            }
            if (f == this.OBJECT || f == this.FUNCTION) {
                switch (g.constructor) {case Array:return this.ARRAY;case RegExp:return this.REGEXP;case Date:return this.DATE}
            }
            if (this.event(g)) {
                return this.EVENT
            }
            return f
        },test:function(g, f) {
            return !!(this.type(g) & f)
        },def:function(f) {
            return typeof f != e
        },undef:function(f) {
            return typeof f == e
        },object:function(f) {
            return typeof f == "object"
        },func:function(f) {
            return typeof f == "function"
        },number:function(f) {
            return typeof f == "number"
        },string:function(f) {
            return typeof f == "string"
        },bool:function(f) {
            return typeof f == "boolean"
        },nul:function(f) {
            return f === null
        },array:function(f) {
            return f instanceof Array
        },regexp:function(f) {
            return f instanceof RegExp
        },date:function(f) {
            return f instanceof Date
        },event:function(f) {
            return f && typeof f.type != e && typeof (f.stopPropagation || f.cancelBubble) != e
        },element:function(f) {
            return d(f, Node.ELEMENT_NODE)
        },attribute:function(f) {
            return d(f, Node.ATTRIBUTE_NODE)
        },text:function(f) {
            return d(f, Node.TEXT_NODE)
        },document:function(f) {
            return(f && typeof f.documentElement != e) || false
        },comment:function(f) {
            return d(f, Node.COMMENT_NODE)
        },node:function(f) {
            return(f && typeof f.nodeType != e)
        }}
    })();
    B.GC = {data:[],collect:function(d) {
        this.data.push(d);
        return d
    },remove:function(e) {
        var d = this.data.indexOf(e);
        if (d != -1) {
            this.destruct(d);
            this.data.splice(d, 1)
        }
    },destruct:function(d) {
        var e = this.data[d];
        if (e) {
            if (typeof e.cleanup == "function") {
                e.cleanup()
            } else {
                if (typeof e.destruct == "function") {
                    e.destruct()
                }
            }
        }
        this.data[d] = null
    },cleanup:function() {
        for (var d = this.data.length - 1; d >= 0; d--) {
            this.destruct(d)
        }
        this.data.length = 0;
        if (B.is_ie && CollectGarbage) {
            CollectGarbage()
        }
    }};
    (function() {
        var s = B.Types,q = B.UNDEF,w = B.VOID,r = B.GC;
        function p(AA, z, AC, AB) {
            return(z ? AA.call(z, AC, AB) : AA(AC, AB))
        }
        var u = "DOMAttrModified",f = "propertychange",j = "DOMMouseScroll",t = "mousewheel",d = {};
        if (document.attachEvent) {
            d[u] = f
        } else {
            d[f] = u
        }
        if (B.is_ie || B.is_opera || B.is_safari) {
            d[j] = t
        } else {
            d[t] = j
        }
        function m(z) {
            return d[z] || z
        }
        var x,v = {L:[0,65535],M:[1],R:[2]};
        if (B.is_ie) {
            function n() {
                this.returnValue = false
            }
            function h() {
                this.cancelBubble = true
            }
            x = function(AB) {
                AB.timeStamp = new Date().getTime();
                AB.charCode = AB.type == "keypress" ? AB.keyCode : 0;
                AB.isChar = AB.charCode > 0;
                AB.target = AB.srcElement;
                AB.metaKey = AB.altKey;
                AB.attrName = AB.propertyName == "className" ? "class" : AB.propertyName;
                AB.preventDefault = n;
                AB.stopPropagation = h;
                var AA = document.documentElement,z = document.body;
                AB.pageX = AB.clientX + (AA.scrollLeft || z.scrollLeft);
                AB.pageY = AB.clientY + (AA.scrollTop || z.scrollTop);
                switch (AB.type) {case"mouseout":AB.relatedTarget = AB.toElement;break;case"mouseover":AB.relatedTarget = AB.fromElement;break}
                AB.scrollDetail = 0;
                if (AB.wheelDelta) {
                    AB.scrollDetail = -AB.wheelDelta / 40
                }
            }
        } else {
            if (B.is_safari) {
                v = {L:[0,65535,1],M:[2],R:[3]};
                x = function(z) {
                    if (!s.func(z.preventDefault)) {
                        z.preventDefault = w
                    }
                    if (!s.func(z.stopPropagation)) {
                        z.stopPropagation = w
                    }
                    if (z.target && (z.target.nodeType == 3 || z.target.nodeType == 4)) {
                        z.target = z.target.parentNode
                    }
                    if (z.wheelDelta) {
                        z.scrollDetail = -z.wheelDelta / 400
                    }
                }
            } else {
                if (B.is_opera) {
                    x = function(z) {
                        z.scrollDetail = 0;
                        if (z.wheelDelta) {
                            z.scrollDetail = z.wheelDelta / 40
                        }
                        if (B.opera_ver >= 9.2) {
                            z.scrollDetail *= -1
                        }
                    };
                    if (B.opera_ver < 8) {
                        v = {L:[1],M:[2],R:[3]}
                    }
                } else {
                    x = function(z) {
                        try {
                            z.scrollDetail = z.detail
                        } catch(z) {
                        }
                    }
                }
            }
        }
        if (B.is_ie || B.is_konq) {
            v = {L:[1],M:[4],R:[2]}
        }
        function k(AA) {
            var z;
            try {
                z = AA.button
            } catch(AA) {
            }
            if (typeof z != q) {
                AA.buttonL = v.L.indexOf(z) != -1;
                AA.buttonM = v.M.indexOf(z) != -1;
                AA.buttonR = v.R.indexOf(z) != -1
            } else {
                AA.buttonL = AA.buttonM = AA.buttonR = false
            }
            x(AA);
            return AA
        }
        B.HandleEvent = {normalizeEvent:k};
        B.AEventListener = function(AC, AD, AB, AE, AA, z) {
            this.type = m(AC);
            this.element = AB || document;
            this.blocked = false;
            this.added = false;
            this.runOnce = z || false;
            var AF = this;
            this.listener = function(AG) {
                var AH = typeof AG != q ? k(AG) : {};
                if (AF.blocked) {
                    AH.stopPropagation();
                    AH.preventDefault();
                    return
                }
                p(AD, AA, AH, AF.element);
                if (AF.runOnce) {
                    AF.cleanup()
                }
            };
            if (AE) {
                this.add()
            }
            r.collect(this)
        };
        B.AEventListener.prototype = {add:function() {
            if (this.added) {
                return
            }
            this._add();
            this.added = true
        },_add:function() {
            this.element.addEventListener(this.type, this.listener, false)
        },remove:function() {
            if (!this.added) {
                return
            }
            this._remove();
            this.added = false
        },_remove:function() {
            this.element.removeEventListener(this.type, this.listener, false)
        },block:function() {
            this.blocked = true
        },unblock:function() {
            this.blocked = false
        },cleanup:function() {
            this.remove();
            this.element = null;
            this.listener = null
        }};
        var o = B.AEventListener;
        if (document.attachEvent) {
            o.prototype._add = function() {
                this.element.attachEvent("on" + this.type, this.listener)
            };
            o.prototype._remove = function() {
                this.element.detachEvent("on" + this.type, this.listener)
            }
        }
        var i = {Mouse:/^mouse|click/,Key:/^key/,Mutation:/^DOM/,HTML:/./},g = [0,1,2];
        if (B.is_safari) {
            g = [0,2,3]
        }
        if (B.is_safari || B.is_opera) {
            delete i.Key
        }
        B.Event = function(AB, AA, z, AC) {
            this.type = m(AB || "click");
            this.element = AA || document;
            this.params = AC || {};
            this.init();
            if (typeof z == q) {
                z = true
            }
            if (z) {
                this.dispatch()
            }
            r.collect(this)
        };
        B.Event.prototype = {init:function() {
            for (var z in i) {
                if (i[z].test(this.type)) {
                    this.eventType = z;
                    break
                }
            }
        },dispatch:function(AC, AB, z) {
            if (typeof AC == q) {
                AC = this.params
            }
            AB = AB || true;
            z = z || true;
            var AA = document.createEvent(this.eventType + "Events");
            switch (this.eventType) {case"Mouse":AA.initMouseEvent(this.type, AB, z, document.defaultView, AC.detail || 0, AC.screenX || 0, AC.screenY || 0, AC.clientX || 0, AC.clientY || 0, AC.ctrlKey || false, AC.altKey || false, AC.shiftKey || false, AC.metaKey || false, g[AC.button || 0], null);break;case"Key":AA.initKeyEvent(this.type, AB, z, document.defaultView, AC.ctrlKey || false, AC.altKey || false, AC.shiftKey || false, AC.metaKey || false, AC.keyCode || 0, AC.charCode || 0);break;default:AA.initEvent(this.type, AB, z);break}
            return this.element.dispatchEvent(AA)
        },cleanup:function() {
            this.element = null
        }};
        var y = B.Event;
        if (document.createEventObject) {
            g = [1,4,2];
            function e(AA, AD) {
                for (var z in AD) {
                    try {
                        var AB;
                        switch (z) {case"button":AB = g[AD.button || 0];break;default:AB = AD[z];break}
                        AA[z] = AB
                    } catch(AC) {
                    }
                }
                return AA
            }
            y.prototype.init = w;
            y.prototype.dispatch = function(AA) {
                if (typeof AA == q) {
                    AA = this.params
                }
                var z = e(document.createEventObject(), AA);
                return this.element.fireEvent("on" + this.type, z)
            }
        } else {
            if (!document.createEvent) {
                y.prototype.init = w;
                y.prototype.dispatch = function(AA) {
                    try {
                        return this.element[this.type](AA)
                    } catch(z) {
                        B.Console.warn("Browser is too old", ["Event"]);
                        return false
                    }
                }
            }
        }
        y.prototype.dispatchEvent = y.prototype.dispatch;
        (function() {
            var AA = {notifiers:{},observers:{},dispatchNotify:function(AB, AE, AC) {
                var AJ = true,AG = this.observers[AB];
                if (AG) {
                    var AI = [].concat(AG),AF = 0,AD = AI.length;
                    for (; AF < AD; AF++) {
                        if (!AI[AF].added) {
                            continue
                        }
                        AJ &= this.runListener(AI[AF], AE);
                        if (AI[AF].runOnce) {
                            AI[AF].cleanup()
                        }
                    }
                }
                if (AC) {
                    var AH = this.notifiers;
                    if (!AH[AB]) {
                        AH[AB] = []
                    }
                    AH[AB].push(AE)
                }
                return AJ
            },runListener:function(AC, AD) {
                var AB = true;
                AB &= p(AC.listener, AC.context, AD);
                B.Console.log("Observer listener: ", AC, ["Notifier"]);
                return AB
            },addObserver:function(AB) {
                var AD = AB.id,AC = this.observers;
                if (!AC[AD]) {
                    AC[AD] = []
                }
                AC[AD].push(AB);
                this.fireNotify(AB)
            },removeObserver:function(AB) {
                var AF = this.observers[AB.id],AC;
                if (AF) {
                    for (var AD = 0,AE = AF.length; AD < AE; AD++) {
                        if (AF[AD] === AB) {
                            AF.splice(AD, 1);
                            break
                        }
                    }
                }
            },fireNotify:function(AD) {
                var AC = this.notifiers[AD.id];
                if (AC) {
                    for (var AE = 0,AB = AC.length; AE < AB; AE++) {
                        this.runListener(AD, AC[AE])
                    }
                }
            },generateId:function(AC, AB) {
                var AD = AB;
                if (!AB || typeof AB == "object") {
                    AD = B.Utils.getUniqueId(AB || B)
                }
                return AC + "_" + AD
            }};
            B.Observer = function(AE, AF, AD, AG, AC, AB) {
                this.id = AA.generateId(AE, AD);
                this.added = false;
                this.listener = AF;
                this.context = AC;
                this.runOnce = AB || false;
                if (AG) {
                    this.add()
                }
                r.collect(this)
            };
            B.Observer.prototype = {add:function() {
                if (!this.added) {
                    AA.addObserver(this);
                    this.added = true;
                    B.Console.log("Observer added: ", this, ["Observer"])
                }
            },remove:function() {
                if (this.added) {
                    AA.removeObserver(this);
                    this.added = false;
                    B.Console.log("Observer removed: ", this, ["Observer"])
                }
            },cleanup:function() {
                this.remove();
                this.context = null;
                this.listener = null
            }};
            var z = B.Observer.prototype;
            z.start = z.add;
            z.stop = z.remove;
            B.Notifier = function(AD, AC, AB, AE) {
                this.id = AA.generateId(AD, AC);
                this.params = AE;
                B.Console.log("Notifier new: ", this, ["Notifier"]);
                if (typeof AB == q) {
                    AB = true
                }
                if (AB) {
                    this.dispatch()
                }
            };
            B.Notifier.prototype = {dispatch:function(AD, AC) {
                if (typeof AD == q) {
                    AD = this.params
                }
                B.Console.group("Notifier dispatch: ", [this,AD], ["Notifier"]);
                var AB = AA.dispatchNotify(this.id, AD, AC);
                B.Console.groupEnd();
                return AB
            }};
            B.Notify = function(AD, AC, AF, AE) {
                B.Console.group("Notifier dispatch: ", [this,AF], ["Notifier"]);
                var AG = AA.generateId(AD, AC);
                var AB = AA.dispatchNotify(AG, AF, AE);
                B.Console.groupEnd();
                return AB
            }
        })();
        B.Events = {isCustom:function(z) {
            return z.indexOf(":") != -1
        },observe:function(AE, AB, AD, AG, z, AF) {
            if (!AD) {
                return{add:w,remove:w}
            }
            switch (s.type(AE)) {case s.ARRAY:var AA = AE.length,AH = new Array(AA),AC = 0;for (; AC < AA; AC++) {
                AH[AC] = this.observeEvent(AE[AC], AB, AD, AG, z, AF)
            }return AH;case s.STRING:return this.observeEvent(AE, AB, AD, AG, z, AF)}
            return null
        },observeOnce:function(AB, AC, AA, AD, z) {
            return this.observe(AB, AC, AA, AD, z, true)
        },notify:function(AB, AA, z, AC, AE) {
            var AD = this.isCustom(AB) ? B.Notifier : B.Event;
            return new AD(AB, AA, z, AC, AE)
        },observeProperty:function(AE, AD, AC, AF, AB, z) {
            function AA(AG) {
                if (AG.attrName == AE) {
                    p(AD, AB, AG, AC)
                }
            }
            return new o(u, AA, AC, AF, null, z)
        },observePropertyOnce:function(AD, AC, AB, AE, AA) {
            function z(AF) {
                if (AF.attrName == AD) {
                    p(AC, AA, AF, AB)
                }
            }
            return new o(u, z, AB, AE, null, true)
        },observeEvent:function(AC, AD, AB, AF, AA, z) {
            var AE = this.isCustom(AC) ? B.Observer : o;
            return new AE(AC, AD, AB, AF, AA, z)
        }};
        var l = B.Events;
        l.create = l.observe;
        l.make = l.notify;
        l.PropertyListener = l.observeProperty;
        (function() {
            var AF,AA = "load";
            function AB() {
                if (B.domloaded) {
                    return
                }
                B.domloaded = true;
                if (AF) {
                    window.clearInterval(AF)
                }
                AB = w;
                B.Notify("dom:loaded", B, null, true)
            }
            if (document.addEventListener) {
                if (B.is_safari || B.is_khtml) {
                    var AC = /loaded|complete/;
                    AF = window.setInterval(function() {
                        if (AC.test(document.readyState)) {
                            AB()
                        }
                    }, 0)
                } else {
                    if (B.is_opera && B.opera_ver < 9) {
                    } else {
                        AA = "DOMContentLoaded"
                    }
                }
            } else {
                var AD = document;
                function z(AG) {
                    return typeof AD[AG] != "undefined"
                }
                function AE() {
                    if (AD.body !== null && AD.getElementsByTagName) {
                        if (z("readyState") && (/loaded|complete/).test(AD.readyState)) {
                            AB()
                        }
                        if (z("fileSize")) {
                            try {
                                AD.documentElement.doScroll("left");
                                AB()
                            } catch(AG) {
                            }
                        }
                    }
                }
                AF = window.setInterval(AE, 10)
            }
            B.Events.observe(AA, AB, window, true)
        })();
        if (!(B.is_gecko && B.gecko_ver < 1.8 || B.is_opera)) {
            new o("unload", r.cleanup, window, true, r)
        }
    })();
    (function() {
        var e = B.Types,d = e.NODE | e.BOOLEAN | e.NUMBER | e.STRING | e.EVENT | e.REGEXP | e.FUNCTION | e.NULL;
        B.Utils = {counterId:0,generateId:function(f) {
            return(f || "") + ((new Date()).getTime() + Math.round(Math.random() * 10000))
        },generateUniqueId:function() {
            return"y5__id" + (++this.counterId)
        },getUniqueId:function(f) {
            if (f === document) {
                return this.documentID
            }
            return f.uniqueID || this.setUniqueId(f)
        },setUniqueId:function(f, g) {
            if (typeof g == B.UNDEF) {
                g = this.generateUniqueId()
            }
            return(f.uniqueID = g)
        },isEqual:function(g, f) {
            return this.getUniqueId(g) == this.getUniqueId(f)
        },fakeFrame:{frame:null,transparentFrame:null,init:function(f) {
            if (!B.is_ie7down) {
                return
            }
            this.frame = B.Dom.$("fakeFrame");
            if (!this.frame) {
                this.create(f)
            }
            return[this.frame,this.transparentFrame]
        },create:function(f) {
            this.transparentFrame = document.createElement('<iframe id="fakeTransparentFrame" src="about:blank" frameborder="0" tabindex="-1" style="filter:Alpha(opacity=1); position: absolute;">');
            this.frame = document.createElement('<iframe id="fakeFrame" src="about:blank" frameborder="0" tabindex="-1" allowtransparency="true" style="FILTER: chroma(color=#FFFFFF); position: absolute;">');
            this.transparentFrame.style.zIndex = B.Elements.getPropertyValue(f, "z-index") - 2;
            this.frame.style.zIndex = B.Elements.getPropertyValue(f, "z-index") - 1;
            this.frame.style.display = this.transparentFrame.style.display = "none";
            B.Dom.getBody().appendChild(this.transparentFrame);
            B.Dom.getBody().appendChild(this.frame)
        },adjust:function(f) {
            if (!this.frame) {
                return
            }
            if (f.offsetHeight > 0) {
                if (this.transparentFrame.style.zIndex == -2) {
                    this.transparentFrame.style.zIndex = B.Elements.getPropertyValue(f, "z-index") - 2;
                    this.frame.style.zIndex = B.Elements.getPropertyValue(f, "z-index") - 1
                }
                this.frame.style.width = this.transparentFrame.style.width = f.offsetWidth + "px";
                this.frame.style.height = this.transparentFrame.style.height = f.offsetHeight + "px";
                this.frame.style.left = this.transparentFrame.style.left = f.offsetLeft + "px";
                this.frame.style.top = this.transparentFrame.style.top = f.offsetTop + "px"
            }
            this.frame.style.display = this.transparentFrame.style.display = f.style.display
        }},hexDigit:"0123456789ABCDEF".split(""),dec2hex:function(f) {
            return this.hexDigit[f >> 4] + this.hexDigit[f & 15]
        },hex2dec:function(f) {
            return parseInt(f, 16)
        },objectCopy:function(g) {
            var h,f = {},i = arguments.length;
            for (h in g) {
                f[h] = g[h]
            }
            if (i == 1) {
                return f
            } else {
                if (i == 2) {
                    var k = arguments[1],l,j;
                    for (h in k) {
                        l = k[h];
                        j = {};
                        if (e.test(l, d)) {
                            j = l
                        } else {
                            if (e.date(l)) {
                                j = new Date(l)
                            } else {
                                if (e.array(l)) {
                                    j = [].concat(l)
                                } else {
                                    if (e.def(f[h])) {
                                        j = f[h]
                                    }
                                    j = this.objectCopy(j, l)
                                }
                            }
                        }
                        f[h] = j
                    }
                } else {
                    h = 1;
                    for (; h < i; h++) {
                        f = this.objectCopy(f, arguments[h])
                    }
                }
            }
            return f
        },objectExtends:function(g, j, h) {
            var i;
            h = h || j.toString().match(/function\s*([^\(]+)\(/)[1];
            var f = {};
            for (i in g.prototype) {
                f[i] = g.prototype[i]
            }
            g.prototype[h] = j;
            for (i in j.prototype) {
                g.prototype[i] = j.prototype[i]
            }
            for (i in f) {
                if (e.object(g.prototype[i])) {
                    g.prototype[i] = this.objectCopy(g.prototype[i], f[i])
                } else {
                    g.prototype[i] = f[i]
                }
            }
        },setTimeout:function(m, n, j) {
            var g = [];
            for (var h = 3,f = arguments.length; h < f; h++) {
                g.push(arguments[h])
            }
            function k() {
                m.apply(j, g)
            }
            return window.setTimeout(k, n)
        },clearTimeout:function(f) {
            window.clearTimeout(f)
        },formatNumber:function(l, n, f) {
            l = parseFloat(l.toString(), 10);
            if (isNaN(l)) {
                return
            }
            n = e.string(n) ? n : " ";
            f = f ? f : ".";
            var j = l < 0 ? "-" : "",p = l.toString(),m = p.indexOf("."),h = 0;
            if (m != -1) {
                h = p.substr(m + 1)
            }
            l = Math.floor(Math.abs(l)).toString();
            var o = l.length % 3,q = l.substr(0, o),g = Math.floor(l.length / 3),k = 0;
            for (; k < g; k++) {
                q += n + l.substr(3 * k + o, 3)
            }
            if (o == 0) {
                q = q.substr(1)
            }
            if (h) {
                q += f + h
            }
            return j + q
        }};
        B.Utils.documentID = B.Utils.generateId("y5__");
        B.Utils.getUniqueID = B.Utils.getUniqueId;
        B.loaded("Utils")
    })();
    B.registerNamespace("y5", "y5.js");
    B.loaded("Types");
    B.loaded("Events");
    if (/y5debug/.test(location.search + document.cookie)) {
        B.require("Debug")
    }
    (function() {
        var q = B.UNDEF,u = B.Types,r = /^((((\w+):)\/\/)(([\w\-\.]+)(\:(\d+))?))?(\/?[^\?#]*)?(\?([^#]*))?(#(.*))?$/,x = /^(([\w]+:)?\/\/)?(([\d\w]|%[a-fA-f\d]{2,2})+(:([\d\w]|%[a-fA-f\d]{2,2})+)?@)?([\d\w][-\d\w]{0,253}[\d\w]\.)+[\w]{2,4}(:[\d]+)?(\/([-+_~.\d\w]|%[a-fA-f\d]{2,2})*)*(\?(&?([-+_~.\d\w]|%[a-fA-f\d]{2,2})=?)*)?(#([-+_~.\d\w]|%[a-fA-f\d]{2,2})*)?$/,n = "E9F6F3EAE5EDE3F8F9E7F5FAF4FBE2E0EFF0EEEBE4E6FDFFF7F1ECE8F2FCE1FEB8C9D6D3CAC5CDC3D8D9C7D5DAD4DBC2C0CFD0CECBC4C6DDDFD7D1CCC8D2DCC1DEA8".match(/../g),w = "%D0%B9%D1%86%D1%83%D0%BA%D0%B5%D0%BD%D0%B3%D1%88%D1%89%D0%B7%D1%85%D1%8A%D1%84%D1%8B%D0%B2%D0%B0%D0%BF%D1%80%D0%BE%D0%BB%D0%B4%D0%B6%D1%8D%D1%8F%D1%87%D1%81%D0%BC%D0%B8%D1%82%D1%8C%D0%B1%D1%8E%D1%91%D0%99%D0%A6%D0%A3%D0%9A%D0%95%D0%9D%D0%93%D0%A8%D0%A9%D0%97%D0%A5%D0%AA%D0%A4%D0%AB%D0%92%D0%90%D0%9F%D0%A0%D0%9E%D0%9B%D0%94%D0%96%D0%AD%D0%AF%D0%A7%D0%A1%D0%9C%D0%98%D0%A2%D0%AC%D0%91%D0%AE%D0%81".match(/.{6}/g),p = {},g = encodeURIComponent,v = 0,s = n.length;
        for (; v < s; v++) {
            p[n[v]] = w[v]
        }
        function e(i, l) {
            return p[l] || i
        }
        function d(i) {
            return i.replace(/%([A-Fa-f0-9]{2})/g, e)
        }
        function o(i) {
            var z = i.replace(/\+/g, "%20");
            try {
                var y = decodeURIComponent(z);
                if (y == undefined) {
                    throw"malformed URI sequence"
                }
                return y
            } catch(l) {
                try {
                    return decodeURIComponent(d(z))
                } catch(l) {
                    return unescape(z)
                }
            }
        }
        function h(i, l, y) {
            if (u.undef(i[l])) {
                i[l] = []
            }
            if (u.array(y)) {
                i[l] = i[l].concat(y)
            } else {
                i[l].push(y)
            }
        }
        function f(AA, AB, AD) {
            var AE = AA[AB],z = AE.length,y = new Array(z),l = AB,AC = 0;
            if (AD) {
                l = g(AB)
            }
            for (; AC < z; AC++) {
                if (AD) {
                    y[AC] = l + "=" + g(AE[AC])
                } else {
                    y[AC] = l + "=" + AE[AC]
                }
            }
            return y
        }
        function k(AF) {
            var AC = {},AD = AF.replace(/\+/g, "%20").split("&"),AE,z,AB,AA = 0,y = AD.length;
            for (; AA < y; AA++) {
                AE = AD[AA].split("=");
                z = AE.shift();
                if (z) {
                    AB = AE.length > 1 ? AE.join("=") : (AE[0] || "");
                    h(AC, o(z), o(AB))
                }
            }
            return AC
        }
        B.URL = function(i) {
            i = u.def(i) ? i.toString() : window.location.href;
            var l = i.match(r);
            if (!l) {
                throw new B.Exception("This is not an url", "constructor", "URL")
            }
            this.Href = l[0];
            this.Proto = l[4] || "";
            this.Host = l[6] || "";
            this.Port = l[8] || 0;
            this.Path = o(l[9] || "");
            this.Query = k(l[11] || "");
            this.Hash = o(l[13] || "")
        };
        B.URL.prototype = {go:function() {
            window.location.href = this.toString()
        },toString:function() {
            var i = "";
            if (this.Proto || this.Host) {
                if (this.Host) {
                    i += (this.Proto || "http") + "://" + this.Host;
                    if (this.Port) {
                        i += ":" + this.Port
                    }
                }
            }
            if (this.Path) {
                if (this.Host && this.Path.indexOf("/") != 0) {
                    i += "/"
                }
                i += encodeURI(this.Path)
            }
            var l = this.query(undefined, true);
            if (l) {
                i += "?" + l
            }
            if (this.Hash) {
                i += "#" + g(this.Hash)
            }
            return i
        },clone:function() {
            return new B.URL(this.toString())
        },proto:function() {
        },host:function() {
        },port:function() {
        },path:function() {
        },hash:function() {
        },query:function(z, y) {
            if (u.def(z)) {
                this.clearQuery();
                if (u.string(z)) {
                    this.Query = k(z)
                } else {
                    this.replaceParams(z)
                }
                return this
            }
            var i = [];
            for (var l in this.Query) {
                i = i.concat(f(this.Query, l, y))
            }
            return i.join("&")
        },queryKeys:function() {
            var i = [];
            for (var l in this.Query) {
                i.push(l)
            }
            return i.sort()
        },addParam:function(i, l) {
            h(this.Query, i, l);
            return this
        },addParams:function(l) {
            for (var i in l) {
                h(this.Query, i, l[i])
            }
            return this
        },removeParams:function(AA) {
            for (var z = 0,y = AA.length; z < y; z++) {
                delete this.Query[AA[z]]
            }
            return this
        },replaceParams:function(y) {
            var l = [];
            for (var i in y) {
                l.push(i)
            }
            this.removeParams(l);
            this.addParams(y);
            return this
        },clearQuery:function() {
            this.Query = {};
            return this
        },getParam:function(i) {
            var l = this.Query[i];
            return l ? l[0] : null
        },getParams:function(i) {
            return this.Query[i] || []
        }};
        var j = B.URL,t = j.prototype;
        var m = {proto:"Proto",host:"Host",port:"Port",path:"Path",hash:"Hash"};
        for (v in m) {
            t[v] = (function(i) {
                return function(l) {
                    if (u.def(l)) {
                        this[i] = l;
                        return this
                    }
                    return this[i]
                }
            })(m[v])
        }
        t.get = t.toString;
        B.Url = function(i) {
            return new j(i)
        };
        j.isValid = function(i) {
            return x.test(i)
        };
        B.loaded("URL")
    })();
    B.Cache = function() {
        this.data = {}
    };
    B.Cache.prototype = {get:function(d) {
        return this.data[d]
    },set:function(d, e) {
        return this.data[d] = e
    },test:function(d) {
        return typeof this.data[d] != B.UNDEF
    },empty:function(d) {
        return typeof this.data[d] == B.UNDEF
    },remove:function(d) {
        delete this.data[d]
    }};
    B.loaded("Cache");
    (function() {
        var v = "",i = " ",o = /(^[\s\xA0]+|[\s\xA0]+$)/g,d = /^[\s\xA0]*$/,e = /[\s\xA0]{2,}/g,q = /([\|\!\[\]\^\$\(\)\{\}\+\=\?\.\*\\])/g,p = /(<([^>]+)>)/ig,u = /\r\n|\r|\n/g,j = /[^\s\xA0]+/g,r = /[&<>\"\']/g,f = function(w) {
            return"&#" + w.charCodeAt(0) + ";"
        },t = /(&(lt|gt|quot|apos|amp|#\d+);|.)/gi,l = {lt:"<",gt:">",quot:'"',apos:"'",amp:"&"},m = function(w, y, x) {
            return l[x] || (x ? String.fromCharCode(x.substring(1)) : y)
        },s = /[A-Z]+[a-z]+/g,n = function(w) {
            return"-" + w.toLowerCase()
        },k = function(x, w) {
            if (w != 0) {
                return B.Strings.capitalize(x)
            }
            return x
        };
        function h(AA, y) {
            y = y.toString();
            var x = /^%(0?)(\d+)d$/.exec(AA);
            if (x) {
                var w = x[1] || i,z = parseInt(x[2], 10) - y.length;
                return B.Strings.repeat(w, z) + y
            }
            return y
        }
        B.Strings = {isEmpty:function(w) {
            return(w == v)
        },isVoid:function(w) {
            return(!w || d.test(w))
        },contains:function(y, x, w) {
            if (w) {
                y = y.toUpperCase();
                x = x.toUpperCase()
            }
            return y.indexOf(x) !== -1
        },startsWith:function(y, x, w) {
            if (w) {
                y = y.toUpperCase();
                x = x.toUpperCase()
            }
            return y.indexOf(x) === 0
        },endsWith:function(y, x, w) {
            if (w) {
                y = y.toUpperCase();
                x = x.toUpperCase()
            }
            return y.lastIndexOf(x) + x.length === y.length
        },trim:function(w) {
            return w.replace(o, v)
        },normalizeSpace:function(w) {
            return this.trim(w.replace(e, i))
        },escapeRegexp:function(w) {
            return w.replace(q, "\\$1")
        },getCode:function(w) {
            return String.fromCharCode(w)
        },escapeHTML:function(w) {
            return w.replace(r, f)
        },unescapeHTML:function(w) {
            return w.replace(t, m)
        },stripTags:function(w) {
            return(typeof w == "string" ? w : w.innerHTML).replace(p, v)
        },IoToIe:function(w) {
            return w.replace(/[\u0451\u0401]/g, "\u0435")
        },plural:function(z, x, y) {
            var AA = 2;
            var w = z % 10;
            var AB = z % 100;
            if (z == 0) {
                if (x[3]) {
                    return x[3]
                }
            } else {
                if (AB < 5 || AB > 20) {
                    if (w == 1) {
                        AA = 0
                    } else {
                        if (w >= 2 && w <= 4) {
                            AA = 1
                        }
                    }
                }
            }
            if (y) {
                return x[AA]
            } else {
                return z + i + x[AA]
            }
        },conversion:function(w, x) {
            return this.plural(w, [x[0],x[2],x[1],x[3]])
        },capitalize:function(w) {
            return w.charAt(0).toUpperCase() + w.substr(1).toLowerCase()
        },camelize:function(w) {
            return w.split("-").map(k).join(v)
        },dasherize:function(w) {
            return w.replace(s, n)
        },repeat:function(x, w) {
            if (w < 1) {
                return v
            }
            return(new Array(w + 1)).join(x)
        },nl2br:function(x, w) {
            return x.replace(u, w ? "<br />" : "<br>")
        },text2html:function(w) {
            return this.nl2br(this.escapeHTML(w))
        },words:function(w) {
            return w.match(j) || []
        },wordsCount:function(w) {
            return this.words(w).length
        },printf:function(AD, AC) {
            var x = AC;
            var y = arguments,AB = y.length;
            if (AB > 2) {
                x = [];
                for (var AA = 1; AA < AB; AA++) {
                    x.push(y[AA])
                }
            } else {
                if (typeof AC != "object") {
                    x = [AC]
                }
            }
            var w = 0;
            function z(AE) {
                var AF = x[w];
                w++;
                return h(AE, typeof AF != B.UNDEF ? AF : v)
            }
            return AD.replace(/%(s|\d*d)/g, z).replace(/%%/g, "%")
        },EMPTY:v,SPACE:i,NBSP:"\u00A0"};
        var g = B.Strings;
        g.strip = g.trim;
        g.times = g.repeat;
        g.isBlank = g.isVoid;
        g.normalize = g.normalizeSpace;
        g.stripHTML = g.stripTags
    })();
    B.loaded("Strings");
    B.require(["Cache","Strings"], function() {
        var f = true,j = new B.Cache(),h = new B.Cache(),i = B.Strings,g = function(k) {
            if (typeof k == "string") {
                return k.split(" ")
            }
            if (typeof k.source != B.UNDEF) {
                return[k]
            }
            return k
        },d = function(m, l, k) {
            if (typeof l == B.UNDEF || l == null) {
                throw new B.Exception("object required", m, "Classes")
            }
            if (!k || (typeof k != "string" && !k.source)) {
                throw new B.Exception("class name required", m, "Classes")
            }
        },e = function(m, n) {
            var l = "",k = "",o = "";
            if (typeof m == "string") {
                o = l = i.escapeRegexp(m)
            } else {
                o = m.source;
                l = m.toString();
                k += m.ignoreCase ? "i" : ""
            }
            if (!n && f) {
                if (j.empty(l)) {
                    return j.set(l, new RegExp("(^|\\s+)" + o + "(\\s+|$)", k))
                }
                return j.get(l)
            }
            return new RegExp("(^|\\s+)" + o + "(\\s+|$)", k)
        };
        B.Classes = {test:function(m, k, l) {
            d("test", m, k);
            if (k == "*") {
                return true
            }
            try {
                if (!l && f) {
                    var p = k + " " + m.className;
                    if (h.empty(p)) {
                        return h.set(p, e(k).test(m.className))
                    }
                    return h.get(p)
                }
                return e(k, l).test(m.className)
            } catch(o) {
            }
            return false
        },set:function(l, k) {
            d("set", l, k);
            if (l.className != k) {
                l.className = k;
                return true
            }
            return false
        },add:function(k, m) {
            var l = g(m).filter(function(n) {
                return !this.test(k, n)
            }, this);
            if (l.length) {
                k.className += " " + l.join(" ")
            }
            return l
        },remove:function(l, n) {
            var k = l.className;
            var m = [];
            g(n).forEach(function(o) {
                while (e(o).test(k)) {
                    m.push(o);
                    k = k.replace(e(o), " ")
                }
            });
            l.className = i.normalizeSpace(k);
            return m
        },replace:function(l, m, k) {
            if (this.test(l, m)) {
                l.className = i.normalizeSpace(l.className.replace(e(m, true), "$1" + k + "$2"));
                return true
            }
            return false
        },assign:function(l, k, m) {
            if (m) {
                return this.add(l, k)
            } else {
                return this.remove(l, k)
            }
        },toggle:function(m, k) {
            var l = !this.test(m, k);
            this.assign(m, k, l);
            return l
        },swap:function(m, l, k) {
            if (this.test(m, l)) {
                this.replace(m, l, k);
                return k
            } else {
                if (this.test(m, k)) {
                    this.replace(m, k, l)
                } else {
                    this.add(m, l)
                }
            }
            return l
        }};
        B.loaded("Classes")
    });
    B.require("Strings", function() {
        var f = B.Strings,h = B.Types,i = /\s*;\s*/g,k = /\s*:\s*/,j = /[^\w]/,o = /\d+px/,l = /z-?index|font-?weight|opacity|zoom|line-?height/i;
        function n(p, r, q) {
            p.style[f.camelize(r)] = q;
            return p
        }
        function e(p, q) {
            return B.Elements.getStyle(p).getPropertyValue(f.dasherize(q))
        }
        B.Elements = {create:function(r, p, s) {
            var q;
            if (!h.string(r)) {
                p = r.attributes;
                r = r.tagName
            }
            if (j.test(r)) {
                q = document.createElement("div");
                q.innerHTML = r;
                q = q.firstChild
            } else {
                if (B.is_ie && p && p.name) {
                    q = document.createElement("<" + r + ' name="' + p.name + '"/>');
                    delete p.name
                } else {
                    q = document.createElement(r)
                }
            }
            if (p) {
                this.setAttributes(q, p)
            }
            if (s) {
                this.setHTML(q, s)
            }
            return q
        },setAttributes:function(s, p) {
            if (!p) {
                return
            }
            var q,v,u,w,t,r;
            for (q in p) {
                v = p[q];
                switch (q) {case"style":case"cssText":if (s.style.cssText && !(v.indexOf("opacity") != -1 && B.is_ie)) {
                    s.style.cssText = v
                } else {
                    u = v.split(i);
                    w = u.length;
                    for (r = 0; r < w; r++) {
                        t = u[r].split(k);
                        n(s, t[0], t[1])
                    }
                }break;case"class":case"className":s.className = v;break;case"innerHTML":s.innerHTML = v;break;default:s.setAttribute(q, v)}
            }
        },setHTML:function(q, p) {
            q.innerHTML = p;
            return q
        },css:function(p, s, r) {
            var q = s,t;
            if (h.string(s)) {
                if (h.undef(r)) {
                    r = e(p, s);
                    if (s == "opacity") {
                        return parseFloat(r, 10)
                    }
                    return o.test(r) ? parseInt(r, 10) : r
                } else {
                    q = {};
                    q[s] = r
                }
            }
            for (t in q) {
                n(p, t, h.number(r = q[t]) && !l.test(t) ? r + "px" : r)
            }
        },getStyle:function(p) {
            return document.defaultView.getComputedStyle(p, null)
        }};
        var d = B.Elements;
        d.createElement = d.create;
        d.setElementAttributes = d.setAttributes;
        d.createElementWithName = d.createWithName;
        d.createElementFromHTML = d.create;
        d.createWithName = function(p, q) {
            return d.create(p, {name:q})
        };
        d.createFromHTML = d.create;
        d.getPropertyValue = d.css;
        d.getPropertyValuePx = d.css;
        d.getPropertyValueFloat = d.css;
        d.setPropertyValue = d.css;
        d.setPropertyValuePx = d.css;
        if (h.undef(document.defaultView)) {
            d.getStyle = function(p) {
                return p.currentStyle || p.runtimeStyle
            };
            e = function(q, u) {
                var p = f.camelize(u),r;
                switch (p) {case"opacity":r = 100;try {
                    r = q.filters["DXImageTransform.Microsoft.Alpha"].opacity
                } catch(s) {
                    try {
                        r = q.filters("alpha").opacity
                    } catch(s) {
                    }
                }return(r / 100).toString();case"float":p = "styleFloat";break;case"width":case"height":case"top":case"right":case"bottom":case"left":var t = false;if (!q.style[p]) {
                    q.style[p] = d.getStyle(q)[p];
                    t = true
                }r = q.style["pixel" + f.capitalize(p)];if (t) {
                    q.style[p] = null
                }return r}
                return d.getStyle(q)[p]
            };
            function m(p) {
                return p.replace(/alpha\s*\([^\)]*\)/ig, "")
            }
            var g = n;
            n = function(p, t, s) {
                switch (t) {case"opacity":var r = e(p, "filter");var q = p.style;if (s == 1) {
                    r = m(r);
                    if (r) {
                        q.filter = r
                    } else {
                        q.removeAttribute("filter")
                    }
                    return p
                } else {
                    if (s < 0.00001) {
                        s = 0
                    }
                }if (!q.zoom) {
                    q.zoom = 1
                }q.filter = m(r) + "alpha(opacity=" + (s * 100) + ")";break;default:g(p, t, s)}
                return p
            }
        }
        B.loaded("Elements")
    });
    B.require(["Classes","Strings","Elements"], function() {
        var u = B.Types,m = B.Classes,l = B.Strings,z = B.Elements,r = B.UNDEF,k = "*",w = "parentNode",s = "previousSibling",g = "nextSibling";
        function d(i) {
            return document.getElementById(i)
        }
        function t(AF, AA, AE, AD) {
            AA = AA || B.Dom.getBody();
            var AC = z.create("span", {style:"position:absolute;display:block;visibility:hidden;width:100" + AD});
            var AB = AA.appendChild(AC);
            var i = AE(AF, AB.clientWidth, 100);
            AA.removeChild(AB);
            return i
        }
        function n(AB, AA, i) {
            return AB * AA / i
        }
        function x(AB, AA, i) {
            return AB / (AA / i)
        }
        function p(AE, AA) {
            var AD = AE.length;
            if (typeof AA != r) {
                AD = Math.min(AA, AD)
            }
            var AB = new Array(AD),AC = 0;
            for (; AC < AD; AC++) {
                AB[AC] = AE[AC]
            }
            return AB
        }
        function y(AD, AC) {
            if (AC === k) {
                return q(AD)
            } else {
                if (u.string(AC)) {
                    return p(AD.getElementsByTagName(AC))
                } else {
                    var AE = [],AB = 0,AA = AC.length;
                    for (; AB < AA; AB++) {
                        if (AC[AB] === k) {
                            return q(AD)
                        }
                        AE = AE.concat(y(AD, AC[AB]))
                    }
                    return AE
                }
            }
        }
        function f(i) {
            if (typeof i[0] != r) {
                return i[0]
            }
            return null
        }
        var q;
        if (B.is_ie6down) {
            q = function(i) {
                return i.all
            }
        } else {
            q = function(i) {
                return i.getElementsByTagName(k)
            }
        }
        B.Dom = {XPathSupport:typeof XPathEvaluator != r,getBody:function() {
            return this.body || (this.body = document.body || this.getDescendant(document, "body"))
        },getHtml:function() {
            return this.html || (this.html = document.documentElement || this.getDescendant(document, "html"))
        },getDescendants:function(AB, AA, AC, i) {
            return this.filterElements(y(AB, AA || k), k, AC, i)
        },getDescendantsOrSelf:function(AB, AA, AC, i) {
            return this.filterElements(y(AB, AA || k), AA, AC, i, AB)
        },getDescendant:function(AA, i, AB) {
            return f(this.getDescendants(AA, i, AB, 1))
        },getDescendantOrSelf:function(AA, i, AB) {
            return f(this.getDescendantsOrSelf(AA, i, AB, 1))
        },getAncestors:function(AA, i, AB) {
            return this.getElementsByType(AA, i, AB, w)
        },getAncestorsOrSelf:function(AA, i, AB) {
            return this.getElementsByType(AA, i, AB, w, AA)
        },getAncestor:function(AA, i, AB) {
            return this.getElementByType(AA, i, AB, w)
        },getAncestorOrSelf:function(AA, i, AB) {
            return this.getElementByType(AA, i, AB, w, AA)
        },getChildren:function(AB, AA, AC, i) {
            return this.filterElements(AB.childNodes, AA, AC, i)
        },getChild:function(AA, i, AB) {
            return f(this.getChildren(AA, i, AB, 1))
        },getPreceding:function(AA, i, AB) {
            return this.getElementsByType(AA, i, AB, s)
        },getPrev:function(AA, i, AB) {
            return this.getElementByType(AA, i, AB, s)
        },getFollowing:function(AA, i, AB) {
            return this.getElementsByType(AA, i, AB, g)
        },getNext:function(AA, i, AB) {
            return this.getElementByType(AA, i, AB, g)
        },getElementsByTagNameAndClass:function(AB, AC, AA, i) {
            return this.getDescendants(AA || document, AB, AC, i)
        },getElementByTagNameAndClass:function(AA, AB, i) {
            return f(this.getElementsByTagNameAndClass(AA, AB, i, 1))
        },getElementsByTagName:function(AB, AA, i) {
            return this.getDescendants(AA || document, AB, k, i)
        },getElementByTagName:function(AA, i) {
            return f(this.getElementsByTagName(AA, i, 1))
        },getElementsByClass:function(AB, AA, i) {
            return this.getDescendants(AA || document, k, AB, i)
        },getElementByClass:function(AA, i) {
            return f(this.getElementsByClass(AA, i, 1))
        },getParentByTagName:function(AA, i) {
            return this.getAncestorOrSelf(AA, i, k)
        },getParentByClass:function(AA, i) {
            return this.getAncestorOrSelf(i, k, AA)
        },filterElements:function(AA, AB, AG, AC, AH) {
            var AE,AI = [],AF = 0,AD = 0;
            AC = AC || -1;
            if (AH) {
                if (this.testElement(AH, AB, AG)) {
                    AI[AD++] = AH
                }
            }
            while (AD != AC && (AE = AA[AF++])) {
                if (this.testElement(AE, AB, AG)) {
                    AI[AD++] = AE
                }
            }
            return AI
        },testElement:function(AA, i, AB) {
            return(this.testTagName(AA, i) && this.testClassName(AA, AB))
        },testTagName:function(AF, AE) {
            if (!AF || !AF.tagName) {
                return false
            }
            if ((AE || k) == k) {
                return true
            }
            var AB = AF.tagName.toLowerCase();
            if (typeof AE == "string") {
                return AB == AE.toLowerCase()
            }
            var AC,AD = 0,AA = AE.length;
            for (; AD < AA; AD++) {
                AC = AE[AD];
                if (AC == k || AB == AC.toLowerCase()) {
                    return true
                }
            }
            return false
        },testClassName:function(i, AA) {
            return m.test(i, AA || k)
        },isChild:function(AA, i) {
            if (i == document) {
                return true
            }
            while (AA) {
                if (AA === i) {
                    return true
                }
                AA = AA.parentNode
            }
            return false
        },removeNode:function(i) {
            if (i && i.parentNode) {
                i.parentNode.removeChild(i)
            }
        },clearNode:function(i) {
            if (!i) {
                return null
            }
            var AA;
            while ((AA = i.firstChild)) {
                i.removeChild(AA)
            }
            return i
        },cutNode:function(AA) {
            var i = AA.parentNode,AB;
            while ((AB = AA.firstChild)) {
                i.appendChild(AB)
            }
            this.removeNode(AA)
        },replaceNode:function(AA, AE) {
            switch (typeof AE) {case"string":if (AA.outerHTML) {
                if (AE.indexOf("<") == 0) {
                    var AC = "__outer_span__";
                    AA.outerHTML = '<span id="' + AC + '">&#160;</span>' + AE;
                    var AB = B.$(AC);
                    AB.parentNode.removeChild(AB)
                } else {
                    AA.outerHTML = AE
                }
            } else {
                var AD;
                if (l.normalize(AE) == "") {
                    AD = document.createTextNode(AE)
                } else {
                    var i = AA.ownerDocument.createRange();
                    i.selectNodeContents(AA);
                    AD = i.createContextualFragment(AE)
                }
                AA.parentNode.replaceChild(AD, AA)
            }break;default:AA.parentNode.replaceChild(AE, AA);break}
        },textContent:(function() {
            var i = document.createElement("span");
            if (B.gecko_ver > 1.7 && u.def(i.textContent)) {
                return function(AA) {
                    return AA.textContent
                }
            } else {
                if (u.def(i.innerText)) {
                    return function(AA) {
                        return AA.innerText
                    }
                } else {
                    return function(AA) {
                        return AA.innerHTML.replace(new RegExp("<.*?>", "g"), "")
                    }
                }
            }
        })(),insertBefore:function(AA, i) {
            return i.parentNode.insertBefore(AA, i)
        },insertAfter:function(AA, i) {
            var AB = i.nextSibling;
            if (AB) {
                return this.insertBefore(AA, AB)
            }
            return i.parentNode.appendChild(AA)
        },viewPort:function() {
            var i = this.getBody();
            return[i.clientWidth,i.clientHeight]
        },getOffset:function(AH, AI) {
            var AB = 0,AA = 0,AD = [0,0],AG = this.getBody(),AF,AC,AE,i;
            if (!AI || !u.element(AI)) {
                AI = document
            }
            if (B.is_opera && z.getPropertyValue(AH, "display") == "inline") {
                AB = z.getPropertyValuePx(AH, "margin-left");
                i = z.create("span");
                this.insertBefore(i, AH);
                AH = i
            }
            while (AH !== null && AH !== AI) {
                AB += AH.offsetLeft || 0;
                AA += AH.offsetTop || 0;
                if (!(B.is_konq || (B.is_opera && B.opera_ver > 8.6))) {
                    AF = z.getPropertyValue(AH, "position");
                    AC = AF == "static";
                    AE = AF == "relative";
                    if (AC || (!B.is_opera && AE)) {
                        AB += z.getPropertyValuePx(AH, "border-left-width");
                        AA += z.getPropertyValuePx(AH, "border-top-width");
                        if (B.is_ie && AH !== AG) {
                            AB += z.getPropertyValuePx(AH, "margin-left");
                            AA += z.getPropertyValuePx(AH, "margin-top")
                        }
                    }
                }
                AH = AH.offsetParent
            }
            if (u.element(i)) {
                this.removeNode(i)
            }
            if (AI !== document && AH !== AI) {
                AD = this.getOffset(AI)
            }
            return[AB - AD[0],AA - AD[1]]
        },offsetTop:function(AA, i) {
            return this.getOffset(AA, i)[1]
        },offsetLeft:function(AA, i) {
            return this.getOffset(AA, i)[0]
        },getDimensions:function(AA) {
            var AF = z.getPropertyValue(AA, "display");
            if (AF != "none" && AF != null) {
                return[AA.offsetWidth,AA.offsetHeight]
            }
            var AC = AA.style,AE = AC.visibility,AB = AC.position,i = AC.display,AD;
            AC.visibility = "hidden";
            AC.position = "absolute";
            AC.display = "block";
            AD = [AA.offsetWidth,AA.offsetHeight];
            AC.display = i;
            AC.position = AB;
            AC.visibility = AE;
            return AD
        },getWidth:function(i) {
            return this.getDimensions(i)[0]
        },getHeight:function(i) {
            return this.getDimensions(i)[1]
        },getPageScrollX:function() {
            return this.getBody().scrollLeft || this.getHtml().scrollLeft
        },getPageScrollY:function() {
            return this.getBody().scrollTop || this.getHtml().scrollTop
        },getStyle:function(i) {
            return z.getStyle(i)
        },getPropertyValue:function(i, AA) {
            return z.getPropertyValue(i, AA)
        },getPropertyValuePx:function(i, AA) {
            return z.getPropertyValuePx(i, AA)
        },em2px:function(AA, i) {
            return t(AA, i, n, "em")
        },px2em:function(AA, i) {
            return t(AA, i, x, "em")
        },unit2px:function(AB, i, AA) {
            return t(AB, i, n, AA)
        },px2unit:function(AA, i, AB) {
            return t(AA, i, x, AB)
        },getElementByType:function(AB, AA, AD, AC, i) {
            AB = i || AB[AC];
            while (AB) {
                if (this.testElement(AB, AA, AD)) {
                    return AB
                }
                AB = AB[AC]
            }
            return null
        },getElementsByType:function(AC, AB, AE, AD, AA) {
            var i = [];
            AC = AA || AC[AD];
            while (AC) {
                if (this.testElement(AC, AB, AE)) {
                    i.push(AC)
                }
                AC = AC[AD]
            }
            if (AD == s || AD == w) {
                return i.reverse()
            }
            return i
        },getElementsByXPath:function(AF, AC) {
            var AG = new XPathEvaluator();
            var AE = AG.evaluate(AF, AC, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            var AD = AE.snapshotLength;
            var AA = new Array(AD);
            for (var AB = 0; AB < AD; AB++) {
                AA[AB] = AE.snapshotItem(AB)
            }
            return AA
        },getElementByXPath:function(AB, i) {
            var AC = new XPathEvaluator();
            var AA = AC.evaluate(AB, i, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null);
            return AA != null ? AA.singleNodeValue : null
        }};
        var e = B.Dom;
        if (e.XPathSupport) {
            function o(AC) {
                switch (u.type(AC)) {case u.STRING:return AC.toLowerCase();case u.ARRAY:var AB = 0,AD = AC.length,AE = [],AA;if (AD == 1) {
                    return AC[0].toLowerCase()
                }for (; AB < AD; AB++) {
                    AA = AC[AB];
                    if (AA != k) {
                        AE.push("name()='" + AA.toLowerCase() + "'");
                        AE.push("name()='" + AA.toUpperCase() + "'")
                    } else {
                        return k
                    }
                }return k + "[" + AE.join(" or ") + "]"}
                return k
            }
            function j(i) {
                if (i && i != k) {
                    return"[contains(concat(' ',@class,' '),' " + i + " ')]"
                }
                return""
            }
            e.getByAxis = function(AB, AD, AA, AC, i) {
                var AE = AD + o(AA);
                if (typeof AC == "string") {
                    AE += j(AC);
                    if (i) {
                        AE += "[position()<=" + i + "]"
                    }
                    return this.getElementsByXPath(AE, AB)
                } else {
                    return this.filterElements(this.getElementsByXPath(AE, AB), k, AC, i)
                }
            };
            var h = {getDescendants:"descendant",getDescendantsOrSelf:"descendant-or-self",getAncestors:"ancestor",getAncestorsOrSelf:"ancestor-or-self",getChildren:"child",getFollowing:"following-sibling",getPreceding:"preceding-sibling"};
            for (var v in h) {
                e[v] = (function(i) {
                    return function(AC, AB, AD, AA) {
                        return this.getByAxis(AC, i + "::", AB, AD, AA)
                    }
                })(h[v])
            }
        }
        if (document.getElementsByClassName) {
            e.__getElementsByClass = e.getElementsByClass;
            e.getElementsByClass = function(AB, AA, i) {
                if (u.regexp(AB)) {
                    return this.__getElementsByClass(AB, AA, i)
                }
                return p((AA || document).getElementsByClassName(AB), i)
            }
        }
        if (window.innerHeight) {
            e.viewPort = function() {
                return[window.innerWidth,window.innerHeight]
            }
        } else {
            if (document.documentElement && document.documentElement.clientHeight) {
                e.viewPort = function() {
                    var i = document.documentElement;
                    return[i.clientWidth,i.clientHeight]
                }
            }
        }
        if (document.documentElement && document.documentElement.getBoundingClientRect) {
            e.getOffset = function(AC, i) {
                if (AC === document) {
                    return[0,0]
                }
                if (!u.element(i)) {
                    i = document
                }
                var AD,AB = (i !== document ? i.getBoundingClientRect() : {left:document.body.clientLeft + document.documentElement.clientLeft,top:document.body.clientTop + document.documentElement.clientTop});
                if (z.getPropertyValue(AC, "display") == "inline") {
                    var AA = AC.getClientRects();
                    AD = {left:AA[0].left,top:AA[0].top}
                } else {
                    AD = AC.getBoundingClientRect()
                }
                return[Math.round(AD.left + Math.max(document.documentElement.scrollLeft, document.body.scrollLeft) - AB.left),Math.round(AD.top + Math.max(document.documentElement.scrollTop, document.body.scrollTop) - AB.top)]
            }
        } else {
            if (document.getBoxObjectFor) {
                e.getOffset = function(AB, i) {
                    if (AB === document) {
                        return[0,0]
                    }
                    if (!u.element(i)) {
                        i = document
                    }
                    var AA = {x:0,y:0},AC = document.getBoxObjectFor(AB);
                    if (i !== document) {
                        AA = document.getBoxObjectFor(i)
                    }
                    return[AC.x - AA.x,AC.y - AA.y]
                }
            }
        }
        if (typeof window.pageXOffset == "number") {
            e.getPageScrollX = function() {
                return window.pageXOffset
            };
            e.getPageScrollY = function() {
                return window.pageYOffset
            }
        }
        B.$ = d;
        e.$ = d;
        e.getNextElement = e.getNext;
        e.getPreviousElement = e.getPrev;
        e.deleteNode = e.removeNode;
        e.getOffsset = e.getOffset;
        e.innerText = e.textContent;
        B.loaded("Dom")
    });
    B.require("Events", function() {
        var d = [];
        B.CallBacks = {add:function(h, j, f, i, g) {
            if (typeof i == B.UNDEF) {
                i = true
            }
            var e = new B.Observer("y5:" + h, j, f, i, g);
            d.push(e);
            return e
        },remove:function(e) {
            var f = d.indexOf(e);
            if (f != -1) {
                d[f].remove()
            }
        },dispatch:function(f, e, g) {
            return B.Notify("y5:" + f, e, g)
        },Listener:B.NULL};
        B.loaded("CallBacks")
    });
    B.DragAndDrop = {};
    B.DragAndDrop.MouseMove = function(h) {
        var k = this;
        function e(n) {
            if (!n.buttonL) {
                return
            }
            n.preventDefault();
            g.add();
            l.add();
            i.add();
            B.Notify("y5:start", k, n)
        }
        function f(n) {
            if (!n.buttonL) {
                d(n)
            }
            B.Notify("y5:move", k, n)
        }
        function d(n) {
            g.remove();
            l.remove();
            i.remove();
            B.Notify("y5:stop", k, n)
        }
        function j(n) {
            n.preventDefault();
            n.stopPropagation()
        }
        var m = B.Events.observe("mousedown", e, h, true);
        var g = B.Events.observe("mousemove", f, document, false);
        var l = B.Events.observe("mouseup", d, document, false);
        var i = B.Events.observe("selectstart", j, document, false)
    };
    B.require("Events", function() {
        B.loaded("DragAndDrop")
    });
    B.require(["Dom","Events"], function() {
        function AA(AH, AK, AJ, AI) {
            this.masks = AH;
            this.node = AJ;
            if (AI.context) {
                this.callback = function(AM, AL) {
                    AK.apply(AI.context, [AM,AL])
                }
            } else {
                this.callback = AK
            }
            this.options = AI;
            this.add();
            B.GC.collect(this)
        }
        AA.prototype = {add:function() {
            this.enable(true)
        },remove:function() {
            this.enable(false)
        },isEnable:function() {
            return this.enabled
        },enable:function(AH) {
            this.enabled = AH
        },check:function(AH, AI, AJ, AK) {
            if (!this.enabled) {
                return false
            }
            if (this.options.checkTarget && AI) {
                return false
            }
            if (!this.checkMask(AH)) {
                return false
            }
            if (B.Dom.isChild(AJ, this.node)) {
                if (this.options.preventDefault) {
                    AK.preventDefault()
                }
                this.callback(AK, this.options);
                return true
            }
            return false
        },checkMask:function(AJ) {
            for (var AK = 0,AI = this.masks.length; AK < AI; AK++) {
                var AH = this.masks[AK];
                if (AH == AJ || AH == 0) {
                    return true
                }
            }
            return false
        },cleanup:function() {
            this.remove();
            this.node = null;
            this.options = null;
            this.callback = null
        }};
        var g = 1 << 16,s = g - 1,AF = g,n = AF << 1,t = n << 1,AE = 1,h = 2,AB = {checkTarget:true,preventDefault:true,context:null,once:false},e = [],u = [];
        var AC = {BS:8,BACKSPACE:8,TAB:9,ENTER:13,SHIFT:16,CTRL:17,ALT:18,PAUSE:19,CAPS_LOCK:20,ESC:27,SPACE:32,PAGE_UP:33,PGUP:33,PAGE_DOWN:34,PGDN:34,END:35,HOME:36,LEFT_ARROW:37,LEFT:37,UP_ARROW:38,UP:38,RIGHT_ARROW:39,RIGHT:39,DOWN_ARROW:40,DOWN:40,INSERT:45,INS:45,DELETE:46,DEL:46,LEFT_WINDOW:91,RIGHT_WINDOW:92,SELECT:93,PLUS:B.is_ie || B.is_safari ? 187 : 61,PLUS_NUM:107,MINUS:B.is_ie || B.is_safari ? 189 : 109,MINUS_NUM:109,NUM_1:49,F1:112,F2:113,F3:114,F4:115,F5:116,F6:117,F7:118,F8:119,F9:120,F10:121,F11:122,F12:123,NUM_LOCK:144,SCROLL_LOCK:145,SLASH:191,ASTERISK:106};
        function o(AH) {
            if (B.Types.object(AH)) {
                return AH
            }
            var AI = AH.replace(/\s+/g, "").split(/\+/g);
            AH = {};
            AI.forEach(function(AJ) {
                AJ = AJ.toUpperCase();
                switch (AJ) {case"ALT":AH.alt = true;break;case"SHFT":case"SHIFT":AH.shift = true;break;case"CTL":case"CTRL":AH.ctrl = true;break;default:var AK = AC[AJ];if (AK) {
                    AH.key = AK
                } else {
                    AH.ch = AJ
                }}
            });
            return AH
        }
        function w(AH) {
            if (!B.Types.array(AH)) {
                AH = [AH]
            }
            return AH.map(o)
        }
        function i(AH) {
            for (var AI in AB) {
                if (typeof AH[AI] === B.UNDEF) {
                    AH[AI] = AB[AI]
                }
            }
            return AH
        }
        function q(AI, AJ) {
            var AH = AJ.lastIndexOf(AI);
            if (AH !== -1) {
                B.GC.remove(AJ[AH]);
                AJ = AJ.splice(AH, 1);
                return true
            }
            return false
        }
        function l(AK, AI) {
            var AJ = 0,AH;
            if (AK.key) {
                AJ = AK.key
            } else {
                if (AK.ch) {
                    switch (AI) {case AE:AJ = AK.ch.toUpperCase();break;case h:AJ = AK.ch.toLowerCase();break}
                    AJ = AJ.charCodeAt(0)
                }
            }
            AH = s & AJ;
            if (AK.ctrl) {
                AH ^= AF
            }
            if (AK.alt) {
                AH ^= n
            }
            if (AK.shift) {
                AH ^= t
            }
            return AH
        }
        function y(AL, AK) {
            var AH = AL.length,AI = new Array(AH);
            for (var AJ = 0; AJ < AH; AJ++) {
                AI[AJ] = l(AL[AJ], AK)
            }
            return AI
        }
        function d(AH, AI) {
            return{key:AH,ctrl:AI.ctrlKey,alt:AI.altKey,shift:AI.shiftKey}
        }
        function z(AJ, AI) {
            switch (AI) {case AE:return l(d(AJ.keyCode, AJ));case h:var AH = AJ.charCode ? AJ.charCode : AJ.keyCode;return l(d(AH, AJ))}
            return 0
        }
        function AD(AH) {
            if (!AH.tagName) {
                return false
            }
            switch (AH.tagName.toLowerCase()) {case"input":switch (AH.type) {case"text":case"password":case"file":case"search":return true}break;case"textarea":return true}
            return false
        }
        function k(AN, AQ, AO) {
            var AL,AJ,AI = AN.target,AS = z(AN, AQ),AR = AD(AI),AM = [],AP = false;
            for (AL = 0,AJ = AO.length; AL < AJ; AL++) {
                if (AO[AL].isEnable()) {
                    AM.push(AO[AL])
                }
            }
            for (AL = 0,AJ = AM.length; AL < AJ; AL++) {
                var AK = AM[AL];
                var AH = AK.check(AS, AR, AI, AN);
                if (AH && AK.options.once) {
                    q(AK, AO)
                }
                AP = AH || AP
            }
            return AP
        }
        function m(AH) {
            return k(AH, AE, e)
        }
        function f(AH) {
            if (!(AH.ctrlKey || AH.altKey)) {
                AH.stopPropagation()
            }
            return k(AH, h, u)
        }
        function x(AI, AM, AK, AL, AJ, AH) {
            switch (typeof AH) {case"object":break;case"boolean":AH = {checkTarget:AH};break;case"undefined":AH = {};break}
            AH = i(AH);
            return(AM[AM.length] = new AA(y(w(AK), AI), AL, AJ || document, AH))
        }
        B.ShortCut = {down:function(AJ, AK, AI, AH) {
            return x(AE, e, AJ, AK, AI, AH)
        },press:function(AJ, AK, AI, AH) {
            return x(h, u, AJ, AK, AI, AH)
        },remove:function(AH) {
            return this.removeDown(AH) || this.removePress(AH)
        },removeDown:function(AH) {
            return q(AH, e)
        },removePress:function(AH) {
            return q(AH, u)
        }};
        for (var AG in AC) {
            B.ShortCut[AG] = AC[AG]
        }
        var r = B.AEventListener;
        var j = m;
        if (B.is_ie) {
            j = function(AH) {
                if (!AH.repeat) {
                    m(AH)
                }
                f(AH)
            }
        } else {
            if (B.is_safari) {
                var p,v = false;
                p = new r("keyup", function() {
                    v = false;
                    p.remove()
                }, document, false);
                j = function(AH) {
                    if (!v) {
                        p.add();
                        m(AH)
                    }
                    v = true;
                    f(AH)
                }
            } else {
                new r("keypress", f, document, true)
            }
        }
        new r("keydown", j, document, true);
        B.loaded("ShortCuts")
    });
    B.require("Strings", "Events", "URL", "Utils", function() {
        var l = B.URL,i = B.Utils,g = B.Types,h = B.VOID,e = "Request",n = ["uninitialized","loading","loaded","interactive","complete"];
        function k(o) {
            B.Console.error(e, [o], [e])
        }
        function m() {
            return B.Strings.trim(this.responseText)
        }
        function j() {
            return this.responseText
        }
        function f() {
            if (B.JSON) {
                return B.JSON.decode(this.responseText)
            }
            throw new B.Exception("y5:JSON module required", f, "Request")
        }
        function d(o, q) {
            this.req = null;
            this.url = o instanceof l ? o : new l(o);
            var p;
            if (q) {
                p = q.callbackContext;
                q.callbackContext = null
            }
            this.params = i.objectCopy(i.objectCopy({}, this.defaultParams), q);
            if (g.object(p)) {
                this.params.callbackContext = p
            }
            this.params.method = this.params.method.toLowerCase()
        }
        d.prototype = {defaultParams:{id:null,method:"get",onexception:k,callbackContext:null,callbackObject:null},abort:h,send:function(o) {
            try {
                this.init();
                B.Console.log(this.toString() + " " + this.params.method.toUpperCase(), [this,o], [e,this.toString()]);
                if (o && B.Types.object(o) && !o.submit) {
                    o = B.Url("").replaceParams(o).query()
                }
                this._send(o)
            } catch(p) {
                this.dispatch("exception", p)
            }
        },init:function() {
            if (this.isInit) {
                return
            }
            if (!this.id) {
                this.id = i.generateUniqueId()
            }
            this._init();
            this.isInit = true
        },_init:h,end:function() {
            this._end();
            this.req = null;
            this.isInit = false;
            this.dispatch("end")
        },_end:h,dispatch:function(o, r) {
            var t = this.params,q = "on" + o,p = t.callbackContext || this,s = t.callbackObject;
            r = r || this.req;
            if (g.func(this[q])) {
                this[q].call(p, r)
            }
            if (g.func(t[q])) {
                t[q].call(p, r)
            }
            if (s && g.func(s[q])) {
                s[q](r)
            }
            B.Notify("request:" + o, t.id || this, r)
        },onStateChange:function() {
            try {
                B
            } catch(q) {
                return
            }
            var r = this.req.readyState,p = r == 4;
            if (p) {
                var o = 0;
                try {
                    o = this.req.status
                } catch(q) {
                }
                if (B.is_ie && o == 1223) {
                    o = 204
                }
                if (!g.func(this["on" + o])) {
                    o = (o >= 200 && o < 300) ? "load" : "error"
                }
                var s = g.string(this.req.responseText);
                this.req.text = s ? m : j;
                this.req.json = s ? f : B.NULL;
                this.dispatch(o)
            }
            this.dispatch(n[r]);
            if (p) {
                this.end()
            }
        },toString:function() {
            return e
        }};
        B.Get = {};
        B.Post = {};
        B.Load = {};
        d.ext = function(q, r, o) {
            if (g.undef(o)) {
                i.objectExtends(r, d, e)
            }
            var p = e + "." + q;
            B.Get[q] = function(s, v, t) {
                var u = new r(s, i.objectCopy(v || {}, {method:"get"}));
                u.send(t);
                return u
            };
            B.Post[q] = function(s, v, t) {
                var u = new r(s, i.objectCopy(v || {}, {method:"post"}));
                u.send(t);
                return u
            };
            B.Load[q] = function(s, t, w, u) {
                var v = new r(t, w);
                v.onload = function(x) {
                    if (g.string(s)) {
                        s = document.getElementById(s)
                    }
                    s.innerHTML = x.responseText
                };
                v.send(u);
                return v
            };
            r.prototype.toString = function() {
                return p
            };
            d[q] = r;
            B.loaded(p)
        };
        B.Request = d;
        B.loaded(e)
    });
    B.require("Request", function() {
        function e(g, h) {
            this.Request(g, h);
            if (this.params.method == "post") {
                var f = this.toString();
                B.Console.warn(f + ": POST not supported", [f])
            }
        }
        e.prototype = {defaultParams:{encoding:"UTF-8",key:"requestid"},abort:function() {
            this.dispatch("abort");
            this.end()
        },_init:function() {
            this.observer = new B.Observer(":onMessage", this._load, this.id, true, this)
        },_end:function() {
            if (this.observer) {
                this.observer.cleanup();
                delete this.observer
            }
        },_send:function(h) {
            var f = this.url.clone(),i = this.params,g = {};
            if (h) {
                f.query(h)
            }
            g[i.key] = this.id;
            f.addParams(g);
            B.Loader.loadScript(f, i.encoding);
            this.dispatch("loading")
        },_load:function(f) {
            try {
                this.req = f;
                this.onStateChange()
            } catch(g) {
                this.dispatch("exception", g)
            }
        }};
        function d(i, l, m, f) {
            m = m || {};
            for (var h in m) {
                m[h.toLowerCase()] = m[h]
            }
            var k = function(n) {
                return m[n.toLowerCase()]
            };
            var g = k("status");
            if (g) {
                f = parseInt(g, 10)
            }
            var j = {responseText:l,status:f,readyState:4,getResponseHeader:k};
            B.Notify(":onMessage", i, j)
        }
        e.onload = function(f, g, h) {
            d(f, g, h, 200)
        };
        e.onerror = function(f, g, h) {
            d(f, g, h, 500)
        };
        if (!B.AjaxJS) {
            B.AjaxJS = {onload:e.onload}
        }
        B.Request.ext("Script", e)
    });
    var a = U.Point = function(d, e) {
        this.x = (typeof d == "number" ? d : Number(d)) || 0;
        this.y = typeof e == B.UNDEF ? this.x : (typeof e == "number" ? e : Number(e)) || 0
    };
    a.fromString = function(d) {
        d = d.split(",");
        return new a(d[0], d[1])
    };
    a.prototype = {toString:function() {
        return this.x + "," + this.y
    },copy:function() {
        return new this.constructor(this.x, this.y)
    },moveToX:function(d) {
        this.x = d;
        return this
    },moveToY:function(d) {
        this.y = d;
        return this
    },moveTo:function(d) {
        this.x = d.x;
        this.y = d.y;
        return this
    },moveByX:function(d) {
        this.x += d;
        return this
    },moveByY:function(d) {
        this.y += d;
        return this
    },moveBy:function(d) {
        this.x += d.x;
        this.y += d.y;
        return this
    },diff:function(d) {
        return new this.constructor(this.x - d.x, this.y - d.y)
    },equals:function(d) {
        return((this.x == d.x) && (this.y == d.y))
    },scale:function(d) {
        this.x *= d;
        this.y *= d;
        return this
    },scaleX:function(d) {
        this.x *= d;
        return this
    },scaleY:function(d) {
        this.y *= d;
        return this
    },neg:function() {
        return new this.constructor(-this.x, -this.y)
    },distance:function(d) {
        var e = this.diff(d);
        return Math.sqrt((e.x * e.x) + (e.y * e.y))
    },length:function() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y))
    },angle:function() {
        return Math.acos(this.x / this.length())
    }};
    a.prototype.constructor = a;
    var P = U.GeoPoint = function(d, e, f) {
        this._unbounded = f || 0;
        this.setLng((typeof d == "number" ? d : Number(d)) || 0);
        this.setLat((typeof e == "number" ? e : Number(e)) || 0);
        this.__tileCoords = {hash:"",value:null}
    };
    C = P.prototype;
    P.fromString = function(d) {
        d = d.split(",");
        return new P(d[0], d[1])
    };
    C.toString = function(d) {
        d = typeof d != B.UNDEF ? d : 6;
        return Number(this.lng.toFixed(d)) + "," + Number(this.lat.toFixed(d))
    };
    C.isUnbounded = function() {
        return this._unbounded
    };
    C.setLng = function(d) {
        this.lng = this._unbounded ? d : U.cycleRestrict(d, -180, 180);
        return this
    };
    C.setLat = function(d) {
        this.lat = this._unbounded ? d : U.boundaryRestrict(d, -90, 90);
        return this
    };
    C.moveTo = function(d) {
        this.setLng(d.lng);
        this.setLat(d.lat);
        return this
    };
    C.moveBy = function(d) {
        this.setLng(this.lng + d.lng);
        this.setLat(this.lat + d.lat);
        return this
    };
    C.scale = function(d) {
        this.setLng(this.lng * d);
        this.setLat(this.lat * d);
        return this
    };
    C.neg = function() {
        this.setLng(-this.lng);
        this.setLat(-this.lat);
        return this
    };
    C.copy = function() {
        return new P(this.lng, this.lat, this._unbounded)
    };
    C.getTileCoordinates = function() {
        var d = this.toString(10);
        if (d != this.__tileCoords.hash) {
            this.__tileCoords.hash = d;
            this.__tileCoords.value = this._mercatorToTiles(this._geoToMercator(this))
        }
        return this.__tileCoords.value.copy()
    };
    C.setTileCoordinates = function(e) {
        var d = this._mercatorToGeo(this._tileToMercator(e));
        this.moveTo(d);
        return this
    };

    C._mercatorToTiles = function(e) {
        var d = Math.round((20037508.342789 + e.x) * 53.5865938),f = Math.round((20037508.342789 - e.y) * 53.5865938);
        if (!this._unbounded) {
            d = U.boundaryRestrict(d, 0, 2147483647);
            f = U.boundaryRestrict(f, 0, 2147483647)
        }
        return new a(d, f)
    };

    C._tileToMercator = function(d) {
        return new a(Math.round(d.x / 53.5865938 - 20037508.342789), Math.round(20037508.342789 - d.y / 53.5865938))
    };
    C._mercatorToGeo = function(e) {
        var j = Math.PI,f = j / 2,i = 6378137,n = 0.003356551468879694,k = 0.00000657187271079536,h = 1.764564338702e-8,m = 5.328478445e-11;
        var g = f - 2 * Math.atan(1 / Math.exp(e.y / i));
        var l = g + n * Math.sin(2 * g) + k * Math.sin(4 * g) + h * Math.sin(6 * g) + m * Math.sin(8 * g);
        var d = e.x / i;
        return new P(d * 180 / Math.PI, l * 180 / Math.PI, 1)
    };
    C._geoToMercator = function(g) {
        var d = g.lng * Math.PI / 180,m = g.lat * Math.PI / 180,l = 6378137,k = 0.0818191908426,f = k * Math.sin(m);
        var h = Math.tan(Math.PI / 4 + m / 2),j = Math.pow(Math.tan(Math.PI / 4 + Math.asin(f) / 2), k),i = h / j;
        return new a(Math.round(l * d), Math.round(l * Math.log(i)))
    };
    C.diff = function(e) {
        var f = e.lng - this.lng;
        if (!e._unbounded) {
            var d = Math.round(f / 360);
            if (d) {
                f -= d * 360
            }
        }
        return new P(f, e.lat - this.lat, 1)
    };
    C.distance = function(n) {
        var d = n;
        if (!this._unbounded) {
            var f = this.diff(d);
            d = new P(this.lng + f.lng, this.lat + f.lat, 1)
        }
        var l = this.lng,i = this.lat,k = d.lng,g = d.lat,m = 0;
        if (!(Math.abs(g - i) < 1e-7 && Math.abs(l - k) < 1e-7)) {
            var e = (i + (g - i) / 2) * Math.PI / 180,o = Math.atan(((k * 60 - l * 60) / (g * 60 - i * 60)) * Math.cos(e)),h = 6378137,j = 2 * Math.PI * h / 360;
            m = Math.abs(g - i) < 1e-7 ? Math.abs(((k - l) / Math.sin(o)) * Math.cos(e) * j) : Math.abs(j * (g - i) / Math.cos(o))
        }
        return m
    };
    U.Request = {Script:{onload:function() {
        B.Request.Script.onload.apply(B.Request.Script, arguments)
    }}};
    U.extend = function(f, d) {
        var e = function() {
        };
        e.prototype = d.prototype;
        e.prototype.constructor = d;
        return f.prototype = new e
    };
    U.humanDistance = function(i, h) {
        var f = !!h;
        var g = U._MapData.constants.metric.metre,e = Math.round(i);
        if (e >= 1000) {
            e /= 1000;
            g = U._MapData.constants.metric.kilometre;
            if (e.toFixed) {
                var d = 0;
                if (f) {
                    if (e < 3) {
                        d = 1
                    }
                } else {
                    if (e < 2) {
                        d = 3
                    } else {
                        if (e < 7) {
                            d = 2
                        } else {
                            if (e < 50) {
                                d = 1
                            }
                        }
                    }
                }
                e = e.toFixed(d).replace(".", ",")
            }
        } else {
            if (f) {
                e = Math.round(e / 50) * 50
            }
        }
        return e + "&#160;" + g
    };
    U.boundaryRestrict = function(f, e, d) {
        if (typeof f == B.UNDEF || typeof e == B.UNDEF || e == null || typeof d == B.UNDEF || d == null) {
            return NaN
        }
        return Math.max(Math.min(f, d), e)
    };
    U.cycleRestrict = function(f, e, d) {
        if (typeof f == B.UNDEF || typeof e == B.UNDEF || e == null || typeof d == B.UNDEF || d == null) {
            return NaN
        }
        if (f == Number.POSITIVE_INFINITY) {
            return d
        } else {
            if (f == Number.NEGATIVE_INFINITY) {
                return e
            } else {
                while (f < e) {
                    f += d - e
                }
                while (f > d) {
                    f -= d - e
                }
            }
        }
        return f
    };
    U.callMethod = function(j, e) {
        j = j instanceof Array ? j : [j];
        var f = Array.prototype.slice.call(arguments, 2),g = 0,d = j.length,h;
        for (; g < d; g++) {
            (h = j[g])[e].apply(h, f)
        }
    };
    U.toScale = function(d) {
        return 23 - d
    };
    U.maxLatitude = 85.0840588;
    U.minLatitude = -85.0840588;
    U.Browsers = B.Utils.objectCopy(B.Vars, {is_strict:!B.is_ie || document.compatMode && document.compatMode == "CSS1Compat",is_compatible:B.Vars.is_ie6up || B.Vars.gecko_ver >= 1.8 || B.Vars.opera_ver >= 8.5 || B.Vars.safari_ver >= 2});
    U.Size = a;
    var E = U.ControlPosition = function(d, e) {
        this.anchor = this._normalizeAnchor(d);
        this.offsets = this._normalizeOffsets(e || new U.Size())
    };
    E.TOP_LEFT = 0;
    E.TOP_RIGHT = 1;
    E.BOTTOM_LEFT = 2;
    E.BOTTOM_RIGHT = 3;
    E.prototype = {_normalizeAnchor:function(d) {
        return(isNaN(Number(d)) ? 0 : d % 4)
    },_normalizeOffsets:function(d) {
        return[this._normalizeCoordinate(d.x),this._normalizeCoordinate(d.y)]
    },_normalizeCoordinate:function(e) {
        var d = Number(e || 0);
        return isNaN(d) ? e : d + "px"
    },apply:function(d) {
        if (B.Types.element(d)) {
            d.style[this.anchor & 1 ? "right" : "left"] = this.offsets[0];
            d.style[this.anchor & 2 ? "bottom" : "top"] = this.offsets[1]
        }
    }};
    E = U._MouseEvent = function(g, d, f) {
        this._event = g;
        this._point = d;
        this._posInPixels = f
    };
    E.prototype = {getGeoPoint:function(d) {
        return new P(this._point.lng, this._point.lat, d)
    },getLocalPixels:function() {
        return this._posInPixels
    },getEvent:function() {
        return this._event
    }};
    E = U._ObjectCollection = function() {
        this._objects = {}
    };
    E.prototype = {add:function(e, d) {
        this._objects[e] = d
    },get:function(d) {
        return this._objects[d]
    },remove:function(e) {
        var d = this.get(e);
        delete this._objects[e];
        return d
    }};
    E = U.Bounds = function(d, e) {
        this._left = d.x;
        this._right = e.x;
        this._top = e.y;
        this._bottom = d.y
    };
    E.prototype = {_getResultPoint:function(d, e) {
        return new a(d, e)
    },getTop:function() {
        return this._top
    },getRight:function() {
        return this._right
    },getBottom:function() {
        return this._bottom
    },getLeft:function() {
        return this._left
    },equals:function(d) {
        return d._left == this._left && d._top == this._top && d._right == this._right && d._bottom == this._bottom
    },getLeftTop:function() {
        return this._getResultPoint(this._left, this._top)
    },getRightTop:function() {
        return this._getResultPoint(this._right, this._top)
    },getRightBottom:function() {
        return this._getResultPoint(this._right, this._bottom)
    },getLeftBottom:function() {
        return this._getResultPoint(this._left, this._bottom)
    },getCenter:function() {
        return this._getResultPoint((this._left + this._right) / 2, (this._top + this._bottom) / 2)
    },getSpan:function() {
        return new a(Math.abs(this._left - this._right), Math.abs(this._top - this._bottom))
    },contains:function(d) {
        return d.x > this._left && d.x < this._right && d.y > this._top && d.y < this._bottom
    }};
    E = U.CollectionBounds = function(d) {
        var e = new a();
        U.Bounds.call(this, e, e);
        this._empty = 1;
        if (d) {
            this.add(d)
        }
    };
    C = U.extend(E, U.Bounds);
    C.add = function(d) {
        if (d instanceof Array) {
            d.forEach(this.add, this);
            return
        }
        if (this._empty) {
            this._left = this._right = d.x;
            this._top = this._bottom = d.y;
            this._empty = 0
        } else {
            if (d.x > this._right) {
                this._right = d.x
            } else {
                if (d.x < this._left) {
                    this._left = d.x
                }
            }
            if (d.y > this._bottom) {
                this._bottom = d.y
            } else {
                if (d.y < this._top) {
                    this._top = d.y
                }
            }
        }
    };
    C.clear = function() {
        this._left = this._top = this._right = this._bottom = 0
    };
    E = U.GeoBounds = function(e, d) {
        this._left = e.lng;
        this._right = d.lng;
        this._top = d.lat;
        this._bottom = e.lat;
        this._isResultUnbounded = e.isUnbounded() && d.isUnbounded()
    };
    C = U.extend(E, U.Bounds);
    E.fromCenterAndSpan = function(e, r) {
        var j = 6378137,k = Math.PI,g = e.lat * k / 180,l = r.y * k / 180,m = j * Math.log(Math.tan(g / 2 + k / 4)),f = Math.exp(2 * m / j),q = 1,p = (f + 1) * Math.tan(l / 2),n = -f,d = p * p - 4 * q * n,o = (-p + Math.sqrt(d)) / 2 * q,i = ((Math.atan(o) - k / 4) * 2) * 180 / k;
        return new U.GeoBounds(new P(e.lng - r.x / 2, i, 1), new P(e.lng + r.x / 2, i + r.y, 1))
    };
    C._getResultPoint = function(d, e) {
        return new P(d, e, this._isResultUnbounded)
    };
    C.__getVector = function() {
        return this._leftTopToRightBottomVector || (this._leftTopToRightBottomVector = new P(this._left, this._top).diff(this.__getUnboundedRightBottom()))
    };
    C.__getUnboundedRightBottom = function() {
        return new P(this._right < this._left ? this._right + 360 : this._right, this._bottom, 1)
    };
    C.getMapZoomLevel = function(g) {
        var f = 0,e = this.__getUnboundedRightBottom().getTileCoordinates().diff(this.getLeftTop().getTileCoordinates()),d = g._state.size.x,h = e.x;
        if (h / d < e.y / g._state.size.y) {
            h = e.y;
            d = g._state.size.y
        }
        while (h > d) {
            h = Math.floor(h / 2);
            f++
        }
        return U.toScale(f)
    };
    C.getCenter = function() {
        var e = this.__getUnboundedRightBottom().getTileCoordinates().moveBy(this.getLeftTop().getTileCoordinates()).scale(0.5),d = new P(0, 0, 1).setTileCoordinates(e);
        return this._getResultPoint(d.lng, d.lat)
    };
    C.getSpan = function() {
        return new P(this.__getVector().lng, -this.__getVector().lat)
    };
    C.contains = function(d) {
        return d.lng > this._left && d.lng < this._right && d.lat > this._top && d.lat < this._bottom
    };
    E = U.GeoCollectionBounds = function(d) {
        var e = new P(0, 0, 1);
        U.GeoBounds.call(this, e, e);
        this._empty = 1;
        if (d) {
            this.add(d)
        }
    };
    C = U.extend(E, U.GeoBounds);
    C.clear = function() {
        this._left = this._top = this._right = this._bottom = 0
    };
    C.add = function(d) {
        if (d instanceof Array) {
            d.forEach(this.add, this);
            return
        }
        if (this._empty) {
            this._left = this._right = d.lng;
            this._top = this._bottom = d.lat;
            this._empty = 0
        } else {
            if (d.lng > this._right) {
                this._right = d.lng
            } else {
                if (d.lng < this._left) {
                    this._left = d.lng
                }
            }
            if (d.lat < this._bottom) {
                this._bottom = d.lat
            } else {
                if (d.lat > this._top) {
                    this._top = d.lat
                }
            }
        }
    };
    var A = B.Elements,c = U.Elements = {css:function(d, f, e) {
        return A.css(d, f, e)
    },getSummaryScroll:function(e, f) {
        var d = B.Dom.getBody(),h = 0,g = 0;
        while (!(e == null || e == f)) {
            h += e == d ? B.Dom.getPageScrollX() : e.scrollLeft;
            g += e == d ? B.Dom.getPageScrollY() : e.scrollTop;
            e = e.offsetParent
        }
        return new a(h, g)
    },size:function(e, d, f) {
        if (typeof d == B.UNDEF) {
            return new U.Size(e.offsetWidth, e.offsetHeight)
        } else {
            c.css(e, d instanceof a ? {width:d.x,height:d.y} : {width:d,height:typeof f == B.UNDEF ? d : f})
        }
    },position:function(f, e, d) {
        if (typeof e == B.UNDEF) {
            return new a(f.offsetLeft, f.offsetTop)
        }
        c.css(f, e instanceof a ? {left:e.x,top:e.y} : {left:e,top:typeof d == B.UNDEF ? e : d})
    },create:function(f, d, e) {
        return f ? B.Elements.create(f, d, e) : null
    },setAttributes:function(e, d) {
        return B.Elements.setAttributes(e, d)
    }};
    U.Converter = function(f) {
        var e = f._state,d = [];
        this.localPixelsToCoordinates = function(j, h) {
            var i = j.diff(e.centerInPixels),g = this.pixelsToTileCoordinates(i);
            g.moveBy(e.centerInTiles);
            return new P(0, 0, h).setTileCoordinates(g)
        };
        this.coordinatesToLocalPixels = function(g) {
            var h = g.copy();
            if (!h.isUnbounded()) {
                h.setLat(U.boundaryRestrict(h.lat, U.minLatitude, U.maxLatitude))
            }
            return this.tileCoordinatesToPixels(this.nearestTileOffset(h)).moveBy(e.centerInPixels)
        };
        this.nearestTileOffset = function(j, i) {
            var g,h;
            if (i) {
                g = new P(i.lng, i.lat, 1);
                h = g.getTileCoordinates()
            } else {
                g = (new P(0, 0, 1)).setTileCoordinates(e.centerInTiles);
                h = e.centerInTiles
            }
            return g.moveBy(g.diff(j)).getTileCoordinates().diff(h)
        };
        this.clientPixelsToCoordinates = function(g, h) {
            return this.localPixelsToCoordinates(this.clientPixelsToLocalPixels(g), h)
        };
        this.coordinatesToClientPixels = function(g) {
            return this.localPixelsToClientPixels(this.coordinatesToLocalPixels(g))
        };
        this.clientPixelsToLocalPixels = function(g) {
            return g.diff(e.domOffset).moveBy(c.getSummaryScroll(f.getContainer()))
        };
        this.localPixelsToClientPixels = function(g) {
            return g.copy().moveBy(e.domOffset).diff(c.getSummaryScroll(f.getContainer()))
        };
        this.coordinatesToMapPixels = function(g) {
            return this.localPixelsToMapPixels(this.coordinatesToLocalPixels(g))
        };
        this.mapPixelsToCoordinates = function(g, h) {
            return this.localPixelsToCoordinates(this.mapPixelsToLocalPixels(g), h)
        };
        this.mapPixelsToLocalPixels = function(g) {
            return g.copy().moveBy(e.offset)
        };
        this.localPixelsToMapPixels = function(g) {
            return g.diff(e.offset)
        };
        this.pixelsToTileCoordinates = function(i, h) {
            var g = Math.pow(2, typeof h == B.UNDEF ? e.zoom : h);
            return new a(i.x * g, i.y * g)
        };
        this.tileCoordinatesToPixels = function(i, h) {
            var g = Math.pow(2, typeof h == B.UNDEF ? e.zoom : h);
            return new a(parseInt(i.x / g), parseInt(i.y / g))
        };
        this.getWorldSizeInPixels = function(g) {
            g = typeof g == B.UNDEF ? U.toScale(e.zoom) : g;
            return d[g] || (d[g] = 256 << g)
        }
    };
    var Q = U.Events = new function() {
        var d = B.Observer,f = B.AEventListener;
        function g(i) {
            var h = i.prototype;
            h.enable = h.add;
            h.disable = h.remove
        }
        g(d);
        g(f);
        this.observe = function(i, k, l, j, h) {
            return e(d, k, l, i, typeof h != B.UNDEF ? h : 1, j)
        };
        this.domObserve = function(i, k, l, j, h) {
            return e(f, k, l, i, typeof h != B.UNDEF ? h : 1, j)
        };
        function e(p, q, j, m, o, h) {
            var r;
            if (q instanceof Array) {
                r = [];
                for (var n = 0,k = q.length; n < k; n++) {
                    r[n] = new p(q[n], j, m, o, h || m)
                }
            } else {
                r = new p(q, j, m, o, h || m)
            }
            return r
        }
        this.cleanup = function(h) {
            if (h instanceof Array) {
                h.forEach(this.cleanup, this)
            } else {
                if (h && h.cleanup) {
                    h.cleanup()
                }
            }
        };
        this.notify = function(i, h, j, k) {
            B.Notify(i, h, j, k)
        }
    };
    var E = U.Group = function() {
        this._objects = []
    },C = E.prototype;
    C.Events = {Add:"Add",Remove:"Remove"};
    C.isGroup = true;
    C.destructor = function() {
        U.callMethod(this._objects, "destructor");
        this._objects = null
    };
    C.add = function(d) {
        if (d) {
            if (d instanceof Array) {
                d.forEach(this.add, this)
            } else {
                this._objects.push(d);
                Q.notify(this.Events.Add, this, d)
            }
        }
    };
    C.replace = function(d, f) {
        var e = this._objects.indexOf(d);
        if (e != -1) {
            this._objects[e] = f;
            Q.notify(this.Events.Remove, this, d);
            Q.notify(this.Events.Add, this, f)
        }
    };
    C.remove = function(e) {
        var d = this._objects.indexOf(e);
        if (d != -1) {
            this._objects.splice(d, 1);
            Q.notify(this.Events.Remove, this, e)
        }
    };
    C.update = function() {
        U.callMethod(this._objects, "update")
    };
    C.removeAll = function() {
        while (this._objects.length) {
            this.remove(this.get(0))
        }
    };
    C.get = function(e) {
        var d = this._objects.length;
        return e < 0 && (e = d + e) < 0 || d <= e ? null : this._objects[e]
    };
    C.indexOf = function(d) {
        return this._objects.indexOf(d)
    };
    C.length = function() {
        return this._objects.length
    };
    C.forEach = function() {
        this._objects.forEach.apply(this._objects, arguments)
    };
    C.filter = function(f, e) {
        var d = [];
        this.__filter(d, this, f, e);
        return d
    };
    C.__filter = function(g, h, j, f) {
        if (j.call(f, h)) {
            g.push(h)
        }
        if (h.isGroup) {
            for (var e = 0,d = h.length(); e < d; e++) {
                this.__filter(g, h.get(e), j, f)
            }
        }
    };
    E = U.OverlayGroup = function() {
        U.Group.call(this);
        this.__listeners = [Q.observe(this, this.Events.Add, this.__addObjectToMap, this),Q.observe(this, this.Events.Remove, this.__removeObjectFromMap, this)]
    };
    C = U.extend(E, U.Group);
    C.onAddToMap = function(d, e) {
        this._map = d;
        this._parentContainer = e;
        this.forEach(this.__addObjectToMap, this)
    };
    C.__addObjectToMap = function(d) {
        if (this._map) {
            d.onAddToMap(this._map, this._parentContainer)
        }
    };
    C.onRemoveFromMap = function() {
        this.forEach(this.__removeObjectFromMap, this);
        this._map = this._parentContainer = null
    };
    C.__removeObjectFromMap = function(d) {
        if (this._map) {
            d.onRemoveFromMap()
        }
    };
    C.destructor = function() {
        U.Group.prototype.destructor.call(this);
        Q.cleanup(this.__listeners);
        this.__listeners = null
    };
    U.Layer = function() {
        this._element = c.create("div", {"class":"YMapsLayer"})
    };
    var C = U.Layer.prototype;
    C.onAddToMap = function(d, e) {
        this._map = d;
        e.appendChild(this._element)
    };
    C.onRemoveFromMap = function() {
        if (this._map) {
            B.Dom.removeNode(this._element);
            this._map = null
        }
    };
    C.update = function() {
        c.position(this._element, this._map._state.offset)
    };
    C.getContainer = function() {
        return this._element
    };
    C.setVisible = function(d) {
        this._element.style.display = (d ? "" : "none")
    };
    C.getVisible = function() {
        return this._element.style.display != "none"
    };
    C.onMove = function(d, e) {
        c.position(this._element, d)
    };
    C.onSmoothScrollingStart = function() {
        this.setVisible(0)
    };
    C.onSmoothScrollingTick = B.NULL;
    C.onSmoothScrollingStop = function() {
        this.setVisible(1)
    };
    C.getCopyright = function(e, d) {
        return""
    };
    C.destructor = function(d) {
        B.Dom.removeNode(this._element);
        this._map = this._element = null
    };
    var E = U.ObjectLayer = function() {
        U.Group.call(this);
        U.Layer.call(this);
        this.__listeners = [Q.observe(this, this.Events.Add, this.__addObjectToMap, this),Q.observe(this, this.Events.Remove, this.__removeObjectFromMap, this)]
    };
    var C = U.extend(E, U.Group);
    (function(d, e) {
        for (var f in e) {
            d[f] = e[f]
        }
    })(C, U.Layer.prototype);
    C.onAddToMap = function(d, e) {
        U.Layer.prototype.onAddToMap.call(this, d, e);
        this.forEach(this.__addObjectToMap, this)
    };
    C.__addObjectToMap = function(d) {
        if (this._map) {
            d.onAddToMap(this._map, this.getContainer())
        }
    };
    C.onRemoveFromMap = function() {
        this.forEach(this.__removeObjectFromMap, this);
        U.Layer.prototype.onRemoveFromMap.call(this)
    };
    C.__removeObjectFromMap = function(d) {
        if (this._map) {
            d.onRemoveFromMap()
        }
    };
    C.update = function() {
        U.Layer.prototype.update.call(this);
        U.Group.prototype.update.call(this)
    };
    C.destructor = function() {
        U.Layer.prototype.destructor.call(this);
        U.Group.prototype.destructor.call(this);
        Q.cleanup(this.__listeners);
        this.__listeners = null
    };
    U._ImageTile = function(d, f) {
        this.errorUrl = d;
        this._transparent = f && B.is_ie55up && B.is_ie7down;
        var e = this.image = document.createElement("img");
        e.galleryImg = false;
        Q.domObserve(e, "load", this._onLoad, this);
        Q.domObserve(e, "error", this._onError, this);
        this._htmlElement = this._transparent ? document.createElement("span") : e;
        this._top = this._left = this._right = this._bottom = this._height = this._width = null;
        c.css(this._htmlElement, "position", "absolute");
        this.setSize(I.SIZE, I.SIZE)
    };
    var C = U._ImageTile.prototype;
    C.onAddToMap = function(d, e) {
        this._map = d;
        e.appendChild(this._htmlElement)
    };
    C.onRemoveFromMap = function() {
        B.Dom.removeNode(this._htmlElement)
    };
    C.setSize = function(e, d) {
        this.setWidth(e);
        this.setHeight(d)
    };
    C.getSize = function() {
        return new U.Size(this.getWidth(), this.getHeight())
    };
    C.setHeight = function(d) {
        this._height = d;
        c.css(this._htmlElement, "height", this._height)
    };
    C.getHeight = function() {
        return this._height
    };
    C.setWidth = function(d) {
        this._width = d;
        c.css(this._htmlElement, "width", this._width)
    };
    C.getWidth = function() {
        return this._width
    };
    C.moveTo = function(d) {
        this.setLeft(d.x);
        this.setTop(d.y)
    };
    C.setLeft = function(d) {
        this._left = d;
        return c.css(this._htmlElement, "left", this._left)
    };
    C.getLeft = function() {
        return this._left
    };
    C.setTop = function(d) {
        this._top = d;
        return c.css(this._htmlElement, "top", this._top)
    };
    C.getTop = function() {
        return this._top
    };
    C.setRight = function(d) {
        this._right = d;
        return c.css(this._htmlElement, "right", this._right)
    };
    C.getRight = function() {
        return this._right !== null ? this._right : this.getLeft() + this.getWidth()
    };
    C.setBottom = function(d) {
        this._bottom = d;
        return c.css(this._htmlElement, "bottom", this._bottom)
    };
    C.getBottom = function() {
        return this._bottom !== null ? this._bottom : this.getTop() + this.getHeight()
    };
    C.setUrl = function(d, e) {
        if (!d) {
            this._onError()
        } else {
            var f = this.image.src || "";
            if (f.slice(-d.length) != d) {
                if (!B.is_opera && B.opera_ver < 9.5) {
                    c.css(this._htmlElement, "display", "none")
                }
                this._setSrc(d)
            } else {
                this._onLoad()
            }
        }
    };
    if (B.is_opera && B.opera_ver < 9.5) {
        C._setSrc = function(d) {
            this.image.src = d;
            if (this.image.complete) {
                this._onLoad()
            }
        }
    } else {
        C._setSrc = function(d) {
            this.image.src = d
        }
    }
    C.stopLoading = function() {
        if (!this.image.complete) {
            this._onError()
        }
    };
    C._onError = function(d) {
        if (this.image.src.slice(-this.errorUrl.length) != this.errorUrl) {
            this._setSrc(this.errorUrl)
        }
    };
    if (B.is_opera && B.opera_ver < 9.5) {
        C._onLoad = function(d) {
            if (this._map) {
                Q.notify(this._map.Events._NeedRedraw, this._map)
            }
        }
    } else {
        C._onLoad = function(d) {
            if (this._transparent) {
                c.css(this._htmlElement, "filter", "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + this.image.src + ")")
            }
            c.css(this._htmlElement, "display", "")
        }
    }
    var I = U.Tile = new function() {
        this.SIZE = 256;
        var e = 8;
        var d = (1 << e) - 1;
        this.getTileOffset = function(g, f) {
            return(g >> f) & d
        };
        this.getTile = function(h, i) {
            var j = U.toScale(i),g = h.x >> j,f = h.y >> j;
            return new a(g >> e, f >> e)
        }
    };
    var S = function(d) {
        this.dataSource = d;
        this.curTileSize = I.SIZE;
        this.state = "master";
        this._freeTiles = [];
        this._addedTiles = [];
        this.element = c.create("div", {"class":"YMapsTileContainer"})
    };
    S.prototype = {onAddToMap:function(d, e) {
        this.map = d;
        this.parentContainer = e;
        e.appendChild(this.element)
    },onRemoveFromMap:function() {
        B.Dom.removeNode(this.element);
        this.map = this.parentContainer = null
    },destructor:function(d) {
        this._addedTiles.forEach(function(e) {
            e.forEach(this.releaseTile, this)
        }, this);
        U.callMethod(this._freeTiles, "destructor");
        this.element = this.map = this.parentContainer = this._freeTiles = this._addedTiles = null
    },update:function() {
        switch (this.state) {case"slave":this.setState("ignore");return;case"ignore":this.setState("hide");return;case"hide":return }
        this._setSize(this.map.getContainerSize());
        this.centering();
        this._updateCells(0, 0)
    },_setSize:function(e) {
        var f = 0,d = 0;
        if (e.x && e.y) {
            f = Math.ceil((e.y + 1) / this.curTileSize) + 1;
            d = Math.ceil((e.x + 1) / this.curTileSize) + 1
        }
        this._fill(f, d)
    },_fill:function(l, g) {
        var k = this._addedTiles.length,d = this._addedTiles[0] ? this._addedTiles[0].length : 0;
        if (k > l) {
            for (var h = l; h < k; h++) {
                this._addedTiles[h].forEach(this.releaseTile, this)
            }
            this._addedTiles.splice(l, k - l);
            k = l
        }
        if (d > g) {
            for (var h = 0,f; h < k; h++) {
                for (f = g; f < d; f++) {
                    this.releaseTile(this._addedTiles[h][f])
                }
                this._addedTiles[h].splice(g, d - g)
            }
            d = g
        }
        if (k < l || d < g) {
            for (var h = 0,f; h < k; h++) {
                for (f = d; f < g; f++) {
                    this._addedTiles[h].push(this.occupyTile())
                }
            }
            for (var h = k,f,e; h < l; h++) {
                e = [];
                for (f = 0; f < g; f++) {
                    e.push(this.occupyTile())
                }
                this._addedTiles.push(e)
            }
        }
    },releaseTile:function(d) {
        this._freeTiles.push(d);
        d.onRemoveFromMap()
    },occupyTile:function() {
        var d = this._freeTiles.shift() || this.createTile();
        d.onAddToMap(this.map, this.element);
        return d
    },createTile:function() {
        return new U._ImageTile(this.dataSource.getErrorTileUrl(), this.dataSource.isTransparent() && this.dataSource.isPng())
    },centering:function() {
        var d = this.map._state,f = new a(this.calculateBacklash(d.centerInPixels.x), this.calculateBacklash(d.centerInPixels.y)),e = new a(I.getTileOffset(d.centerInTiles.x, d.zoom), I.getTileOffset(d.centerInTiles.y, d.zoom));
        f.moveBy(e.neg());
        f = f.diff(d.offset);
        this.moveTo(f, 1)
    },calculateBacklash:function(e) {
        var d = Math.ceil(e / this.curTileSize);
        return e - d * this.curTileSize
    },onMove:function(d) {
        if (this.state == "master") {
            this._updateCells(d.x > 0 ? 1 : 0, d.y > 0 ? 1 : 0)
        }
    },callMethod:function(e) {
        var h = Array.prototype.slice.call(arguments, 1),k = 0,d = this._addedTiles.length,m,g,f;
        for (; k < d; k++) {
            m = this._addedTiles[k];
            g = 0;
            f = m.length;
            for (; g < f; g++) {
                m[g][e].apply(m[g], h)
            }
        }
    },moveTo:function(g, f) {
        for (var e = 0,d = this._addedTiles.length; e < d; e++) {
            this._updateRow(e, g.copy(), f);
            g.moveByY(this.curTileSize)
        }
    },setZIndex:function(d) {
        c.css(this.element, "zIndex", d)
    },setState:function(d) {
        switch ((this.state = d)) {case"master":c.css(this.element, "display", "");this.scale(1);break;case"slave":this.callMethod("stopLoading");break;case"hide":c.css(this.element, "display", "none");break}
    },_updateCells:function(d, h) {
        var f = this.map._state.offset,g = this.map.getContainerSize(),e = this.curTileSize;
        while (1) {
            if (!d && (this.getLeft() + f.x + e) < 0) {
                this._shift(0, 0);
                continue
            }
            if (!h && (this.getTop() + f.y + e) < 0) {
                this._shift(1, 0);
                continue
            }
            if (d && (this.getRight() + f.x - e) > g.x) {
                this._shift(0, 1);
                continue
            }
            if (h && (this.getBottom() + f.y - e) > g.y) {
                this._shift(1, 1);
                continue
            }
            break
        }
    },_shift:function(h, m) {
        var e = this.getLeft(),k = this.getTop(),l = this._addedTiles.length;
        if (h) {
            if (m) {
                var j = new a(e, k - this.curTileSize),n = this._addedTiles.pop();
                this._addedTiles.splice(0, 0, n);
                this._updateRow(0, j, 1)
            } else {
                var j = new a(e, this.getBottom()),g = this._addedTiles.shift();
                this._addedTiles.splice(l, 0, g);
                this._updateRow(l - 1, j, 1)
            }
        } else {
            if (m) {
                var j = new a(e - this.curTileSize, k),f = 0,n;
                for (; f < l; f++) {
                    n = this._addedTiles[f].pop();
                    this._addedTiles[f].splice(0, 0, n)
                }
                this._updateColumn(0, j, 1)
            } else {
                var j = new a(this.getRight(), k),f = 0,d,g;
                for (; f < l; f++) {
                    d = this._addedTiles[f];
                    g = d.shift();
                    d.splice(d.length, 0, g)
                }
                this._updateColumn(this._addedTiles[0].length - 1, j, 1)
            }
        }
    },scale:function(e, f) {
        var g = new a(this.getLeft(), this.getTop()),d = U.Tile.SIZE * e;
        if (this.curTileSize != d) {
            this.curTileSize = d;
            this.callMethod("setSize", d, d)
        }
        if (f) {
            g.moveBy(f)
        }
        this.moveTo(g)
    },_updateRow:function(j, f, g) {
        for (var e = 0,h = this._addedTiles[j],d = h.length; e < d; e++) {
            if (f) {
                h[e].moveTo(f);
                f.moveByX(this.curTileSize)
            }
            if (g) {
                this._updateTileUrl(h[e])
            }
        }
    },_updateColumn:function(e, g, h) {
        for (var f = 0,d = this._addedTiles.length; f < d; f++) {
            if (g) {
                this._addedTiles[f][e].moveTo(g);
                g.moveByY(this.curTileSize)
            }
            if (h) {
                this._updateTileUrl(this._addedTiles[f][e])
            }
        }
    },__maxY:Math.pow(2, 31) - 1,_updateTileUrl:function(f) {
        var j = new a(f.getLeft(), f.getTop());
        j.moveBy(new a(this.curTileSize / 2));
        var g = this.map,e = g.getZoom(),i = g.converter.mapPixelsToLocalPixels(j),h = i.diff(g._state.centerInPixels),d = g.converter.pixelsToTileCoordinates(h).moveBy(g._state.centerInTiles);
        if (d.y < 0 || d.y > this.__maxY) {
            f.setUrl(this.dataSource.getErrorTileUrl())
        } else {
            f.setUrl(this.dataSource.getTileUrl(I.getTile(d, e), e))
        }
    },_isEmpty:function() {
        return(!this._addedTiles[0] || !this._addedTiles[0].length)
    },_getProp:function(f, e, d) {
        return this._addedTiles[e || 0][d || 0]["get" + f]()
    },getWidth:function() {
        return this._isEmpty() ? 0 : this._addedTiles[0].length * this.curTileSize
    },getHeight:function() {
        return this._isEmpty() ? 0 : this._addedTiles.length * this.curTileSize
    },getLeft:function() {
        return this._isEmpty() ? 0 : this._getProp("Left")
    },getTop:function() {
        return this._isEmpty() ? 0 : this._getProp("Top")
    },getRight:function() {
        return this._isEmpty() ? 0 : this._getProp("Right", 0, this._addedTiles[0].length - 1)
    },getBottom:function() {
        return this._isEmpty() ? 0 : this._getProp("Bottom", this._addedTiles.length - 1)
    }};
    var E = U.MapLayer = function(d) {
        U.ObjectLayer.call(this);
        B.Classes.add(this.getContainer(), "YMapsMapLayer");
        this._dataSource = d
    };
    var C = U.extend(E, U.ObjectLayer);
    C.onAddToMap = function(f, g) {
        U.ObjectLayer.prototype.onAddToMap.apply(this, arguments);
        if (!this._master) {
            var d = this._master = new S(this._dataSource);
            d.setZIndex(6);
            this.add(d);
            var e = this.getContainer();
            e.onmousedown = e.onselectstart = e.ondragstart = B.FALSE
        }
    };
    C.destructor = function() {
        U.ObjectLayer.prototype.destructor.call(this);
        this._master = this._slave = null
    };
    C.onMove = function(d, e) {
        U.ObjectLayer.prototype.onMove.call(this, d, e);
        if (B.is_opera) {
            var f = this;
            window.setTimeout(function() {
                f._master.onMove(e)
            }, 0)
        } else {
            this._master.onMove(e)
        }
    };
    if (B.is_opera && B.opera_ver < 9.5) {
        C.onSmoothScrollingStart = C.onSmoothScrollingTick = C.onSmoothScrollingStop = B.NULL
    } else {
        C.onSmoothScrollingStart = function() {
            if (this._dataSource.isTransparent()) {
                this._master.setState("hide");
                return
            }
            if (this._slave) {
                this._slave.setState("hide")
            }
        };
        C.onSmoothScrollingTick = function(h) {
            if (this._dataSource.isTransparent()) {
                return
            }
            var g;
            if (h.saveThisPosition) {
                var f = this._master.curTileSize,e = I.SIZE * h.scaleCoefficientDiff;
                function d(j) {
                    var i = Math.floor(j / f),k = i * e + ((j - (i * f)) / f) * e;
                    return -1 * k
                }
                g = this._map.converter.coordinatesToMapPixels(h.saveThisPosition);
                g = new a(d(g.x - this._master.getLeft()), d(g.y - this._master.getTop()))
            }
            this._master.scale(h.scaleCoefficient, g)
        };
        C.onSmoothScrollingStop = function() {
            if (this._dataSource.isTransparent()) {
                this._master.setState("master");
                return
            }
            var d = this._master,e = this._slave;
            if (!e) {
                this.add(e = new S(this._dataSource))
            }
            d.setState("slave");
            var f = new a(d.getLeft(), d.getTop());
            f.moveBy(this._map._state.offset);
            d.moveTo(f);
            e.setState("master");
            d.setZIndex(5);
            e.setZIndex(6);
            this._master = e;
            this._slave = d
        }
    }
    U._ControlsLayer = function(e, f) {
        var j = {},k = {},h = 0,g;
        var l = Q.observe(e, "mousedown", function(o) {
            if (B.is_ie && document.selection) {
                document.selection.empty()
            } else {
                if (window.getSelection) {
                    if (B.is_safari) {
                        try {
                            window.getSelection().collapse()
                        } catch(n) {
                        }
                    } else {
                        window.getSelection().removeAllRanges()
                    }
                }
            }
        });
        function d(p, n, o) {
            var q = Q.domObserve(p, n, function(r) {
                m(n, r)
            }, null, o);
            if (!o) {
                k[n] = 0;
                j[n] = q
            }
        }
        d(f, "click", 1);
        d(f, "dblclick", 1);
        d(f, "contextmenu", 1);
        d(f, "mousedown", 1);
        d(f, "DOMMouseScroll");
        d(document, "mouseup");
        d(document, "mousemove");
        function m(n, p) {
            if (p.dontHandle) {
                return
            }
            g = i(n) ? g || (function() {
                g = e.converter.localPixelsToClientPixels(e._state.centerInPixels);
                return{clientX:g.x,clientY:g.y}
            })() : {clientX:p.clientX,clientY:p.clientY};
            var o = new a(g.clientX, g.clientY);
            if (n == "mousedown") {
                h = 0
            } else {
                if (n == "mousemove") {
                    h = 1
                } else {
                    if (n == "click" && h) {
                        h = 0;
                        return
                    }
                }
            }
            Q.notify(n, e, new U._MouseEvent(p, e.converter.clientPixelsToCoordinates(o, 1), e.converter.clientPixelsToLocalPixels(o)))
        }
        function i(n) {
            return n == e.Events.DOMMouseScroll && B.is_gecko && B.gecko_ver < 1.9 && !B.is_safari
        }
        this.listenMouseEvent = function(o, n) {
            if (j[o]) {
                var p = (k[o] += n ? 1 : -1);
                if (!n && !p) {
                    j[o].remove();
                    if (i(o)) {
                        this.listenMouseEvent(e.Events.MouseMove, 0)
                    }
                }
                if (n && p == 1) {
                    j[o].add();
                    if (i(o)) {
                        this.listenMouseEvent(e.Events.MouseMove, 1)
                    }
                }
            }
        };
        this.destructor = function() {
            for (var n in j) {
                j[n].cleanup()
            }
            l.cleanup();
            g = j = k = l = null
        }
    };
    U._SmoothScrolling = function(v) {
        var u = 0,i = 3,q = 20,d = 300,m = B.is_opera && B.opera_ver < 9.5 ? 0 : 4,s = 1,o = false,x = 0,t = 0,y = null,w = [],l = v._state.zoom,j = null,h = null;
        function f() {
            j = null;
            k()
        }
        this.scroll = function(AB, AA) {
            t = AA || 0;
            y = AB;
            e()
        };
        function e() {
            if (!o) {
                l = v.getZoom();
                if (z()) {
                    o = true;
                    g();
                    v._smoothScrollingStart();
                    if (m) {
                        k()
                    } else {
                        window.clearTimeout(h);
                        h = window.setTimeout(function() {
                            k()
                        }, d)
                    }
                }
            } else {
                if (Math.abs(u) < i && z()) {
                    g()
                }
            }
        }
        function z() {
            return(!(l + t > v.getType().getMaxZoom() || l + t < v.getType().getMinZoom()))
        }
        function g() {
            l += t;
            x = n();
            for (var AA = 0; AA < m; AA++) {
                s += x;
                w.push({saveThisPosition:y,scaleCoefficientDiff:x,scaleCoefficient:s})
            }
        }
        function n() {
            return(t * Math.pow(2, (t < 0 ? --u : u++)) / m)
        }
        function k() {
            var AA = w.shift();
            if (AA) {
                p(AA);
                j = window.setTimeout(f, q)
            } else {
                r()
            }
        }
        function p(AA) {
            v._smoothScrollingTick(AA)
        }
        function r() {
            var AA = v.converter.nearestTileOffset(y);
            if (u < 0) {
                AA.scale(Math.pow(2, Math.abs(u)) - 1);
                AA = AA.neg()
            } else {
                AA.scale((Math.pow(2, u) - 1) / Math.pow(2, u))
            }
            AA.moveBy(v._state.centerInTiles);
            v._smoothScrollingStop(AA, l);
            u = 0;
            s = 1;
            o = false
        }
        this.destructor = function() {
            if (j) {
                window.clearTimeout(j);
                r()
            }
            v = null
        }
    };
    U._DblClickZoomControl = function() {
        var g,e,d;
        this.onAddToMap = function(j) {
            g = j;
            e = [Q.observe(g, "dblclick", f, this, 0),Q.observe(g, "contextmenu", i, this, 0)];
            (this.onAddToMap = h)()
        };
        function h() {
            U.callMethod(e, "enable")
        }
        function f(j) {
            g.setCenter(j.getGeoPoint(), g.getZoom() + 1)
        }
        function i(j) {
            var l = new Date();
            if (d && (l - d < 500)) {
                g.setCenter(j.getGeoPoint(), g.getZoom() - 1);
                l = null
            }
            d = l;
            var k = j.getEvent();
            k.stopPropagation();
            k.preventDefault()
        }
        this.onRemoveFromMap = function() {
            d = null;
            U.callMethod(e, "disable")
        };
        this.destructor = function() {
            if (g) {
                Q.cleanup(e);
                g = e = null
            }
        }
    };
    U._ScrollZoomControl = function() {
        var h,e,d,j = new U._MagnifierControl(1);
        this.onAddToMap = function(k) {
            h = k;
            e = new U._SmoothScrolling(h);
            d = Q.observe(h, h.Events.DOMMouseScroll, f, this, 0);
            (this.onAddToMap = i)()
        };
        function i() {
            j.onAddToMap(h);
            g(1)
        }
        function g(k) {
            d[k ? "add" : "remove"]();
            h.listenMouseEvent(h.Events.DOMMouseScroll, k)
        }
        function f(l) {
            var k = l.getEvent();
            if (k.scrollDetail) {
                e.scroll(l.getGeoPoint(1), (k.scrollDetail > 0 ? -1 : 1))
            }
            k.stopPropagation();
            k.preventDefault()
        }
        this.onRemoveFromMap = function() {
            j.onRemoveFromMap();
            g(0)
        };
        this.destructor = function() {
            this.onRemoveFromMap();
            if (h) {
                e.destructor();
                d.cleanup()
            }
            j.destructor();
            h = d = j = e = null
        }
    };
    U._DraggingControl = function() {
        var d,i,k,j;
        function g(n, m) {
            d.listenMouseEvent("mouse" + n, m);
            j[n][m ? "enable" : "disable"]()
        }
        this.onAddToMap = function(m) {
            d = m;
            j = {down:Q.observe(d, "mousedown", l, this, 0),move:Q.observe(d, "mousemove", e, this, 0),up:Q.observe(d, "mouseup", h, this, 0)};
            (this.onAddToMap = f)()
        };
        function f() {
            B.Classes.add(d.getContainer(), "cursor-grab");
            g("down", 1)
        }
        function l(n) {
            if (!n._event.buttonL) {
                return
            }
            var m = d.getContainer();
            if (B.is_ie) {
                m.setCapture()
            }
            k = 0;
            i = n.getLocalPixels();
            n._event.stopPropagation();
            g("move", 1);
            g("up", 1);
            B.Classes.add(m, "cursor-grabbing")
        }
        function e(m) {
            if (!k) {
                k = 1;
                d._moveStart()
            }
            d._moveBy(i.diff(m.getLocalPixels()));
            i = m.getLocalPixels()
        }
        function h() {
            var m = d.getContainer();
            if (B.is_ie) {
                m.releaseCapture()
            }
            B.Classes.remove(m, "cursor-grabbing");
            g("move", 0);
            g("up", 0);
            i = null;
            if (k) {
                d._moveEnd()
            }
        }
        this.onRemoveFromMap = function() {
            if (i) {
                h()
            }
            B.Classes.remove(d.getContainer(), "cursor-grab");
            g("down", 0)
        };
        this.destructor = function() {
            if (d) {
                this.onRemoveFromMap();
                Q.cleanup(j);
                d = i = j = null
            }
        }
    };
    U._HotKeysControl = function(i) {
        i = r(i);
        var AC,y,t = [],AB = [],e = 0,m = 0,u = i.startSpeed,q,j,x;
        function r(AD) {
            return B.Utils.objectCopy({zoomTimeout:300,startSpeed:1,maxSpeed:15,speedUpCoef:1.05}, AD)
        }
        this.onAddToMap = function(AE) {
            AC = AE;
            k();
            var AG = AC.Events,AH = [AG.ChangeType,AG.MoveEnd,AG.Click];
            for (var AF = 0,AD = AH.length; AF < AD; AF++) {
                t[AF] = Q.observe(AC, AH[AF], d, this, 0)
            }
            t.push(Q.observe(AC, AG.Update, v), Q.domObserve(y, "blur", v), Q.domObserve(window, "blur", v));
            g();
            (this.onAddToMap = h)()
        };
        function k() {
            y = c.create("button", {"class":"YMapsHotKeysFocusHolder"});
            AC.getContainer().appendChild(y)
        }
        function d() {
            y.focus()
        }
        function g() {
            var AD = B.ShortCut;
            AB.push(AD[B.is_safari ? "down" : "press"]([{key:AD.UP_ARROW},{key:AD.RIGHT_ARROW},{key:AD.DOWN_ARROW},{key:AD.LEFT_ARROW},{key:AD.UP_ARROW,shift:true},{key:AD.RIGHT_ARROW,shift:true},{key:AD.DOWN_ARROW,shift:true},{key:AD.LEFT_ARROW,shift:true}], w, y, {checkTarget:1}));
            AB.push(Q.domObserve(y, "keyup", n));
            AB.push(AD[B.is_ie ? "press" : "down"]([{key:AD.PLUS_NUM},{key:AD.PLUS},{key:AD.PLUS,shift:true},{ch:"="},{ch:"+"}], p, y, {checkTarget:1}));
            AB.push(AD[B.is_ie ? "press" : "down"]([{key:AD.MINUS_NUM},{key:AD.MINUS},{ch:"_"}], f, y, {checkTarget:1}))
        }
        function h() {
            U.callMethod(t, "add");
            U.callMethod(AB, "add")
        }
        this.onRemoveFromMap = function() {
            v();
            U.callMethod(t, "remove");
            U.callMethod(AB, "remove")
        };
        function w(AE) {
            var AD = AE.charCode ? AE.charCode : AE.keyCode;
            if (AD & 1) {
                m = AD & 2 ? 1 : -1
            } else {
                e = AD & 2 ? -1 : 1
            }
            if (!q) {
                AC._moveStart();
                if (AE.shiftKey && !B.is_opera) {
                    q = window.setInterval(s, 20)
                } else {
                    q = window.setInterval(AA, 20)
                }
            }
        }
        function AA(AD) {
            var AE = Math.round(u);
            AC._moveBy(new a(m * AE, e * AE));
            if (u < i.maxSpeed) {
                u = Math.min(u * i.speedUpCoef, i.maxSpeed)
            }
        }
        function s() {
            AC._moveBy(new a(m * i.maxSpeed, e * i.maxSpeed))
        }
        function n(AG) {
            var AF = AG.charCode ? AG.charCode : AG.keyCode,AE = B.ShortCut,AD = [AE.UP_ARROW,AE.RIGHT_ARROW,AE.DOWN_ARROW,AE.LEFT_ARROW];
            if (AD.indexOf(AF) != -1) {
                if (AF & 1) {
                    m = 0
                } else {
                    e = 0
                }
                if (m || e) {
                    return
                }
            }
            v()
        }
        function v() {
            if (q) {
                window.clearInterval(q);
                q = null;
                AC._moveEnd();
                m = e = 0;
                u = i.startSpeed
            }
        }
        function p(AE) {
            var AD = (x || AC.getZoom()) + 1;
            if (o(AD)) {
                x = AD;
                z()
            }
        }
        function f(AE) {
            var AD = (x || AC.getZoom()) - 1;
            if (o(AD)) {
                x = AD;
                z()
            }
        }
        function o(AD) {
            var AE = AC.getType();
            return !(AD < AE.getMinZoom() || AD > AE.getMaxZoom(AC) || AD == AC.getZoom())
        }
        function z() {
            if (j) {
                window.clearTimeout(j)
            }
            j = window.setTimeout(l, i.zoomTimeout)
        }
        function l() {
            j = null;
            AC.setZoom(x);
            x = null
        }
        this.destructor = function() {
            this.onRemoveFromMap();
            Q.cleanup(t);
            for (var AE = 0,AD = AB.length; AE < AD; AE++) {
                B.ShortCut.remove(AB[AE])
            }
            B.Dom.removeNode(y);
            AC = y = AB = t = null
        }
    };
    U._LayerManager = function(f) {
        var d = c.create("div", {"class":"YMapsLayerContainer"});
        f.getContainer().appendChild(d);
        var e = [];
        this.getContainer = function() {
            return d
        };
        this.addLayer = function(g) {
            e.push(g);
            g.onAddToMap(f, d)
        };
        this.removeLayer = function(h) {
            var g = e.indexOf(h);
            if (g != -1) {
                e.splice(g, 1);
                h.onRemoveFromMap()
            }
        };
        this.update = function() {
            U.callMethod(e, "update")
        };
        this.onMove = function(g, h) {
            U.callMethod(e, "onMove", g, h)
        };
        this.onSmoothScrollingStart = function() {
            U.callMethod(e, "onSmoothScrollingStart")
        };
        this.onSmoothScrollingTick = function(g) {
            U.callMethod(e, "onSmoothScrollingTick", g)
        };
        this.onSmoothScrollingStop = function() {
            U.callMethod(e, "onSmoothScrollingStop")
        };
        this.getCopyrights = function() {
            var j = [],m = f.getBounds(),k = f.getZoom();
            for (var h = 0,g = e.length,n; h < g; h++) {
                if (n = e[h].getCopyright(m, k)) {
                    j.push(n)
                }
            }
            return j
        };
        this.destructor = function() {
            B.Dom.removeNode(d);
            d = e = createdLayers = null
        }
    };
    var E = U.Layers = new U._ObjectCollection();
    E.get = function(e) {
        var d = this._objects[e];
        return typeof d == "function" ? new d() : d || null
    };
    var E = U.MapType = function(f, e, d) {
        f = f.slice(0);
        this.getLayers = function() {
            return f.slice(0)
        };
        d = B.Utils.objectCopy(B.Utils.objectCopy({}, this._defaultOptions), d);
        d.name = e;
        function g(i, j) {
            j["get" + i.charAt(0).toUpperCase() + i.substr(1)] = function() {
                return d[i]
            }
        }
        for (var h in d) {
            g(h, this)
        }
    };
    E.prototype._defaultOptions = {textColor:"#000",shadowTextColor:"#FFF",minZoom:0,maxZoom:23};
    var G = U.__MapData,R = G.constants.types,M = U.MapType,H = {textColor:"#444",shadowTextColor:"#ddd",minZoom:4,maxZoom:17};
    function N(d, e) {
        M[d] = new M(e, R[d], H)
    }
    N("MAP", ["map"]);
    H.textColor = "#ddd";
    H.shadowTextColor = "#444";
    N("SATELLITE", ["sat"]);
    N("HYBRID", ["sat","skl"]);
    var W = new function() {
        var e = /%d/,d = /%c/;
        this.parseUrlTemplate = function(f, h, g) {
            return f.replace(e, (2 * (h.x & 1) + (h.y & 1) + 1)).replace(d, "x=" + h.x + "&y=" + h.y + "&z=" + g)
        }
    };
    var E = U.DataSource = function(f, e, d) {
        this._tileUrlTemplate = f;
        this._isTransparent = !!e;
        this._isPng = !!d
    };
    E.prototype = {getTileUrl:function(e, d) {
        return W.parseUrlTemplate(this._tileUrlTemplate, e, d)
    },getErrorTileUrl:function() {
        return"http://img.yandex.net/maps/i/404" + (this.isTransparent() ? "t" : "") + ".png"
    },isTransparent:function() {
        return this._isTransparent
    },isPng:function() {
        return this._isPng
    }};
    U._MapData = new function() {
        this.versionPath = G.versionPath;
        this.w1251 = G.w1251;
        this.constants = G.constants;
        this.userKey = G.userKey || 0;
        this.printerHost = G.printerHost;
        var h = new K(G.VendorsData),g = "http://base.wezen.maps.cloudkill.yandex.ru/tiles",f = function(l, k, j) {
            var i = function() {
                U.MapLayer.call(this, k);
                this.getCopyright = function(n, m) {
                    return h.getCopyrights(l, n, m)
                }
            };
            U.extend(i, U.MapLayer);
            U.Layers.add(l, i)
        };
        var e = G.tileUrlTemplates,d = G.DataVersions;
        f("map", new U.DataSource(e.map.replace("%c", "v=" + d.map + "&%c")));
        f("sat", new U.DataSource(e.sat.replace("%c", "v=" + d.sat + "&%c")));
        f("skl", new U.DataSource(e.skl.replace("%c", "v=" + d.skl + "&%c"), 1, 1));
        delete U.__MapData
    };
    function K(j) {
        var i = {setPoint:function(l) {
            this.lng = l.lng;
            this.lat = l.lat
        },check:function(l) {
            return((l.min[0] < this.lng) && (l.min[1] < this.lat) && (l.max[0] > this.lng) && (l.max[1] > this.lat))
        }};
        var e = {setBound:function(m) {
            var n = m.getLeftTop(),l = m.getRightBottom();
            this.lng1 = n.lng;
            this.lng2 = l.lng;
            this.lat1 = l.lat;
            this.lat2 = n.lat
        },check:function(l) {
            return l.min[0] < this.lng2 && l.min[1] < this.lat2 && l.max[0] > this.lng1 && l.max[1] > this.lat1
        }};
        function g(s, o) {
            var u = null,q = j.layers[s];
            if (q) {
                var t = q.refs;
                if (t) {
                    var r;
                    for (var p = 0,m = t[p],n = t.length; p < n; m = t[++p]) {
                        if (m.smax >= o && m.smin <= o) {
                            r = m;
                            break
                        }
                    }
                    if (r) {
                        q = j.layers[r.type]
                    }
                }
                u = q
            }
            return u
        }
        function k(n, t, p) {
            var u = [],s = [t],r;
            while (s.length) {
                r = s.pop();
                for (var q = 0,m,o = (r.nodes ? r.nodes.length : 0); q < o; q++) {
                    if (n.check(m = r.nodes[q])) {
                        s.push(m);
                        if (m.vid && m.smax >= p && m.smin <= p) {
                            u.push(m)
                        }
                    }
                }
            }
            return u
        }
        function f(n, q, p) {
            var m = g(p, q),l = k(n, m, q),r = l.length;
            for (var o = 0; o < r; o++) {
                d.push(l[o].vid)
            }
            return r
        }
        function h(o) {
            var q = o.getLeft(),n = o.getRight(),m = o.getBottom(),p = o.getTop(),l;
            if (n - q < 0) {
                l = [new U.Bounds(new P(q, m), new P(180, p)),new U.Bounds(new P(-180, m), new P(n, p))]
            } else {
                l = [o]
            }
            return l
        }
        var d = new function() {
            var l = {};
            this.push = function(m) {
                for (var o = 0,n = m.length; o < n; o++) {
                    l[m[o]] = 1
                }
            };
            this.getCopyrights = function() {
                var n = [];
                for (var m in l) {
                    n.push(j.vendors[m])
                }
                l = {};
                return n.join(", ")
            }
        };
        this.getCopyrights = function(r, o, q) {
            i.setPoint(o.getCenter());
            if (!f(i, q, r)) {
                var p = h(o);
                for (var n = 0,m = p.length; n < m; n++) {
                    e.setBound(p[n]);
                    f(e, q, r)
                }
            }
            return d.getCopyrights()
        }
    }
    U.Map = function(d) {
        if (!d) {
            return
        }
        this._construct(d);
        this.__copyrights = new U._Copyrights();
        this.addControl(this.__copyrights);
        this.enableDblClickZoom();
        this.enableDragging();
        this.__resizeListener = Q.domObserve(B.is_ie ? this._element : window, "resize", function() {
            this.redraw(1)
        }, this);
        if (B.is_safari || B.is_opera) {
            this.__loadListener = Q.domObserve(window, "load", function() {
                if (this._inited) {
                    this.redraw()
                }
            }, this)
        }
    };
    var C = U.Map.prototype;
    C.Events = {Update:"update",ChangeType:"changetype",ChangeTypeList:"changetypelist",AddLayer:"addlayer",RemoveLayer:"removelayer",_NeedRedraw:"_needredraw",MoveStart:"movestart",Move:"move",MoveEnd:"moveend",Click:"click",DblClick:"dblclick",ContextMenu:"contextmenu",MouseDown:"mousedown",DOMMouseScroll:"DOMMouseScroll",MouseUp:"mouseup",MouseMove:"mousemove"};
    C._construct = function(d) {
        this._inited = 0;
        this._element = d;
        B.Classes.add(this._element, "YMaps");
        this.__initPredefControls();
        this.__overlays = [];
        this.__createdLayers = {};
        this.__layers = [];
        var e = new a();
        this._state = {zoom:23,type:"",size:e,offset:new a(),domOffset:e,centerInTiles:e,centerInPixels:e};
        this.converter = new U.Converter(this);
        this.__layerManager = new U._LayerManager(this);
        this.__objectLayer = new U._CommonObjectLayer();
        this.addLayer(this.__objectLayer);
        this.__controlLayer = new U._ControlsLayer(this, this.__layerManager.getContainer());
        var f = U.MapType;
        this.__enabledMapTypes = [f.MAP,f.SATELLITE,f.HYBRID];
        this.setType(U.MapType.MAP)
    };
    C.__initPredefControls = function() {
        var d = {};
        ["Dragging","DblClickZoom","ScrollZoom","Magnifier","Ruler","HotKeys"].forEach(function(f) {
            d[f] = {enabled:0,control:null};
            var e;
            if (!this[e = "enable" + f]) {
                this[e] = function() {
                    this.__setEnabledControl(f, 1)
                }
            }
            if (!this[e = "disable" + f]) {
                this[e] = function() {
                    this.__setEnabledControl(f, 0)
                }
            }
            if (!this[e = f.charAt(0).toLowerCase() + f.substr(1) + "Enabled"]) {
                this[e] = function() {
                    return d[f].enabled
                }
            }
        }, this);
        this.__actionControls = d;
        this.__controls = [];
        this.__controlsPositions = []
    };
    C.__setEnabledControl = function(d, e) {
        var f = this.__actionControls[d];
        if (f && f.enabled != e) {
            if (e) {
                this.addControl(f.control || (f.control = new U["_" + d + "Control"]()))
            } else {
                if (f.enabled) {
                    this.removeControl(f.control)
                }
            }
            f.enabled = e
        }
    };
    C.enableMagnifier = function() {
        if (this.draggingEnabled()) {
            this.__actionControls.Dragging.control.onRemoveFromMap(this)
        }
        this.__setEnabledControl("Magnifier", 1)
    };
    C.disableMagnifier = function() {
        if (this.draggingEnabled()) {
            this.__actionControls.Dragging.control.onAddToMap(this)
        }
        this.__setEnabledControl("Magnifier", 0)
    };
    C.getRulerState = function() {
        return this.__actionControls.Ruler.control ? this.__actionControls.Ruler.control.getString() : ""
    };
    C.setRulerState = function(d) {
        if (!this.__actionControls.Ruler.control) {
            this.enableRuler();
            this.disableRuler()
        }
        this.__actionControls.Ruler.control.setString(d)
    };
    C.redraw = function(f) {
        if (this._inited) {
            var g,d;
            if (f) {
                var e = c.size(this._element);
                g = this.getCenter();
                d = this.converter.localPixelsToCoordinates(new a(Math.ceil(e.x / 2), Math.ceil(e.y / 2)))
            }
            if (this.__updateContainerSize()) {
                if (f) {
                    this.setCenter(d);
                    this.panTo(g)
                } else {
                    this.update()
                }
            }
        }
    };
    C.__updateContainerSize = function() {
        var d = c.size(this._element),e = B.Dom.getOffset(this._element);
        e = new a(e[0], e[1]);
        if (!this._state.size.equals(d) || !this._state.domOffset.equals(e)) {
            this._state.size = d;
            this._state.domOffset = e;
            this._state.centerInPixels = new a(Math.ceil(d.x / 2), Math.ceil(d.y / 2));
            return 1
        }
        return 0
    };
    C.listenMouseEvent = function(e, d) {
        this.__controlLayer.listenMouseEvent(e, d)
    };
    C._smoothScrollingStart = function() {
        this.__layerManager.onSmoothScrollingStart()
    };
    C._smoothScrollingTick = function(d) {
        this.__layerManager.onSmoothScrollingTick(d)
    };
    C._smoothScrollingStop = function(d, e) {
        this.__setZoom(e);
        this._state.centerInTiles = d;
        this.__layerManager.onSmoothScrollingStop();
        this.update()
    };
    C.panTo = function(d) {
        var e = this._state.centerInPixels.diff(this.converter.coordinatesToLocalPixels(d));
        if (3 * this._state.size.x < Math.abs(e.x) || 3 * this._state.size.y < Math.abs(e.y)) {
            this.setCenter(d)
        } else {
            new U.SmoothMoving(this, d)
        }
    };
    C._moveStart = function() {
        Q.notify(this.Events.MoveStart, this)
    };
    C._moveBy = function(d) {
        this._move(this.converter.pixelsToTileCoordinates(d))
    };
    C._move = function(d) {
        this._state.centerInTiles.moveBy(d);
        var e = this.converter.tileCoordinatesToPixels(this._state.centerInTiles.diff(this.__curTileCenter));
        if (e.x || e.y) {
            if (this._state.size.x < Math.abs(e.x) || this._state.size.y < Math.abs(e.y)) {
                this.update()
            } else {
                this.__curTileCenter.moveBy(this.converter.pixelsToTileCoordinates(e));
                e = e.neg();
                this._state.offset.moveBy(e);
                this.__layerManager.onMove(this._state.offset, e);
                Q.notify(this.Events.Move, this, d)
            }
        }
    };
    C._moveEnd = function() {
        Q.notify(this.Events.MoveEnd, this)
    };
    C.update = function() {
        this.__update();
        Q.notify(this.Events.Update, this, this)
    };
    C.__update = function() {
        this.__curTileCenter = this._state.centerInTiles.copy();
        this._state.offset.moveTo(new a());
        this.__layerManager.update()
    };
    C.setCenter = function(d, f, e) {
        this._state.centerInTiles = d.getTileCoordinates();
        if (typeof f != B.UNDEF) {
            this.__setZoom(f)
        }
        if (typeof e != B.UNDEF) {
            this.__setType(e)
        }
        if (!this._inited) {
            this.__init();
            this._inited = 1;
            this.__addLayers();
            this.__update();
            this.__addOverlays();
            this.__addControls()
        } else {
            this.update()
        }
    };
    C.__init = function() {
        this.__updateContainerSize();
        if (B.is_opera && B.opera_ver < 9.5) {
            var g = this,f = null,d = function() {
                B.Classes.toggle(g.__layerManager.getContainer(), "fakeClassName");
                f = null
            },e = function() {
                if (!f) {
                    f = window.setTimeout(d, 0)
                }
            };
            Q.observe(this, this.Events.Move, e, this);
            Q.observe(this, this.Events._NeedRedraw, e, this)
        }
    };
    C.getCenter = function() {
        return new P().setTileCoordinates(this._state.centerInTiles)
    };
    C.getBounds = function(g) {
        var f = this.getContainerSize(),d = this.converter.localPixelsToCoordinates(new a(0, f.y), g),e = this.converter.localPixelsToCoordinates(new a(f.x, 0), g);
        if (!g) {
            if (this.converter.getWorldSizeInPixels(this.getZoom()) < f.x) {
                d.setLng(-180);
                e.setLng(180)
            }
            if (e.lat > U.maxLatitude) {
                e.setLat(U.maxLatitude)
            }
            if (d.lat < U.minLatitude) {
                d.setLat(U.minLatitude)
            }
        }
        return new U.GeoBounds(d, e)
    };
    C.setBounds = function(d) {
        if (d instanceof U.GeoBounds) {
            if (!this._inited) {
                this.__updateContainerSize()
            }
            this.setCenter(d.getCenter(), d.getMapZoomLevel(this))
        }
    };
    C.setType = function(d) {
        if (this.__setType(d) && this._inited) {
            this.__layerManager.update();
            Q.notify(this.Events.ChangeType, this)
        }
    };
    C.__setType = function(e) {
        if (this.__enabledMapTypes.indexOf(e) != -1) {
            var d = this._state;
            if (e && !(d.type && d.type == e)) {
                if (d.type) {
                    this.__changeType("remove", d.type)
                }
                d.type = e;
                this.__changeType("add", e);
                this.setZoom(this.getZoom());
                return 1
            }
        }
    };
    C.__changeType = function(g, f) {
        g += "Layer";
        for (var e = 0,h = f.getLayers(),d = h.length; e < d; e++) {
            this.__layerManager[g](this.__prepareLayer(h[e]))
        }
    };
    C.getType = function() {
        return this._state.type
    };
    C.addType = function(d) {
        if (d && this.__enabledMapTypes.indexOf(d) == -1) {
            this.__enabledMapTypes.push(d);
            Q.notify(this.Events.ChangeTypeList, this)
        }
    };
    C.removeType = function(e) {
        var d,f = this.__enabledMapTypes;
        if (e && f.length > 1 && (d = f.indexOf(e)) != -1) {
            f.splice(d, 1);
            if (this._state.type == e) {
                this.setType(f[0])
            }
            Q.notify(this.Events.ChangeTypeList, this)
        }
    };
    C.getTypes = function() {
        return this.__enabledMapTypes.slice(0)
    };
    C.__prepareLayer = function(d) {
        return typeof d == "string" ? this.__createdLayers[d] || (this.__createdLayers[d] = U.Layers.get(d)) : d
    };
    C.addLayer = function(d) {
        this.__layers.push(d);
        if (this._inited) {
            this.__assignLayer(d, 1)
        }
    };
    C.__assignLayer = function(d, e) {
        d = this.__prepareLayer(d);
        this.__layerManager[(e ? "add" : "remove") + "Layer"](d);
        Q.notify(this.Events[(e ? "Add" : "Remove") + "Layer"], this, d)
    };
    C.__addLayers = function() {
        for (var e = 0,d = this.__layers.length; e < d; e++) {
            this.__assignLayer(this.__layers[e], 1)
        }
    };
    C.removeLayer = function(e) {
        var d = this.__layers.indexOf(e);
        if (d !== -1) {
            this.__layers.splice(d, 1);
            if (this._inited) {
                this.__assignLayer(e, 0)
            }
        }
    };
    C._getLayerCopyrights = function() {
        return this.__layerManager.getCopyrights()
    };
    C.setZoom = function(d) {
        if (!isNaN(Number(d)) && this.__setZoom(d) && this._inited) {
            this.update()
        }
    };
    C.__setZoom = function(e) {
        var f = this._state,d = f.type;
        e = U.toScale(U.boundaryRestrict(e, d.getMinZoom(), d.getMaxZoom()));
        if (f.zoom != e) {
            f.zoom = e;
            return 1
        }
    };
    C.getZoom = function() {
        return U.toScale(this._state.zoom)
    };
    C.getContainer = function() {
        return this._element
    };
    C.getContainerSize = function() {
        return this._state.size.copy()
    };
    C.addOverlay = function(d) {
        this.__overlays.push(d);
        if (this._inited) {
            this.__objectLayer.add(d)
        }
    };
    C.__addOverlays = function() {
        for (var e = 0,d = this.__overlays.length; e < d; e++) {
            this.__objectLayer.add(this.__overlays[e])
        }
    };
    C.removeOverlay = function(e) {
        var d = this.__overlays.indexOf(e);
        if (d !== -1) {
            this.__overlays.splice(d, 1);
            if (this._inited) {
                this.__objectLayer.remove(e)
            }
        }
    };
    C.removeAllOverlays = function() {
        for (var e = 0,f = this.__overlays.slice(0),d = f.length; e < d; e++) {
            this.removeOverlay(f[e])
        }
    };
    C.addControl = function(e, d) {
        this.__controls.push(e);
        this.__controlsPositions.push(d);
        if (this._inited) {
            e.onAddToMap(this, d)
        }
    };
    C.__addControls = function() {
        for (var e = 0,d = this.__controls.length; e < d; e++) {
            this.__controls[e].onAddToMap(this, this.__controlsPositions[e])
        }
    };
    C.removeControl = function(e) {
        var d = this.__controls.indexOf(e);
        if (d !== -1) {
            this.__controls.splice(d, 1);
            this.__controlsPositions.splice(d, 1);
            if (this._inited) {
                e.onRemoveFromMap()
            }
        }
    };
    C.openBalloon = function(e, i, g) {
        if (e) {
            this.closeBalloon();
            var d = this.__balloon;
            if (!d) {
                this.__balloon = d = new U.Balloon();
                this.__balloonCloseClickListener = Q.observe(d, d.Events.Close, this.closeBalloon, this)
            }
            this.__curBalloon = d;
            d.setGeoPoint(e.copy());
            d.setContent(i);
            d.setState(d.State.Normal);
            var h = this.getContainerSize(),f = B.Utils.objectCopy({layoutKey:"default#balloon",hasCloseButton:1,margin:23,maxWidth:320,maxHeight:(h.y > 250 ? h.y - 150 : 100),mapAutoPan:1}, g);
            this.__balloonCloseCallback = typeof f.onClose == "function" ? f.onClose : null;
            d.setOptions(f);
            d.updateView();
            this.addOverlay(d);
            if (f.mapAutoPan) {
                d.mapAutoPan()
            }
        }
    };
    C.closeBalloon = function() {
        if (this.__curBalloon) {
            if (this.__balloonCloseCallback) {
                this.__balloonCloseCallback()
            }
            this.__curBalloon = null;
            this.removeOverlay(this.__balloon)
        }
    };
    C.getBalloon = function() {
        return this.__curBalloon
    };
    C.addCopyright = function(d) {
        this.__copyrights.addCopyright(d)
    };
    C.removeCopyright = function(d) {
        this.__copyrights.removeCopyright(d)
    };
    C.destructor = function() {
        this.__balloonCloseCallback = null;
        this.closeBalloon();
        if (this.__balloon) {
            this.__balloonCloseClickListener.cleanup();
            this.__balloon.destructor()
        }
        this._destructor();
        this.__resizeListener.cleanup();
        if (this.__loadListener) {
            this.__loadListener.cleanup()
        }
        this.__copyrights = this.__balloon = this.__loadListener = this.__resizeListener = this.__balloonCloseClickListener = this.__curBalloon = null
    };
    C._destructor = function() {
        for (var d = 0; d < this.__controls.length; d++) {
            this.__controls[d].destructor()
        }
        for (var d = 0; d < this.__layers.length; d++) {
            this.__layers[d].destructor()
        }
        this.__controlLayer.destructor();
        this.__layerManager.destructor();
        B.Dom.removeNode(this._element);
        this.converter = this.__overlays = this._element = this.__controls = this.__actionControls = this.__controlsPositions = this.__layers = this._inited = this.__layerManager = this.__objectLayer = this._graphicsLayer = this.__controlLayer = this._state = null
    };
    U.TypeControl = function() {
        var e,h,g,o,k,l;
        this.onAddToMap = function(q, p) {
            e = q;
            h = p || new U.ControlPosition(U.ControlPosition.TOP_RIGHT);
            g = c.create("TABLE", {"class":"YMapsTypeControl"});
            e.getContainer().appendChild(g);
            o = g.insertRow(0);
            k = [];
            i();
            var r = e.Events;
            l = [Q.observe(e, r.ChangeType, f),Q.observe(e, r.ChangeTypeList, j)];
            h.apply(g);
            f()
        };
        function i() {
            for (var r = 0,q = e.getTypes(),p = q.length; r < p; r++) {
                k[r] = n(q[r])
            }
        }
        function n(q) {
            var t = c.create("TD");
            o.appendChild(t);
            var s = c.create("div");
            t.appendChild(s);
            var r = {container:t,mapType:q},p = r.a = c.create("a", {href:"javascript:void(0)"}, q.getName());
            s.appendChild(p);
            r.onClick = Q.domObserve(p, "click", function(u) {
                m(q);
                u.preventDefault()
            });
            return r
        }
        function m(p) {
            if (e.getType() != p) {
                e.setType(p);
                f()
            }
        }
        function f() {
            for (var r = 0,p = k.length,q,s = e.getType(); q = k[r++];) {
                q.a.className = q.mapType == s ? "active" : ""
            }
        }
        function j() {
            d();
            i();
            f()
        }
        function d() {
            for (var r = 0,p = k.length,q; q = k[r++];) {
                o.removeChild(q.container);
                q.onClick.cleanup();
                q.a = q.container = null
            }
            k.splice(0, k.length)
        }
        this.destructor = this.onRemoveFromMap = function() {
            if (e) {
                d();
                e.getContainer().removeChild(g);
                Q.cleanup(l);
                l = e = h = g = o = k = null
            }
        }
    };
    U.ScaleLine = function() {
        var g = 75;
        var e,i,m,h,o,l;
        this.onAddToMap = function(q, p) {
            e = q;
            i = p || new U.ControlPosition(U.ControlPosition.BOTTOM_RIGHT, new U.Size(10, 18));
            h = c.create("div", {"class":"YMapsScaleLine",style:"height:" + (U.Browsers.is_strict ? 3 : 5) + "px"});
            h.innerHTML = '<span class="YMapsTextShadow">0</span><span>0</span>';
            e.getContainer().appendChild(h);
            var r = h.getElementsByTagName("span");
            l = r[0];
            o = r[1];
            i.apply(h);
            f();
            k();
            var s = Q.observe,t = e.Events;
            m = [].concat(s(e, [t.Update,t.MoveEnd], k), s(e, t.ChangeType, f))
        };
        function f() {
            var p = e.getType();
            o.style.color = p.getTextColor();
            l.style.color = p.getShadowTextColor()
        }
        function k() {
            var q = n(g),p = d(q);
            h.style.width = parseInt(p * g / q) + "px";
            j(p > 1000 ? p / 1000 + " " + U._MapData.constants.metric.kilometre : p + " " + U._MapData.constants.metric.metre)
        }
        function n(r) {
            var q = new P().setTileCoordinates(new a(0, e._state.centerInTiles.y)),p = new P().setTileCoordinates(new a(r << e._state.zoom, e._state.centerInTiles.y));
            return q.distance(p)
        }
        function d(q) {
            var p = Math.round(q).toString();
            return(Number(p.charAt(0)) + 1) * Math.pow(10, p.length - 1)
        }
        function j(p) {
            o.innerHTML = p;
            l.innerHTML = p
        }
        this.destructor = this.onRemoveFromMap = function() {
            if (e) {
                e.getContainer().removeChild(h);
                Q.cleanup(m);
                e = i = h = o = l = m = null
            }
        }
    };
    U.Zoom = function() {
        var k = 300;
        var e,h,f,i = [],l,d,j;
        this.onAddToMap = function(n, m) {
            e = n;
            h = m || new U.ControlPosition(U.ControlPosition.TOP_LEFT, new U.Size(10, 50));
            g();
            i.push(Q.observe(e, e.Events.Update, function() {
                f.setValue(e.getZoom())
            }), Q.observe(e, e.Events.ChangeType, g, this), Q.observe(f.getContainer(), "sliderchange", function(o) {
                if (l) {
                    window.clearTimeout(l)
                }
                l = window.setTimeout(function() {
                    e.setZoom(o);
                    if (!B.is_safari) {
                        f.setFocus()
                    }
                }, k)
            }, this))
        };
        function g() {
            if (!f) {
                f = new U.Zoom.Slider();
                var o = f.getContainer();
                e.getContainer().appendChild(o);
                h.apply(o)
            }
            var q = e.getType(),p = q.getMaxZoom(),n = q.getMinZoom(),m = [];
            for (; p >= n; p--) {
                m.push(p)
            }
            f.update({value:e.getZoom(),values:m})
        }
        this.destructor = function() {
            if (l) {
                window.clearTimeout(l)
            }
            if (e) {
                e.getContainer().removeChild(f.getContainer());
                f.destructor();
                Q.cleanup(i);
                e = h = f = null
            }
        };
        this.onRemoveFromMap = this.destructor
    };
    U.Zoom.Slider = function(l) {
        var t,x,h;
        var AB,e,AH,u,AE,j,AF,y;
        var m,AG,r,o;
        var AA = [];
        var g = B.Dom;
        AB = c.create("div", {"class":"YMapsZoomControl"}, '<input class="Slider-Plus" type="image" src="http://info.maps.yandex.net/api/i/zoom/minus.gif" alt="-"/>        <div class="YMapsSliderScaleContainer">            <div class="Slider-Scale">                <div class="Slider-Grab"></div>                <div class="Slider-ActiveRegion"></div>            </div>        </div>        <div class="YMapsButtonContainer">            <button class="Slider-Button"></button>        </div>        <input class="Slider-Minus" type="image" src="http://info.maps.yandex.net/api/i/zoom/plus.gif" alt="+"/>');
        e = g.getElementByClass("Slider-Button", AB);
        AH = g.getElementByClass("Slider-ActiveRegion", AB);
        u = g.getElementByClass("Slider-Grab", AB);
        AF = g.getElementByClass("Slider-Scale", AB);
        AE = {input:g.getElementByClass("Slider-Plus", AB)};
        j = {input:g.getElementByClass("Slider-Minus", AB)};
        n(AE);
        n(j);
        y = new B.DragAndDrop.MouseMove(AH);
        z();
        k();
        c.css(u, "visibility", "visible");
        function n(AI) {
            var AJ = c.create("img");
            AJ.onerror = function() {
                AI.disabledSrc = AI.enabledSrc;
                AJ.onload()
            };
            AJ.onload = function() {
                AJ = AJ.onload = AJ.onerror = null
            };
            AI.enabledSrc = AI.input.src;
            AJ.src = AI.disabledSrc = AI.enabledSrc.replace(/\.([a-zA-Z]*)$/, "-disabled.$1")
        }
        function z() {
            AA.push(new B.Observer("y5:start", AD, y, 1), new B.Observer("y5:move", q, y, 1), new B.Observer("y5:stop", s, y, 1), Q.domObserve(AH, "click", p), Q.domObserve(AE.input, "click", function(AI) {
                AI.preventDefault();
                v()
            }), Q.domObserve(j.input, "click", function(AI) {
                AI.preventDefault();
                d()
            }))
        }
        function AD() {
            o = t
        }
        function q(AI) {
            w(Math.round((AG - AI.pageY + g.offsetTop(AH) - c.getSummaryScroll(AB.parentNode, g.getBody()).y) / r))
        }
        function w(AJ) {
            var AI = Math.max(0, Math.min(x, AJ));
            f(AE, (AI == x));
            f(j, (AI == 0));
            if (AI != x) {
                u.style.top = AG - Math.round(AI * r) - Math.round(m / 2) + "px"
            } else {
                u.style.top = -Math.round(m / 2) + "px"
            }
            t = AI
        }
        function f(AJ, AI) {
            AJ.input.src = AJ[(AI ? "dis" : "en") + "abledSrc"]
        }
        function s() {
            if (o != t) {
                Q.notify("sliderchange", AB, i());
                o = null
            }
        }
        function p(AI) {
            q(AI);
            s()
        }
        function d() {
            AC(t - 1)
        }
        function v() {
            AC(t + 1)
        }
        function AC(AI) {
            AD();
            w(AI);
            s()
        }
        function k() {
            function AJ() {
                AC(0)
            }
            function AI() {
                AC(x)
            }
            var AK = B.ShortCut;
            AA.push(AK.press([{key:AK.DOWN_ARROW}], d, e, 1), AK.down([{key:AK.PLUS_NUM}], d, e, 1), AK.press([{key:AK.UP_ARROW}], v, e, 1), AK.down([{key:AK.MINUS_NUM}], v, e, 1), AK.press([{key:AK.END}], AJ, e, 1), AK.press([{key:AK.HOME}], AI, e, 1))
        }
        this.update = function(AL) {
            h = AL.values;
            x = h.length - 1;
            var AI = (x + 1) * 13,AK = function(AM, AN) {
                AM.style.height = AI + AN + "px"
            };
            AK(AB, 16 * 2);
            AK(g.getElementByTagNameAndClass("div", "YMapsSliderScaleContainer", AB), -10);
            AK(AF, -10 - (U.Browsers.is_strict ? 2 : 0));
            AK(AH, -10 - (U.Browsers.is_strict ? 2 : 0));
            var AJ = g.getElementByTagNameAndClass("div", "YMapsButtonContainer", AB);
            AK(AJ, -10);
            AJ.style.marginTop = -(AI - 10) + "px";
            m = parseInt(g.getStyle(u).height) || u.offsetHeight;
            AG = parseInt(g.getStyle(AF).height) || AF.offsetHeight;
            r = (AG / x);
            this.setValue(AL.value);
            AB.style.display = "none";
            AB.style.display = "block"
        };
        this.destructor = function() {
            Q.cleanup(AA);
            AB = e = AH = u = AE = j = AF = y = null
        };
        this.setValue = function(AJ) {
            if (B.Types.number(AJ)) {
                var AI = h.indexOf(AJ);
                if (AI != -1) {
                    w(AI)
                }
            }
        };
        function i(AI) {
            return h[isNaN(AI = parseInt(AI)) ? t : AI]
        }
        this.getValue = i;
        this.setFocus = function() {
            e.focus()
        };
        this.getContainer = function() {
            return AB
        }
    };
    var E = U._AToolBar = function() {
        this._btns = []
    };
    var C = E.prototype;
    C.getContainer = function() {
        return this._element
    };
    C.onAddToMap = function(e, f) {
        this.map = e;
        var d = this._element = c.create("DIV", {"class":"YMapsToolBar"});
        if (B.is_opera) {
            d.style.opacity = 1
        }
        (f || new U.ControlPosition(U.ControlPosition.TOP_LEFT)).apply(d);
        e.getContainer().appendChild(d);
        U.callMethod(this._btns, "addToToolBar", this);
        if (this.__activeBtn) {
            this.__activeBtn.setActive(1)
        }
    };
    C.onRemoveFromMap = function() {
        if (this.map) {
            if (this.__activeBtn) {
                this.__activeBtn.setActive(0)
            }
            U.callMethod(this._btns, "removeFromToolBar");
            this.map.getContainer().removeChild(this._element);
            this._element = this.map = null
        }
    };
    C.destructor = function() {
        this.onRemoveFromMap();
        this.__activeBtn = this.__defActiveBtn = null
    };
    C.addButton = function(d) {
        this._btns.push(d);
        if (this.map) {
            d.addToToolBar(this)
        }
    };
    C.getButtonCount = function() {
        return this._btns.length
    };
    C.getButton = function(d) {
        return this._btns[d]
    };
    C.removeButton = function(e) {
        var d = this._btns.indexOf(e);
        if (d != -1) {
            this._btns.splice(d, 1);
            if (this.map) {
                e.removeFromToolBar()
            }
        }
    };
    C.setDefaultActiveButton = function(d) {
        this.__defActiveBtn = d;
        if (!this.__activeBtn) {
            this.setActiveButton(d)
        }
    };
    C.setActiveButton = function(d) {
        if (!this.map) {
            this.__activeBtn = d;
            return
        }
        if (this.__activeBtn) {
            this.__activeBtn.setActive(0);
            this.__activeBtn = null
        }
        if (d) {
            (this.__activeBtn = d).setActive(1)
        } else {
            if (this.__defActiveBtn) {
                this.setActiveButton(this.__defActiveBtn)
            }
        }
    };
    C.getActiveButton = function() {
        return this.__activeBtn
    };
    E = U._ToolBarButton = function(d, e) {
        this._active = 0;
        this._enabled = 1;
        this._cssClass = d;
        this._title = e || ""
    };
    C = E.prototype;
    C.Events = {ChangeActive:"ChangeActive"};
    C.activeCssClassName = "YMapsToolBarButtonActive";
    C.addToToolBar = function(d) {
        this._toolBar = d;
        var e = this._element = c.create("DIV", {"class":"YMapsToolBarButton",title:this._title});
        d.getContainer().appendChild(e);
        var f = c.create("DIV", {"class":this._cssClass});
        e.appendChild(f);
        this._onClickList = Q.domObserve(e, "click", this._onClick, this);
        this._applyActive(this._active);
        this._applyEnabled(this._enabled)
    };
    C.removeFromToolBar = function() {
        if (this._toolBar) {
            if (this._active) {
                this._toolBar.setActiveButton(null)
            }
            this._toolBar.getContainer().removeChild(this._element);
            this._onClickList.cleanup();
            this._onClickList = this._element = this._toolBar = null
        }
    };
    C._onClick = function(d) {
        if (!this._active) {
            this._toolBar.setActiveButton(this)
        }
    };
    C.setActive = function(d) {
        if (this._active != d) {
            this._active = d;
            if (this._toolBar) {
                this._applyActive(d)
            }
        }
    };
    C._applyActive = function(d) {
        B.Classes.assign(this._element, this.activeCssClassName, d);
        Q.notify(this.Events.ChangeActive, this, d)
    };
    C.getActive = function() {
        return this._active
    };
    C.setEnabled = function(d) {
        if (this._enabled != d) {
            this._enabled = d;
            if (this._toolBar) {
                this._applyEnabled(d)
            }
        }
    };
    C._applyEnabled = function(d) {
        this._onClickList[d ? "add" : "remove"]();
        B.Classes.assign(this._element, "YMapsToolBarButtonDisable", !d)
    };
    C.getEnabled = function() {
        return this._enabled
    };
    E = U.ToolBar = function() {
        U._AToolBar.call(this);
        var e = U._MapData.constants.hints.toolBar,d = new U._ToolBarButton("YMapsMoveButton", e.move);
        this.addButton(d);
        this.setDefaultActiveButton(d);
        d = new U._ToolBarButton("YMapsMagnifierButton", e.magnifier);
        Q.observe(d, d.Events.ChangeActive, function(f) {
            this.map[(f ? "en" : "dis") + "ableMagnifier"]()
        }, this);
        this.addButton(d);
        d = new U._ToolBarButton("YMapsRulerButton", e.ruler);
        Q.observe(d, d.Events.ChangeActive, function(f) {
            this.map[(f ? "en" : "dis") + "ableRuler"]()
        }, this);
        this.addButton(d)
    };
    U.extend(E, U._AToolBar);
    U._MagnifierControl = function(m) {
        var d,f,r,o,l,g;
        this.onAddToMap = function(s) {
            d = s;
            f = c.create("div", {"class":"YMapsSelectFrame",style:"display:none;"});
            d.getContainer().appendChild(f);
            r = B.ShortCut.down([{key:B.ShortCut.ESC}], q, document);
            o = {down:Q.observe(d, "mousedown", p, this, 0),move:Q.observe(d, "mousemove", e, this, 0),up:Q.observe(d, "mouseup", k, this, 0)};
            (this.onAddToMap = h)()
        };
        function h() {
            if (!m) {
                B.Classes.add(d.getContainer(), "cursor-zoom")
            }
            i("down", 1);
            r.add()
        }
        function i(t, s) {
            d.listenMouseEvent("mouse" + t, s);
            o[t][s ? "add" : "remove"]()
        }
        function p(t) {
            var s = t._event;
            if (m ? s.buttonR : s.buttonL) {
                s.stopPropagation();
                l = t.getLocalPixels();
                i("move", 1);
                i("up", 1)
            }
        }
        function e(w) {
            var v = w.getLocalPixels(),x = l.copy(),t = v.diff(x);
            var u = f.style;
            if (!t.x || !t.y) {
                u.display = "none";
                return
            }
            if (t.x < 0) {
                x.moveByX(t.x);
                t.scaleX(-1)
            }
            if (t.y < 0) {
                x.moveByY(t.y);
                t.scaleY(-1)
            }
            u.left = x.x + "px";
            u.top = x.y + "px";
            u.height = t.y + "px";
            u.width = t.x + "px";
            u.display = ""
        }
        function k(s) {
            j(s);
            n()
        }
        function q() {
            if (l) {
                n()
            }
        }
        function n() {
            i("move", 0);
            i("up", 0);
            f.style.display = "none";
            l = null
        }
        function j(s) {
            var z = s.getLocalPixels();
            if (z.x == l.x) {
                if (!m) {
                    d.setCenter(s.getGeoPoint(), d.getZoom() + 1)
                }
                return
            }
            var t = new U.CollectionBounds([l,z]),u = d.converter.localPixelsToCoordinates(t.getCenter()),v = d.getContainerSize(),y = t.getSpan(),x = v.x / y.x,w = v.x / y.y,AA = Math.floor(Math.log(Math.min(x, w)) / Math.LN2),AB = d.getZoom() + (AA > 0 ? AA : 0);
            d.setCenter(u, AB)
        }
        this.onRemoveFromMap = function() {
            if (l) {
                n()
            }
            if (!m) {
                B.Classes.remove(d.getContainer(), "cursor-zoom")
            }
            i("down", 0);
            r.remove()
        };
        this.destructor = function() {
            if (d) {
                this.onRemoveFromMap();
                B.ShortCut.remove(r);
                for (var s in o) {
                    o[s].cleanup()
                }
                g = l = o = r = f = d = null
            }
        }
    };
    U.MiniMap = function(u) {
        u = typeof u == B.UNDEF ? 5 : u;
        var h = U._MapData.constants.hints.miniMap,v,x,m,d,j,f,y,l,k,o,r,s,q,n,g = 1;
        function e(AA) {
            var z = new U.Layer();
            var AB = z.onAddToMap;
            z.onAddToMap = function(AD, AE) {
                AB.call(this, AD, AE);
                var AC = this._element;
                AC.className = "YMapsLayerFrame";
                AC.onselectstart = B.FALSE
            };
            z.update = function() {
                var AC = AA.getContainerSize().scale(1 / Math.pow(2, AA.getZoom() - f.getZoom())),AE = AC.copy().scale(-0.5),AD = this._element;
                c.size(AD, AC);
                c.css(AD, "margin", AE.y + "px 0 0 " + AE.x + "px")
            };
            z.onMove = B.NULL;
            return z
        }
        this.onAddToMap = function(AB, z) {
            v = AB;
            var AA = U.ControlPosition;
            x = z || new AA(AA.BOTTOM_LEFT);
            m = c.create("DIV", {"class":"YMapsMiniMap"});
            v.getContainer().appendChild(m);
            x.apply(m);
            d = c.create("a", {"class":"YMapsButtonMiniMapHide",title:h.hide});
            m.appendChild(d);
            Q.domObserve(d, "click", function() {
                this.setVisible(!this.getVisible())
            }, this);
            m.appendChild(j = c.create("DIV", {"class":"YMapsMiniMapMap"}));
            w()
        };
        function w() {
            if (U.is_safari && (j.offsetWidth == 0 || j.offsetHeight == 0)) {
                window.setTimeout(function() {
                    w()
                }, 100);
                return
            }
            function z(AA) {
                this._construct(AA);
                this.destructor = this._destructor
            }
            U.extend(z, U.Map);
            f = new z(j);
            f.enableDragging();
            f.addLayer(new e(v));
            t();
            i()
        }
        function t() {
            var z = v.Events;
            y = Q.observe(f, z.Click, function(AA) {
                v.panTo(AA.getGeoPoint())
            });
            o = Q.observe(v, z.Update, i);
            if (U.is_opera) {
                k = Q.observe(v, z.MoveEnd, i)
            } else {
                k = Q.observe(v, z.Move, function(AA) {
                    p([r,s]);
                    f._move(AA);
                    p([r,s], 1)
                })
            }
            r = Q.observe(f, z.Update, function() {
                p(o);
                v.setCenter(f.getCenter());
                p(o, 1)
            });
            s = Q.observe(f, z.Move, function(AA) {
                p(k);
                v._move(AA);
                p(k, 1)
            });
            q = Q.observe(f, z.MoveStart, function() {
                v._moveStart()
            });
            n = Q.observe(f, z.MoveEnd, function() {
                v._moveEnd()
            })
        }
        function p(AA, z) {
            U.callMethod(AA, (z ? "en" : "dis") + "able")
        }
        function i() {
            p(r);
            f.setCenter(v.getCenter(), v.getZoom() - u);
            p(r, 1)
        }
        this.setVisible = function(z) {
            if (z != g) {
                g = z;
                B.Classes.toggle(m, "YMapsMiniMapInvisible");
                d.title = h[g ? "hide" : "show"];
                if (g) {
                    i()
                }
                p([o,k], g);
                c.css(m, "zIndex", g ? "" : 1002)
            }
        };
        this.getVisible = function() {
            return g
        };
        this.destructor = this.onRemoveFromMap = function() {
            if (v) {
                Q.cleanup([y,l,o,r,k,s,q,n]);
                y = l = o = r = k = s = q = n = null;
                v.getContainer().removeChild(m);
                f.destructor();
                v = x = m = d = j = f = null
            }
        }
    };
    U.SmoothMoving = function(e, k, l) {
        l = B.Utils.objectCopy(l || {}, {acceleration:7,speed:37,timeout:20});
        var d = Math.max(l.acceleration, 1),j = k instanceof Array ? k : [k],f = j.pop(),g = e._state.centerInTiles.copy();
        e._moveStart();
        i();
        function i() {
            if (h()) {
                setTimeout(i, l.timeout)
            } else {
                e._moveEnd();
                e = f = g = j = null
            }
        }
        function h() {
            var o = e._state;
            if (g.equals(o.centerInTiles)) {
                if (d > 1) {
                    d--
                }
                var p = e.converter.nearestTileOffset(f),n = p.distance(new a()),m = e.converter.pixelsToTileCoordinates(new a(parseInt(l.speed / d), 0)).x;
                if (n > m) {
                    p.scale(m / n)
                }
                e._move(p);
                if (n > m || (f = j.pop())) {
                    g = o.centerInTiles.copy();
                    return 1
                }
            }
            return 0
        }
    };
    var E = U._CommonObjectLayer = function() {
        U.ObjectLayer.call(this)
    };
    var C = U.extend(E, U.ObjectLayer);
    C.onAddToMap = function(e, f) {
        U.ObjectLayer.prototype.onAddToMap.call(this, e, f);
        var d = this._element;
        B.Classes.add(d, "YMapsCommonObjectLayer");
        d.onmousedown = B.FALSE;
        d.onselectstart = B.FALSE;
        d.ondragstart = B.FALSE
    };
    var E = U.Templates = new U._ObjectCollection();
    var T = new RegExp("\\$\\[[^\\]]*\\]", "g"),D = new RegExp("[^\\$\\[\\]\\|\\s]+"),V = new RegExp("\\|([^\\]]*)");
    E.parseTemplate = function(e, d) {
        var f = this.get(e);
        if (f) {
            var g = c.create(J(f.text, d));
            if (g) {
                return g
            }
        }
        return null
    };
    function J(j, g) {
        var f = j.match(T);
        if (f) {
            for (var e = 0,d = f.length,h; e < d; e++) {
                h = O(f[e], g);
                if (h == null) {
                    h = f[e].match(V);
                    if (h && h.length == 2) {
                        h = h[1]
                    }
                }
                j = j.replace(f[e], h == null ? "" : h)
            }
        }
        return j
    }
    function O(h, d) {
        if (h) {
            var g = d,f = h.match(D)[0].split("."),e = "";
            while (f.length && g) {
                e = f.shift();
                if (e == "style" && g == d && typeof d._getStyle == "function") {
                    g = d._getStyle()
                } else {
                    g = g[e]
                }
            }
            return g
        }
        return null
    }
    var E = U.Styles = new U._ObjectCollection();
    E.DefaultStyleKey = "default#style";
    E.get = function(d) {
        var e = this._objects[d];
        if (!e) {
            e = this._objects[this.DefaultStyleKey]
        }
        e._resolveParent();
        return e
    };
    var E = U.Layouts = new U._ObjectCollection();
    E.get = function(e) {
        var g = this._objects[e];
        if (g) {
            var f = function() {
            };
            U.extend(f, g);
            var d = new f();
            g.apply(d, Array.prototype.slice.call(arguments, 1));
            return d
        }
        return null
    };
    U.Template = function(d) {
        this.id = d || "";
        this.text = ""
    };
    new function() {
        var d = new U.Template(),e = B.is_ie && (!B.is_ie7up || !U.Browsers.is_strict),f = function(g) {
            return e ? "filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='http://api-beta-maps.yandex.ru/i/small/$[color]/" + g + "', sizingMethod='crop');" : ""
        };
        d.text = "<div class='b-balloon-point'>        <div class='b-balloon b-balloon-small b-balloon-$[color]'>            <div class='b-balloon-wrap'>                <table class='b-balloon-frame'>                <tr>                    <td class='b-balloon-tl'><i style=\"background: url(http://api-beta-maps.yandex.ru/i/small/$[color]/t.png) no-repeat;\"><i style=\"" + f("t.png") + '"></i></i></td>                    <td class=\'b-balloon-t\' style="background: url(http://api-beta-maps.yandex.ru/i/small/$[color]/t.png) no-repeat 50% 0;"><i><i style="' + f("t.png") + '"></i></i></td>                    <td class=\'b-balloon-tr\'><i style="background: url(http://api-beta-maps.yandex.ru/i/small/$[color]/t.png) no-repeat 100% 0;"><i style="' + f("t.png") + "\"></i></i></td>                </tr>                <tr>                    <td class='b-balloon-ml' style=\"background: url(http://api-beta-maps.yandex.ru/i/small/$[color]/m.png) repeat-y;" + f("m-left.png") + "\"></td>                    <td class='b-balloon-m' style=\"$[contentCssStyle]\">                        <div class='b-balloon-content' style=\"background: url(http://api-beta-maps.yandex.ru/i/small/$[color]/bg.png) repeat-x 0 100%;\"></div>                    </td>                    <td class='b-balloon-mr' style=\"background: url(http://api-beta-maps.yandex.ru/i/small/$[color]/m.png) repeat-y 100% 0;" + f("m-right.png") + "\"></td>                </tr>                <tr>                    <td class='b-balloon-b' colspan='3'>                        <b style=\"background: url(http://api-beta-maps.yandex.ru/i/small/$[color]/b.png) no-repeat;" + f("b.png") + '"></b>                        <i><i style="background: url(http://api-beta-maps.yandex.ru/i/small/$[color]/b.png) no-repeat;' + f("b.png") + "\"></i></i>                    </td>                </tr>                </table>                <i class='b-balloon-shad'></i>            </div>            <i class='b-balloon-tail' style=\"background: url(http://api-beta-maps.yandex.ru/i/small/$[color]/tail.png) no-repeat;" + f("tail.png") + '"></i>        </div>    </div>';
        U.Templates.add("default#iconWithContent", d);
        d = new U.Template();
        d.text = '<div style="position:absolute; display:block;">        <img src=\'$[href]\' style="width:$[size.x]px; height:$[size.y]px;">    </div>';
        U.Templates.add("default#iconWithoutContent", d);
        d = new U.Template();
        d.text = "<div>        <img src='$[style.iconStyle.href]' style=\"width:$[style.iconStyle.size.x]px; height:$[style.iconStyle.size.y]px;\">    </div>";
        U.Templates.add("default#icon", d);
        d = new U.Template();
        d.text = "<div>        <img src='$[style.iconStyle.shadow.href]' style=\"width:$[style.iconStyle.shadow.size.x]px; height:$[style.iconStyle.shadow.size.y]px;\">    </div>";
        U.Templates.add("default#iconShadow", d);
        d = new U.Template();
        d.text = "";
        U.Templates.add("default#iconContent", d);
        d = new U.Template();
        d.text = "<div class='YMapsDefaultPopup'></div>";
        U.Templates.add("default#popup", d);
        d = new U.Template();
        d.text = "<div class='b-balloon b-balloon-hint'>        <table class='b-balloon-frame'>        <tr>            <td class='b-balloon-tl'><i><i></i></i></td>            <td class='b-balloon-t'><i><i></i></i></td>            <td class='b-balloon-tr'><i><i></i></i></td>        </tr>        <tr>            <td class='b-balloon-ml'></td>            <td class='b-balloon-m'>                <div class='b-balloon-content'></div>            </td>            <td class='b-balloon-mr'></td>        </tr>        <tr>            <td class='b-balloon-b' colspan='3'><b></b><i><i></i></i></td>        </tr>        </table>    </div>";
        U.Templates.add("default#placemarkHint", d);
        d = new U.Template();
        d.text = "<div>$[name]</div>";
        U.Templates.add("default#hintContent", d);
        d = new U.Template();
        d.text = "<div class='b-balloon-point'>        <div class='b-balloon b-balloon-full b-balloon-close-on'>            <div class='b-balloon-wrap'>                <table class='b-balloon-frame'>                <tr>                    <td class='b-balloon-tl'><i><i></i></i></td>                    <td class='b-balloon-t'><i><i></i></i></td>                    <td class='b-balloon-tr'><i><i></i></i></td>                </tr>                <tr>                    <td class='b-balloon-ml'></td>                    <td class='b-balloon-m'>                        <div class='b-balloon-content'></div>                    </td>                    <td class='b-balloon-mr'></td>                </tr>                <tr>                    <td class='b-balloon-b' colspan='3'><b></b><i><i></i></i></td>                </tr>                </table>                <i class='b-balloon-close'></i>                <i class='b-balloon-shad'></i>            </div>            <i class='b-balloon-tail'></i>        </div>    </div>";
        U.Templates.add("default#balloon", d);
        d = new U.Template();
        d.text = "<div>$[description]</div>";
        U.Templates.add("default#balloonContent", d)
    };
    var E = U.Style = function(e, d) {
        this._parentStyle = e;
        if (!d) {
            this._resolveParent()
        }
    };
    var C = E.prototype;
    C._resolveParent = function() {
        var d = this._parentStyle || "";
        delete this._parentStyle;
        if (!d || typeof d != "object") {
            d = U.Styles.get(d)
        }
        d = B.Utils.objectCopy(d);
        for (var e in d) {
            if (typeof this[e] == B.UNDEF) {
                this[e] = d[e]
            }
        }
        this._resolveParent = B.NULL
    };
    U.ABaseStyleType = function(d) {
        this.templateKey = d
    };
    U.AIconStyle = function(d) {
        U.ABaseStyleType.call(this, d);
        this.href = null;
        this.size = null;
        this.offset = new a()
    };
    U.IconStyle = function() {
        U.AIconStyle.call(this, "default#icon");
        this.shadow = null
    };
    U.ShadowStyle = function() {
        U.AIconStyle.call(this, "default#iconShadow")
    };
    U.IconContentStyle = function() {
        U.ABaseStyleType.call(this, "default#iconContent")
    };
    U.BalloonContentStyle = function() {
        U.ABaseStyleType.call(this, "default#balloonContent")
    };
    U.HintContentStyle = function() {
        U.ABaseStyleType.call(this, "default#hintContent")
    };
    U.LineStyle = function() {
        this.strokeColor = "ff0000";
        this.strokeWidth = 1
    };
    U.PolygonStyle = function() {
        U.LineStyle.call(this);
        this.fillColor = "ff0000";
        this.fill = 1;
        this.outline = 1
    };
    new function() {
        var d = function(e) {
            var f = new U.Style(0, 1);
            f._resolveParent = B.NULL;
            f.hasHint = 0;
            f.hasBalloon = 1;
            f.iconLayoutKey = "default#" + e + "PointIcon";
            f.iconContentStyle = new U.IconContentStyle();
            f.hintLayoutKey = "default#placemarkHint";
            f.hintContentStyle = new U.HintContentStyle();
            f.balloonLayoutKey = "default#balloon";
            f.balloonContentStyle = new U.BalloonContentStyle();
            f.lineStyle = new U.LineStyle();
            U.Styles.add("default#" + e + "Point", f);
            return f
        };
        d("white");
        d("green");
        d("red");
        d("yellow");
        d("darkblue");
        d("night");
        d("grey");
        d("blue");
        d("orange");
        d("darkorange");
        d("pink");
        d("violet");
        U.Styles.add(U.Styles.DefaultStyleKey, d("lightblue"))
    };
    U.ALayout = function(d) {
        if (!d) {
            return
        }
        this.templateKey = null;
        this.context = d;
        this.topNode = null;
        this.nodes = {}
    };
    var C = U.ALayout.prototype;
    C.build = function() {
        if (this.templateKey) {
            this.topNode = U.Templates.parseTemplate(this.templateKey, this.context)
        }
    };
    C.getContainer = function() {
        return this.topNode
    };
    C.show = function() {
        if (this.topNode && this.topNode.style.display == "none") {
            this.topNode.style.display = this.__display || ""
        }
    };
    C.hide = function() {
        var d = this.topNode.style.display;
        if (this.topNode && d != "none") {
            this.__display = d;
            this.topNode.style.display = "none"
        }
    };
    C.destructor = function() {
        for (var d in this.nodes) {
            this.nodes[d] = null
        }
        B.Dom.removeNode(this.topNode);
        this.topNode = this.nodes = this.context = this.templateKey = null
    };
    var E = U.Popup = function(e) {
        e = e || {};
        var d = e.viewportNode,f = this;
        this._layoutKey = e.layoutKey || this.DEFAULT_LAYOUT_KEY;
        this._vpNode = B.Types.node(d) ? d : B.Dom.getBody();
        this._vpPaddings = this.DEFAULT_VIEWPORT_PADDINGS;
        if (typeof e.viewportPaddings != B.UNDEF) {
            this.__setViewportPaddings.apply(this, [].concat(e.viewportPaddings))
        }
        this._maxSize = [0,0];
        this.__actualMaxSize = [0,0];
        if (e.maxWidth || e.maxHeight) {
            this.__setMaxSize(e.maxWidth || 0, e.maxHeight || 0)
        }
        this.__showed = 0;
        this._recalcActualMaxSize();
        this._showTimeout = this.DEFAULT_SHOW_TIMEOUT;
        if (typeof e.showTimeout != B.UNDEF) {
            this._setShowTimeout(e.showTimeout)
        }
        this._hideTimeout = this.DEFAULT_HIDE_TIMEOUT;
        if (typeof e.hideTimeout != B.UNDEF) {
            this._setHideTimeout(e.hideTimeout)
        }
        this.__onShowTimeout = function() {
            f.quickShow()
        };
        this.__onHideTimeout = function() {
            f.quickHide()
        }
    };
    var C = E.prototype;
    C.DEFAULT_LAYOUT_KEY = "default#popup";
    C.DEFAULT_SHOW_TIMEOUT = 250;
    C.DEFAULT_HIDE_TIMEOUT = 700;
    C.DEFAULT_VIEWPORT_PADDINGS = [0,0,0,0];
    C.setPosition = function(d) {
        if (d instanceof a) {
            this._position = d.copy();
            if (this._layout && this.__showed) {
                this.__applyPosition()
            }
        }
    };
    C.getPosition = function() {
        return this._position
    };
    C.__applyPosition = function() {
        if (this._position) {
            var d = this._getActualViewportBounds(),i = this._position.copy(),g = d.getLeft(),k = d.getTop(),l = d.getRight(),e = d.getBottom(),j = c.size(this._layout.getContainer()),h = i.x + j.x,f = i.y + j.y;
            if (h > l) {
                i.x -= h - l
            } else {
                if (i.x < g) {
                    i.x += g - i.x
                }
            }
            if (f > e) {
                i.y -= f - e
            } else {
                if (i.y < k) {
                    i.y += k - i.y
                }
            }
            this._layout.moveTo(i)
        }
    };
    C._getActualViewportBounds = function() {
        var d = this._vpNode,e = this._vpPaddings,p = e[0],h = e[1],g = e[2],i = e[3],l = this.__getViewportScroll(),j = d.clientWidth,n = d.clientHeight,f = i + h,o = p + g;
        if (f > j) {
            var m = Math.ceil((f - j) / 2);
            i -= m;
            h -= m
        }
        if (o > n) {
            var k = Math.ceil((o - n) / 2);
            p -= k;
            g -= k
        }
        return new U.Bounds(new a(i + l.x, -g + n + l.y), new a(-h + j + l.x, p + l.y))
    };
    C.__getViewportScroll = function() {
        var g = this._vpNode,d = B.Dom.getBody(),f = g == d ? B.Dom.getPageScrollX() : g.scrollLeft,e = g == d ? B.Dom.getPageScrollY() : g.scrollTop;
        return new a(f, e)
    };
    C._setLayoutKey = function(d) {
        if (d && this._layoutKey != d && U.Layouts.get(d, this)) {
            this._layoutKey = d;
            this.updateView()
        }
    };
    C.updateView = function() {
        if (this.__showed) {
            this._recreate()
        } else {
            this._removeLayout()
        }
    };
    C._recreate = function() {
        this._removeLayout();
        this._createLayout();
        if (this._layout) {
            this.__rewriteContent();
            this._layout.setMaxSize.apply(this._layout, this.__actualMaxSize);
            if (this.__showed) {
                this.__showLayout()
            }
        }
    };
    C._removeLayout = function() {
        this.__clearEvents();
        if (this._layout) {
            this._layout.destructor();
            this._layout = null
        }
    };
    C._createLayout = function() {
        this._layout = U.Layouts.get(this._layoutKey, this);
        if (this._layout) {
            this._layout.build();
            this.__createEvents();
            B.Classes.add(this._layout.getContainer(), "YMapsPopup")
        }
    };
    C.__showLayout = function() {
        var d = this._layout.getContainer();
        d.style.visibility = "hidden";
        this._vpNode.appendChild(d);
        this._layout.update();
        this.__applyPosition();
        d.style.visibility = "visible"
    };
    C.__clearEvents = function() {
        if (this.__onMouseOverListener) {
            this.__onMouseOverListener.cleanup()
        }
        if (this.__onMouseOutListener) {
            this.__onMouseOutListener.cleanup()
        }
        this.__onMouseOverListener = this.__onMouseOutListener = null
    };
    C.__createEvents = function() {
        this.__onMouseOverListener = Q.domObserve(this._layout.getContainer(), "mouseover", this._onMouseOver, this, 0);
        this.__onMouseOutListener = Q.domObserve(this._vpNode, "mouseover", this._onMouseOut, this, 0)
    };
    C.setContent = function(d) {
        this._content = this._normalizeContent(d);
        if (this._layout) {
            this.__rewriteContent();
            if (this.__showed) {
                this._layout.update();
                this.__applyPosition()
            }
        }
    };
    C.getContent = function() {
        return this._content
    };
    C._normalizeContent = function(d) {
        if (!(B.Types.node(d) || d == null || d == B.UNDEF)) {
            d = c.create("div", null, d)
        }
        return d || null
    };
    C.__rewriteContent = function() {
        var d = this._layout.getContentContainer();
        B.Dom.clearNode(d);
        if (this._content) {
            d.appendChild(this._content)
        } else {
            this._hide()
        }
    };
    C.setOptions = function(e) {
        if (e) {
            var d = 0,g = {};
            for (var f in e) {
                g[f] = typeof e[f] != B.UNDEF
            }
            if (g.layoutKey) {
                this._setLayoutKey(e.layoutKey)
            }
            if (g.viewportNode) {
                this._setViewportNode(e.viewportNode)
            }
            if (g.viewportPaddings) {
                d = this.__setViewportPaddings.apply(this, [].concat(e.viewportPaddings))
            }
            if (g.maxWidth || g.maxHeight) {
                d = this.__setMaxSize(e.maxWidth || 0, e.maxHeight || 0)
            }
            if (d) {
                this._recalcActualMaxSize()
            }
            if (g.showTimeout) {
                this._setShowTimeout(e.showTimeout)
            }
            if (g.hideTimeout) {
                this._setHideTimeout(e.hideTimeout)
            }
        }
    };
    C.getOptions = function() {
        return{layoutKey:this._layoutKey,viewportNode:this._vpNode,viewportPaddings:this._vpPaddings,maxWidth:this._maxSize[0],maxHeight:this._maxSize[1],showTimeout:this._showTimeout,hideTimeout:this._hideTimeout}
    };
    C._setViewportNode = function(d) {
        if (B.Types.node(d)) {
            this._vpNode = d;
            this._recalcActualMaxSize();
            if (this._layout && this.__showed) {
                this.__showLayout()
            }
        }
    };
    C.__setViewportPaddings = function(e, g, f, d) {
        if (typeof e == "number") {
            this._vpPaddings = [e,typeof g == "number" ? g : g = e,typeof f == "number" ? f : f = e,typeof d == "number" ? d : d = g];
            return 1
        }
        return 0
    };
    C.__setMaxSize = function(f, e) {
        f = Math.max(f, 0) || 0;
        e = Math.max(e, 0) || 0;
        var d = 0;
        if (this._maxSize[0] != f) {
            this._maxSize[0] = f;
            d = 1
        }
        if (this._maxSize[1] != e) {
            this._maxSize[1] = e;
            d = 1
        }
        return d
    };
    C._recalcActualMaxSize = function() {
        var d = this._getActualViewportBounds();
        this.__actualMaxSize = [Math.min(d.getRight() - d.getLeft(), this._maxSize[0] || Infinity),Math.min(d.getBottom() - d.getTop(), this._maxSize[1] || Infinity)];
        if (this._layout) {
            this._layout.setMaxSize.apply(this._layout, this.__actualMaxSize);
            if (this.__showed) {
                this._layout.update();
                this.__applyPosition()
            }
        }
    };
    C._setShowTimeout = function(d) {
        if (typeof d == "number" && !isNaN(d)) {
            this._showTimeout = d
        }
    };
    C._setHideTimeout = function(d) {
        if (typeof d == "number" && !isNaN(d)) {
            this._hideTimeout = d
        }
    };
    C._clearAllTimeouts = function() {
        this._clearShowTimeout();
        this._clearHideTimeout()
    };
    C._clearShowTimeout = function() {
        if (this.__showTimeoutID) {
            window.clearTimeout(this.__showTimeoutID);
            this.__showTimeoutID = null
        }
    };
    C._clearHideTimeout = function() {
        if (this.__hideTimeoutID) {
            window.clearTimeout(this.__hideTimeoutID);
            this.__hideTimeoutID = null
        }
    };
    C.show = function(d) {
        this._clearHideTimeout();
        if (d) {
            this.setPosition(d)
        }
        if (!this.__showed && !this.__showTimeoutID) {
            this.__showTimeoutID = window.setTimeout(this.__onShowTimeout, this._showTimeout)
        }
    };
    C.quickShow = function(d) {
        this._clearAllTimeouts();
        if (d) {
            this.setPosition(d)
        }
        if (this.__showed || !this._content) {
            return
        }
        this.__showed = 1;
        if (this._layout) {
            this.__showLayout()
        } else {
            this._recreate()
        }
        this.__onMouseOverListener.add()
    };
    C.hide = function() {
        this._clearShowTimeout();
        if (this.__showed && !this.__hideTimeoutID) {
            this.__hideTimeoutID = window.setTimeout(this.__onHideTimeout, this._hideTimeout)
        }
    };
    C.quickHide = function() {
        this._clearAllTimeouts();
        if (this.__showed) {
            if (this.__mouseOver) {
                this.__pendingHide = 1
            } else {
                this._hide()
            }
        }
    };
    C._hide = function() {
        if (this.__showed) {
            this.__showed = 0;
            this.__onMouseOverListener.remove();
            this.__onMouseOutListener.remove();
            this._vpNode.removeChild(this._layout.getContainer())
        }
    };
    C.isShown = function() {
        return this.__showed
    };
    C._onMouseOver = function(d) {
        this.__mouseOver = 1;
        d.stopPropagation();
        this.__onMouseOutListener.add()
    };
    C._onMouseOut = function(d) {
        this.__mouseOver = 0;
        this.__onMouseOutListener.remove();
        if (this.__pendingHide) {
            this.__pendingHide = 0;
            this._hide()
        }
    };
    C.destructor = function() {
        this._clearAllTimeouts();
        this._removeLayout();
        this._vpNode = null
    };
    E = U.APopupLayout = function(d) {
        U.ALayout.call(this, d)
    };
    C = U.extend(E, U.ALayout);
    C.update = B.NULL;
    C.setMaxSize = B.NULL;
    C.getContentContainer = function() {
        return this.topNode
    };
    C.moveTo = function(d) {
        c.position(this.topNode, d)
    };
    new function() {
        var e = function(f) {
            U.APopupLayout.apply(this, arguments);
            this.templateKey = "default#popup";
            this._maxSize = [0,0]
        },d = U.extend(e, U.APopupLayout);
        d.update = function() {
            var h = c,j = this._maxSize,f = 8;
            h.css(this.topNode, {top:-10000,left:-10000,width:"",height:"",overflow:"hidden"});
            var g = this.topNode.offsetWidth;
            if (j[0] && g > j[0]) {
                h.css(this.topNode, "width", j[0] - f)
            }
            var i = this.topNode.offsetHeight;
            if (j[1] && i > j[1]) {
                h.css(this.topNode, "height", j[1] - f)
            }
        };
        d.setMaxSize = function(g, f) {
            this._maxSize = [g,f]
        };
        U.Layouts.add("default#popup", e)
    };
    (function() {
        var l = function() {
            this.__popup = new U.Popup();
            this._owner = null
        };
        var d = l.prototype;
        d.setOwner = function(i) {
            if (this._owner != i) {
                this.__popup.quickHide();
                this.__popup.setOptions({layoutKey:"default#placemarkHint",viewportNode:B.Dom.getBody(),viewportPaddings:0,showTimeout:250,hideTimeout:700,maxWidth:750,maxHeight:300});
                this.__popup.setContent()
            }
            this._owner = i
        };
        d.getOwner = function(i) {
            return this._owner
        };
        var e = ["updateView","setContent","getContent","setPosition","getPosition","setOptions","getOptions","show","quickShow","hide","quickHide","isShown"],k = function(i) {
            return function(j) {
                return this._owner == j ? this.__popup[i].apply(this.__popup, Array.prototype.slice.call(arguments, 1)) : null
            }
        };
        for (var g = 0,f = e.length; g < f; g++) {
            d[e[g]] = k(e[g])
        }
        var h = function(i) {
            U.APopupLayout.apply(this, arguments);
            this.templateKey = "default#placemarkHint";
            this._maxSize = [0,0]
        };
        d = U.extend(h, U.APopupLayout);
        d.build = function() {
            U.APopupLayout.prototype.build.call(this);
            this.nodes = {content:B.Dom.getElementByClass("b-balloon-content", this.topNode),table:B.Dom.getElementByClass("b-balloon-frame", this.topNode)}
        };
        d.getContentContainer = function() {
            return this.nodes.content
        };
        d.update = function() {
            var j = c,p = this._maxSize;
            j.position(this.topNode, -10000);
            j.css(this.topNode, "width", "");
            j.css(this.nodes.content, {height:"",overflow:"hidden"});
            var i = this.nodes.table.offsetWidth;
            if (p[0] && i > p[0]) {
                j.css(this.topNode, "width", p[0])
            }
            var o = this.nodes.table.offsetHeight;
            if (p[1] && o > p[1]) {
                var n = p[1] - (o - this.nodes.content.offsetHeight);
                j.css(this.nodes.content, "height", Math.max(n, 0))
            }
        };
        d.setMaxSize = function(j, i) {
            this._maxSize = [j,i]
        };
        U.Layouts.add("default#placemarkHint", h);
        U.PlacemarkHint = {};
        var m;
        U.PlacemarkHint.getHintInstance = function() {
            return m ? m : (m = new l())
        }
    })();
    var E = U.Balloon = function(d) {
        d = d || {};
        this._layoutKey = d.layoutKey || this.DEFAULT_LAYOUT_KEY;
        this._maxSize = [0,0];
        if (d.maxWidth || d.maxHeight) {
            this.__setMaxSize(d.maxWidth || 0, d.maxHeight || 0)
        }
        this._layoutListeners = [];
        this._state = this.State.Normal;
        this._hasCloseButton = typeof d.hasCloseButton == B.UNDEF ? 1 : d.hasCloseButton;
        this._margin = this.MARGIN;
        if (typeof d.margin != B.UNDEF) {
            this.__setMargin.apply(this, [].concat(d.margin))
        }
    };
    var C = E.prototype;
    C.State = {Normal:1,Maximized:2};
    C.Events = {Close:"Close",Restore:"Restore",Maximize:"Maximize"};
    C.MARGIN = [23,23,23,23];
    C.DEFAULT_LAYOUT_KEY = "default#balloon";
    C.setOptions = function(d) {
        if (d) {
            var f = {};
            for (var e in d) {
                f[e] = typeof d[e] != B.UNDEF
            }
            if (f.layoutKey) {
                this._setLayoutKey(d.layoutKey)
            }
            if (f.maxWidth || f.maxHeight) {
                this._setMaxSize(d.maxWidth || 0, d.maxHeight || 0)
            }
            if (f.hasCloseButton) {
                this._setHasCloseButton(d.hasCloseButton)
            }
            if (f.margin) {
                this.__setMargin.apply(this, [].concat(d.margin))
            }
        }
    };
    C.getOptions = function() {
        return{layoutKey:this._layoutKey,maxWidth:this._maxSize[0],maxHeight:this._maxSize[1],hasCloseButton:this._hasCloseButton,margin:this._margin}
    };
    C._setMaxSize = function(e, d) {
        if (this.__setMaxSize(e, d) && this._layout) {
            this.__updateLayoutMaxSize()
        }
    };
    C.__setMaxSize = function(f, e) {
        f = Math.max(f, 0) || 0;
        e = Math.max(e, 0) || 0;
        var d = 0;
        if (this._maxSize[0] != f) {
            this._maxSize[0] = f;
            d = 1
        }
        if (this._maxSize[1] != e) {
            this._maxSize[1] = e;
            d = 1
        }
        return d
    };
    C.__updateLayoutMaxSize = function() {
        this._layout.setMaxSize(this._maxSize[0], this._maxSize[1]);
        this._layout.update();
        this.update()
    };
    C.__setMargin = function(f, e, g, d) {
        if (typeof f == "number") {
            this._margin = [f,typeof e == "number" ? e : e = f,typeof g == "number" ? g : g = f,typeof d == "number" ? d : d = e]
        }
    };
    C.setState = function(d) {
        switch (d) {case this.State.Normal:this._restore();break;case this.State.Maximized:this._maximize();break;default:return }
        this._state = d
    };
    C.getState = function() {
        return this._state
    };
    C._maximize = function() {
        Q.notify(this.Events.Maximize, this)
    };
    C._restore = function() {
        Q.notify(this.Events.Restore, this)
    };
    C.setContent = function(d) {
        this._content = this._normalizeContent(d);
        if (this._layout) {
            this.__rewriteContent();
            if (this._parentContainer) {
                this._layout.update();
                this.update()
            }
        }
    };
    C.getContent = function() {
        return this._content
    };
    C._normalizeContent = function(d) {
        if (!(B.Types.node(d) || d == null || d == B.UNDEF)) {
            d = c.create("div", null, d)
        }
        return d || null
    };
    C.__rewriteContent = function() {
        var d = this._layout.getContentContainer();
        B.Dom.clearNode(d);
        if (this._content) {
            d.appendChild(this._content)
        }
    };
    C.setGeoPoint = function(d) {
        this._point = d;
        this.update()
    };
    C.getGeoPoint = function() {
        return this._point
    };
    C._setLayoutKey = function(d) {
        if (d && this._layoutKey != d && U.Layouts.get(d, this)) {
            this._layoutKey = d;
            this.updateView()
        }
    };
    C.updateView = function() {
        this.__clearLayout();
        if (this._parentContainer) {
            this.__createLayout();
            this._layout.setMaxSize(this._maxSize[0], this._maxSize[1]);
            this.__rewriteContent();
            this.__applyHasCloseButton();
            this.__addToParentContainer();
            this._layout.update();
            this.update()
        }
    };
    C.__addToParentContainer = function() {
        if (this._layout.getContainer().parentNode != this._parentContainer) {
            this._parentContainer.appendChild(this._layout.getContainer())
        }
    };
    C.update = function() {
        if (this._parentContainer) {
            if (this._point) {
                this.__posInPixels = this._map.converter.coordinatesToMapPixels(this._point);
                this._layout.moveTo(this.__posInPixels)
            } else {
                throw"Balloon position point is not defined."
            }
        }
    };
    C.__clearLayout = function() {
        if (this._layout) {
            this._removeLayoutListeners();
            this._layout.destructor();
            this._layout = this.__writtenContent = null
        }
    };
    C.__createLayout = function() {
        this._layout = U.Layouts.get(this._layoutKey, this);
        if (this._layout) {
            this._layout.build();
            this._setLayoutListeners();
            B.Classes.add(this._layout.getContainer(), "YMapsBalloon")
        }
    };
    C._removeLayoutListeners = function() {
        Q.cleanup(this._layoutListeners)
    };
    C._setLayoutListeners = function() {
        var f = this._layout,d = f.getContainer(),g = f.getEventBlockerNode(),e = f.getCloseNode();
        this._layoutListeners.concat(Q.domObserve(g, ["dragstart","selectstart","mousedown","click","dblclick","DOMMouseScroll"], function(h) {
            h.stopPropagation()
        }));
        this._layoutListeners.push(Q.domObserve(d, "contextmenu", function(h) {
            if (!h.dontHandle) {
                h.stopPropagation();
                h.preventDefault()
            }
        }));
        this._layoutListeners.push(Q.domObserve(f.getContentContainer(), "contextmenu", B.is_gecko ? function(h) {
            h.dontHandle = true
        } : function(h) {
            h.stopPropagation()
        }));
        if (e) {
            this._layoutListeners.push(Q.domObserve(e, "click", this._onCloseClick, this))
        }
    };
    C._onCloseClick = function(d) {
        Q.notify(this.Events.Close, this)
    };
    C._setHasCloseButton = function(d) {
        if (typeof d != B.UNDEF) {
            this._hasCloseButton = d;
            if (this._layout) {
                this.__applyHasCloseButton()
            }
        }
    };
    C.__applyHasCloseButton = function() {
        if (this._hasCloseButton) {
            this._layout.enableClose()
        } else {
            this._layout.disableClose()
        }
    };
    C.onAddToMap = function(f, g) {
        this._map = f;
        this._parentContainer = g;
        if (this._layout) {
            this.__addToParentContainer();
            this._layout.update();
            this.update()
        } else {
            this.updateView()
        }
        if (B.is_gecko) {
            this.ffObservers = [];
            var d,e = this._layout.getContentContainer();
            this.ffObservers.push(Q.observe(this._map, this._map.Events.MoveStart, function() {
                d = e.style.overflow;
                e.style.overflow = "hidden"
            }, this));
            this.ffObservers.push(Q.observe(this._map, this._map.Events.MoveEnd, function() {
                e.style.overflow = d
            }, this))
        }
        this.__mapMoveEndListener = Q.observe(this._map, this._map.Events.MoveEnd, this._onMapMoveEnd, this)
    };
    C._onMapMoveEnd = function() {
        if (this.__posInPixels && !this._point.isUnbounded()) {
            var e = this._map.converter.localPixelsToMapPixels(this._map._state.centerInPixels),d = this._map.converter.getWorldSizeInPixels() / 2;
            if (Math.abs(this.__posInPixels.x - e.x) > d) {
                this.update()
            }
        }
    };
    C.onRemoveFromMap = function() {
        B.Dom.removeNode(this._layout.getContainer());
        this._parentContainer = this._map = null;
        if (this._autoPanTimeout) {
            window.clearTimeout(this._autoPanTimeout)
        }
        if (this.ffObservers) {
            Q.cleanup(this.ffObservers);
            this.ffObservers = null
        }
        if (this.__mapMoveEndListener) {
            this.__mapMoveEndListener.cleanup();
            this.__mapMoveEndListener = null
        }
    };
    C.mapAutoPan = function() {
        if (this._map) {
            if (B.is_safari && (this._layout.getContainer().offsetParent == document.body || !this._layout.getAnchorOffset())) {
                var j = this;
                this._autoPanTimeout = window.setTimeout(function() {
                    j._autoPanTimeout = null;
                    j.mapAutoPan()
                }, 100);
                return
            }
            var k = this._map.converter.coordinatesToLocalPixels(this._point),g = this._margin,n = this._layout.getSize(),h = this._layout.getAnchorOffset();
            k.moveBy(h.neg());
            var l = k.y - g[0],m = k.x + n.x + g[1],d = k.y + n.y + g[2],f = k.x - g[3],i = new a();
            if (f <= 0) {
                i.moveToX(-1 * f)
            } else {
                if (m > this._map._state.size.x) {
                    i.moveToX(this._map._state.size.x - m)
                }
            }
            if (l <= 0) {
                i.moveToY(-1 * l)
            } else {
                if (d > this._map._state.size.y) {
                    i.moveToY(this._map._state.size.y - d)
                }
            }
            if (i.x || i.y) {
                var e = this._map._state.centerInPixels.diff(i);
                this._map.panTo(this._map.converter.localPixelsToCoordinates(e))
            }
        }
    };
    C.destructor = function() {
        this.onRemoveFromMap();
        this.__clearLayout();
        this._content = null
    };
    E = U.ABalloonLayout = function(d) {
        U.ALayout.apply(this, arguments)
    };
    C = U.extend(E, U.ALayout);
    C.update = B.NULL;
    C.moveTo = function(d) {
        c.position(this.topNode, d)
    };
    C.setMaxSize = B.NULL;
    C.getAnchorOffset = function() {
        return new a()
    };
    C.getSize = function() {
        return c.size(this.topNode)
    };
    C.enableClose = B.NULL;
    C.disableClose = B.NULL;
    C.getContentContainer = function() {
        throw'Balloon layout "getContentContainer" method is not defined.'
    };
    C.getEventBlockerNode = C.getContainer;
    C.getCloseNode = B.NULL;
    new function() {
        var e = function(f) {
            U.ABalloonLayout.apply(this, arguments);
            this.templateKey = "default#balloon"
        };
        var d = e.prototype = new U.ABalloonLayout();
        d.build = function() {
            U.ABalloonLayout.prototype.build.call(this);
            this._getNodes();
            this.nodes.close.title = U._MapData.constants.hints.balloon.close
        };
        d._getNodes = function() {
            this.nodes = {visContainer:B.Dom.getElementByClass("b-balloon", this.topNode),contentParent:B.Dom.getElementByClass("b-balloon-m", this.topNode),content:B.Dom.getElementByClass("b-balloon-content", this.topNode),close:B.Dom.getElementByClass("b-balloon-close", this.topNode),eventBlockNode:B.Dom.getElementByClass("b-balloon-frame", this.topNode)}
        };
        d.getContentContainer = function() {
            return this.nodes.content
        };
        d.getCloseNode = function() {
            return this.nodes.close
        };
        d.getEventBlockerNode = function() {
            return this.nodes.eventBlockNode
        };
        d.update = function() {
            var g = c,i = this.nodes.visContainer,j = this.nodes.content,l,k,m = this._maxSize[0],n = this._maxSize[1];
            g.size(i, 10000);
            g.css(i, "visibility", "hidden");
            g.css(j, {overflow:"auto",width:"auto",height:"auto"});
            if (B.is_opera) {
                g.css(i, "overflow", "hidden");
                g.css(j, "overflow", "hidden")
            }
            if (m != null) {
                g.css(j, "maxWidth", m);
                l = Math.max(j.scrollWidth, j.offsetWidth) > m;
                if (l && B.is_ie) {
                    g.css(j, "width", m)
                }
            }
            if (n != null) {
                g.css(j, "maxHeight", n);
                k = Math.max(j.scrollHeight, j.offsetHeight) > n;
                if (k && B.is_ie) {
                    g.css(j, "height", n)
                }
            }
            if (l) {
                if (B.is_opera) {
                    g.css(j, "overflow", "auto")
                }
                if (B.is_ie) {
                    var p = j.offsetHeight - j.clientHeight;
                    g.css(j, "height", j.offsetHeight + p)
                }
            }
            var f = c.size(j);
            if (B.is_safari && (this.topNode.offsetParent == document.body)) {
                var h = this;
                this._updateTimeout = window.setTimeout(function() {
                    h._updateTimeout = null;
                    h.update()
                }, 100);
                return
            }
            if (f.y < 15) {
                f.y = 15
            }
            g.css(j, "overflow", l || k ? "auto" : "");
            g.size(j, f);
            var o = this.nodes.contentParent.offsetWidth - f.x;
            if (f.x + o < 52) {
                f.x = 52 - o;
                g.css(j, "width", f.x)
            }
            if (B.is_opera) {
                g.css(i, "overflow", "")
            }
            g.css(i, {width:"",height:"",visibility:""})
        };
        d.getAnchorOffset = function() {
            var f = this.getSize();
            return new a(f.x - 44, f.y - 1)
        };
        d.getSize = function() {
            return c.size(this.nodes.visContainer).moveByY(37)
        };
        d.setMaxSize = function(g, f) {
            this._maxSize = [g || 760,f || 760]
        };
        d.enableClose = function() {
            B.Classes.add(this.nodes.visContainer, "b-balloon-close-on");
            this.update()
        };
        d.disableClose = function() {
            B.Classes.remove(this.nodes.visContainer, "b-balloon-close-on");
            this.update()
        };
        d.destructor = function() {
            U.ABalloonLayout.prototype.destructor.call(this);
            if (this._updateTimeout) {
                window.clearTimeout(this._updateTimeout)
            }
        };
        U.Layouts.add("default#balloon", e)
    };
    var E = U.Placemark = function(d, e) {
        this.metaDataProperty = {};
        e = e || {};
        this.setGeoPoint(d);
        this.__style = e.style;
        this.__draggable = !!e.draggable;
        this.__needIconUpdate = 0;
        this.__needIconContentUpdate = 0;
        this._hintOptions = e.hintOptions || {};
        this._hintOffset = this._hintOptions.hintOffset || new a(15, 15);
        if (typeof e.hasHint != B.UNDEF) {
            this._hasHint = e.hasHint
        }
        this._balloonOptions = e.balloonOptions || {};
        if (typeof e.hasBalloon != B.UNDEF) {
            this._hasBalloon = e.hasBalloon
        }
        this.__iconEventListeners = []
    };
    var C = E.prototype;
    C.isPlacemark = 1;
    C.Events = {Click:"Click",DblClick:"DblClick",DragStart:"DragStart",Drag:"Drag",DragEnd:"DragEnd",MouseOver:"MouseOver",MouseOut:"MouseOut",MouseMove:"MouseMove",PositionChange:"PositionChange",BalloonOpen:"BalloonOpen",BalloonClose:"BalloonClose"};
    C.onAddToGroup = function(d) {
        this.__parentGroup = d
    };
    C.getParentGroup = function() {
        return this.__parentGroup
    };
    C.onAddToMap = function(d, e) {
        this._map = d;
        this._parentContainer = e;
        if (this._icon) {
            this.__addIconEventListeners();
            this.__addIconNodes();
            this._icon.setContent(this.getIconContent());
            this.__updateIconAndShadow();
            this.update()
        } else {
            this._recreateIcon()
        }
        this.__mapMoveEndListener = Q.observe(this._map, this._map.Events.MoveEnd, this._onMapMoveEnd, this)
    };
    C.__updateIconAndShadow = function() {
        this._icon.update();
        if (this._iconShadow) {
            this._iconShadow.update()
        }
    };
    C.onRemoveFromMap = function() {
        this.closeBalloon();
        this._hideHintIfVisible();
        this.__removeIconEventListeners();
        this.__mapMoveEndListener.cleanup();
        if (this._icon) {
            B.Dom.removeNode(this._icon.getContainer())
        }
        if (this._iconShadow) {
            B.Dom.removeNode(this._iconShadow.getContainer())
        }
        this.__mapMoveEndListener = this._map = this._parentContainer = null
    };
    C.update = function() {
        if (this._parentContainer) {
            if (this._point) {
                this.__posInPixels = this._map.converter.coordinatesToMapPixels(this._point);
                if (this._icon) {
                    this._icon.moveTo(this.__posInPixels.copy());
                    if (this._iconShadow) {
                        this._iconShadow.moveTo(this.__posInPixels.copy())
                    }
                }
                if (this._balloonVisible) {
                    this._map.getBalloon().setGeoPoint(this._point)
                }
            } else {
                throw"Placemark position point is not defined."
            }
        }
    };
    C._onMapMoveEnd = function() {
        if (this.__posInPixels && !this._point.isUnbounded()) {
            var e = this._map.converter.localPixelsToMapPixels(this._map._state.centerInPixels),d = this._map.converter.getWorldSizeInPixels() / 2;
            if (Math.abs(this.__posInPixels.x - e.x) > d) {
                this.update()
            }
        }
    };
    C.setStyle = function(d) {
        this.__style = d;
        this.__currentStyle = null;
        this.updateView()
    };
    C.getStyle = function() {
        return this.__style
    };
    C._getCurStyle = function() {
        if (!this.__currentStyle) {
            this.__currentStyle = B.Utils.objectCopy(this._getStyle())
        }
        return this.__currentStyle
    };
    C._getStyle = function() {
        var d = this,e;
        while (!e && d) {
            e = d.getStyle();
            d = d.getParentGroup()
        }
        return(e && typeof e == "object" ? e : U.Styles.get(e))
    };
    C.__overwriteStyleProperty = function(d, e) {
        this._getCurStyle()[d] = e || this._getStyle()[d]
    };
    C.updateView = function() {
        this._recreateIcon();
        this._updateHint();
        this._updateBalloon()
    };
    C._recreateIcon = function() {
        this.__clearIcon();
        if (this._parentContainer) {
            if (!this._balloonVisible) {
                this.__createIcon();
                this._icon.setContent(this.getIconContent());
                this.__addIconNodes();
                this.__updateIconAndShadow();
                this.update()
            } else {
                this.__needIconUpdate = 1
            }
        }
    };
    C.__addIconNodes = function() {
        var d = this._icon.getContainer(),e = this._iconShadow ? this._iconShadow.getContainer() : null;
        if (e && e.parentNode != this._parentContainer) {
            this._parentContainer.appendChild(e)
        }
        if (d.parentNode != this._parentContainer) {
            this._parentContainer.appendChild(d)
        }
    };
    C.__clearIcon = function() {
        this.__cleanupIconEventListeners();
        if (this._icon) {
            this._icon.destructor()
        }
        if (this._iconShadow) {
            this._iconShadow.destructor()
        }
        this._icon = this._iconShadow = null
    };
    C.__createIcon = function() {
        var d = this._getCurStyle().iconStyle;
        if (d) {
            this._icon = U.Layouts.get("default#icon", this, d);
            if (d.shadow) {
                this._iconShadow = U.Layouts.get("default#icon", this, d.shadow);
                this._iconShadow.build()
            }
        } else {
            this._icon = U.Layouts.get(this._getCurStyle().iconLayoutKey, this)
        }
        this._icon.build();
        B.Classes.add(this._icon.getContainer(), "Placemark");
        if (this._iconShadow) {
            B.Classes.add(this._iconShadow.getContainer(), "PlacemarkShadow")
        }
        this.__createIconEventListeners()
    };
    C.setIconContent = function(d) {
        this._iconCustomContent = this._normalizeContent(d);
        this._updateIconContent()
    };
    C._normalizeContent = function(d) {
        if (!(B.Types.node(d) || d == null || d == B.UNDEF)) {
            d = c.create("div", null, d)
        }
        return d || null
    };
    C._updateIconContent = function() {
        this._iconContent = null;
        if (this._parentContainer) {
            if (!this._balloonVisible) {
                this._icon.setContent(this.getIconContent());
                this.__updateIconAndShadow();
                this.update()
            } else {
                this.__needIconContentUpdate = 1
            }
        }
    };
    C.getIconContent = function() {
        if (!this._iconContent) {
            this._createIconContent()
        }
        return this._iconContent
    };
    C._createIconContent = function() {
        this._iconContent = this._iconCustomContent || U.Templates.parseTemplate(this._getCurStyle().iconContentStyle.templateKey, this)
    };
    C.__createIconEventListeners = function() {
        var e = this._icon.getContainer(),d = new B.DragAndDrop.MouseMove(e);
        this.__iconEventListeners = [].concat(Q.domObserve(e, "dblclick", this._onDblClick, this), Q.domObserve(e, ["click","contextmenu"], function(f) {
            f.stopPropagation()
        }, this), Q.domObserve(e, "mousemove", this._onMouseMove, this), Q.domObserve(e, "mouseover", this._onMouseOver, this), Q.domObserve(e, "mouseout", this._onMouseOut, this), Q.observe(d, "y5:move", this._move, this), Q.observe(d, "y5:start", this._startMove, this), Q.observe(d, "y5:stop", this._stopMove, this))
    };
    C.__cleanupIconEventListeners = function() {
        U.callMethod(this.__iconEventListeners, "cleanup");
        this.__iconEventListeners.length = 0
    };
    C.__removeIconEventListeners = function() {
        U.callMethod(this.__iconEventListeners, "remove")
    };
    C.__addIconEventListeners = function() {
        U.callMethod(this.__iconEventListeners, "add")
    };
    C._onDblClick = function(d) {
        d.stopPropagation();
        d.preventDefault();
        Q.notify(this.Events.DblClick, this, this)
    };
    C._startMove = function(d) {
        d.stopPropagation();
        d.preventDefault();
        if (this.__draggable) {
            this.__startMovePos = this._point;
            this.__grabOffset = this._map.converter.coordinatesToClientPixels(this._point).diff(new a(d.clientX, d.clientY))
        }
    };
    C._move = function(d) {
        d.stopPropagation();
        d.preventDefault();
        if (this.__draggable) {
            if (!this.__isDragged) {
                this.__isDragged = 1;
                this._hideHintIfVisible();
                Q.notify(this.Events.DragStart, this, this)
            }
            this._point = this._map.converter.clientPixelsToCoordinates((new a(d.clientX, d.clientY)).moveBy(this.__grabOffset), this._point.isUnbounded());
            this.update();
            Q.notify(this.Events.Drag, this, this)
        }
    };
    C._stopMove = function(d) {
        d.stopPropagation();
        d.preventDefault();
        if (this.__draggable && this.__isDragged) {
            this.__isDragged = 0;
            Q.notify(this.Events.DragEnd, this, this);
            Q.notify(this.Events.PositionChange, this, {object:this,oldPoint:this.__startMovePos,newPoint:this._point})
        } else {
            if (this._balloonVisible) {
                this.closeBalloon()
            } else {
                this.openBalloon()
            }
            Q.notify(this.Events.Click, this, this)
        }
    };
    C._onMouseMove = function(d) {
        this._mouseMoveAction(d);
        Q.notify(this.Events.MouseMove, this, this)
    };
    C._onMouseOver = function(d) {
        if (!B.Dom.isChild(d.relatedTarget, this._icon.getContainer())) {
            this.__enableHover();
            if (this.hasHint() && !this._hintVisible && !this.__isDragged) {
                var f = this._hint || (this._hint = U.PlacemarkHint.getHintInstance());
                if (f.getOwner() != this) {
                    f.setOwner(this)
                }
                this.__applyHintOptions();
                f.setContent(this, this.getHintContent());
                this._mouseMoveAction(d);
                f.show(this);
                this._hintVisible = 1
            }
            Q.notify(this.Events.MouseOver, this, this)
        }
    };
    C._onMouseOut = function(d) {
        if (!B.Dom.isChild(d.relatedTarget, this._icon.getContainer())) {
            this.__disableHover();
            if (this._hintVisible) {
                this._hint.hide(this);
                this._hintVisible = 0
            }
            Q.notify(this.Events.MouseOut, this, this)
        }
    };
    C.__enableHover = function() {
        B.Classes.add(this._icon.getContainer(), "Hover");
        if (this._iconShadow) {
            B.Classes.add(this._iconShadow.getContainer(), "Hover")
        }
    };
    C.__disableHover = function() {
        B.Classes.remove(this._icon.getContainer(), "Hover");
        if (this._iconShadow) {
            B.Classes.remove(this._iconShadow.getContainer(), "Hover")
        }
    };
    C._mouseMoveAction = function(i) {
        if (this._hintVisible) {
            var g = this._hint.getOptions(this).viewportNode,k = B.Dom,f = k.getBody(),j = k.getOffset(g),h = k.getOffset(f),d = c.getSummaryScroll(g, f);
            j = new a(j[0], j[1]);
            h = new a(h[0], h[1]);
            this._hint.setPosition(this, new a(i.pageX, i.pageY).diff(j).moveBy(h).moveBy(d).moveBy(this._hintOffset))
        }
    };
    C.enableHint = function() {
        this._hasHint = 1
    };
    C.disableHint = function() {
        this._hasHint = 0;
        this._hideHintIfVisible()
    };
    C.hasHint = function() {
        return typeof this._hasHint == B.UNDEF ? this._getCurStyle().hasHint : this._hasHint
    };
    C.setHintContent = function(d) {
        this._hintCustomContent = this._normalizeContent(d);
        this._updateHint()
    };
    C._createHintContent = function() {
        this._hintContent = this._hintCustomContent || U.Templates.parseTemplate(this._getCurStyle().hintContentStyle.templateKey, this)
    };
    C.getHintContent = function() {
        if (!this._hintContent) {
            this._createHintContent()
        }
        return this._hintContent
    };
    C.setHintOptions = function(d) {
        this._hintOptions = B.Utils.objectCopy(this._hintOptions, d);
        this._hintOffset = this._hintOptions.hintOffset || new a(15, 15);
        if (this._hintVisible) {
            this.__applyHintOptions()
        }
    };
    C.__applyHintOptions = function() {
        var d = B.Utils.objectCopy(this._hintOptions);
        if (typeof d.layoutKey == B.UNDEF) {
            d.layoutKey = this._getCurStyle().hintLayoutKey
        }
        this._hint.setOptions(this, d)
    };
    C._updateHint = function() {
        this._hintContent = null;
        if (this._hintVisible) {
            this._hint.setContent(this, this.getHintContent())
        }
    };
    C._hideHintIfVisible = function() {
        if (this._hintVisible) {
            this._hint.quickHide(this);
            this._hintVisible = 0
        }
    };
    C._updateBalloon = function() {
        this._balloonContent = null;
        if (this._balloonVisible) {
            var d = this._map.getBalloon();
            d.setContent(this.getBalloonContent());
            this.__applyBalloonOptions();
            d.update()
        }
    };
    C.openBalloon = function(e, d) {
        if (typeof e != B.UNDEF) {
            this.setBalloonContent(e)
        }
        if (d) {
            this.setBalloonOptions(d)
        }
        if (this._map && this.hasBalloon()) {
            this._hideHintIfVisible();
            var f = this;
            d = B.Utils.objectCopy(this._balloonOptions);
            d.onClose = function() {
                if (f._balloonOptions.onClose) {
                    f._balloonOptions.onClose()
                }
                f._onCloseBalloon()
            };
            d.layoutKey = d.layoutKey || this._getCurStyle().balloonLayoutKey;
            this._map.openBalloon(this._point, this.getBalloonContent(), d);
            this._balloonVisible = 1;
            this._icon.hide();
            if (this._iconShadow) {
                this._iconShadow.hide()
            }
            Q.notify(this.Events.BalloonOpen, this, {object:this})
        }
    };
    C.closeBalloon = function() {
        if (this._balloonVisible) {
            this._map.closeBalloon();
            this._balloonVisible = 0
        }
    };
    C.setBalloonOptions = function(d) {
        this._balloonOptions = B.Utils.objectCopy(this._balloonOptions, d);
        if (this._balloonVisible) {
            this.__applyBalloonOptions()
        }
    };
    C.__applyBalloonOptions = function() {
        var d = B.Utils.objectCopy(this._balloonOptions);
        if (typeof d.layoutKey == B.UNDEF) {
            d.layoutKey = this._getCurStyle().balloonLayoutKey
        }
        this._map.getBalloon().setOptions(d)
    };
    C.enableBalloon = function() {
        this._hasBalloon = 1
    };
    C.disableBalloon = function() {
        this._hasBalloon = 0;
        this.closeBalloon()
    };
    C.hasBalloon = function() {
        return typeof this._hasBalloon == B.UNDEF ? this._getCurStyle().hasBalloon : this._hasBalloon
    };
    C._onCloseBalloon = function() {
        this._balloonVisible = 0;
        if (this.__needIconUpdate) {
            this._recreateIcon();
            this.__needIconUpdate = 0
        } else {
            this._icon.show();
            if (this._iconShadow) {
                this._iconShadow.show()
            }
            if (this.__needIconContentUpdate) {
                this._updateIconContent();
                this.__needIconContentUpdate = 0
            }
        }
        Q.notify(this.Events.BalloonClose, this, {object:this})
    };
    C.setBalloonContent = function(d) {
        this._balloonCustomContent = this._normalizeContent(d);
        this._updateBalloon()
    };
    C.getBalloonContent = function() {
        if (!this._balloonContent) {
            this._createBalloonContent()
        }
        return this._balloonContent
    };
    C._createBalloonContent = function() {
        this._balloonContent = this._balloonCustomContent || U.Templates.parseTemplate(this._getCurStyle().balloonContentStyle.templateKey, this)
    };
    C.setGeoPoint = function(d) {
        if (d instanceof P) {
            var e = this._point || d;
            this._point = d.copy();
            this.update();
            Q.notify(this.Events.PositionChange, this, {object:this,oldPoint:e,newPoint:d})
        }
    };
    C.getGeoPoint = function() {
        return this._point
    };
    C.setDraggable = function(d) {
        this.__draggable = d
    };
    C.getDraggable = function() {
        return this.__draggable
    };
    C.destructor = function() {
        this._hideHintIfVisible();
        this.__clearIcon();
        this.__mapMoveEndListener.cleanup();
        this.__mapMoveEndListener = this._map = this._parentContainer = null
    };
    E = U.APlacemarkIconLayout = function(d) {
        U.ALayout.apply(this, arguments)
    };
    C = U.extend(E, U.ALayout);
    C.update = B.NULL;
    C.moveTo = function(d) {
        c.position(this.topNode, d)
    };
    C.setContent = B.NULL;
    new function() {
        var d = function(o, j, n, g) {
            var h = 790,f = 300,i = 20,m = 15;
            var k = function(p) {
                U.APlacemarkIconLayout.apply(this, arguments);
                this.templateKey = "default#iconWithContent"
            },e = U.extend(k, U.APlacemarkIconLayout);
            e.build = function() {
                this.topNode = U.Templates.parseTemplate(this.templateKey, {context:this.context,color:j,contentCssStyle:g || ""});
                this.nodes = {visContainer:B.Dom.getElementByClass("b-balloon", this.topNode),content:B.Dom.getElementByClass("b-balloon-content", this.topNode)}
            };
            e.setContent = function(q) {
                var p = this.nodes.content;
                B.Dom.clearNode(p);
                if (B.Types.node(q)) {
                    p.appendChild(q)
                }
            };
            e.update = function() {
                var q = c;
                q.css(this.nodes.visContainer, {visibility:"hidden",width:h,height:f});
                if (B.is_opera) {
                    q.css(this.nodes.visContainer, "overflow", "hidden")
                }
                q.css(this.nodes.content, {overflow:"",width:"auto",height:"auto"});
                var p = c.size(this.nodes.content);
                q.size(this.nodes.content, new a(p.x > h ? h : (p.x < i ? i : p.x), p.y > f ? f : (p.y < m ? m : p.y)));
                q.css(this.nodes.content, "overflow", "hidden");
                if (B.is_opera) {
                    q.css(this.nodes.visContainer, "overflow", "")
                }
                q.css(this.nodes.visContainer, {width:"",height:"",visibility:""})
            };
            var l = function(q) {
                U.APlacemarkIconLayout.apply(this, arguments);
                this._content = null;
                var p = (this._noContentStyle = new U.IconStyle());
                p.templateKey = "default#iconWithoutContent";
                p.offset = new a(-7, -28);
                p.href = "http://api-beta-maps.yandex.ru/i/micro/pm" + n + "s.png";
                p.size = new a(28, 29);
                this._withContentLayout = new k(q);
                this._noContentLayout = U.Layouts.get("default#icon", this._noContentStyle, this._noContentStyle)
            },e = U.extend(l, U.APlacemarkIconLayout);
            e.build = function() {
                this.topNode = c.create("div");
                this._withContentLayout.build();
                this._noContentLayout.build();
                this._withContentLayout.moveTo(new a());
                this._noContentLayout.moveTo(new a())
            };
            e.update = function() {
                var p = this._noContentLayout.getContainer(),q = this._withContentLayout.getContainer();
                B.Dom.clearNode(this.topNode);
                if (this._content) {
                    this.topNode.appendChild(this._withContentLayout.getContainer());
                    this._withContentLayout.setContent(this._content);
                    this._withContentLayout.update()
                } else {
                    this.topNode.appendChild(this._noContentLayout.getContainer());
                    this._noContentLayout.update()
                }
            };
            e.setContent = function(p) {
                this._content = p
            };
            e.destructor = function() {
                this._withContentLayout.destructor();
                this._noContentLayout.destructor();
                U.APlacemarkIconLayout.prototype.destructor.call(this)
            };
            U.Layouts.add("default#" + o + "PointIcon", l)
        };
        d("white", "white", "wt");
        d("green", "green", "gn");
        d("red", "red", "rd");
        d("yellow", "yellow", "yw");
        d("darkblue", "blue-d", "db");
        d("night", "night", "nt", "background: #4068f9; color: #fff;");
        d("grey", "grey", "gr");
        d("blue", "blue", "bl");
        d("orange", "orange", "or");
        d("darkorange", "orange-d", "do");
        d("pink", "pink", "pn");
        d("violet", "violet", "vv");
        d("lightblue", "blue-l", "lb")
    };
    E = U.Placemark._SimpleIconOrShadowLayout = function(d, e) {
        U.APlacemarkIconLayout.apply(this, arguments);
        this.__style = e;
        this.templateKey = this.__style.templateKey
    };
    C = U.extend(E, U.APlacemarkIconLayout);
    C.moveTo = function(d) {
        c.position(this.topNode, d.moveBy(this.__style.offset))
    };
    C.update = function() {
        if (B.is_ie7down) {
            var d = this.topNode.getElementsByTagName("img");
            for (var f = 0; f < d.length; f++) {
                var e = d[f],g = c.create("div");
                c.size(g, this.__style.size || new a(e.clientWidth, e.clientHeight));
                g.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + e.src + "', sizingMethod='crop')";
                e.parentNode.replaceChild(g, e)
            }
        }
    };
    U.Layouts.add("default#icon", E);
    U.ObjectManager = function(l) {
        l = l || {};
        var AH,m = parseInt(l.maxZoom) || 16,i = parseInt(l.cellSize) || 256,s = parseInt(l.padding) || 100,r = typeof l.trackObjects == B.UNDEF ? true : !!l.trackObjects,q,AG = 0,e = [],AB = [],f,z = [],AD;
        AE();
        this.onAddToMap = function(AK, AL) {
            AH = AK;
            q = AH.getZoom();
            f = t();
            AD = Q.observe(AH, AH.Events.MoveEnd, this.update);
            while (z.length) {
                y.apply(null, z.shift())
            }
            p(f, h)
        };
        this.onRemoveFromMap = function() {
            p(f, AI);
            AD.cleanup();
            AH = AD = null
        };
        function AE() {
            var AK = 256;
            for (var AL = 0; AL <= m; AL++) {
                e[AL] = [];
                AB[AL] = Math.ceil(AK / i);
                AK <<= 1
            }
        }
        this.removeAll = function() {
            p(f, AI);
            AE()
        };
        function t() {
            return AJ(AH.getBounds(), q, s)
        }
        function AJ(AK, AR, AP) {
            AR = Math.min(AR, m);
            var AQ = AK.getLeftBottom(),AN = AK.getRightTop(),AL = d(AQ, AR, (new a(-AP, AP))),AM = d(AN, AR, (new a(AP, -AP))),AO = AB[AR];
            if (AN.x < AQ.x || AM.x < AL.x) {
                AL.x -= AO
            }
            if (AM.x - AL.x + 1 >= AO) {
                AL.x = 0;
                AM.x = AO - 1
            }
            var AS = new U.Bounds(AL, AM);
            AS.zoom = AR;
            return AS
        }
        function d(AK, AM, AN) {
            var AL = AH.converter.tileCoordinatesToPixels(AK.getTileCoordinates(), U.toScale(AM));
            AN = AN || new a();
            return new a(Math.floor((AL.x + AN.x) / i), Math.floor((AL.y + AN.y) / i))
        }
        this.add = function(AM, AO, AK) {
            if (AM) {
                AK = typeof AK == B.UNDEF ? m : AK;
                AO = typeof AO == B.UNDEF ? 0 : AO;
                if (AM instanceof Array) {
                    if (AH && AG > 0) {
                        p(f, AI)
                    }
                    for (var AN = AM.length - 1; AN >= 0; AN--) {
                        y(AM[AN], AO, AK)
                    }
                    if (AH) {
                        p(f, h)
                    }
                } else {
                    y(AM, AO, AK);
                    if (AH) {
                        var AL = d(AM.getGeoPoint(), q);
                        if (n(AL) && AO <= f.zoom && f.zoom <= AK) {
                            h(AM)
                        }
                    }
                }
            }
        };
        function y(AN, AP, AL) {
            if (r) {
                Q.observe(AN, AN.Events.PositionChange, g)
            }
            if (AH) {
                var AM = d(AN.getGeoPoint(), AL),AK;
                for (var AO = AL; AO >= AP; AO--) {
                    AK = u(AM.x, AM.y, AO);
                    AK.push(AN);
                    AM.x = AM.x >> 1;
                    AM.y = AM.y >> 1
                }
            } else {
                z.push([AN,AP,AL])
            }
        }
        this.remove = function(AL) {
            var AN = m,AO = false,AM = d(AL.getGeoPoint(), AN),AK;
            while (AN >= 0) {
                AK = AC(AM.x, AM.y, AN);
                if (AK) {
                    w(AK, AL)
                }
                if (AN == q && AH) {
                    if (n(AM)) {
                        AI(AL);
                        AO = true
                    }
                }
                AM.x = AM.x >> 1;
                AM.y = AM.y >> 1;
                --AN
            }
        };
        function n(AK) {
            var AO = f.getLeftTop(),AM = f.getRightBottom(),AN = AO.y <= AK.y && AK.y <= AM.y,AL = AO.x <= AK.x && AK.x <= AM.x;
            if (!AL && AO.x < 0) {
                var AP = AB[f.zoom];
                AL = AO.x + AP <= AK.x && AK.x <= AP - 1
            }
            return AN && AL
        }
        function g(AK) {
            AA(AK.object, AK.oldPoint, AK.newPoint)
        }
        function AA(AN, AQ, AL) {
            var AP = m,AR = false,AO = d(AQ, AP),AM = d(AL, AP),AK;
            while (AP >= 0 && (AO.x != AM.x || AO.y != AM.y)) {
                AK = AC(AO.x, AO.y, AP);
                if (AK) {
                    if (w(AK, AN)) {
                        u(AM.x, AM.y, AP).push(AN)
                    }
                }
                if (AP == q) {
                    if (n(AO)) {
                        if (!n(AM)) {
                            AI(AN);
                            AR = true
                        }
                    } else {
                        if (n(AM)) {
                            h(AN);
                            AR = true
                        }
                    }
                }
                AO.x = AO.x >> 1;
                AO.y = AO.y >> 1;
                AM.x = AM.x >> 1;
                AM.y = AM.y >> 1;
                --AP
            }
        }
        function u(AK, AO, AN) {
            if (AK < 0) {
                AK += AB[AN]
            }
            var AM = e[AN],AL = AM[AK];
            if (!AL) {
                return((AM[AK] = [])[AO] = [])
            }
            return AL[AO] || (AL[AO] = [])
        }
        this.update = function() {
            window.setTimeout(x, 0)
        };
        function x() {
            if (AH) {
                q = AH.getZoom();
                var AQ = t();
                if (AQ.zoom != f.zoom) {
                    var AR = [],AN = [],AM = function(AS) {
                        AR.push(AS)
                    },AK = function(AS) {
                        AN.push(AS)
                    };
                    p(f, AM);
                    p(AQ, AK);
                    for (var AL = AR.length - 1; AL >= 0; AL--) {
                        var AP = AR[AL],AO = AN.indexOf(AP);
                        if (AO == -1) {
                            AI(AP)
                        } else {
                            AN.splice(AO, 1)
                        }
                    }
                    for (var AL = AN.length - 1; AL >= 0; AL--) {
                        h(AN[AL])
                    }
                } else {
                    if (!AQ.equals(f)) {
                        o(f, AQ, k);
                        o(AQ, f, v)
                    }
                }
                f = AQ
            }
        }
        function p(AL, AP) {
            for (var AK = AL.getLeft(),AO = AL.getRight(); AK <= AO; AK++) {
                for (var AN = AL.getTop(),AM = AL.getBottom(); AN <= AM; AN++) {
                    j(AK, AN, AL.zoom, AP)
                }
            }
        }
        function k(AK, AM, AL) {
            j(AK, AM, AL, AI)
        }
        function v(AK, AM, AL) {
            j(AK, AM, AL, h)
        }
        function j(AL, AP, AN, AO) {
            var AK = AC(AL, AP, AN);
            if (AK) {
                for (var AM = AK.length - 1; AM >= 0; AM--) {
                    AO.call(this, AK[AM])
                }
            }
        }
        function o(AL, AK, AM) {
            AF(AL, AK, function(AN, AO) {
                AM.apply(null, [AN,AO,AL.zoom])
            })
        }
        function AF(AL, AK, AV) {
            var AP = AL.getLeft(),AW = AL.getTop(),AR = AL.getRight(),AN = AL.getBottom(),AO = AK.getLeft(),AU = AK.getTop(),AQ = AK.getRight(),AM = AK.getBottom(),AT,AS;
            for (AT = AP; AT <= AR; AT++) {
                for (AS = AW; AS <= AN && AS < AU; AS++) {
                    AV(AT, AS)
                }
                for (AS = Math.max(AM + 1, AW); AS <= AN; AS++) {
                    AV(AT, AS)
                }
            }
            for (AS = Math.max(AW, AU); AS <= Math.min(AN, AM); AS++) {
                for (AT = Math.min(AR + 1, AO) - 1; AT >= AP; AT--) {
                    AV(AT, AS)
                }
                for (AT = Math.max(AP, AQ + 1); AT <= AR; AT++) {
                    AV(AT, AS)
                }
            }
        }
        function AC(AK, AM, AL) {
            if (AK < 0) {
                AK += AB[AL]
            }
            return e[AL][AK] ? e[AL][AK][AM] : null
        }
        function AI(AK) {
            AH.removeOverlay(AK);
            AG--
        }
        function h(AK) {
            AH.addOverlay(AK);
            AG++
        }
        function w(AK, AN) {
            var AL = 0;
            for (var AM = 0; AM < AK.length; ++AM) {
                if (AK[AM] === AN) {
                    AK.splice(AM--, 1);
                    AL++
                }
            }
            return AL
        }
        this.destructor = function() {
            AD.cleanup();
            AE();
            AH = f = AD = null
        }
    };
    var E = U.GeoObjectCollection = function(d) {
        U.OverlayGroup.call(this);
        this.metaDataProperty = {};
        this.__style = d
    };
    var C = U.extend(E, U.OverlayGroup);
    C.onAddToGroup = function(d) {
        this.__parentGroup = d
    };
    C.getParentGroup = function() {
        return this.__parentGroup
    };
    C.add = function(d) {
        if (d) {
            U.callMethod(d, "onAddToGroup", this);
            U.OverlayGroup.prototype.add.call(this, d)
        }
    };
    C.getStyle = function() {
        return this.__style
    };
    C.setStyle = function(d) {
        this.__style = d
    };
    var b = new function() {
        var e = {},d = {},f = U._MapData.versionPath + "xsl/YMapsMLParser.xml?key=" + U._MapData.userKey + (U._MapData.w1251 ? "&w1251=1" : "") + "&url=";
        this.loadDocument = function(g, l, h, i) {
            if (!e[g] || i) {
                if (!d[g]) {
                    d[g] = []
                }
                d[g].push([l,h]);
                if (d[g].length == 1) {
                    var k = this,j = new B.Request.Script(f + encodeURIComponent(g), {encoding:U._MapData.w1251 ? "windows-1251" : "utf-8",onload:function(m) {
                        k.loaded(m)
                    },onerror:function() {
                        k.loaded({url:g,data:""})
                    }});
                    j.send()
                }
            } else {
                l.call(h, e[g])
            }
        };
        this.loaded = function(m) {
            m = m.responseText;
            var o = m.data,h = m.url;
            if (typeof o !== "string") {
                e[h] = o;
                for (var k = 0,j = o.Templates,g = j.length; k < g; k++) {
                    U.Templates.add(h + "#" + j[k].id, j[k])
                }
                for (var k = 0,n = o.Styles,g = n.length; k < g; k++) {
                    U.Styles.add(h + "#" + n[k].id, n[k].s)
                }
            }
            var p = d[h];
            if (p) {
                for (var k = p.length - 1; k >= 0; k--) {
                    p[k][0].call(p[k][1], o)
                }
                d[h] = null
            }
        }
    };
    var E = U.YMapsML = function(e, d) {
        U.GeoObjectCollection.call(this);
        this._options = B.Utils.objectCopy({viewAutoApply:1,notFromCache:0}, d);
        b.loadDocument(e, this.__onLoaded, this, this._options.notFromCache)
    };
    var C = U.extend(E, U.GeoObjectCollection);
    C.Events.Load = "Load";
    C.Events.Fault = "Fault";
    C.__onLoaded = function(d) {
        if (typeof d == "string") {
            Q.notify(this.Events.Fault, this, d, 1)
        } else {
            this._isLoaded = 1;
            this.add(d.Objects);
            this.view = d.View;
            Q.notify(this.Events.Load, this, null, 1);
            if (this._options.viewAutoApply) {
                this.applyView()
            }
        }
    };
    C.onAddToMap = function(d, e) {
        U.GeoObjectCollection.prototype.onAddToMap.call(this, d, e);
        if (this._isLoaded && this._options.viewAutoApply) {
            this.applyView()
        }
    };
    C.applyView = function() {
        if (this._map && this.view) {
            this._map.setType(this.view.mapType);
            this._map.setBounds(this.view.boundedBy)
        }
    };
    U._Graphics = new function() {
        this.VML_DEFINED = false;
        this.SVG_DEFINED = false;
        if (B.is_ie55up) {
            this.VML_DEFINED = true;
            if (!document.namespaces.vml) {
                document.namespaces.add("vml", "urn:schemas-microsoft-com:vml")
            }
            var e = document.createStyleSheet();
            e.addRule("vml\\:*", "behavior: url(#default#VML);")
        } else {
            var g = B.is_safari && B.safari_ver > 420 && (!B.is_mac),f = B.is_gecko && B.gecko_ver > 1.5,d = B.is_opera && B.opera_ver >= 9.5;
            if (!B.is_ie && (g || f || d)) {
                this.SVG_DEFINED = true
            }
        }
        this.codingCoefficient = 1000000
    };
    U._Graphics._Shape = function() {
        this.create();
        c.css(this._htmlElement, {position:"absolute",fontSize:0})
    };
    C = U._Graphics._Shape.prototype;
    C.createElement = function(d) {
        this._htmlElement = this._createElement(d)
    };
    C.setStyleProperty = function(d, e) {
        this._htmlElement.style[d] = e
    };
    C.setStrokeColor = function(d) {
        this.setProperty("strokeStyle", d)
    };
    C.getStrokeColor = function() {
        return this.getProperty("strokeStyle")
    };
    C.setStrokeWidth = function(d) {
        this.setProperty("lineWidth", d)
    };
    C.getStrokeWidth = function() {
        return this.getProperty("lineWidth")
    };
    C.setStrokeOpacity = function(d) {
        this.setProperty("strokeOpacity", d)
    };
    if (U._Graphics.VML_DEFINED) {
        C._expansionCoefficient = 1;
        C.getStrokeOpacity = function() {
            return this.__opacity
        };
        C.setStrokeOpacity = function(e) {
            var d = this._htmlElement;
            this.__opacity = e;
            if (d.stroke) {
                d.stroke.opacity = this.__opacity ? this.__opacity : 1
            }
        };
        C._propertyName = {lineWidth:"strokeweight",strokeStyle:"strokecolor"};
        C._prepareProperty = function(d) {
            return this._propertyName[d] || d
        };
        C.setProperty = function(e, d) {
            this._htmlElement.setAttribute(this._prepareProperty(e), d)
        };
        C.getProperty = function(d) {
            return this._htmlElement.getAttribute(this._prepareProperty(d))
        };
        C._createElement = function(d) {
            return document.createElement("vml:" + d)
        }
    } else {
        if (U._Graphics.SVG_DEFINED) {
            C._expansionCoefficient = 0.5;
            C.setStrokeOpacity = function(d) {
                this.setProperty("strokeOpacity", d)
            };
            C._propertyName = {lineWidth:"stroke-width",strokeStyle:"stroke",strokeOpacity:"stroke-opacity"};
            C._prepareProperty = function(d) {
                return this._propertyName[d] || d
            };
            C._getElement = function() {
                return this._htmlElement.firstChild.firstChild
            };
            C.setProperty = function(d, e) {
                this._getElement().setAttribute(this._prepareProperty(d), e)
            };
            C.getProperty = function(d) {
                return this._getElement().getAttribute(this._prepareProperty(d))
            };
            C._createElement = function(g) {
                var e = document.createElement("div"),d = document.createElementNS("http://www.w3.org/2000/svg", "svg"),f = document.createElementNS("http://www.w3.org/2000/svg", g);
                d.appendChild(f);
                e.appendChild(d);
                return e
            };
            C._setBoundingBox = function(d, g) {
                var f;
                d = d.firstChild;
                function e(h) {
                    try {
                        f = g || d.firstChild.getBBox();
                        d.setAttribute("viewBox", f.x + " " + f.y + " " + f.width + " " + f.height);
                        d.setAttribute("width", f.width);
                        d.setAttribute("height", f.height)
                    } catch(i) {
                        window.setTimeout(function() {
                            e(h)
                        }, 100)
                    }
                }
                e(this)
            }
        } else {
            C.__getPolygon = function(g, q) {
                function m(i, l) {
                    return new a(Math.cos(i) * l, Math.sin(i) * l)
                }
                var o = g[1].diff(g[0]),k;
                o.x = g[0].x - o.x;
                o.y = g[0].y - o.y;
                var u = [o].concat(g);
                k = u[u.length - 1].diff(u[u.length - 2]);
                k.x = u[u.length - 1].x + k.x;
                k.y = u[u.length - 1].y + k.y;
                u.push(k);
                var t = [],p = [],j = u.length;
                var x,f,w,e;
                for (var v = 1,s = j - 1,y,d,n,r,h; v < s; v++) {
                    r = u[v];
                    d = u[v + 1].diff(u[v - 1]);
                    h = u[v - 1].x < u[v + 1].x ? 1 : -1;
                    n = Math.atan(0 - d.x / d.y);
                    x = Math.cos(n) * q + r.x;
                    f = Math.sin(n) * q + r.y;
                    w = r.x - Math.cos(n) * q;
                    e = r.y - Math.sin(n) * q;
                    t.push(Math.round(x), Math.round(f));
                    p[(j - v - 1) * 2 + 1] = Math.round(e);
                    p[(j - v - 1) * 2] = Math.round(w)
                }
                return t.concat(p)
            };
            C.setProperty = B.NULL;
            C._expansionCoefficient = 1;
            C._createElement = function() {
                this.__color = "#0000FF";
                this.__width = 5;
                this.__opacity = 1;
                this._id = Math.random().toString().substr(2, 6);
                var d = document.createElement("div");
                d.innerHTML = '<img border="0" usemap="#' + this._id + '" style="width:100%;height:100%;"/><map name="' + this._id + '"><area shape="poly"/></map>';
                return d
            };
            if (B.is_ie) {
                C.setStrokeOpacity = function(d) {
                    this.__opacity = d;
                    this._htmlElement.style.filter = "alpha(opacity=" + d + ")"
                }
            } else {
                C.setStrokeOpacity = function(d) {
                    this.__opacity = d;
                    this._htmlElement.style.opacity = d
                }
            }
            C.getStrokeOpacity = function() {
                return this.__opacity
            };
            C.getStrokeWidth = function() {
                return this.__width
            };
            C.setStrokeWidth = function(d) {
                if (this.__width != d) {
                    this.__width = d;
                    if (this.__drawed) {
                        this.draw()
                    }
                }
            };
            C.getStrokeColor = function() {
                return this.__color
            };
            C.setStrokeColor = function(d) {
                if (this.__color != d) {
                    this.__color = d;
                    if (this.__drawed) {
                        this.draw()
                    }
                }
            }
        }
    }
    C.destructor = function() {
        this._htmlElement = this.__opacity = this.__color = this.__width = this._id = null
    };
    C.create = B.NULL;
    var E = U._Graphics._Line = function() {
        U._Graphics._Shape.call(this, null)
    };
    var C = U.extend(E, U._Graphics._Shape);
    C.create = function() {
        U._Graphics._Shape.prototype.create.call(this);
        this.createElement("line")
    };
    C.onAddToMap = function(e, d) {
        this._map = e;
        this._pContainer = d;
        this._listeners = [Q.observe(e, e.Events.MoveEnd, this.draw, this)];
        this.draw()
    };
    C.onRemoveFromMap = function() {
        var d = this._htmlElement;
        Q.cleanup(this._listeners);
        if (d && d.parentNode) {
            d.parentNode.removeChild(d)
        }
        this._listeners = this._pContainer = this._map = null
    };
    C.setToPoint = function(d) {
        this.draw(null, d)
    };
    C.getToPoint = function() {
        return this._to
    };
    C.setFromPoint = function(d) {
        this.draw(d)
    };
    C.getFromPoint = function() {
        return this._from
    };
    C.draw = function(k, l) {
        this._from = k || this._from;
        this._to = l || this._to;
        if (this._map && this._from && this._to) {
            var h = this._map.getBounds(1),i = this._map.converter.coordinatesToMapPixels(h.getLeftTop()),g = this._map.converter.coordinatesToMapPixels(h.getRightBottom()),m = g.diff(i);
            m.x *= this._expansionCoefficient;
            m.y *= this._expansionCoefficient;
            i.x -= m.x;
            i.y -= m.y;
            g.x += m.x;
            g.y += m.y;
            var e = U._Graphics._SazerlandKohen.getIntersection(this._from, this._to, [i,g]);
            var f = this._htmlElement;
            if (e.intersection) {
                var d = e.from ? new a(e.from.x, e.from.y) : this._from,j = e.to ? new a(e.to.x, e.to.y) : this._to;
                if (!f.parentNode) {
                    this._pContainer.appendChild(f)
                }
                this.__draw(d, j)
            } else {
                if (f.parentNode) {
                    this._pContainer.removeChild(f)
                }
            }
        }
    };
    if (U._Graphics.VML_DEFINED) {
        C.__draw = function(f, e) {
            var d = this._htmlElement;
            this.setStyleProperty("left", f.x + "px");
            this.setStyleProperty("top", f.y + "px");
            this._htmlElement.setAttribute("from", "0px, 0px");
            this._htmlElement.setAttribute("to", (e.x - f.x) + "px," + (e.y - f.y) + "px");
            this._pContainer.appendChild(d)
        }
    } else {
        if (U._Graphics.SVG_DEFINED) {
            C.__draw = function(n, o) {
                var r = this._htmlElement,l = new U.CollectionBounds([n,o]),f = l.getLeft(),k = l.getTop(),p = l.getRight(),d = l.getBottom(),j = n.x - f,h = n.y - k,i = o.x - f,g = o.y - k,m = Number(this.getStrokeWidth()),e = Math.ceil(p - f + m),q = Math.ceil(d - k + m);
                this.setStyleProperty("left", Math.ceil(f) + "px");
                this.setStyleProperty("top", Math.ceil(k) + "px");
                this.setStyleProperty("width", e + "px");
                this.setStyleProperty("height", q + "px");
                this.setProperty("x1", j);
                this.setProperty("y1", h);
                this.setProperty("x2", i);
                this.setProperty("y2", g);
                this._setBoundingBox(r, {x:-m / 2,y:-m / 2,width:e + m,height:q + m})
            }
        } else {
            C.__draw = function(o, e) {
                this.__drawed = true;
                var i = this._htmlElement,v = new U.CollectionBounds([o,e]),r = v.getSpan(),n = r.x,l = r.y,m = Number(this.getStrokeWidth()),f = this._map.converter.mapPixelsToCoordinates(o),g = f.diff(this._map.converter.mapPixelsToCoordinates(e)),q = U._Graphics.codingCoefficient,k = U.Base64.encode4bytes(Math.round(f.lng * q)),t = this._map.converter.mapPixelsToCoordinates(v.getCenter()),p = this.getStrokeColor().substr(1),d = U.Base64,j = v.getLeft(),s = v.getTop(),h,w = i.firstChild,u = i.lastChild.firstChild;
                k = k.concat(d.encode4bytes(Math.round(f.lat * q)));
                k = k.concat(d.encode4bytes(Math.round(g.lng * q)));
                k = k.concat(d.encode4bytes(Math.round(g.lat * q)));
                i.style.top = (s + m) + "px";
                i.style.left = (j + m) + "px";
                i.style.width = (n ? n : m) + "px";
                i.style.height = (l ? l : m) + "px";
                h = U._MapData.printerHost + "/?ll=" + t.lng + "," + t.lat + "&size=" + n + "," + l + "&linewidth=" + this.getStrokeWidth() + "&routecolor=" + p + "&polyline=" + d.encode(k) + "&z=" + this._map.getZoom();
                w.src = h;
                this._pContainer.appendChild(i);
                u.setAttribute("coords", this.__getPolygon([new a(o.x - j, o.y - s),new a(e.x - j, e.y - s)], m + 5))
            }
        }
    }
    C.destructor = function() {
        U._Graphics._Shape.destructor.call(this);
        Q.cleanup(this._listeners);
        this._from = this._to = this._map = this._pContainer = this._listeners = null
    };
    E = U.Line = function(e, d) {
        this.name = "";
        this.description = "";
        this.snippet = "";
        this.metaDataProperty = {};
        this.boundedBy = null;
        U._Graphics._Line.call(this);
        if (e && d) {
            this.draw(e, d)
        }
    };
    C = U.extend(E, U._Graphics._Line);
    C.setEndPoint = function(d) {
        this.draw(null, d)
    };
    C.getEndPoint = function() {
        return this._to
    };
    C.setBeginPoint = function(d) {
        this.draw(d)
    };
    C.getBeginPoint = function() {
        return this._from
    };
    C.onAddToGroup = function(d) {
        this._parentGroup = d
    };
    C.getParentGroup = function() {
        return this._parentGroup
    };
    C.setStyle = function(d) {
        if (d) {
            this.__style = d;
            this._applyStyle()
        }
    };
    C.getStyle = function() {
        return this.__style
    };
    C._getStyle = function() {
        var d = this,e;
        while (!e && d) {
            e = d.getStyle();
            d = d.getParentGroup()
        }
        return(e && typeof e == "object" ? e : U.Styles.get(e))
    };
    C._applyStyle = function() {
        var e = this._getStyle();
        if (e) {
            this.setStrokeWidth(e.lineStyle.strokeWidth);
            this.setStrokeColor("#" + e.lineStyle.strokeColor.slice(0, 6));
            var f = e.lineStyle.strokeColor.slice(6, 8),d = parseInt("0x" + (f ? f : "ff"));
            if (!isNaN(d)) {
                this.setStrokeOpacity(d / 256)
            }
        }
    };
    C.update = function() {
        this.draw()
    };
    C.draw = function(k, j) {
        this._fromLatLng = k || this._fromLatLng;
        this._toLatLng = j || this._toLatLng;
        if (this._map && this._fromLatLng && this._toLatLng) {
            this._applyStyle();
            var i = this._map.converter,h = i.nearestTileOffset(this._toLatLng, this._fromLatLng).scale(0.5),g = new P(0, 0, this._fromLatLng.isUnbounded()).setTileCoordinates(this._fromLatLng.getTileCoordinates().moveBy(h)),d = i.nearestTileOffset(g).moveBy(this._map._state.centerInTiles),f = i.coordinatesToMapPixels(new P(0, 0, 1).setTileCoordinates(d.diff(h))),e = i.coordinatesToMapPixels(new P(0, 0, 1).setTileCoordinates(d.copy().moveBy(h)));
            U._Graphics._Line.prototype.draw.apply(this, [f,e])
        }
    };
    C.destructor = function() {
        U._Graphics._Line.destructor.call(this);
        Q.cleanup(this.__mapMoveEndListener);
        this.__mapMoveEndListener = this._parentGroup = this.__styleKey = this._fromLatLng = this._toLatLng = null
    };
    var E = U.PaintLine = function(e, d) {
        U.Line.call(this, e, d)
    };
    C = U.extend(E, U.Line);
    C.__draw = function(o, p) {
        var s = this._htmlElement,q = "",r = p.diff(o),j = Math.sqrt(r.x * r.x + r.y * r.y),e = 30,g = Math.atan(r.y / r.x),d = o.x < p.x ? 1 : -1,n = d * e * Math.cos(g),m = d * e * Math.sin(g),k = Math.round(this.getStrokeWidth() / 1.41);
        for (var h = 0,f = j / e; h < f; h++) {
            q += '<div style="position:absolute;font:0px;left:' + (o.x + h * n) + "px;top:" + (o.y + h * m) + "px;width:" + 5 + "px;height:" + 5 + "px;background-color:" + this.getStrokeColor() + ';"></div>'
        }
        s.innerHTML = q
    };
    U._Graphics._Polyline = function() {
        this._points = [];
        U._Graphics._Shape.call(this, null)
    };
    var C = U.extend(U._Graphics._Polyline, U._Graphics._Shape);
    C.create = function() {
        U._Graphics._Shape.prototype.create.call(this);
        this.createElement("polyline");
        if (U._Graphics.SVG_DEFINED) {
            this.setProperty("fill", "none");
            this.setProperty("stroke-linejoin", "round");
            this.setProperty("stroke-linecap", "round")
        }
    };
    C.onAddToMap = function(e, d) {
        this._map = e;
        this._pContainer = d;
        this.draw()
    };
    C.onRemoveFromMap = function() {
        B.Dom.removeNode(this._htmlElement);
        this._map = null
    };
    C.draw = function(d) {
        if (this._map) {
            this._points = d || this._points;
            if (this._points.length) {
                this._draw(this._points)
            }
        }
    };
    if (U._Graphics.VML_DEFINED) {
        C._draw = function(j) {
            if (!j) {
                return
            }
            var f = this._htmlElement,k = [],h = new U.CollectionBounds();
            if (f && f.parentNode) {
                f.parentNode.removeChild(f)
            }
            for (var g = 0,e = j.length,d; g < e; g++) {
                d = j[g];
                h.add(d);
                k.push(d.x, d.y)
            }
            this.setProperty("filled", false);
            f.setAttribute("points", k.toString());
            this._pContainer.appendChild(f);
            this.setStrokeOpacity(this.getStrokeOpacity());
            this.setStyleProperty("left", h.getLeft() + "px");
            this.setStyleProperty("top", h.getTop() + "px")
        };
        C.getActiveDomElement = function() {
            return this._htmlElement
        }
    } else {
        if (U._Graphics.SVG_DEFINED) {
            C._draw = function(q) {
                if (!q) {
                    return
                }
                var n = this._htmlElement,g = [],k = new U.CollectionBounds(),d,j,e,r,m = this.getStrokeWidth();
                for (var h = 0,f = q.length,p; h < f; h++) {
                    p = q[h];
                    k.add(p);
                    g.push(p.x, p.y)
                }
                var o = k.getSpan();
                this.setStyleProperty("left", (d = k.getLeft() - m) + "px");
                this.setStyleProperty("top", (j = k.getTop() - m) + "px");
                this.setStyleProperty("width", e = o.x + Number(m) * 2);
                this.setStyleProperty("height", r = o.y + Number(m) * 2);
                for (var h = 0,f = g.length; h < f; h++) {
                    g[h] = h % 2 ? g[h] - j : g[h] - d
                }
                this.setProperty("points", g);
                this._pContainer.appendChild(n);
                this._setBoundingBox(n, {x:0,y:0,width:e,height:r})
            };
            C.getActiveDomElement = C._getElement
        } else {
            C.getActiveDomElement = function() {
                return this._htmlElement.lastChild.firstChild
            };
            C._draw = function(w) {
                if (!w || !w.length) {
                    return
                }
                this.__drawed = true;
                var g = this._htmlElement,AB = new U.CollectionBounds(),m = [],z,q,n,p = this.getStrokeWidth(),d = U.Base64,t = this.getStrokeColor().substr(1),e,j,y,AA = [],w = w,u = U._Graphics.codingCoefficient;
                for (var x = 0,s = w.length,r,f,h,o = new P(0, 0),k = []; x < s; x++) {
                    r = w[x];
                    AB.add(r);
                    h = this._map.converter.mapPixelsToCoordinates(r, 1);
                    f = o.diff(h);
                    m = m.concat(d.encode4bytes(f.lng * u), d.encode4bytes(f.lat * u));
                    o = h
                }
                g.style.position = "absolute";
                var v = AB.getSpan();
                g.style.top = (y = AB.getTop()) + "px";
                g.style.left = (j = AB.getLeft()) + "px";
                g.style.width = ((q = v.x + p + 2)) + "px";
                g.style.height = ((n = v.y + p + 2)) + "px";
                z = this._map.converter.mapPixelsToCoordinates(AB.getCenter());
                e = U._MapData.printerHost + "/?ll=" + z.lng + "," + z.lat + "&size=" + q + "," + n + "&linewidth=" + p + "&routecolor=" + t + "&polyline=" + d.encode(m) + "&z=" + this._map.getZoom();
                for (var x = 0,s = w.length,r,o = null; x < s; x++) {
                    r = w[x];
                    if (o && o.equals(r)) {
                        continue
                    }
                    AA.push(new a(r.x - j, r.y - y));
                    o = r
                }
                g.firstChild.src = e;
                g.lastChild.firstChild.setAttribute("coords", this.__getPolygon(AA, p + 5));
                this._pContainer.appendChild(g)
            }
        }
    }
    C.destructor = function() {
        U._Graphics._Shape.destructor.call(this);
        this._points = this._map = this._pContainer = null
    };
    U.Polyline = function(d) {
        this.name = "";
        this.description = "";
        this.snippet = "";
        this.metaDataProperty = {};
        this.boundedBy = null;
        this._encodedPoints = null;
        this._points = null;
        this._levels = null;
        this._maxLevel = null;
        this.__pointCollection = null;
        this._polylines = [];
        this._options = d || {clickable:true};
        this._hint = U.PlacemarkHint.getHintInstance();
        this._hintOptions = null;
        this._hintVisible = true;
        this._mouseListeners = [];
        this._hintOffset = new a(0, 0);
        this._parentGroup = null
    };
    var C = U.Polyline.prototype;
    C.setClickable = function(d) {
        this._options.clickable = d;
        if (d) {
            for (var g = 0,e = this._polylines,f; g < e; g++) {
                this._setMouseEvents(this._polylines[g])
            }
        } else {
            Q.cleanup(this._mouseListeners)
        }
    };
    C.getClickable = function() {
        return this._options.clickable
    };
    C.Events = {Click:"click",MouseOver:"mouseover",MouseOut:"mouseout",MouseMove:"mousemove"};
    C._setMouseEvents = function(d) {
        var e = d.getActiveDomElement();
        this._mouseListeners = this._mouseListeners.concat([Q.domObserve(e, "click", this.__click, this),Q.domObserve(e, "mouseover", this.__mouseOver, this),Q.domObserve(e, "mousemove", this.__mouseMove, this),Q.domObserve(e, "mouseout", this.__mouseOut, this)])
    };
    C.__click = function(e) {
        var d = this._map.converter.clientPixelsToLocalPixels(new a(e.clientX, e.clientY));
        Q.notify(this.Events.Click, this, new U._MouseEvent(e, d, this._map.converter.localPixelsToCoordinates(d)))
    };
    C.__mouseMove = function(e) {
        var d = this._map.converter.clientPixelsToLocalPixels(new a(e.clientX, e.clientY));
        Q.notify(this.Events.MouseMove, this, new U._MouseEvent(e, d, this._map.converter.localPixelsToCoordinates(d)))
    };
    C.__mouseOver = function(e) {
        var d = this._map.converter.clientPixelsToLocalPixels(new a(e.clientX, e.clientY));
        this._hint.setOwner(this);
        this._setHintPosition(e);
        this._hint.show(this);
        Q.notify(this.Events.MouseOver, this, new U._MouseEvent(e, d, this._map.converter.localPixelsToCoordinates(d)))
    };
    C._setHintPosition = function(g) {
        if (this._hintVisible) {
            var f = this._hint.getOptions(this).viewportNode,j = B.Dom,e = j.getBody(),i = j.getOffset(f),h = j.getOffset(e),d = c.getSummaryScroll(f, e);
            i = new a(i[0], i[1]);
            h = new a(h[0], h[1]);
            this._hint.setPosition(this, new a(g.pageX, g.pageY).diff(i).moveBy(h).moveBy(d).moveBy(this._hintOffset))
        }
    };
    C.__mouseOut = function(e) {
        var d = this._map.converter.clientPixelsToLocalPixels(new a(e.clientX, e.clientY));
        Q.notify(this.Events.MouseOut, this, new U._MouseEvent(e, d, this._map.converter.localPixelsToCoordinates(d)))
    };
    C.setStrokeWidth = function(d) {
        this.__width = d;
        this._applyMethod("setStrokeWidth", d)
    };
    C.getStrokeWidth = function() {
        return this.__width
    };
    C.setStrokeColor = function(d) {
        this.__color = d;
        this._applyMethod("setStrokeColor", d)
    };
    C.getStrokeColor = function() {
        return this.__color
    };
    C.setStrokeOpacity = function(d) {
        this.__opacity = d;
        this._applyMethod("setStrokeOpacity", d)
    };
    C.getStrokeOpacity = function() {
        return this.__opacity
    };
    C._applyMethod = function(g) {
        var f = Array.prototype.slice.call(arguments, 1);
        for (var h = 0,d = this._polylines.length,e; h < d; h++) {
            e = this._polylines[h];
            e[g].apply(e, f)
        }
    };
    C._expansionCoefficient = U._Graphics._Shape.prototype._expansionCoefficient;
    C.draw = function(v) {
        if (!this.__points && !v) {
            return
        }
        this.__points = v || this.__points;
        var q = [],t,u,d,o = -1,n = null,k = this._map.getBounds(1),h = this._map.converter.coordinatesToMapPixels(k.getLeftTop()),f = this._map.converter.coordinatesToMapPixels(k.getRightBottom()),s = f.diff(h);
        s.x *= this._expansionCoefficient;
        s.y *= this._expansionCoefficient;
        h.x -= s.x;
        h.y -= s.y;
        f.x += s.x;
        f.y += s.y;
        var m = [h,f];
        for (var p = 0,g = this.__points.length - 1; p < g; p++) {
            t = this.__points[p];
            u = this.__points[p + 1];
            d = U._Graphics._SazerlandKohen.getIntersection(t, u, m);
            if (d.intersection) {
                if (!n) {
                    q[++o] = []
                }
                if (d.to.x != u.x || d.to.y != u.y) {
                    if (n) {
                        q[o].push(n);
                        n = null
                    }
                    q[o].push(d.from, d.to)
                } else {
                    q[o].push(d.from);
                    n = d.to
                }
            } else {
                if (n) {
                    q[o].push(n);
                    n = null
                }
            }
        }
        if (n) {
            q[o].push(n)
        }
        for (var p = 0,g = this._polylines.length; p < g; p++) {
            this._polylines[p].onRemoveFromMap()
        }
        U.Polyline._PolylineManager.free(this._polylines);
        var e = q.length;
        this._polylines = U.Polyline._PolylineManager.get(e);
        this._applyStyle();
        this.__mouseListeners = [];
        for (var p = 0,r; p < e; p++) {
            r = this._polylines[p];
            r.onAddToMap(this._map, this._pContainer);
            r.draw(q[p]);
            if (this._options.clickable) {
                this._setMouseEvents(r)
            }
        }
    };
    C.onAddToGroup = function(d) {
        this._parentGroup = d
    };
    C.getParentGroup = function() {
        return this._parentGroup
    };
    C.setStyle = function(d) {
        if (d) {
            this.__style = d;
            this._applyStyle()
        }
    };
    C.getStyle = function() {
        return this.__style
    };
    C._getStyle = function() {
        var d = this,e;
        while (!e && d) {
            e = d.getStyle();
            d = d.getParentGroup()
        }
        return(e && typeof e == "object" ? e : U.Styles.get(e))
    };
    C._applyStyle = function() {
        var e = this._getStyle();
        if (e && this._polylines) {
            this._applyMethod("setStrokeWidth", this.__width || e.lineStyle.strokeWidth);
            this._applyMethod("setStrokeColor", this.__color || "#" + e.lineStyle.strokeColor.slice(0, 6));
            var d = this.__opacity >= 0 ? this.__opacity : parseInt("0x" + (e.lineStyle.strokeColor.slice(6, 8) || "ff")) / 256;
            if (!isNaN(d)) {
                this._applyMethod("setStrokeOpacity", d)
            }
        }
    };
    C.setEncodedPoints = function(e, f, d) {
        this._encodedPoints = e;
        this._levels = f;
        this._maxLevel = d;
        this._points = this._decodeLine()
    };
    C.setPoints = function(d) {
        this._maxLevel = "A";
        this._points = [d]
    };
    C.onAddToMap = function(d, e) {
        this._map = d;
        this.__onMoveEnd = Q.observe(d, d.Events.MoveEnd, this.draw, this);
        this._pContainer = e;
        this.update()
    };
    C.onRemoveFromMap = function() {
        this._mouseListeners = null;
        U.Polyline._PolylineManager.free(this._polylines)
    };
    C._decodeLine = function() {
        var f = [[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[]];
        var k = this._encodedPoints.length,j = 0,g = 0,p = this._maxLevel.charCodeAt(0) - 65,h = new P(),i = U._Graphics.codingCoefficient;
        while (j < k) {
            var m = 0,l = 0,o = 0,d = this._encodedPoints.substr(j, 8);
            while (o < 4) {
                m |= (d.charCodeAt(o) << o * 8);
                l |= (d.charCodeAt(o + 4) << o * 8);
                o++
            }
            var e = new P(m / i, l / i),n = e.moveBy(h);
            h = n;
            curLevel = this._levels.charCodeAt(g) - 65;
            for (; curLevel <= p; curLevel++) {
                f[curLevel][f[curLevel].length] = n
            }
            j += 8;
            g++
        }
        return f
    };
    C.update = function() {
        var j = this._map.getZoom() - this._map.getType().getMinZoom(),e = this._maxLevel.charCodeAt(0) - "A".charCodeAt(0);
        j = Math.min(j, e);
        var g = this._points[j],k = [],h = new P(0, 0, 1).setTileCoordinates(this._map._state.centerInTiles);
        for (var f = 0,d = g.length; f < d; f++) {
            h.moveBy(h.diff(g[f]));
            k[f] = this._map.converter.coordinatesToMapPixels(h)
        }
        this._applyStyle();
        this.draw(k)
    };
    C.updateView = function() {
    };
    C.enableHint = function() {
    };
    C.disableHint = function() {
    };
    C.hasHint = function() {
    };
    C.setHintContent = function(d) {
    };
    C.getHintContent = function() {
    };
    C.setHintOptions = function(d) {
    };
    C.openBalloon = function(e, d) {
    };
    C.closeBalloon = function() {
    };
    C.setBalloonOptions = function(d) {
    };
    C.enableBalloon = function() {
    };
    C.disableBalloon = function() {
    };
    C.hasBalloon = function() {
    };
    C.setBalloonContent = function(d) {
    };
    C.getBalloonContent = function() {
    };
    C.destructor = function() {
        Q.cleanup(this._mouseListeners);
        U.Polyline._PolylineManager.free(this._polylines);
        this.name = this.description = this.snippet = this.metaDataProperty = this.boundedBy = this._encodedPoints = this._points = this._levels = this._maxLevel = this.__pointCollection = this._polylines = this._options = this._mouseListeners = null
    };
    U.Polyline._PolylineManager = new function() {
        var d = [];
        this.get = function(g) {
            var h = g - d.length;
            var e;
            if (h >= 0) {
                e = d;
                for (var f = 0; f < h; f++) {
                    d.push(new U._Graphics._Polyline())
                }
                d = []
            } else {
                e = d.slice(0, g - 1);
                d = d.slice(g)
            }
            return e
        };
        this.free = function(e) {
            d.concat(e)
        }
    };
    var E = U.ConnectedPoints = function() {
        U.OverlayGroup.call(this);
        this._showLines = 1;
        this._lines = new U.OverlayGroup();
        this.__addObjListener = Q.observe(this, this.Events.Add, function() {
            if (this.length() > 1) {
                this._lines.add(this.createLine(this.get(-2), this.get(-1)))
            }
        }, this)
    };
    var C = U.extend(E, U.OverlayGroup);
    C.onAddToMap = function(d, e) {
        U.OverlayGroup.prototype.onAddToMap.call(this, d, e);
        if (this._showLines) {
            this._lines.onAddToMap(d, e)
        }
    };
    C.onRemoveFromMap = function() {
        U.OverlayGroup.prototype.onRemoveFromMap.call(this);
        if (this._showLines) {
            this._lines.onRemoveFromMap()
        }
    };
    C.setLineVisible = function(d) {
        if (this._showLines != d) {
            this._showLines = d;
            if (this._map) {
                if (d) {
                    this._lines.onAddToMap(this._map, this._parentContainer)
                } else {
                    this._lines.onRemoveFromMap()
                }
            }
        }
    };
    C.getLineVisible = function() {
        return this._showLines
    };
    C.createLine = function(e, d) {
        return new U.ConnectedPoints._Line(e, d)
    };
    C.remove = function(f) {
        var e = this.indexOf(f);
        if (e != -1) {
            if (e != 0) {
                var d = this._lines.get(e - 1),g = this.get(e + 1);
                if (g) {
                    d.toPlacemark = g;
                    d.update()
                } else {
                    this._lines.remove(d)
                }
            }
            if (e != this.length()) {
                this._lines.remove(this._lines.get(e))
            }
            U.OverlayGroup.prototype.remove.call(this, f)
        }
    };
    C.update = function(d) {
        if (d) {
            var f = this.indexOf(d);
            if (f != -1) {
                var e = null;
                if ((e = this._lines.get(f - 1))) {
                    e.update()
                }
                if ((e = this._lines.get(f))) {
                    e.update()
                }
            }
        } else {
            U.OverlayGroup.prototype.update.call(this);
            if (this._showLines) {
                this._lines.update()
            }
        }
    };
    C.removeAll = function() {
        U.OverlayGroup.prototype.removeAll.call(this);
        this._lines.removeAll()
    };
    C.destructor = function() {
        U.OverlayGroup.prototype.destructor.call(this);
        this._lines.destructor();
        this.__addObjListener.cleanup();
        this._lines = this.__addObjListener = null
    };
    E = U.ConnectedPoints._Line = function(e, d) {
        U.Line.call(this);
        this.fromPlacemark = e;
        this.toPlacemark = d;
        this.update()
    };
    C = U.extend(E, U.Line);
    C.update = function() {
        U.Line.prototype.draw.call(this, this.fromPlacemark.getGeoPoint(), this.toPlacemark.getGeoPoint())
    };
    U._RulerControl = function() {
        var g,f,d,e;
        this.onAddToMap = function(h) {
            g = h;
            d = new U._RulerControl._Container();
            g.addOverlay(d);
            e = Q.observe(g, g.Events.Click, function(l) {
                d.createRulerPoint(l.getGeoPoint(1));
                l._event.stopPropagation()
            }, this, 0);
            var k = new U.Style();
            k.hasBalloon = 0;
            k.iconLayoutKey = "ruler#icon";
            U.Styles.add("ruler#style", k);
            var j = function() {
                U.APlacemarkIconLayout.apply(this, arguments);
                this.templateKey = "ruler#icon";
                this.moveTo = function(l) {
                    U.APlacemarkIconLayout.prototype.moveTo.call(this, l.moveByX(-3).moveByY(-3))
                }
            };
            U.extend(j, U.APlacemarkIconLayout);
            U.Layouts.add("ruler#icon", j);
            var i = new U.Template();
            i.text = '<div class="Placemark">                                 <div>                                     <div class="YMapsRulerIcon" style="background-color:#F00;"></div>                                     <div class="YMapsRulerLabel">                                         <nobr><span class="YMapsRulerTextNum" style="color:#777;font-size:9px;">0) </span><span class="YMapsRulerTextDist">0 м</span></nobr>                                     </div>                                 </div>                             </div>';
            U.Templates.add("ruler#icon", i);
            if (f) {
                this.setString(f)
            }
            this.onAddToMap = function() {
                B.Classes.add(g.getContainer(), "cursor-default");
                e.add()
            };
            this.onAddToMap()
        };
        this.onRemoveFromMap = function() {
            B.Classes.remove(g.getContainer(), "cursor-default");
            e.remove()
        };
        this.destructor = function() {
            e.cleanup();
            g = e = d = null
        };
        this.setString = function(o) {
            if (d) {
                for (var n = o.split(";"),j = n.length,m = 0,h,k = new P(); m < j; m++) {
                    if ((h = n[m].split(",")).length > 1) {
                        d.createRulerPoint(k = new P(k.lng + Number(h[0]), k.lat + Number(h[1]), 1))
                    }
                }
            } else {
                f = o
            }
        };
        this.getString = function() {
            var o = "";
            if (d) {
                for (var m = 0,h = d.length(),n,j = new P(),k; m < h; m++) {
                    n = d.get(m).getGeoPoint();
                    k = new P(n.lng - j.lng, n.lat - j.lat, 1);
                    o += k.lng.toFixed(6) + "," + k.lat.toFixed(6) + ";";
                    j = n
                }
            }
            return o
        }
    };
    var E = U._RulerControl._Container = function() {
        U.ConnectedPoints.call(this);
        var j,g,i,e,h = [],f = 0,d = U.ConnectedPoints.prototype;
        this.onAddToMap = function(k, l) {
            d.onAddToMap.call(this, k, l);
            j = c.create("span", null, '&#160;<img style="vertical-align:middle;" src="http://img.yandex.ru/maps/i/i-close.gif" width="11" height="11" border="0" title="' + U._MapData.constants.hints.ruler.close + '" alt="x">');
            e = Q.domObserve(j, "click", function(m) {
                m.stopPropagation();
                m.preventDefault();
                if (this.length() < 3 || confirm(U._MapData.constants.hints.ruler.sure)) {
                    this.removeAll()
                }
            }, this);
            g = c.create("span", null, '&#160;<img style="vertical-align:middle;" src="http://img.yandex.ru/maps/i/i-hide.gif" width="11" height="11" border="0" title="' + U._MapData.constants.hints.ruler.minimize + '" alt="_">');
            i = Q.domObserve(g, "click", function(p) {
                p.stopPropagation();
                p.preventDefault();
                f = !f;
                for (var n = 0,m = this.length() - 1,o = null; n < m; n++) {
                    this.get(n).setLabelVisible(!f)
                }
            }, this);
            this.onAddToMap = B.NULL
        };
        this.onRemoveFromMap = function() {
            this._map.addOverlay(this)
        };
        this.createRulerPoint = function(n) {
            var l = new U._RulerControl._Point(n),p = [],r = l.Events;
            this.add(l);
            this.update(l);
            if (!(U._Graphics.SVG_DEFINED || U._Graphics.VML_DEFINED)) {
                var m,k,q,o;
                p.push(Q.observe(l, r.DragStart, function() {
                    var s = this.indexOf(l);
                    m = s != 0 ? this._lines.get(s - 1) : null;
                    k = this._lines.get(s);
                    if (m) {
                        this._lines.replace(m, q = new U._RulerControl._PaintLine(m.fromPlacemark, m.toPlacemark))
                    }
                    if (k) {
                        this._lines.replace(k, o = new U._RulerControl._PaintLine(k.fromPlacemark, k.toPlacemark))
                    }
                }, this), Q.observe(l, r.DragEnd, function() {
                    if (q) {
                        this._lines.replace(q, m);
                        m.draw(q.fromPlacemark.getGeoPoint(), q.toPlacemark.getGeoPoint())
                    }
                    if (o) {
                        this._lines.replace(o, k);
                        k.draw(o.fromPlacemark.getGeoPoint(), o.toPlacemark.getGeoPoint())
                    }
                    m = k = q = o = null
                }, this))
            }
            p.push(Q.observe(l, r.Drag, function() {
                this.update(l)
            }, this), Q.observe(l, r.DblClick, function() {
                this.remove(l);
                this.update()
            }, this));
            h.push(p);
            if (this.length() > 1) {
                this.get(-2).setLabelVisible(!f);
                l.addButton(g)
            }
            l.addButton(j);
            return l
        };
        this.remove = function(k) {
            var m = this.length(),l = this.indexOf(k);
            if (m > 1 && l == m - 1) {
                var n = this.get(-2);
                if (m > 2) {
                    n.addButton(g)
                }
                n.setLabelVisible(1);
                n.addButton(j)
            }
            Q.cleanup(h[l]);
            h.splice(l, 1);
            d.remove.call(this, k)
        };
        this.createLine = function(m, l) {
            var k = new U._RulerControl._Line(m, l);
            k.setStrokeColor("#F00");
            return k
        };
        this.update = function(k) {
            U.ConnectedPoints.prototype.update.call(this, k);
            this._recalcDistance()
        };
        this._recalcDistance = function() {
            for (var n = 0,o = 0,k,m = this.length(); n < m; n++) {
                k = this.get(n);
                k.setData(n + 1, n ? (o += k.getGeoPoint().distance(this.get(n - 1).getGeoPoint())) : 0)
            }
        };
        C.destructor = function() {
            if (e) {
                i.cleanup();
                e.cleanup();
                j = g = i = e = null
            }
        }
    };
    U.extend(E, U.ConnectedPoints);
    E = U._RulerControl._Line = function(e, d) {
        U.ConnectedPoints._Line.call(this, e, d)
    };
    var C = U.extend(E, U.ConnectedPoints._Line);
    C._draw = function() {
        if (this._from && this._to) {
            this.__draw(this._map.converter.coordinatesToMapPixels(this._from), this._map.converter.coordinatesToMapPixels(this._to))
        }
    };
    E = U._RulerControl._PaintLine = function(e, d) {
        U.PaintLine.call(this);
        this.fromPlacemark = e;
        this.toPlacemark = d;
        this.update()
    };
    C = U.extend(E, U.PaintLine);
    C.update = U._RulerControl._Line.prototype.update;
    C._draw = U._RulerControl._Line.prototype._draw;
    E = U._RulerControl._Point = function(d) {
        U.Placemark.call(this, d, {style:"ruler#style",draggable:1})
    };
    C = U.extend(E, U.Placemark);
    C.addButton = function(d) {
        this._icon.getContainer().getElementsByTagName("nobr")[0].appendChild(d)
    };
    C.setData = function(e, g) {
        var f = B.Dom,d = this._icon.getContainer();
        this.__num = f.getElementByTagNameAndClass("span", "YMapsRulerTextNum", d);
        this.__text = f.getElementByTagNameAndClass("span", "YMapsRulerTextDist", d);
        this.setData = this.__setData;
        this.setData(e, g)
    };
    C.__setData = function(d, e) {
        this.__num.innerHTML = d + ") ";
        this.__text.innerHTML = U.humanDistance(e)
    };
    C.setLabelVisible = function(d) {
        B.Classes[d ? "remove" : "add"](this._icon.getContainer(), "YMapsRulerPointWithHiddenLabel")
    };
    C.update = function() {
        if (this._parentContainer) {
            this._icon.moveTo(this._map.converter.coordinatesToMapPixels(this._point))
        }
    };
    U._Copyrights = function() {
        var e,h,k,n,d,j,l,i,f = [];
        this.onAddToMap = function(o) {
            e = o;
            var q = "YMapsCopyrights",p = '<span></span>&nbsp;&ndash;&nbsp;<a href="http://maps.yandex.ru/agreement.xml#rights" target="_blank">' + U._MapData.constants.copyrights + "</a>";
            h = c.create("div", {"class":q}, p);
            k = c.create("div", {"class":q + " " + q + "Shadow"}, p);
            var r = e.getContainer();
            r.appendChild(k);
            r.appendChild(h);
            d = k.getElementsByTagName("span")[0];
            l = k.getElementsByTagName("a")[0];
            n = h.getElementsByTagName("span")[0];
            j = h.getElementsByTagName("a")[0];
            m();
            var s = e.Events;
            i = [Q.observe(e, s.ChangeType, m)].concat(Q.observe(e, [s.Update,s.MoveEnd,s.AddLayer,s.RemoveLayer], g))
        };
        function m() {
            var o = e.getType();
            j.style.color = n.parentNode.style.color = o.getTextColor();
            l.style.color = d.parentNode.style.color = o.getShadowTextColor();
            g()
        }
        function g() {
            var o = f.join(", ");
            n.innerHTML = d.innerHTML = B.Strings.text2html((o ? o + ", " : "") + e._getLayerCopyrights().join(", "))
        }
        this.addCopyright = function(o) {
            if (o) {
                f.push(o);
                if (e) {
                    g()
                }
            }
        };
        this.removeCopyright = function(o) {
            var p = f.indexOf(o);
            if (p != -1) {
                f.splice(p, 1);
                if (e) {
                    g()
                }
            }
        };
        this.onRemoveFromMap = B.NULL;
        this.destructor = function() {
            if (e) {
                B.Dom.removeNode(h);
                B.Dom.removeNode(k);
                Q.cleanup(i);
                e = h = k = n = d = j = l = i = f = null
            }
        }
    };
    U._Graphics._SazerlandKohen = new function() {
        this.getIntersection = function(o, p, j) {
            var f = o.x,d = p.x,n = o.y,m = p.y;
            var k = 0;
            if (f < j[0].x) {
                k = k | 1
            }
            if (f > j[1].x) {
                k = k | 2
            }
            if (n < j[0].y) {
                k = k | 4
            }
            if (n > j[1].y) {
                k = k | 8
            }
            var i = 0;
            if (d < j[0].x) {
                i = i | 1
            }
            if (d > j[1].x) {
                i = i | 2
            }
            if (m < j[0].y) {
                i = i | 4
            }
            if (m > j[1].y) {
                i = i | 8
            }
            var l = ((k | i) == 0),e = ((k & i) != 0),g = !(l || e),h = false;
            while (!(l || e)) {
                if (k == 0) {
                    h = !h;
                    var q;
                    q = f;
                    f = d;
                    d = q;
                    q = n;
                    n = m;
                    m = q;
                    q = k;
                    k = i;
                    i = q
                }
                if ((k & 1) != 0) {
                    n = n + Math.floor((m - n) * (j[0].x - f) / (d - f));
                    f = j[0].x
                } else {
                    if ((k & 2) != 0) {
                        n = n + Math.floor((m - n) * (j[1].x - f) / (d - f));
                        f = j[1].x
                    } else {
                        if ((k & 4) != 0) {
                            f = f + Math.floor((d - f) * (j[0].y - n) / (m - n));
                            n = j[0].y
                        } else {
                            if ((k & 8) != 0) {
                                f = f + Math.floor((d - f) * (j[1].y - n) / (m - n));
                                n = j[1].y
                            }
                        }
                    }
                }
                var k = 0;
                if (f < j[0].x) {
                    k = k | 1
                }
                if (f > j[1].x) {
                    k = k | 2
                }
                if (n < j[0].y) {
                    k = k | 4
                }
                if (n > j[1].y) {
                    k = k | 8
                }
                l = ((k | i) == 0);
                e = ((k & i) != 0)
            }
            return{intersection:l,clipping:g,outs:e,to:new a(h ? f : d, h ? n : m),from:new a(h ? d : f, h ? m : n)}
        }
    };
    var F = new function() {
        var d = U._MapData.versionPath + "xsl/Geocoder.xml?key=" + U._MapData.userKey + "&origin=clientGeocoder" + (U._MapData.w1251 ? "&w1251=1" : "");
        this.load = function(f, i, g) {
            var h = new B.Request.Script(e(f), {encoding:U._MapData.w1251 ? "windows-1251" : "utf-8",onload:function(j) {
                i.call(g, j.responseText)
            },onerror:function() {
                i.call(g, {error:{code:408,message:"Timeout"}})
            }});
            h.send()
        };
        function e(h) {
            var g = [d];
            var j = h.boundedBy;
            if (j) {
                var f = j.getCenter();
                h.ll = f.lng + "," + f.lat;
                h.span = j.getSpan().toString();
                delete h.boundedBy
            }
            for (var i in h) {
                g.push(i + "=" + encodeURIComponent(h[i]))
            }
            return g.join("&")
        }
    };
    var E = U.Geocoder = function(e, d) {
        e = String(e);
        U.GeoObjectCollection.call(this);
        this.setStyle("gecoder#result");
        this.request = e;
        this.suggest = null;
        if (e) {
            d = B.Utils.objectCopy(d);
            d.geocode = e;
            F.load(d, this.__onLoaded, this)
        } else {
            this.__onLoaded({objects:[]})
        }
    };
    var C = U.extend(E, U.GeoObjectCollection);
    C.Events.Load = "Load";
    C.Events.Fault = "Fault";
    C.__onLoaded = function(d) {
        if (d.error) {
            Q.notify(this.Events.Fault, this, d.error, 1)
        } else {
            this.suggest = d.suggest || null;
            this.add(d.objects);
            Q.notify(this.Events.Load, this, this, 1)
        }
    };
    var X = new U.Style(),L = X.balloonContentStyle = new U.BalloonContentStyle();
    L.templateKey = "gecoder#template";
    U.Styles.add("gecoder#result", X);
    var Z = new U.Template();
    Z.text = "<div>Тип - $[kind]<br />Точность - $[precision]<br />Текст - $[text]</div>";
    U.Templates.add("gecoder#template", Z);
    E = U.GeocoderResult = function(d, e) {
        U.Placemark.call(this, d, e);
        this.kind = "";
        this.text = "";
        this.precision = "";
        this.AddressDetails = null
    };
    U.extend(E, U.Placemark);
    U.Base64 = new function() {
        var d = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_=";
        this.encode4bytes = function(e) {
            var g = [];
            for (var f = 0; f < 4; f++) {
                g[f] = e & 255;
                e = e >> 8
            }
            return g
        };
        this.encode = function(h) {
            var e = "",r,p,n,q,o,m,k,g = 0,f = h.length,j = typeof h == "string";
            while (g < f) {
                if (j) {
                    r = h.charCodeAt(g++);
                    p = h.charCodeAt(g++);
                    n = h.charCodeAt(g++)
                } else {
                    r = h[g++];
                    p = h[g++];
                    n = h[g++]
                }
                q = r >> 2;
                o = ((r & 3) << 4) | (p >> 4);
                m = ((p & 15) << 2) | (n >> 6);
                k = n & 63;
                if (isNaN(p)) {
                    m = k = 64
                } else {
                    if (isNaN(n)) {
                        k = 64
                    }
                }
                e += d.charAt(q) + d.charAt(o) + d.charAt(m) + d.charAt(k)
            }
            return e
        };
        this.decode = function(h) {
            var e = "",q,o,m,p,n,k,j,g = 0,f = (h = h.replace(/[^A-Za-z0-9\-_\=]/g, "")).length;
            while (g < f) {
                p = d.indexOf(h.charAt(g++));
                n = d.indexOf(h.charAt(g++));
                k = d.indexOf(h.charAt(g++));
                j = d.indexOf(h.charAt(g++));
                q = (p << 2) | (n >> 4);
                o = ((n & 15) << 4) | (k >> 2);
                m = ((k & 3) << 6) | j;
                e = e + String.fromCharCode(q);
                if (k != 64) {
                    e = e + String.fromCharCode(o)
                }
                if (j != 64) {
                    e = e + String.fromCharCode(m)
                }
            }
            return e
        }
    }
})(YMaps);