

/**
 * @ngdoc function
 * @name guideAppApp.controller:SubCategoriesCtrl
 * @description
 * # SubCategoriesCtrl
 * Controller of the guideAppApp
 */
angular.module('guideAppApp')
    .controller('SubCategoriesCtrl', ['$scope', '$location', '$rootScope', function ($scope, $location, $rootScope) {
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

                $rootScope.getSubCategories(getEntitiesSuccess, getEntitiesError);
            },
        
            deleteEntityError = function (data) {
                console.log("error");
                console.log(data);
            };
        
        
        $scope.init = function () {
            $rootScope.getSubCategories(getEntitiesSuccess, getEntitiesError);
        };
        
        $scope.deleteEntityById = function (id) {
            $rootScope.removeSubCategory(id, deleteEntitySuccess, deleteEntityError);
        };
                
        $rootScope.init($scope);
        
        
    }]);
