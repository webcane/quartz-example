(function(angular) {
  var AppController = function($scope, Cron) {
    Cron.query(function(response) {
      $scope.cron = response ? response : [];
    });
    
    $scope.updateItem = function(item) {
      item.$update(item.expression);
    };
  };
  
  AppController.$inject = ['$scope', 'Cron'];
  angular.module("myApp.controllers").controller("AppController", AppController);
}(angular));