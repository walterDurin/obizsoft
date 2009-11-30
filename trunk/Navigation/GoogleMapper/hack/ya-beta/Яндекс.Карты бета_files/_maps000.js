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
(function(A) {
    A.fn.assignClass = function(B, C) {
        return this[C ? "addClass" : "removeClass"](B)
    };
    A.capitalize = function(B) {
        return B.charAt(0).toUpperCase() + B.substr(1).toLowerCase()
    }
})(jQuery);
function Application() {
    var C = this,B = $("#loader")[0],G = window.homePage,A = 0;
    if ($.browser.msie) {
        function E() {
            var I = document.body.clientHeight - $("#l-head")[0].offsetHeight,H = I - $("#b-foot-info")[0].offsetHeight - 4;
            $("#l-page,#l-body").height(I);
            $("#l-map,#l-panel").height(H)
        }
        $(window).resize(E);
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
        var K = $("#activeObject")[0].title + ", " + I.innerHTML,H = K.split(", ").slice(-2).join(", ");
        return F(I, I, {ll:J,name:H,text:K})
    }
}
function SearchForm(B) {
    var A = $("#mapsForm")[0];
    A.target = "loader";
    A.output.value = "js";
    $(A).submit(function() {
        this.sll.value = B.getCenter().toString();
        this.sspn.value = B.getSpan().toString()
    });
    this.setData = function(D) {
        for (var C in D) {
            A[C].value = D[C]
        }
    }
}
function Panel() {
    this.setData = function(B) {
        $("#l-panel").attr("scrollTop", 0);
        $("#panel").empty()[0].innerHTML = B;
        $("#activeObject").triggerHandler("click")
    };
    var A;
    this.activateElement = function(B) {
        if (A) {
            A.removeClass("active")
        }
        A = $(B);
        A.addClass("active")
    }
}
function Map() {
    var N = this,H = $("#l-map")[0],W = H.onclick(),V = new YMaps.Map(H),R = new YMaps.ToolBar(),U = V.converter,O = 0,X = [],D = {},F = new YMaps.Placemark(null, {balloonOptions:{mapAutoPan:0}}),T = YMaps.MapType,E = ["MAP","SATELLITE","HYBRID"],Q = {map:1,sat:2,skl:4},P = {},I = {bl:"blue",db:"darkblue","do":"darkorange",gn:"green",gr:"gray",lb:"lightblue",nt:"night",or:"orange",pn:"pink",rd:"red",vv:"violet",wt:"white",yw:"yellow"};
    E.forEach(function(Y) {
        P[G(T[Y].getLayers())] = T[Y]
    });
    V.addControl(R);
    V.addControl(new YMaps.ScaleLine());
    V.addControl(new YMaps.TypeControl());
    V.addControl(new YMaps.MiniMap());
    V.addControl(new YMaps.Zoom());
    V.enableScrollZoom(1);
    V.enableHotKeys(1);
    V.removeType(T.HYBRID);
    A(W.l);
    M(W);
    V.setRulerState(W.rl || "");
    this.permalink = new Permalink(location.href.match("^http://[^/]+/"));
    this.permalink.observeMap(this);
    this.permalink.observeSearch(D);
    this.permalink.addToToolBar(R);
    this.isMoved = 1;
    YMaps.Events.observe(V, [V.Events.Update,V.Events.MoveEnd], function() {
        this.isMoved = 1
    }, this);
    this.setData = function(Y) {
        J();
        C();
        A(Y.l);
        M(Y);
        this.isMoved = 1;
        O = !!Y.ll;
        K(Y.pt)
    };
    this.getCenter = function() {
        return V.getCenter()
    };
    this.getCenterByOffset = function(Z) {
        var Y = U.coordinatesToLocalPixels(V.getCenter());
        return U.localPixelsToCoordinates(Y.moveBy(Z))
    };
    this.getSpan = function() {
        return V.getBounds().getSpan()
    };
    this.getRulerState = function() {
        return V.getRulerState()
    };
    this.showSearchResult = function(a) {
        D.name = a.name;
        D.text = a.text;
        D.center = L(a.ll);
        if (a.bb) {
            D.bounds = S(a.bb);
            D.span = D.bounds.getSpan()
        }
        var Y = D.center,Z = a.bb && D.bounds.getMapZoomLevel(V);
        if (O) {
            O = 0
        } else {
            if (Z && Z !== V.getZoom()) {
                V.setCenter(Y, Z)
            } else {
                V.panTo(Y)
            }
        }
        this.removeOverlay(F);
        F.setGeoPoint(Y);
        F.setBalloonContent(a.name);
        this.addOverlay(F);
        F.openBalloon();
        this.isMoved = 0
    };
    function G(Z) {
        var Y = 0;
        Z.forEach(function(a) {
            Y = Y | Q[a]
        });
        return Y
    }
    function A(Y) {
        if (!Y) {
            return
        }
        V.setType(P[G(Y.split(","))])
    }
    this.getLayers = function() {
        var Y = V.getType().getLayers().join(",");
        return Y != "map" && Y
    };
    function M(Y) {
        if (Y.spn) {
            V.setBounds(S(Y.ll, Y.spn))
        } else {
            if (Y.bb) {
                V.setBounds(S(Y.bb))
            } else {
                if (Y.ll && Y.zoom) {
                    V.setCenter(L(Y.ll), Y.zoom)
                }
            }
        }
    }
    function K(Y) {
        if (!Y) {
            return
        }
        Y.split("|").forEach(function(Z) {
            var c = Z.split(","),Z = new YMaps.GeoPoint(+c[0], +c[1]),a = c[2] && I[c[2].substr(2, 2)],b = new YMaps.Placemark(Z, {style:a && "default#" + a + "Point",hasBalloon:0});
            N.addOverlay(b)
        })
    }
    this.addOverlay = function(Y) {
        X.push(Y);
        V.addOverlay(Y)
    };
    this.removeOverlay = function(Z) {
        var Y = X.indexOf(Z);
        if (Y >= 0) {
            X.splice(Y, 1);
            V.removeOverlay(Z)
        }
    };
    function C() {
        while (X.length) {
            V.removeOverlay(X.pop())
        }
    }
    this.hasOverlayVisible = function(Y) {
        return Y && V.getBounds().contains(Y)
    };
    function J() {
        for (var Y in D) {
            delete D[Y]
        }
    }
    function B(Y) {
        return Y && new YMaps.Point(Y.x, Y.y)
    }
    function L(Y) {
        return Y && new YMaps.GeoPoint(Y.lng, Y.lat)
    }
    function S() {
        switch (arguments.length) {case (1):var a = arguments[0];return a && new YMaps.GeoBounds(L(a.lb), L(a.rt));case (2):var Z = arguments[0],Y = arguments[1];return Z && Y && YMaps.GeoBounds.fromCenterAndSpan(L(Z), B(Y))}
    }
}
function Permalink(B) {
    var I = this,J = [],D = {text:null,ll:null,spn:null,sll:null,sspn:null,l:null,rl:null},F = document.createElement("A");
    F.href = B;
    F.className = "b-mapLink";
    F.innerHTML = "Ссылка на это место";
    $(F).bind("focus mouseover", function() {
        this.href = I.toString()
    });
    this.addToToolBar = function(K) {
        K.getContainer().appendChild(F)
    };
    function G() {
        var L = [];
        for (var K in D) {
            if (D[K] !== null) {
                L.push(K + "=" + encodeURIComponent(D[K]))
            }
        }
        return B + "?" + L.join("&")
    }
    this.toString = function() {
        for (var K = 0; K < J.length; K++) {
            J[K]()
        }
        return G()
    };
    var A;
    function H() {
        D.ll = A.isMoved && A.getCenter().toString() || null;
        D.spn = A.isMoved && A.getSpan().toString() || null;
        D.l = A.getLayers() || null;
        D.rl = A.getRulerState() || null
    }
    this.observeMap = function(K) {
        A = K;
        J.push(H)
    };
    var E;
    function C() {
        D.text = E.text || null;
        D.sll = E.center && E.center.toString() || null;
        D.sspn = E.span && E.span.toString() || null
    }
    this.observeSearch = function(K) {
        E = K;
        J.push(C)
    }
}
(function() {
    var A,D,I,B;
    function N() {
        A = app.map;
        D = $("#fbForm");
        I = $("#fbMode");
        B = D.children()
    }
    var M = {},F = {},G = {draggable:1,hasBalloon:0},O = {red:"rd",green:"gn"};
    C("absent", "green", "Должен быть здесь", 50);
    C("misnamed", "red", "Неправильно назван");
    C("redundant", "red", "Не должно быть на карте");
    C("wrong", "red", "Неправильно расположен");
    C("correct", "green", "Должен быть здесь", 50);
    function C(T, P, Q, S) {
        var R = new YMaps.Placemark(null, G);
        R.id = $.capitalize(T);
        R.name = Q;
        R.metaDataProperty = {color:P,offset:new YMaps.Point(S)};
        R.setStyle("default#" + P + "Point");
        YMaps.Events.observe(R, [R.Events.DragEnd,R.Events.PositionChange], E);
        M[T] = R
    }
    function E() {
        var R = $("#fb" + this.id + "Place")[0],P = this.getGeoPoint().toString(),Q = O[this.metaDataProperty.color];
        R.value = A.permalink + "&pt=" + encodeURIComponent(P + ",pm" + Q);
        R.nextSibling.value = P;
        F[this.metaDataProperty.color] = this.getGeoPoint()
    }
    function H(Q) {
        var R = Q.metaDataProperty,P = F[R.color];
        if (!A.hasOverlayVisible(P)) {
            P = R.offset ? A.getCenterByOffset(R.offset) : A.getCenter()
        }
        Q.setGeoPoint(P);
        Q.setIconContent(Q.name);
        A.addOverlay(Q)
    }
    function K() {
        for (i in M) {
            A.removeOverlay(M[i])
        }
    }
    function J() {
        $("#fbSend").attr("disabled", B.filter("." + I.val()).is(".wrong"))
    }
    function L() {
        var P = I.val();
        $("#fbSubject").val(I[0].options[I[0].selectedIndex].innerHTML);
        B.each(function() {
            $("input,textarea", this).attr("disabled", !$(this).hasClass(P))
        });
        J();
        D.attr("className", P);
        K();
        switch (P) {case"absent":case"misnamed":case"redundant":H(M[P]);break;case"misplaced":H(M.wrong);H(M.correct)}
    }
    Feedback = function() {
        N();
        $(".required", D).bind("change keyup", function() {
            $(this).parents(".fieldset").assignClass("wrong", !this.value);
            J()
        });
        var P = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-z]{2,7}$/i;
        $("#fbEmail").bind("change keyup", function() {
            $(this).parents(".fieldset").assignClass("wrong", this.value && !P.test(this.value));
            J()
        });
        D.submit(function() {
            $("#fbBrowser").val(navigator.userAgent);
            $("#fbPermalink").val(A.permalink.toString());
            $("#fbPermalinkLl").val(A.getCenter().toString());
            I.val("disabled", true);
            $(this.elements).each(function() {
                this.disabled = this.disabled || !this.value || $(this).is(".g-example")
            })
        });
        L();
        I.change(L)
    }
})();
$(function() {
    app = new Application();
    app.loadHomePage()
});
function blurExample(A) {
    if (!A.value) {
        A.value = A.title;
        A.title = "";
        $(A).addClass("g-example")
    }
}
function focusExample(A) {
    if (!A.title) {
        A.title = A.value;
        A.value = ""
    }
    $(A).removeClass("g-example")
}
["MAP","SATELLITE","HYBRID"].forEach(function(A) {
    YMaps.MapType[A].getMinZoom = function() {
        return 1
    }
});