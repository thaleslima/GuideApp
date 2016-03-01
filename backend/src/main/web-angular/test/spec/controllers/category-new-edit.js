'use strict';

describe('Controller: CategoryNewEditCtrl', function () {

  // load the controller's module
  beforeEach(module('guideAppApp'));

  var CategoryNewEditCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CategoryNewEditCtrl = $controller('CategoryNewEditCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(CategoryNewEditCtrl.awesomeThings.length).toBe(3);
  });
});
