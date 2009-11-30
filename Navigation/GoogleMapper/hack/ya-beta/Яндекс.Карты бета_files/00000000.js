
        var YMaps = {};
        YMaps.__MapData = {
            userKey: "AD23kUgBAAAAKAPnSgMAOCWDaBP4e3RA63n46p988XtiyxEAAAAAAAAAAABkM9dP_fJ79jtPHpf0Z4ThKda2Hg==",
            w1251: 0,
            versionPath: "http://api-beta-maps.yandex.ru/1.5.1/",
            constants: {
                copyrights: "Правообладатели",
                metric: {
                    metre: "м",
                    kilometre: "км"
                },
                types: {
                    MAP: "Схема",
                    SATELLITE: "Спутник",
                    HYBRID: "Гибрид"
                },
                hints: {
                    miniMap: {
                        show: "показать миникарту",
                        hide: "скрыть миникарту"
                    },
                    ruler: {
                        close: "Удалить путь",
                        minimize: "Скрыть/показать расcтояния",
                        sure: "Удалить все отметки? \n\n(для удаления только одной отметки\n дважды щелкните по ней мышью)"
                    },
                    toolBar: {
                        move: "Переместить карту",
                        magnifier: "Увеличить",
                        ruler: "Измерение расстояний на карте"
                    },
                    balloon: {
                        close: "Закрыть"
                    }
                }
            },
            tileUrlTemplates:{
                map: 'http://vec0%d.maps.yandex.net/tiles?l=map&%c',
                sat: 'http://sat0%d.maps.yandex.net/tiles?l=sat&%c',
                skl: 'http://vec0%d.maps.yandex.net/tiles?l=skl&%c'
            },
            printerHost: 'http:\/\/print.maps.yandex.net'
        }

        new function () {
            var scriptsToLoad = [
                    YMaps.__MapData.versionPath + 'YMapsData.xml?v=' + '1.17.1' + (YMaps.__MapData.w1251 ? "&w1251=1" : ""),
                    YMaps.__MapData.versionPath + 'YMaps.js'
                ],
                cssToLoad = [
                    YMaps.__MapData.versionPath + 'YMaps.css',
                    YMaps.__MapData.versionPath + 'YMaps-ie.css'
                ],
                i = 0, l = scriptsToLoad.length;

            
                
                var include = function include (src) {
                    document.write("<script src='" + src + "' type='text/javascript' charset='utf-8'></script>");
                };

                document.write(
                    '<!--[if gt IE 7]><!--><link rel="stylesheet" href="' + cssToLoad[0] + '"/><!--<![endif]-->' +
                    '<!--[if lt IE 8]><link rel="stylesheet" href="' + cssToLoad[1] + '"/><![endif]-->'
                );

                for (; i < l; i++) {
                    include(scriptsToLoad[i]);
                }

                YMaps.load = function (callback) {
                    if (typeof callback == 'function') {
                        callback();
                    }
                }
                
            
        }
    