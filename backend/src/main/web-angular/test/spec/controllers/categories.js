'use strict';

describe('Controller: CategoriesCtrl', function () {

  // load the controller's module
  beforeEach(module('guideAppApp'));

  var CategoriesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CategoriesCtrl = $controller('CategoriesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(CategoriesCtrl.awesomeThings.length).toBe(3);
  });
});
