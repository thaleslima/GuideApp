

/**
 * @ngdoc function
 * @name guideAppApp.controller:SubCategoryNewEditCtrl
 * @description
 * # SubCategoryNewEditCtrl
 * Controller of the guideAppApp
 */
angular.module('guideAppApp')
    .controller('SubCategoryNewEditCtrl', ['$scope', '$location', '$rootScope', '$routeParams', function ($scope, $location, $rootScope, $routeParams) {
        'use strict';
      
        var mId = null,
            mIdCategory = "",
            
            hideAlertRegistration = function () {
                $i("alert-registration").style.display = "none";
            },
            
            
            clearTextErrors = function () {
                $t(".help-block").forEach(function (element) {
                    element.innerText = "";
                });
                hideAlertRegistration();
            },
            
            
            cleanEntity = function () {
                document.getElementById("modal-entity-description").value = "";
                document.getElementById("modal-entity-id").value = "";
                document.getElementById("modal-entity-id-category").value = "";
            },
            
            
            getEntity = function () {
                var description = document.getElementById("modal-entity-description").value,
                    id = document.getElementById("modal-entity-id").value,
                    idCategory = document.getElementById("modal-entity-id-category").value;

                return new SubCategory(description, idCategory);
            },
            
            
            saveNewEntitySuccess = function (data) {
                console.log("success");
                console.log(data);

                cleanEntity();
            },
            
            
            saveNewEntityError = function (data) {
                console.log("error");
                console.log(data);
            },
            
            
            saveUpdateEntitySuccess = function (data) {
                console.log("success");
                console.log(data);
            },
            
            
            saveUpdateEntityError = function (data) {
                console.log("error");
                console.log(data);
            },
            
            
            saveNewEntity = function () {
                var entity = getEntity();

                if (entity.id) {
                    $rootScope.updateSubCategory(entity, saveUpdateEntitySuccess, saveUpdateEntityError);
                } else {
                    $rootScope.insertSubCategory(entity, saveNewEntitySuccess, saveNewEntityError);
                }
            },
            
            
            
            getEntitySuccess = function (data) {
                mIdCategory = data.idCategory;
                document.getElementById("modal-entity-description").value = data.description;
                document.getElementById("modal-entity-id").value = data.id;
                document.getElementById("modal-entity-id-category").value = data.idCategory;
                
                clearTextErrors();
            },
            
            
            getEntityError = function (data) {
                console.log("error");
                console.log(data);
            },
            
            getCategoriesSuccess = function (data) {
                console.log("success");
                console.log(data);
                
                $scope.categories = data.items;
                $scope.selectCategory = mIdCategory;
                
                $scope.$apply();
                
                document.getElementById("modal-entity-id-category").removeAttribute("disabled");
            },
            
            getCategoriesError = function (data) {
                console.log("error");
                console.log(data);
            };
        
        
        $scope.init = function () {
            if (mId) {
                $rootScope.getSubCategory(mId, getEntitySuccess, getEntityError);
                $rootScope.getCategories(getCategoriesSuccess, getCategoriesError);
            }
        };
        
        $scope.initPage = function () {
            hideAlertRegistration();
            mId = $routeParams.id;
            
            if (mId) {
                $scope.pageDescription = "Atualizar sub-categoria";
                $scope.page = "Atualizar";
            } else {
                $scope.pageDescription = "Cadastro de sub0categoria";
                $scope.page = "Nova";
            }
            
            $(".form-registration").find("input,textarea,select").jqBootstrapValidation({
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
