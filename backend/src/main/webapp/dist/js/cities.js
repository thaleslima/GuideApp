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
    var btnNewCity = $('#new-city');
    btnNewCity.click(openModalNewCity);
    btnNewCity.removeClass('disabled');

    getCities(getCitiesSuccess, getCitiesError);
};



var saveNewEntity = function(){
    var name = document.getElementById("modal-city-name").value;
    var e = document.getElementById("modal-city-uf");
    var uf = e.options[e.selectedIndex].value;
    var id = document.getElementById("modal-city-id").value;

    var city = new City(name, uf);

    if(id){
        city.id = id;
        updateCity(city, saveNewCitySuccess, saveNewCityError);
    } else {
        insertCity(city, saveNewCitySuccess, saveNewCityError);
    }
}

var saveNewCitySuccess = function(data){
    console.log("success");
    $('#modal-city').modal("hide");

    getCities(getCitiesSuccess, getCitiesError);
};

var saveNewCityError = function(data){
    console.log("error");

    showMessageRegistration(data.error.message);
};






var openModalNewCity = function (){
    document.getElementById("modal-city-name").value = "";
    document.getElementById("modal-city-uf").value = "";
    document.getElementById("modal-city-id").value = "";

    $('#modal-city-label').text = "Inserir cidade";

    clearTextErrors();

	$('#modal-city').modal("show");
};








var deleteCityById = function (id){
    removeCity(id, deleteCitySuccess, deleteCityError);
};

var deleteCitySuccess = function(data){
    console.log("success");
    console.log(data);

    getCities(getCitiesSuccess, getCitiesError);
};

var deleteCityError = function(data){
    console.log("error");
    console.log(data);
};



var openModalEditCity = function (id){
    getCity(id, getCitySuccess, getCityError);
};

var getCitySuccess = function(data){
    $('#modal-city-label').text = "Atualizar cidade";

    document.getElementById("modal-city-name").value = data.name;
    document.getElementById("modal-city-uf").value = data.uf;
    document.getElementById("modal-city-id").value = data.id;

    clearTextErrors();

    $('#modal-city').modal("show");
};

var getCityError = function(data){
    console.log("error");
    console.log(data);
};








var getCitiesSuccess = function(data){
    console.log("ok");
    console.log(data);

    var items = "";
    data.items.forEach(function(element, index) {
        items += "<tr>";
        items += "  <td>" + element.id + "</td>";
        items += "  <td>" + element.uf + "</td>";
        items += "  <td>" + element.name + "</td>";
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

    document.getElementById("body-table-cities").innerHTML = items;

    $t(".open-edit").forEach(function(element, index) {
        element.onclick = function(){openModalEditCity(this.dataset.id)};
    });

    $t(".delete-city").forEach(function(element, index) {
        element.onclick = function(){deleteCityById(this.dataset.id)};
    });
};

var getCitiesError = function(data){
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


document.addEventListener("DOMContentLoaded", init, false);