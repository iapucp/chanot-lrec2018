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
                                    <strong id="tituloArchivo">Anotación Entidades</strong>
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
                                                    <div class="col-sm-12">
                                                        <div class="panel panel-default">
                                                            <div class="panel-body thread-row">
                                                                <div class="col-md-12" >
                                                                    <div class="row">
                                                                        <div class="form-group row">
                                                                            <label for="txtPalabra" class="col-md-4 col-form-label text-left chanot-margen-label-horizontal">Palabra</label>
                                                                            <div class="col-md-8">
                                                                                <input class="form-control" type="text" id="txtPalabra" disabled="true">
                                                                            </div>
                                                                        </div>                                            
                                                                        <div class="form-group row">
                                                                            <label for="lstCategoria" class="col-md-4 col-form-label text-left chanot-margen-label-horizontal">Entidad</label>
                                                                            <div class="col-md-8">
                                                                                <select class="form-control" id="lstCategoria" data-parsley-min="1" data-parsley-error-message="Seleccione una opción">
                                                                                    <option value="-1">Seleccione</option>
                                                                                    <option value="1">Otro</option>
                                                                                    <option value="2">Locación</option>
                                                                                    <option value="3">Organización</option>
                                                                                    <option value="4">Persona</option>
                                                                                    <option value="5">Número</option>
                                                                                    <option value="6">Fecha</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label for="lstSubCat" class="col-md-4 col-form-label text-left chanot-margen-label-horizontal">Subcategoría</label>
                                                                            <div class="col-md-8">
                                                                                <select class="form-control" id="lstSubCat">
                                                                                    <option value="-1">Seleccione</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label for="lstSubcategoria" class="col-md-4 col-form-label text-left chanot-margen-label-horizontal">Posición</label>
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
        <%@ include file="/WEB-INF/views/common/resources.jsp" %>
        <script type="text/javascript" src="<c:url value='/javascript/app/entidad.js' />"></script>
    </body>
</html>