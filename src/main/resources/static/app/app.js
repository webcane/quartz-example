(function(angular) {
  angular.module("myApp.controllers", ["ui-notification"]);
  angular.module("myApp.services", []);
  angular.module("myApp", ["ngResource", "myApp.controllers", "myApp.services"]);
}(angular));