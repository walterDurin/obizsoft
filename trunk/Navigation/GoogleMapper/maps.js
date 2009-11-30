var g_form,g_searchInput,g_activeInput;
document.documentElement.id = "js";
function addEvent(C, A, B) {
    if (C.addEventListener) {
        C.addEventListener(A, B, 0)
    } else {
        if (C.attachEvent) {
            C.attachEvent("on" + A, B)
        }
    }
}
function commonKeydown(C) {
    if (!C) {
        C = window.event
    }
    var A = C.keyCode;
    if (C.ctrlKey) {
        if (!g_activeInput) {
            if ((A == 13) && (document.forms.length == 1)) {
                g_form.submit()
            } else {
                if (A == 38) {
                    g_searchInput.focus()
                } else {
                    if (A == 37) {
                        var B = document.getElementById("previous_page");
                        if (B) {
                            location.href = B.href
                        }
                    } else {
                        if (A == 39) {
                            var D = document.getElementById("next_page");
                            if (D) {
                                location.href = D.href
                            }
                        }
                    }
                }
            }
        } else {
            if ((A == 38) && (document.forms[0] == g_activeInput.form) && g_activeInput.select) {
                g_activeInput.select()
            }
        }
    } else {
        if ((A == 27) && (typeof (keydownEsc) == "function")) {
            keydownEsc(C)
        }
    }
}
function commonInit(B) {
    g_form = document.forms[0];
    addEvent(document, "keydown", commonKeydown);
    if (g_form) {
        if (typeof (initTabs) == "function") {
            initTabs(g_form)
        }
        for (var D = 0,A = document.forms.length; D < A; D++) {
            var F = document.forms[D];
            for (var C = 0,G = F.length; C < G; C++) {
                var E = F.elements[C];
                if ((E.tagName.toLowerCase() == "textarea") || (E.type && E.type == "text")) {
                    if (!g_searchInput) {
                        g_searchInput = E
                    }
                    addEvent(E, "focus", function() {
                        g_activeInput = this
                    });
                    addEvent(E, "blur", function() {
                        g_activeInput = null
                    })
                }
            }
        }
        if (B && g_searchInput) {
            g_searchInput.focus()
        }
    }
    if (typeof (addEmptyTitleAndBorderToAllImages) == "function") {
        addEmptyTitleAndBorderToAllImages(document)
    }
    if (typeof (applyDecoration) == "function") {
        applyDecoration(document)
    }
}
function delEvent(C, A, B) {
    if (C.removeEventListener) {
        C.removeEventListener(A, B, 0)
    } else {
        if (C.detachEvent) {
            C.detachEvent("on" + A, B)
        }
    }
}
var g_dropdown;
function dropdown(B) {
    var H = document;
    var A = function(I) {
        I = I || event;
        if ((I.keyCode == 27) && !(I.ctrlKey || I.altKey)) {
            C()
        }
    };
    var D = function(J) {
        var I = (J || event).target;
        while (I) {
            if (I.className && (I.className.indexOf("b-dropdown") != -1)) {
                return
            }
            I = I.parentNode
        }
        C()
    };
    var C = function() {
        var I = g_dropdown;
        if (I) {
            delEvent(H, "click", D);
            delEvent(H, "keydown", A);
            I.style.visibility = "hidden";
            I.parentNode.removeChild(I);
            g_dropdown = 0
        }
    };
    C();
    var G = B;
    while (G.className.indexOf("b-dropdown") == -1) {
        G = G.parentNode
    }
    if (G.parentNode) {
        g_dropdown = document.createElement("div");
        g_dropdown.className = "b-dropdown-popup";
        var E = document.createElement("iframe");
        E.src = "javascript:void(0)";
        E.frameBorder = 0;
        g_dropdown.appendChild(E);
        g_dropdown.appendChild(G.cloneNode(true));
        var F = G.parentNode;
        F.insertBefore(g_dropdown, G);
        addEvent(H, "keydown", A);
        addEvent(H, "click", D);
        if (window.event) {
            event.cancelBubble = true
        }
    }
    return false
}
function Application() {
    var C = this,B = y5.$("loader"),G = window.homePage,A = 0;
    if (y5.is_ie) {
        function E() {
            var I = document.body.clientHeight - y5.$("l-head").offsetHeight;
            y5.$("l-page").style.height = y5.$("l-body").style.height = I + "px";
            var H = I - y5.$("b-foot-info").offsetHeight - 4;
            y5.$("l-map").style.height = y5.$("l-panel").style.height = H + "px"
        }
        y5.Events.observe("resize", E, window, 1);
        E()
    }
    this.map = new Map();
    this.panel = new Panel();
    this.searchForm = new SearchForm(this.map);
    this.loadUrl = function(H) {
        B.src = H + (H.indexOf("?") < 0 ? "?" : "&") + "output=js";
        return false
    };
    this.loadHouses = function(H) {
        B.src = H + "&houses=all&output=js";
        return false
    };
    function D(H) {
        C.page = H;
        document.title = H.title;
        C.searchForm.setData(H.form);
        C.map.setData(H.map);
        C.panel.setData(H.panel)
    }
    this.loadHomePage = function() {
        if (!A) {
            A = 1;
            D(G)
        }
    };
    this.loadPage = function(H) {
        A = 0;
        D(H)
    };
    function F(I, H, J) {
        C.panel.activateElement(H);
        C.map.showSearchResult(J);
        I.blur();
        return false
    }
    this.showObject = function(J, K, N) {
        var I = J.parentNode.parentNode,M = I.childNodes[1],H = J.innerHTML,L = (M ? M.data + ", " : "") + H;
        return F(J, I, {ll:K,bb:N,name:H,text:L})
    };
    this.showStreet = function(H, I, J) {
        return F(H, H.parentNode, {ll:I,bb:J,name:H.innerHTML,text:H.title})
    };
    this.showHouse = function(I, J) {
        var K = y5.$("activeObject").title + ", " + I.innerHTML,H = K.split(", ").slice(-2).join(", ");
        return F(I, I, {ll:J,name:H,text:K})
    }
}
function SearchForm(B) {
    var A = y5.$("mapsForm");
    A.target = "loader";
    A.output.value = "js";
    y5.Events.observe("submit", C, A, true, this);
    function C() {
        A.sll.value = B.getCenter().encode();
        A.sspn.value = B.getSpan().encode()
    }
    this.setData = function(E) {
        for (var D in E) {
            A[D].value = E[D]
        }
    }
}
function Panel() {
    var A = y5.$("panel");
    this.setData = function(D) {
        A.innerHTML = D;
        A.scrollTop = 0;
        var C = y5.$("activeObject");
        if (C) {
            C.onclick()
        }
    };
    var B;
    this.activateElement = function(C) {
        if (B) {
            y5.Classes.remove(B, "active")
        }
        B = C;
        y5.Classes.add(B, "active")
    }
}
function Map() {
    var L = this,G = y5.$("l-map"),U = G.onclick(),T = new YMaps.Map(G),Q = new YMaps.ToolBar(),S = T.converter,N = 0,V = [],D = {},E = new YMaps.Placemark(null, {balloonOptions:{mapAutoPan:0}}),P = {map:1,sat:2,skl:4},O = {},H = {bl:"blue",db:"darkblue","do":"darkorange",gn:"green",gr:"gray",lb:"lightblue",nt:"night",or:"orange",pn:"pink",rd:"red",vv:"violet",wt:"white",yw:"yellow"};
    YMaps.MapType.AllTypes.forEach(function(W) {
        O[F(YMaps.MapTypes.get(W).getLayers())] = W
    });
    T.addControl(Q);
    T.addControl(new YMaps.ScaleLine());
    T.addControl(new YMaps.TypeControl());
    T.addControl(new YMaps.MiniMap());
    T.addControl(new YMaps.Zoom());
    T.setEnabledScrollZoom(1);
    T.setEnabledHotKeys(1);
    T.removeType("HYBRID");
    A(U.l);
    M(U);
    T.setRulerState(U.rl || "");
    this.permalink = new Permalink(location.href.match("^http://[^/]+/"));
    this.permalink.observeMap(this);
    this.permalink.observeSearch(D);
    this.permalink.addToToolBar(Q);
    this.isMoved = 1;
    YMaps.Events.observe(T, [T.Events.Update,T.Events.MoveEnd], function() {
        this.isMoved = 1
    }, this);
    this.setData = function(W) {
        I();
        C();
        A(W.l);
        M(W);
        this.isMoved = 1;
        N = !!W.ll;
        J(W.pt)
    };
    this.getCenter = function() {
        return T.getCenter()
    };
    this.getCenterByOffset = function(X) {
        var W = S.coordinatesToLocalPixels(T.getCenter());
        return S.localPixelsToCoordinates(W.moveBy(X))
    };
    this.getSpan = function() {
        return T.getBounds().getSpan()
    };
    this.getRulerState = function() {
        return T.getRulerState()
    };
    this.showSearchResult = function(Y) {
        D.name = Y.name;
        D.text = Y.text;
        D.center = K(Y.ll);
        if (Y.bb) {
            D.bounds = R(Y.bb);
            D.span = D.bounds.getSpan()
        }
        var W = D.center,X = Y.bb && D.bounds.getMapZoomLevel(T);
        if (N) {
            N = 0
        } else {
            if (X && X !== T.getZoom()) {
                T.setCenter(W, X)
            } else {
                T.panTo(W)
            }
        }
        this.removeOverlay(E);
        E.setGeoPoint(W);
        E.setBalloonContent(Y.name);
        this.addOverlay(E);
        E.openBalloon();
        this.isMoved = 0
    };
    function F(W) {
        return W.reduce(function(X, Y) {
            return X | P[Y]
        }, 0)
    }
    function A(W) {
        if (!W) {
            return
        }
        T.setType(O[F(W.split(","))])
    }
    this.getLayers = function() {
        var W = T.getTypeAsObject().getLayers().join(",");
        return W != "map" && W
    };
    function M(W) {
        if (W.spn) {
            T.setBounds(R(W.ll, W.spn))
        } else {
            if (W.bb) {
                T.setBounds(R(W.bb))
            } else {
                if (W.ll && W.zoom) {
                    T.setCenter(K(W.ll), W.zoom)
                }
            }
        }
    }
    function J(W) {
        if (!W) {
            return
        }
        W.split("|").forEach(function(X) {
            var a = X.split(","),X = new YMaps.GeoPoint(+a[0], +a[1]),Y = a[2] && H[a[2].substr(2, 2)],Z = new YMaps.Placemark(X, {styleKey:Y && "default#" + Y + "Point",setEnabledBalloon:0});
            L.addOverlay(Z)
        })
    }
    this.addOverlay = function(W) {
        V.push(W);
        T.addOverlay(W)
    };
    this.removeOverlay = function(X) {
        var W = V.indexOf(X);
        if (W >= 0) {
            V.splice(W, 1);
            T.removeOverlay(X)
        }
    };
    function C() {
        while (V.length) {
            T.removeOverlay(V.pop())
        }
    }
    this.hasOverlayVisible = function(X) {
        if (!X) {
            return false
        }
        var Z = T.getBounds(),Y = Z.getRightTop(),W = Z.getLeftBottom();
        return X.x > W.x && X.y > W.y && X.x < Y.x && X.y < Y.y
    };
    function I() {
        for (var W in D) {
            delete D[W]
        }
    }
    function B(W) {
        return W && new YMaps.Point(W.x, W.y)
    }
    function K(W) {
        return W && new YMaps.GeoPoint(W.x, W.y)
    }
    function R() {
        switch (arguments.length) {case (1):var Y = arguments[0];return Y && new YMaps.GeoBounds(K(Y.lb), K(Y.rt));case (2):var X = arguments[0],W = arguments[1];return X && W && YMaps.GeoBounds.createFromCenterAndSpan(K(X), B(W))}
    }
}
function Permalink(B) {
    var F = y5.Url(B).clearQuery(),D = {text:null,ll:null,spn:null,sll:null,sspn:null,l:null,rl:null},J = [],G = document.createElement("A");
    G.href = B;
    G.className = "b-mapLink";
    G.innerHTML = "—сылка на это место";
    this.addToToolBar = function(L) {
        L.getContainer().appendChild(G)
    };
    function H() {
        var M = F.clone();
        for (var L in D) {
            if (D[L] !== null) {
                M.addParam(L, D[L])
            }
        }
        return M.toString()
    }
    this.toString = function() {
        for (var L = 0; L < J.length; L++) {
            J[L]()
        }
        return H()
    };
    function K() {
        G.href = this.toString()
    }
    y5.Events.observe(["focus","mouseover"], K, G, true, this);
    var A;
    function I() {
        D.ll = A.isMoved && A.getCenter().encode() || null;
        D.spn = A.isMoved && A.getSpan().encode() || null;
        D.l = A.getLayers() || null;
        D.rl = A.getRulerState() || null
    }
    this.observeMap = function(L) {
        A = L;
        J.push(I)
    };
    var E;
    function C() {
        D.text = E.text || null;
        D.sll = E.center && E.center.encode() || null;
        D.sspn = E.span && E.span.encode() || null
    }
    this.observeSearch = function(L) {
        E = L;
        J.push(C)
    }
}
function init() {
    y5.registerNamespace("Maps", "maps.js");
    app = new Application();
    app.loadHomePage()
}
function blurExample(A) {
    if (!A.value) {
        A.value = A.title;
        A.title = "";
        y5.Classes.add(A, "g-example")
    }
}
function focusExample(A) {
    if (!A.title) {
        A.title = A.value;
        A.value = ""
    }
    y5.Classes.remove(A, "g-example")
}
YMaps.Point.prototype.encode = YMaps.GeoPoint.prototype.encode = function() {
    return Number(this.x.toFixed(6)) + "," + Number(this.y.toFixed(6))
};
YMaps.GeoBounds.prototype.getSpan = function() {
    return new YMaps.Point(this.getWidth(), this.getHeight())
};