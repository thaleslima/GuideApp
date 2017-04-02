

/**
 * @ngdoc function
 * @name guideAppApp.controller:SubCategoryNewEditCtrl
 * @description
 * # SubCategoryNewEditCtrl
 * Controller of the guideAppApp
 */

/*jslint devel: true */
/*global $, angular, $i, $t, SubCategory */
angular.module('guideAppApp')
    .controller('SubCategoryNewEditCtrl', ['$scope', '$location', '$rootScope', '$routeParams', '$dataFactory', function ($scope, $location, $rootScope, $routeParams, $dataFactory) {
        'use strict';
      
        var mId = null,
            mIdCategory = '',
            
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
                
                $scope.selectCategory = '';
            },
            
            
            getEntity = function () {
                var description = $i('modal-entity-description').value,
                    id = $i('modal-entity-id').value,
                    idCategory = $i('modal-entity-id-category').value;

                return new SubCategory(id, description, idCategory);
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
                    $dataFactory.updateSubCategory(entity, saveUpdateEntitySuccess, saveUpdateEntityError);
                } else {
                    $dataFactory.insertSubCategory(entity, saveNewEntitySuccess, saveNewEntityError);
                }
            },
            
            
            
            getEntitySuccess = function (data) {
                mIdCategory = data.idCategory;
                $i('modal-entity-description').value = data.description;
                $i('modal-entity-id').value = data.id;
                $i('modal-entity-id-category').value = data.idCategory;
                
                clearTextErrors();
            },
            
            
            getEntityError = function (data) {
                console.log('error');
                console.log(data);
            },
            
            getCategoriesSuccess = function (data) {
                console.log('success');
                console.log(data);
                
                $scope.categories = data.items;
                $scope.selectCategory = mIdCategory;
                
                $scope.$apply();
                
                $i('modal-entity-id-category').removeAttribute('disabled');
            },
            
            getCategoriesError = function (data) {
                console.log('error');
                console.log(data);
            };
        
        
        $scope.init = function () {
            if (mId) {
                $dataFactory.getSubCategory(mId, getEntitySuccess, getEntityError);
            }
            $dataFactory.getCategories(getCategoriesSuccess, getCategoriesError);
        };
        
        $scope.initPage = function () {
            hideAlertRegistration();
            mId = $routeParams.id;
            
            if (mId) {
                $scope.pageDescription = 'Sub-Categorias';
                $scope.page = 'Atualizar';
            } else {
                $scope.pageDescription = 'Sub-Categorias';
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
