

/**
 * @ngdoc function
 * @name guideAppApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the guideAppApp
 */

/*jslint devel: true */
/*global angular */
angular.module('guideAppApp')
    .controller('MainCtrl', ['$scope', '$location', '$rootScope', '$dataFactory', function ($scope, $location, $rootScope, $dataFactory) {
        'use strict';

        var
            getCitiesSuccess = function (data) {
                console.log('Success');
                $scope.cities = data.items;
                console.log(data);
                $scope.$apply();
            },
        
            getCitiesError = function (data) {
                console.log('error');
                console.log(data);
            
            },
            
            getEntitiesSuccess = function (data) {
                console.log('ok');
                console.log(data);

                if (data && data.items) {
                    var positions = [];
                    $scope.randomMarkers = [];

                    data.items.forEach(function (element, index) {
                        var p = {
                            id: index,
                            latitude: element.latitude,
                            longitude: element.longitude,
                            title: 'm'
                        };

                        positions.push(p);
                    });

                    $scope.randomMarkers = positions;
                    $scope.$apply();
                }

            },
            
            getEntitiesError = function (data) {
                console.log('error');
                console.log(data);
            };
        
        $scope.map = { center: { latitude: $rootScope.mLatitude, longitude: $rootScope.mLongitude }, zoom: 4 };
        $scope.randomMarkers = [];
        
        $scope.init = function () {
            $dataFactory.getCities(getCitiesSuccess, getCitiesError);
        };
        
        $scope.selectCityChange = function () {
	        console.log($scope.selectCity);
	        var selectCity = $scope.selectCity,
                latitude = selectCity.latitude,
                longitude = selectCity.longitude;

		    $scope.map = { center: { latitude: latitude, longitude: longitude}, zoom: 14 };
		    $dataFactory.getLocalsByIdCategory(selectCity.id, null, getEntitiesSuccess, getEntitiesError);
	    };

        $rootScope.init($scope);
        
    }]);
