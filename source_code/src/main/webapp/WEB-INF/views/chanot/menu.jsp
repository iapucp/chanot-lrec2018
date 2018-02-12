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
                                    <strong>Archivos</strong>
                                    <label class="btn btn-primary btn-sm pull-right">
                                        <span class="glyphicon glyphicon-plus"></span>                                        
                                        <input id='input-archivo' data-url="<c:url value='/pages/subirArchivo'/>" type="file" accept=".txt" name="file" style="display: none;">
                                    </label>
                                    <div class="clearfix"></div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12" id="files">
                            <input class="search" placeholder="Buscar" style="width: 800px;"/>
                            <button class="sort" data-sort="status">
                                Ordenar por estado
                            </button>
                            <table id="filesTable" class="table">
                                <caption></caption>
                                <thead>
                                    <tr>
                                        <th>
                                            <button type="button" class="btn btn-link" data-order-by="archivo">
                                                Archivo
                                                <span class="glyphicon"></span>
                                            </button>
                                        </th>                                       
                                        <th>
                                            <button type="button" class="btn btn-link" data-order-by="estado">
                                                Estado
                                                <span class="glyphicon"></span>
                                            </button>
                                        </th>
                                        <th>
                                            <button type="button" class="btn btn-link chanot-acciones">
                                                Seleccionar
                                            </button>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody  class="list">
                                </tbody>
                            </table>
                            <ul class="pagination"></ul>
                        </div>
                    </div>                  
                </div>
            </div>
            <%@ include file="/WEB-INF/views/chanot/popupEstadisticas.jsp" %>
            <%@ include file="/WEB-INF/views/chanot/popupBrat.jsp" %>
            <%@ include file="/WEB-INF/views/chanot/popupBratAlerta.jsp" %>
            <%@ include file="/WEB-INF/views/common/resources.jsp" %>
            <script type="text/javascript" src="<c:url value='/javascript/third-party/list.min.js' />"></script>
            <script type="text/javascript" src="<c:url value='/javascript/app/menu.js' />"></script>
    </body>
</html>