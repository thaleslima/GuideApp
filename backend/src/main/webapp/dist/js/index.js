var mPosition = {};
var mCenter = new google.maps.LatLng(-20.3452914,-46.8528537);
var mMap = {};
var mMarkersArray = [];
var mMarker = new google.maps.Marker({position:mCenter});

var init = function () {
	initBind();
};

var initBind = function(){
};


var initPage = function() {

};

var initializeMap = function() {
    var mapProp = {
        center: mCenter,
        zoom: 14
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

var resizeMap = function() {
    if(typeof mMap =="undefined") return;
    setTimeout(resizingMap , 400);
};

var resizingMap = function() {   
    if(typeof mMap == "undefined") return;
    google.maps.event.trigger(mMap, "resize");

    mMap.setZoom(14);

    if(mPosition){
        var position = new google.maps.LatLng(mPosition.latitude, mPosition.longitude);
        mMap.setCenter(position);
        addMarker(mMap, mPosition.latitude, mPosition.longitude)

    } else {
        mMap.setCenter(mCenter);
    }
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

document.addEventListener("DOMContentLoaded", init, false);
google.maps.event.addDomListener(window, 'load', initializeMap);