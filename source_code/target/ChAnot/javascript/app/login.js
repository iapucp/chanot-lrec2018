$(function () {

    $('#txtContrasenha').keypress(function (e) {
        if (e.which == 13) {
             login();
        }
    });

    $('#btnLogin').click(function () {
        login();
    });
});

function login() {
    var user = $('#txtUsuario').val();
    var pass = $('#txtContrasenha').val();
    $.post(App.contextPath + '/pages/login', {
        user: user,
        pass: pass
    }, function (response) {
        if (!response.error) {
            window.location = App.contextPath + '/pages/menu';
        } else {
            alert(response.mensaje);
        }
    });
}