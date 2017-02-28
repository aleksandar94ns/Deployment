app.controller('HomeController', function ($scope, $http, $state, $location, $log, $rootScope, $mdSidenav, $mdDialog, $interval, NgMap, authenticationService, restaurantsService) {

    $scope.page.current = 0;

    $scope.authService = authenticationService;

    var loadData = function () {
        restaurantsService.list(function (response) {
           $scope.allRestaurants = response.data;
        });
        restaurantsService.listVisited(function(response) {
            $scope.restaurants = response.data;
        });
    };

    loadData();

    $scope.rate = function(reservation) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/rateRestaurant.html',
            controller: 'RateRestaurantController',
            locals: {
                reservation: reservation
            }
        }).finally(function () {
            loadData();
        });
    };

    // Map

    NgMap.getMap().then(function(map) {
        $scope.pinClicked = function (event, restaurant) {
            $mdDialog.show({
                parent: angular.element(document.body),
                templateUrl: 'dialog/restaurant.html',
                controller: 'RestaurantController',
                locals: { restaurant: restaurant }
            });
        };
    });
});
