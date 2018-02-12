var posPalabra;
var posOracion;
var totalPalabras;
var totalOraciones;
var oracion;
var entityTags;

var categories = {
    'Seleccione': ['Seleccione'],
    'Locación': ['Único', 'Inicio', 'Medio', 'Fin'],
    'Organización': ['Único', 'Inicio', 'Medio', 'Fin'],
    'Persona': ['Único', 'Inicio', 'Medio', 'Fin'],
    'Número': ['Único', 'Inicio', 'Medio', 'Fin'],
    'Fecha': ['Único', 'Inicio', 'Medio', 'Fin'],
    'Otro': ['']
};
var subCategories = {
    'Seleccione': ['Seleccione'],
    'Locación': ['País', 'Ciudad', 'Río'],
    'Organización': ['Institución', 'ONG', 'Empresa'],
    'Persona': ['Nombre', 'Sobrenombre', 'Cargo'],
    'Número': [''],
    'Fecha': [''],
    'Otro': ['']
};

$(function () {

    $('#btnProcesar').click(function () {
        predecirEntidades(oracion)
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
                obtenerPalabra(posPalabra);
            } else {
                pintarPrediccion();
            }
        }
    });

    $('#btnPalabraLeft').click(function () {
        removerVerificacionAnotacion();
        if (posPalabra > 0) {
            posPalabra -= 1;
            var pal = $('#sentenceTable tbody td:nth(' + posPalabra + ')').text();
            $('#pal').text("Palabra " + (posPalabra + 1) + " de " + totalPalabras + ": " + pal);
            $('#txtPalabra').val(pal);
            $('#trBody td:eq(' + (posPalabra + 1) + ')').removeClass('info');
            $('#trBody td:eq(' + posPalabra + ')').addClass('info');
            if (!$('#trHead th:eq(' + posPalabra + ')').hasClass('danger')) {
                obtenerPalabra(posPalabra);
            } else {
                pintarPrediccion();
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
        $('#lstCategoria').parsley().validate();
        if ($('#txtPalabra').parsley().isValid() && $('#lstCategoria').parsley().isValid()) {
            $('#sentenceTable thead th:nth(' + posPalabra + ')').removeClass("danger warning").addClass("success");
            enviarPalabraAnotada();
        }
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

    $('#lstCategoria').change(function () {
        $('#lstSubCat').prop("disabled", false);
        var categoria = $(this).find('option:selected').text(), lcns = subCategories[categoria] || [];
        var html = $.map(lcns, function (lcn) {
            return '<option value="' + lcn + '">' + lcn + '</option>'
        }).join('');
        $('#lstSubCat').html(html)
        if (html == '<option value=""></option>') {
            $('#lstSubCat').prop("disabled", true);
        }
    });

    $.get(App.contextPath + "/pages/entidad/getOracion",
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

    $.get(App.contextPath + "/pages/entidad/nombreArchivo",
            function (response) {
                if (response.error) {
                    alert(response.mensaje);
                } else {
                    $('#tituloArchivo').text("Anotación de Entidades - " + response.mensaje);
                }
            });

});

function enviarPalabraAnotada() {
    $.post(App.contextPath + '/pages/entidad/anotarPalabra', {
        palabra: $('#txtPalabra').val(),
        categoria: $('#lstCategoria option:selected').text(),
        subCategoria: $('#lstSubcategoria option:selected').text(), //posicion
        subCat: $('#lstSubCat option:selected').text(), //subcategoria
        posicion: posPalabra,
        prediccion: entityTags[posPalabra]
    }, function (response) {
        if (response.error) {
            alert(response.mensaje);
        }
    });
}

function cambiarOracion(dir) {
    if ((posOracion + dir) >= 1 && totalOraciones >= (posOracion + dir)) {
        removerVerificacionAnotacion();
        posOracion = posOracion + dir;
        $.post(App.contextPath + '/pages/entidad/cambiarOracion', {
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
    $.post(App.contextPath + '/pages/entidad/getPalabra', {
        posicion: pos
    }, function (response) {
        if (!response.error) {
            var optCat = $("#lstCategoria option:contains('" + response.categoria + "')").attr('value');
            $("#lstCategoria").val(optCat).change();
            var optSub = $("#lstSubcategoria option:contains('" + response.subCategoria + "')").attr('value');
            $("#lstSubcategoria").val(optSub).change();
            var optSubCat = $("#lstSubCat option:contains('" + response.subCat + "')").attr('value');
            $("#lstSubCat").val(optSubCat).change();
        } else {
            alert(response.mensaje);
        }
    });
}

function removerVerificacionAnotacion() {
    $('#txtPalabra').parsley().reset();
    $('#lstCategoria').parsley().reset();
}

function predecirEntidades(oracion) {
    $.get('http://scarif.inf.pucp.edu.pe:8070/chaner/api/ner/' + oracion, {
    }, function (response) {
        entityTags = (response.result);
        console.log(entityTags);
        posPalabra = 0;
        $('#panelPalabra').show();
        $("#afijoTable").find("tr:gt(0)").remove();
        var pal = $('#sentenceTable tbody td:first').text();
        $('#pal').text("Palabra " + (posPalabra + 1) + " de " + totalPalabras + ": " + pal);
        $('#txtPalabra').val(pal);
        $('#btnProcesar').prop("disabled", true);
        $('#trBody td:eq(' + posPalabra + ')').addClass('info');
        $('#txtLema').val("");
        $('#txtLema').focus();
        if (!$('#trHead th:eq(' + posPalabra + ')').hasClass('danger')) {
            obtenerPalabra(0);
        } else {
            pintarPrediccion();
        }
    }).fail(function () {
        $.get('http://localhost:8070/chaner/api/ner/' + oracion, {
        }, function (response) {
            entityTags = (response.result);
            console.log(entityTags);
            posPalabra = 0;
            $('#panelPalabra').show();
            $("#afijoTable").find("tr:gt(0)").remove();
            var pal = $('#sentenceTable tbody td:first').text();
            $('#pal').text("Palabra " + (posPalabra + 1) + " de " + totalPalabras + ": " + pal);
            $('#txtPalabra').val(pal);
            $('#btnProcesar').prop("disabled", true);
            $('#trBody td:eq(' + posPalabra + ')').addClass('info');
            $('#txtLema').val("");
            $('#txtLema').focus();
            if (!$('#trHead th:eq(' + posPalabra + ')').hasClass('danger')) {
                obtenerPalabra(0);
            } else {
                pintarPrediccion();
            }
        });
    });
}

function pintarPrediccion() {
    var cat = "";
    var subCat = "";
    var tag = entityTags[posPalabra];
    console.log(tag)
    if (tag == "O") {
        cat = "Otro";
    } else {
        if (tag.length == 3) {
            subCat = "Único";
        } else {
            var pos = tag.substring(0, 1);
            if (pos == "B") {
                subCat = "Inicio";
            } else if (pos == "I") {
                subCat = "Medio";
            } else {
                subCat = "Fin";
            }
        }
        var tipo = tag.substring(tag.length - 3);
        if (tipo == "LOC") {
            cat = "Locación";
        } else if (tipo == "PER") {
            cat = "Persona";
        } else if (tipo == "ORG") {
            cat = "Organización";
        } else if (tipo == "NUM") {
            cat = "Número";
        } else {
            cat = "Fecha";
        }

    }
    var optCat = $("#lstCategoria option:contains('" + cat + "')").attr('value');
    $("#lstCategoria").val(optCat).change();
    var optSub = $("#lstSubcategoria option:contains('" + subCat + "')").attr('value');
    $("#lstSubcategoria").val(optSub).change();
}
