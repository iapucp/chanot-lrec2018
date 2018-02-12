<!DOCTYPE html>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html lang="es">
    <head>
        <%@ include file="/WEB-INF/views/common/head.jsp" %>
    </head>
    <body>
        <div class="container chanot-login-panel">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <strong>Iniciar Sesión</strong>
                        </div>
                        <div class="panel-body">
                                <div class="form-group">
                                    <label for="txtUsuario">Usuario</label>
                                    <input type="text" class="form-control" id="txtUsuario" name="user" placeholder="Ingrese su usuario." />
                                </div>
                                <div class="form-group">
                                    <label for="txtContrasenha">Contraseña</label>
                                    <input type="password" class="form-control" id="txtContrasenha" name="pass" placeholder="Ingrese su contraseña." />
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary btn-block" id="btnLogin" >
                                        <span class="glyphicon glyphicon-user"></span>
                                        <span class="js-label">Iniciar Sesión</span>
                                    </button>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/views/common/resources.jsp" %>
        <script type="text/javascript" src="<c:url value='/javascript/app/login.js' />"></script>
    </body>
</html>