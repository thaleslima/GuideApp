/**
 * @ngdoc function
 * @name guideAppApp.controller:CitiesCtrl
 * @description
 * # CitiesCtrl
 * Controller of the guideAppApp
 */

/*jslint devel: true */
/*global angular */
angular.module('guideAppApp')
    .controller('CitiesCtrl', ['$scope', '$location', '$rootScope', '$dataFactory', function ($scope, $location, $rootScope, $dataFactory) {
        'use strict';
        
        var
            getEntitiesSuccess = function (data) {
                console.log('success');
                console.log(data);
                $scope.entities = data.items;
                $scope.$apply();
            },
        
            getEntitiesError = function (data) {
                console.log('error');
                console.log(data);
            },
            
            deleteEntitySuccess = function (data) {
                console.log('success');
                console.log(data);

                $dataFactory.getCities(getEntitiesSuccess, getEntitiesError);
            },
        
            deleteEntityError = function (data) {
                console.log('error');
                console.log(data);
            };
        
        
        $scope.init = function () {
            $dataFactory.getCities(getEntitiesSuccess, getEntitiesError);
        };
        
        $scope.deleteEntityById = function (id) {
            $dataFactory.removeCity(id, deleteEntitySuccess, deleteEntityError);
        };
                
        $rootScope.init($scope);
    }]);