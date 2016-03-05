
/**
 * @ngdoc function
 * @name guideAppApp.controller:LocalNewEditCtrl
 * @description
 * # LocalNewEditCtrl
 * Controller of the guideAppApp
 */

/*jslint plusplus: true, devel: true */
/*global angular, $, $i, $t, google, uploadFileToCloud, Local, addClass, removeClass, getSelectedOptions, setSelectedOptions*/
angular.module('guideAppApp')
    .controller('LocalNewEditCtrl', ['$scope', '$location', '$rootScope', '$routeParams', '$dataFactory', function ($scope, $location, $rootScope, $routeParams, $dataFactory) {
        
        'use strict';

        var mPosition = null,
            mCenter = new google.maps.LatLng($rootScope.mLatitude, $rootScope.mLongitude),
            mMap = {},
            mMarkersArray = [],
            mZoom = 3,
            mZoomCity = 16,
            mId = null,
            mIdCategory = '',
            mIdCity = '',
            mIdSubCategory = '',
            mSubCategories,
            
            hideMessageRegistration = function () {
                $i('alert-registration').style.display = 'none';
            },
            
            showMessageRegistration = function (message) {
                $i('alert-registration').style.display = 'block';
                $i('alert-registration-message').innerHTML = message;
            },
            
            showImage = function (data) {
                var imageLocal = $i('image-local');
                imageLocal.src = data;
                
                removeClass(imageLocal, 'hide');
                removeClass($i('image-group'), 'empty');
                
                $i('modal-entity-image-path').value = data;
                $i('file').value = '';
                
                $t('.image-legend').forEach(function (element) {
                    addClass(element, 'hide');
                });
                
                addClass($i('image-loading'), 'hide');
            },
            
            
            hideImage = function () {
                var imageLocal = $i('image-local');
                imageLocal.src = '';
                
                addClass(imageLocal, 'hide');
                addClass($i('image-group'), 'empty');
                
                $i('modal-entity-image-path').value = '';
                $i('file').value = '';
                
                $t('.image-legend').forEach(function (element) {
                    removeClass(element, 'hide');
                });
            },

            
            showImageLoading = function () {
                removeClass($i('image-loading'), 'hide');
                
                $t('.image-legend').forEach(function (element) {
                    addClass(element, 'hide');
                });
                
                addClass($i('image-local'), 'hide');
            },


            hideImageLoading = function () {
                addClass($i('image-loading'), 'hide');
                
                $t('.image-legend').forEach(function (element) {
                    removeClass(element, 'hide');
                });
                
                addClass($i('image-local'), 'hide');
            },
            
            
            sendFileToCloudSuccess = function (data) {
                console.log('success');
                console.log(data);

                showImage(data);
            },


            sendFileToCloudError = function (data) {
                console.log('error');
                console.log(data);
                hideImageLoading();

                showMessageRegistration('Problema ao enviar imagem. ' + data.error.message);
            },

            
            sendFileToCloud = function () {
                hideMessageRegistration();
                var fileInput = $i('file');

                if (fileInput.files.length > 0) {
                    showImageLoading();
                    uploadFileToCloud(fileInput.files[0], sendFileToCloudSuccess, sendFileToCloudError);
                }
            },
            
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
                $i('modal-entity-id').value = '';
                $i('modal-entity-description').value = '';
                $i('modal-entity-site').value = '';
                $i('modal-entity-phone').value = '';
                $i('modal-entity-address').value = '';
                $i('modal-entity-no').checked = true;
                $i('modal-entity-detail').value = '';
                $i('modal-entity-latitude').value = '';
                $i('modal-entity-longitude').value = '';
                                
                $scope.selectCity = '';
                $scope.selectCategoy = '';
                
                $scope.subCategories = [];
                $scope.$apply();
                
                hideImage();
            },
            
            
            getEntity = function () {
                var id = $i('modal-entity-id').value,
                    description = $i('modal-entity-description').value,
                    site = $i('modal-entity-site').value,
                    phone = $i('modal-entity-phone').value,
                    address = $i('modal-entity-address').value,
                    wifi = document.querySelector('input[name=\'wifi\']:checked').value,
                    detail = $i('modal-entity-detail').value,
                    latitude = $i('modal-entity-latitude').value,
                    longitude = $i('modal-entity-longitude').value,
                    imagePath = $i('modal-entity-image-path').value,
                    idCategory = $i('modal-entity-id-category').value,
                    idCity = $i('modal-entity-id-city').value,
                    idCategories = [idCategory],
                    idSubCategories = getSelectedOptions($i('modal-entity-id-sub-category'));

                if (wifi === 'no') {
                    wifi = false;
                } else {
                    wifi = true;
                }
                
                return new Local(id,
                               description,
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
                
                showMessageRegistration(data.error.message);
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
                var entity = getEntity();

                console.log(entity);

                if (entity.id) {
                    $dataFactory.updateLocal(entity, saveUpdateEntitySuccess, saveUpdateEntityError);
                } else {
                    $dataFactory.insertLocal(entity, saveNewEntitySuccess, saveNewEntityError);
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
        
            getCategoriesSuccess = function (data) {
                console.log('success');
                console.log(data);
                
                $scope.categories = data.items;
                $scope.$apply();
                
                var entityIdCategory = $i('modal-entity-id-category');
                entityIdCategory.value = mIdCategory;
                entityIdCategory.removeAttribute('disabled');
            },
            
            getCategoriesError = function (data) {
                console.log('error');
                console.log(data);
            },
        
            getCitiesSuccess = function (data) {
                console.log('success');
                console.log(data);
                
                $scope.cities = data.items;
                $scope.selectCity = mIdCity;
                $scope.$apply();
                
                $i('modal-entity-id-city').removeAttribute('disabled');
            },
            
            getCitiesError = function (data) {
                console.log('error');
                console.log(data);
            },
        
            
            
            getSubCategoriesByIdCategory = function (idCategories, change) {
                var subCategories = [],
                    comboSubCategory = $i('modal-entity-id-sub-category');
                $scope.subCategories = [];

                if (mSubCategories && mSubCategories.items && idCategories) {
                    mSubCategories.items.forEach(function (element) {
                        if (element.idCategory === idCategories) {
                            subCategories.push(element);
                        }
                    });
                    
                    $scope.subCategories = subCategories;
                    
                    if (!change) {
                        $scope.$apply();
                    }
                        
                    
                    setSelectedOptions(comboSubCategory, mIdSubCategory);
                    comboSubCategory.removeAttribute('disabled');
                                        
                    mIdSubCategory = '';
                }
            },
            
            getEntitySuccess = function (data) {
                mIdCategory = data.idCategories[0];
                getSubCategoriesByIdCategory(mIdCategory);

                mIdCity = data.idCity;
                mIdSubCategory = data.idSubCategories;
                
                $i('modal-entity-id-category').value = mIdCategory;
                setSelectedOptions($i('modal-entity-id-sub-category'), mIdSubCategory);
                $i('modal-entity-description').value = data.description;
                $i('modal-entity-id').value = data.id;
                $i('modal-entity-site').value = data.site;
                $i('modal-entity-phone').value = data.phone;
                $i('modal-entity-address').value = data.address;
                $i('modal-entity-no').checked = !data.wifi;
                $i('modal-entity-yes').checked = data.wifi;
                $i('modal-entity-detail').value = data.detail;
                $i('modal-entity-latitude').value = data.latitude;
                $i('modal-entity-longitude').value = data.longitude;
                $i('modal-entity-id-city').value = data.idCity;
                                
                showImage(data.imagePath);
                
                clearTextErrors();
                mPosition = {latitude: data.latitude, longitude: data.longitude};
                initPositionMap();
            },
            
            
            getEntityError = function (data) {
                console.log('error');
                console.log(data);
            },
            
            getSubCategoriesSuccess = function (data) {
                console.log('ok');
                console.log(data);

                mSubCategories = data;
                getSubCategoriesByIdCategory(mIdCategory);
            },

            getSubCategoriesError = function (data) {
                console.log('error');
                console.log(data);
            };
        
        
        $scope.selectCityChange = function (e) {
	        console.log($scope.selectCity);
	       
            console.log(this);
            console.log(e);
            
            var entityIdCity = $i('modal-entity-id-city'),
                latitude = entityIdCity.options[entityIdCity.selectedIndex].dataset.latitude,
                longitude = entityIdCity.options[entityIdCity.selectedIndex].dataset.longitude,
                position = new google.maps.LatLng(latitude, longitude);
            
            mMap.setZoom(mZoomCity);
            mMap.setCenter(position);
        };
        
        $scope.selectCategoryChange = function () {
	        console.log($scope.selectCategory);
	        
            var values = $i('modal-entity-id-category').value;
            console.log(values);
            
            getSubCategoriesByIdCategory(values, true);
	    };
        
        $scope.init = function () {
            if (mId) {
                $dataFactory.getLocal(mId, getEntitySuccess, getEntityError);
            }
            
            $dataFactory.getSubCategories(getSubCategoriesSuccess, getSubCategoriesError);
            $dataFactory.getCities(getCitiesSuccess, getCitiesError);
            $dataFactory.getCategories(getCategoriesSuccess, getCategoriesError);
        };
        
        $scope.initPage = function () {
            hideAlertRegistration();
            initializeMap();
            
            $i('file').addEventListener('change', sendFileToCloud, false);
            
            mId = $routeParams.id;
            
            if (mId) {
                $scope.pageDescription = 'Locais';
                $scope.page = 'Atualizar';
            } else {
                $scope.pageDescription = 'Locais';
                $scope.page = 'Nova';
            }
            
            $('.form-registration').find('input,textarea').jqBootstrapValidation({
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
