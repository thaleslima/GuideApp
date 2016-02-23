var mSubCategories = {};

var mPosition = {};
var mCenter = new google.maps.LatLng(mLatitude, mLongitude);
var mMap = {};
var mMarkersArray = [];
var mZoom = 3;
var mZoomCity = 14;

var init = function () {
	initBind();
};

var initBind = function(){
    $('#modal-entity').on('show.bs.modal', resizeMap);
    $('#modal-entity-id-city').on('change', comboCityChange);

    $('#modal-entity-id-category').on('change', comboCategoryChange);

    $(".form-registration").find("input,textarea").not("[type=submit]").jqBootstrapValidation({
          preventSubmit: true,

          submitSuccess: function($form, event) {
              clearTextErrors();
              saveNewEntity();
              event.preventDefault();
          }
      }
    );

    $("#file").change(sendFileToCloud);
};


var initPage = function() {
    var btnNewEntity = $('#new-entity');
    btnNewEntity.click(openModalNewEntity);
    btnNewEntity.removeClass('disabled');

    var btnSearchEntity = $('#search-entity');
    btnSearchEntity.click(searchEntity);
    btnSearchEntity.removeClass('disabled');

    getCities(getCitiesSuccess, getCitiesError);
    getCategories(getCategoriesSuccess, getCategoriesError);
    getSubCategories(getSubCategoriesSuccess, getSubCategoriesError);
};

var comboCityChange = function(){

    var latitude = $(this).find(':selected').data('latitude');
    var longitude = $(this).find(':selected').data('longitude');

    var position = new google.maps.LatLng(latitude,longitude);
    mMap.setZoom(mZoomCity);
    mMap.setCenter(position);
}

var searchEntity = function(){
    var idCategory = document.getElementById("search-id-category").value;
    var idCity = document.getElementById("search-id-city").value;
    initDataGrid();

    if(idCategory && idCity)
        getLocalsByIdCategory(idCity, idCategory, getEntitiesSuccess, getEntitiesError);
};


var sendFileToCloud = function(){
    hideMessageRegistration();
    var fileInput = document.getElementById('file');

    if(fileInput.files.length > 0){
        showImageLoading();
        uploadFileToCloud(fileInput.files[0], sendFileToCloudSuccess, sendFileToCloudError);
    }
}

var sendFileToCloudSuccess = function(data){
    console.log("success");
    console.log(data);

    showImage(data);
};


var sendFileToCloudError = function(data){
    console.log("error");
    console.log(data);
    hideImageLoading();

    showMessageRegistration("Problema ao enviar imagem. " + data.error.message);
};



var showImage = function(data){
    var imageLocal = $("#image-local");
    imageLocal.attr("src", data);
    imageLocal.removeClass("hide");

    $("#modal-entity-image-path").val(data);
    $("#file").val("");

    $(".image").removeClass("empty");
    $(".image-legend").addClass("hide");
    $(".image-loading").addClass("hide");
}

var hideImage = function(){
    var imageLocal = $("#image-local");
    imageLocal.attr("src", "");
    imageLocal.addClass("hide");

    $("#modal-entity-image-path").val("");
    $("#file").val("");

    $(".image").addClass("empty");
    $(".image-legend").removeClass("hide");
}

var showImageLoading = function(){
    $(".image-loading").removeClass("hide");
    $(".image-legend").addClass("hide");
    $("#image-local").addClass("hide");
}


var hideImageLoading = function(){
    $(".image-loading").addClass("hide");
    $(".image-legend").removeClass("hide");
    $("#image-local").addClass("hide");
}





var getSubCategoriesByIdCategory = function (idCategories){
    var subCategories = [];

    if(mSubCategories && mSubCategories.items && idCategories){
        //idCategories.forEach(function(element1, index1) {
            mSubCategories.items.forEach(function(element, index) {
                if(element.idCategory == idCategories){
                    subCategories.push(element);
                }
            });
        //});
    }

    getSubCategoriesByIdCategorySuccess(subCategories);
};


var getSubCategoriesByIdCategorySuccess = function(data){
    console.log("ok");
    console.log(data);

    var items = "<option value=\"\" class=\"hide\">Selecione</option>";

    if(data){
        data.forEach(function(element, index) {
            items += "<option value='" + element.id + "'>" + element.description + "</option>";
        });
    }

    document.getElementById("modal-entity-id-sub-category").innerHTML = items;
};


var initDataGrid = function(){
    document.getElementById("body-table-entity").innerHTML = "";
};



var getCitiesSuccess = function(data){
    console.log("ok");
    console.log(data);

    var title1 = "<option value=\"\">Selecione</option>";
    var title2 = "<option value=\"\">Cidade</option>";
    var items = "";

    if(data && data.items){
        data.items.forEach(function(element, index) {
            items += "<option value='" + element.id + "'  data-latitude='" + element.latitude;
            items += "' data-longitude='" + element.longitude + "'>" + element.name + "</option>";
        });
    }

    document.getElementById("modal-entity-id-city").innerHTML = title1 + items;
    document.getElementById("search-id-city").innerHTML = title2 + items;
};

var getCitiesError = function(data){
    console.log("error");
    console.log(data);
};






var getSubCategoriesSuccess = function(data){
    console.log("ok");
    console.log(data);

    mSubCategories = data;
};

var getSubCategoriesError = function(data){
    console.log("error");
    console.log(data);
};







var getCategoriesSuccess = function(data){
    console.log("ok");
    console.log(data);

    var title1 = "<option value=\"\" class=\"hide\">Selecione</option>";
    var title2 = "<option value=\"\">Categoria</option>";
    var items = "";

    if(data && data.items){
        data.items.forEach(function(element, index) {
            items += "<option value='" + element.id + "'>" + element.description + "</option>";
        });
    }

    document.getElementById("modal-entity-id-category").innerHTML =  title1 + items;
    document.getElementById("search-id-category").innerHTML = title2 + items;
};

var getCategoriesError = function(data){
    console.log("error");
    console.log(data);
};




var saveNewEntity = function(){
    var id = document.getElementById("modal-entity-id").value;
    var description = document.getElementById("modal-entity-description").value;
    var site = document.getElementById("modal-entity-site").value;
    var phone = document.getElementById("modal-entity-phone").value;
    var address = document.getElementById("modal-entity-address").value;
    var wifi = document.querySelector('input[name="wifi"]:checked').value;

    if(wifi == "no")
       wifi = false;
    else
       wifi = true;

    var detail = document.getElementById("modal-entity-detail").value;
    var latitude = document.getElementById("modal-entity-latitude").value;
    var longitude = document.getElementById("modal-entity-longitude").value;
    var imagePath = document.getElementById("modal-entity-image-path").value;
    var idCategory = document.getElementById("modal-entity-id-category").value;

    var idCity = document.getElementById("modal-entity-id-city").value;
    var idCategories = [idCategory];
    var idSubCategories = $("#modal-entity-id-sub-category").val();

    var entity = new Local(description,
                           site,
                           phone,
                           address,
                           wifi,
                           detail,
                           latitude,
                           longitude,
                           imagePath,
                           idCity,
                           idCategories,
                           idSubCategories);



    console.log(entity);

    if(id){
        entity.id = id;
        updateLocal(entity, saveNewEntitySuccess, saveNewEntityError);
    } else {
        insertLocal(entity, saveNewEntitySuccess, saveNewEntityError);
    }
}

var saveNewEntitySuccess = function(data){
    console.log("success");
    $('#modal-entity').modal("hide");
    initDataGrid();
};

var saveNewEntityError = function(data){
    console.log("error");
    console.log(data);

    showMessageRegistration(data.error.message);
};






var openModalNewEntity = function (){
    document.getElementById("modal-entity-id").value = "";
    document.getElementById("modal-entity-description").value = "";
    document.getElementById("modal-entity-site").value = "";
    document.getElementById("modal-entity-phone").value = "";
    document.getElementById("modal-entity-address").value = "";
    document.getElementById("modal-entity-no").checked = true;
    document.getElementById("modal-entity-detail").value = "";
    document.getElementById("modal-entity-latitude").value = "";
    document.getElementById("modal-entity-longitude").value = "";
    document.getElementById("modal-entity-id-city").value = "";

    document.getElementById("modal-entity-id-category").value = "";

    var subCategory = document.getElementById("modal-entity-id-sub-category");
    subCategory.innerHTML = "";
    subCategory.value = "";

    mPosition = undefined;
    clearMarkers();
    clearTextErrors();

    hideImage();

    $('#modal-entity-label').text = "Inserir local";
	$('#modal-entity').modal("show");
};




var openModalEditEntity = function (id){
    getLocal(id, getEntitySuccess, getEntityError);
};

var getEntitySuccess = function(data){
    $('#modal-entity-label').text = "Atualizar local";
    getSubCategoriesByIdCategory(data.idCategories, getSubCategoriesSuccess);


    document.getElementById("modal-entity-description").value = data.description;
    document.getElementById("modal-entity-id").value = data.id;

    document.getElementById("modal-entity-id").value = data.id;
    document.getElementById("modal-entity-description").value = data.description;
    document.getElementById("modal-entity-site").value = data.site;
    document.getElementById("modal-entity-phone").value = data.phone;
    document.getElementById("modal-entity-address").value = data.address;
    document.getElementById("modal-entity-no").checked = !data.wifi;
    document.getElementById("modal-entity-yes").checked = data.wifi;
    document.getElementById("modal-entity-detail").value = data.detail;
    document.getElementById("modal-entity-latitude").value = data.latitude;
    document.getElementById("modal-entity-longitude").value = data.longitude;
    document.getElementById("modal-entity-id-city").value = data.idCity;

    $("#modal-entity-id-category").val(data.idCategories[0]);
    $("#modal-entity-id-sub-category").val(data.idSubCategories);

    showImage(data.imagePath);

    clearTextErrors();
    mPosition = {latitude: data.latitude, longitude: data.longitude};

    $('#modal-entity').modal("show");
};

var getEntityError = function(data){
    console.log("error");
    console.log(data);
};





var deleteEntityById = function (id){
    removeLocal(id, deleteEntitySuccess, deleteEntityError);
};

var deleteEntitySuccess = function(data){
    console.log("success");
    console.log(data);

    initDataGrid();
};

var deleteEntityError = function(data){
    console.log("error");
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
    console.log(data);
};






var initializeMap = function() {
    var mapProp = {
        center: mCenter,
        zoom: 14
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

var initPositionMap = function(){
    if(mPosition){
        var position = new google.maps.LatLng(mPosition.latitude, mPosition.longitude);
        mMap.setZoom(mZoomCity);
        mMap.setCenter(position);
        addMarker(mMap, mPosition.latitude, mPosition.longitude)
    } else {
        mMap.setZoom(mZoom);
        mMap.setCenter(mCenter);
    }
}






var clearTextErrors = function(){
    $t(".help-block").forEach(function(element, index) {
          element.innerText= "";
    });

    hideMessageRegistration();
}

var showMessageRegistration = function(message){
    document.getElementById("alert-registration").style.display = "block";
    document.getElementById("alert-registration-message").innerHTML = message;
}

var hideMessageRegistration = function(){
    document.getElementById("alert-registration").style.display = "none";
}


var comboCategoryChange = function(){
    var values = $('#modal-entity-id-category').val();
    console.log(values);
    getSubCategoriesByIdCategory(values, getSubCategoriesSuccess);

};

document.addEventListener("DOMContentLoaded", init, false);
google.maps.event.addDomListener(window, 'load', initializeMap);