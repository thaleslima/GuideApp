
/**
 * @ngdoc function
 * @name guideAppApp.controller:LocalNewEditCtrl
 * @description
 * # LocalNewEditCtrl
 * Controller of the guideAppApp
 */

/*jslint plusplus: true, devel: true */
/*global $, angular, $i, $t, google, uploadFileToCloud, Local */
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
                document.getElementById('alert-registration').style.display = 'none';
            },
            
            showMessageRegistration = function (message) {
                document.getElementById('alert-registration').style.display = 'block';
                document.getElementById('alert-registration-message').innerHTML = message;
            },
            
            showImage = function (data) {
                var imageLocal = $('#image-local');
                imageLocal.attr('src', data);
                imageLocal.removeClass('hide');

                $('#modal-entity-image-path').val(data);
                $('#file').val('');

                $('.image').removeClass('empty');
                $('.image-legend').addClass('hide');
                $('.image-loading').addClass('hide');
            },
            
            
            hideImage = function () {
                var imageLocal = $('#image-local');
                imageLocal.attr('src', '');
                imageLocal.addClass('hide');

                $('#modal-entity-image-path').val('');
                $('#file').val('');

                $('.image').addClass('empty');
                $('.image-legend').removeClass('hide');
            },

            
            showImageLoading = function () {
                $('.image-loading').removeClass('hide');
                $('.image-legend').addClass('hide');
                $('#image-local').addClass('hide');
            },


            hideImageLoading = function () {
                $('.image-loading').addClass('hide');
                $('.image-legend').removeClass('hide');
                $('#image-local').addClass('hide');
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
                var fileInput = document.getElementById('file');

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

                document.getElementById('modal-entity-latitude').value = lat;
                document.getElementById('modal-entity-longitude').value = lng;
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
                document.getElementById('modal-entity-id').value = '';
                document.getElementById('modal-entity-description').value = '';
                document.getElementById('modal-entity-site').value = '';
                document.getElementById('modal-entity-phone').value = '';
                document.getElementById('modal-entity-address').value = '';
                document.getElementById('modal-entity-no').checked = true;
                document.getElementById('modal-entity-detail').value = '';
                document.getElementById('modal-entity-latitude').value = '';
                document.getElementById('modal-entity-longitude').value = '';
                document.getElementById('modal-entity-id-city').value = '';

                document.getElementById('modal-entity-id-category').value = '';
                
                var subCategory = document.getElementById('modal-entity-id-sub-category');
                subCategory.innerHTML = '';
                subCategory.value = '';
                
                hideImage();
            },
            
            
            getEntity = function () {
                var id = document.getElementById('modal-entity-id').value,
                    description = document.getElementById('modal-entity-description').value,
                    site = document.getElementById('modal-entity-site').value,
                    phone = document.getElementById('modal-entity-phone').value,
                    address = document.getElementById('modal-entity-address').value,
                    wifi = document.querySelector('input[name=\'wifi\']:checked').value,
                    detail = document.getElementById('modal-entity-detail').value,
                    latitude = document.getElementById('modal-entity-latitude').value,
                    longitude = document.getElementById('modal-entity-longitude').value,
                    imagePath = document.getElementById('modal-entity-image-path').value,
                    idCategory = document.getElementById('modal-entity-id-category').value,
                    idCity = document.getElementById('modal-entity-id-city').value,
                    idCategories = [idCategory],
                    idSubCategories = $('#modal-entity-id-sub-category').val();

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
                
                var latitude = document.getElementById('modal-entity-latitude').value,
                    longitude = document.getElementById('modal-entity-longitude').value;
                
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

                mMap = new google.maps.Map(document.getElementById('map-canvas'), mapProp);

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
                
                $('#modal-entity-id-category').val(mIdCategory).removeAttr('disabled');
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
                
                document.getElementById('modal-entity-id-city').removeAttribute('disabled');
            },
            
            getCitiesError = function (data) {
                console.log('error');
                console.log(data);
            },
        
            
            
            getSubCategoriesByIdCategory = function (idCategories, change) {
                var subCategories = [];
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
                    
                    $('#modal-entity-id-sub-category').removeAttr('disabled').val(mIdSubCategory);
                    
                    mIdSubCategory = '';
                }
            },
            
            
            getEntitySuccess = function (data) {
                mIdCategory = data.idCategories[0];
                getSubCategoriesByIdCategory(mIdCategory);

                
                mIdCity = data.idCity;
                mIdSubCategory = data.idSubCategories;
                
                $('#modal-entity-id-category').val(mIdCategory);
                $('#modal-entity-id-sub-category').val(data.idSubCategories);
                document.getElementById('modal-entity-description').value = data.description;
                document.getElementById('modal-entity-id').value = data.id;
                document.getElementById('modal-entity-site').value = data.site;
                document.getElementById('modal-entity-phone').value = data.phone;
                document.getElementById('modal-entity-address').value = data.address;
                document.getElementById('modal-entity-no').checked = !data.wifi;
                document.getElementById('modal-entity-yes').checked = data.wifi;
                document.getElementById('modal-entity-detail').value = data.detail;
                document.getElementById('modal-entity-latitude').value = data.latitude;
                document.getElementById('modal-entity-longitude').value = data.longitude;
                document.getElementById('modal-entity-id-city').value = data.idCity;
                                
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
        
        
        $scope.selectCityChange = function () {
	        console.log($scope.selectCity);
	       
            var latitude = $('#modal-entity-id-city').find(':selected').data('latitude'),
                longitude = $('#modal-entity-id-city').find(':selected').data('longitude'),
                position = new google.maps.LatLng(latitude, longitude);
            
            mMap.setZoom(mZoomCity);
            mMap.setCenter(position);
        };
        
        $scope.selectCategoryChange = function () {
	        console.log($scope.selectCategory);
	        
            var values = $('#modal-entity-id-category').val();
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
            
            $('#file').change(sendFileToCloud);
            
            mId = $routeParams.id;
            
            if (mId) {
                $scope.pageDescription = 'Atualizar Local';
                $scope.page = 'Atualizar';
            } else {
                $scope.pageDescription = 'Cadastro de Local';
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
