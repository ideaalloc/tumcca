/*
    *
    * Wijmo Library 5.20143.39
    * http://wijmo.com/
    *
    * Copyright(c) GrapeCity, Inc.  All rights reserved.
    * 
    * Licensed under the Wijmo Commercial License. 
    * sales@wijmo.com
    * http://wijmo.com/products/wijmo-5/license/
    *
    */
/*
* Wijmo culture file: es (Spanish)
*/
wijmo.culture = {
    Globalize: {
        numberFormat: {
            '.': ',',
            ',': '.',
            percent: { pattern: ['-n %', 'n %'] },
            currency: { decimals: 2, symbol: '€', pattern: ['-n $', 'n $'] }
        },
        calendar: {
            '/': '/',
            ':': ':',
            firstDay: 1,
            days: ['domingo', 'lunes', 'martes', 'miércoles', 'jueves', 'viernes', 'sábado'],
            daysAbbr: ['do.', 'lu.', 'ma.', 'mi.', 'ju.', 'vi.', 'sá.'],
            months: ['enero', 'febrero', 'marzo', 'abril', 'mayo', 'junio', 'julio', 'agosto', 'septiembre', 'octubre', 'noviembre', 'diciembre'],
            monthsAbbr: ['ene.', 'feb.', 'mar.', 'abr.', 'may.', 'jun.', 'jul.', 'ago.', 'sep.', 'oct.', 'nov.', 'dic.'],
            am: ['', ''],
            pm: ['', ''],
            eras: ['d. C.'],
            patterns: {
                d: 'dd/MM/yyyy', D: 'dddd, d" de "MMMM" de "yyyy',
                f: 'dddd, d" de "MMMM" de "yyyy H:mm', F: 'dddd, d" de "MMMM" de "yyyy H:mm:ss',
                t: 'H:mm', T: 'H:mm:ss',
                m: 'd" de "MMMM', M: 'd" de "MMMM',
                y: 'MMMM" de "yyyy', Y: 'MMMM" de "yyyy',
                g: 'dd/MM/yyyy H:mm', G: 'dd/MM/yyyy H:mm:ss',
                s: 'yyyy"-"MM"-"dd"T"HH":"mm":"ss'
            }
        }
    },
    FlexGrid: {
        groupHeaderFormat: '<b>{value} </b>({count:n0} ítems)'
    },
    FlexGridFilter: {
        header: 'Mostrar ítems donde el valor',
        and: 'Y',
        or: 'O',
        apply: 'Aplicar',
        clear: 'Borrar',
        stringOperators: [
            { name: '(ninguno)', op: null },
            { name: 'Es igual a', op: 0 },
            { name: 'No es igual a', op: 1 },
            { name: 'Comienza con x', op: 6 },
            { name: 'Termina con', op: 7 },
            { name: 'Contiene', op: 8 },
            { name: 'No contiene', op: 9 }
        ],
        numberOperators: [
            { name: '(ninguno)', op: null },
            { name: 'Es igual a', op: 0 },
            { name: 'No es igual a', op: 1 },
            { name: 'Es mayor que', op: 2 },
            { name: 'Es mayor que o igual a', op: 3 },
            { name: 'Es menor que', op: 4 },
            { name: 'Es menor que o igual a', op: 5 }
        ],
        dateOperators: [
            { name: '(ninguno)', op: null },
            { name: 'Es igual a', op: 0 },
            { name: 'Es antes de', op: 4 },
            { name: 'Es después de', op: 3 }
        ],
        booleanOperators: [
            { name: '(ninguno)', op: null },
            { name: 'Es igual a', op: 0 },
            { name: 'No es igual a', op: 1 }
        ]
    }
};
//# sourceMappingURL=wijmo.culture.es.js.map
