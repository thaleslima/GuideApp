'use strict';

/**
 * @ngdoc function
 * @name guideAppApp.controller:CategoriesCtrl
 * @description
 * # CategoriesCtrl
 * Controller of the guideAppApp
 */
angular.module('guideAppApp')
  .controller('CategoriesCtrl', ['$scope', '$location', '$rootScope', function ($scope, $location, $rootScope) {
      
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

                $rootScope.getCategories(getEntitiesSuccess, getEntitiesError);
            },
        
            deleteEntityError = function (data) {
                console.log("error");
                console.log(data);
            };
        
        
        $scope.init = function () {
            $rootScope.getCategories(getEntitiesSuccess, getEntitiesError);
        };
        
        $scope.deleteEntityById = function (id) {
            $rootScope.removeCategory(id, deleteEntitySuccess, deleteEntityError);
        };
                
        $rootScope.init($scope);
    
  }]);
