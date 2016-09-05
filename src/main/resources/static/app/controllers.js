(function(angular) {
  var AppController = function($scope, Cron) {
    // cron
    $scope.cron = {};
    
    Cron.get(function(response) {
      $scope.cron = response;
    });
    
    $scope.update = function(cron) {
    	cron.$update();	
    };

    // hours
    $scope.hours = {
      choises: [
        {
          id: 1,
          value: 3,
          text: "Каждые 3ч",
          side: "left"
        },
        {
          id: 2,
          value: 4,
          text: "Каждые 4ч",
          side: "left"
        },
        {
          id: 3,
          value: 6,
          text: "Каждые 6ч",
          side: "left"
        },
        {
          id: 4,
          value: 12,
          text: "Каждые 12ч",
          side: "right"
        },
        {
          id: 5,
          value: 24,
          text: "Каждые 24ч",
          side: "right"
        },
        {
          id: 6,
          value: 48,
          text: "Каждые 48ч",
          side: "right"
        }
      ]
    };
  };
  
  AppController.$inject = ['$scope', 'Cron'];
  angular.module("myApp.controllers").controller("AppController", AppController);
}(angular));