'use strict';

/**
 * @ngdoc overview
 * @name guideAppApp
 * @description
 * # guideAppApp
 *
 * Main module of the application.
 */

/*jshint unused: false*/
/*jslint devel: true */
/*global angular, gapi */
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
    })
    
    .run(function ($rootScope, $window) {
        $rootScope.isBackendReady = false;
        $rootScope.mLatitude = -14.8835514;
        $rootScope.mLongitude = -57.8335856;

        $rootScope.init = function (s) {
            if ($rootScope.isBackendReady === false) {
                s.$on('isBackendReady', function () {
                    s.init();
                });
            } else {
                s.init();
            }
        };

        var isLocalHost = function () {
            if (window.location.hostname === 'localhost' ||
                    window.location.hostname === '127.0.0.1' ||
                    ((window.location.port !== '') && (window.location.port > 1023))) {

                return true;
            }

            return false;
        };
    
        $rootScope.loadEndpoints = function () {
            var apiName = 'guideAppApi', apiVersion = 'v1',
                apiRoot = 'https://' + window.location.host + '/_ah/api';
              
            if (isLocalHost()) {
                    //apiRoot = 'http://' + window.location.host + '/_ah/api';
                apiRoot = 'https://guideapp-br.appspot.com/_ah/api';
            }
            
            gapi.client.load(apiName, apiVersion, function () {
                $rootScope.isBackendReady = true;
                $rootScope.api = gapi.client.guideAppApi;
                $rootScope.$apply();
                $rootScope.$broadcast('isBackendReady');
            }, apiRoot);
        };
    
        $window.init = function () {
            $rootScope.$apply($rootScope.loadEndpoints);
        };
    })
    
    .factory('$dataFactory', function ($rootScope) {
        var $dataFactory = {},
            responseService = function (resp, success, error) {
                if (!resp.error) {
                    success(resp);
                } else if (resp.error) {
                    error(resp);
                }
            },
            
            isAuthenticatedEndReturnLogin = function () {
                var auth2 = gapi.auth2.getAuthInstance();
            
                if (auth2 && !auth2.isSignedIn.get()) {
                    window.location.href = '#/login';
                }
            };
    
        $dataFactory.getCities = function (success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getCities().execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.getCity = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getCity({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.insertCity = function (city, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.insertCity(city).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.updateCity = function (city, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.updateCity(city).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.removeCity = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.removeCity({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.getCategories = function (success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getCategories().execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.getCategory = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getCategory({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.insertCategory = function (category, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.insertCategory(category).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.updateCategory = function (category, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.updateCategory(category).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.removeCategory = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.removeCategory({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };


        $dataFactory.getSubCategories = function (success, error) {
            $rootScope.api.getSubCategories().execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.getSubCategory = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getSubCategory({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.insertSubCategory = function (subCategory, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.insertSubCategory(subCategory).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.updateSubCategory = function (subCategory, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.updateSubCategory(subCategory).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.removeSubCategory = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.removeSubCategory({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };



        $dataFactory.getLocals = function (success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getLocals().execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.getLocalsByIdCategory = function (idCity, idCategory, success, error) {
            $rootScope.api.getLocals({'idCity': idCity, 'idCategory': idCategory}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };


        $dataFactory.getLocal = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.getLocal({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.insertLocal = function (local, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.insertLocal(local).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.updateLocal = function (local, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.updateLocal(local).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        $dataFactory.removeLocal = function (id, success, error) {
            isAuthenticatedEndReturnLogin();
            $rootScope.api.removeLocal({'id': id}).execute(function (resp) {
                responseService(resp, success, error);
            });
        };

        return $dataFactory;
    });

function hasClass(ele, cls) {
    return ele.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}

function addClass(ele, cls) {
    if (!hasClass(ele, cls)) {
        ele.className += ' ' + cls;
    }
}

function removeClass(ele, cls) {
    if (hasClass(ele, cls)) {
        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
        ele.className = ele.className.replace(reg, ' ');
    }
}

function getSelectedOptions(sel) {
    var opts = [],
        opt,
        len = sel.options.length,
        i;
    
    for (i = 0; i < len; i++) {
        opt = sel.options[i];

        if (opt.selected) {
            opts.push(opt.value);
        }
    }
    
    return opts;
}

function setSelectedOptions(sel, values) {
    
    if (!sel || !values) {
        return;
    }
    
    var opts = [],
        opt,
        len = sel.options.length,
        lenValues = values.length,
        i,
        y;
    
    for (y = 0; y < len; y++) {
        opt = sel.options[y];
        opt.selected = false;
    }
    
    for (i = 0; i < lenValues; i++) {
        for (y = 0; y < len; y++) {
            opt = sel.options[y];
            
            if (opt.value === values[i]) {
                opt.selected = true;
                break;
            }
        }
    }
}


function initAuth() {
    gapi.load('auth2', function () {
        var auth2 = gapi.auth2.getAuthInstance({
            client_id: '904382967622-sl60lsln4f5fmnqac53n2medldl6nt40.apps.googleusercontent.com',
            fetch_basic_profile: true,
            scope: 'profile https://www.googleapis.com/auth/devstorage.full_control'
        });

        auth2.then(function () {
            if (!auth2.isSignedIn.get()) {
                console.log('not logged!');
                window.location.href = '#/login';
            } else {
                console.log('logged!');
                var profile = auth2.currentUser.get().getBasicProfile(),
                    image = document.getElementById('image-user');
                
                document.getElementById('user').innerHTML = profile.getName();
                 
                image.src = profile.getImageUrl();
                removeClass(image, 'hide');
            }
        });
    });
}

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile(),
        idToken = googleUser.getAuthResponse().id_token,
        url = window.location.href.toLowerCase();

    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    console.log('ID Token: ' + idToken);
    
    if (url.indexOf('login') !== -1) {
        window.location.href = '/';
    }
}

function disassociate() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect().then(function () {
        console.log('User disconnected from association with app.');
    });
}


var uploadFileToCloud = function (file, success, error) {
    var name = file.name,
        url = 'https://www.googleapis.com/upload/storage/v1/b/guide-app/o?uploadType=media&name=' + name,
        linkStorage = 'https://storage.googleapis.com/guide-app/',
        auth = gapi.auth2.getAuthInstance(),
        token = auth.currentUser.get().getAuthResponse().access_token,
        xhr = new XMLHttpRequest();
    
    xhr.open('POST', url);
    xhr.setRequestHeader('Content-Type', 'image/jpeg');
    xhr.setRequestHeader('x-goog-project-id', '904382967622');
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);

    xhr.send(file);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {

            var response = JSON.parse(xhr.responseText);

            if (xhr.status === 200) {
                success(linkStorage + response.name);
            } else {
                error(response);
            }
        }
    };
};

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
    });
}