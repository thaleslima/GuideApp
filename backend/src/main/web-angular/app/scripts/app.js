'use strict';

/**
 * @ngdoc overview
 * @name guideAppApp
 * @description
 * # guideAppApp
 *
 * Main module of the application.
 */
angular
     .module('guideAppApp', [
        'ngAnimate',
        'ngAria',
        'ngCookies',
        'ngMessages',
        'ngResource',
        'ngRoute',
        'ngSanitize',
        'ngTouch',
        'uiGmapgoogle-maps'
    ])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .when('/about', {
                templateUrl: 'views/about.html',
                controller: 'AboutCtrl',
                controllerAs: 'about'
            })
            .when('/cities', {
              templateUrl: 'views/cities.html',
              controller: 'CitiesCtrl',
              controllerAs: 'cities'
            })
            .when('/categories', {
              templateUrl: 'views/categories.html',
              controller: 'CategoriesCtrl',
              controllerAs: 'categories'
            })
            .when('/cities/new', {
              templateUrl: 'views/city-new-edit.html',
              controller: 'CityneweditCtrl',
              controllerAs: 'cityNewEdit'
            })
            .when('/cities/edit/:id', {
              templateUrl: 'views/city-new-edit.html',
              controller: 'CityneweditCtrl',
              controllerAs: 'cityNewEdit'
            })
            .when('/categories/new', {
              templateUrl: 'views/category-new-edit.html',
              controller: 'CategoryNewEditCtrl',
              controllerAs: 'categoryNewEdit'
            })
            .when('/categories/edit/:id', {
              templateUrl: 'views/category-new-edit.html',
              controller: 'CategoryNewEditCtrl',
              controllerAs: 'categoryNewEdit'
            })
            .when('/sub-categories', {
              templateUrl: 'views/sub-categories.html',
              controller: 'SubCategoriesCtrl',
              controllerAs: 'subCategories'
            })
            .when('/sub-categories/new', {
              templateUrl: 'views/sub-category-new-edit.html',
              controller: 'SubCategoryNewEditCtrl',
              controllerAs: 'subCategoryNewEdit'
            })
            .when('/sub-categories/edit/:id', {
              templateUrl: 'views/sub-category-new-edit.html',
              controller: 'SubCategoryNewEditCtrl',
              controllerAs: 'subCategoryNewEdit'
            })
            .when('/locals', {
              templateUrl: 'views/locals.html',
              controller: 'LocalsCtrl',
              controllerAs: 'locals'
            })
            .when('/locals/new', {
              templateUrl: 'views/local-new-edit.html',
              controller: 'LocalNewEditCtrl',
              controllerAs: 'localNewEdit'
            })
            .when('/locals/edit/:id', {
              templateUrl: 'views/local-new-edit.html',
              controller: 'LocalNewEditCtrl',
              controllerAs: 'localNewEdit'
            })
            .when('/login', {
              templateUrl: 'views/login.html',
              controller: 'LoginCtrl',
              controllerAs: 'login'
            })
            .otherwise({
                redirectTo: '/'
            });
    }).run(function ($rootScope, $window) {
        $rootScope.is_backend_ready = false;
        $rootScope.mLatitude = -14.8835514;
        $rootScope.mLongitude = -57.8335856;

        $rootScope.init = function (s) {
            if ($rootScope.is_backend_ready === false) {
                s.$on('is_backend_ready', function () {
                    s.init();
                });
            } else {
                s.init();
            }
        };

        $window.init = function () {
            $rootScope.$apply($rootScope.load_endpoints);
        };

        $rootScope.load_endpoints = function () {
            var apiName = 'guideAppApi', apiVersion = 'v1', API_ROOT = 'https://guideapp-br.appspot.com/_ah/api';

            gapi.client.load(apiName, apiVersion, function () {
                $rootScope.is_backend_ready = true;
                $rootScope.api = gapi.client.guideAppApi;
                $rootScope.$apply();
                $rootScope.$broadcast('is_backend_ready');
            }, API_ROOT);
        };
    
        var
            responseService = function (resp, success, error) {
                if (!resp.error) {
                    success(resp);
                } else if (resp.error) {
                    error(resp);
                }
            },
            
            isAuthenticatedEndReturnLogin = function () {
                
            };
    
    
    
        $rootScope.getCities = function (success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getCities().execute(function (resp) {
                responseService(resp, success, error);
            });
        };
    
        $rootScope.getCity = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getCity({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };
    
        $rootScope.insertCity = function (city, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.insertCity(city).execute(function (resp) {
                responseService(resp, success, error);
            });
        };
    
        $rootScope.updateCity = function (city, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.updateCity(city).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.removeCity = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.removeCity({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };
    
    
    
    
    
    
    
        $rootScope.getCategories = function (success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getCategories().execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.getCategory = function (id, success, error) {
            isAuthenticatedEndReturnLogin();

            $rootScope.api.getCategory({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.insertCategory = function (category, success, error) {
            isAuthenticatedEndReturnLogin();

            $rootScope.api.insertCategory(category).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.updateCategory = function (category, success, error) {
            isAuthenticatedEndReturnLogin();

            $rootScope.api.updateCategory(category).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.removeCategory = function (id, success, error) {
            isAuthenticatedEndReturnLogin();

            $rootScope.api.removeCategory({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };
    
    
        $rootScope.getSubCategories = function (success, error) {
            $rootScope.api.getSubCategories().execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.getSubCategory = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getSubCategory({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.insertSubCategory = function (subCategory, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.insertSubCategory(subCategory).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.updateSubCategory = function (subCategory, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.updateSubCategory(subCategory).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.removeSubCategory = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.removeSubCategory({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

    
    
        $rootScope.getLocals = function (success, error) {
            $rootScope.api.getLocals().execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.getLocalsByIdCategory = function (idCity, idCategory, success, error) {
            $rootScope.api.getLocals({'idCity': idCity, 'idCategory': idCategory}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };


        $rootScope.getLocal = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getLocal({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.insertLocal = function (local, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.insertLocal(local).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.updateLocal = function (local, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.updateLocal(local).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $rootScope.removeLocal = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.removeLocal({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };
    });

function hasClass(ele,cls) {
    return ele.className.match(new RegExp('(\\s|^)'+cls+'(\\s|$)'));
}

function addClass(ele,cls) {
    if (!this.hasClass(ele,cls)) ele.className += " "+cls;
}

function removeClass(ele,cls) {
    if (hasClass(ele,cls)) {
        var reg = new RegExp('(\\s|^)'+cls+'(\\s|$)');
        ele.className=ele.className.replace(reg,' ');
    }
}

function initAuth() {
    gapi.load('auth2', function() {
        var auth2 = gapi.auth2.getAuthInstance({
            client_id: '904382967622-sl60lsln4f5fmnqac53n2medldl6nt40.apps.googleusercontent.com',
            fetch_basic_profile: true,
            scope: 'profile https://www.googleapis.com/auth/devstorage.full_control'
        });

        auth2.then(function () {
            if (!auth2.isSignedIn.get()) {
                console.log("not logged!");
                window.location.href = "#/login";
            } else {
                console.log("logged!");
                var profile = auth2.currentUser.get().getBasicProfile();
                
                 document.getElementById("user").innerHTML = profile.getName();
                 var image = document.getElementById("image-user");
                 image.src = profile.getImageUrl();
                 removeClass(image, "hide");
            }
        });
    });
}

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile(),
        id_token = googleUser.getAuthResponse().id_token;

    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    console.log("ID Token: " + id_token);
    
    var url = window.location.href.toLowerCase();
    if (url.indexOf("login") != -1) {
        window.location.href = "/";
    }
}

function disassociate() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect().then(function () {
        console.log('User disconnected from association with app.');
    });
}


var uploadFileToCloud = function(file, success, error){
    var size = file.size;
    var name = file.name;
    var url;

    url = "https://www.googleapis.com/upload/storage/v1/b/guide-app/o?uploadType=media&name=" + name;
    
    var link_storage = "https://storage.googleapis.com/guide-app/";
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

function serviceInit() {
    window.init();
}

function $i(field) {
    return document.getElementById(field);
}

function $t(selector) {
    return [].slice.call(document.querySelectorAll(selector));
}

function City(id, name, uf, latitude, longitude) {
    this.id = id;
    this.name = name;
    this.uf = uf;
    this.latitude = latitude;
    this.longitude = longitude;
}


function Category(id, description) {
    this.id = id;
    this.description = description;
}

function SubCategory(id, description, idCategory) {
    this.id = id;
    this.description = description;
    this.idCategory = idCategory;
}

function Local(id, description, site, phone, address, wifi, detail, latitude, longitude, imagePath, idCity,
                idCategories, idSubCategories) {
    this.id = id;
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

function disassociate() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect().then(function () {
        console.log('User disconnected from association with app.');
        //window.location.href = "/pages/login.html";
    });
}

/*
var isAuthenticatedEndReturnLogin = function () {

};


var responseService = function (resp, success, error) {
    if (!resp.error) {
        success(resp);
    } else if (resp.error) {
        error(resp);
    }
};

var getCities = function (success, error) {
    mGapi.getCities().execute(function (resp) {
        responseService(resp, success, error);
    });
};

var getCity = function (id, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.getCity({'id': id}).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var insertCity = function (city, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.insertCity(city).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var updateCity = function (city, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.updateCity(city).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var removeCity = function (id, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.removeCity({'id': id}).execute(function (resp) {
        responseService(resp, success, error);
    });
};




var getCategories = function (success, error) {
    mGapi.getCategories().execute(function (resp) {
        responseService(resp, success, error);
    });
};

var getCategory = function (id, success, error) {

    isAuthenticatedEndReturnLogin();

    mGapi.getCategory({'id': id}).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var insertCategory = function (category, success, error) {
    isAuthenticatedEndReturnLogin();

    mGapi.insertCategory(category).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var updateCategory = function (category, success, error) {
    isAuthenticatedEndReturnLogin();

    mGapi.updateCategory(category).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var removeCategory = function (id, success, error) {
    isAuthenticatedEndReturnLogin();

    mGapi.removeCategory({'id': id}).execute(function (resp) {
        responseService(resp, success, error);
    });
};




var getSubCategories = function (success, error) {
    mGapi.getSubCategories().execute(function (resp) {
        responseService(resp, success, error);
    });
};

var getSubCategory = function (id, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.getSubCategory({'id': id}).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var insertSubCategory = function (subCategory, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.insertSubCategory(subCategory).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var updateSubCategory = function (subCategory, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.updateSubCategory(subCategory).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var removeSubCategory = function (id, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.removeSubCategory({'id': id}).execute(function (resp) {
        responseService(resp, success, error);
    });
};




var getLocals = function (success, error) {
    mGapi.getLocals().execute(function (resp) {
        responseService(resp, success, error);
    });
};

var getLocalsByIdCategory = function (idCity, idCategory, success, error) {
    mGapi.getLocals({'idCity': idCity, 'idCategory': idCategory}).execute(function (resp) {
        responseService(resp, success, error);
    });
};


var getLocal = function (id, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.getLocal({'id': id}).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var insertLocal = function (local, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.insertLocal(local).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var updateLocal = function (local, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.updateLocal(local).execute(function (resp) {
        responseService(resp, success, error);
    });
};

var removeLocal = function (id, success, error) {
    isAuthenticatedEndReturnLogin();
    mGapi.removeLocal({'id': id}).execute(function (resp) {
        responseService(resp, success, error);
    });
};

*/
