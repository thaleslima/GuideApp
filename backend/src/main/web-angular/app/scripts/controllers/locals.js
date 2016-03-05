

/**
 * @ngdoc function
 * @name guideAppApp.controller:LocalsCtrl
 * @description
 * # LocalsCtrl
 * Controller of the guideAppApp
 */

/*jslint devel: true */
/*global angular, $i */
angular.module('guideAppApp')
    .controller('LocalsCtrl', ['$scope', '$location', '$rootScope', '$dataFactory', function ($scope, $location, $rootScope, $dataFactory) {
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

                $scope.searchEntity();
            },
        
            deleteEntityError = function (data) {
                console.log('error');
                console.log(data);
            },
            
            getCitiesSuccess = function (data) {
                console.log('ok');
                console.log(data);

                $scope.cities = data.items;
                $scope.$apply();
                
                $i('search-id-city').removeAttribute('disabled');
            },

        
            getCitiesError = function (data) {
                console.log('error');
                console.log(data);
            },
        
            getCategoriesSuccess = function (data) {
                console.log('ok');
                console.log(data);
            
                $scope.categories = data.items;
                $scope.$apply();
                
                $i('search-id-category').removeAttribute('disabled');
            },

            getCategoriesError = function (data) {
                console.log('error');
                console.log(data);
            };
        
        
        $scope.searchEntity = function () {
            var idCategory = $i('search-id-category').value,
                idCity = $i('search-id-city').value;

            if (idCategory && idCity) {
                $dataFactory.getLocalsByIdCategory(idCity, idCategory, getEntitiesSuccess, getEntitiesError);
            }
        };
        
        
        $scope.init = function () {
            $dataFactory.getCities(getCitiesSuccess, getCitiesError);
            $dataFactory.getCategories(getCategoriesSuccess, getCategoriesError);
        };
        
        $scope.deleteEntityById = function (id) {
            $dataFactory.removeLocal(id, deleteEntitySuccess, deleteEntityError);
        };
                
        $rootScope.init($scope);
    }]);
