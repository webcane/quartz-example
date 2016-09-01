(function(angular) {
  var CronFactory = function($resource) {
    return $resource('/cron', {}, {
      update: {
        method: "PUT"
      }
    });
  };
  
  CronFactory.$inject = ['$resource'];
  angular.module("myApp.services").factory("Cron", CronFactory);
}(angular));