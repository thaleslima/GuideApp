'use strict';

/**
 * @ngdoc function
 * @name guideAppApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the guideAppApp
 */
angular.module('guideAppApp')
    .controller('LoginCtrl', ['$scope', function ($scope) {
        document.getElementById("nav-bar").style.display = "none";        
        $(".g-signin-center").removeClass("hide");
    }]);
