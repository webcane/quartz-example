(function(angular) {
	// cron controller
	var CronController = function($scope, Notification, Cron) {
		$scope.cron = {};

		Cron.get(function(response) {
			$scope.cron = response;
		});

		$scope.update = function(cron) {
			cron.$update();
            Notification.primary('cron expression was updated successfully!')
		};

	};

	CronController.$inject = [ '$scope', 'Notification', 'Cron' ];
	angular.module("myApp.controllers").controller("CronController",
        CronController);

	// simple controller
	var SimpleController = function($scope, Notification, Simple) {
		$scope.simple = {};
		$scope.myChoise = {};

		// hours
		$scope.hours = {
			choises : [ {
				id : 1,
				value : 3,
				text : "Каждые 3ч",
				side : "left"
			}, {
				id : 2,
				value : 4,
				text : "Каждые 4ч",
				side : "left"
			}, {
				id : 3,
				value : 6,
				text : "Каждые 6ч",
				side : "left"
			}, {
				id : 4,
				value : 12,
				text : "Каждые 12ч",
				side : "right"
			}, {
				id : 5,
				value : 24,
				text : "Каждые 24ч",
				side : "right"
			}, {
				id : 6,
				value : 48,
				text : "Каждые 48ч",
				side : "right"
			} ]
		};

		Simple.get(function(response) {
			$scope.simple = response;
			
			// select choise
			$scope.hours.choises.forEach(function(item, i, arr) {
				if (response.intervalInHours === item.value) {
					$scope.myChoise = item;
				}
			});
		});

		$scope.updateChoise = function() {
			$scope.simple.$update();
            Notification.primary('choise was updated successfully!');
		};
		
		
		$scope.setChoise = function (choise) {
			$scope.simple.intervalInHours = choise.value;
		};

	};

	SimpleController.$inject = [ '$scope', 'Notification', 'Simple' ];
	angular.module("myApp.controllers").controller("SimpleController",
        SimpleController);
}(angular));