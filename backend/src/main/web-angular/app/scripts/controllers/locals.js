'use strict';

/**
 * @ngdoc function
 * @name guideAppApp.controller:LocalsCtrl
 * @description
 * # LocalsCtrl
 * Controller of the guideAppApp
 */
angular.module('guideAppApp')
    .controller('LocalsCtrl', ['$scope', '$location', '$rootScope', function ($scope, $location, $rootScope) {
        
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

                $scope.searchEntity();
            },
        
            deleteEntityError = function (data) {
                console.log("error");
                console.log(data);
            },
            
            getCitiesSuccess = function(data){
                console.log("ok");
                console.log(data);

                $scope.cities = data.items;    
                $scope.$apply();   
                
                document.getElementById("search-id-city").removeAttribute("disabled");
            },

        
            getCitiesError = function(data){
                console.log("error");
                console.log(data);
            },
        
            getCategoriesSuccess = function(data){
                console.log("ok");
                console.log(data);
            
                $scope.categories = data.items;    
                $scope.$apply();
                
                document.getElementById("search-id-category").removeAttribute("disabled");
            },

            getCategoriesError = function(data){
                console.log("error");
                console.log(data);
            };
        
        
        $scope.searchEntity = function(){
            var idCategory = document.getElementById("search-id-category").value;
            var idCity = document.getElementById("search-id-city").value;

            if(idCategory && idCity) {
                $scope.getLocalsByIdCategory(idCity, idCategory, getEntitiesSuccess, getEntitiesError);
            }
        };
        
        
        
        
        $scope.init = function () {
            $rootScope.getCities(getCitiesSuccess, getCitiesError);
            $rootScope.getCategories(getCategoriesSuccess, getCategoriesError);
        };
        
        $scope.deleteEntityById = function (id) {
            $rootScope.removeLocal(id, deleteEntitySuccess, deleteEntityError);
        };
                
        $rootScope.init($scope);
        
        
    }]);
