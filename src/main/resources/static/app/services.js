(function(angular) {
	// cron resource
	var CronFactory = function($resource) {
		return $resource('/quartz/cron', {}, {
			update : {
				method : 'PUT'
			}
		});
	};

	CronFactory.$inject = [ '$resource' ];
	angular.module("myApp.services").factory("Cron", CronFactory);

	// simple resource
	var SimpleFactory = function($resource) {
		return $resource('/quartz/simple', {}, {
			update : {
				method : 'PUT'
			}
		});
	};

	SimpleFactory.$inject = [ '$resource' ];
	angular.module("myApp.services").factory("Simple", SimpleFactory);
}(angular));