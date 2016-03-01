/**
 * @ngdoc function
 * @name guideAppApp.controller:CategoryNewEditCtrl
 * @description
 * # CategoryNewEditCtrl
 * Controller of the guideAppApp
 */
angular.module('guideAppApp')
    .controller('CategoryNewEditCtrl', ['$scope', '$location', '$rootScope', '$routeParams', function ($scope, $location, $rootScope, $routeParams) {
        'use strict';
        
         var mId = null,           
            
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
            },
            
            
            getEntity = function () {
                var description = document.getElementById("modal-entity-description").value,
                    id = document.getElementById("modal-entity-id").value;

                return new Category(id, description);
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
                    $rootScope.updateCategory(entity, saveUpdateEntitySuccess, saveUpdateEntityError);
                } else {
                    $rootScope.insertCategory(entity, saveNewEntitySuccess, saveNewEntityError);
                }
            },
            
            
            
            getEntitySuccess = function(data){
                document.getElementById("modal-entity-description").value = data.description;
                document.getElementById("modal-entity-id").value = data.id;
                
                clearTextErrors();
            },
            
            
            getEntityError = function(data){
                console.log("error");
                console.log(data);
            };
        
        
        $scope.init = function () {
            if (mId) {
                $rootScope.getCategory(mId, getEntitySuccess, getEntityError);
            }        
        };
        
        $scope.initPage = function () {        
            hideAlertRegistration();
            mId = $routeParams.id;
            
            if(mId) {
                $scope.pageDescription = "Atualizar categoria";
                $scope.page = "Atualizar";
            } else {
                $scope.pageDescription = "Cadastro de categoria";
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
