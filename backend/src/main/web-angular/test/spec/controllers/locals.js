'use strict';

describe('Controller: LocalsCtrl', function () {

  // load the controller's module
  beforeEach(module('guideAppApp'));

  var LocalsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    LocalsCtrl = $controller('LocalsCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(LocalsCtrl.awesomeThings.length).toBe(3);
  });
});
