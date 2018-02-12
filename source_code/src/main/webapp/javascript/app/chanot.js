var App = (function () {
    return {
        contextPath: window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)),
        numberOfPages: 10,
        maxResults: 4,
        iconUp: 'glyphicon-chevron-up',
        iconDown: 'glyphicon-chevron-down',
        asc: 'asc',
        desc: 'desc'
    };
})();

var Loading = (function () {
    var dialog;
    return {
        show: function (message) {
            message = message ? message : 'Cargando...';
            dialog = bootbox.dialog({
                message: '<div class="text-center"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> ' + message + '</div>'
            });
        },
        hide: function () {
            if (dialog) {
                dialog.modal('hide');
            }
        }
    };
})();

var Alert = (function () {
    return {
        show: function (message, callback, labelOk) {
            bootbox.alert({
                message: message,
                closeButton: false,
                callback: function () {
                    if (callback) {
                        callback();
                    }
                },
                buttons: {
                    ok: {
                        label: labelOk ? labelOk : 'Aceptar'
                    }
                }
            });
        }
    };
})();

var Confirm = (function () {
    return {
        show: function (message, callback, labelOk, labelCancel) {
            bootbox.confirm({
                message: message,
                closeButton: false,
                callback: function (result) {
                    if (result && callback) {
                        callback();
                    }
                },
                buttons: {
                    confirm: {
                        label: labelOk ? labelOk : 'Aceptar'
                    },
                    cancel: {
                        label: labelCancel ? labelCancel : 'Cancelar'
                    }
                }
            });
        }
    };
})();

