'use strict';

describe('Controller: SubCategoryNewEditCtrl', function () {

  // load the controller's module
  beforeEach(module('guideAppApp'));

  var SubCategoryNewEditCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SubCategoryNewEditCtrl = $controller('SubCategoryNewEditCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(SubCategoryNewEditCtrl.awesomeThings.length).toBe(3);
  });
});
