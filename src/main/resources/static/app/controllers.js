(function(angular) {
  var AppController = function($scope, Cron) {
    $scope.cron = {};
    Cron.get(function(response) {
      $scope.cron.expression = response;
    });
  };
  
  AppController.$inject = ['$scope', 'Cron'];
  angular.module("myApp.controllers").controller("AppController", AppController);
}(angular));