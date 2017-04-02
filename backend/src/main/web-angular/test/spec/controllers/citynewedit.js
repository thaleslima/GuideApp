'use strict';

describe('Controller: CityneweditCtrl', function () {

  // load the controller's module
  beforeEach(module('guideAppApp'));

  var CityneweditCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CityneweditCtrl = $controller('CityneweditCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(CityneweditCtrl.awesomeThings.length).toBe(3);
  });
});
