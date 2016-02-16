function init() {
      var apiName = 'guideAppApi';
      var apiVersion = 'v1';
      var apiRoot = 'https://' + window.location.host + '/_ah/api';
      if (window.location.hostname == 'localhost'
          || window.location.hostname == '127.0.0.1'
          || ((window.location.port != "") && (window.location.port > 1023))) {
            // We're probably running against the DevAppServer
            apiRoot = 'http://' + window.location.host + '/_ah/api';
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
     gapi.client.guideAppApi.getCity({'id': id}).execute(function(resp) {
          responseService(resp, success, error);
     });
}

var insertCity = function(city, success, error){
     gapi.client.guideAppApi.insertCity(city).execute(function(resp) {
          responseService(resp, success, error);
     });
}

var updateCity = function(city, success, error){
     gapi.client.guideAppApi.updateCity(city).execute(function(resp) {
          responseService(resp, success, error);
     });
}

var removeCity = function(id, success, error){
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
     gapi.client.guideAppApi.getCategory({'id': id}).execute(function(resp) {
          responseService(resp, success, error);
     });
}

var insertCategory = function(category, success, error){
     gapi.client.guideAppApi.insertCategory(category).execute(function(resp) {
          responseService(resp, success, error);
     });
}

var updateCategory = function(category, success, error){
     gapi.client.guideAppApi.updateCategory(category).execute(function(resp) {
          responseService(resp, success, error);
     });
}

var removeCategory = function(id, success, error){
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
     gapi.client.guideAppApi.getSubCategory({'id': id}).execute(function(resp) {
          responseService(resp, success, error);
     });
}

var insertSubCategory = function(subCategory, success, error){
     gapi.client.guideAppApi.insertSubCategory(subCategory).execute(function(resp) {
          responseService(resp, success, error);
     });
}

var updateSubCategory = function(subCategory, success, error){
     gapi.client.guideAppApi.updateSubCategory(subCategory).execute(function(resp) {
          responseService(resp, success, error);
     });
}

var removeSubCategory = function(id, success, error){
     gapi.client.guideAppApi.removeSubCategory({'id': id}).execute(function(resp) {
          responseService(resp, success, error);
     });
}






var responseService = function(resp, success, error){
    if (!resp.error) {
        success(resp);
    } else if (resp.error) {
        error(resp);
    }
};

function City(name, uf) {
  this.name = name;
  this.uf = uf;
}

function Category(description) {
  this.description = description;
}

function SubCategory(description, idCategory) {
  this.description = description;
  this.idCategory = idCategory;
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
