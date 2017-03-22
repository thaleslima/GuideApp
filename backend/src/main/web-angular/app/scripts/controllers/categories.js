

/**
 * @ngdoc function
 * @name guideAppApp.controller:CategoriesCtrl
 * @description
 * # CategoriesCtrl
 * Controller of the guideAppApp
 */

/*jslint devel: true */
/*global angular */
angular.module('guideAppApp')
    .controller('CategoriesCtrl', ['$scope', '$location', '$rootScope', '$dataFactory', function ($scope, $location, $rootScope, $dataFactory) {
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

                $dataFactory.getCategories(getEntitiesSuccess, getEntitiesError);
            },
        
            deleteEntityError = function (data) {
                console.log('error');
                console.log(data);
            };
        
        
        $scope.init = function () {
            $dataFactory.getCategories(getEntitiesSuccess, getEntitiesError);
        };
        
        $scope.deleteEntityById = function (id) {
            $dataFactory.removeCategory(id, deleteEntitySuccess, deleteEntityError);
        };
                
        $rootScope.init($scope);
    
    }]);
