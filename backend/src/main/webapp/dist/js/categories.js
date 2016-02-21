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
    var btnNewEntity = $('#new-entity');
    btnNewEntity.click(openModalNewEntity);
    btnNewEntity.removeClass('disabled');

    getCategories(getEntitiesSuccess, getEntitiesError);
};



var saveNewEntity = function(){
    var description = document.getElementById("modal-entity-description").value;
    var id = document.getElementById("modal-entity-id").value;

    var entity = new Category(description);

    if(id){
        entity.id = id;
        updateCategory(entity, saveNewEntitySuccess, saveNewEntityError);
    } else {
        insertCategory(entity, saveNewEntitySuccess, saveNewEntityError);
    }
}

var saveNewEntitySuccess = function(data){
    console.log("success");
    $('#modal-entity').modal("hide");

    getCategories(getEntitiesSuccess, getEntitiesError);
};

var saveNewEntityError = function(data){
    console.log("error");

    showMessageRegistration(data.error.message);
};






var openModalNewEntity = function (){
    document.getElementById("modal-entity-description").value = "";
    document.getElementById("modal-entity-id").value = "";

    $('#modal-entity-label').text = "Inserir categoria";

    clearTextErrors();
	$('#modal-entity').modal("show");
};





var openModalEditEntity = function (id){
    getCategory(id, getEntitySuccess, getEntityError);
};

var getEntitySuccess = function(data){
    $('#modal-entity-label').text = "Atualizar categoria";

    document.getElementById("modal-entity-description").value = data.description;
    document.getElementById("modal-entity-id").value = data.id;

    clearTextErrors();
    $('#modal-entity').modal("show");
};

var getEntityError = function(data){
    console.log("error");
    console.log(data);
};





var deleteEntityById = function (id){
    removeCategory(id, deleteEntitySuccess, deleteEntityError);
};

var deleteEntitySuccess = function(data){
    console.log("success");
    console.log(data);

    getCategories(getEntitiesSuccess, getEntitiesError);
};

var deleteEntityError = function(data){
    console.log("error");
    console.log(data);
};






var getEntitiesSuccess = function(data){
    console.log("ok");
    console.log(data);

    var items = "";
    data.items.forEach(function(element, index) {
        items += "<tr>";
        items += "  <td>" + element.id + "</td>";
        items += "  <td>" + element.description + "</td>";
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
};

var getEntitiesError = function(data){
    console.log("error");
    console.log(data);
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