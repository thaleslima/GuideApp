var init = function () {
	initBind();
};

var initBind = function(){
     $(".form-registration").find("input,textarea,select").jqBootstrapValidation({
          preventSubmit: true,

          submitSuccess: function($form, event) {
              clearTextErrors();
              saveNewEntity();
              event.preventDefault();
          }
      }
    );
};

var initPage = function() {
    getSubCategories(getEntitiesSuccess, getEntitiesError);
    getCategories(getCategoriesSuccess, getEntitiesError);
};



var saveNewEntity = function(){
    var description = document.getElementById("modal-entity-description").value;
    var id = document.getElementById("modal-entity-id").value;
    var idCategory = document.getElementById("modal-entity-id-category").value;

    var entity = new SubCategory(description,idCategory);

    if(id){
        entity.id = id;
        updateSubCategory(entity, saveNewEntitySuccess, saveNewEntityError);
    } else {
        insertSubCategory(entity, saveNewEntitySuccess, saveNewEntityError);
    }
}

var saveNewEntitySuccess = function(data){
    console.log("success");
    $('#modal-entity').modal("hide");

    getSubCategories(getEntitiesSuccess, getEntitiesError);
};

var saveNewEntityError = function(data){
    console.log("error");

    showMessageRegistration(data.error.message);
};






var openModalNewEntity = function (){
    document.getElementById("modal-entity-description").value = "";
    document.getElementById("modal-entity-id").value = "";
    document.getElementById("modal-entity-id-category").value = "";

    $('#modal-entity-label').text = "Inserir sub-categoria";

    clearTextErrors();
	$('#modal-entity').modal("show");
};





var openModalEditEntity = function (id){
    getSubCategory(id, getEntitySuccess, getEntityError);
};

var getEntitySuccess = function(data){
    $('#modal-entity-label').text = "Atualizar sub-categoria";

    document.getElementById("modal-entity-description").value = data.description;
    document.getElementById("modal-entity-id").value = data.id;
    document.getElementById("modal-entity-id-category").value = data.idCategory;

    clearTextErrors();
    $('#modal-entity').modal("show");
};

var getEntityError = function(data){
    console.log("error");
    console.log(data);
};





var deleteEntityById = function (id){
    removeSubCategory(id, deleteEntitySuccess, deleteEntityError);
};

var deleteEntitySuccess = function(data){
    console.log("success");
    console.log(data);

    getSubCategories(getEntitiesSuccess, getEntitiesError);
};

var deleteEntityError = function(data){
    console.log("error");
    console.log(data);
};






var getCategoriesSuccess = function(data){
    console.log("getCategoriesSuccess:ok");
    console.log(data);

    if(data && data.items){
        var items = "<option value=\"\">Selecione a categoria</option>";
        data.items.forEach(function(element, index) {
            items += "<option value='" + element.id + "'>" + element.description + "</option>";
        });

        document.getElementById("modal-entity-id-category").innerHTML = items;

        var btnNewEntity = $('#new-entity');
        btnNewEntity.click(openModalNewEntity);
        btnNewEntity.removeClass('disabled');
    }
};

var getEntitiesError = function(data){
    console.log("getEntitiesError:ok");
    console.log(data);
};





var getEntitiesSuccess = function(data){
    console.log("ok");
    console.log(data);

    if(data && data.items){
        var items = "";
        data.items.forEach(function(element, index) {
            items += "<tr>";
            items += "  <td>" + element.id + "</td>";
            items += "  <td>" + element.description + "</td>";
            items += "  <td>" + element.idCategory + "</td>";
            items += "  <td>";
            items += "      <button type='button' class='btn btn-xs btn-info open-edit'";
            items += "          data-id='" + element.id + "'>";
            items += "          <span class='glyphicon glyphicon-pencil' aria-hidden='true'></span>";
            items += "      </button>";
            items += "      <button type='button' class='btn btn-xs btn-danger delete-city'";
            items += "          data-id='" + element.id + "'>";
            items += "          <span class='glyphicon glyphicon-trash' aria-hidden='true'></span>";
            items += "      </button>";
            items += "  </td>";
            items += "</tr>";
        });

        document.getElementById("body-table-entity").innerHTML = items;

        $t(".open-edit").forEach(function(element, index) {
            element.onclick = function(){openModalEditEntity(this.dataset.id)};
        });

        $t(".delete-city").forEach(function(element, index) {
            element.onclick = function(){deleteEntityById(this.dataset.id)};
        });
    }
};

var getEntitiesError = function(data){
    console.log("error");
    console.log(data.error.message);
};



var showMessageRegistration = function(message){
    document.getElementById("alert-registration").style.display = "block";
    document.getElementById("alert-registration-message").innerHTML = message;
}

var hideMessageRegistration = function(){
    document.getElementById("alert-registration").style.display = "none";
}


var clearTextErrors = function(){
    $t(".help-block").forEach(function(element, index) {
          element.innerText= "";
    });

    hideMessageRegistration();
}


$(document).ready(init);