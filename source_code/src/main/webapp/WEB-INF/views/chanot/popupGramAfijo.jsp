<div id="modalAfijos" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="panel panel-primary">
                    <div class="panel-heading text-left modal-title">
                        <strong>Agregar afijos</strong>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="row">
                            <div class="form-group row">
                                <label for="txtAfijo" class="col-md-3 col-form-label text-right">Afijo</label>
                                <div class="col-md-7">
                                    <input class="form-control" type="text" id="txtAfijo" required >
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="txtPosicion" class="col-md-3 col-form-label text-right">Posición</label>
                                <div class="col-md-7">
                                    <input class="form-control" type="number" id="txtPosicion" required data-parsley-type="digits">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="lstTipo" class="col-md-3 col-form-label text-right">Tipo</label>
                                <div class="col-md-7">
                                    <select class="form-control" id="lstTipo" data-parsley-min="1" data-parsley-error-message="Seleccione una opción">
                                        <option value="-1">Seleccione</option>
                                        <option value="1">Clít. de segunda posición</option>
                                        <option value="2">Clít. de posición libre</option>
                                        <option value="3">Clít. concord. de particip.</option>
                                        <option value="4">Clít. nominal</option>
                                        <option value="5">Prefijo</option>
                                        <option value="6">Sufijo nominal</option>
                                        <option value="7">Sufijo verbal</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> 
            </div>

            <div class="modal-footer">
                <div class="row">
                    <div class="col-md-12 pull-right">
                        <div class="pull-right">
                            <button type="button" class="btn btn-primary btn-sm" id="btnPopupAgregar">
                                <span class="glyphicon glyphicon-plus"></span>
                                <span class="js-label">Agregar</span>
                            </button>
                            <button type="button" class="btn btn-danger btn-sm" id="btnPopupCancelar">
                                <span class="glyphicon glyphicon-remove"></span>
                                <span class="js-label">Cancelar</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
