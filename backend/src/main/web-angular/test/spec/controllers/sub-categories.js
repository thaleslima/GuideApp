'use strict';

describe('Controller: SubCategoriesCtrl', function () {

  // load the controller's module
  beforeEach(module('guideAppApp'));

  var SubCategoriesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SubCategoriesCtrl = $controller('SubCategoriesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(SubCategoriesCtrl.awesomeThings.length).toBe(3);
  });
});
