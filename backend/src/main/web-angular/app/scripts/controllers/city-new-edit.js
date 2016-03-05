
/**
 * @ngdoc function
 * @name guideAppApp.controller:CityneweditCtrl
 * @description
 * # CityneweditCtrl
 * Controller of the guideAppApp
 */

/*jslint plusplus: true, devel: true*/
/*global $, angular, $i, $t, google, City */
angular.module('guideAppApp')
    .controller('CityneweditCtrl', ['$scope', '$location', '$rootScope', '$routeParams', '$dataFactory', function ($scope, $location, $rootScope, $routeParams, $dataFactory) {
        'use strict';
        
        var mPosition = null,
            mCenter = new google.maps.LatLng($rootScope.mLatitude, $rootScope.mLongitude),
            mMap = {},
            mMarkersArray = [],
            mZoom = 3,
            mZoomCity = 16,
            mId = null,
            
            clearMarkers = function () {
                var i = 0;
                
                for (i = 0; i < mMarkersArray.length; i++) {
                    mMarkersArray[i].setMap(null);
                }

                mMarkersArray.length = 0;
            },
            
            
            addMarker = function (map, lat, lng) {
                clearMarkers();
                var position = new google.maps.LatLng(lat, lng),
                    marker = new google.maps.Marker({position: position, map: map});
                
                mMarkersArray.push(marker);

                $i('modal-entity-latitude').value = lat;
                $i('modal-entity-longitude').value = lng;
            },
            
            
            initPositionMap = function () {
                if (mPosition) {
                    var position = new google.maps.LatLng(mPosition.latitude, mPosition.longitude);
                    mMap.setZoom(mZoomCity);
                    mMap.setCenter(position);
                    addMarker(mMap, mPosition.latitude, mPosition.longitude);
                } else {
                    clearMarkers();
                    mMap.setCenter(mCenter);
                    mMap.setZoom(mZoom);
                }
            },
            
            
            hideAlertRegistration = function () {
                $i('alert-registration').style.display = 'none';
            },
            
            
            clearTextErrors = function () {
                $t('.help-block').forEach(function (element) {
                    element.innerText = '';
                });
                hideAlertRegistration();
            },
            
            
            clearTextErrorsMap = function () {
                $t('.text-map').forEach(function (element) {
                    element.innerText = '';
                });
            },
            
            
            cleanEntity = function () {
                $i('modal-city-name').value = '';
                $i('modal-city-uf').value = '';
                $i('modal-city-id').value = '';
                $i('modal-entity-latitude').value = '';
                $i('modal-entity-longitude').value = '';
            },
            
            
            getEntity = function () {
                var name = $i('modal-city-name').value,
                    e = $i('modal-city-uf'),
                    uf = e.options[e.selectedIndex].value,
                    id = $i('modal-city-id').value,
                    latitude = $i('modal-entity-latitude').value,
                    longitude = $i('modal-entity-longitude').value;

                return new City(id, name, uf, latitude, longitude);
            },
            
            
            saveNewEntitySuccess = function (data) {
                console.log('success');
                console.log(data);

                cleanEntity();
                initPositionMap();
            },
            
            
            saveNewEntityError = function (data) {
                console.log('error');
                console.log(data);
            },
            
            
            saveUpdateEntitySuccess = function (data) {
                console.log('success');
                console.log(data);
                
                var latitude = $i('modal-entity-latitude').value,
                    longitude = $i('modal-entity-longitude').value;
                
                mPosition = {latitude: latitude, longitude: longitude};
                initPositionMap();
            },
            
            
            saveUpdateEntityError = function (data) {
                console.log('error');
                console.log(data);
            },
            
            
            saveNewEntity = function () {
                var city = getEntity();

                console.log(city);

                if (city.id) {
                    $dataFactory.updateCity(city, saveUpdateEntitySuccess, saveUpdateEntityError);
                } else {
                    $dataFactory.insertCity(city, saveNewEntitySuccess, saveNewEntityError);
                }
            },
            
            
            initializeMap = function () {
                var mapProp = {
                    center: mCenter,
                    zoom: mZoom
                };

                mMap = new google.maps.Map($i('map-canvas'), mapProp);

                google.maps.event.addListener(mMap, 'click', function (event) {
                    addMarker(mMap, event.latLng.lat(), event.latLng.lng());
                    clearTextErrorsMap();
                });
            },
            
            
            getEntitySuccess = function (data) {
                $i('modal-city-name').value = data.name;
                $i('modal-city-uf').value = data.uf;
                $i('modal-city-id').value = data.id;
                $i('modal-entity-latitude').value = data.latitude;
                $i('modal-entity-longitude').value = data.longitude;
                clearTextErrors();
                mPosition = {latitude: data.latitude, longitude: data.longitude};
                initPositionMap();
            },
            
            
            getEntityError = function (data) {
                console.log('error');
                console.log(data);
            };
        
        
        $scope.init = function () {
            if (mId) {
                $dataFactory.getCity(mId, getEntitySuccess, getEntityError);
            }
        };
        
        $scope.initPage = function () {
            hideAlertRegistration();
            initializeMap();
            
            mId = $routeParams.id;
            
            if (mId) {
                $scope.pageDescription = 'Cidades';
                $scope.page = 'Atualizar';
            } else {
                $scope.pageDescription = 'Cidades';
                $scope.page = 'Nova';
            }
            
            $('.form-registration').find('input,textarea,select').jqBootstrapValidation({
                preventSubmit: true,

                submitSuccess: function () {
                    clearTextErrors();
                    saveNewEntity();
                }
            });
        };
        
        $scope.initPage();
        $rootScope.init($scope);
        
    }]);