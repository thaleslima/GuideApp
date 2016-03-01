'use strict';

describe('Controller: LocalNewEditCtrl', function () {

  // load the controller's module
  beforeEach(module('guideAppApp'));

  var LocalNewEditCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    LocalNewEditCtrl = $controller('LocalNewEditCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(LocalNewEditCtrl.awesomeThings.length).toBe(3);
  });
});
