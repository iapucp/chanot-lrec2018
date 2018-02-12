var archBrat;
var opcionBrat;

var options = {
    valueNames: ['name', 'status'],
    page: 8,
    pagination: true
};

var userList;

$(function () {

    cargarArchivos();

    $.get(App.contextPath + "/pages/getUsuario",
            function (response) {
                if (response.error) {
                    alert(response.mensaje);
                } else {
                    $('#msjBienvenido').text($('#msjBienvenido').text() + response.mensaje);
                }
            });

    $('#input-archivo').fileupload({
        dataType: 'json',
        replaceFileInput: false,
        autoUpload: true,
        done: function (e, data) {
            var response = data.result;
            if (!response.error) {
                cargarArchivos();
            } else {
                alert(response.mensaje);
            }

        }
    });
});


function anotacionGramatical(archivo) {
    $.post(App.contextPath + '/pages/gramatical/setArchivo', {
        archivo: archivo
    }, function (response) {
        if (response.error) {
            alert(response.mensaje);
        } else {
            window.location = App.contextPath + '/pages/gramatical/inicio';
        }
    });
}

function anotacionEntidad(archivo) {
    $.post(App.contextPath + '/pages/gramatical/setArchivo', {
        archivo: archivo
    }, function (response) {
        if (response.error) {
            alert(response.mensaje);
        } else {
            window.location = App.contextPath + '/pages/entidad/inicio';
        }
    });
}

function estadisticas(archivo) {
    $.post(App.contextPath + '/pages/getStatistics', {
        archivo: archivo
    }, function (response) {
        $('#titulo').text('Estadisticas: ' + archivo);
        if (response.error) {
            $('#txtOraciones').text("---");
            $('#txtPalabras').text("---");
            $('#txtTiempo').text("---");
            $('#txtProm').text("---");
        } else {
            $('#txtOraciones').text(response.oraciones);
            $('#txtPalabras').text(response.palabras);
            $('#txtTiempo').text(response.tiempoTotal);
            $('#txtProm').text(response.tiempoProm);
        }
    });
}

function brat(arch) {
    if (opcionBrat == 'new') {
        $.post(App.contextPath + '/pages/exportarBrat', {
            archivo: arch
        }, function (response) {
            if (response.error) {
                alert(response.mensaje);
            } else {
                archivo = response.mensaje + arch;
                $("#modalBrat").modal('show');
            }
        });
    } else if (opcionBrat == 'reset') {
        console.log('reescribir')
        $.post(App.contextPath + '/pages/reescribirBrat', {
            archivo: arch
        }, function (response) {
            if (response.error) {
                alert(response.mensaje);
            } else {
                archivo = response.mensaje + arch;
                $("#modalBrat").modal('show');
            }
        });

    }
}

function cargarArchivos() {

    $.get(App.contextPath + "/pages/archivos",
            function (response) {
                if (response.error) {
                    alert(response.mensaje);
                } else {
                    $('#filesTable tbody').html('');
                    $('#filesTable caption').html('');
                    var listado = response.archivos;
                    total = response.total;
                    if (listado && listado.length > 0) {
                        var cadena = '';
                        $.each(listado, function (key, value) {
                            if (value.estado == 1) {
                                cadena += '<tr class="success"><td class="name">' + value.archivo + '</td>' +
                                        '<td class="status">' + "En Proceso" + '</td>' +
                                        '<td>' +
                                        '<button type="button" class="btn btn-primary btn-xs js-gramatical" title="Anotación Gramatical"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;' +
                                        '<button type="button" class="btn btn-primary btn-xs js-estadistica" title="Estadisticas"><span class="glyphicon glyphicon-list-alt"></span></button>&nbsp;' +
                                        '<button type="button" class="btn btn-primary btn-xs js-brat" title="Brat"><span class="glyphicon glyphicon-share-alt"></span></button>&nbsp;' +
                                        '<button type="button" class="btn btn-primary btn-xs js-brat-reset" title="Reiniciar Brat"><span class="glyphicon glyphicon-refresh"></span></button>&nbsp;' +
                                        '<button type="button" class="btn btn-primary btn-xs js-entidad" title="Anotación Entidades"><span class="glyphicon glyphicon-th-large"></span></button>' +
                                        '</td>' +
                                        '</tr>';
                            } else if (value.estado == 0) {
                                cadena += '<tr class="danger"><td class="name">' + value.archivo + '</td>' +
                                        '<td class="status">' + "Sin Anotar" + '</td>' +
                                        '<td>' +
                                        '<button type="button" class="btn btn-primary btn-xs js-gramatical" title="Anotación Gramatical"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;' +
                                        '<button type="button" class="btn btn-primary btn-xs js-entidad" title="Anotación Entidades"><span class="glyphicon glyphicon-th-large"></span></button>' +
                                        '</td>' +
                                        '</tr>';
                            } else if (value.estado == 2) {
                                cadena += '<tr class="info"><td class="name">' + value.archivo + '</td>' +
                                        '<td class="status">' + "Finalizado" + '</td>' +
                                        '<td>' +
                                        '<button type="button" class="btn btn-primary btn-xs js-gramatical" title="Anotación Gramatical"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;' +
                                        '<button type="button" class="btn btn-primary btn-xs js-estadistica" title="Estadisticas"><span class="glyphicon glyphicon-list-alt"></span></button>&nbsp;' +
                                        '<button type="button" class="btn btn-primary btn-xs js-brat" title="Brat"><span class="glyphicon glyphicon-share-alt"></span></button>&nbsp;' +
                                        '<button type="button" class="btn btn-primary btn-xs js-brat-reset" title="Reiniciar Brat"><span class="glyphicon glyphicon-refresh"></span></button>&nbsp;' +
                                        '<button type="button" class="btn btn-primary btn-xs js-entidad" title="Anotación Entidades"><span class="glyphicon glyphicon-th-large"></span></button>' +
                                        '</td>' +
                                        '</tr>';
                            }
                        });
                        $('#filesTable tbody').html(cadena);
                    } else {
                        $('#filesTable caption').html('No se encontraron resultados.');
                    }
                }
                userList = new List('files', options);
            });

    $('#filesTable').off('click', '.js-gramatical').on('click', '.js-gramatical', function () {
        var archivo = $(this.closest('tr')).find("td:first").text();
        anotacionGramatical(archivo);
    });

    $('#filesTable').off('click', '.js-entidad').on('click', '.js-entidad', function () {
        var archivo = $(this.closest('tr')).find("td:first").text();
        anotacionEntidad(archivo);
    });

    $('#filesTable').off('click', '.js-estadistica').on('click', '.js-estadistica', function () {
        var archivo = $(this.closest('tr')).find("td:first").text();
        estadisticas(archivo.slice(0, -4));
        $('#modalEstadisticas').modal('show');
    });

    $('#filesTable').off('click', '.js-brat').on('click', '.js-brat', function () {
        archBrat = $(this.closest('tr')).find("td:first").text();
        opcionBrat = 'new';
        if ($(this.closest('tr')).hasClass('success')) {
            $('#mensajeBrat').text('Este archivo aún no se encuentra finalizado. ¿Desea continuar?');
            $('#modalBratAlerta').modal('show');
        } else {
            brat(archBrat.slice(0, -4));
        }

    });

    $('#filesTable').off('click', '.js-brat-reset').on('click', '.js-brat-reset', function () {
        archBrat = $(this.closest('tr')).find("td:first").text();
        opcionBrat = 'reset';
        $('#mensajeBrat').text('El archivo se volvera a generar. ¿Desea continuar?');
        $('#modalBratAlerta').modal('show');
    });

    $('#btnPopupCancelar').click(function () {
        $('#modalEstadisticas').modal('hide');
    });

    $('#btnPopupBratCancelar').click(function () {
        $('#modalBrat').modal('hide');
    });

    $('#btnPopupBratAlertaCancelar').click(function () {
        $('#modalBratAlerta').modal('hide');
    });

    $('#btnPopupBratAlertaContinuar').click(function () {
        $('#modalBratAlerta').modal('hide');
        brat(archBrat.slice(0, -4));
    });

    $('#btnPucp').click(function () {
        window.location = 'http://scarif.inf.pucp.edu.pe:8001/index.xhtml#/chana/' + archivo;
    });

    $('#btnPutty').click(function () {
        window.location = 'http://localhost:8001/index.xhtml#/chana/' + archivo;
    });
}
