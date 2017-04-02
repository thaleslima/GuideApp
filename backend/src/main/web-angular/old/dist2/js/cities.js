var mPosition = {};
var mCenter = new google.maps.LatLng(mLatitude, mLongitude);
var mMap = {};
var mMarkersArray = [];
var mZoom = 3;
var mZoomCity = 16;

var init = function () {
	initBind();
};

var initBind = function(){
    $('#modal-city').on('show.bs.modal', resizeMap);

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
    var latitude = document.getElementById("modal-entity-latitude").value;
    var longitude = document.getElementById("modal-entity-longitude").value;


    var city = new City(name, uf, latitude, longitude);

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
    document.getElementById("modal-entity-latitude").value = "";
    document.getElementById("modal-entity-longitude").value = "";

    mPosition = undefined;
    clearMarkers();
    clearTextErrors();

    $('#modal-city-label').text = "Inserir cidade";

    initPositionMap();
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
    document.getElementById("modal-entity-latitude").value = data.latitude;
    document.getElementById("modal-entity-longitude").value = data.longitude;

    clearTextErrors();

    mPosition = {latitude: data.latitude, longitude: data.longitude};

    initPositionMap();
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

var initPositionMap = function(){
    if(mPosition){
        var position = new google.maps.LatLng(mPosition.latitude, mPosition.longitude);
        mMap.setZoom(mZoomCity);
        mMap.setCenter(position);
        addMarker(mMap, mPosition.latitude, mPosition.longitude)
    } else {
        mMap.setZoom(mZoom);
        mMap.setCenter(mCenter);;
    }
}

var initializeMap = function() {
    var mapProp = {
        center: mCenter,
        zoom: mZoom
    };

    mMap = new google.maps.Map(document.getElementById("map-canvas"), mapProp);

    google.maps.event.addListener(mMap, 'click', function(event) {
        addMarker(mMap, event.latLng.lat(), event.latLng.lng())
        clearTextErrorsMap();
    });
};


var resizeMap = function() {
    if(typeof mMap =="undefined") return;
    setTimeout(resizingMap , 400);
};


var resizingMap = function() {
    if(typeof mMap == "undefined") return;
    google.maps.event.trigger(mMap, "resize");

    initPositionMap();
};


var addMarker = function(map, lat, lng){
    clearMarkers();
    var position = new google.maps.LatLng(lat, lng);
    var marker = new google.maps.Marker({position: position, map: map});
    mMarkersArray.push(marker);

    document.getElementById("modal-entity-latitude").value = lat;
    document.getElementById("modal-entity-longitude").value = lng;
}

var clearMarkers = function() {
    for (var i = 0; i < mMarkersArray.length; i++ ) {
        mMarkersArray[i].setMap(null);
    }

    mMarkersArray.length = 0;
}

var clearTextErrorsMap = function(){
    $t(".text-map").forEach(function(element, index) {
          element.innerText= "";
    });
}


var showMessageRegistration = function(message){
    document.getElementById("alert-registration").style.display = "block";
    document.getElementById("alert-registration-message").innerHTML = message;
}

var hideMessageRegistration = function(){
    document.getElementById("alert-registration").style.display = "none";
}


document.addEventListener("DOMContentLoaded", init, false);
google.maps.event.addDomListener(window, 'load', initializeMap);