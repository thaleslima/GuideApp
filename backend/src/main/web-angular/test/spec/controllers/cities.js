'use strict';

describe('Controller: CitiesCtrl', function () {

  // load the controller's module
  beforeEach(module('guideAppApp'));

  var CitiesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CitiesCtrl = $controller('CitiesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(CitiesCtrl.awesomeThings.length).toBe(3);
  });
});
