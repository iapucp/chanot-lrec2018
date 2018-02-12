var posPalabra;
var posOracion;
var totalPalabras;
var totalOraciones;
var timeIni;
var timeFin;
var edicion = 0;
var htmlEdicion;
var lemaPred = "";
var predictedTags;
var oracion;
var categories = {
    'Seleccione': ['Seleccione'],
    'Adjetivo': ['Calificativo', 'Cuantificador', 'Indefinido'],
    'Adverbio': ['Cantidad', 'Locación', 'Manera', 'Tiempo'],
    //'Clítico': ['De posición libre', 'De segunda posición'],
    'Conjunción': ['Coordinante', 'Subordinanate'],
    'Determinante': ['Cuantificador', 'Demostrativo', 'Interrogativo', 'Posesivo'],
    'Interjección': [''],
    'Nombre': ['Contable', 'No contable', 'Parte del cuerpo'],
    'Nombre Propio': [''],
    'Numeral': [''],
    'Postposición': [''],
    'Pronombre': ['Demostrativo', 'Enfático', 'Personal', 'Indefinido'],
    'Puntuación': [''],
    'Símbolo': [''],
    'Verbo': ['Intransitivo', 'Transitivo', 'Copulativo'],
    'Verbo Auxiliar': [''],
    'Palabra Interrogativa': [''],
    'Onomatopeya': [''],
};

$(function () {

    $('#btnProcesar').click(function () {
        predecirPOST(oracion)
        posPalabra = 0;
        $('#panelPalabra').show();
        $("#afijoTable").find("tr:gt(0)").remove();
        var pal = $('#sentenceTable tbody td:first').text();
        timeIni = new Date();
        $('#pal').text("Palabra " + (posPalabra + 1) + " de " + totalPalabras + ": " + pal);
        $('#txtPalabra').val(pal);
        $('#btnProcesar').prop("disabled", true);
        $('#trBody td:eq(' + posPalabra + ')').addClass('info');
        $('#txtLema').val("");
        $('#txtLema').focus();
        if (!$('#trHead th:eq(' + posPalabra + ')').hasClass('danger')) {
            if (!$('#trHead th:eq(' + posPalabra + ')').hasClass('success')) {
                predecirLema(pal);
                $('#txtLema').val("");
            }
            obtenerPalabra(0);
        } else {
            predecirLema(pal);
            //$("#lstCategoria").val(-1).change();
        }
    });

    $('#btnPopupCancelar').click(function () {
        $('#txtAfijo').val("");
        $('#txtPosicion').val("");
        $('#lstTipo').val(-1);
        $('#modalAfijos').modal('hide');
        $('#txtAfijo').parsley().reset();
        $('#txtPosicion').parsley().reset();
        $('#lstTipo').parsley().reset();
        edicion = 0;
    });

    $('#btnPopupAgregar').click(function () {
        $('#txtAfijo').parsley().validate();
        $('#txtPosicion').parsley().validate();
        $('#lstTipo').parsley().validate();
        if ($('#txtAfijo').parsley().isValid() && $('#txtPosicion').parsley().isValid() && $('#lstTipo').parsley().isValid()) {
            var af = $('#txtAfijo').val();
            var tipo = $('#lstTipo :selected').text();
            var pos = $('#txtPosicion').val();
            if (edicion == 0) {
                $('#afijoTable tr:last').after('<tr><td>' + af + '</td><td>' + tipo + '</td><td>' + pos + '</td><td>'
                        + '<button type="button" class="btn btn-warning btn-xs js-editar" title="Editar"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;'
                        + '<button type="button" class="btn btn-danger btn-xs js-eliminar" title="Eliminar"><span class="glyphicon glyphicon-remove"></span></button>'
                        + '</td></tr>');
                $('#btnPopupCancelar').click();
            } else {
                $(htmlEdicion).eq(0).text(af);
                $(htmlEdicion).eq(1).text(tipo);
                $(htmlEdicion).eq(2).text(pos);
                $('#btnPopupCancelar').click();
            }
        }
    });

    $('#btnPopupTradGuardar').click(function () {
        $('#txtTraduccion').parsley().validate();
        var tra = $('#txtTraduccion').val();
        if ($('#txtTraduccion').parsley().isValid()) {
            guardarTraduccion(tra);
        }
    });

    $('#btnPopupTradCancelar').click(function () {
        $('#modalTraduccion').modal('hide');
        $('#txtTraduccion').parsley().reset();
    });

    $('#btnPalabraRight').click(function () {
        removerVerificacionAnotacion();
        $("#afijoTable").find("tr:gt(0)").remove();
        if (totalPalabras > posPalabra + 1) {
            posPalabra += 1;
            var pal = $('#sentenceTable tbody td:nth(' + posPalabra + ')').text();
            $('#pal').text("Palabra " + (posPalabra + 1) + " de " + totalPalabras + ": " + pal);
            $('#txtPalabra').val(pal);
            $('#txtLema').val("");
            $('#trBody td:eq(' + (posPalabra - 1) + ')').removeClass('info');
            $('#trBody td:eq(' + posPalabra + ')').addClass('info');
            if (!$('#trHead th:eq(' + (posPalabra) + ')').hasClass('danger')) {
                if (!$('#trHead th:eq(' + posPalabra + ')').hasClass('success')) {
                    predecirLema(pal);
                    $('#txtLema').val("");
                }
                obtenerPalabra(posPalabra);
            } else {
                predecirLema(pal);
                pintarPredTag();
                //$("#lstCategoria").val(-1).change();
            }
        }
    });

    $('#btnPalabraLeft').click(function () {
        removerVerificacionAnotacion();
        $("#afijoTable").find("tr:gt(0)").remove();
        if (posPalabra > 0) {
            posPalabra -= 1;
            var pal = $('#sentenceTable tbody td:nth(' + posPalabra + ')').text();
            $('#pal').text("Palabra " + (posPalabra + 1) + " de " + totalPalabras + ": " + pal);
            $('#txtPalabra').val(pal);
            $('#txtLema').val("");
            $('#trBody td:eq(' + (posPalabra + 1) + ')').removeClass('info');
            $('#trBody td:eq(' + posPalabra + ')').addClass('info');
            if (!$('#trHead th:eq(' + posPalabra + ')').hasClass('danger')) {
                if (!$('#trHead th:eq(' + posPalabra + ')').hasClass('success')) {
                    predecirLema(pal);
                    $('#txtLema').val("");
                }
                obtenerPalabra(posPalabra);
            } else {
                predecirLema(pal);
                pintarPredTag()
                //$("#lstCategoria").val(-1).change();
            }
        }
    });

    $('#btnOracionLeft').click(function () {
        cambiarOracion(-1);
    });

    $('#btnOracionRight').click(function () {
        cambiarOracion(1);
    });

    $('#btnAnotar').click(function () {
        $('#txtPalabra').parsley().validate();
        $('#txtLema').parsley().validate();
        $('#lstCategoria').parsley().validate();
        if ($('#txtPalabra').parsley().isValid() && $('#txtLema').parsley().isValid() && $('#lstCategoria').parsley().isValid()) {
            $('#sentenceTable thead th:nth(' + posPalabra + ')').removeClass("danger warning").addClass("success");
            enviarPalabraAnotada();
        }
    });

    $('#modalTraduccion').on('shown.bs.modal', function () {
        $('#txtTraduccion').val($('#panelTraduccion').text());
        $('#txtTraduccion').focus();
    })

    $('#modalAfijos').on('shown.bs.modal', function () {
        $('#txtAfijo').focus();
    })

    $('#afijoTable').off('click', '.js-eliminar').on('click', '.js-eliminar', function () {
        $(this.closest('tr')).remove();
    });

    $('#afijoTable').off('click', '.js-editar').on('click', '.js-editar', function () {
        sublist = $(this.closest('tr')).find("td");
        var optText = $(sublist).eq(1).text();
        var optVal = $("#lstTipo option:contains('" + optText + "')").attr('value');
        $('#txtAfijo').val($(sublist).eq(0).text());
        $("#lstTipo").val(optVal)
        $('#txtPosicion').val($(sublist).eq(2).text());
        $('#btnAgregar').click();
        edicion = 1;
        htmlEdicion = sublist;
    });

    $('#lstCategoria').change(function () {
        $('#lstSubcategoria').prop("disabled", false);
        var categoria = $(this).find('option:selected').text(), lcns = categories[categoria] || [];
        var html = $.map(lcns, function (lcn) {
            return '<option value="' + lcn + '">' + lcn + '</option>'
        }).join('');
        $('#lstSubcategoria').html(html)
        if (html == '<option value=""></option>') {
            $('#lstSubcategoria').prop("disabled", true);
        }
    });

    $.get(App.contextPath + "/pages/gramatical/getOracion",
            function (response) {
                if (response.error) {
                    alert(response.mensaje);
                } else {
                    var listado = response.palabras;
                    var i = 0;
                    totalPalabras = response.total;
                    if (listado && listado.length > 0) {
                        totalOraciones = response.totalOraciones;
                        posOracion = response.oracionActual;
                        oracion = response.oracion;
                        oracion = oracion.replace("?", "%3F");
                        $('#panelTraduccion').text(response.traduccion);
                        $('#contOracion').text("Oración " + posOracion + " de " + totalOraciones);
                        $.each(listado, function (key, value) {
                            i += 1;
                            if (value.estado == 0) {
                                $('#trHead').append('<th class="danger">' + i + '</th>');
                            } else if (value.estado == 1) {
                                $('#trHead').append('<th class="warning">' + i + '</th>');
                            } else if (value.estado == 2) {
                                $('#trHead').append('<th class="success">' + i + '</th>');
                            }
                            $('#trBody').append('<td>' + value.palabra + '</td>');
                        });
                    }
                }
            });

    $.get(App.contextPath + "/pages/getUsuario",
            function (response) {
                if (response.error) {
                    alert(response.mensaje);
                } else {
                    $('#msjBienvenido').text($('#msjBienvenido').text() + response.mensaje);
                }
            });

    $.get(App.contextPath + "/pages/gramatical/nombreArchivo",
            function (response) {
                if (response.error) {
                    alert(response.mensaje);
                } else {
                    $('#tituloArchivo').text("Anotación Gramatical - " + response.mensaje);
                }
            });

});

function enviarPalabraAnotada() {
    timeFin = new Date();
    var idAfijos = []
    var time = Math.round(new Date(timeFin - timeIni) / 1000);
    var afij = "[";
    listado = $('#afijoTable tr:not(.head-afijo)');
    $.each(listado, function (key, value) {
        sublist = $(value).find("td");
        var item = "";
        $.each(sublist, function (key, value) {
            if (key == 0) {
                item += '{"afijo":"' + $(value).text();
            } else if (key == 1) {
                item += '","tipo":"' + $(value).text();
            } else if (key == 2) {
                item += '","posicion":"' + $(value).text();
                idAfijos.push($(value).text());
            }
        });
        afij += item + '"},';
    });
    afij = afij.slice(0, -1)
    afij += ']';
    idAfijos.sort();
    var sub = 1;
    for (var i = 0; i < idAfijos.length - 1; i++) {
        if (idAfijos[i + 1] == idAfijos[i]) {
            sub = 0;
            break;
        }
    }
    if (sub == 1) {
        $.post(App.contextPath + '/pages/gramatical/anotarPalabra', {
            palabra: $('#txtPalabra').val(),
            lema: $('#txtLema').val(),
            lemaPred: lemaPred,
            posTagPred: predictedTags[posPalabra],
            categoria: $('#lstCategoria option:selected').text(),
            subCategoria: $('#lstSubcategoria option:selected').text(),
            afijos: afij,
            posicion: posPalabra,
            tiempo: time
        }, function (response) {
            if (response.error) {
                alert(response.mensaje);
            }
        });
    }else{
        alert("Existen afijos con la misma numeración");
    }
}

function cambiarOracion(dir) {
    if ((posOracion + dir) >= 1 && totalOraciones >= (posOracion + dir)) {
        removerVerificacionAnotacion();
        posOracion = posOracion + dir;
        $.post(App.contextPath + '/pages/gramatical/cambiarOracion', {
            direccion: dir
        }, function (response) {
            if (!response.error) {
                oracion = response.oracion;
                oracion = oracion.replace("?", "%3F");
                $('#btnProcesar').prop("disabled", false);
                $('#trHead').html("");
                $('#trBody').html("");
                $('#panelPalabra').hide();
                $('#contOracion').text("Oración " + posOracion + " de " + totalOraciones);
                var listado = response.palabras;
                var i = 0;
                totalPalabras = response.total;
                if (listado && listado.length > 0) {
                    var cadena = '';
                    $('#panelTraduccion').text(response.traduccion);
                    $.each(listado, function (key, value) {
                        i += 1;
                        if (value.estado == 0) {
                            $('#trHead').append('<th class="danger">' + i + '</th>');
                        } else if (value.estado == 1) {
                            $('#trHead').append('<th class="warning">' + i + '</th>');
                        } else if (value.estado == 2) {
                            $('#trHead').append('<th class="success">' + i + '</th>');
                        }
                        $('#trBody').append('<td>' + value.palabra + '</td>');
                    });
                }
            } else {
                alert(response.mensaje);
            }
        });
    }
}

function obtenerPalabra(pos) {
    $.post(App.contextPath + '/pages/gramatical/getPalabra', {
        posicion: pos
    }, function (response) {
        if (!response.error) {
            $('#txtLema').val(response.lema);
            var optCat = $("#lstCategoria option:contains('" + response.categoria + "')").attr('value');
            $("#lstCategoria").val(optCat).change();
            var optSub = $("#lstSubcategoria option:contains('" + response.subCategoria + "')").attr('value');
            $("#lstSubcategoria").val(optSub).change();
            var afijos = response.afijos.afijos;
            var totalAfijos = afijos.total;
            if (afijos && afijos.length > 0) {
                $.each(afijos, function (key, value) {
                    var af = value.afijo;
                    var tipo = value.tipo;
                    var pos = value.posicion;
                    $('#afijoTable tr:last').after('<tr><td>' + af + '</td><td>' + tipo + '</td><td>' + pos + '</td><td>'
                            + '<button type="button" class="btn btn-warning btn-xs js-editar" title="Editar"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;'
                            + '<button type="button" class="btn btn-danger btn-xs js-eliminar" title="Eliminar"><span class="glyphicon glyphicon-remove"></span></button>'
                            + '</td></tr>');
                });
            }
        } else {
            alert(response.mensaje);
        }
    });
}

function removerVerificacionAnotacion() {
    timeIni = new Date();
    $('#txtPalabra').parsley().reset();
    $('#txtLema').parsley().reset();
    $('#lstCategoria').parsley().reset();
}

function guardarTraduccion(tra) {
    $.post(App.contextPath + '/pages/gramatical/editarTraduccion', {
        traduccion: tra
    }, function (response) {
        if (!response.error) {
            $('#panelTraduccion').text($('#txtTraduccion').val());
            $('#btnPopupTradCancelar').click();
        } else {
            alert(response.mensaje);
        }
    });
}

function pintarPredTag() {
    var tag = predictedTags[ posPalabra ];
    var optCat = $("#lstCategoria option:contains('" + tag + "')").attr('value');
    $("#lstCategoria").val(optCat).change();
}

function predecirLema(palabra) {
    $.get('http://scarif.inf.pucp.edu.pe:8070/chalem/api/lemmatize/sklearn/' + palabra, {
    }, function (response) {
        if ($('#txtLema').val() == "") {
            $('#txtLema').val(response.result);
            lemaPred = response.result;
        }
    }).fail(function () {
        $.get('http://localhost:8070/chalem/api/lemmatize/sklearn/' + palabra, {
        }, function (response) {
            if ($('#txtLema').val() == "") {
                $('#txtLema').val(response.result);
                lemaPred = response.result;
            }
        });
    });
}

function predecirPOST(oracion) {
    $.get('http://scarif.inf.pucp.edu.pe:8070/chapost/api/post/' + oracion, {
    }, function (response) {
        predictedTags = response.result
        pintarPredTag();

    }).fail(function () {
        $.get('http://localhost:8070/chapost/api/post/' + oracion, {
        }, function (response) {
            predictedTags = response.result
            pintarPredTag();
        });
    });
}
