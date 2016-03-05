/**
 * @ngdoc function
 * @name guideAppApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the guideAppApp
 */

/*global angular, $i, removeClass */
angular.module('guideAppApp')
    .controller('LoginCtrl', ['$scope', function () {
        'use strict';
        
        $i('nav-bar').style.display = 'none';
        removeClass($i('g-signin-center'), 'hide');
        
    }]);
