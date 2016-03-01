/**
 * @ngdoc function
 * @name guideAppApp.controller:CitiesCtrl
 * @description
 * # CitiesCtrl
 * Controller of the guideAppApp
 */
angular.module('guideAppApp')
    .controller('CitiesCtrl', ['$scope', '$location', '$rootScope', function ($scope, $location, $rootScope) {
        'use strict';
        
        var
            getEntitiesSuccess = function (data) {
                console.log("success");
                console.log(data);
                $scope.entities = data.items;
                $scope.$apply();
            },
        
            getEntitiesError = function (data) {
                console.log("error");
                console.log(data);
            },
            
            deleteEntitySuccess = function (data) {
                console.log("success");
                console.log(data);

                $rootScope.getCities(getCitiesSuccess, getCitiesError);
            },
        
            deleteEntityError = function (data) {
                console.log("error");
                console.log(data);
            };
        
        
        $scope.init = function () {
            $rootScope.getCities(getEntitiesSuccess, getEntitiesError);
        };
        
        $scope.deleteEntityById = function (id) {
            $rootScope.removeCity(id, deleteEntitySuccess, deleteEntityError);
        };
                
        $rootScope.init($scope);
    }]);