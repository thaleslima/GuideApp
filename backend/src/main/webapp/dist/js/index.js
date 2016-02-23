var mPosition = {};
var mCenter = new google.maps.LatLng(mLatitude,mLongitude);
var mMap = {};
var mMarkersArray = [];
var mMarker = new google.maps.Marker({position:mCenter});
var mZoom = 4;
var mZoomCity = 14;

var init = function () {
	initBind();
};

var initBind = function(){
    $('#search-id-city').on('change', comboCityChange);
};


var initPage = function() {
    getCities(getCitiesSuccess, getCitiesError);
};

var getCitiesSuccess = function(data){
    console.log("ok");
    console.log(data);

    var items = "<option value=\"\">Cidade</option>";

    if(data && data.items){
        data.items.forEach(function(element, index) {
            items += "<option value='" + element.id + "'  data-latitude='" + element.latitude;
            items += "' data-longitude='" + element.longitude + "'>" + element.name + "</option>";
        });
    }

    document.getElementById("search-id-city").innerHTML =  items;
};

var getCitiesError = function(data){
    console.log("error");
    console.log(data);
};

var comboCityChange = function(){
    var latitude = $(this).find(':selected').data('latitude');
    var longitude = $(this).find(':selected').data('longitude');

    var position = new google.maps.LatLng(latitude,longitude);
    mMap.setZoom(mZoomCity);
    mMap.setCenter(position);

    var idCity = $(this).val();

    getLocalsByIdCategory(idCity, null, getEntitiesSuccess, getEntitiesError);
}


var getEntitiesSuccess = function(data){
    console.log("ok");
    console.log(data);

    if(data && data.items){
        clearMarkers();
        data.items.forEach(function(element, index) {
            addMarker(mMap, element.latitude, element.longitude)
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
        zoom: mZoom
    };

    mMap = new google.maps.Map(document.getElementById("map-canvas"), mapProp);

    //google.maps.event.addListener(mMap, 'click', function(event) {
    //    addMarker(mMap, event.latLng.lat(), event.latLng.lng())
    //    clearTextErrorsMap();
    //});

    //google.maps.event.addListener(mMarker, 'click', function() {
    //    infowindow.setContent(contentString);
    //    infowindow.open(mMap, mMarker);
    //});
};


var addMarker = function(map, lat, lng){
    var position = new google.maps.LatLng(lat, lng);
    var marker = new google.maps.Marker({position: position, map: map});
    mMarkersArray.push(marker);
}

var clearMarkers = function() {
    for (var i = 0; i < mMarkersArray.length; i++ ) {
        mMarkersArray[i].setMap(null);
    }

    mMarkersArray.length = 0;
}

document.addEventListener("DOMContentLoaded", init, false);
google.maps.event.addDomListener(window, 'load', initializeMap);