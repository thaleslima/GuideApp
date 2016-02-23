var auth2;
var dev = false;

var mLatitude = -14.8835514; //Brazil
var mLongitude = -57.8335856; //Brazil

function isLocalHost() {
    if (window.location.hostname == 'localhost'
          || window.location.hostname == '127.0.0.1'
          || ((window.location.port != "") && (window.location.port > 1023))) {

          return true;
    }

    return false;
}

function initAuth() {
    gapi.load('auth2', function() {
        auth2 = gapi.auth2.init({
          client_id: '904382967622-sl60lsln4f5fmnqac53n2medldl6nt40.apps.googleusercontent.com',
          fetch_basic_profile: true,
          scope: 'profile https://www.googleapis.com/auth/devstorage.full_control',
        });

        auth2.then(function () {
          if (!auth2.isSignedIn.get()) {
            console.log("not logged!");
            window.location.href = "/pages/login.html";
          } else {
            console.log("logged!");
            var profile = auth2.currentUser.get().getBasicProfile();

            document.getElementById("user").innerHTML = profile.getName();
            document.getElementById("image-user").src = profile.getImageUrl();
          }
        });
    });
}

function initService() {
      var apiName = 'guideAppApi';
      var apiVersion = 'v1';
      var apiRoot = 'https://' + window.location.host + '/_ah/api';
      if (isLocalHost()) {
            if(dev)
                apiRoot = 'http://' + window.location.host + '/_ah/api';
            else
                apiRoot = 'https://guideapp-br.appspot.com/_ah/api';
      }

      gapi.client.load(apiName, apiVersion, initPage, apiRoot);
};

var initPage = function() {};

$(function() {
    $('#side-menu').metisMenu();
});

var getCities = function(success, error){
    gapi.client.guideAppApi.getCities().execute(function(resp) {
      responseService(resp, success, error);
    });
}

var getCity = function(id, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.getCity({'id': id}).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var insertCity = function(city, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.insertCity(city).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var updateCity = function(city, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.updateCity(city).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var removeCity = function(id, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.removeCity({'id': id}).execute(function(resp) {
      responseService(resp, success, error);
    });
}




var getCategories = function(success, error){
    gapi.client.guideAppApi.getCategories().execute(function(resp) {
      responseService(resp, success, error);
    });
}

var getCategory = function(id, success, error){

    isAuthenticatedEndReturnLogin();

    gapi.client.guideAppApi.getCategory({'id': id}).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var insertCategory = function(category, success, error){
    isAuthenticatedEndReturnLogin();

    gapi.client.guideAppApi.insertCategory(category).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var updateCategory = function(category, success, error){
    isAuthenticatedEndReturnLogin();

    gapi.client.guideAppApi.updateCategory(category).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var removeCategory = function(id, success, error){
    isAuthenticatedEndReturnLogin();

    gapi.client.guideAppApi.removeCategory({'id': id}).execute(function(resp) {
      responseService(resp, success, error);
    });
}




var getSubCategories = function(success, error){
    gapi.client.guideAppApi.getSubCategories().execute(function(resp) {
      responseService(resp, success, error);
    });
}

var getSubCategory = function(id, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.getSubCategory({'id': id}).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var insertSubCategory = function(subCategory, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.insertSubCategory(subCategory).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var updateSubCategory = function(subCategory, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.updateSubCategory(subCategory).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var removeSubCategory = function(id, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.removeSubCategory({'id': id}).execute(function(resp) {
      responseService(resp, success, error);
    });
}




var getLocals = function(success, error){
    gapi.client.guideAppApi.getLocals().execute(function(resp) {
      responseService(resp, success, error);
    });
}

var getLocalsByIdCategory = function(idCity, idCategory, success, error){
    gapi.client.guideAppApi.getLocals({'idCity': idCity, 'idCategory': idCategory}).execute(function(resp) {
      responseService(resp, success, error);
    });
}


var getLocal = function(id, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.getLocal({'id': id}).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var insertLocal = function(local, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.insertLocal(local).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var updateLocal = function(local, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.updateLocal(local).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var removeLocal = function(id, success, error){
    isAuthenticatedEndReturnLogin();
    gapi.client.guideAppApi.removeLocal({'id': id}).execute(function(resp) {
      responseService(resp, success, error);
    });
}

var link_storage = "https://storage.googleapis.com/guide-app/";

var uploadFileToCloud = function(file, success, error){
    var size = file.size;
    var name = file.name;
    var url;

    if (dev)
        url = "https://www.googleapis.com/upload/storage/v1/b/guide-app-dev/o?uploadType=media&name=" + name;
    else
        url = "https://www.googleapis.com/upload/storage/v1/b/guide-app/o?uploadType=media&name=" + name;

    var auth = gapi.auth2.getAuthInstance();
    var token = auth.currentUser.get().getAuthResponse().access_token;

    var xhr = new XMLHttpRequest();
    xhr.open('POST', url);
    xhr.setRequestHeader("Content-Type", "image/jpeg");
    xhr.setRequestHeader("x-goog-project-id", "904382967622");
    xhr.setRequestHeader("Authorization", "Bearer " + token);

    xhr.send(file);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {

            var response = JSON.parse(xhr.responseText);

            if(xhr.status == 200){
                success(link_storage + response.name)
            } else {
                error(response)
            }
        }
    }
}




var responseService = function(resp, success, error){
    if (!resp.error) {
        success(resp);
    } else if (resp.error) {
        error(resp);
    }
};

function City(name, uf, latitude, longitude) {
  this.name = name;
  this.uf = uf;
  this.latitude = latitude;
  this.longitude = longitude;
}

function Category(description) {
  this.description = description;
}

function SubCategory(description, idCategory) {
  this.description = description;
  this.idCategory = idCategory;
}

function Local(description, site, phone, address, wifi, detail, latitude, longitude, imagePath, idCity,
                idCategories, idSubCategories) {
  this.description = description;
  this.site = site;
  this.phone = phone;
  this.address = address;
  this.wifi = wifi;
  this.detail = detail;
  this.latitude = latitude;
  this.longitude = longitude;
  this.imagePath = imagePath;
  this.idCity = idCity;
  this.idCategories = idCategories;
  this.idSubCategories = idSubCategories;
}



function $t(selector) {
     return [].slice.call(document.querySelectorAll(selector));
}

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    var element = $('ul.nav a').filter(function() {
        return this.href == url || url.href.indexOf(this.href) == 0;
    }).addClass('active').parent().parent().addClass('in').parent();
    if (element.is('li')) {
        element.addClass('active');
    }
});


function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail());

  var id_token = googleUser.getAuthResponse().id_token;
  console.log("ID Token: " + id_token);

  window.location.href = "/pages/index.html";
}


function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
}

function onSuccess(googleUser) {
      console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
}

function onFailure(error) {
  console.log(error);
}


function isAuthenticated() {
    return auth2.isSignedIn.get();
}

function isAuthenticatedEndReturnLogin() {
    if(auth2 && !isAuthenticated()){
        window.location.href = "/pages/login.html";
    }
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
        window.location.href = "/pages/login.html";
    });
}

function disassociate() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect().then(function () {
        console.log('User disconnected from association with app.');
        window.location.href = "/pages/login.html";
    });
}

