/**
 * @ngdoc function
 * @name guideAppApp.controller:CategoryNewEditCtrl
 * @description
 * # CategoryNewEditCtrl
 * Controller of the guideAppApp
 */

/*jslint devel: true */
/*global $, angular, $i, $t, Category */
angular.module('guideAppApp')
    .controller('CategoryNewEditCtrl', ['$scope', '$location', '$rootScope', '$routeParams', '$dataFactory', function ($scope, $location, $rootScope, $routeParams, $dataFactory) {
        'use strict';
        
        var mId = null,
            
            hideAlertRegistration = function () {
                $i('alert-registration').style.display = 'none';
            },
            
            
            clearTextErrors = function () {
                $t('.help-block').forEach(function (element) {
                    element.innerText = '';
                });
                hideAlertRegistration();
            },
            
            
            cleanEntity = function () {
                $i('modal-entity-description').value = '';
                $i('modal-entity-id').value = '';
            },
            
            
            getEntity = function () {
                var description = $i('modal-entity-description').value,
                    id = $i('modal-entity-id').value;

                return new Category(id, description);
            },
            
            
            saveNewEntitySuccess = function (data) {
                console.log('success');
                console.log(data);

                cleanEntity();
            },
            
            
            saveNewEntityError = function (data) {
                console.log('error');
                console.log(data);
            },
            
            
            saveUpdateEntitySuccess = function (data) {
                console.log('success');
                console.log(data);
            },
            
            
            saveUpdateEntityError = function (data) {
                console.log('error');
                console.log(data);
            },
            
            
            saveNewEntity = function () {
                var entity = getEntity();

                if (entity.id) {
                    $dataFactory.updateCategory(entity, saveUpdateEntitySuccess, saveUpdateEntityError);
                } else {
                    $dataFactory.insertCategory(entity, saveNewEntitySuccess, saveNewEntityError);
                }
            },
            
            
            
            getEntitySuccess = function (data) {
                $i('modal-entity-description').value = data.description;
                $i('modal-entity-id').value = data.id;
                
                clearTextErrors();
            },
            
            
            getEntityError = function (data) {
                console.log('error');
                console.log(data);
            };
        
        
        $scope.init = function () {
            if (mId) {
                $dataFactory.getCategory(mId, getEntitySuccess, getEntityError);
            }
        };
        
        $scope.initPage = function () {
            hideAlertRegistration();
            mId = $routeParams.id;
            
            if (mId) {
                $scope.pageDescription = 'Categorias';
                $scope.page = 'Atualizar';
            } else {
                $scope.pageDescription = 'Categorias';
                $scope.page = 'Nova';
            }
            
            $('.form-registration').find('input,textarea,select').jqBootstrapValidation({
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
