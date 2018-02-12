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
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <strong>ACCESO DENEGADO</strong>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <p>Usted no tiene los privilegios necesarios para ver esta página. Contacte al administrador si cree que debería tener acceso. Por favor intente volver a la página principal haciendo clic <a href="${contextPath}/pages/inicio">aquí</a>.</p>
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
    </body>
</html>