(function(angular) {
  var CronFactory = function($resource) {
    return $resource('/cron');
  };
  
  CronFactory.$inject = ['$resource'];
  angular.module("myApp.services").factory("Cron", CronFactory);
}(angular));