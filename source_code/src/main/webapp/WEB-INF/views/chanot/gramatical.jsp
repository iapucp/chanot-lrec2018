<!DOCTYPE html>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html lang="es">
    <head>
        <%@ include file="/WEB-INF/views/common/head.jsp" %>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <%@ include file="/WEB-INF/views/common/header.jsp" %>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-primary">
                                <div class="panel-heading text-center">
                                    <strong id="tituloArchivo">Anotación Gramatical</strong>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class = "panel panel-info">
                                                <div class = "panel-heading">
                                                    <label id="contOracion"></label> 
                                                    <button type="button" class="btn btn-primary btn-sm pull-right" id="btnOracionRight">
                                                        <span class="glyphicon glyphicon-chevron-right"></span>
                                                    </button> 
                                                    <button type="button" class="btn btn-primary btn-sm pull-right" id="btnOracionLeft">
                                                        <span class="glyphicon glyphicon-chevron-left"></span>
                                                    </button> 
                                                    <div class="clearfix"></div>
                                                </div>
                                                <div class = "panel-body text-center">
                                                    <div class="table-responsive">
                                                        <table id="sentenceTable" class="table">
                                                            <thead>
                                                                <tr id="trHead">
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr id="trBody">
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class = "panel panel-info">
                                                        <div class = "panel-body text-center">
                                                            <label><a id="panelTraduccion" data-toggle="modal" data-target="#modalTraduccion"></a></label>                                                    
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>                                           
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12 pull-right">
                                            <div class="pull-right">
                                                <button type="button" class="btn btn-primary btn-sm" id="btnProcesar">
                                                    <span class="glyphicon glyphicon-wrench"></span>
                                                    <span class="js-label">Procesar</span>
                                                </button>                                             
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel-body oculto" id="panelPalabra">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class = "panel panel-default">
                                                <div class = "panel-heading" id="palabra">
                                                    <b id="pal"></b>
                                                    <button type="button" class="btn btn-default btn-sm pull-right" id="btnPalabraRight">
                                                        <span class="glyphicon glyphicon-chevron-right"></span>
                                                    </button> 
                                                    <button type="button" class="btn btn-default btn-sm pull-right" id="btnPalabraLeft">
                                                        <span class="glyphicon glyphicon-chevron-left"></span>
                                                    </button> 
                                                    <div class="clearfix"></div>
                                                </div>
                                                <div class = "panel-body text-center">
                                                    <div class="col-sm-5">
                                                        <div class="panel panel-default">
                                                            <div class="panel-body thread-row">
                                                                <div class="col-md-12" >
                                                                    <div class="row">
                                                                        <div class="form-group row">
                                                                            <label for="txtPalabra" class="col-md-4 col-form-label text-left chanot-margen-label-horizontal">Palabra</label>
                                                                            <div class="col-md-8">
                                                                                <input class="form-control" type="text" id="txtPalabra" required>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label for="txtLema" class="col-md-4 col-form-label text-left chanot-margen-label-horizontal">Lema</label>
                                                                            <div class="col-md-8">
                                                                                <input class="form-control" type="text" id="txtLema" required>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label for="lstCategoria" class="col-md-4 col-form-label text-left chanot-margen-label-horizontal">Categoría</label>
                                                                            <div class="col-md-8">
                                                                                <select class="form-control" id="lstCategoria" data-parsley-min="1" data-parsley-error-message="Seleccione una opción">
                                                                                    <option value="-1">Seleccione</option>
                                                                                    <option value="1">Adjetivo</option>
                                                                                    <option value="2">Adverbio</option>
                                                                                    <!--  <option value="3">Clítico</option> -->
                                                                                    <option value="4">Conjunción</option>
                                                                                    <option value="5">Determinante</option>
                                                                                    <option value="6">Interjección</option>
                                                                                    <option value="7">Nombre</option>
                                                                                    <option value="8">Nombre Propio</option>
                                                                                    <option value="9">Numeral</option>
                                                                                    <option value="10">Postposición</option>
                                                                                    <option value="11">Pronombre</option>
                                                                                    <option value="12">Puntuación</option>
                                                                                    <option value="13">Símbolo</option>
                                                                                    <option value="14">Verbo</option>
                                                                                    <option value="15">Verbo Auxiliar</option>  
                                                                                    <option value="16">Palabra Interrogativa</option>
                                                                                    <option value="17">Onomatopeya</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label for="lstSubcategoria" class="col-md-4 col-form-label text-left chanot-margen-label-horizontal">Subcategoría</label>
                                                                            <div class="col-md-8">
                                                                                <select class="form-control" id="lstSubcategoria">
                                                                                    <option value="-1">Seleccione</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-1"></div>
                                                    <div class="col-sm-6">
                                                        <div class="panel panel-default">
                                                            <div class="panel-body thread-row">
                                                                <div class="col-md-12" >
                                                                    <div class="row" style="overflow: auto; height: 160px !important;">
                                                                        <table id="afijoTable" class="table table-striped">
                                                                            <thead>
                                                                                <tr class="head-afijo">
                                                                                    <th>
                                                                                        Afijo
                                                                                    </th>                                       
                                                                                    <th>
                                                                                        Tipo
                                                                                    </th>
                                                                                    <th>
                                                                                        Posición
                                                                                    </th>
                                                                                    <th>
                                                                                        Accion
                                                                                    </th>                                                                                    
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>                                                                         

                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="pull-right" style="padding-top:5px;">
                                                                            <button type="button" class="btn btn-default btn-sm" id="btnAgregar" data-toggle="modal" data-target="#modalAfijos">
                                                                                <span class="glyphicon glyphicon-plus"></span>
                                                                                <span class="js-label">Agregar</span>
                                                                            </button>                                                                                                                     
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>                                           
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12 pull-right">
                                            <div class="pull-right">
                                                <button type="button" class="btn btn-default btn-sm" id="btnAnotar">
                                                    <span class="glyphicon glyphicon-check"></span>
                                                    <span class="js-label">Anotar</span>
                                                </button>                                             
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>                    
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/views/chanot/popupGramAfijo.jsp" %>
        <%@ include file="/WEB-INF/views/chanot/popupGramTraduccion.jsp" %>
        <%@ include file="/WEB-INF/views/common/resources.jsp" %>
        <script type="text/javascript" src="<c:url value='/javascript/app/gramatical.js' />"></script>
    </body>
</html>
