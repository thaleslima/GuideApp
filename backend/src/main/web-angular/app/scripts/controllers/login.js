/**
 * @ngdoc function
 * @name guideAppApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the guideAppApp
 */

/*global angular, $ */
angular.module('guideAppApp')
    .controller('LoginCtrl', ['$scope', function () {
        'use strict';
        document.getElementById('nav-bar').style.display = 'none';
        $('.g-signin-center').removeClass('hide');
    }]);
