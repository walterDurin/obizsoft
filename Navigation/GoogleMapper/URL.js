(function() {
    var Q = y5.UNDEF,D = y5.Types,G = /^((((\w+):)\/\/)(([\w\-\.]+)(\:(\d+))?))?(\/?[^\?#]*)?(\?([^#]*))?(#(.*))?$/,P = "E9F6F3EAE5EDE3F8F9E7F5FAF4FBE2E0EFF0EEEBE4E6FDFFF7F1ECE8F2FCE1FEB8C9D6D3CAC5CDC3D8D9C7D5DAD4DBC2C0CFD0CECBC4C6DDDFD7D1CCC8D2DCC1DEA8".match(/../g),F = "%D0%B9%D1%86%D1%83%D0%BA%D0%B5%D0%BD%D0%B3%D1%88%D1%89%D0%B7%D1%85%D1%8A%D1%84%D1%8B%D0%B2%D0%B0%D0%BF%D1%80%D0%BE%D0%BB%D0%B4%D0%B6%D1%8D%D1%8F%D1%87%D1%81%D0%BC%D0%B8%D1%82%D1%8C%D0%B1%D1%8E%D1%91%D0%99%D0%A6%D0%A3%D0%9A%D0%95%D0%9D%D0%93%D0%A8%D0%A9%D0%97%D0%A5%D0%AA%D0%A4%D0%AB%D0%92%D0%90%D0%9F%D0%A0%D0%9E%D0%9B%D0%94%D0%96%D0%AD%D0%AF%D0%A7%D0%A1%D0%9C%D0%98%D0%A2%D0%AC%D0%91%D0%AE%D0%81".match(/.{6}/g),N = {},J = encodeURIComponent;
    for (var E = 0,C = P.length; E < C; E++) {
        N[P[E]] = F[E]
    }
    function O(R, S) {
        return N[S] || R
    }
    function H(R) {
        return R.replace(/%([A-Fa-f0-9]{2})/g, O)
    }
    function A(R) {
        var U = R.replace(/\+/g, "%20");
        try {
            var T = decodeURIComponent(U);
            if (T == undefined) {
                throw"malformed URI sequence"
            }
            return T
        } catch(S) {
            try {
                return decodeURIComponent(H(U))
            } catch(S) {
                return unescape(U)
            }
        }
    }
    function I(R, S, T) {
        if (typeof R[S] == Q) {
            R[S] = new Array()
        }
        if (D.array(T)) {
            R[S] = R[S].concat(T)
        } else {
            R[S].push(T)
        }
    }
    function M(U, V) {
        var X = U[V];
        var T = X.length;
        var S = new Array(T);
        var R = J(V);
        for (var W = 0; W < T; W++) {
            S[W] = R + "=" + J(X[W])
        }
        return S
    }
    y5.URL = function(R) {
        this.parse(typeof R != Q ? R : window.location.href)
    };
    y5.URL.prototype = {go:function() {
        window.location.href = this.toString()
    },toString:function() {
        var R = [];
        if (this.Proto || this.Host) {
            if (this.Host) {
                R.push(this.Proto || "http");
                R.push("://");
                R.push(this.Host);
                if (this.Port) {
                    R.push(":");
                    R.push(this.Port)
                }
            }
        }
        if (this.Path) {
            if (this.Host && this.Path.indexOf("/") != 0) {
                R.push("/")
            }
            R.push(encodeURI(this.Path))
        }
        var S = this.query();
        if (S) {
            R.push("?");
            R.push(S)
        }
        if (this.Hash) {
            R.push("#");
            R.push(J(this.Hash))
        }
        return R.join("")
    },clone:function() {
        return new y5.URL(this.toString())
    },parse:function(R) {
        var S = String(R).match(G);
        if (!S) {
            throw new y5.Exception("this is not an url", "parseURL", "URL");
            return false
        }
        this.Href = S[0];
        this.Proto = S[4] || "";
        this.Host = S[6] || "";
        this.Port = S[8] || 0;
        this.Path = A(S[9] || "");
        this.Query = this.parseQuery(S[11] || "");
        this.Hash = A(S[13] || "");
        return true
    },parseQuery:function(V) {
        var U = {};
        var W = V.replace(/\+/g, "%20").split("&");
        for (var T = 0,R = W.length; T < R; T++) {
            var X = W[T].split("=");
            var S = X.shift();
            if (S) {
                var V = X.length > 1 ? X.join("=") : (X[0] || "");
                I(U, A(S), A(V))
            }
        }
        return U
    },proto:function(R) {
    },host:function(R) {
    },port:function(R) {
    },path:function(R) {
    },hash:function(R) {
    },query:function(T) {
        if (typeof T != Q) {
            this.clearQuery();
            if (D.string(T)) {
                this.Query = this.parseQuery(T)
            } else {
                this.replaceParams(T)
            }
            return this
        }
        var R = new Array();
        for (var S in this.Query) {
            R = R.concat(M(this.Query, S))
        }
        return R.join("&")
    },queryKeys:function() {
        var R = [];
        for (var S in this.Query) {
            R.push(S)
        }
        return R.sort()
    },addParam:function(R, S) {
        I(this.Query, R, S);
        return this
    },addParams:function(S) {
        for (var R in S) {
            I(this.Query, R, S[R])
        }
        return this
    },removeParams:function(T) {
        for (var S = 0,R = T.length; S < R; S++) {
            delete this.Query[T[S]]
        }
        return this
    },replaceParams:function(T) {
        var S = [];
        for (var R in T) {
            S.push(R)
        }
        this.removeParams(S);
        this.addParams(T);
        return this
    },clearQuery:function() {
        this.Query = {};
        return this
    },getParam:function(R) {
        var S = this.Query[R];
        return S ? S[0] : null
    },getParams:function(R) {
        return this.Query[R] || []
    }};
    var L = y5.URL;
    var K = L.prototype;
    var B = {proto:"Proto",host:"Host",port:"Port",path:"Path",hash:"Hash"};
    for (var E in B) {
        K[E] = (function(R) {
            return function(S) {
                if (typeof S != Q) {
                    this[R] = S;
                    return this
                }
                return this[R]
            }
        })(B[E])
    }
    K.get = K.toString;
    y5.Url = function(R) {
        return new L(R)
    };
    y5.loaded("URL")
})();